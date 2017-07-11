package me.nikl.cookieclicker;

import me.nikl.cookieclicker.productions.*;
import me.nikl.cookieclicker.updates.Curser.Ambidextrous;
import me.nikl.cookieclicker.updates.Curser.CarpalTunnelPreventionCream;
import me.nikl.cookieclicker.updates.Curser.ReinforcedIndexFinger;
import me.nikl.cookieclicker.updates.Upgrade;
import me.nikl.cookieclicker.updates.bank.ScissorResistantCreditCards;
import me.nikl.cookieclicker.updates.bank.TallerTellers;
import me.nikl.cookieclicker.updates.clicking.IronMouse;
import me.nikl.cookieclicker.updates.clicking.PlasticMouse;
import me.nikl.cookieclicker.updates.clicking.TitaniumMouse;
import me.nikl.cookieclicker.updates.factory.ChildLabor;
import me.nikl.cookieclicker.updates.factory.SturdierConveyorBelts;
import me.nikl.cookieclicker.updates.farm.CheapHoes;
import me.nikl.cookieclicker.updates.farm.Fertilizer;
import me.nikl.cookieclicker.updates.grandma.ForwardsFromGrandma;
import me.nikl.cookieclicker.updates.grandma.SteelPlatedRollingPins;
import me.nikl.cookieclicker.updates.mine.Megadrill;
import me.nikl.cookieclicker.updates.mine.SugarGas;
import me.nikl.gamebox.Sounds;
import me.nikl.gamebox.nms.NMSUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public double cookiesPerClick = 1.;
    public double cookiesPerClickPerCPS = 0.;

    // stats
    private double totalCookiesProduced = 0.;
    private double clickCookiesProduced = 0.;

    private double cookiesPerSecond = 0.;
    private long lastTimeStamp = System.currentTimeMillis();

    private HashMap<Productions, Production> productions = new HashMap<>();
    private HashMap<Integer, Productions> productionsPositions = new HashMap<>();

    private ItemStack mainCookie = new MaterialData(Material.COOKIE).toItemStack();
    private int mainCookieSlot = 31;
    private ItemStack oven = new MaterialData(Material.FURNACE).toItemStack();
    private int ovenSlot = 0;

    private Set<Upgrade> activeUpgrades = new HashSet<>();
    private Map<Integer, Upgrade> futureUpgrades = new HashMap<>();
    private Map<Integer, Upgrade> shownUpgrades = new HashMap<>();

    private Sound click = Sounds.CLICK.bukkitSound();
    private Sound clickCookie = Sounds.WOOD_CLICK.bukkitSound();
    private Sound upgrade = Sounds.LEVEL_UP.bukkitSound();
    private Sound no = Sounds.VILLAGER_NO.bukkitSound();
    private float volume = 0.5f;
    private float pitch = 10f;


    public Game(GameRules rule, Main plugin, Player player, boolean playSounds, ConfigurationSection save){
        this.plugin = plugin;
        nms = plugin.getNms();
        this.lang = plugin.lang;
        this.rule = rule;
        this.player = player;

        cookies = 0.;


        Set<Upgrade> futureUpgradesTemp = new HashSet<>();

        // clicking
        futureUpgradesTemp.add(new PlasticMouse(this));
        futureUpgradesTemp.add(new IronMouse(this));
        futureUpgradesTemp.add(new TitaniumMouse(this));

        // Curser
        futureUpgradesTemp.add(new CarpalTunnelPreventionCream(this));
        futureUpgradesTemp.add(new ReinforcedIndexFinger(this));
        futureUpgradesTemp.add(new Ambidextrous(this));

        // GRANDMA
        futureUpgradesTemp.add(new ForwardsFromGrandma(this));
        futureUpgradesTemp.add(new SteelPlatedRollingPins(this));

        // Farm
        futureUpgradesTemp.add(new CheapHoes(this));
        futureUpgradesTemp.add(new Fertilizer(this));

        // Mine
        futureUpgradesTemp.add(new SugarGas(this));
        futureUpgradesTemp.add(new Megadrill(this));

        // Factory
        futureUpgradesTemp.add(new SturdierConveyorBelts(this));
        futureUpgradesTemp.add(new ChildLabor(this));

        // Bank
        futureUpgradesTemp.add(new TallerTellers(this));
        futureUpgradesTemp.add(new ScissorResistantCreditCards(this));


        // sort updates in map with ids as key (fast lookup for loading of old game)
        Upgrade upgrade;
        Iterator<Upgrade> iterator = futureUpgradesTemp.iterator();
        while (iterator.hasNext()){
            upgrade = iterator.next();
            futureUpgrades.put(upgrade.getId(), upgrade);
        }

        // only play sounds if the game setting allows to
        this.playSounds = plugin.getPlaySounds() && playSounds;

        // create inventory
        this.inventory = Bukkit.createInventory(null, 54, lang.GAME_TITLE.replace("%score%", String.valueOf((int) cookies)));

        productions.put(Productions.CURSER, new Curser(plugin, 3, "Curser"));
        productionsPositions.put(3, Productions.CURSER);
        productions.put(Productions.GRANDMA, new Grandma(plugin, 4, "Grandma"));
        productionsPositions.put(4, Productions.GRANDMA);
        productions.put(Productions.FARM, new Farm(plugin, 5, "Farm"));
        productionsPositions.put(5, Productions.FARM);
        productions.put(Productions.MINE, new Mine(plugin, 6, "Mine"));
        productionsPositions.put(6, Productions.MINE);
        productions.put(Productions.FACTORY, new Factory(plugin, 7, "Factory"));
        productionsPositions.put(7, Productions.FACTORY);
        productions.put(Productions.BANK, new Bank(plugin, 8, "Bank"));
        productionsPositions.put(8, Productions.BANK);

        if(save != null){
            //load the game
            load(save);
        }

        buildInv();

        player.openInventory(inventory);

        this.runTaskTimer(plugin, 0, 10);
    }

    private void load(ConfigurationSection save) {
        if(save.isConfigurationSection("cookies")){
            ConfigurationSection cookieSection = save.getConfigurationSection("cookies");
            cookies = cookieSection.getDouble("total", 0.);
            clickCookiesProduced = cookieSection.getDouble("click", 0.);
        }

        if(save.isConfigurationSection("productions")) {
            for (String key : save.getConfigurationSection("productions").getKeys(false)) {
                productions.get(Productions.valueOf(key)).addProductions(save.getInt("productions" + "." + key, 0));
            }
        }

        List<Integer> upgrades = save.getIntegerList("upgrades");

        if(upgrades != null && !upgrades.isEmpty()){
            for(int id : upgrades){
                Upgrade upgrade = futureUpgrades.get(id);
                if(upgrade == null) continue;
                upgrade.onActivation();
                activeUpgrades.add(upgrade);
                futureUpgrades.remove(id);
            }
        }
    }

    private void buildInv() {
        visualize();
        calcCookiesPerSecond();


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
            lore.add(line.replace("%cookies_per_second%", Utility.convertHugeNumber(cookiesPerSecond))
                    .replace("%cookies_per_click%", Utility.convertHugeNumber(cookiesPerClick + cookiesPerClickPerCPS * cookiesPerSecond)));
        }
        ItemMeta meta = oven.getItemMeta();
        meta.setLore(lore);
        oven.setItemMeta(meta);
        inventory.setItem(ovenSlot, oven);
    }


    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if(inventoryClickEvent.getAction() != InventoryAction.PICKUP_ALL && inventoryClickEvent.getAction() != InventoryAction.PICKUP_HALF) return;
        if(inventoryClickEvent.getCurrentItem() == null) return;

        // Click on cookie
        if(inventoryClickEvent.getRawSlot() == mainCookieSlot) {
            cookies += cookiesPerClick + cookiesPerClickPerCPS * cookiesPerSecond;
            clickCookiesProduced += cookiesPerClick + cookiesPerClickPerCPS * cookiesPerSecond;
            totalCookiesProduced += cookiesPerClick + cookiesPerClickPerCPS * cookiesPerSecond;

            if(playSounds) player.playSound(player.getLocation(), clickCookie, volume * 0.5f, pitch);
        }

        // click on production
        else if(productionsPositions.keySet().contains(inventoryClickEvent.getRawSlot())){
            Production production = productions.get(productionsPositions.get(inventoryClickEvent.getRawSlot()));
            double cost = production.getCost();

            switch (inventoryClickEvent.getAction()){
                case PICKUP_ALL:
                    if(cookies < cost){
                        if(playSounds) player.playSound(player.getLocation(), no, volume, pitch);
                        return;
                    }
                    cookies -= cost;
                    production.addProductions(1);
                    production.visualize(inventory);
                    if(playSounds) player.playSound(player.getLocation(), click, volume, pitch);
                    break;

                case PICKUP_HALF:
                    if(production.getCount() == 0) return;

                    production.addProductions(-1);
                    cookies += 0.45 * cost;
                    if(playSounds) player.playSound(player.getLocation(), clickCookie, volume, pitch);
                    production.visualize(inventory);
                    break;
            }
            calcCookiesPerSecond();
            updateOven();
        }

        // click on upgrade
        else if(shownUpgrades.keySet().contains(53 - inventoryClickEvent.getRawSlot())){
            Upgrade upgrade = shownUpgrades.get(53 - inventoryClickEvent.getRawSlot());
            if(cookies < upgrade.getCost()) {
                if(playSounds) player.playSound(player.getLocation(), no, volume, pitch);
                return;
            }

            cookies -= upgrade.getCost();
            upgrade.onActivation();
            if(playSounds) player.playSound(player.getLocation(), this.upgrade, volume, pitch);

            activeUpgrades.add(upgrade);
            shownUpgrades.remove(53 - inventoryClickEvent.getRawSlot());

            visualizeUpgrades();
            calcCookiesPerSecond();
            updateOven();
        }
    }

    private void calcCookiesPerSecond() {
        cookiesPerSecond = 0.;
        for(Production production : productions.values()){
            cookiesPerSecond += production.getAllInAllProductionPerSecond();
        }
    }

    private void checkUpgrades(){
        boolean added = false;
        Set<Upgrade> toAdd =  new HashSet<>();
        Iterator<Upgrade> iterator = futureUpgrades.values().iterator();
        while (iterator.hasNext()){
            Upgrade upgrade = iterator.next();
            if(!upgrade.isUnlocked()) continue;

            added = true;
            toAdd.add(upgrade);
            iterator.remove();
        }

        if(added) visualizeUpgrades(toAdd);
    }

    private void visualizeUpgrades(Set<Upgrade> toAdd) {
        Iterator<Upgrade> iterator = toAdd.iterator();
        int slot = 8;
        while (iterator.hasNext()){
            if(shownUpgrades.keySet().contains(slot)){
                slot --;
                continue;
            }

            Upgrade upgrade = iterator.next();

            shownUpgrades.put(slot, upgrade);
            slot--;
            iterator.remove();
        }
        visualizeUpgrades();
    }


    private void visualizeUpgrades() {
        Map<Integer, Upgrade> orderedUpgrades = new HashMap<>();

        if(shownUpgrades.isEmpty()){
            inventory.setItem(53 - 8, null);
            return;
        }
        int currentSlot = 8;


        double lowestCost;
        int cheapestUpgrade;
        while (!shownUpgrades.isEmpty()) {
            lowestCost = Double.MAX_VALUE;
            cheapestUpgrade = 0;
            for (int slot : shownUpgrades.keySet()) {
                if (shownUpgrades.get(slot).getCost() < lowestCost) {
                    lowestCost = shownUpgrades.get(slot).getCost();
                    cheapestUpgrade = slot;
                }
            }
            orderedUpgrades.put(currentSlot, shownUpgrades.get(cheapestUpgrade));
            shownUpgrades.remove(cheapestUpgrade);
            currentSlot--;
        }

        shownUpgrades = orderedUpgrades;

        for(int i = 8; i >= 0 ; i --){
            if(shownUpgrades.get(i) == null){
                inventory.setItem(53 - i, null);
                continue;
            }

            inventory.setItem(53 - i, shownUpgrades.get(i).getIcon());
        }
    }


    public void onGameEnd() {
        Map<String, Double> cookies = new HashMap<>();
        cookies.put("total", this.cookies);
        cookies.put("click", this.clickCookiesProduced);

        Map<String, Integer> productions = new HashMap<>();
        for(Productions production : productionsPositions.values()){
            productions.put(production.toString(), getProduction(production).getCount());
        }

        List<Integer> upgrades = new ArrayList<>();
        for(Upgrade upgrade : activeUpgrades){
            upgrades.add(upgrade.getId());
        }

        plugin.getGameManager().saveGame(rule, player.getUniqueId(), cookies, productions, upgrades);
    }

    @Override
    public void run() {
        long newTimeStamp = System.currentTimeMillis();

        if(cookiesPerSecond > 0) {
            double newCookies = ((newTimeStamp - lastTimeStamp) / 1000.) * cookiesPerSecond;
            cookies += newCookies;
            totalCookiesProduced += newCookies;
        }

        lastTimeStamp = newTimeStamp;

        nms.updateInventoryTitle(player, lang.GAME_TITLE.replace("%score%", Utility.convertHugeNumber(BigInteger.valueOf((long) cookies))));
        checkUpgrades();
    }

    public void visualize(){
        for(Production production : productions.values()) {
            production.visualize(inventory);
        }
    }

    public double getTotalCookiesProduced(){
        return this.totalCookiesProduced;
    }

    public double getClickCookiesProduced(){
        return this.clickCookiesProduced;
    }

    public Production getProduction(Productions production) {
        return productions.get(production);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
