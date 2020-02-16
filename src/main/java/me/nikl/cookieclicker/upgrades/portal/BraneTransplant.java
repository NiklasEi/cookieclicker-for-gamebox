package me.nikl.cookieclicker.upgrades.portal;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Niklas Eicker
 */
public class BraneTransplant extends Upgrade {

    public BraneTransplant(CookieClicker game) {
        super(game, 116);
        this.cost = 5000000000000000000.;
        productionsRequirements.put(Buildings.PORTAL, 100);

        icon = new ItemStack(Material.ENDER_EYE, 1);
        icon.setAmount(1);

        loadLanguage(UpgradeType.CLASSIC, Buildings.PORTAL);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.PORTAL).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.PORTAL).visualize(game);
    }
}
