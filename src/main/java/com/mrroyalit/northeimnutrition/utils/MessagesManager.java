package com.mrroyalit.northeimnutrition.utils;

import org.bukkit.configuration.file.FileConfiguration;

public class MessagesManager {
    public boolean doActionBarMessage;

    public String eatSomethingElse;
    public String needProteins;
    public String needCarbs;
    public String needVitamins;

    public String addProtein;
    public String addCarbs;
    public String addVitamins;

    public void loadMessages(FileConfiguration configuration){
        doActionBarMessage = configuration.getBoolean("General.enableActionBarMessages");

        eatSomethingElse = Utils.asColor(configuration.getString("Messages.needOtherFood","&7I should eat something else!"));
        needProteins = Utils.asColor(configuration.getString("Messages.needProtein","&7I could really use some meat"));
        needCarbs = Utils.asColor(configuration.getString("Messages.needCarbs","&7I would love some form of bread"));
        needVitamins = Utils.asColor(configuration.getString("Messages.needVitamins","&7I really want to eat something green"));

        addProtein = Utils.asColor(configuration.getString("Messages.addProteinActionBar","&c+10 Protein"));
        addCarbs = Utils.asColor(configuration.getString("Messages.addCarbsActionBar","&6+10 Carbs"));
        addVitamins = Utils.asColor(configuration.getString("Messages.addVitaminsActionBar","&d+10 Vitamins"));
    }
}
