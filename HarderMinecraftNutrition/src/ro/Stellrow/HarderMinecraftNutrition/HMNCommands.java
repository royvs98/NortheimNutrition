package ro.Stellrow.HarderMinecraftNutrition;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ro.Stellrow.HarderMinecraftNutrition.utils.Utils;

public class HMNCommands implements CommandExecutor {
    private final HMNutrition pl;

    public HMNCommands(HMNutrition pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length==1&&args[0].equalsIgnoreCase("reload")){
            if (sender.hasPermission("hm.nutrition")){
                pl.reloadConfig();
                pl.messagesManager.loadMessages(pl.getConfig());
                sender.sendMessage(Utils.asColor("&a[Nutrition]Reloaded config"));
                return true;
            }
            return true;
        }
        if (sender instanceof Player){
            Player player = (Player) sender;
            NutritionData data = pl.nutritionManager.getNutrition(player.getUniqueId());
            player.sendMessage(Utils.asColor("&7----[&aNutrition&7]----"));
            player.sendMessage(Utils.asColor("&7Protein: &c"+data.getProtein()));
            player.sendMessage(Utils.asColor("&7Carbs: &6"+data.getCarbs()));
            player.sendMessage(Utils.asColor("&7Vitamins: &d"+data.getVitamins()));
        }
        return true;
    }
}
