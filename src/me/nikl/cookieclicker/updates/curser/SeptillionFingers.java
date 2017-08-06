package me.nikl.cookieclicker.updates.curser;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.updates.Upgrade;
import me.nikl.gamebox.util.NumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by Niklas on 09.07.2017.
 *
 */
public class SeptillionFingers extends Upgrade{

    public SeptillionFingers(Game game) {
        super(game, 188);
        this.cost = 1000000000000000.;
        productionsRequirements.put(Buildings.CURSOR, 280);

        icon = new MaterialData(Material.ARROW).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Septillion fingers");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + NumberUtil.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "The mouse and cursors gain "+ ChatColor.BOLD + "+500000"+ ChatColor.RESET + ChatColor.AQUA + " cookies");
        lore.add(ChatColor.AQUA + "     for each non-cursor object owned.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"[cursory flavor text]\"");

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public void onActivation() {
        for(Buildings buildings : Buildings.values()){
            if (buildings == Buildings.CURSOR) continue;
            game.addBuildingBonus(Buildings.CURSOR, buildings, 500000);
            game.addClickBonus(buildings, 500000);
        }
        active = true;
    }


}
