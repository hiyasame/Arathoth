package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.enums.StatusType;
import ink.rainbowbridge.v1.arathoth.utils.NmsUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @Author 寒雨
 * @Since 2021/1/26 10:37
 */
public class CritDamage extends ArathothAttribute {
    @Override
    public void setDefaultConfig(FileConfiguration config) {
        config.set(getName()+".Settings.subTitle_Victim","&e暴击");
        config.set(getName()+".Settings.subTitle_Attacker","&7遭暴击");
    }

    @Override
    public void onRegistering() {

    }

    @Override
    public void onExecute(Event event, LivingEntity executor, Projectile projectile) {
        if (event instanceof EntityDamageByEntityEvent){
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
            Double rate1 = ArathothI.getAPI().getAttrInstance("CritChance").ParseValue(executor).solveData();
            Double rate2 = ArathothI.getAPI().getAttrInstance("CritDefense").ParseValue((LivingEntity) e.getEntity()).solveData();
            Double armor = ArathothI.getAPI().getAttrInstance("CritArmor").ParseValue((LivingEntity)e.getEntity()).solveData();
            Double damage = ParseValue(executor).solveData();
            if (armor > damage){ return; }
            if (ArathothI.getAPI().Chance((rate1-rate2)/100)){
                try{
                    NmsUtils.sendTitle((Player)e.getDamager()," ",10,10,10,getConfig().getString(getName()+".Settings.subTitle_Attacker"),10,30,10);
                }catch (Exception e1){}
                try{
                    NmsUtils.sendTitle((Player)e.getEntity()," ",10,10,10,getConfig().getString(getName()+".Settings.subTitle_Victim"),10,30,10);
                }catch (Exception e1){}
                e.setDamage(e.getDamage()+damage-armor);
            }
        }
    }

    @Override
    public StatusType getType() {
        return StatusType.ATTACK;
    }
}
