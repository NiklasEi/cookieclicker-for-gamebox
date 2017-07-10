package me.nikl.cookieclicker.updates.Curser;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.Utility;
import me.nikl.cookieclicker.productions.Productions;
import me.nikl.cookieclicker.updates.Upgrade;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by Niklas on 09.07.2017.
 *
 */
public class Ambidextrous extends Upgrade{

    public Ambidextrous(Game game) {
        super(game, 2);
        this.cost = 10000;
        productionsRequirements.put(Productions.CURSER, 10);

        icon = new MaterialData(Material.ARROW).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Ambidextrous");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + Utility.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "The mouse and cursors are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"Look ma, both hands!\"");

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
