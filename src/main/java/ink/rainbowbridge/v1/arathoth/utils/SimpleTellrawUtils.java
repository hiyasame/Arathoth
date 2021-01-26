package ink.rainbowbridge.v1.arathoth.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 不用淘宝库的下场
 * 不过还是有好处的，基本学会草nms了
 * @Author 寒雨
 * @Since 2021/1/25 15:40
 */
public class SimpleTellrawUtils {
    private static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Class<?> getNMSClass(String class_name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + class_name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public static Class getCbClass(String name){

        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 自己写的第一个nms方法
     * @param p 玩家
     * @param str str
     */
    public static void SimpleTellraw(Player p, String str){
        try {
            Object craftPlayer = getCbClass("CraftPlayer").cast(p);
            Object entityPlayer = craftPlayer.getClass().getMethod("getHandle").invoke(craftPlayer);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            Class PacketPlayOutChat = getNMSClass("PacketPlayOutChat");
            Class PacketDataSerializer = getNMSClass("PacketDataSerializer");
            Object PacketPlayOutChatInstance = PacketPlayOutChat.getConstructor(PacketDataSerializer).newInstance(PacketDataSerializer.getMethod("a",String.class).invoke(str));
            sendPacket(p,PacketPlayOutChatInstance);
        }catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException | InstantiationException e){}
    }

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
        SimpleTellraw(p,Json);
    }
}
