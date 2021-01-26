package ink.rainbowbridge.v1.arathoth.commands.sub;

import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.commands.SubCommandExecutor;
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
            ((Player) sender).sendTitle("Arathoth I", "by.寒雨", 20, 40, 20);
        }
        ArathothI.send( sender, "&fHologram Display");
        ArathothI.send( sender, "&f&lArathoth &7- &8&lI");
        ArathothI.send( sender, "Version: &f"+ArathothI.getInstance().getDescription().getVersion());
        return true;
    }
}
