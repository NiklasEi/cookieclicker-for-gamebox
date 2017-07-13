package me.nikl.cookieclicker.updates.bank;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.Utility;
import me.nikl.cookieclicker.productions.Buildings;
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
public class ScissorResistantCreditCards extends Upgrade{

    public ScissorResistantCreditCards(Game game) {
        super(game, 233);
        this.cost = 70000000;
        productionsRequirements.put(Buildings.BANK, 5);

        icon = new MaterialData(Material.GOLD_NUGGET).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Scissor-resistant credit cards");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + Utility.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "Banks are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"For those truly valued customers.\"");

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
