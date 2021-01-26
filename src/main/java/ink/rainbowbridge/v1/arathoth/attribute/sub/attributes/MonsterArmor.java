package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
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
    public void setDefaultConfig(FileConfiguration config) {

    }

    @Override
    public void onRegistering() {

    }

    @Override
    public void onExecute(Event event, LivingEntity executor, Projectile projectile) {
        if (event instanceof EntityDamageByEntityEvent){
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
            if(e.getEntity() instanceof Monster) {
                e.setDamage(e.getDamage() - ParseValue(executor).solveData());
            }
        }
    }

    @Override
    public StatusType getType() {
        return StatusType.DEFENSE;
    }
}
