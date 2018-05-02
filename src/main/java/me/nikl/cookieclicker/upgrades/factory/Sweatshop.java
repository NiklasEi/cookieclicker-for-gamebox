package me.nikl.cookieclicker.upgrades.factory;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * @author Niklas Eicker
 *
 *         Custom upgrade replacing 'Sweatshop'
 */
public class Sweatshop extends Upgrade {

    public Sweatshop(CookieClicker game) {
        super(game, 15);
        this.cost = 65000000;
        productionsRequirements.put(Buildings.FACTORY, 25);

        icon = new MaterialData(Material.IRON_BLOCK).toItemStack();
        icon.setAmount(1);

        loadLanguage(UpgradeType.CLASSIC, Buildings.FACTORY);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.FACTORY).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.FACTORY).visualize(game);
    }
}
