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
public class Factory extends Building {

    public Factory(Main plugin, int slot, String name) {
        super(plugin, slot, name);

        icon = new MaterialData(Material.IRON_BLOCK).toItemStack();
        icon.setAmount(1);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Factory");
        icon.setItemMeta(meta);

        this.productionPerSecond = 260;
        this.baseCost = 130000;

        this.description = new ArrayList<>();
        description.add(ChatColor.ITALIC + "\"Produces large quantities of cookies.\"");
    }
}
