package me.nikl.cookieclicker.upgrades.portal;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * @author Niklas Eicker
 */
public class DeitySizedPortals extends Upgrade {

    public DeitySizedPortals(CookieClicker game) {
        super(game, 198);
        this.cost = 500000000000000000000.;
        productionsRequirements.put(Buildings.PORTAL, 150);

        icon = new MaterialData(Material.EYE_OF_ENDER).toItemStack();
        icon.setAmount(1);

        loadLanguage(UpgradeType.CLASSIC, Buildings.PORTAL);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.PORTAL).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.PORTAL).visualize(game);
    }
}
