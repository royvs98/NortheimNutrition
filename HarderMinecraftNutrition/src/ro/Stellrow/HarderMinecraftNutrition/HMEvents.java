package ro.Stellrow.HarderMinecraftNutrition;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ro.Stellrow.HarderMinecraftNutrition.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class HMEvents implements Listener {
    private final HMNutrition pl;
    private final List<Material> proteinList = Arrays.asList(Material.COOKED_CHICKEN,Material.COOKED_COD,Material.COOKED_MUTTON,Material.COOKED_PORKCHOP,Material.COOKED_RABBIT,
            Material.COOKED_SALMON,Material.MUSHROOM_STEM,Material.PUMPKIN_PIE,Material.RABBIT_STEW,Material.BEEF,Material.CHICKEN,Material.COD,Material.MUTTON,
            Material.PORKCHOP,Material.ROTTEN_FLESH,Material.SALMON,Material.RABBIT,Material.COOKED_BEEF);
    private final List<Material> carbsList = Arrays.asList(Material.BAKED_POTATO,Material.BEETROOT,Material.BEETROOT_SOUP,Material.BREAD,Material.POTATO);
    private final List<Material> vitamins = Arrays.asList(Material.APPLE, Material.CARROT,Material.DRIED_KELP,Material.GOLDEN_APPLE,Material.GOLDEN_CARROT,Material.MELON_SLICE,
            Material.SWEET_BERRIES);
    private final List<Material> ignoreBlocks = Arrays.asList(Material.CHEST,Material.FURNACE,Material.BARREL,Material.DISPENSER,Material.CRAFTING_TABLE);

    public HMEvents(HMNutrition pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        pl.nutritionManager.loadOrCreateData(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        pl.nutritionManager.removeNutrition(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        Material type = event.getItem().getType();
        if (proteinList.contains(type)){
            NutritionData data = pl.nutritionManager.getNutrition(uuid);
            if (!data.canEat(type)){
                event.setCancelled(true);
                event.getPlayer().sendMessage(pl.messagesManager.eatSomethingElse);
                return;
            }
            data.addRecentFood(type);
            data.addProtein(10);
            if (pl.messagesManager.doActionBarMessage) {
                event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(pl.messagesManager.addProtein));
            }
        }
        if (carbsList.contains(type)){
            NutritionData data = pl.nutritionManager.getNutrition(uuid);
            if (!data.canEat(type)){
                event.setCancelled(true);
                event.getPlayer().sendMessage(pl.messagesManager.eatSomethingElse);
                return;
            }
            data.addRecentFood(type);
            data.addCarbs(10);
            if (pl.messagesManager.doActionBarMessage) {
                event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(pl.messagesManager.addCarbs));
            }
        }
        if (vitamins.contains(type)){
            NutritionData data = pl.nutritionManager.getNutrition(uuid);
            if (!data.canEat(type)){
                event.setCancelled(true);
                event.getPlayer().sendMessage(pl.messagesManager.eatSomethingElse);
                return;
            }
            data.addRecentFood(type);
            data.addVitamins(10);
            if (pl.messagesManager.doActionBarMessage) {
                event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(pl.messagesManager.addVitamins));
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        NutritionData data = pl.nutritionManager.getNutrition(player.getUniqueId());
        data.addProtein(100);
        data.addCarbs(100);
        data.addVitamins(100);
        data.clearLastFood();
    }


}
