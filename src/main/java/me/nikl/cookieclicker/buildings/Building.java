package me.nikl.cookieclicker.buildings;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.CCLanguage;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.gamebox.utility.NumberUtility;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Niklas Eicker
 */
public class Building {
    protected Map<UUID, Integer> count = new HashMap<>();
    protected double productionPerSecond;
    protected Map<UUID, Double> multiplier = new HashMap<>();
    protected Map<UUID, Double> otherBuildingsBonus = new HashMap<>();

    protected double baseCost = 0.;
    protected int slot;
    protected ItemStack icon;
    protected List<String> lore;
    protected String name;
    protected CCLanguage lang;
    private Buildings type;

    public Building(CookieClicker plugin, int slot, Buildings building) {
        this.type = building;
        this.lang = (CCLanguage) plugin.getGameLang();
        this.name = lang.buildingName.get(building);
        lore = new ArrayList<>();
        for (String line : lang.buildingLore.get(building)) {
            lore.add(line.replace("%name%", name));
        }
        if (slot < 0 || slot > 53) {
            slot = 0;
        }
        this.slot = slot;
    }

    public double getAllInAllProductionPerSecond(UUID uuid) {
        return getProductionPerSecondPerItem(uuid) * count.get(uuid);
    }

    public void addProductions(UUID uuid, int amount) {
        Integer current = count.get(uuid);
        count.put(uuid, amount + current);
    }

    public void prepareGame(UUID uuid) {
        count.put(uuid, 0);
        multiplier.put(uuid, 1.);
        otherBuildingsBonus.put(uuid, 0.);
    }

    public void removeGame(UUID uuid) {
        count.remove(uuid);
        multiplier.remove(uuid);
        otherBuildingsBonus.remove(uuid);
    }

    /***
     * Add a multiplier
     *
     * 0.5 equals 50%
     * 1 equals 100%
     * @param toAdd production to add to multiplier
     */
    public void addMultiplier(UUID uuid, double toAdd) {
        this.multiplier.put(uuid, multiplier.get(uuid) + toAdd);
    }

    /**
     * Multiply the current production
     *
     * The current multiplier is multiplied by the
     * given factor
     *
     * @param multiplier factor for old multiplier
     */
    public void multiply(UUID uuid, double multiplier) {
        this.multiplier.put(uuid, this.multiplier.get(uuid)*multiplier);
    }

    public int getCount(UUID uuid) {
        return count.get(uuid);
    }

    /**
     * Calculate and return the cost for the next building
     *
     * @return cost of next building
     */
    public double getCost(UUID uuid) {
        return (baseCost * Math.pow(1.15, count.get(uuid)));
    }

    public double getProductionPerSecondPerItem(UUID uuid) {
        return productionPerSecond * multiplier.get(uuid) + otherBuildingsBonus.get(uuid);
    }

    public void visualize(CCGame game) {
        if (icon == null || lore == null || game.getInventory() == null) return;
        UUID uuid = game.getGameUuid();
        List<String> newLore = new ArrayList<>();
        for (String line : lore) {
            newLore.add(line.replace("%count%", String.valueOf(count.get(game.getGameUuid())))
                    .replace("%cost%", NumberUtility.convertHugeNumber(getCost(uuid)))
                    .replace("%cookies_per_second_per_item%", NumberUtility.convertHugeNumber(getProductionPerSecondPerItem(uuid)))
                    .replace("%cookies_per_second%", NumberUtility.convertHugeNumber(getAllInAllProductionPerSecond(uuid)))
                    .replace("%cost_long%", NumberUtility.convertHugeNumber(getCost(uuid), false))
                    .replace("%cookies_per_second_per_item_long%", NumberUtility.convertHugeNumber(getProductionPerSecondPerItem(uuid), false))
                    .replace("%cookies_per_second_long%", NumberUtility.convertHugeNumber(getAllInAllProductionPerSecond(uuid), false)));
        }
        ItemMeta meta = icon.getItemMeta();
        meta.setLore(newLore);
        icon.setItemMeta(meta);

        game.getInventory().setItem(slot, icon);
    }

    public void setOtherBuildingsBonus(UUID uuid, double bonus) {
        this.otherBuildingsBonus.put(uuid, bonus);
    }

    public ItemStack getIcon() {
        return icon.clone();
    }

    public Buildings getType() {
        return type;
    }
}
