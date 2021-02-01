package ink.rainbowbridge.v1.arathoth.attribute.sub.conditions;

import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.SlotManager;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothCondition;
import ink.rainbowbridge.v1.arathoth.attribute.events.ArathothUpdateExecuteEvent;
import ink.rainbowbridge.v1.arathoth.utils.ColorUtils;
import ink.rainbowbridge.v1.arathoth.utils.ItemUtils;
import ink.rainbowbridge.v1.arathoth.utils.NameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author 寒雨
 * @Since 2021/1/26 11:14
 */
public class LevelRequired extends ArathothCondition implements Listener {
    @Override
    public FileConfiguration setDefaultConfig(FileConfiguration config) {
        config.set(getName()+".Settings.Message","&7&l[&f&lArathoth&7&l] &7你的等级不足以使用 {item} &7,将不会计入属性计算.");
        return config;
    }

    @Override
    public void onRegistering() {
        Bukkit.getPluginManager().registerEvents(this, ArathothI.getInstance());
    }

    @Override
    public boolean pass(ItemStack item, Player player) {
        String Value = null;
        for (String str : item.getItemMeta().getLore()){
            str = ColorUtils.Uncolor(str);
            for (String pt : getPatterns()){
                Pattern pt1 = Pattern.compile(pt.replace("[VALUE]","(\\d+)"));
                Matcher m  = pt1.matcher(str);
                if (m.find()){
                    Value = m.group(1);
                    break;
                }
            }
            if (Value != null){break;}
        }
        if (Value == null){ return true;}
        int level = Integer.parseInt(Value);
        if (level > player.getLevel()){
            return false;
        }
        return true;
    }

    @EventHandler
    public void onUpdate(ArathothUpdateExecuteEvent event){
        if (event.getExecutor() instanceof Player){
            Player p =(Player)event.getExecutor();
            for(Integer i : SlotManager.getSlots().keySet()){
                if (ItemUtils.isApproveItem(p.getInventory().getItem(i))){
                    if (!pass(p.getInventory().getItem(i),p)){
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString(getName()+".Settings.Message","&7&l[&f&lArathoth&7&l] &7你的等级不足以使用 {item} &7,将不会计入属性计算.").replace("{item}", NameUtils.getItemName(p.getInventory().getItem(i)))));
                    }
                }

            }
            if (ItemUtils.isApproveItem(p.getInventory().getItemInMainHand())){
                if (!pass(p.getInventory().getItemInMainHand(),p)){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString(getName()+".Settings.Message","&7&l[&f&lArathoth&7&l] &7你的等级不足以使用 {item} &7,将不会计入属性计算.").replace("{item}", NameUtils.getItemName(p.getInventory().getItemInMainHand()))));
                }
            }
        }
    }

}
