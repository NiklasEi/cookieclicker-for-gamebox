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
public class TallerTellers extends Upgrade {

    public TallerTellers(CookieClicker game) {
        super(game, 232);
        this.cost = 14000000;
        productionsRequirements.put(Buildings.BANK, 1);

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
