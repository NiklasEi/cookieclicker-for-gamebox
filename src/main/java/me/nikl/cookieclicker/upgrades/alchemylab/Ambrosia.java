package me.nikl.cookieclicker.upgrades.alchemylab;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;

/**
 * @author Niklas Eicker
 */
public class Ambrosia extends Upgrade {

    public Ambrosia(CookieClicker game) {
        super(game, 49);
        this.cost = 3750000000000000.;
        productionsRequirements.put(Buildings.ALCHEMY_LAB, 50);

        loadLanguage(UpgradeType.CLASSIC, Buildings.ALCHEMY_LAB);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.ALCHEMY_LAB).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.ALCHEMY_LAB).visualize(game);
    }
}
