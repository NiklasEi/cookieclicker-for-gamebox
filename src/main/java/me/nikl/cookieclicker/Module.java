package me.nikl.cookieclicker;

import me.nikl.gamebox.module.GameBoxModule;

public class Module extends GameBoxModule {
    public final static String MODULE_COOKIECLICKER = "cookieclicker";

    @Override
    public void onEnable() {
        registerGame(MODULE_COOKIECLICKER, CookieClicker.class, "cc");
    }

    @Override
    public void onDisable() {

    }
}
