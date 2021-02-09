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
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author 寒雨
 * @Since 2021/1/27 1:14
 */
public class OwnderRequest extends ArathothCondition implements Listener {
    @Override
    public FileConfiguration setDefaultConfig(FileConfiguration config) {
        config.set(getName()+".Settings.PickLimit",true);
        config.set(getName()+".Settings.PickMessage","&7&l[&f&lArathoth&7&l] &7这件物品不属于你，无法拾取.");
        config.set(getName()+".Settings.Message","&7&l[&f&lArathoth&7&l] &7物品 {item} &7不属于你,将不会计入属性计算.");
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
                Pattern pt1 = Pattern.compile(pt.replace("[VALUE]","(\\S+)"));
                Matcher m  = pt1.matcher(str);
                if (m.find()){
                    Value = m.group(1);
                    break;
                }
            }
            if (Value != null){break;}
        }
        if (Value == null){ return true;}
        if (!Value.equals(player.getName())){
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
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString(getName()+".Settings.Message","&7&l[&f&lArathoth&7&l] &7物品 {item} &7不属于你,将不会计入属性计算.").replace("{item}", NameUtils.getItemName(p.getInventory().getItem(i)))));
                    }
                }

            }
            if (ItemUtils.isApproveItem(p.getInventory().getItemInMainHand())){
                if (!pass(p.getInventory().getItemInMainHand(),p)){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',getConfig().getString(getName()+".Settings.Message","&7&l[&f&lArathoth&7&l] &7物品 {item} &7不属于你,将不会计入属性计算.").replace("{item}", NameUtils.getItemName(p.getInventory().getItemInMainHand()))));
                }
            }
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e){
        if (e.getEntity() instanceof Player){
            Player p = (Player)e.getEntity();
            if (getConfig().getBoolean(getName()+".Settings.PickLimit",true)){
                if (ItemUtils.isApproveItem(e.getItem().getItemStack())) {
                    if (!pass(e.getItem().getItemStack(), p)) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("&7&l[&f&lArathoth&7&l] &7这件物品不属于你，无法拾取.")));
                        e.setCancelled(true);
                     }
                }
            }
        }
    }
    @Override
    public String getDescription() {
        return "灵魂绑定";
    }
}
