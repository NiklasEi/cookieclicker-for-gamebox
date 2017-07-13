package me.nikl.cookieclicker.buildings;

import me.nikl.cookieclicker.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

/**
 * Created by Niklas on 07.07.2017.
 */
public class Grandma extends Building {

    public Grandma(Main plugin, int slot, String name) {
        super(plugin, slot, name);

        icon = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        icon.setAmount(1);
        SkullMeta skullMeta = (SkullMeta) icon.getItemMeta();
        skullMeta.setOwner("MHF_Villager");
        skullMeta.setDisplayName(name);
        icon.setItemMeta(skullMeta);

        this.baseCost = 100;
        this.productionPerSecond = 1;

        this.description = new ArrayList<>();
        description.add(ChatColor.ITALIC + "\"A nice grandma to bake more cookies.\"");
    }
}
