package me.nikl.cookieclicker.upgrades.clicking;

import me.nikl.cookieclicker.Game;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import me.nikl.gamebox.util.NumberUtil;
import me.nikl.cookieclicker.upgrades.Upgrade;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by Niklas on 09.07.2017.
 */
public class IronMouse extends Upgrade{

    public IronMouse(Game game) {
        super(game, 76);
        this.cost = 5000000;
        setClickCookieReq(100000);

        icon = new MaterialData(Material.ARROW).toItemStack();
        icon.setAmount(1);

        gain = "+1%";
        loadLanguage(UpgradeType.GAIN_MOUSE_PER_CPS);
    }

    @Override
    public void onActivation() {
        game.cookiesPerClickPerCPS += 0.01;
        active = true;
    }


}
