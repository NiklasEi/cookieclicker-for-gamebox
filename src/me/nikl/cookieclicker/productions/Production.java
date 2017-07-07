package me.nikl.cookieclicker.productions;

import me.nikl.cookieclicker.Language;
import me.nikl.cookieclicker.Main;
import me.nikl.cookieclicker.Utility;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklas on 05.07.2017.
 */
public class Production {
    protected int count = 0;
    protected double baseCost = 0.;
    protected int slot;
    protected ItemStack icon;
    protected double productionPerSecond = 0.;
    protected double multiplier = 1.;
    protected List<String > lore;
    protected List<String > description;

    protected String name;

    protected Language lang;

    public Production(Main plugin, int slot, String name){
        this.lang = plugin.lang;
        this.name = name;

        lore = new ArrayList<>();
        for(String line : lang.GAME_PRODUCTION_LORE){
            lore.add(line.replace("%name%", name));
        }

        if(slot < 0 || slot > 53){
            slot = 0;
        }


        this.slot = slot;
    }

    public double getAllInAllProductionPerSecond(){
        return productionPerSecond * count * multiplier;
    }

    public void addProductions(int amount){
        this.count += amount;
    }

    /***
     * Add a multiplier
     *
     * 0.5 => 50%
     * 1 => 100%
     * @param toAdd production to add to multiplier
     */
    public void addMultiplier(double toAdd) {
        this.multiplier += toAdd;
    }

    /**
     * Multiply the current production
     *
     * The current multiplier is multiplied by the
     * given factor
     * @param multiplier
     */
    public void multiply(double multiplier){
        this.multiplier = this.multiplier * multiplier;
    }

    public int getCount() {
        return count;
    }

    /**
     * Calculate and return the cost for the next building
     * @return
     */
    public int getCost() {
        return (int) (baseCost * Math.pow(1.15, count));
    }

    public double getProductionPerSecondPerItem() {
        return productionPerSecond * multiplier;
    }

    public void visualize(Inventory inventory){
        if(icon == null || lore == null) return;
        List<String> newLore = new ArrayList<>();
        for (String line : lore) {
            newLore.add(line.replace("%count%", String.valueOf(count))
                    .replace("%cost%", Utility.convertHugeNumber(getCost()))
                    .replace("%cookies_per_second_per_item%", Utility.convertHugeNumber(getProductionPerSecondPerItem()))
                    .replace("%cookies_per_second%", Utility.convertHugeNumber(getAllInAllProductionPerSecond())));
        }
        if(description != null && !description.isEmpty()){
            newLore.add("");
            newLore.addAll(description);
        }
        ItemMeta meta = icon.getItemMeta();
        meta.setLore(newLore);
        icon.setItemMeta(meta);

        inventory.setItem(slot, icon);
    }
}
