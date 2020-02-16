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
public class InsaneOatlingWorkers extends Upgrade {

    public InsaneOatlingWorkers(CookieClicker game) {
        super(game, 26);
        this.cost = 50000000000000.;
        productionsRequirements.put(Buildings.PORTAL, 5);

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
