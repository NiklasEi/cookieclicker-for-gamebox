package me.nikl.cookieclicker;

import me.nikl.cookieclicker.buildings.Buildings;
import me.nikl.cookieclicker.upgrades.UpgradeType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Language {
	private Main plugin;
	private FileConfiguration langFile;
	
	public String PREFIX = "[CookieClicker]", NAME = "&1CookieClicker&r";
	public List<String> GAME_HELP, GAME_OVEN_LORE, GAME_PRODUCTION_LORE;
	private YamlConfiguration defaultLang;

	public String GAME_TITLE, GAME_CLOSED
			, GAME_COOKIE_NAME, GAME_OVEN_NAME;
	public String GAME_PAYED, GAME_NOT_ENOUGH_MONEY;

	public HashMap<Buildings, String> buildingName = new HashMap<>();
	public HashMap<Buildings, List<String>> buildingLore = new HashMap<>();

	public HashMap<Integer, String> upgradeName = new HashMap<>();
	public HashMap<Integer, List<String>> upgradeAddLore = new HashMap<>();
	public HashMap<UpgradeType, List<String>> upgradeLore = new HashMap<>();

	public Language(Main plugin){
		this.plugin = plugin;
		getLangFile();
		PREFIX = getString("prefix");
		NAME = getString("name");


		getGameMessages();
		loadBuildingLanguage();
		loadUpgradeLanguage();
	}

	private void loadUpgradeLanguage() {
	}

	private void loadBuildingLanguage() {
		this.GAME_PRODUCTION_LORE = getStringList("buildings.generalLore");
		Buildings building;
		List<String> lore = new ArrayList<>();

		if(langFile.isConfigurationSection("buildings")) {
			for (String key : langFile.getConfigurationSection("buildings").getKeys(false)) {
				try {
					building = Buildings.valueOf(key.toUpperCase());
				} catch (IllegalArgumentException exception) {
					// ignore
					continue;
				}
				lore.clear();
				lore.addAll(GAME_PRODUCTION_LORE);
				buildingName.put(building, getString("buildings." + key + ".name"));
				lore.addAll(getStringList("buildings." + key + ".description"));
				buildingLore.put(building, new ArrayList<>(lore));
			}
		}

		// check for missing language in default file
		for(String key : defaultLang.getConfigurationSection("buildings").getKeys(false)){
			try{
				building = Buildings.valueOf(key.toUpperCase());
			} catch (IllegalArgumentException exception){
				// ignore
				continue;
			}
			if(buildingLore.containsKey(building) && buildingName.containsKey(building)) continue;
			lore.clear();
			lore.addAll(GAME_PRODUCTION_LORE);
			buildingName.put(building, getString("buildings." + key + ".name"));
			lore.addAll(getStringList("buildings." + key + ".description"));
			buildingLore.put(building, new ArrayList<>(lore));
		}
	}

	private void getGameMessages() {
		this.GAME_TITLE = getString("game.inventoryTitles.gameTitle");

		this.GAME_COOKIE_NAME = getString("game.cookieName");

		this.GAME_OVEN_NAME = getString("game.ovenName");
		this.GAME_OVEN_LORE = getStringList("game.ovenLore");

		this.GAME_PAYED = getString("game.econ.payed");
		this.GAME_NOT_ENOUGH_MONEY = getString("game.econ.notEnoughMoney");

		this.GAME_CLOSED = getString("game.closedGame");

		this.GAME_HELP = getStringList("gameHelp");
	}


	private List<String> getStringList(String path) {
		List<String> toReturn;
		if(!langFile.isList(path)){
			toReturn = defaultLang.getStringList(path);
			for(int i = 0; i<toReturn.size(); i++){
				toReturn.set(i, ChatColor.translateAlternateColorCodes('&',toReturn.get(i)));
			}
			return toReturn;
		}
		toReturn = langFile.getStringList(path);
		for(int i = 0; i<toReturn.size(); i++){
			toReturn.set(i, ChatColor.translateAlternateColorCodes('&',toReturn.get(i)));
		}
		return toReturn;
	}

	private String getString(String path) {
		if(!langFile.isString(path)){
			return ChatColor.translateAlternateColorCodes('&',defaultLang.getString(path));
		}
		return ChatColor.translateAlternateColorCodes('&',langFile.getString(path));
	}

	private void getLangFile() {
		try {
			String fileName = "language/lang_en.yml";
			this.defaultLang =  YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource(fileName), "UTF-8"));
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		
		File defaultEn = new File(plugin.getDataFolder().toString() + File.separatorChar + "language" + File.separatorChar + "lang_en.yml");
		if(!defaultEn.exists()){
			defaultEn.getParentFile().mkdirs();
			plugin.saveResource("language" + File.separatorChar + "lang_en.yml", false);
		}
		File defaultDe = new File(plugin.getDataFolder().toString() + File.separatorChar + "language" + File.separatorChar + "lang_de.yml");
		if(!defaultDe.exists()){
			plugin.saveResource("language" + File.separatorChar + "lang_de.yml", false);
		}
		if(!plugin.getConfig().isString("langFile")){
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4*******************************************************"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4Language file is missing in the config!"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4Add the following to your config:"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " langFile: 'lang_en.yml'"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4*******************************************************"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4Using default language file"));
			this.langFile = defaultLang;
			return;
		}
		String fileName = plugin.getConfig().getString("langFile");
		if(fileName.equalsIgnoreCase("default") || fileName.equalsIgnoreCase("default.yml")){
			this.langFile = defaultLang;
			return;
		}
		File languageFile = new File(plugin.getDataFolder().toString() + File.separatorChar + "language" + File.separatorChar + plugin.getConfig().getString("langFile"));
		if(!languageFile.exists()){
			languageFile.mkdir();
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4*******************************************************"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4Language file not found!"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4*******************************************************"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4Using default language file"));
			this.langFile = defaultLang;
			return;
		}
		try { 
			this.langFile = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(languageFile), "UTF-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace(); 
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4*******************************************************"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4Error in language file!"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4*******************************************************"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4Using default language file"));
			this.langFile = defaultLang;
			return;
		}
		int count = 0;
		for(String key : defaultLang.getKeys(true)){
			if(defaultLang.isString(key)){
				if(!this.langFile.isString(key)){// there is a message missing
					if(count == 0){
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*"));
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4Missing message(s) in your language file!"));
					}
					Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " " + key));
					count++;
				}
			} else if (defaultLang.isList(key)){
				if(!this.langFile.isList(key)){// there is a message missing
					if(count == 0){
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*"));
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4Missing message(s) in your language file!"));
					}
					Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " " + key + "     (StringList!)"));
					count++;
				}
			}
		}
		if(count > 0){
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + ""));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4Game will use default messages for these paths"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + ""));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4Please get an updated language file"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4Or add the listed paths by hand"));
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + " &4*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*"));
		}
		return;
		
	}
	
}

