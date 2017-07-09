package me.nikl.cookieclicker.updates.grandma;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.Utility;
import me.nikl.cookieclicker.productions.Productions;
import me.nikl.cookieclicker.updates.Upgrade;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by Niklas on 09.07.2017.
 */
public class ForwardsFromGrandma extends Upgrade{

    public ForwardsFromGrandma(Game game) {
        super(game);
        this.cost = 1000;
        productionsRequirements.put(Productions.Grandma, 1);

        icon = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        icon.setAmount(1);
        SkullMeta skullMeta = (SkullMeta) icon.getItemMeta();
        skullMeta.setOwner("MHF_Villager");
        skullMeta.setDisplayName("Forwards from grandma");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + Utility.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "Grandmas are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"RE:RE:thought you'd get a kick out of this ;))\"");

        skullMeta.setLore(lore);
        icon.setItemMeta(skullMeta);
    }

    @Override
    public void onActivation() {
        game.getProduction(Productions.Grandma).multiply(2);
        game.getProduction(Productions.Grandma).visualize(game.getInventory());
        active = true;
    }


}
