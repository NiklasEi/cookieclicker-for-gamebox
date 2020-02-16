package me.nikl.cookieclicker.upgrades.temple;

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
public class EnlargedPantheon extends Upgrade {

    public EnlargedPantheon(CookieClicker game) {
        super(game, 242);
        this.cost = 100000000000000.;
        productionsRequirements.put(Buildings.TEMPLE, 100);

        icon = new ItemStack(Material.ENCHANTING_TABLE, 1);
        icon.setAmount(1);

        loadLanguage(UpgradeType.CLASSIC, Buildings.TEMPLE);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.TEMPLE).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.TEMPLE).visualize(game);
    }


}
