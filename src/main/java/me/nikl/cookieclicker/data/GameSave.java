package me.nikl.cookieclicker.data;

import me.nikl.cookieclicker.buildings.Buildings;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Niklas Eicker
 */
public class GameSave {
    public static final String CURRENT = "current";
    public static final String CLICKED = "click";
    public static final String TOTAL = "total";

    private final UUID uuid;
    private final String gameType;
    private final Map<String, Double> cookies;
    private final List<Integer> upgrades;
    private final Map<Buildings, Integer> buildings;

    private GameSave(Builder builder) {
        this.uuid = builder.uuid;
        this.gameType = builder.gameType;
        this.cookies = builder.cookies;
        this.upgrades = builder.upgrades;
        this.buildings = builder.buildings;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Map<String, Double> getCookies() {
        return cookies;
    }

    public List<Integer> getUpgrades() {
        return upgrades;
    }

    public String getUpgradesAsString() {
        return String.join(",", upgrades.stream().map(String::valueOf).collect(Collectors.toList()));
    }

    public Map<Buildings, Integer> getBuildings() {
        return buildings;
    }

    public String getGameType() {
        return gameType;
    }

    public static class Builder {
        private final UUID uuid;
        private final String gameType;
        private Map<String, Double> cookies = new HashMap<>();
        private List<Integer> upgrades = new ArrayList<>();
        private final Map<Buildings, Integer> buildings = new HashMap<>();

        public Builder(UUID player, String gameType) {
            this.uuid = player;
            this.gameType = gameType;
        }

        public Builder setCookiesTotal(double cookiesTotal) {
            cookies.put(TOTAL, cookiesTotal);
            return this;
        }

        public Builder addBuilding(Buildings building, int count) {
            buildings.put(building, count);
            return this;
        }

        public Builder setCookiesClicked(double cookiesClicked) {
            cookies.put(CLICKED, cookiesClicked);
            return this;
        }

        public Builder setCookiesCurrent(double cookiesCurrent) {
            cookies.put(CURRENT, cookiesCurrent);
            return this;
        }

        public Builder setUpgrades(String upgrades) {
            if (upgrades == null || upgrades.isEmpty()) return this;
            String[] split = upgrades.split(",");
            if (split.length < 1) return this;
            try {
                for (String upgrade : split) {
                    this.upgrades.add(Integer.valueOf(upgrade));
                }
            } catch (NumberFormatException e) {
                Bukkit.getLogger().warning("Error while loading a cookie clicker save!");
                e.printStackTrace();
            }
            return this;
        }

        public Builder setUpgrades(List<Integer> upgrades) {
            this.upgrades = upgrades;
            return this;
        }

        public GameSave build() {
            cookies.putIfAbsent(CURRENT, 0.);
            cookies.putIfAbsent(TOTAL, 0.);
            cookies.putIfAbsent(CLICKED, 0.);
            return new GameSave(this);
        }
    }
}
