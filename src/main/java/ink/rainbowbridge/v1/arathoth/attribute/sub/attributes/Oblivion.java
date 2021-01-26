package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.enums.StatusType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * 物理反噬
 * 3行有效代码，扯不扯?
 * @Author 寒雨
 * @Since 2021/1/26 9:36
 */
public class Oblivion extends ArathothAttribute {
    @Override
    public void setDefaultConfig(FileConfiguration config) {

    }

    @Override
    public void onRegistering() {

    }

    @Override
    public void onExecute(Event event, LivingEntity executor, Projectile projectile) {
        if(event instanceof EntityDamageByEntityEvent){
            executor.damage(ParseValue(executor).solveData());
        }
    }

    @Override
    public StatusType getType() {
        return StatusType.ATTACK;
    }
}
