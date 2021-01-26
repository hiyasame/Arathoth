package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.enums.StatusType;
import ink.rainbowbridge.v1.arathoth.attribute.events.ArathothPostDamageEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;

/**
 * PostDamage机制的例子
 * @Author 寒雨
 * @Since 2021/1/26 10:12
 */
public class MagicDamage extends ArathothAttribute {
    @Override
    public void setDefaultConfig(FileConfiguration config) {

    }

    @Override
    public void onRegistering() {

    }

    @Override
    public void onExecute(Event event, LivingEntity executor, Projectile projectile) {
        if (event instanceof ArathothPostDamageEvent){
            ArathothPostDamageEvent e = (ArathothPostDamageEvent)event;
            e.setMagic(e.getMagic().SimpleAdd(ParseValue(executor).solveData()));
        }
    }

    @Override
    public StatusType getType() {
        return StatusType.ATTACK;
    }
}
