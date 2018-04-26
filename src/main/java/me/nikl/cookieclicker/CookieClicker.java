package me.nikl.cookieclicker;

import me.nikl.cookieclicker.data.Database;
import me.nikl.cookieclicker.data.FileDatabase;
import me.nikl.cookieclicker.data.MySQLDatabase;
import me.nikl.gamebox.GameBox;
import me.nikl.gamebox.GameBoxSettings;
import me.nikl.gamebox.game.Game;
import me.nikl.gamebox.game.GameSettings;

/**
 * Created by Niklas
 *
 * Main class of the GameBox game Cookie Clicker
 */
public class CookieClicker extends Game {
    private Database database;

    public CookieClicker(GameBox gameBox) {
        super(gameBox, GameBox.MODULE_COOKIECLICKER);
    }

    @Override
    public void onDisable() {
        ((CCGameManager) gameManager).onShutDown();
        database.onShutDown();
    }

    @Override
    public void init() {
        if (GameBoxSettings.useMysql) {
            this.database = new MySQLDatabase(this);
        } else {
            this.database = new FileDatabase(this);
        }
    }

    @Override
    public void loadSettings() {
        gameSettings.setGameType(GameSettings.GameType.SINGLE_PLAYER);
        gameSettings.setGameGuiSize(54);
        gameSettings.setHandleClicksOnHotbar(false);
    }

    @Override
    public void loadLanguage() {
        this.gameLang = new CCLanguage(this);
    }

    @Override
    public void loadGameManager() {
        this.gameManager = new CCGameManager(this);
    }

    public Database getDatabase() {
        return this.database;
    }
}
