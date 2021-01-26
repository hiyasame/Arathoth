package ink.rainbowbridge.v1.arathoth.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 寒雨
 * @Since 2021/1/26 0:48
 */
public class ColorUtils {
    public static List<String> Uncolor(List<String> origin){
        List<String> arrays = new ArrayList<>();
        for(String str : origin){
            arrays.add(ChatColor.translateAlternateColorCodes('&',str));
        }
        return arrays;
    }
    public static String Uncolor(String str){
        return ChatColor.translateAlternateColorCodes('&',str);
    }
}
