package ink.rainbowbridge.v1.arathoth.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

/**
 * @Author 寒雨
 * @Since 2021/1/17 8:12
 */
public class NameUtils {
    public static String getBukkitVersion(){
        return Bukkit.getServer().getClass().getName().split("\\.")[3];
    }

    /**
     * 获取生物名称
     * @param e 指定生物
     * @return 名称
     */
    public static String getEntityName(LivingEntity e){
        if(e.getCustomName() != null){
            return e.getCustomName();
        }
        return e.getType().toString();
    }

    /**
     * 获取物品名称
     * @param item 物品
     * @return 名称
     */
    public static String getItemName(ItemStack item){
        if(item.hasItemMeta()){
            if (item.getItemMeta().hasDisplayName()){
                return item.getItemMeta().getDisplayName();
            }
        }
        return item.getType().toString().toLowerCase();
    }

}
