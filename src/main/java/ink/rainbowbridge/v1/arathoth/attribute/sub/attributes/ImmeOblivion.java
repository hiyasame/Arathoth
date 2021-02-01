package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.data.ArathothStatusData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 即刻物理反噬 CD: 1s
 * 只对玩家有效
 * @Author 寒雨
 * @Since 2021/1/26 9:40
 */
public class ImmeOblivion extends ArathothAttribute {
    private BukkitRunnable task;
    @Override
    public FileConfiguration setDefaultConfig(FileConfiguration config) {
        return config;
    }

    @Override
    public void onRegistering() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()){
                    onExecute(null,p,null);
                }
            }
        };
        task.runTaskTimer(ArathothI.getInstance(),20,20);
    }

    @Override
    public void onExecute(Event event, LivingEntity executor, ArathothStatusData data) {
        executor.damage(data.solveData());
    }
}
