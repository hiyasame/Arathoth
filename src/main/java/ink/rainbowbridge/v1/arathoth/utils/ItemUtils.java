package ink.rainbowbridge.v1.arathoth.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @Author 寒雨
 * @Since 2021/1/17 8:21
 */
public class ItemUtils {
    public static boolean isNull(ItemStack item) {
        if (item == null) {
            return true;
        }

        if (item.getType().equals(Material.AIR)) {
            return true;
        }

        return false;
    }

    public static boolean hasLore(ItemStack item) {
        if (isNull(item)) {
            return false;
        }

        if (!item.getItemMeta().hasLore()) {
            return false;
        }

        return true;
    }

    public static boolean isApproveItem(ItemStack item) {
        return (!isNull(item) && item.getItemMeta().hasLore());
    }
}
