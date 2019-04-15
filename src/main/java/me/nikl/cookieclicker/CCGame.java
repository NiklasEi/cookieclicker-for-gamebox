package me.nikl.cookieclicker;

import me.nikl.cookieclicker.buildings.Building;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.data.GameSave;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.gamebox.data.database.DataBase;
import me.nikl.gamebox.nms.NmsFactory;
import me.nikl.gamebox.nms.NmsUtility;
import me.nikl.gamebox.utility.NumberUtility;
import me.nikl.gamebox.utility.Sound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Niklas
 *
 * Game
 */
public class CCGame extends BukkitRunnable {
    private final UUID gameUuid = UUID.randomUUID();
    private CCGameManager manager;
    public double baseCookiesPerClick = 1.;
    public double cookiesPerClickPerCPS = 0.;
    private Random rand;
    private CCLanguage lang;
    private NmsUtility nms;
    private CookieClicker cookieClicker;
    private boolean playSounds = false;
    private CCGameRules rule;
    private Player player;
    private Inventory inventory;
    private double cookies = 0;
    private double cookiesPerClick = 0.;
    private Map<Buildings, Double> clickBonuses = new HashMap<>();
    private Map<Buildings, Map<Buildings, Double>> buildingBonuses = new HashMap<>();
    // stats
    private double totalCookiesProduced = 0.;
    private double clickCookiesProduced = 0.;
    private double cookiesPerSecond = 0.;

    private double currentGlobalClickingBoost = 1.;
    private double currentGlobalProductionBoost = 1.;

    private long lastAction = System.currentTimeMillis();
    private boolean idle = false;
    private long lastTimeStamp = System.currentTimeMillis();
    private ItemStack mainCookie = new MaterialData(Material.COOKIE).toItemStack();
    private ItemStack luckyCookieItem = new MaterialData(Material.COOKIE).toItemStack();
    private int mainCookieSlot = 31;
    private int luckyCookie = -1;
    private int luckyCookieSpawnTime = 300000;
    private int luckyCookieDespawnTime = 13000;
    private long lastluckyCookieTimeStamp = System.currentTimeMillis();
    private List<Integer> mainCookieSlots;
    private int moveCookieAfterClicks;
    private ItemStack oven;
    private int ovenSlot = 0;
    private Set<Integer> activeUpgrades = new HashSet<>();
    private Set<Integer> futureUpgrades = new HashSet<>();
    private Set<Upgrade> upgradesWaitingList = new HashSet<>();
    private Map<Integer, Integer> shownUpgradesSlotToID = new HashMap<>();

    private org.bukkit.Sound click = Sound.CLICK.bukkitSound();
    private org.bukkit.Sound clickCookie = Sound.WOOD_CLICK.bukkitSound();
    private org.bukkit.Sound upgrade = Sound.LEVEL_UP.bukkitSound();
    private org.bukkit.Sound no = Sound.VILLAGER_NO.bukkitSound();
    private float volume = 0.5f;
    private float pitch = 10f;

    private boolean loaded = false;

    public CCGame(CCGameRules rule, CCGameManager manager, Player player, boolean playSounds) {
        this.cookieClicker = manager.getCookieClicker();
        this.manager = manager;
        cookieClicker.prepareGame(gameUuid);
        nms = NmsFactory.getNmsUtility();
        this.lang = (CCLanguage) cookieClicker.getGameLang();
        this.rule = rule;
        this.player = player;
        rand = new Random();
        mainCookieSlots = new ArrayList<>();
        mainCookieSlots.add(30);
        mainCookieSlots.add(31);
        mainCookieSlots.add(32);
        moveCookieAfterClicks = rule.getMoveCookieAfterClicks();
        cookies = 0.;
        futureUpgrades = cookieClicker.getUpgradeIDs();

        cookieClicker.getDatabase().getGameSave(player.getUniqueId(), rule.getKey(), new DataBase.Callback<GameSave>() {
            @Override
            public void onSuccess(GameSave gameSave) {
                load(gameSave);
            }

            @Override
            public void onFailure(Throwable throwable, GameSave gameSave) {
                if (throwable != null) throwable.printStackTrace();
                load(gameSave);
            }
        });

        // only play sounds if the game setting allows to
        this.playSounds = cookieClicker.getSettings().isPlaySounds() && playSounds;

        // create inventory
        String title = lang.GAME_TITLE
                .replace("%score%", String.valueOf((int) cookies));

        this.inventory = cookieClicker.createInventory(54, title);

        updateGlobalBoosts();
        buildInv();

        player.openInventory(inventory);

        this.runTaskTimer(cookieClicker.getGameBox(), 0, 10);
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

        luckyCookieItem.setAmount(1);
        meta = luckyCookieItem.getItemMeta();
        meta.setDisplayName(lang.GAME_LUCKY_COOKIE_NAME);
        luckyCookieItem.setItemMeta(meta);
        luckyCookieItem = nms.addGlow(luckyCookieItem);

        oven = new MaterialData(Material.FURNACE).toItemStack(1);
        meta = oven.getItemMeta();
        meta.setDisplayName(lang.GAME_OVEN_NAME);
        oven.setItemMeta(meta);
        updateOven();
    }

    private void updateOven() {
        ArrayList<String> lore = new ArrayList<>();
        for (String line : lang.GAME_OVEN_LORE) {
            lore.add(line.replace("%cookies_per_second%", NumberUtility.convertHugeNumber(cookiesPerSecond * currentGlobalProductionBoost))
                    .replace("%cookies_per_click%", NumberUtility.convertHugeNumber(cookiesPerClick * currentGlobalClickingBoost))
                    .replace("%cookies_per_second_long%", NumberUtility.convertHugeNumber(cookiesPerSecond * currentGlobalProductionBoost, false))
                    .replace("%cookies_per_click_long%", NumberUtility.convertHugeNumber(cookiesPerClick * currentGlobalClickingBoost, false)));
        }
        if (currentGlobalProductionBoost > 1 || currentGlobalClickingBoost > 1) {
            lore.add("");
            lore.add(lang.GAME_OVEN_LORE_BOOST_TITLE);
            if (currentGlobalClickingBoost > 1) {
                lore.add(lang.GAME_OVEN_LORE_BOOST_CLICKING.replace("%boost%", String.valueOf(currentGlobalClickingBoost)));
            }
            if (currentGlobalProductionBoost > 1) {
                lore.add(lang.GAME_OVEN_LORE_BOOST_PRODUCTION.replace("%boost%", String.valueOf(currentGlobalProductionBoost)));
            }
        }

        ItemMeta meta = oven.getItemMeta();
        meta.setLore(lore);
        oven.setItemMeta(meta);
        inventory.setItem(ovenSlot, oven);
    }


    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if (!loaded || inventoryClickEvent.getAction() != InventoryAction.PICKUP_ALL && inventoryClickEvent.getAction() != InventoryAction.PICKUP_HALF)
            return;
        if (inventoryClickEvent.getCurrentItem() == null) return;
        lastAction = System.currentTimeMillis();
        if (idle) {
            idle = false;
            lastTimeStamp = System.currentTimeMillis();
        }

        // Click on cookie
        if (inventoryClickEvent.getRawSlot() == mainCookieSlot) {
            double cookiesToGet = cookiesPerClick * currentGlobalClickingBoost;
            cookies += cookiesToGet;
            clickCookiesProduced += cookiesToGet;
            totalCookiesProduced += cookiesToGet;

            // move the cookie if configured
            if (moveCookieAfterClicks == 1) {
                int oldSlot = mainCookieSlot;
                while (oldSlot == mainCookieSlot) {
                    mainCookieSlot = mainCookieSlots.get(rand.nextInt(mainCookieSlots.size()));
                }
                inventory.setItem(oldSlot, null);
                inventory.setItem(mainCookieSlot, mainCookie);
                moveCookieAfterClicks = rule.getMoveCookieAfterClicks();
            } else if (moveCookieAfterClicks > 0) {
                moveCookieAfterClicks--;
            }

            if (playSounds) player.playSound(player.getLocation(), clickCookie, volume * 0.5f, pitch);
        }

        // click on production
        else if (cookieClicker.isBuildingSlot(inventoryClickEvent.getRawSlot())) {
            Building building = cookieClicker.getBuilding(inventoryClickEvent.getRawSlot());
            double cost = building.getCost(gameUuid);

            switch (inventoryClickEvent.getAction()) {
                case PICKUP_ALL:
                    if (cookies < cost) {
                        if (playSounds) player.playSound(player.getLocation(), no, volume, pitch);
                        return;
                    }
                    cookies -= cost;
                    building.addProductions(gameUuid, 1);
                    building.visualize(this);
                    if (playSounds) player.playSound(player.getLocation(), click, volume, pitch);
                    break;

                case PICKUP_HALF:
                    if (building.getCount(gameUuid) == 0) return;

                    building.addProductions(gameUuid, -1);
                    cookies += 0.45 * cost;
                    if (playSounds) player.playSound(player.getLocation(), clickCookie, volume, pitch);
                    building.visualize(this);
                    break;
            }
            calcCookiesPerSecond();
            calcCookiesPerClick();
            updateOven();
        }

        // click on upgrade
        else if (shownUpgradesSlotToID.keySet().contains(53 - inventoryClickEvent.getRawSlot())) {
            Upgrade upgrade = cookieClicker.getUpgrade(shownUpgradesSlotToID.get(53 - inventoryClickEvent.getRawSlot()));
            if (cookies < upgrade.getCost()) {
                if (playSounds) player.playSound(player.getLocation(), no, volume, pitch);
                return;
            }

            cookies -= upgrade.getCost();
            upgrade.onActivation(this);
            if (playSounds) player.playSound(player.getLocation(), this.upgrade, volume, pitch);

            activeUpgrades.add(upgrade.getId());
            shownUpgradesSlotToID.remove(53 - inventoryClickEvent.getRawSlot());
            upgradesWaitingList.remove(upgrade);
            visualizeUpgrades();
            calcCookiesPerSecond();
            calcCookiesPerClick();
            updateOven();
        }

        else if (inventoryClickEvent.getRawSlot() == luckyCookie) {
            inventory.setItem(luckyCookie, null);
            double wonCookies = Math.min(cookies * 0.15, cookiesPerSecond * currentGlobalProductionBoost * 900);
            cookies += wonCookies;
            luckyCookie = -1;
        }
    }

    private void calcCookiesPerSecond() {
        cookiesPerSecond = 0.;
        for (Buildings buildingType : Buildings.values()) {
            // check for bonuses from other buildings
            if (buildingBonuses.keySet().contains(buildingType)) {

                double otherBuildingBonus = 0.;
                double bonus;
                for (Buildings otherBuilding : buildingBonuses.get(buildingType).keySet()) {
                    bonus = cookieClicker.getBuilding(otherBuilding).getCount(gameUuid) * buildingBonuses.get(buildingType).get(otherBuilding);
                    otherBuildingBonus += bonus;
                }

                // update the building with the new bonus
                cookieClicker.getBuilding(buildingType).setOtherBuildingsBonus(gameUuid, otherBuildingBonus);
                cookieClicker.getBuilding(buildingType).visualize(this);
            }

            cookiesPerSecond += cookieClicker.getBuilding(buildingType).getAllInAllProductionPerSecond(gameUuid);
        }
    }


    private void calcCookiesPerClick() {
        cookiesPerClick = baseCookiesPerClick + cookiesPerClickPerCPS * cookiesPerSecond;

        for (Buildings buildingType : clickBonuses.keySet()) {
            cookiesPerClick += cookieClicker.getBuilding(buildingType).getCount(gameUuid) * clickBonuses.get(buildingType);
        }
    }

    private void checkUpgrades() {
        boolean added = false;
        for (int upgradeID : futureUpgrades) {
            Upgrade upgrade = cookieClicker.getUpgrade(upgradeID);
            if (!upgrade.isUnlocked(this)) continue;

            added = true;
            upgradesWaitingList.add(upgrade);
        }
        if (added) {
            for (Upgrade upgrade : upgradesWaitingList) {
                futureUpgrades.remove(upgrade.getId());
            }
            visualizeUpgrades();
        }
    }

    private Upgrade getCheapestUpgrade(Set<Upgrade> toAdd) {
        double lowestCost = Double.MAX_VALUE;
        Upgrade cheapestUpgrade = null;
        for (Upgrade upgrade : toAdd) {
            if (upgrade.getCost() < lowestCost) {
                lowestCost = upgrade.getCost();
                cheapestUpgrade = upgrade;
            }
        }
        return cheapestUpgrade;
    }

    private void visualizeUpgrades() {
        List<Upgrade> orderedUpgrades = new ArrayList<>();
        cookieClicker.debug("waiting size: " + upgradesWaitingList.size());
        Set<Upgrade> waitingUpgradesTemp = new HashSet<>(upgradesWaitingList);
        while (!waitingUpgradesTemp.isEmpty() && orderedUpgrades.size() < 9) {
            Upgrade upgrade = getCheapestUpgrade(waitingUpgradesTemp);
            orderedUpgrades.add(upgrade);
            waitingUpgradesTemp.remove(upgrade);
        }
        cookieClicker.debug("ordered size: " + orderedUpgrades.size());
        for (int i = 0; i < 9; i++) {
            if (orderedUpgrades.size() <= i) {
                inventory.setItem(45 + i, null);
                continue;
            }
            shownUpgradesSlotToID.put(8 - i, orderedUpgrades.get(i).getId());
            inventory.setItem(45 + i, orderedUpgrades.get(i).getIcon());
        }
    }

    public void onGameEnd(boolean async) {
        // this removes possible ghost items that players can take out of the inventory
        inventory.clear();
        player.sendMessage(lang.PREFIX + lang.GAME_CLOSED.replace("%score%", NumberUtility.convertHugeNumber(Math.floor(totalCookiesProduced))));
        GameSave.Builder builder = new GameSave.Builder(player.getUniqueId(), rule.getKey());

        builder.setCookiesClicked(this.clickCookiesProduced);
        builder.setCookiesCurrent(this.cookies);
        builder.setCookiesTotal(this.totalCookiesProduced);
        for (Buildings production : Buildings.values()) {
            builder.addBuilding(production, getBuilding(production).getCount(gameUuid));
        }
        builder.setUpgrades(activeUpgrades);
        GameSave save = builder.build();
        cookieClicker.getDatabase().saveGame(save, async);
        cookieClicker.removeGame(gameUuid);
        manager.saveStatistics(save, async);
    }

    private void load(GameSave save) {
        loaded = true;
        if (save == null) return;
        Map<String, Double> cookiesMap = save.getCookies();
        cookies = cookiesMap.get(GameSave.CURRENT);
        clickCookiesProduced = cookiesMap.get(GameSave.CLICKED);
        totalCookiesProduced = cookiesMap.get(GameSave.TOTAL);
        Map<Buildings, Integer> buildings = save.getBuildings();
        for (Buildings building : buildings.keySet()) {
            cookieClicker.getBuilding(building).addProductions(gameUuid, buildings.get(building));
            if (inventory != null) cookieClicker.getBuilding(building).visualize(this);
        }
        List<Integer> upgrades = save.getUpgrades();
        if (upgrades != null && !upgrades.isEmpty()) {
            for (int id : upgrades) {
                Upgrade upgrade = cookieClicker.getUpgrade(id);
                if (upgrade == null) continue;
                upgrade.onActivation(this);
                activeUpgrades.add(upgrade.getId());
                futureUpgrades.remove(id);
            }
        }
        if (oven != null && inventory != null) {
            calcCookiesPerSecond();
            calcCookiesPerClick();
            updateOven();
        }
    }

    @Override
    public void run() {
        if (idle || !loaded) return;
        long newTimeStamp = System.currentTimeMillis();
        if (cookiesPerSecond > 0) {
            double newCookies = ((newTimeStamp - lastTimeStamp) / 1000.) * cookiesPerSecond * currentGlobalProductionBoost;
            cookies += newCookies;
            totalCookiesProduced += newCookies;
        }
        lastTimeStamp = newTimeStamp;
        nms.updateInventoryTitle(player, lang.GAME_TITLE
                .replace("%score%", NumberUtility.convertHugeNumber(cookies))
                .replace("%score_long%", NumberUtility.convertHugeNumber(cookies, false)));
        checkUpgrades();
        if (rule.getIdleSeconds() > 0 && (newTimeStamp - lastAction)/1000. > rule.getIdleSeconds()) {
            idle = true;
            player.sendMessage(lang.PREFIX + lang.GAME_IDLE.replace("%player%", player.getName()));
            nms.updateInventoryTitle(player, lang.GAME_TITLE_IDLE);
        }
        if (luckyCookie == -1) {
            if (newTimeStamp - lastluckyCookieTimeStamp > luckyCookieSpawnTime) {
                spawnLuckyCookie();
            }
        } else {
            if (newTimeStamp - lastluckyCookieTimeStamp > luckyCookieDespawnTime) {
                inventory.setItem(luckyCookie, null);
                luckyCookie = -1;
            }
        }
    }

    private void spawnLuckyCookie() {
        lastluckyCookieTimeStamp = System.currentTimeMillis();
        int addToSlot = rand.nextInt(24);
        // skip the main cookie slots (30, 31, 32)
        if (addToSlot > 11) addToSlot = addToSlot + 3;
        int slot = 18 + addToSlot;
        inventory.setItem(slot, luckyCookieItem);
        luckyCookie = slot;
    }

    public void visualize() {
        for (Building building : cookieClicker.getBuildings().values()) {
            building.visualize(this);
        }
    }

    /**
     * Add a bonus number of cookies per click and per specified building
     *
     * @param production building type to apply bonus to
     * @param bonusPerBuilding bonus to apply per building
     */
    public void addClickBonus(Buildings production, double bonusPerBuilding) {
        if (clickBonuses.keySet().contains(production)) {
            clickBonuses.put(production, (clickBonuses.get(production) + bonusPerBuilding));
            return;
        } else {
            clickBonuses.put(production, bonusPerBuilding);
            return;
        }
    }

    public void addBuildingBonus(Buildings buildingThatGetsTheBonus, Buildings buildingTheBonusComesFrom, double bonus) {
        if (buildingBonuses.keySet().contains(buildingThatGetsTheBonus)) {
            Map<Buildings, Double> bonusMap = buildingBonuses.get(buildingThatGetsTheBonus);
            if (bonusMap.keySet().contains(buildingTheBonusComesFrom)) {
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

    public void updateGlobalBoosts() {
        this.currentGlobalClickingBoost = Math.round(cookieClicker.getBoostManager().getCurrentClickingBoost() * 10)/10.;
        this.currentGlobalProductionBoost = Math.round(cookieClicker.getBoostManager().getCurrentProductionBoost() * 10)/10.;
        updateOven();
    }

    public double getTotalCookiesProduced() {
        return this.totalCookiesProduced;
    }

    public double getClickCookiesProduced() {
        return this.clickCookiesProduced;
    }

    public Building getBuilding(Buildings production) {
        return cookieClicker.getBuilding(production);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public CCGameRules getRule() {
        return this.rule;
    }

    public Player getPlayer() {
        return this.player;
    }

    public CCLanguage getLang() {
        return this.lang;
    }

    public UUID getGameUuid() {
        return gameUuid;
    }
}
