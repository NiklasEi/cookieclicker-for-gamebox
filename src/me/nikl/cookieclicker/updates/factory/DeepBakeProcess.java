package me.nikl.cookieclicker.updates.factory;

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
 * Custom upgrade replacing 'Sweatshop'
 */
public class DeepBakeProcess extends Upgrade{

    public DeepBakeProcess(Game game) {
        super(game, 194);
        this.cost = 65000000000000.;
        productionsRequirements.put(Buildings.FACTORY, 150);

        icon = new MaterialData(Material.IRON_BLOCK).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Deep-bake process");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + NumberUtil.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "Factories are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"A patented process increasing cookie");
        lore.add(ChatColor.ITALIC + "    yield two-fold for the same");
        lore.add(ChatColor.ITALIC + "    amount of ingredients.");
        lore.add(ChatColor.ITALIC + "    Don't ask how, don't take pictures,");
        lore.add(ChatColor.ITALIC + "    and be sure to wear your");
        lore.add(ChatColor.ITALIC + "    protective suit.\"");

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.FACTORY).multiply(2);
        game.getBuilding(Buildings.FACTORY).visualize(game.getInventory());
        active = true;
    }
}
