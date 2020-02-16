package me.nikl.cookieclicker.upgrades.wizardtower;

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
public class KitchenCurses extends Upgrade {

    public KitchenCurses(CookieClicker game) {
        super(game, 247);
        this.cost = 16500000000000.;
        productionsRequirements.put(Buildings.WIZARD_TOWER, 50);

        icon = new ItemStack(Material.BLAZE_ROD, 1);
        icon.setAmount(1);

        loadLanguage(UpgradeType.CLASSIC, Buildings.WIZARD_TOWER);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.WIZARD_TOWER).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.WIZARD_TOWER).visualize(game);
    }


}
