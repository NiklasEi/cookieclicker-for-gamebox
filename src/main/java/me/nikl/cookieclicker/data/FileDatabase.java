package me.nikl.cookieclicker.data;

import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.gamebox.data.database.DataBase;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

/**
 * @author Niklas Eicker
 */
public class FileDatabase extends Database {
    private File savesFile;
    private FileConfiguration saves;

    public FileDatabase(CookieClicker cookieClicker) {
        super(cookieClicker);

        savesFile = new File(cookieClicker.getDataFolder().toString() + File.separatorChar + "saves.yml");
        if (!savesFile.exists()) {
            try {
                savesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.saves = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(savesFile), "UTF-8"));
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getGameSave(UUID uuid, String gameType, DataBase.Callback<GameSave> callback) {
        if (!saves.isConfigurationSection(gameType + "." + uuid.toString())) {
            callback.onSuccess(null);
            return;
        }
        GameSave.Builder builder = new GameSave.Builder(uuid, gameType);
        ConfigurationSection save = saves.getConfigurationSection(gameType + "." + uuid.toString());
        if (save.isConfigurationSection("cookies")) {
            ConfigurationSection cookieSection = save.getConfigurationSection("cookies");
            builder.setCookiesCurrent(cookieSection.getDouble(GameSave.CURRENT, 0.));
            builder.setCookiesClicked(cookieSection.getDouble(GameSave.CLICKED, 0.));
            builder.setCookiesTotal(cookieSection.getDouble(GameSave.TOTAL, 0.));
        }
        if (save.isConfigurationSection("productions")) {
            Buildings building;
            for (String key : save.getConfigurationSection("productions").getKeys(false)) {
                try {
                    building = Buildings.valueOf(key);
                    builder.addBuilding(building, save.getInt("productions" + "." + key, 0));
                } catch (IllegalArgumentException exception) {
                    // ignore
                }
            }
        }
        List<Integer> upgrades = save.getIntegerList("upgrades");
        if (upgrades != null && !upgrades.isEmpty()) {
            builder.setUpgrades(upgrades);
        }
        callback.onSuccess(builder.build());
    }

    @Override
    public void saveGame(GameSave gameSave, boolean async) {
        for (String key : gameSave.getCookies().keySet()) {
            saves.set(gameSave.getGameType() + "." + gameSave.getUuid().toString() + "." + "cookies" + "." + key, Math.floor(gameSave.getCookies().get(key)));
        }
        for (Buildings building : gameSave.getBuildings().keySet()) {
            saves.set(gameSave.getGameType() + "." + gameSave.getUuid().toString() + "." + "productions" + "." + building.toString(), gameSave.getBuildings().get(building));
        }
        saves.set(gameSave.getGameType() + "." + gameSave.getUuid().toString() + "." + "upgrades", gameSave.getUpgrades());
    }

    @Override
    public void onShutDown() {
        save();
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

    private void save() {
        try {
            saves.save(savesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
