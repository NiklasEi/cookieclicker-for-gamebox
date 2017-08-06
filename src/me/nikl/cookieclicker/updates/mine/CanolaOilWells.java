package me.nikl.cookieclicker.updates.mine;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.updates.Upgrade;
import me.nikl.gamebox.GameBoxSettings;
import me.nikl.gamebox.util.NumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by Niklas on 09.07.2017.
 *
 */
public class CanolaOilWells extends Upgrade{

    public CanolaOilWells(Game game) {
        super(game, 309);
        this.cost = 6000000000000000000.;
        productionsRequirements.put(Buildings.MINE, 250);

        icon = new MaterialData(Material.DIAMOND_PICKAXE).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Canola oil wells");
        if(!GameBoxSettings.delayedInventoryUpdate) meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + NumberUtil.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "Mines are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"A previously untapped resource,");
        lore.add(ChatColor.ITALIC + "     canola oil permeates the underground");
        lore.add(ChatColor.ITALIC + "     olifers which grant it its particular");
        lore.add(ChatColor.ITALIC + "     taste and lucrative properties.\"");

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.MINE).multiply(2);
        game.getBuilding(Buildings.MINE).visualize(game.getInventory());
        active = true;
    }


}
