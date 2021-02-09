package me.nikl.cookieclicker;

import me.nikl.gamebox.data.toplist.SaveType;
import me.nikl.gamebox.game.rules.GameRuleMultiRewards;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by Niklas
 *
 * Game rules container for Cookie Clicker
 */
public class CCGameRules extends GameRuleMultiRewards {
    private int moveCookieAfterClicks;
    private int idleSeconds;

    public CCGameRules(CookieClicker game, String key, double cost, int moveCookieAfterClicks, boolean saveStats, int idleSeconds) {
        super(key, saveStats, SaveType.HIGH_NUMBER_SCORE, cost);
        loadRewards(game);
        this.moveCookieAfterClicks = moveCookieAfterClicks;
        this.idleSeconds = idleSeconds;
    }

    private void loadRewards(CookieClicker game) {
        if(!game.getConfig().isConfigurationSection("gameBox.gameButtons." + key + ".scoreIntervals")) return;
        ConfigurationSection onGameEnd = game.getConfig().getConfigurationSection("gameBox.gameButtons." + key + ".scoreIntervals");
        for (String key : onGameEnd.getKeys(false)) {
            double doubleKey;
            try {
                doubleKey = Double.parseDouble(key);
            } catch (NumberFormatException e) {
                Bukkit.getLogger().warning(game.getGameLang().PREFIX + " NumberFormatException while getting the rewards from config!");
                continue;
            }
            if (onGameEnd.isSet(key + ".money") && (onGameEnd.isDouble(key + ".money") || onGameEnd.isInt(key + ".money"))) {
                addMoneyReward(doubleKey, onGameEnd.getDouble(key + ".money"));
            } else {
                addMoneyReward(doubleKey, 0.);
            }
            if (onGameEnd.isSet(key + ".tokens") && (onGameEnd.isDouble(key + ".tokens") || onGameEnd.isInt(key + ".tokens"))) {
                addTokenReward(doubleKey, onGameEnd.getInt(key + ".tokens"));
            } else {
                addTokenReward(doubleKey, 0);
            }
        }
    }

    public int getMoveCookieAfterClicks() {
        return moveCookieAfterClicks;
    }

    public int getIdleSeconds() {
        return idleSeconds;
    }
}
