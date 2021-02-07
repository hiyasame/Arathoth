package ink.rainbowbridge.v1.arathoth.utils;

import org.bukkit.entity.LivingEntity;

/**
 * @Author 寒雨
 * @Since 2021/2/7 18:27
 */
public class AttrUtils {
    /**
     * 设置玩家生命，参数填负值就是真伤
     * @param e e
     * @param num num
     */
    public static void addHealth(LivingEntity e,Double num){
        double v1 = e.getMaxHealth();
        double v2 = e.getHealth() + num;
        if (v2 < 0.0D){
            e.setHealth(0D);
            return;
        }
        if (v2 > v1){
            e.setHealth(v1);
            return;
        }
        e.setHealth(v2);
    }
}
