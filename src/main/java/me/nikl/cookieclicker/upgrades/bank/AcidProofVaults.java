package me.nikl.cookieclicker.upgrades.bank;

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
public class AcidProofVaults extends Upgrade {

    public AcidProofVaults(CookieClicker game) {
        super(game, 234);
        this.cost = 700000000;
        productionsRequirements.put(Buildings.BANK, 25);

        icon = new MaterialData(Material.GOLD_NUGGET).toItemStack();
        icon.setAmount(1);

        loadLanguage(UpgradeType.CLASSIC, Buildings.BANK);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.BANK).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.BANK).visualize(game);
    }


}
