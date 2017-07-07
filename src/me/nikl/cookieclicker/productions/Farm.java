package me.nikl.cookieclicker.productions;

import me.nikl.cookieclicker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklas on 05.07.2017.
 */
public class Farm extends Production {

    public Farm(Main plugin, int slot, String name) {
        super(plugin, slot, name);

        icon = new MaterialData(Material.DIRT).toItemStack();
        icon.setAmount(1);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Farm");
        icon.setItemMeta(meta);

        this.productionPerSecond = 8;
        this.baseCost = 1100;

        this.description = new ArrayList<>();
        description.add(ChatColor.ITALIC + "\"Grows cookie plants from cookie seeds.\"");
    }
}
