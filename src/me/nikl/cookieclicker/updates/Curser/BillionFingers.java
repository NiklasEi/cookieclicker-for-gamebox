package me.nikl.cookieclicker.updates.Curser;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.Utility;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.updates.Upgrade;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by Niklas on 09.07.2017.
 *
 */
public class BillionFingers extends Upgrade{

    public BillionFingers(Game game) {
        super(game, 5);
        this.cost = 100000000;
        productionsRequirements.put(Buildings.CURSER, 80);

        icon = new MaterialData(Material.ARROW).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Billion fingers");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + Utility.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "The mouse and cursors gain "+ ChatColor.BOLD + "+5"+ ChatColor.RESET + ChatColor.AQUA + " cookies");
        lore.add(ChatColor.AQUA + "     for each non-cursor object owned.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"clickityclickityclickity\"");

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public void onActivation() {
        for(Buildings buildings : Buildings.values()){
            if (buildings == Buildings.CURSER) continue;
            game.addBuildingBonus(Buildings.CURSER, buildings, 5);
            game.addClickBonus(buildings, 5);
        }
        active = true;
    }


}
