package me.nikl.cookieclicker;

import me.nikl.gamebox.GameBox;
import me.nikl.gamebox.game.Game;
import me.nikl.gamebox.game.GameSettings;
import me.nikl.cookieclicker.CCGameManager;
import me.nikl.cookieclicker.CCLanguage;

/**
 * Created by Niklas
 *
 * Main class of the GameBox game Cookie Clicker
 */
public class CookieClicker extends Game {

    public CookieClicker(GameBox gameBox) {
        super(gameBox, GameBox.MODULE_COOKIECLICKER);
    }

    @Override
    public void onDisable() {
        ((CCGameManager) gameManager).onShutDown();
    }

    @Override
    public void init() {

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
}
