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
import org.bukkit.scheduler.BukkitRunnable;

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
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        attr.execute(event,event.getExecutor(),attr.ParseValue(event.getExecutor()));
                    }
                }.runTask(ArathothI.getInstance());
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
                    attr.execute(event, (LivingEntity) event.getEntity(), attr.ParseValue((LivingEntity) event.getEntity()));
                }
                else{
                    if(((EntityDamageByEntityEvent)event).getDamager().hasMetadata(attr.getName())) {
                        attr.execute(event, (LivingEntity) event.getEntity(), ArathothI.getAPI().getArrowData(((EntityDamageByEntityEvent) event).getDamager(), attr.getName()));
                    }
                }
            }
            else if(attr.getType() == StatusType.ATTACK){
                if (event instanceof EntityDamageByEntityEvent) {
                    if (((EntityDamageByEntityEvent) event).getDamager() instanceof LivingEntity) {
                        attr.execute(event, (LivingEntity) ((EntityDamageByEntityEvent) event).getDamager(), attr.ParseValue((LivingEntity) ((EntityDamageByEntityEvent) event).getDamager()));
                    }
                    else if(((EntityDamageByEntityEvent) event).getDamager() instanceof Projectile){
                        if(((EntityDamageByEntityEvent)event).getDamager().hasMetadata(attr.getName())) {
                            if (((Projectile) ((EntityDamageByEntityEvent) event).getDamager()).getShooter() instanceof LivingEntity) {
                                attr.execute(event, (LivingEntity) ((Projectile) ((EntityDamageByEntityEvent) event).getDamager()).getShooter(), ArathothI.getAPI().getArrowData(((EntityDamageByEntityEvent) event).getDamager(), attr.getName()));
                            }
                        }
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
                    attr.execute(e, (LivingEntity) e.getBukkitEvent().getEntity(), attr.ParseValue((LivingEntity) e.getBukkitEvent().getEntity()));
                }
                else{
                    attr.execute(e,(LivingEntity)e.getBukkitEvent().getEntity(), ArathothI.getAPI().getArrowData((e.getBukkitEvent()).getDamager(),attr.getName()));
                }
            }
            else if(attr.getType() == StatusType.ATTACK){
                    if ((e.getBukkitEvent()).getDamager() instanceof LivingEntity) {
                        attr.execute(e, (LivingEntity) (e.getBukkitEvent()).getDamager(), attr.ParseValue((LivingEntity) (e.getBukkitEvent()).getDamager()));
                    }
                    else if((e.getBukkitEvent()).getDamager() instanceof Projectile){
                        if(e.getBukkitEvent().getDamager().hasMetadata(attr.getName())) {
                            if (((Projectile)e.getBukkitEvent().getDamager()).getShooter() instanceof LivingEntity) {
                                attr.execute(e, (LivingEntity) ((Projectile)e.getBukkitEvent().getDamager()).getShooter(), ArathothI.getAPI().getArrowData((e.getBukkitEvent()).getDamager(), attr.getName()));
                            }
                        }
                    }
            }
        }
    }

}
