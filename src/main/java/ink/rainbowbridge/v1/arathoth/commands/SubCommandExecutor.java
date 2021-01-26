package ink.rainbowbridge.v1.arathoth.commands;

import org.bukkit.command.CommandSender;

/**
 * @Author 寒雨
 * @Since 2021/1/17 9:31
 */
public interface SubCommandExecutor {
    boolean command(CommandSender sender, String[] args);
}
