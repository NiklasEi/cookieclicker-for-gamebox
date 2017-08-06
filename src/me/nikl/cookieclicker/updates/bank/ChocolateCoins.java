package me.nikl.cookieclicker.updates.bank;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.updates.Upgrade;
import me.nikl.gamebox.util.NumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by Niklas on 09.07.2017.
 *
 */
public class ChocolateCoins extends Upgrade{

    public ChocolateCoins(Game game) {
        super(game, 235);
        this.cost = 70000000000.;
        productionsRequirements.put(Buildings.BANK, 50);

        icon = new MaterialData(Material.GOLD_NUGGET).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Chocolate coins");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + NumberUtil.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "Banks are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"This revolutionary currency is");
        lore.add(ChatColor.ITALIC + "    much easier to melt and form");
        lore.add(ChatColor.ITALIC + "    into ingots - and tastes much");
        lore.add(ChatColor.ITALIC + "    better, for a change.\"");

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.BANK).multiply(2);
        game.getBuilding(Buildings.BANK).visualize(game.getInventory());
        active = true;
    }


}
