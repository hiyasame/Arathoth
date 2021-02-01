package ink.rainbowbridge.v1.arathoth.listener;


import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.events.ArathothPostDamageEvent;
import ink.rainbowbridge.v1.arathoth.attribute.events.ArathothUpdateExecuteEvent;
import ink.rainbowbridge.v1.arathoth.utils.NameUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 呼出/处理属性事件
 * @Author 寒雨
 * @Since 2021/1/26 6:38
 */
public class StatusEventCaller implements Listener {
    /**
     * 呼出事件
     * @param e entity
     */
    private void callEve(LivingEntity e){
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getPluginManager().callEvent(new ArathothUpdateExecuteEvent(e));
            }
        }.runTaskAsynchronously(ArathothI.getInstance());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerItemHeldEvent(PlayerItemHeldEvent event){
        if (event.isCancelled()){return;}
        callEve(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event){
        if (event.isCancelled()){return;}
        callEve(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onInventoryCloseEvent(InventoryCloseEvent event){
        callEve(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerDropEvent(PlayerDropItemEvent event){
        if (event.isCancelled()){return;}
        callEve(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerPickupItemEvent(PlayerPickupItemEvent event){
        if (event.isCancelled()){return;}
        callEve(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.isCancelled()){return;}
        callEve(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onPlayerJoinEvent(PlayerJoinEvent event) {
        callEve(event.getPlayer());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    void onEntitySpawnEvent(CreatureSpawnEvent event)  {
        if (event.isCancelled()){return;}
        callEve(event.getEntity());
    }

    /**
     * 呼出元素投递事件
     * @param e bk event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    void onPostEventCall(EntityDamageByEntityEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            ArathothPostDamageEvent eve = new ArathothPostDamageEvent(e);
            Bukkit.getPluginManager().callEvent(eve);
            if (eve.isCancelled()) {
                return;
            }
            e.setDamage(e.getDamage() + eve.outFinalValue());
            ArathothI.Debug(3, "投递元素伤害: &f" + eve.outFinalValue() + " &8Attacker: &f" + e.getDamager().getType());
        }
    }
}
