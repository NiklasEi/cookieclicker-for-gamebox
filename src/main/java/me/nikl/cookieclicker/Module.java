package me.nikl.cookieclicker;

import me.nikl.gamebox.module.GameBoxModule;

public class Module extends GameBoxModule {
    @Override
    public void onEnable() {
        registerGame("cookieclicker", CookieClicker.class, "cc");
    }

    @Override
    public void onDisable() {

    }
}
