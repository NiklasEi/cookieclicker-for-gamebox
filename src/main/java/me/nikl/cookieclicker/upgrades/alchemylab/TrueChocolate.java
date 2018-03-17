package me.nikl.cookieclicker.upgrades.alchemylab;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;

/**
 * @author Niklas Eicker
 */
public class TrueChocolate extends Upgrade {

    public TrueChocolate(CCGame game) {
        super(game, 24);
        this.cost = 37500000000000.;
        productionsRequirements.put(Buildings.ALCHEMY_LAB, 25);

        loadLanguage(UpgradeType.CLASSIC, Buildings.ALCHEMY_LAB);
    }

    @Override
    public void onActivation() {
        game.getBuilding(Buildings.ALCHEMY_LAB).multiply(2);
        game.getBuilding(Buildings.ALCHEMY_LAB).visualize(game.getInventory());
        active = true;
    }
}
