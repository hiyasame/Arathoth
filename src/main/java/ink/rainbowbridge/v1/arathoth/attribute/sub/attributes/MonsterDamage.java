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
 * @Since 2021/1/26 10:17
 */
public class MonsterDamage extends ArathothAttribute {
    @Override
    public FileConfiguration setDefaultConfig(FileConfiguration config) {
        return config;
    }

    @Override
    public void onRegistering() {

    }

    @Override
    public void onExecute(Event event, LivingEntity executor, ArathothStatusData data) {
        if(event instanceof EntityDamageByEntityEvent) {
            if (((EntityDamageByEntityEvent) event).getEntity() instanceof Monster) {
                ((EntityDamageByEntityEvent) event).setDamage(((EntityDamageByEntityEvent) event).getDamage() + data.solveData());
            }
        }
    }

    @Override
    public StatusType getType() {
        return StatusType.ATTACK;
    }

    @Override
    public String getDescription() {
        return "怪物伤害，提升数值效果";
    }
}
