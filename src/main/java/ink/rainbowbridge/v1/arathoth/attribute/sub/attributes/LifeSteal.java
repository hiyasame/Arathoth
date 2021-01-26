package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.enums.StatusType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @Author 寒雨
 * @Since 2021/1/26 10:23
 */
public class LifeSteal extends ArathothAttribute {
    @Override
    public void setDefaultConfig(FileConfiguration config) {

    }

    @Override
    public void onRegistering() {

    }

    @Override
    public void onExecute(Event event, LivingEntity executor, Projectile projectile) {
        if (event instanceof EntityDamageByEntityEvent){
            ((EntityDamageByEntityEvent) event).setDamage(((EntityDamageByEntityEvent) event).getDamage()+ParseValue(executor).solveData());
            if(((EntityDamageByEntityEvent) event).getDamager() instanceof LivingEntity){
                EntityDamageByEntityEvent eve = (EntityDamageByEntityEvent)event;
                if((((LivingEntity) eve.getDamager()).getMaxHealth() - ParseValue(executor).solveData()) >= ((LivingEntity) eve.getDamager()).getHealth()){
                    ((LivingEntity) eve.getDamager()).setHealth(((LivingEntity) eve.getDamager()).getHealth() + ParseValue(executor).solveData());
                }
                else{
                    ((LivingEntity) eve.getDamager()).setHealth(((LivingEntity) eve.getDamager()).getMaxHealth());
                }
            }
        }
    }

    @Override
    public StatusType getType() {
        return StatusType.ATTACK;
    }
}
