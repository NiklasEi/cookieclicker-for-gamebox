package me.nikl.cookieclicker.buildings;

import me.nikl.cookieclicker.CookieClicker;
import me.nikl.gamebox.GameBoxSettings;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author Niklas Eicker
 */
public class Mine extends Building {

    public Mine(CookieClicker plugin, int slot, Buildings building) {
        super(plugin, slot, building);

        icon = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        icon.setAmount(1);
        ItemMeta meta = icon.getItemMeta();
        if (!GameBoxSettings.version1_8) meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(lang.GAME_BUILDING_NAME.replace("%name%", name));
        icon.setItemMeta(meta);

        this.productionPerSecond = 47;
        this.baseCost = 12000;
    }
}
