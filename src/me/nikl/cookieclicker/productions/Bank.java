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
public class Bank extends Building {

    public Bank(Main plugin, int slot, String name) {
        super(plugin, slot, name);

        icon = new MaterialData(Material.GOLD_NUGGET).toItemStack();
        icon.setAmount(1);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Bank");
        icon.setItemMeta(meta);

        this.productionPerSecond = 1400;
        this.baseCost = 1400000;

        this.description = new ArrayList<>();
        description.add(ChatColor.ITALIC + "\"Generates cookies from interest.\"");
    }
}
