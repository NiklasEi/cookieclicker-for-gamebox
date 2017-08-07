package me.nikl.cookieclicker.upgrades.bank;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
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
public class FinancialZen extends Upgrade{

    public FinancialZen(Game game) {
        super(game, 237);
        this.cost = 700000000000000.;
        productionsRequirements.put(Buildings.BANK, 150);

        icon = new MaterialData(Material.GOLD_NUGGET).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Financial zen");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + NumberUtil.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "Banks are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"The ultimate grail of economic thought;");
        lore.add(ChatColor.ITALIC + "    the feng shui of big money,");
        lore.add(ChatColor.ITALIC + "    the stock market yoga,");
        lore.add(ChatColor.ITALIC + "    the Heimlich maneuver");
        lore.add(ChatColor.ITALIC + "    of dimes and nickels.\"");

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
