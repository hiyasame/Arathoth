package ink.rainbowbridge.v1.arathoth.commands.sub;

import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.AttributeManager;
import ink.rainbowbridge.v1.arathoth.attribute.SlotManager;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothCondition;
import ink.rainbowbridge.v1.arathoth.commands.SubCommandExecutor;
import ink.rainbowbridge.v1.arathoth.utils.SimpleTellrawUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 寒雨
 * @Since 2021/1/26 5:24
 */
public class ListAttr implements SubCommandExecutor {
    @Override
    public boolean command(CommandSender sender, String[] args) {
        if (sender instanceof Player){
            Player p = (Player)sender;
            ArathothI.send(p,"&f&lArathothI &8AttributesList:");
            for (ArathothAttribute attr : AttributeManager.Registered.keySet()){
                List<String> hover = new ArrayList<>();
                hover.add("&8&lArathothAttribute &7- &f&l"+attr.getName());
                hover.add("\n");
                hover.add("\n");
                hover.add("&8isActive: &f"+attr.isEnable());
                hover.add("\n");
                hover.add("&8Plugin: &f"+AttributeManager.Registered.get(attr));
                hover.add("\n");
                hover.add("&8Type: &f"+attr.getType());
                hover.add("\n");
                hover.add("&8Description: &f"+attr.getDescription());
                SimpleTellrawUtils.TellrawJson(p,"&7&l● &8"+attr.getName()+" &7isActive: &f"+attr.isEnable(),hover);
            }
            sender.sendMessage(" ");
            ArathothI.send(p,"&f&lArathothI &8ConditionsList:");
            for (ArathothCondition condition : AttributeManager.RegisteredCondition.keySet()){
                List<String> hover = new ArrayList<>();
                hover.add("&8&lArathothCondition &7- &f&l"+condition.getName());
                hover.add("\n");
                hover.add("\n");
                hover.add("&8isActive: &f"+condition.isEnable());
                hover.add("\n");
                hover.add("&8Plugin: &f"+AttributeManager.RegisteredCondition.get(condition));
                hover.add("\n");
                hover.add("&8Description: &f"+condition.getDescription());
                SimpleTellrawUtils.TellrawJson(p,"&7&l● &8"+condition.getName()+" &7isActive: &f"+condition.isEnable(),hover);
            }
            sender.sendMessage(" ");
            ArathothI.send(p,"&f&lArathothI &8SlotsList:");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7&l● " + "&8Location:&f MainHand" + " &8Name: &f" + SlotManager.getMainKey()));
            if(!SlotManager.getSlots().isEmpty()){
                for(Integer i : SlotManager.getSlots().keySet()) {
                    sender.sendMessage(("&7&l● " + "&8Location:&f " + i + " &8Name: &f" + SlotManager.getSlots().get(i)).replaceAll("&", "§"));
                }
            }
        return true;
        }
        else{
            ArathothI.send(sender,"&f&lArathothI &8AttributesList:");
            for (ArathothAttribute attr : AttributeManager.Registered.keySet()){
                ArathothI.send(sender,"&7&l● &8"+attr.getName()+" &7isActive: &f"+attr.isEnable());
            }
            sender.sendMessage(" ");
            ArathothI.send(sender,"&f&lArathothI &8ConditionsList:");
            for (ArathothCondition condition : AttributeManager.RegisteredCondition.keySet()){
                ArathothI.send(sender,"&7&l● &8"+condition.getName()+" &7isActive: &f"+condition.isEnable());
            }
            sender.sendMessage(" ");
            ArathothI.send(sender,"&f&lArathothI &8SlotsList:");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&7&l● " + "&8Location:&f MainHand" + " &8Name: &f" + SlotManager.getMainKey()));
            if(!SlotManager.getSlots().isEmpty()){
                for(Integer i : SlotManager.getSlots().keySet()) {
                    sender.sendMessage(("&7&l● " + "&8Location:&f " + i + " &8Name: &f" + SlotManager.getSlots().get(i)).replaceAll("&", "§"));
                }
            }
            return true;
        }
    }
}
