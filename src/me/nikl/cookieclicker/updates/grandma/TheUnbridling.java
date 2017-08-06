package me.nikl.cookieclicker.updates.grandma;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.updates.Upgrade;
import me.nikl.gamebox.util.NumberUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

/**
 * Created by Niklas on 09.07.2017.
 */
public class TheUnbridling extends Upgrade{

    public TheUnbridling(Game game) {
        super(game, 307);
        this.cost = 50000000000000000.;
        productionsRequirements.put(Buildings.GRANDMA, 250);

        icon = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        icon.setAmount(1);
        SkullMeta skullMeta = (SkullMeta) icon.getItemMeta();
        skullMeta.setOwner("MHF_Villager");
        skullMeta.setDisplayName("The Unbridling");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + NumberUtil.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "Grandmas are "+ ChatColor.BOLD + "twice"+ ChatColor.RESET + ChatColor.AQUA + " as efficient.");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"It might be a classic tale of bad");
        lore.add(ChatColor.ITALIC + "    parenting, but let's see where");
        lore.add(ChatColor.ITALIC + "    grandma is going with this.\"");

        skullMeta.setLore(lore);
        icon.setItemMeta(skullMeta);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.GRANDMA).multiply(2);
        game.getBuilding(Buildings.GRANDMA).visualize(game.getInventory());
        active = true;
    }


}
