package me.nikl.cookieclicker.updates.farm;

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
public class CheapHoes extends Upgrade{

    public CheapHoes(Game game) {
        super(game, 10);
        this.cost = 11000;
        productionsRequirements.put(Productions.FARM, 1);

        icon = new MaterialData(Material.DIRT).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Cheap hoes");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + Utility.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "Farms are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"Rake in the dough!\"");

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public void onActivation() {
        game.getProduction(Productions.FARM).multiply(2);
        game.getProduction(Productions.FARM).visualize(game.getInventory());
        active = true;
    }


}
