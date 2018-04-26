package me.nikl.cookieclicker.data;

import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.gamebox.GameBox;
import me.nikl.gamebox.common.zaxxer.hikari.HikariDataSource;
import me.nikl.gamebox.data.database.DataBase;
import me.nikl.gamebox.data.database.MysqlDB;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Niklas Eicker
 */
public class MySQLDatabase extends Database {
    private static final String TABLE = "cookieclicker_saves";
    private static final String PLAYER_UUID = "uuid";
    private static final String COOKIES_TOTAL = "cookies_total";
    private static final String COOKIES_CLICKED = "cookies_clicked";
    private static final String COOKIES_CURRENT = "cookies_current";
    private static final String UPGRADES = "upgrades";

    private static final String SAVE = "UPDATE " + TABLE + " SET " + COOKIES_TOTAL + "=?, " + COOKIES_CLICKED + "=?, " + COOKIES_CURRENT + "=?, " + UPGRADES  + "=? %buildings% WHERE " + PLAYER_UUID + "=?";
    private static final String INSERT = "INSERT IGNORE " + TABLE + "(" + PLAYER_UUID + ") VALUES(?)";
    private static final String SELECT = "SELECT * FROM `" + TABLE + "` WHERE " + PLAYER_UUID + "=?";
    private static final String BUILDING_SAVE = ", `%building%`=%count%";
    private MysqlDB mysqlDB;
    private HikariDataSource hikari;

    public MySQLDatabase(CookieClicker cookieClicker) {
        super(cookieClicker);
        this.mysqlDB = (MysqlDB) gameBox.getDataBase();
        hikari = mysqlDB.getHikariDataSource();
        prepareDatabase();
    }

    @Override
    public void getGameSave(UUID uuid, String gameType, DataBase.Callback<GameSave> callback) {
        // load player from database and set the results in the player class
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                try (Connection connection = hikari.getConnection();
                     PreparedStatement insert = connection.prepareStatement(INSERT);
                     PreparedStatement select = connection.prepareStatement(SELECT)) {
                    GameSave.Builder builder =new GameSave.Builder(uuid, gameType);
                    insert.setString(1, uuid.toString());
                    insert.execute();

                    select.setString(1, uuid.toString());
                    ResultSet result = select.executeQuery();
                    if (result.next()) {
                        builder.setCookiesTotal(result.getDouble(COOKIES_TOTAL));
                        builder.setCookiesClicked(result.getDouble(COOKIES_CLICKED));
                        builder.setCookiesCurrent(result.getDouble(COOKIES_CURRENT));
                        builder.setUpgrades(result.getString(UPGRADES));
                        for (Buildings building : Buildings.values()) {
                            builder.addBuilding(building, result.getInt(building.toString()));
                        }

                        // back to main thread and set player
                        Bukkit.getScheduler().runTask(gameBox, () ->
                                callback.onSuccess(builder.build()));
                        try {
                            result.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            result.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Bukkit.getScheduler().runTask(gameBox, () ->
                                callback.onSuccess(builder.build()));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        task.runTaskAsynchronously(gameBox);
    }

    @Override
    public void saveGame(GameSave gameSave, boolean async) {
        // must work async and sync since sync is needed on server shutdown
        if (async) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    saveGame(gameSave);
                }
            }.runTaskAsynchronously(cookieClicker.getGameBox());
        } else {
            saveGame(gameSave);
        }
    }

    public void saveGame(GameSave gameSave) {
        StringBuilder builder = new StringBuilder();
        for (Buildings building : Buildings.values()) {
            builder.append(BUILDING_SAVE
                    .replace("%building%", building.toString())
                    .replace("%count%", String.valueOf(gameSave.getBuildings().getOrDefault(building, 0))));
        }
        String buildingsQuery = builder.toString();
        try (Connection connection = hikari.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE.replace("%buildings%", buildingsQuery))) {
            GameBox.debug("Query: " + SAVE.replace("%buildings%", buildingsQuery));
            statement.setDouble(1, gameSave.getCookies().getOrDefault(GameSave.TOTAL, 0.));
            statement.setDouble(2, gameSave.getCookies().getOrDefault(GameSave.CLICKED, 0.));
            statement.setDouble(3, gameSave.getCookies().getOrDefault(GameSave.CURRENT, 0.));
            statement.setString(4, gameSave.getUpgradesAsString());
            statement.setString(5, gameSave.getUuid().toString());
            statement.execute();
        } catch (SQLException e) {
            cookieClicker.warn("Error while saving a game to the database!");
            e.printStackTrace();
        }
    }

    @Override
    public void onShutDown() {
        // nothing to do
    }

    @Override
    public void deleteSaves(String gameType) {

    }

    @Override
    public void deleteSaves(UUID player) {

    }

    @Override
    public void deleteSave(String gameType, UUID player) {

    }

    private void prepareDatabase() {
        try (Connection connection = hikari.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `" + TABLE + "`(`" +
                    PLAYER_UUID + "` varchar(36) NOT NULL, `" +
                    COOKIES_TOTAL + "` DOUBLE NOT NULL DEFAULT '0', `" +
                    COOKIES_CLICKED + "` DOUBLE NOT NULL DEFAULT '0', `" +
                    COOKIES_CURRENT + "` DOUBLE NOT NULL DEFAULT '0', `" +
                    // just ensure that there will always be enough place for a list of all upgrade IDs... 64k is max length for the whole row
                    UPGRADES + "` varchar(10000) NOT NULL DEFAULT '', " +
                    "PRIMARY KEY (`" + PLAYER_UUID + "`))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addAllBuildingsToTable();
    }

    private void addAllBuildingsToTable() {
        List<String> querries = new ArrayList<>();
        for (Buildings building : Buildings.values()) {
            String name = building.toString();
            if (!doesColumnExist(name)) {
                querries.add(" ADD `" + name + "` INT NOT NULL DEFAULT '0'");
            }
        }
        if (!querries.isEmpty()) {
            try (Connection connection = hikari.getConnection();
                 Statement statement = connection.createStatement()) {
                GameBox.debug("  Adding " + querries.size() + " columns");
                statement.executeUpdate("ALTER TABLE `" + TABLE + "` " + String.join(",", querries));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean doesColumnExist(String columnName) {
        try (Connection connection = hikari.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM information_schema.COLUMNS " +
                            "WHERE TABLE_SCHEMA = '" + hikari.getDataSourceProperties().getProperty("databaseName") + "'" +
                            "AND TABLE_NAME = '" + TABLE + "'" +
                            "AND COLUMN_NAME = '" + columnName + "'");
            if (!resultSet.next()) {
                GameBox.debug("  Column does not exist");
                return false;
            } else {
                GameBox.debug("  Column already exists");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
