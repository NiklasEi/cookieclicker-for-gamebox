package me.nikl.cookieclicker.updates.clicking;

import me.nikl.cookieclicker.Game;
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
public class FantasteelMouse extends Upgrade{

    public FantasteelMouse(Game game) {
        super(game, 366);
        this.cost = 5000000000000000000.;
        setClickCookieReq(100000000000000000.);

        icon = new MaterialData(Material.ARROW).toItemStack();
        icon.setAmount(1);

        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Fantasteel mouse");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("Price: " + NumberUtil.convertHugeNumber(cost));
        lore.add(ChatColor.AQUA + "Clicking gains +1% of your CpS..");
        lore.add("");
        lore.add(ChatColor.ITALIC + "\"You could be clicking using");
        lore.add(ChatColor.ITALIC + "   your touchpad and");
        lore.add(ChatColor.ITALIC + "   we'd benone the wiser .");

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public void onActivation() {
        game.cookiesPerClickPerCPS += 0.01;
        active = true;
    }


}
