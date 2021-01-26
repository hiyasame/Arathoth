package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.enums.StatusType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;

/**
 * ArathothI 第一个属性，代码行数较前版本缩水80%
 * 知道Zero有多烂了吧
 * @Author 寒雨
 * @Since 2021/1/26 9:16
 */
public class AdditionalHealth extends ArathothAttribute {
    @Override
    public void setDefaultConfig(FileConfiguration config) {
        config.set(getName()+".Settings.MinHealth",1.0D);
        config.set(getName()+".Settings.BaseHealth",20.0D);
    }

    @Override
    public void onRegistering() {
        //无事可做
    }

    @Override
    public void onExecute(Event event, LivingEntity executor, Projectile projectile) {
        Double value = ParseValue(executor).solveData();
        Double v2 = value+getConfig().getDouble(getName()+".Settings.BaseHealth");
        if(v2 < getConfig().getDouble(getName()+".Settings.BaseHealth")){
            executor.setMaxHealth(getConfig().getDouble(getName()+".Settings.BaseHealth"));
        }
        else{
            executor.setHealth(v2);
        }
    }

    @Override
    public StatusType getType() {
        return StatusType.UPDATE;
    }
}
