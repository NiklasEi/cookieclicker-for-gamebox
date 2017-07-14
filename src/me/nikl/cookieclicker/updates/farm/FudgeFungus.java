package me.nikl.cookieclicker.updates.farm;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.Utility;
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
public class FudgeFungus extends Upgrade{

    public FudgeFungus(Game game) {
        super(game, 295);
        this.cost = 550000000000000.;
        productionsRequirements.put(Buildings.FARM, 200);

        icon = new MaterialData(Material.DIRT).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Fudge fungus");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + Utility.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "Farms are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"A sugary parasite whose tendrils help cookie growth.\"");
        lore.add(ChatColor.ITALIC + "\"    Please do not breathe in the spores.\"");
        lore.add(ChatColor.ITALIC + "\"    In case of spore ingestion,\"");
        lore.add(ChatColor.ITALIC + "\"    seek medical help within the next 36 seconds.\"");

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.FARM).multiply(2);
        game.getBuilding(Buildings.FARM).visualize(game.getInventory());
        active = true;
    }


}
