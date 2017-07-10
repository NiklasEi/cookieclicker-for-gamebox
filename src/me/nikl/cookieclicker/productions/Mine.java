package me.nikl.cookieclicker.productions;

import me.nikl.cookieclicker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by Niklas on 05.07.2017.
 */
public class Mine extends Production {

    public Mine(Main plugin, int slot, String name) {
        super(plugin, slot, name);

        icon = new MaterialData(Material.DIAMOND_PICKAXE).toItemStack();
        icon.setAmount(1);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Mine");
        icon.setItemMeta(meta);

        this.productionPerSecond = 47;
        this.baseCost = 12000;

        this.description = new ArrayList<>();
        description.add(ChatColor.ITALIC + "\"Mines out cookie dough and chocolate chips.\"");
    }
}
