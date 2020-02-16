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
public class DarkFormulas extends Upgrade {

    public DarkFormulas(CookieClicker game) {
        super(game, 249);
        this.cost = 165000000000000000.;
        productionsRequirements.put(Buildings.WIZARD_TOWER, 150);

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
