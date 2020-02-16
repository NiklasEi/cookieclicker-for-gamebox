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
public class BillionFingers extends Upgrade {

    public BillionFingers(CookieClicker game) {
        super(game, 5);
        this.cost = 100000000;
        productionsRequirements.put(Buildings.CURSOR, 100);

        icon = new ItemStack(Material.ARROW, 1);
        icon.setAmount(1);

        gain = "+5";
        loadLanguage(UpgradeType.GAIN_MOUSE_AND_OTHER, Buildings.CURSOR);
    }

    @Override
    public void onActivation(CCGame game) {
        for (Buildings buildings : Buildings.values()) {
            if (buildings == Buildings.CURSOR) continue;
            game.addBuildingBonus(Buildings.CURSOR, buildings, 5);
            game.addClickBonus(buildings, 5);
        }
    }


}
