package me.nikl.cookieclicker.updates.grandma;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.updates.Upgrade;
import me.nikl.gamebox.util.NumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

/**
 * Created by Niklas on 09.07.2017.
 */
public class DoubleThickGlasses extends Upgrade{

    public DoubleThickGlasses(Game game) {
        super(game, 110);
        this.cost = 500000000;
        productionsRequirements.put(Buildings.GRANDMA, 100);

        icon = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        icon.setAmount(1);
        SkullMeta skullMeta = (SkullMeta) icon.getItemMeta();
        skullMeta.setOwner("MHF_Villager");
        skullMeta.setDisplayName("Double-thick glasses");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + NumberUtil.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "Grandmas are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"Oh... so THAT's what I've been baking.\"");

        skullMeta.setLore(lore);
        icon.setItemMeta(skullMeta);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.GRANDMA).multiply(2);
        game.getBuilding(Buildings.GRANDMA).visualize(game.getInventory());
        active = true;
    }


}
