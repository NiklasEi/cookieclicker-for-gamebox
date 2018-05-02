package me.nikl.cookieclicker.upgrades.alchemylab;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;

/**
 * @author Niklas Eicker
 */
public class OriginCrucible extends Upgrade {
    public OriginCrucible(CookieClicker game) {
        super(game, 197);
        this.cost = 37500000000000000000.;
        productionsRequirements.put(Buildings.ALCHEMY_LAB, 150);
        loadLanguage(UpgradeType.CLASSIC, Buildings.ALCHEMY_LAB);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.ALCHEMY_LAB).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.ALCHEMY_LAB).visualize(game);
    }
}
