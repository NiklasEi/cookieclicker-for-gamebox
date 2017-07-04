package me.nikl.cookieclicker;

import me.nikl.gamebox.nms.NMSUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Niklas
 *
 * Game
 */
public class Game extends BukkitRunnable{

    private Language lang;

    private NMSUtil nms;

    private Main plugin;

    private boolean playSounds;

    private GameRules rule;

    private Player player;

    private Inventory inventory;

    private double cookies;

    private double cookiesPerSecond = 0.;
    private long lastTimeStamp = System.currentTimeMillis();

    private int time;

    private ItemStack mainCookie = new MaterialData(Material.COOKIE).toItemStack();
    private int mainCookieSlot = 40;
    private ItemStack oven = new MaterialData(Material.FURNACE).toItemStack();
    private int ovenSlot = 0;


    public Game(GameRules rule, Main plugin, Player player, boolean playSounds){
        this.plugin = plugin;
        nms = plugin.getNms();
        this.lang = plugin.lang;
        this.rule = rule;
        this.player = player;

        cookies = 0;
        time = 0;

        // only play sounds if the game setting allows to
        this.playSounds = plugin.getPlaySounds() && playSounds;

        // create inventory
        this.inventory = Bukkit.createInventory(null, 54, lang.GAME_TITLE.replace("%score%", String.valueOf((int) cookies)));

        buildInv();

        player.openInventory(inventory);

        this.runTaskTimer(plugin, 0, 10);
    }

    private void buildInv() {
        ItemMeta meta;

        mainCookie.setAmount(1);
        meta = mainCookie.getItemMeta();
        meta.setDisplayName(lang.GAME_COOKIE_NAME);
        mainCookie.setItemMeta(meta);
        inventory.setItem(mainCookieSlot, mainCookie);

        oven.setAmount(1);
        meta = oven.getItemMeta();
        meta.setDisplayName(lang.GAME_OVEN_NAME);
        oven.setItemMeta(meta);
        updateOven();
    }

    private void updateOven() {
        ArrayList<String> lore = new ArrayList<>();
        for(String line : lang.GAME_OVEN_LORE){
            lore.add(line.replace("%cookies_per_second%", String.format("%.2f", cookiesPerSecond)));
        }
        ItemMeta meta = oven.getItemMeta();
        meta.setLore(lore);
        oven.setItemMeta(meta);
        inventory.setItem(ovenSlot, oven);
    }


    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if(inventoryClickEvent.getAction() != InventoryAction.PICKUP_ALL && inventoryClickEvent.getAction() != InventoryAction.PICKUP_HALF) return;

        cookies ++;

        cookiesPerSecond += 0.01;
    }


    public void onGameEnd() {

    }

    @Override
    public void run() {
        long newTimeStamp = System.currentTimeMillis();

        if(cookiesPerSecond > 0) {
            cookies += ((newTimeStamp - lastTimeStamp) / 1000.) * cookiesPerSecond;
        }

        lastTimeStamp = newTimeStamp;

        nms.updateInventoryTitle(player, lang.GAME_TITLE.replace("%score%", String.valueOf((int) cookies)));
        updateOven();
    }
}
