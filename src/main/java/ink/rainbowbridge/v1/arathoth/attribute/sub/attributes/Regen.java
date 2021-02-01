package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.data.ArathothStatusData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

/**
 * CUSTOM 类属性例子
 * @Author 寒雨
 * @Since 2021/1/26 10:18
 */
public class Regen extends ArathothAttribute implements Listener {
    @Override
    public FileConfiguration setDefaultConfig(FileConfiguration config) {
        return config;
    }

    @Override
    public void onRegistering() {
        Bukkit.getPluginManager().registerEvents(this, ArathothI.getInstance());
    }

    @Override
    public void onExecute(Event event, LivingEntity executor, ArathothStatusData data) {
        if (event instanceof EntityRegainHealthEvent){
            ((EntityRegainHealthEvent) event).setAmount(((EntityRegainHealthEvent) event).getAmount()+data.solveData());
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    public void ListenAttribute(EntityRegainHealthEvent e){
        execute(e,(LivingEntity) e.getEntity(),null);
    }
}
