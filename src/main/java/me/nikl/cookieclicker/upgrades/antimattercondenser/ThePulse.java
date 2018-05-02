package me.nikl.cookieclicker.upgrades.antimattercondenser;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;

/**
 * @author Niklas Eicker
 */
public class ThePulse extends Upgrade {

    public ThePulse(CookieClicker game) {
        super(game, 305);
        this.cost = 85000000000000000000000000.;
        productionsRequirements.put(Buildings.ANTIMATTER_CONDENSER, 200);

        // for the standard upgrade type the building icon is used
        loadLanguage(UpgradeType.CLASSIC, Buildings.ANTIMATTER_CONDENSER);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.ANTIMATTER_CONDENSER).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.ANTIMATTER_CONDENSER).visualize(game);
    }
}
