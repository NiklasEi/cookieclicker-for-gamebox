package me.nikl.cookieclicker.upgrades.clicking;

import me.nikl.cookieclicker.CCGame;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.upgrades.Upgrade;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Niklas Eicker
 */
public class UnobtainiumMouse extends Upgrade {

    public UnobtainiumMouse(CookieClicker game) {
        super(game, 119);
        this.cost = 5000000000000.;
        setClickCookieReq(100000000000.);

        icon = new ItemStack(Material.ARROW, 1);
        icon.setAmount(1);

        gain = "+1%";
        loadLanguage(UpgradeType.GAIN_MOUSE_PER_CPS);
    }

    @Override
    public void onActivation(CCGame game) {
        game.cookiesPerClickPerCPS += 0.01;
    }


}
