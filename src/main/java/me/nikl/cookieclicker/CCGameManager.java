package me.nikl.cookieclicker;

import me.nikl.cookieclicker.data.GameSave;
import me.nikl.gamebox.data.database.DataBase;
import me.nikl.gamebox.data.toplist.SaveType;
import me.nikl.gamebox.game.exceptions.GameStartException;
import me.nikl.gamebox.game.manager.GameManager;
import me.nikl.gamebox.game.rules.GameRule;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

/**
 * @author Nikl
 *
 * GameManager
 */

public class CCGameManager implements GameManager {
    private CookieClicker game;
    private BukkitTask saveInterval;
    private Map<UUID, CCGame> games = new HashMap<>();

    private Map<String, CCGameRules> gameRules = new HashMap<>();
    private DataBase statistics;

    public CCGameManager(CookieClicker game) {
        this.game = game;
        this.statistics = game.getGameBox().getDataBase();
        setupAutoSave();
    }

    private void setupAutoSave() {
        if (!game.getConfig().isConfigurationSection("autoSave")) {
            return;
        }
        ConfigurationSection autoSave = game.getConfig().getConfigurationSection("autoSave");
        if (!autoSave.getBoolean("enabled", true)) {
            return;
        }
        int interval = autoSave.getInt("interval", 300);
        if (interval < 1) {
            interval = 300;
        }
        saveInterval = new BukkitRunnable() {
            @Override
            public void run() {
                games.values().forEach(game -> game.saveGameAndStatistics(true));
            }
        }.runTaskTimer(game.getGameBox(), interval * 20, interval * 20);
    }

    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        if (!games.containsKey(inventoryClickEvent.getWhoClicked().getUniqueId())) return;
        CCGame game = games.get(inventoryClickEvent.getWhoClicked().getUniqueId());
        game.onClick(inventoryClickEvent);
    }


    public void onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {
        if (!games.containsKey(inventoryCloseEvent.getPlayer().getUniqueId())) return;

        // do same stuff as on removeFromGame()
        removeFromGame(inventoryCloseEvent.getPlayer().getUniqueId());
    }


    public boolean isInGame(UUID uuid) {
        return games.containsKey(uuid);
    }


    public void startGame(Player[] players, boolean playSounds, String... strings) throws GameStartException {
        if (strings.length != 1) {
            Bukkit.getLogger().log(Level.WARNING, " unknown number of arguments to start a game: " + Arrays.asList(strings));
            throw new GameStartException(GameStartException.Reason.ERROR);
        }
        CCGameRules rule = gameRules.get(strings[0]);
        if (rule == null) {
            Bukkit.getLogger().log(Level.WARNING, " unknown gametype: " + Arrays.asList(strings));
            throw new GameStartException(GameStartException.Reason.ERROR);
        }
        if (!game.payIfNecessary(players[0], rule.getCost())) {
            throw new GameStartException(GameStartException.Reason.NOT_ENOUGH_MONEY);
        }
        games.put(players[0].getUniqueId(), new CCGame(rule, this, players[0], playSounds));
    }


    public void removeFromGame(UUID uuid) {
        CCGame game = games.get(uuid);
        if (game == null) return;
        game.cancel();
        game.onGameEnd(true);
        games.remove(uuid);
    }

    public void loadGameRules(ConfigurationSection buttonSec, String buttonID) {
        int moveCookieAfterClicks = buttonSec.getInt("moveCookieAfterClicks", 0);
        if (moveCookieAfterClicks < 1) moveCookieAfterClicks = 0;
        int idleSeconds = buttonSec.getInt("idleSeconds", 0);
        if (idleSeconds < 1) idleSeconds = 0;
        double cost = buttonSec.getDouble("cost", 0.);
        boolean saveStats = buttonSec.getBoolean("saveStats", false);
        CCGameRules rules = new CCGameRules(this.game, buttonID, cost, moveCookieAfterClicks, saveStats,idleSeconds);
        if (buttonSec.isConfigurationSection("rewards")) {
            ConfigurationSection rewards = buttonSec.getConfigurationSection("rewards");
            for (String key : rewards.getKeys(false)) {
                try {
                    int minScore = Integer.parseInt(key);
                    int token = rewards.getInt(key + ".token", 0);
                    double money = rewards.getDouble(key + ".money", 0.);
                    rules.addMoneyReward(minScore, money);
                    rules.addTokenReward(minScore, token);
                } catch (NumberFormatException exception) {
                    continue;
                }
            }
        }
        gameRules.put(buttonID, rules);
    }

    @Override
    public Map<String, ? extends GameRule> getGameRules() {
        return gameRules;
    }

    public void onShutDown() {
        if (saveInterval != null && !saveInterval.isCancelled()) {
            saveInterval.cancel();
        }
        // save all open games!
        for (CCGame game : games.values()) {
            game.cancel();
            game.onGameEnd(false);
        }
    }

    public void restart(String key) {
        CCGameRules rule = gameRules.get(key);
        if (rule == null) return;
        Set<Player> players = new HashSet<>();
        for (CCGame game : games.values()) {
            if (game.getRule().getKey().equals(key)) {
                players.add(game.getPlayer());
            }
        }
        for (Player player : players) {
            if (player != null) player.closeInventory();
        }
        // here pay out any rewards and gather the top players

        // ToDo: update GameBox and allow for removing of statistics

        // delete saves
        game.getDatabase().deleteSaves(key);
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    public void saveStatistics(GameSave save, boolean async) {
        statistics.addStatistics(save.getUuid(), game.getGameID(), save.getGameType(),
                Math.floor(save.getCookies().getOrDefault("total", 0.)), SaveType.HIGH_NUMBER_SCORE, async);
    }

    public CookieClicker getCookieClicker() {
        return this.game;
    }

    public void updateGlobalBoosts() {
        for (CCGame game : games.values()) {
            game.updateGlobalBoosts();
        }
    }
}
