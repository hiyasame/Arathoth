package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.data.ArathothStatusData;
import ink.rainbowbridge.v1.arathoth.attribute.enums.StatusType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 寒雨
 * @Since 2021/2/2 2:11
 */
public class AttackRange extends ArathothAttribute {
    @Override
    public StatusType getType() {
        return StatusType.ATTACK;
    }

    @Override
    public FileConfiguration setDefaultConfig(FileConfiguration config) {
        return config;
    }

    @Override
    public void onRegistering() {

    }

    @Override
    public void onExecute(Event event, LivingEntity executor, ArathothStatusData data) {
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent eve = (EntityDamageByEntityEvent) event;
            if (eve.getDamager() instanceof LivingEntity) {
                List<Entity> entities = new ArrayList<>();
                try{
                    entities.addAll(eve.getDamager().getNearbyEntities(data.solveData(),data.solveData(),data.solveData()));
                    entities.remove(eve.getDamager());
                    entities.remove(eve.getEntity());
                }catch (NullPointerException ignored){}
                for(Entity en : entities){
                    if(en instanceof LivingEntity){
                        ((LivingEntity) en).damage(eve.getDamage());
                    }
                }
            }
            else if (eve.getDamager() instanceof Projectile){
                List<Entity> entities = new ArrayList<>();
                try{
                    entities.addAll(eve.getDamager().getNearbyEntities(data.solveData(),data.solveData(),data.solveData()));
                    entities.remove(eve.getDamager());
                    entities.remove(eve.getEntity());
                }catch (NullPointerException ignored){}
                for(Entity en : entities){
                    if(en instanceof LivingEntity){
                        ((LivingEntity) en).damage(eve.getDamage());
                    }
                }
            }
        }
    }
}
