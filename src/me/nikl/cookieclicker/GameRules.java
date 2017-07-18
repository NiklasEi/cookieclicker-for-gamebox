package me.nikl.cookieclicker;

/**
 * Created by Niklas
 *
 * Game rules container for Whac a mole
 */
public class GameRules {

    private double cost;
    private boolean saveStats;
    private String key;

    public GameRules(Main plugin, String key, double cost, boolean saveStats){
        this.cost = cost;
        this.saveStats = saveStats;
        this.key = key;
    }



    public double getCost() {
        return cost;
    }

    public boolean isSaveStats() {
        return saveStats;
    }

    public String getKey() {
        return key;
    }
}
