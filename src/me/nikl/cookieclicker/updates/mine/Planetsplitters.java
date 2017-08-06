package me.nikl.cookieclicker.updates.mine;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.updates.Upgrade;
import me.nikl.gamebox.GameBoxSettings;
import me.nikl.gamebox.util.NumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by Niklas on 09.07.2017.
 *
 */
public class Planetsplitters extends Upgrade{

    public Planetsplitters(Game game) {
        super(game, 296);
        this.cost = 6000000000000000.;
        productionsRequirements.put(Buildings.MINE, 200);

        icon = new MaterialData(Material.DIAMOND_PICKAXE).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Planetsplitters");
        if(!GameBoxSettings.delayedInventoryUpdate) meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + NumberUtil.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "Mines are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"These new state-of-the-art excavators");
        lore.add(ChatColor.ITALIC + "     have been tested on Merula,");
        lore.add(ChatColor.ITALIC + "     Globort and Flwanza VI, among other");
        lore.add(ChatColor.ITALIC + "     distant planets which have been");
        lore.add(ChatColor.ITALIC + "     curiously quiet lately.\"");

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.MINE).multiply(2);
        game.getBuilding(Buildings.MINE).visualize(game.getInventory());
        active = true;
    }


}
