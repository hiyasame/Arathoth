package ink.rainbowbridge.v1.arathoth.commands.sub;

import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.commands.SubCommandExecutor;
import ink.rainbowbridge.v1.arathoth.utils.NmsUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @Author 寒雨
 * @Since 2021/1/26 5:49
 */
public class About implements SubCommandExecutor {
    @Override
    public boolean command(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            NmsUtils.sendTitle((Player) sender,"&8&lArathothI",20,60,20,"&7&lby.&9&l寒雨",20,60,20);
        }
        ArathothI.send( sender, "&fHologram Display");
        ArathothI.send( sender, "&f&lArathoth &7- &8&lI");
        ArathothI.send( sender, "Version: &f"+ArathothI.getInstance().getDescription().getVersion());
        return true;
    }
}
