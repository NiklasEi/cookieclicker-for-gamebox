package me.nikl.cookieclicker.updates.curser;

import me.nikl.cookieclicker.Game;
import me.nikl.gamebox.util.NumberUtil;
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
public class MillionFingers extends Upgrade{

    public MillionFingers(Game game) {
        super(game, 4);
        this.cost = 10000000;
        productionsRequirements.put(Buildings.CURSOR, 40);

        icon = new MaterialData(Material.ARROW).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Million fingers");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + NumberUtil.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "The mouse and cursors gain "+ ChatColor.BOLD + "+0.5"+ ChatColor.RESET + ChatColor.AQUA + " cookies");
        lore.add(ChatColor.AQUA + "     for each non-cursor object owned.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"clickityclickity\"");

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public void onActivation() {
        for(Buildings buildings : Buildings.values()){
            if (buildings == Buildings.CURSOR) continue;
            game.addBuildingBonus(Buildings.CURSOR, buildings, 0.5);
            game.addClickBonus(buildings, 0.5);
        }
        active = true;
    }


}
