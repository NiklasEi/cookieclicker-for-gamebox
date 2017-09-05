package me.nikl.cookieclicker;

import me.nikl.cookieclicker.buildings.Bank;
import me.nikl.cookieclicker.buildings.Building;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.buildings.Cursor;
import me.nikl.cookieclicker.buildings.Factory;
import me.nikl.cookieclicker.buildings.Farm;
import me.nikl.cookieclicker.buildings.Grandma;
import me.nikl.cookieclicker.buildings.Mine;
import me.nikl.cookieclicker.buildings.Shipment;
import me.nikl.cookieclicker.buildings.Temple;
import me.nikl.cookieclicker.buildings.WizardTower;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.bank.AcidProofVaults;
import me.nikl.cookieclicker.upgrades.bank.ChocolateCoins;
import me.nikl.cookieclicker.upgrades.bank.ExponentialInterestRates;
import me.nikl.cookieclicker.upgrades.bank.FinancialZen;
import me.nikl.cookieclicker.upgrades.bank.ScissorResistantCreditCards;
import me.nikl.cookieclicker.upgrades.bank.TallerTellers;
import me.nikl.cookieclicker.upgrades.bank.TheStuffRationale;
import me.nikl.cookieclicker.upgrades.bank.WayOfTheWallet;
import me.nikl.cookieclicker.upgrades.clicking.AdamantiumMouse;
import me.nikl.cookieclicker.upgrades.clicking.EludiumMouse;
import me.nikl.cookieclicker.upgrades.clicking.FantasteelMouse;
import me.nikl.cookieclicker.upgrades.clicking.IronMouse;
import me.nikl.cookieclicker.upgrades.clicking.NevercrackMouse;
import me.nikl.cookieclicker.upgrades.clicking.PlasticMouse;
import me.nikl.cookieclicker.upgrades.clicking.TitaniumMouse;
import me.nikl.cookieclicker.upgrades.clicking.UnobtainiumMouse;
import me.nikl.cookieclicker.upgrades.clicking.WishalloyMouse;
import me.nikl.cookieclicker.upgrades.curser.Ambidextrous;
import me.nikl.cookieclicker.upgrades.curser.BillionFingers;
import me.nikl.cookieclicker.upgrades.curser.CarpalTunnelPreventionCream;
import me.nikl.cookieclicker.upgrades.curser.MillionFingers;
import me.nikl.cookieclicker.upgrades.curser.OctillionFingers;
import me.nikl.cookieclicker.upgrades.curser.QuadrillionFingers;
import me.nikl.cookieclicker.upgrades.curser.QuintillionFingers;
import me.nikl.cookieclicker.upgrades.curser.ReinforcedIndexFinger;
import me.nikl.cookieclicker.upgrades.curser.SeptillionFingers;
import me.nikl.cookieclicker.upgrades.curser.SextillionFingers;
import me.nikl.cookieclicker.upgrades.curser.ThousandFingers;
import me.nikl.cookieclicker.upgrades.curser.TrillionFingers;
import me.nikl.cookieclicker.upgrades.factory.ChildLabor;
import me.nikl.cookieclicker.upgrades.factory.CyborgWorkforce;
import me.nikl.cookieclicker.upgrades.factory.DeepBakeProcess;
import me.nikl.cookieclicker.upgrades.factory.HourDays;
import me.nikl.cookieclicker.upgrades.factory.RadiumReactors;
import me.nikl.cookieclicker.upgrades.factory.Recombobulators;
import me.nikl.cookieclicker.upgrades.factory.SturdierConveyorBelts;
import me.nikl.cookieclicker.upgrades.factory.Sweatshop;
import me.nikl.cookieclicker.upgrades.farm.CheapHoes;
import me.nikl.cookieclicker.upgrades.farm.CookieTrees;
import me.nikl.cookieclicker.upgrades.farm.Fertilizer;
import me.nikl.cookieclicker.upgrades.farm.FudgeFungus;
import me.nikl.cookieclicker.upgrades.farm.GeneticallyModifiedCookies;
import me.nikl.cookieclicker.upgrades.farm.GingerbreadScarecrows;
import me.nikl.cookieclicker.upgrades.farm.PulsarSprinklers;
import me.nikl.cookieclicker.upgrades.farm.WheatTriffids;
import me.nikl.cookieclicker.upgrades.grandma.AgingAgents;
import me.nikl.cookieclicker.upgrades.grandma.DoubleThickGlasses;
import me.nikl.cookieclicker.upgrades.grandma.ForwardsFromGrandma;
import me.nikl.cookieclicker.upgrades.grandma.LubricatedDentures;
import me.nikl.cookieclicker.upgrades.grandma.PruneJuice;
import me.nikl.cookieclicker.upgrades.grandma.SteelPlatedRollingPins;
import me.nikl.cookieclicker.upgrades.grandma.TheUnbridling;
import me.nikl.cookieclicker.upgrades.grandma.XtremeWalkers;
import me.nikl.cookieclicker.upgrades.mine.CanolaOilWells;
import me.nikl.cookieclicker.upgrades.mine.Coreforge;
import me.nikl.cookieclicker.upgrades.mine.HBombMining;
import me.nikl.cookieclicker.upgrades.mine.Megadrill;
import me.nikl.cookieclicker.upgrades.mine.Planetsplitters;
import me.nikl.cookieclicker.upgrades.mine.SugarGas;
import me.nikl.cookieclicker.upgrades.mine.Ultimadrill;
import me.nikl.cookieclicker.upgrades.mine.Ultradrill;
import me.nikl.gamebox.GameBoxSettings;
import me.nikl.gamebox.Sounds;
import me.nikl.gamebox.nms.NMSUtil;
import me.nikl.gamebox.util.NumberUtil;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by Niklas
 *
 * Game
 */
public class Game extends BukkitRunnable{

    private Random rand;

    private Language lang;

    private NMSUtil nms;

    private Main plugin;

    private boolean playSounds;

    private GameRules rule;

    private Player player;

    private Inventory inventory;

    private double cookies;

    private double cookiesPerClick = 0.;

    public double baseCookiesPerClick = 1.;
    public double cookiesPerClickPerCPS = 0.;

    private Map<Buildings, Double> clickBonuses = new HashMap<>();


    private Map<Buildings, Map<Buildings, Double>> buildingBonuses = new HashMap<>();

    // stats
    private double totalCookiesProduced = 0.;
    private double clickCookiesProduced = 0.;

    private double cookiesPerSecond = 0.;
    private long lastTimeStamp = System.currentTimeMillis();

    private HashMap<Buildings, Building> buildings = new HashMap<>();
    private HashMap<Integer, Buildings> buildingsPositions = new HashMap<>();

    private ItemStack mainCookie = new MaterialData(Material.COOKIE).toItemStack();
    private int mainCookieSlot = 31;
    private List<Integer> mainCookieSlots;
    private int moveCookieAfterClicks;
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

        rand = new Random();

        mainCookieSlots = new ArrayList<>();
        mainCookieSlots.add(30);
        mainCookieSlots.add(31);
        mainCookieSlots.add(32);

        moveCookieAfterClicks = rule.getMoveCookieAfterClicks();

        cookies = 0.;


        Set<Upgrade> futureUpgradesTemp = new HashSet<>();

        // clicking
        futureUpgradesTemp.add(new PlasticMouse(this));
        futureUpgradesTemp.add(new IronMouse(this));
        futureUpgradesTemp.add(new TitaniumMouse(this));
        futureUpgradesTemp.add(new AdamantiumMouse(this));
        futureUpgradesTemp.add(new UnobtainiumMouse(this));
        futureUpgradesTemp.add(new EludiumMouse(this));
        futureUpgradesTemp.add(new WishalloyMouse(this));
        futureUpgradesTemp.add(new FantasteelMouse(this));
        futureUpgradesTemp.add(new NevercrackMouse(this));

        // Cursor
        futureUpgradesTemp.add(new CarpalTunnelPreventionCream(this));
        futureUpgradesTemp.add(new ReinforcedIndexFinger(this));
        futureUpgradesTemp.add(new Ambidextrous(this));
        futureUpgradesTemp.add(new ThousandFingers(this));
        futureUpgradesTemp.add(new MillionFingers(this));
        futureUpgradesTemp.add(new BillionFingers(this));
        futureUpgradesTemp.add(new TrillionFingers(this));
        futureUpgradesTemp.add(new QuadrillionFingers(this));
        futureUpgradesTemp.add(new QuintillionFingers(this));
        futureUpgradesTemp.add(new SextillionFingers(this));
        futureUpgradesTemp.add(new SeptillionFingers(this));
        futureUpgradesTemp.add(new OctillionFingers(this));

        // GRANDMA
        futureUpgradesTemp.add(new ForwardsFromGrandma(this));
        futureUpgradesTemp.add(new SteelPlatedRollingPins(this));
        futureUpgradesTemp.add(new LubricatedDentures(this));
        futureUpgradesTemp.add(new PruneJuice(this));
        futureUpgradesTemp.add(new DoubleThickGlasses(this));
        futureUpgradesTemp.add(new AgingAgents(this));
        futureUpgradesTemp.add(new XtremeWalkers(this));
        futureUpgradesTemp.add(new TheUnbridling(this));

        // Farm
        futureUpgradesTemp.add(new CheapHoes(this));
        futureUpgradesTemp.add(new Fertilizer(this));
        futureUpgradesTemp.add(new CookieTrees(this));
        futureUpgradesTemp.add(new GeneticallyModifiedCookies(this));
        futureUpgradesTemp.add(new GingerbreadScarecrows(this));
        futureUpgradesTemp.add(new PulsarSprinklers(this));
        futureUpgradesTemp.add(new FudgeFungus(this));
        futureUpgradesTemp.add(new WheatTriffids(this));

        // Mine
        futureUpgradesTemp.add(new SugarGas(this));
        futureUpgradesTemp.add(new Megadrill(this));
        futureUpgradesTemp.add(new Ultradrill(this));
        futureUpgradesTemp.add(new Ultimadrill(this));
        futureUpgradesTemp.add(new HBombMining(this));
        futureUpgradesTemp.add(new Coreforge(this));
        futureUpgradesTemp.add(new Planetsplitters(this));
        futureUpgradesTemp.add(new CanolaOilWells(this));

        // Factory
        futureUpgradesTemp.add(new SturdierConveyorBelts(this));
        futureUpgradesTemp.add(new ChildLabor(this));
        futureUpgradesTemp.add(new Sweatshop(this));
        futureUpgradesTemp.add(new RadiumReactors(this));
        futureUpgradesTemp.add(new Recombobulators(this));
        futureUpgradesTemp.add(new DeepBakeProcess(this));
        futureUpgradesTemp.add(new CyborgWorkforce(this));
        futureUpgradesTemp.add(new HourDays(this));

        // Bank
        futureUpgradesTemp.add(new TallerTellers(this));
        futureUpgradesTemp.add(new ScissorResistantCreditCards(this));
        futureUpgradesTemp.add(new AcidProofVaults(this));
        futureUpgradesTemp.add(new ChocolateCoins(this));
        futureUpgradesTemp.add(new ExponentialInterestRates(this));
        futureUpgradesTemp.add(new FinancialZen(this));
        futureUpgradesTemp.add(new WayOfTheWallet(this));
        futureUpgradesTemp.add(new TheStuffRationale(this));


        // sort upgrades in map with ids as key (fast lookup for loading of old game)
        Upgrade upgrade;
        Iterator<Upgrade> iterator = futureUpgradesTemp.iterator();
        while (iterator.hasNext()){
            upgrade = iterator.next();
            futureUpgrades.put(upgrade.getId(), upgrade);
        }

        // only play sounds if the game setting allows to
        this.playSounds = plugin.getPlaySounds() && playSounds;

        // create inventory
        String title = lang.GAME_TITLE.replace("%score%", String.valueOf((int) cookies));
        if(GameBoxSettings.checkInventoryLength && title.length() > 32){
            title = "Title is too long!";
        }
        this.inventory = Bukkit.createInventory(null, 54, title);

        buildings.put(Buildings.CURSOR, new Cursor(plugin, 2, Buildings.CURSOR));
        buildingsPositions.put(2, Buildings.CURSOR);
        buildings.put(Buildings.GRANDMA, new Grandma(plugin, 3, Buildings.GRANDMA));
        buildingsPositions.put(3, Buildings.GRANDMA);
        buildings.put(Buildings.FARM, new Farm(plugin, 4, Buildings.FARM));
        buildingsPositions.put(4, Buildings.FARM);
        buildings.put(Buildings.MINE, new Mine(plugin, 5, Buildings.MINE));
        buildingsPositions.put(5, Buildings.MINE);
        buildings.put(Buildings.FACTORY, new Factory(plugin, 6, Buildings.FACTORY));
        buildingsPositions.put(6, Buildings.FACTORY);
        buildings.put(Buildings.BANK, new Bank(plugin, 7, Buildings.BANK));
        buildingsPositions.put(7, Buildings.BANK);
        buildings.put(Buildings.TEMPLE, new Temple(plugin, 8, Buildings.TEMPLE));
        buildingsPositions.put(8, Buildings.TEMPLE);
        buildings.put(Buildings.WIZARD_TOWER, new WizardTower(plugin, 11, Buildings.WIZARD_TOWER));
        buildingsPositions.put(11, Buildings.WIZARD_TOWER);
        buildings.put(Buildings.SHIPMENT, new Shipment(plugin, 12, Buildings.SHIPMENT));
        buildingsPositions.put(12, Buildings.SHIPMENT);
        buildings.put(Buildings.ALCHEMY_LAB, new Shipment(plugin, 13, Buildings.ALCHEMY_LAB));
        buildingsPositions.put(13, Buildings.ALCHEMY_LAB);

        if(save != null){
            //load the game
            load(save);
        }

        buildInv();

        player.openInventory(inventory);

        this.runTaskTimer(plugin, 0, 10);
    }

    private void buildInv() {
        calcCookiesPerSecond();
        calcCookiesPerClick();
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
            lore.add(line.replace("%cookies_per_second%", NumberUtil.convertHugeNumber(cookiesPerSecond))
                    .replace("%cookies_per_click%", NumberUtil.convertHugeNumber(cookiesPerClick)));
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
            cookies += cookiesPerClick;
            clickCookiesProduced += cookiesPerClick ;
            totalCookiesProduced += cookiesPerClick;

            // move the cookie if configured
            if(moveCookieAfterClicks == 1){
                int oldSlot = mainCookieSlot;
                while (oldSlot == mainCookieSlot){
                    mainCookieSlot = mainCookieSlots.get(rand.nextInt(mainCookieSlots.size()));
                }
                inventory.setItem(oldSlot, null);
                inventory.setItem(mainCookieSlot, mainCookie);
                moveCookieAfterClicks = rule.getMoveCookieAfterClicks();
            } else if(moveCookieAfterClicks > 0){
                moveCookieAfterClicks --;
            }

            if(playSounds) player.playSound(player.getLocation(), clickCookie, volume * 0.5f, pitch);
        }

        // click on production
        else if(buildingsPositions.keySet().contains(inventoryClickEvent.getRawSlot())){
            Building building = buildings.get(buildingsPositions.get(inventoryClickEvent.getRawSlot()));
            double cost = building.getCost();

            switch (inventoryClickEvent.getAction()){
                case PICKUP_ALL:
                    if(cookies < cost){
                        if(playSounds) player.playSound(player.getLocation(), no, volume, pitch);
                        return;
                    }
                    cookies -= cost;
                    building.addProductions(1);
                    building.visualize(inventory);
                    if(playSounds) player.playSound(player.getLocation(), click, volume, pitch);
                    break;

                case PICKUP_HALF:
                    if(building.getCount() == 0) return;

                    building.addProductions(-1);
                    cookies += 0.45 * cost;
                    if(playSounds) player.playSound(player.getLocation(), clickCookie, volume, pitch);
                    building.visualize(inventory);
                    break;
            }
            calcCookiesPerSecond();
            calcCookiesPerClick();
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
            calcCookiesPerClick();
            updateOven();
        }
    }

    private void calcCookiesPerSecond() {
        cookiesPerSecond = 0.;
        for(Buildings buildings : buildings.keySet()){
            // check for bonuses from other buildings
            if(buildingBonuses.keySet().contains(buildings)) {

                double otherBuildingBonus = 0.;
                double bonus;
                for (Buildings otherBuilding : buildingBonuses.get(buildings).keySet()) {
                    bonus = this.buildings.get(otherBuilding).getCount() * buildingBonuses.get(buildings).get(otherBuilding);
                    otherBuildingBonus += bonus;
                }

                // update the building with the new bonus
                this.buildings.get(buildings).setOtherBuildingsBonus(otherBuildingBonus);
                this.buildings.get(buildings).visualize(inventory);
            }

            cookiesPerSecond += this.buildings.get(buildings).getAllInAllProductionPerSecond();
        }
    }


    private void calcCookiesPerClick() {
        cookiesPerClick = baseCookiesPerClick + cookiesPerClickPerCPS * cookiesPerSecond;

        for(Buildings buildings : clickBonuses.keySet()){
            cookiesPerClick += this.buildings.get(buildings).getCount() * clickBonuses.get(buildings);
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
        player.sendMessage(lang.PREFIX + lang.GAME_CLOSED.replace("%score%", NumberUtil.convertHugeNumber(Math.floor(totalCookiesProduced))));

        Map<String, Double> cookies = new HashMap<>();
        cookies.put("current", this.cookies);
        cookies.put("click", this.clickCookiesProduced);
        cookies.put("total", this.totalCookiesProduced);

        Map<String, Integer> productions = new HashMap<>();
        for(Buildings production : buildingsPositions.values()){
            productions.put(production.toString(), getBuilding(production).getCount());
        }

        List<Integer> upgrades = new ArrayList<>();
        for(Upgrade upgrade : activeUpgrades){
            upgrades.add(upgrade.getId());
        }

        plugin.getGameManager().saveGame(rule, player.getUniqueId(), cookies, productions, upgrades);
    }

    private void load(ConfigurationSection save) {
        if(save.isConfigurationSection("cookies")){
            ConfigurationSection cookieSection = save.getConfigurationSection("cookies");
            cookies = cookieSection.getDouble("current", 0.);
            clickCookiesProduced = cookieSection.getDouble("click", 0.);
            totalCookiesProduced = cookieSection.getDouble("total", 0.);
        }

        if(save.isConfigurationSection("productions")) {

            // building name of Cursor was corrected => allow for old save and convert
            Buildings building;
            for (String key : save.getConfigurationSection("productions").getKeys(false)) {
                try{
                    building = Buildings.valueOf(key);
                    buildings.get(building).addProductions(save.getInt("productions" + "." + key, 0));
                } catch (IllegalArgumentException exception){
                    // ignore
                }
            }

            if(save.isInt("productions.CURSER")){
                buildings.get(Buildings.CURSOR).addProductions(save.getInt("productions" + "." + "CURSER", 0));
                save.set("productions.CURSER", null);
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

    @Override
    public void run() {
        long newTimeStamp = System.currentTimeMillis();

        if(cookiesPerSecond > 0) {
            double newCookies = ((newTimeStamp - lastTimeStamp) / 1000.) * cookiesPerSecond;
            cookies += newCookies;
            totalCookiesProduced += newCookies;
        }

        lastTimeStamp = newTimeStamp;

        nms.updateInventoryTitle(player, lang.GAME_TITLE.replace("%score%", NumberUtil.convertHugeNumber(cookies)));
        checkUpgrades();
    }

    public void visualize(){
        for(Building building : buildings.values()) {
            building.visualize(inventory);
        }
    }

    /**
     * Add a bonus number of cookies per click and per specified building
     * @param production
     * @param bonusPerBuilding
     */
    public void addClickBonus(Buildings production, double bonusPerBuilding){
        if(clickBonuses.keySet().contains(production)){
            clickBonuses.put(production, (clickBonuses.get(production) + bonusPerBuilding));
            return;
        } else {
            clickBonuses.put(production, bonusPerBuilding);
            return;
        }
    }

    public void addBuildingBonus(Buildings buildingThatGetsTheBonus, Buildings buildingTheBonusComesFrom, double bonus){
        if(buildingBonuses.keySet().contains(buildingThatGetsTheBonus)){
            Map<Buildings, Double> bonusMap = buildingBonuses.get(buildingThatGetsTheBonus);
            if(bonusMap.keySet().contains(buildingTheBonusComesFrom)){
                bonusMap.put(buildingTheBonusComesFrom, bonusMap.get(buildingTheBonusComesFrom) + bonus);
                buildingBonuses.put(buildingThatGetsTheBonus, bonusMap);
                return;
            } else {
                bonusMap.put(buildingTheBonusComesFrom, bonus);
                return;
            }
        } else {
            Map<Buildings, Double> bonusMap = new HashMap<>();
            bonusMap.put(buildingTheBonusComesFrom, bonus);
            buildingBonuses.put(buildingThatGetsTheBonus, bonusMap);
            return;
        }
    }

    public double getTotalCookiesProduced(){
        return this.totalCookiesProduced;
    }

    public double getClickCookiesProduced(){
        return this.clickCookiesProduced;
    }

    public Building getBuilding(Buildings production) {
        return buildings.get(production);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public GameRules getRule(){
        return this.rule;
    }

    public Player getPlayer(){
        return this.player;
    }

    public Language getLang(){
        return this.lang;
    }
}
