package me.nikl.cookieclicker.buildings;

import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Building;
import me.nikl.cookieclicker.buildings.Buildings;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

/**
 * @author Niklas Eicker
 */
public class Portal extends Building {

    public Portal(CookieClicker plugin, int slot, Buildings building) {
        super(plugin, slot, building);

        icon = new MaterialData(Material.EYE_OF_ENDER).toItemStack();
        icon.setAmount(1);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(lang.GAME_BUILDING_NAME.replace("%name%", name));
        icon.setItemMeta(meta);

        this.productionPerSecond = 10000000.;
        this.baseCost = 1000000000000.;
    }
}
