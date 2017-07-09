package me.nikl.cookieclicker.updates;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.productions.Productions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by Niklas on 09.07.2017.
 *
 *
 */
public abstract class Upgrade {
    protected Map<Productions, Integer> productionsRequirements;
    private Map<String, Double> cookiesRequirements;

    protected double cost;
    protected ItemStack icon;

    protected Game game;

    protected boolean active;

    public Upgrade(Game game){
        productionsRequirements = new HashMap<>();
        cookiesRequirements = new HashMap<>();
        icon = new MaterialData(Material.BARRIER).toItemStack();
        icon.setAmount(1);
        this.cost = 1;
        this.game = game;
    }

    protected void setTotalCookieReq(double count){
        cookiesRequirements.put("total", count);
    }

    protected void setClickCookieReq(double count){
        cookiesRequirements.put("click", count);
    }

    public boolean isUnlocked(){
        for(String key : cookiesRequirements.keySet()){
            switch (key){
                case "total":
                    if(game.getTotalCookiesProduced() < cookiesRequirements.get(key)){
                        return false;
                    }
                    break;
                case "click":
                    if(game.getClickCookiesProduced() < cookiesRequirements.get(key)){
                        return false;
                    }
                    break;
                default:
                    Bukkit.getLogger().log(Level.SEVERE, "unknown cookie requirement");
                    return false;
            }
        }

        for (Productions productions : productionsRequirements.keySet()){
            if(game.getProduction(productions).getCount() < productionsRequirements.get(productions)){
                return false;
            }
        }

        return true;
    }

    public abstract void onActivation();

    public boolean isActive() {
        return active;
    }

    public double getCost(){
        return this.cost;
    }

    public ItemStack getIcon(){
        return this.icon;
    }
}
