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
public class DeliciousBlessing extends Upgrade {

    public DeliciousBlessing(CookieClicker game) {
        super(game, 240);
        this.cost = 10000000000.;
        productionsRequirements.put(Buildings.TEMPLE, 25);

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
