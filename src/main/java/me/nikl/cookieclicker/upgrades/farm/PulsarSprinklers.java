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
public class PulsarSprinklers extends Upgrade {

    public PulsarSprinklers(CookieClicker game) {
        super(game, 193);
        this.cost = 550000000000.;
        productionsRequirements.put(Buildings.FARM, 150);

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
