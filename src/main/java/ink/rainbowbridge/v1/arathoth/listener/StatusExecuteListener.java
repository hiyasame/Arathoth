package ink.rainbowbridge.v1.arathoth.listener;

import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.enums.StatusType;
import ink.rainbowbridge.v1.arathoth.attribute.events.ArathothPostDamageEvent;
import ink.rainbowbridge.v1.arathoth.attribute.events.ArathothUpdateExecuteEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

/**
 * 运行属性监听器
 * @Author 寒雨
 * @Since 2021/1/26 6:58
 */
public class StatusExecuteListener implements Listener {
    @EventHandler
    public void onBowShootEvent(EntityShootBowEvent e){
        for (ArathothAttribute attr : ArathothI.getAPI().getAttrInstSet()){
            ArathothI.getAPI().setArrowData(e.getProjectile(),attr.getName(),attr.ParseValue(e.getEntity()));
        }
    }

    /**
     * 执行update属性
     * @param event 事件
     */
    @EventHandler
    public void onAttrExecute_Update(ArathothUpdateExecuteEvent event){
        for(ArathothAttribute attr : ArathothI.getAPI().getAttrInstSet()){
            if (attr.getType() == StatusType.UPDATE){
                attr.execute(event,event.getExecutor(),null);
            }
        }
    }

    /**
     * 运行伤害属性
     * @param event 事件
     */
    @EventHandler
    public void onAttrExecute_Damage(EntityDamageEvent event){
        for (ArathothAttribute attr : ArathothI.getAPI().getAttrInstSet()){
            if (attr.getType() == StatusType.DEFENSE){
                if (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
                    attr.execute(event, (LivingEntity) event.getEntity(), null);
                }
                else{
                    attr.execute(event,(LivingEntity)event.getEntity(), (Projectile) ((EntityDamageByEntityEvent)event).getDamager());
                }
            }
            else if(attr.getType() == StatusType.ATTACK){
                if (event instanceof EntityDamageByEntityEvent) {
                    if (((EntityDamageByEntityEvent) event).getDamager() instanceof LivingEntity) {
                        attr.execute(event, (LivingEntity) ((EntityDamageByEntityEvent) event).getDamager(), null);
                    }
                    else if(((EntityDamageByEntityEvent) event).getDamager() instanceof Projectile){
                        attr.execute(event,null, (Projectile) ((EntityDamageByEntityEvent) event).getDamager());
                    }
                }
            }
        }
    }

    /**
     * 可能存在一个属性多次运行的情况，不过只是发送两次debug信息，其实没差
     * 等待改良
     * @param e eve
     */
    @EventHandler
    public void onAttrExecute_PostDamage(ArathothPostDamageEvent e){
        for (ArathothAttribute attr : ArathothI.getAPI().getAttrInstSet()){
            if (attr.getType() == StatusType.DEFENSE){
                if (e.getBukkitEvent().getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
                    attr.execute(e, (LivingEntity) e.getBukkitEvent().getEntity(), null);
                }
                else{
                    attr.execute(e,(LivingEntity)e.getBukkitEvent().getEntity(), (Projectile) (e.getBukkitEvent()).getDamager());
                }
            }
            else if(attr.getType() == StatusType.ATTACK){
                    if ((e.getBukkitEvent()).getDamager() instanceof LivingEntity) {
                        attr.execute(e, (LivingEntity) (e.getBukkitEvent()).getDamager(), null);
                    }
                    else if((e.getBukkitEvent()).getDamager() instanceof Projectile){
                        attr.execute(e,null, (Projectile) (e.getBukkitEvent()).getDamager());
                    }
            }
        }
    }

}
