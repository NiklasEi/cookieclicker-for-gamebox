package me.nikl.cookieclicker.updates.bank;

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
public class TallerTellers extends Upgrade{

    public TallerTellers(Game game) {
        super(game, 232);
        this.cost = 14000000;
        productionsRequirements.put(Productions.BANK, 1);

        icon = new MaterialData(Material.GOLD_NUGGET).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Taller tellers");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + Utility.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "Banks are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"Able to process a higher amount of transactions.");
        lore.add(ChatColor.ITALIC + "    Careful though, as taller tellers tell tall tales.\"");

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public void onActivation() {
        game.getProduction(Productions.BANK).multiply(2);
        game.getProduction(Productions.BANK).visualize(game.getInventory());
        active = true;
    }


}
