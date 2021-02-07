package ink.rainbowbridge.v1.arathoth.utils;

import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @Author 寒雨
 * @Since 2021/1/25 15:40
 */
public class SimpleTellrawUtils {
    /**
     * 包装起来的Json方法，只支持HoverEvent
     * @param p 玩家
     * @param title text
     * @param HoverLines hover
     */
    public static void TellrawJson(Player p,String title,List<String> HoverLines){
        title = ChatColor.translateAlternateColorCodes('&',title);
        HoverLines = ColorUtils.Uncolor(HoverLines);
        String Json = "{text:\"" + title + "\",hoverEvent:{action:show_text,value:[\"" +
                String.join("\",\"",HoverLines) + "\"]}}";
        p.spigot().sendMessage(ComponentSerializer.parse(Json));
    }
}
