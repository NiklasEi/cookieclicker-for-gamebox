package me.nikl.cookieclicker;

import me.nikl.gamebox.data.toplist.SaveType;
import me.nikl.gamebox.game.rules.GameRuleMultiRewards;

/**
 * Created by Niklas
 *
 * Game rules container for Cookie Clicker
 */
public class CCGameRules extends GameRuleMultiRewards {
    private int moveCookieAfterClicks;
    private int idleSeconds;

    public CCGameRules(String key, double cost, int moveCookieAfterClicks, boolean saveStats, int idleSeconds) {
        super(key, saveStats, SaveType.HIGH_NUMBER_SCORE, cost);
        this.moveCookieAfterClicks = moveCookieAfterClicks;
        this.idleSeconds = idleSeconds;
    }

    public int getMoveCookieAfterClicks() {
        return moveCookieAfterClicks;
    }

    public int getIdleSeconds() {
        return idleSeconds;
    }
}
