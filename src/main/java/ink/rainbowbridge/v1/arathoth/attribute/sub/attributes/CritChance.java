package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.data.ArathothStatusData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;

/**
 * 空属性,只作记录数据用
 * @Author 寒雨
 * @Since 2021/1/26 10:33
 */
public class CritChance extends ArathothAttribute {
    @Override
    public FileConfiguration setDefaultConfig(FileConfiguration config) {
        return config;
    }

    @Override
    public void onRegistering() {

    }

    @Override
    public void onExecute(Event event, LivingEntity executor, ArathothStatusData data) {

    }
}
