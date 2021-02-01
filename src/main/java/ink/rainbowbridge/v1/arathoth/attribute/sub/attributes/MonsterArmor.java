package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.data.ArathothStatusData;
import ink.rainbowbridge.v1.arathoth.attribute.enums.StatusType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @Author 寒雨
 * @Since 2021/1/27 1:41
 */
public class MonsterArmor extends ArathothAttribute {
    @Override
    public FileConfiguration setDefaultConfig(FileConfiguration config) {
        return config;
    }

    @Override
    public void onRegistering() {

    }

    @Override
    public void onExecute(Event event, LivingEntity executor, ArathothStatusData data) {
        if (event instanceof EntityDamageByEntityEvent){
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
            if(e.getEntity() instanceof Monster) {
                e.setDamage(e.getDamage() - data.solveData());
            }
        }
    }

    @Override
    public StatusType getType() {
        return StatusType.DEFENSE;
    }
}
