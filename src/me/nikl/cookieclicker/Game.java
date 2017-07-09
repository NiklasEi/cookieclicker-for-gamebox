package me.nikl.cookieclicker;

import me.nikl.cookieclicker.productions.Curser;
import me.nikl.cookieclicker.productions.Farm;
import me.nikl.cookieclicker.productions.Grandma;
import me.nikl.cookieclicker.productions.Production;
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

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

    private HashMap<Integer, Production> productions = new HashMap<>();

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

        cookies = 0.;
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
        productions.put(6, new Curser(plugin, 6, "Curser"));
        productions.put(7, new Grandma(plugin, 7, "Grandma"));
        productions.put(8, new Farm(plugin, 8, "Farm"));


        visualize();



        mainCookie.setAmount(1);
        ItemMeta meta = mainCookie.getItemMeta();
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
            lore.add(line.replace("%cookies_per_second%", Utility.convertHugeNumber(cookiesPerSecond)));
        }
        ItemMeta meta = oven.getItemMeta();
        meta.setLore(lore);
        oven.setItemMeta(meta);
        inventory.setItem(ovenSlot, oven);
    }


    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if(inventoryClickEvent.getAction() != InventoryAction.PICKUP_ALL && inventoryClickEvent.getAction() != InventoryAction.PICKUP_HALF) return;
        if(inventoryClickEvent.getCurrentItem() == null) return;

        if(inventoryClickEvent.getRawSlot() == mainCookieSlot) {
            cookies += 1000000;
        } else if(productions.keySet().contains(inventoryClickEvent.getRawSlot())){
            Production production = productions.get(inventoryClickEvent.getRawSlot());
            int cost = production.getCost();

            switch (inventoryClickEvent.getAction()){
                case PICKUP_ALL:
                    if(cookies < cost){
                        return;
                    }
                    cookies -= cost;
                    production.addProductions(1);
                    production.visualize(inventory);
                    break;

                case PICKUP_HALF:
                    if(production.getCount() == 0) return;

                    production.addProductions(-1);
                    cookies += 0.45 * cost;
                    production.visualize(inventory);
                    break;
            }
            calcCookiesPerSecond();
        }
    }

    private void calcCookiesPerSecond() {
        cookiesPerSecond = 0.;
        for(Production production : productions.values()){
            cookiesPerSecond += production.getAllInAllProductionPerSecond();
        }
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

        nms.updateInventoryTitle(player, lang.GAME_TITLE.replace("%score%", Utility.convertHugeNumber((int) cookies)));
        updateOven();
    }

    public void visualize(){
        for(Production production : productions.values()) {
            production.visualize(inventory);
        }
    }

}
