package me.nikl.cookieclicker.data;

import me.nikl.cookieclicker.CookieClicker;
import me.nikl.gamebox.GameBox;
import me.nikl.gamebox.data.database.DataBase;

import java.util.UUID;

/**
 * @author Niklas Eicker
 */
public abstract class Database {
    protected CookieClicker cookieClicker;
    protected GameBox gameBox;

    public Database(CookieClicker cookieClicker) {
        this.cookieClicker = cookieClicker;
        this.gameBox = cookieClicker.getGameBox();
    }

    public abstract void getGameSave(UUID uuid, String gameType, DataBase.Callback<GameSave> callback);

    public abstract void saveGame(GameSave gameSave, boolean async);

    public abstract void onShutDown();

    public abstract void deleteSaves(String gameType);

    public abstract void deleteSaves(UUID player);

    public abstract void deleteSave(String gameType, UUID player);
}
