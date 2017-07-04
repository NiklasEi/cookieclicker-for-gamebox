package me.nikl.cookieclicker;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by Niklas
 *
 * Game
 */
public class Game {

    private Language lang;

    private Main plugin;

    private boolean playSounds;

    private GameRules rule;

    private Player player;

    private Inventory inventory;

    private double cookies;

    private int time;


    public Game(GameRules rule, Main plugin, Player player, boolean playSounds){
        this.plugin = plugin;
        this.lang = plugin.lang;
        this.rule = rule;
        this.player = player;

        cookies = 0;
        time = 0;

        // only play sounds if the game setting allows to
        this.playSounds = plugin.getPlaySounds() && playSounds;

        // create inventory
        this.inventory = Bukkit.createInventory(null, 54, lang.GAME_TITLE_START.replace("%score%", String.valueOf((int) cookies)).replace("%time%", String.valueOf(time)));

        buildInv();

        player.openInventory(inventory);

    }

    private void buildInv() {

    }



    public void onClick(InventoryClickEvent inventoryClickEvent) {


    }


    public void onGameEnd() {

    }
}
