package me.nikl.cookieclicker.upgrades.mine;

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
public class CanolaOilWells extends Upgrade {

    public CanolaOilWells(CookieClicker game) {
        super(game, 309);
        this.cost = 6000000000000000000.;
        productionsRequirements.put(Buildings.MINE, 250);

        icon = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        icon.setAmount(1);

        loadLanguage(UpgradeType.CLASSIC, Buildings.MINE);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.MINE).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.MINE).visualize(game);
    }


}
