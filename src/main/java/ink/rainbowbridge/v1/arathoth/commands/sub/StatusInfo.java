package ink.rainbowbridge.v1.arathoth.commands.sub;

import ink.rainbowbridge.v1.arathoth.ALocale;
import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.commands.SubCommandExecutor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 继承自Arathoth-Zero
 * @Author 寒雨
 * @Since 2021/1/26 5:42
 */
public class StatusInfo implements SubCommandExecutor {
    @Override
    public boolean command(CommandSender sender, String[] args) {
        if (args.length == 2) {
            try {
                Player p = Bukkit.getPlayer(args[1]);
                if (ArathothI.getInstance().getConfig().getStringList("StatusInfo.Messages") == null) {
                    ArathothI.send(p, "No status messages,please set it in config.yml!");
                } else {
                    for(String str : ArathothI.getInstance().getConfig().getStringList("StatusInfo.Messages")){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(p,str)));
                    }
                }
            } catch (NullPointerException e) {
                ArathothI.send(sender, ALocale.getString("Command.StatusInfo.NOT-ONLINE"));
                return true;
            }
        }
        else {
            ArathothI.send(sender,ALocale.getString("Command.Error"));
        }
        return true;
    }
    }
