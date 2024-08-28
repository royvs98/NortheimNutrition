package com.mrroyalit.northeimnutrition;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.mrroyalit.northeimnutrition.utils.MessagesManager;

public class NortheimNutrition extends JavaPlugin {
    public NutritionManager nutritionManager = new NutritionManager(this);
    public MessagesManager messagesManager = new MessagesManager();

    public void onEnable(){
        loadConfig();
        messagesManager.loadMessages(getConfig());
        nutritionManager.loadTimers();
        getServer().getPluginManager().registerEvents(new HMEvents(this),this);
        getCommand("nutrition").setExecutor(new HMNCommands(this));
        loadPlayers();
    }
    public void onDisable(){
        savePlayersSync();
    }

    private void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }


    private void loadPlayers(){
        for (Player p : Bukkit.getOnlinePlayers()){
            nutritionManager.loadOrCreateData(p);
        }
    }
    private void savePlayersSync(){
        for (Player p : Bukkit.getOnlinePlayers()){
            nutritionManager.removeNutritionSync(p);
        }
    }




}
