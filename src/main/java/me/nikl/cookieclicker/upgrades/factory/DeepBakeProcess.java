package me.nikl.cookieclicker.upgrades.factory;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Niklas Eicker
 *
 *         Custom upgrade replacing 'Sweatshop'
 */
public class DeepBakeProcess extends Upgrade {

    public DeepBakeProcess(CookieClicker game) {
        super(game, 194);
        this.cost = 65000000000000.;
        productionsRequirements.put(Buildings.FACTORY, 150);

        icon = new ItemStack(Material.IRON_BLOCK, 1);
        icon.setAmount(1);

        loadLanguage(UpgradeType.CLASSIC, Buildings.FACTORY);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.FACTORY).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.FACTORY).visualize(game);
    }
}
