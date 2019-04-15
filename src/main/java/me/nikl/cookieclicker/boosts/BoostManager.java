package me.nikl.cookieclicker.boosts;

import me.nikl.cookieclicker.CookieClicker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

/**
 * @author Niklas Eicker
 */
public class BoostManager {
    private CookieClicker cookieClicker;
    private CalendarEventsHook calendarEventsHook;

    public BoostManager(CookieClicker cookieClicker) {
        this.cookieClicker = cookieClicker;
        loadGlobalBoosts();
    }

    private void loadGlobalBoosts() {
        if (!cookieClicker.getConfig().isConfigurationSection("globalBoosts")) return;
        ConfigurationSection globalBoostersSection =  cookieClicker.getConfig().getConfigurationSection("globalBoosts");
        if (!globalBoostersSection.getBoolean("active", false)) return;
        if (!globalBoostersSection.isConfigurationSection("boosts")) return;
        ConfigurationSection boostsSection =  globalBoostersSection.getConfigurationSection("boosts");

       Plugin calendarEvents = Bukkit.getPluginManager().getPlugin("CalendarEvents");
        if(calendarEvents == null || !calendarEvents.isEnabled()){
            cookieClicker.getGameBox().getLogger().log(Level.SEVERE, cookieClicker.getGameLang().PLAIN_PREFIX + " CalendarEvents is not running!");
            cookieClicker.getGameBox().getLogger().log(Level.SEVERE, cookieClicker.getGameLang().PLAIN_PREFIX + " It is required to schedule global boosts.");
            cookieClicker.getGameBox().getLogger().log(Level.SEVERE, cookieClicker.getGameLang().PLAIN_PREFIX + " Remove the boosts from the config, deactivate them or install CalendarEvents.");
            return;
        }
        calendarEventsHook = new CalendarEventsHook(cookieClicker, boostsSection);
    }

    public double getCurrentProductionBoost() {
        return calendarEventsHook != null ? calendarEventsHook.getCurrentProductionBoost() : 1.;
    }

    public double getCurrentClickingBoost() {
        return calendarEventsHook != null ? calendarEventsHook.getCurrentClickingBoost() : 1.;
    }
}
