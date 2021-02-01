package ink.rainbowbridge.v1.arathoth.commands.sub;

import ink.rainbowbridge.v1.arathoth.ALocale;
import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.AttributeManager;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothCondition;
import ink.rainbowbridge.v1.arathoth.commands.SubCommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @Author 寒雨
 * @Since 2021/1/26 5:32
 */
public class Reload implements SubCommandExecutor {
    @Override
    public boolean command(CommandSender sender, String[] args) {
        long time = System.currentTimeMillis();
        ArathothI.getInstance().load();
        for (ArathothAttribute attr : AttributeManager.Registered.keySet()){
            attr.load();
        }
        for (ArathothCondition condition : AttributeManager.RegisteredCondition.keySet()){
            condition.load();
        }
        String timestr = "&7&l[&8&l"+(System.currentTimeMillis()-time)+"&7&l]";
        ArathothI.send(sender, ALocale.getString("Command.Reload").replace("{time}",timestr));
        return true;
    }
}
