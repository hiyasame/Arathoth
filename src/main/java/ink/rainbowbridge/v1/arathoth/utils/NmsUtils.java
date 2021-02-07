package ink.rainbowbridge.v1.arathoth.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * NMS方法
 * @Author 寒雨
 * @Since 2021/1/17 8:23
 */
public class NmsUtils {
    public static void setItemCooldownInHand(Player player, int tick) {
        try {
            Class<?> che = Class.forName("crg.bukkit.entity.CraftHumanEntity");
            Object converted = che.cast(player);
            Method handle = converted.getClass().getMethod("getHandle", new Class[0]);
            Object eh = handle.invoke(converted, new Object[0]);
            Method iteminhand = eh.getClass().getMethod("getItemInMainHand", new Class[0]);
            Object ih = iteminhand.invoke(eh, new Object[0]);
            Method mitem = ih.getClass().getMethod("getItem", new Class[0]);
            Object item = mitem.invoke(ih, new Object[0]);
            String name = null;
            String version;
            switch (version = NameUtils.getBukkitVersion()) {
                case "v1_10_R1":
                    name = "df";
                    break;
                case "v1_11_R1":
                    name = "di";
                    break;
                case "v1_12_R1":
                    name = "getCooldownTracker";
                    break;
                case "v1_9_R1":
                    name = "da";
                    break;
                case "v1_9_R2":
                    name = "db";
                    break;
                default:
                    if (player.getInventory().getItemInMainHand().getType() != Material.AIR){
                        player.setCooldown(player.getInventory().getItemInMainHand().getType(),tick);
                    }
                    return;
            }
            Method mic = eh.getClass().getMethod(name, new Class[0]);
            Object ic = mic.invoke(eh, new Object[0]);
            Class<?> itemc = Class.forName("net.minecraft.server."+NameUtils.getBukkitVersion()+".Item");
            Method zx = ic.getClass().getMethod("a", new Class[] { itemc, int.class });
            zx.invoke(ic, new Object[] { item, Integer.valueOf(tick) });
        } catch (Exception exception) {}
    }

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

    public static void sendTitle(Player p, String title, int fadeint, int stayt, int fadeoutt, String subtitle, int fadeinst, int stayst, int fadeoutst) {
        if (title == null)
            title = "";
        if (subtitle == null)
            subtitle = "";
        title = ChatColor.translateAlternateColorCodes('&',title);
        subtitle = ChatColor.translateAlternateColorCodes('&',subtitle);
        try {
            if (title != null) {
                Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
                Constructor<?> subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
                Object titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle, Integer.valueOf(fadeint), Integer.valueOf(stayt), Integer.valueOf(fadeoutt) });
                sendPacket(p, titlePacket);
                e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent") });
                titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle });
                sendPacket(p, titlePacket);
            }
            if (subtitle != null) {
                Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
                Object chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
                Constructor<?> subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
                Object subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, Integer.valueOf(fadeinst), Integer.valueOf(stayst), Integer.valueOf(fadeoutst) });
                sendPacket(p, subtitlePacket);
                e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
                subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
                subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, Integer.valueOf(fadeinst), Integer.valueOf(stayst), Integer.valueOf(fadeoutst) });
                sendPacket(p, subtitlePacket);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
