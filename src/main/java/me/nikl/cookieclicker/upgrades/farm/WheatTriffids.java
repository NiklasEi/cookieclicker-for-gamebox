package me.nikl.cookieclicker.upgrades.farm;

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
public class WheatTriffids extends Upgrade {

    public WheatTriffids(CookieClicker game) {
        super(game, 308);
        this.cost = 550000000000000000.;
        productionsRequirements.put(Buildings.FARM, 250);

        icon = new ItemStack(Material.DIRT, 1);
        icon.setAmount(1);

        loadLanguage(UpgradeType.CLASSIC, Buildings.FARM);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.FARM).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.FARM).visualize(game);
    }
}
