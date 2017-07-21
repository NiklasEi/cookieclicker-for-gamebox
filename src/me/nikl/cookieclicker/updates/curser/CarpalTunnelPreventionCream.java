package me.nikl.cookieclicker.updates.curser;

import me.nikl.cookieclicker.Game;
import me.nikl.gamebox.util.NumberUtil;
import me.nikl.cookieclicker.buildings.Buildings;
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
public class CarpalTunnelPreventionCream extends Upgrade{

    public CarpalTunnelPreventionCream(Game game) {
        super(game, 1);
        this.cost = 500;
        productionsRequirements.put(Buildings.CURSER, 1);

        icon = new MaterialData(Material.ARROW).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Carpal tunnel prevention cream");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + NumberUtil.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "The mouse and cursors are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"it... it hurts to click...\"");

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public void onActivation() {
        game.baseCookiesPerClick = game.baseCookiesPerClick * 2;
        game.getBuilding(Buildings.CURSER).multiply(2);
        game.getBuilding(Buildings.CURSER).visualize(game.getInventory());
        active = true;
    }


}
