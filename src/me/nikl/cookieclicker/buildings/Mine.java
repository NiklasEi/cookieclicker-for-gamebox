package me.nikl.cookieclicker.buildings;

import me.nikl.cookieclicker.Main;
import me.nikl.gamebox.GameBoxSettings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by Niklas on 05.07.2017.
 */
public class Mine extends Building {

    public Mine(Main plugin, int slot, String name) {
        super(plugin, slot, name);

        icon = new MaterialData(Material.DIAMOND_PICKAXE).toItemStack();
        icon.setAmount(1);
        ItemMeta meta = icon.getItemMeta();
        if(!GameBoxSettings.delayedInventoryUpdate) meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName("Mine");
        icon.setItemMeta(meta);

        this.productionPerSecond = 47;
        this.baseCost = 12000;

        this.description = new ArrayList<>();
        description.add(ChatColor.ITALIC + "\"Mines out cookie dough and chocolate chips.\"");
    }
}
