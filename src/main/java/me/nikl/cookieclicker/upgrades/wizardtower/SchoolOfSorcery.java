package me.nikl.cookieclicker.upgrades.wizardtower;

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
public class SchoolOfSorcery extends Upgrade {

    public SchoolOfSorcery(CookieClicker game) {
        super(game, 248);
        this.cost = 1650000000000000.;
        productionsRequirements.put(Buildings.WIZARD_TOWER, 100);

        icon = new MaterialData(Material.BLAZE_ROD).toItemStack();
        icon.setAmount(1);

        loadLanguage(UpgradeType.CLASSIC, Buildings.WIZARD_TOWER);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.WIZARD_TOWER).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.WIZARD_TOWER).visualize(game);
    }


}
