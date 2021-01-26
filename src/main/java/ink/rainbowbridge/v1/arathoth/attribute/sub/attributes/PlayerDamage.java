package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.enums.StatusType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @Author 寒雨
 * @Since 2021/1/26 10:15
 */
public class PlayerDamage extends ArathothAttribute {
    @Override
    public void setDefaultConfig(FileConfiguration config) {

    }

    @Override
    public void onRegistering() {

    }

    @Override
    public void onExecute(Event event, LivingEntity executor, Projectile projectile) {
        if(event instanceof EntityDamageByEntityEvent) {
            if (((EntityDamageByEntityEvent) event).getEntity() instanceof Player) {
                ((EntityDamageByEntityEvent) event).setDamage(((EntityDamageByEntityEvent) event).getDamage() + ParseValue(executor).solveData());
            }
        }
    }

    @Override
    public StatusType getType() {
        return StatusType.ATTACK;
    }
}
