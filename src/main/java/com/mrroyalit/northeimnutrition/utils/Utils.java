package com.mrroyalit.northeimnutrition.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String asColor(String toTranslate){
        return ChatColor.translateAlternateColorCodes('&',toTranslate);
    }
    public static List<String> listAsColor(List<String> toTranslate){
        List<String> toReturn = new ArrayList<>();
        for(String s : toTranslate){
            toReturn.add(asColor(s));
        }
        return toReturn;
    }
}
