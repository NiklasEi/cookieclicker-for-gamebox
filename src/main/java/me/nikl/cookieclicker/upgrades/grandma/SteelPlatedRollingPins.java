package me.nikl.cookieclicker.upgrades.grandma;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * @author Niklas Eicker
 */
public class SteelPlatedRollingPins extends Upgrade {

    public SteelPlatedRollingPins(CookieClicker game) {
        super(game, 8);
        this.cost = 5000;
        productionsRequirements.put(Buildings.GRANDMA, 5);

        icon = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        icon.setAmount(1);
        SkullMeta skullMeta = (SkullMeta) icon.getItemMeta();
        skullMeta.setOwner("MHF_Villager");
        icon.setItemMeta(skullMeta);

        loadLanguage(UpgradeType.CLASSIC, Buildings.GRANDMA);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.GRANDMA).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.GRANDMA).visualize(game);
    }


}
