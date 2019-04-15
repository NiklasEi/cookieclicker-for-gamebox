package me.nikl.cookieclicker.boosts;

import me.nikl.calendarevents.CalendarEvent;
import me.nikl.calendarevents.CalendarEvents;
import me.nikl.calendarevents.CalendarEventsApi;
import me.nikl.cookieclicker.CCGameManager;
import me.nikl.cookieclicker.CookieClicker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

/**
 * @author Niklas Eicker
 */
public class CalendarEventsHook extends BukkitRunnable implements Listener {
    private CalendarEvents calendarEvents;
    private CookieClicker cookieClicker;
    private Map<String, Boost> boosts = new HashMap<>();
    private Map<String, Long> boostTimeOuts = new HashMap<>();
    private Set<String> activeBoosts = new HashSet<>();

    public CalendarEventsHook(CookieClicker cookieClicker, ConfigurationSection boostsSection) {
        this.cookieClicker = cookieClicker;
        calendarEvents = (CalendarEvents) Bukkit.getPluginManager().getPlugin("CalendarEvents");
        if(calendarEvents == null || !calendarEvents.isEnabled()){
            Bukkit.getLogger().log(Level.SEVERE, " CalendarEvents is not running!");
            Bukkit.getLogger().log(Level.SEVERE, " It is required to schedule global boosts.");
            Bukkit.getLogger().log(Level.SEVERE, " Remove the configured boosts from the config or install the plugin.");
            return;
        }
        Bukkit.getPluginManager().registerEvents(this, cookieClicker.getGameBox());
        loadBoosts(boostsSection);

        this.runTaskTimer(cookieClicker.getGameBox(), 0, 40);
    }

    private void loadBoosts(ConfigurationSection boostsSection) {
        CalendarEventsApi api = calendarEvents.getApi();
        for (String boost: boostsSection.getKeys(false)) {
            int duration = boostsSection.getInt(boost + ".duration", 0);
            if (duration < 1) {
                cookieClicker.getGameBox().getLogger().warning(" Invalid duration of CC boost " + boost);
                continue;
            }
            double clickingBoost = boostsSection.getDouble(boost + ".clickingBoost", 1.);
            double productionBoost = boostsSection.getDouble(boost + ".productionBoost", 1.);
            if (clickingBoost < 0 || productionBoost < 0) {
                cookieClicker.getGameBox().getLogger().warning(" Invalid multiplier in boost " + boost);
                continue;
            }
            if (!boostsSection.isString(boost + ".timing.occasion") || !boostsSection.isString(boost + ".timing.time")) continue;
            String occasion = boostsSection.getString(boost + ".timing.occasion");
            String time = boostsSection.getString(boost + ".timing.time");
            UUID uuid = UUID.randomUUID();
            if(api.addEvent("CC-boost-" + uuid.toString(), occasion, time)) {
                this.boosts.put("CC-boost-" + uuid.toString(), new Boost("CC-boost-" + uuid.toString(), duration, clickingBoost, productionBoost, boost));
            } else {
                cookieClicker.getGameBox().getLogger().warning(" Failed to schedule boost " + boost);
            }
        }
    }

    @EventHandler
    public void onBoostEvent(CalendarEvent event){
        boolean update = false;
        for (String label : boosts.keySet()) {
            if (event.getLabels().contains(label)) {
                update = true;
                activateBoost(boosts.get(label));
            }
        }
        if (update) ((CCGameManager) cookieClicker.getGameManager()).updateGlobalBoosts();
    }

    private void activateBoost(Boost boost) {
        activeBoosts.add(boost.eventID);
        boostTimeOuts.put(boost.eventID, System.currentTimeMillis() + boost.duration * 60 * 1000);
    }

    public double getCurrentProductionBoost() {
        double factor = 1.;
        for (String boostEventID : activeBoosts) {
            factor = factor * boosts.get(boostEventID).productionBoost;
        }
        return factor;
    }

    public double getCurrentClickingBoost() {
        double factor = 1.;
        for (String boostEventID : activeBoosts) {
            factor = factor * boosts.get(boostEventID).clickingBoost;
        }
        return factor;
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        List<String> toDeactivate = new ArrayList<>();
        // check durations of the boosts for boosts to deactivate
        for (String boostKey : boostTimeOuts.keySet()) {
            if (currentTime > boostTimeOuts.get(boostKey)) {
                toDeactivate.add(boostKey);
            }
        }
        if (toDeactivate.isEmpty()) return;
        for (String key : toDeactivate) {
            activeBoosts.remove(key);
            boostTimeOuts.remove(key);
        }
        ((CCGameManager) cookieClicker.getGameManager()).updateGlobalBoosts();
    }

    protected class Boost {
        String eventID;
        int duration;
        double clickingBoost;
        double productionBoost;
        String key;

        public Boost(String eventID, int duration, double clickingBoost, double productionBoost, String key) {
            this.eventID = eventID;
            this.duration = duration;
            this.clickingBoost = clickingBoost;
            this.productionBoost = productionBoost;
            this.key = key;
        }
    }
}
