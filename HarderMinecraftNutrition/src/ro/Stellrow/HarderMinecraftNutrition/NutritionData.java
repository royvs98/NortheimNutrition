package ro.Stellrow.HarderMinecraftNutrition;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ro.Stellrow.HarderMinecraftNutrition.utils.CustomConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NutritionData {
    private final UUID uuid;
    private int protein;
    private int carbs;
    private int vitamins;
    private final CustomConfig config;
    private List<Material> lastFood = new ArrayList<>();

    public NutritionData(UUID uuid, int protein, int carbs, int vitamins, CustomConfig config) {
        this.uuid = uuid;
        this.protein = protein;
        this.carbs = carbs;
        this.vitamins = vitamins;
        this.config = config;
    }
    public NutritionData(UUID uuid, int protein, int carbs, int vitamins, CustomConfig config,List<Material> lastFood) {
        this.uuid = uuid;
        this.protein = protein;
        this.carbs = carbs;
        this.vitamins = vitamins;
        this.config = config;
        this.lastFood = lastFood;
    }

    public void save(JavaPlugin main){
        config.getConfig().set("Nutrition.Protein",protein);
        config.getConfig().set("Nutrition.Carbs",carbs);
        config.getConfig().set("Nutrition.Vitamins",vitamins);
        for (int x = 0;x<lastFood.size();x++){
            config.getConfig().set("LastFood."+x,lastFood.get(x).toString());
        }
        config.saveAsync(main);
    }
    public void saveSync(){
        config.getConfig().set("Nutrition.Protein",protein);
        config.getConfig().set("Nutrition.Carbs",carbs);
        config.getConfig().set("Nutrition.Vitamins",vitamins);
        for (int x = 0;x<lastFood.size();x++){
            config.getConfig().set("LastFood."+x,lastFood.get(x).toString());
        }
        config.save();
    }

    public void reduceNutrition(){
        if (protein>=3){
            protein-=3;
        }
        if (carbs>=1){
            carbs-=1;
        }
        if (vitamins>=3){
            vitamins-=3;
        }
    }

    public void addProtein(int amount){
        protein+=amount;
        if (protein>100){
            protein=100;
        }
    }
    public void addCarbs(int amount){
        carbs+=amount;
        if (carbs>100){
            carbs=100;
        }
    }
    public void addVitamins(int amount){
        vitamins+=amount;
        if (vitamins>100){
            vitamins=100;
        }
    }


    public static NutritionData buildData(UUID uuid,CustomConfig config){
        int protein = config.getConfig().getInt("Nutrition.Protein");
        int carbs = config.getConfig().getInt("Nutrition.Carbs");
        int vitamins = config.getConfig().getInt("Nutrition.Vitamins");
        List<Material> lastFood = new ArrayList<>();
        if (config.getConfig().contains("LastFood")){
            for (String s : config.getConfig().getConfigurationSection("LastFood").getKeys(false)){
                lastFood.add(Material.valueOf(config.getString("LastFood."+s)));
            }
        }
        return new NutritionData(uuid,protein,carbs,vitamins,config,lastFood);
    }

    public void setLastFood(List<Material> lastFood) {
        this.lastFood = lastFood;
    }

    public List<Material> getLastFood() {
        return lastFood;
    }
    public void clearLastFood(){
        lastFood.clear();
    }
    public int getProtein() {
        return protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getVitamins() {
        return vitamins;
    }

    public boolean canEat(Material material){
        if (lastFood.contains(material)){
            int amount = 0;
            for (Material m : lastFood){
                if (m==material){
                    amount++;
                }
            }
            if (amount>2){
                return false;
            }
        }
        return true;
    }

    public void addRecentFood(Material type){
        lastFood.add(0,type);
        if (lastFood.size()>5){
            lastFood.remove(4);
        }
    }
}
