package me.nikl.cookieclicker.upgrades.curser;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 * @author Niklas Eicker
 */
public class SeptillionFingers extends Upgrade {

    public SeptillionFingers(CookieClicker game) {
        super(game, 188);
        this.cost = 1000000000000000.;
        productionsRequirements.put(Buildings.CURSOR, 350);

        icon = new MaterialData(Material.ARROW).toItemStack();
        icon.setAmount(1);

        gain = "+500000";
        loadLanguage(UpgradeType.GAIN_MOUSE_AND_OTHER, Buildings.CURSOR);
    }

    @Override
    public void onActivation(CCGame game) {
        for (Buildings buildings : Buildings.values()) {
            if (buildings == Buildings.CURSOR) continue;
            game.addBuildingBonus(Buildings.CURSOR, buildings, 500000);
            game.addClickBonus(buildings, 500000);
        }
    }


}
