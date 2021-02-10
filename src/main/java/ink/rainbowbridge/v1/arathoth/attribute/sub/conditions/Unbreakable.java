package ink.rainbowbridge.v1.arathoth.attribute.sub.conditions;

import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothCondition;
import ink.rainbowbridge.v1.arathoth.utils.ColorUtils;
import ink.rainbowbridge.v1.arathoth.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @Author 寒雨
 * @Since 2021/2/10 12:46
 */
public class Unbreakable extends ArathothCondition implements Listener {
    @Override
    public FileConfiguration setDefaultConfig(FileConfiguration config) {
        config.set(getName()+".Patterns", Collections.singletonList("Unbreakable"));
        return config;
    }

    @Override
    public void onRegistering() {
        Bukkit.getPluginManager().registerEvents(this, ArathothI.getInstance());
    }

    @Override
    public boolean pass(ItemStack item, Player player) {
        return true;
    }
    @EventHandler
    public void onDamageItem(PlayerItemDamageEvent e){
        if (isEnable() && ItemUtils.isApproveItem(e.getItem())){
            boolean pass = false;
            for (String str : ColorUtils.Uncolor(e.getItem().getItemMeta().getLore())){
                for (String key : getPatterns()){
                    if (str.contains(key)){
                        pass = true;
                        break;
                    }
                }
                if (pass){
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
}
