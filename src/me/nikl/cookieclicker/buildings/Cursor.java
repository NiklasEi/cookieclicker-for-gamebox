package me.nikl.cookieclicker.buildings;

import me.nikl.cookieclicker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by Niklas on 05.07.2017.
 */
public class Cursor extends Building {

    public Cursor(Main plugin, int slot, String name) {
        super(plugin, slot, name);

        icon = new MaterialData(Material.ARROW).toItemStack();
        icon.setAmount(1);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(name);
        icon.setItemMeta(meta);

        this.productionPerSecond = 0.1;
        this.baseCost = 15;

        this.description = new ArrayList<>();
        description.add(ChatColor.ITALIC + "\"Autoclicks once every 10 seconds.\"");
    }
}
