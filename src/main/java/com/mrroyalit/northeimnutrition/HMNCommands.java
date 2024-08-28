package com.mrroyalit.northeimnutrition;

import com.mrroyalit.northeimnutrition.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HMNCommands implements CommandExecutor {
    private final NortheimNutrition pl;

    public HMNCommands(NortheimNutrition pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length==1&&args[0].equalsIgnoreCase("reload")){
            if (sender.hasPermission("hm.nutrition")){
                pl.reloadConfig();
                pl.messagesManager.loadMessages(pl.getConfig());
                sender.sendMessage(Utils.asColor("&a[Gezondheid]Reloaded config"));
                return true;
            }
            return true;
        }
        if (sender instanceof Player){
            Player player = (Player) sender;
            NutritionData data = pl.nutritionManager.getNutrition(player.getUniqueId());
            player.sendMessage(Utils.asColor("&7----[&aGezondheid&7]----"));
            player.sendMessage(Utils.asColor("&7Proteïne: &c"+data.getProtein()));
            player.sendMessage(Utils.asColor("&7Koolhydraten: &6"+data.getCarbs()));
            player.sendMessage(Utils.asColor("&7Vitamines: &d"+data.getVitamins()));
        }
        return true;
    }
}
