package me.nikl.cookieclicker.buildings;

import me.nikl.cookieclicker.CookieClicker;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

/**
 * @author Niklas Eicker
 */
public class AlchemyLab extends Building {

    public AlchemyLab(CookieClicker plugin, int slot, Buildings building) {
        super(plugin, slot, building);

        // old minecraft versions are missing the material SPLASH_POTION
        Material mat;
        try {
            mat = Material.SPLASH_POTION;
        } catch (NoSuchFieldError tooOldVersion) {
            mat = Material.POTION;
        }

        icon = new ItemStack(mat, 1);
        icon.setAmount(1);
        PotionMeta meta = (PotionMeta) icon.getItemMeta();
        meta.addItemFlags(ItemFlag.values());
        meta.setDisplayName(lang.GAME_BUILDING_NAME.replace("%name%", name));
        icon.setItemMeta(meta);

        this.productionPerSecond = 1600000.;
        this.baseCost = 75000000000.;
    }
}
