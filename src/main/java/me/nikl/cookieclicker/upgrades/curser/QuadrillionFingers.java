package me.nikl.cookieclicker.upgrades.curser;

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
public class QuadrillionFingers extends Upgrade {

    public QuadrillionFingers(CookieClicker game) {
        super(game, 43);
        this.cost = 10000000000.;
        productionsRequirements.put(Buildings.CURSOR, 200);

        icon = new ItemStack(Material.ARROW, 1);
        icon.setAmount(1);

        gain = "+500";
        loadLanguage(UpgradeType.GAIN_MOUSE_AND_OTHER, Buildings.CURSOR);
    }

    @Override
    public void onActivation(CCGame game) {
        for (Buildings buildings : Buildings.values()) {
            if (buildings == Buildings.CURSOR) continue;
            game.addBuildingBonus(Buildings.CURSOR, buildings, 500);
            game.addClickBonus(buildings, 500);
        }
    }


}
