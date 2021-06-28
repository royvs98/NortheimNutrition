package ro.Stellrow.HarderMinecraftNutrition;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ro.Stellrow.HarderMinecraftNutrition.utils.CustomConfig;

import java.util.HashMap;
import java.util.UUID;

public class NutritionManager {
    private final HMNutrition pl;

    public NutritionManager(HMNutrition pl) {
        this.pl = pl;
    }


    private HashMap<UUID,NutritionData> playerNutritions = new HashMap<>();

    public void addNutrition(UUID uuid,NutritionData nutritionData){
        playerNutritions.put(uuid,nutritionData);
    }
    public void removeNutrition(UUID uuid){
        if (playerNutritions.containsKey(uuid)) {
            playerNutritions.get(uuid).save(pl);
            playerNutritions.remove(uuid);
        }
    }
    public void removeNutritionSync(Player player){
        if (playerNutritions.containsKey(player.getUniqueId())){
            playerNutritions.get(player.getUniqueId()).saveSync();
            playerNutritions.remove(player.getUniqueId());
        }
    }
    public NutritionData getNutrition(UUID uuid){
        return playerNutritions.get(uuid);
    }


    public void loadTimers(){
        reduceNutrition();
        checkNutrition();
    }
    //Reduce points off nutrition every 3 minutes
    private void reduceNutrition(){
        Bukkit.getScheduler().runTaskTimer(pl,()->{
            for (UUID uuid : playerNutritions.keySet()){
                playerNutritions.get(uuid).reduceNutrition();
                checkStats(uuid);
            }
        },0,20*3*60);
    }
    //Apply effects(check each 10 seconds)
    private void checkNutrition(){
        Bukkit.getScheduler().runTaskTimer(pl,()->{
            for (UUID uuid : playerNutritions.keySet()){
                NutritionData data = playerNutritions.get(uuid);
                if (data.getProtein()<20){
                    addEffect(uuid,PotionEffectType.WEAKNESS);
                }
                if (data.getCarbs()<20){
                    addEffect(uuid,PotionEffectType.SLOW);
                }
                if (data.getVitamins()<20){
                    addEffect(uuid,PotionEffectType.SLOW_DIGGING);
                }
            }
        },0,20*10);
    }
    private void addEffect(UUID uuid, PotionEffectType type){
        Player p = Bukkit.getPlayer(uuid);
        if (p==null){
            return;
        }
        p.addPotionEffect(new PotionEffect(type,20*10,0));
    }

    public void loadOrCreateData(Player player){
        CustomConfig config = new CustomConfig("player-data",player.getUniqueId().toString(),pl);
        if (!config.getConfig().contains("Nutrition")){
            config.getConfig().set("Nutrition.Protein",100);
            config.getConfig().set("Nutrition.Carbs",100);
            config.getConfig().set("Nutrition.Vitamins",100);
            config.save();
        }
        //Pass config to the manager
        addNutrition(player.getUniqueId(),NutritionData.buildData(player.getUniqueId(),config));
    }
    private void checkStats(UUID uuid){
        Player p = Bukkit.getPlayer(uuid);
        if (p!=null) {
            NutritionData data = getNutrition(uuid);
            if (data==null){
                return;
            }
            if (data.getProtein() < 30) {
                p.sendMessage(pl.messagesManager.needProteins);
            }
            if (data.getCarbs() < 30){
                p.sendMessage(pl.messagesManager.needCarbs);
            }
            if (data.getVitamins() < 30){
                p.sendMessage(pl.messagesManager.needVitamins);
            }
        }
    }

}
