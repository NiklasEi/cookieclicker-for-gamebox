package me.nikl.cookieclicker.upgrades.grandma;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * @author Niklas Eicker
 */
public class XtremeWalkers extends Upgrade {

    public XtremeWalkers(CookieClicker game) {
        super(game, 294);
        this.cost = 50000000000000.;
        productionsRequirements.put(Buildings.GRANDMA, 200);

        icon = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        icon.setAmount(1);
        SkullMeta skullMeta = (SkullMeta) icon.getItemMeta();
        skullMeta.setOwner("MHF_Villager");
        icon.setItemMeta(skullMeta);

        loadLanguage(UpgradeType.CLASSIC, Buildings.GRANDMA);
    }

    @Override
    public void onActivation(CCGame game) {
        game.getBuilding(Buildings.GRANDMA).multiply(game.getGameUuid(), 2);
        game.getBuilding(Buildings.GRANDMA).visualize(game);
    }


}
