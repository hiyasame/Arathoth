package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.data.ArathothStatusData;
import ink.rainbowbridge.v1.arathoth.attribute.enums.StatusType;
import ink.rainbowbridge.v1.arathoth.attribute.events.ArathothPostDamageEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @Author 寒雨
 * @Since 2021/1/26 10:03
 */
public class MagicArmor extends ArathothAttribute {
    @Override
    public FileConfiguration setDefaultConfig(FileConfiguration config) {
        return config;
    }

    @Override
    public void onRegistering() {

    }

    @Override
    public void onExecute(Event event, LivingEntity executor, ArathothStatusData data) {
        if (event instanceof EntityDamageEvent){
            EntityDamageEvent e = (EntityDamageEvent)event;
            if (e.getCause() == EntityDamageEvent.DamageCause.MAGIC){
                e.setDamage(e.getDamage()-data.solveData());
            }
        }
        else if (event instanceof ArathothPostDamageEvent){
            ArathothPostDamageEvent e = (ArathothPostDamageEvent)event;
            e.setMagic(e.getMagic().SimpleDecrease(data.solveData()));
        }
    }

    @Override
    public StatusType getType() {
        return StatusType.DEFENSE;
    }

    @Override
    public String getDescription() {
        return "魔法伤害，提升数值效果";
    }
}
