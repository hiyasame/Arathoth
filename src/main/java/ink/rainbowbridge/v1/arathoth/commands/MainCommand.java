package ink.rainbowbridge.v1.arathoth.commands;

import ink.rainbowbridge.v1.arathoth.ALocale;
import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.commands.sub.About;
import ink.rainbowbridge.v1.arathoth.commands.sub.ListAttr;
import ink.rainbowbridge.v1.arathoth.commands.sub.Reload;
import ink.rainbowbridge.v1.arathoth.commands.sub.StatusInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * 命令,继承自Arathoth-Zero
 * 有小改动
 * @Author 寒雨
 * @Since 2021/1/17 9:10
 */
public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("Arathoth.admin")){
            sender.sendMessage("&f&lArathoth &7- &7&lby.&8&l寒雨".replaceAll("&","§"));
            sender.sendMessage(("&7Version: &f"+ArathothI.getInstance().getDescription().getVersion()).replaceAll("&","§"));
            return true;
        }
        else{
            if(args.length == 0){
                for(String str : ALocale.getStringList("Command.Helper")){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',str.replace("{StatusCommand}",ArathothI.getInstance().getConfig().getString("StatusInfo.Command"))));
                }
            }
            else {
                switch (parse(args[0].toUpperCase())) {
                    case LISTATTR: {
                        return new ListAttr().command(sender,args);
                    }
                    case RELOAD: {
                        return new Reload().command(sender,args);
                    }
                    case STATUSINFO:{
                        return new StatusInfo().command(sender,args);
                    }
                    case ABOUT:{
                        return new About().command(sender, args);
                    }
                    default: {
                        ArathothI.send(sender,ALocale.getString("Command.Error"));
                        return true;
                    }
                }
            }
        }
        return true;
    }


    private enum command {

        LISTATTR, STATUSINFO, RELOAD,

         ABOUT,ERROR
    }

    private command parse(String s) {
        try {
            return command.valueOf(s);
        }
        catch (Exception e) {
            return command.ERROR;
        }
    }
}
