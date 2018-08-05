package me.nikl.cookieclicker.commands;

import me.nikl.cookieclicker.CCGameManager;
import me.nikl.cookieclicker.CookieClicker;
import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.data.GameSave;
import me.nikl.gamebox.GameBox;
import me.nikl.gamebox.commands.admin.GameAdminCommand;
import me.nikl.gamebox.common.acf.annotation.CommandAlias;
import me.nikl.gamebox.common.acf.annotation.CommandCompletion;
import me.nikl.gamebox.common.acf.annotation.Subcommand;
import me.nikl.gamebox.common.acf.annotation.Syntax;
import me.nikl.gamebox.data.database.DataBase;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

/**
 * @author Niklas Eicker
 */
@CommandAlias("%adminCommand")
public class ManipulateCookies extends GameAdminCommand {
    private CookieClicker cookieClicker;

    public ManipulateCookies(GameBox gameBox, CookieClicker cookieClicker) {
        super(gameBox, cookieClicker.getModule());
        this.cookieClicker = cookieClicker;
    }

    @Subcommand("game %cc_subcommands give")
    @CommandCompletion("@players %cc_game_ids 10|500|1000")
    @Syntax("<player> [%cc_game_ids] <count (int)>")
    public void giveCookiesTo(CommandSender sender, OfflinePlayer offlinePlayer, String gameID, int count) {
        if (!cookieClicker.getGameManager().getGameRules().keySet().contains(gameID)) {
            sender.sendMessage(cookieClicker.getGameLang().PREFIX + " Unknown game type '" + gameID + "'!");
            sender.sendMessage(cookieClicker.getGameLang().PREFIX + " Possible options: " + String.join(", ",cookieClicker.getGameManager().getGameRules().keySet()));
            return;
        }
        cookieClicker.getDatabase().getGameSave(offlinePlayer.getUniqueId(), gameID, new DataBase.Callback<GameSave>() {
            @Override
            public void onSuccess(GameSave gameSave) {
                GameSave.Builder builder = new GameSave.Builder(offlinePlayer.getUniqueId(), gameID);
                builder.setUpgrades(gameSave.getUpgrades());
                builder.setCookiesCurrent(gameSave.getCookies().get(GameSave.CURRENT) + count);
                builder.setCookiesClicked(gameSave.getCookies().get(GameSave.CLICKED));
                builder.setCookiesTotal(gameSave.getCookies().get(GameSave.TOTAL) + count);
                for (Buildings buildingType : gameSave.getBuildings().keySet()) {
                    builder.addBuilding(buildingType, gameSave.getBuildings().get(buildingType));
                }
                final GameSave newSave = builder.build();
                cookieClicker.getDatabase().saveGame(newSave, true);
                ((CCGameManager)cookieClicker.getGameManager()).saveStatistics(newSave, true);
                sender.sendMessage(cookieClicker.getGameLang().PREFIX + " Added " + count + " cookies to " + offlinePlayer.getName() + "'s " + gameID + " save.");
            }

            @Override
            public void onFailure(Throwable throwable, GameSave gameSave) {
                sender.sendMessage(cookieClicker.getGameLang().PREFIX + " Failed to find " + offlinePlayer.getName() + "'s " + gameID + " save.");
            }
        });
    }
}
