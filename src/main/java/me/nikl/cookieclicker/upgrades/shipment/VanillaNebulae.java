package me.nikl.cookieclicker.upgrades.shipment;

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
public class VanillaNebulae extends Upgrade {

    public VanillaNebulae(CookieClicker game) {
        super(game, 19);
        this.cost = 51000000000.;
        productionsRequirements.put(Buildings.SHIPMENT, 1);

        icon = new ItemStack(Material.FIREWORK_ROCKET, 1);

        loadLanguage(UpgradeType.CLASSIC, Buildings.SHIPMENT);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.SHIPMENT).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.SHIPMENT).visualize(game);
    }


}
