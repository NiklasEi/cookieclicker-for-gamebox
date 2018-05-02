package me.nikl.cookieclicker.commands;

import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.data.GameSave;
import me.nikl.gamebox.GameBox;
import me.nikl.gamebox.commands.admin.GameAdminCommand;
import me.nikl.gamebox.common.acf.annotation.CommandAlias;
import me.nikl.gamebox.common.acf.annotation.Subcommand;
import me.nikl.gamebox.data.database.DataBase;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

/**
 * @author Niklas Eicker
 */
@CommandAlias("gba game")
public class ManipulateCookies extends GameAdminCommand {
    private CookieClicker cookieClicker;

    public ManipulateCookies(GameBox gameBox, CookieClicker cookieClicker) {
        super(gameBox, cookieClicker.getModule());
        this.cookieClicker = cookieClicker;
    }

    @Subcommand("cc give")
    public void giveCookiesTo(CommandSender sender, String gameID, OfflinePlayer offlinePlayer, int count) {
        cookieClicker.getDatabase().getGameSave(offlinePlayer.getUniqueId(), gameID, new DataBase.Callback<GameSave>() {
            @Override
            public void onSuccess(GameSave gameSave) {
                GameSave.Builder builder = new GameSave.Builder(offlinePlayer.getUniqueId(), gameID);
                builder.setUpgrades(gameSave.getUpgrades());
                builder.setCookiesCurrent(gameSave.getCookies().get(GameSave.CURRENT) + count);
                builder.setCookiesClicked(gameSave.getCookies().get(GameSave.CLICKED));
                builder.setCookiesTotal(gameSave.getCookies().get(GameSave.TOTAL));
                for (Buildings buildingType : gameSave.getBuildings().keySet()) {
                    builder.addBuilding(buildingType, gameSave.getBuildings().get(buildingType));
                }
                cookieClicker.getDatabase().saveGame(builder.build(), true);
                sender.sendMessage("Added " + count + " cookies to " + offlinePlayer.getName() + "'s " + gameID + " save.");
            }

            @Override
            public void onFailure(Throwable throwable, GameSave gameSave) {
                sender.sendMessage("Failed to find " + offlinePlayer.getName() + "'s " + gameID + " save.");
            }
        });
    }

    @Subcommand("test")
    public void giveCookiesTo(CommandSender sender) {
        sender.sendMessage("Test echo");
    }
}
