package me.nikl.cookieclicker.updates.Curser;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.Utility;
import me.nikl.cookieclicker.productions.Productions;
import me.nikl.cookieclicker.updates.Upgrade;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by Niklas on 09.07.2017.
 *
 */
public class ReinforcedIndexFinger extends Upgrade{

    public ReinforcedIndexFinger(Game game) {
        super(game);
        this.cost = 100;
        productionsRequirements.put(Productions.CURSER, 1);

        icon = new MaterialData(Material.ARROW).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Reinforced index finger");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + Utility.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "The mouse and cursors are twice as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"prod prod\"");

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public void onActivation() {
        game.cookiesPerClick = game.cookiesPerClick * 2;
        game.getProduction(Productions.CURSER).multiply(2);
        game.getProduction(Productions.CURSER).visualize(game.getInventory());
        active = true;
    }


}
