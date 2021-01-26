package ink.rainbowbridge.v1.arathoth.listener;

import ink.rainbowbridge.v1.arathoth.ArathothI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * 处理消息提醒的监听类
 * @Author 寒雨
 * @Since 2021/1/17 9:38
 */
public class MessageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(ArathothI.getInstance().getConfig().getBoolean("ActionBarMessageRemind",false)){
            if (e.getDamager() instanceof Player){
                Player p = (Player)e.getDamager();
                if(e.getEntity().getCustomName() != null)
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatColor.translateAlternateColorCodes('&',"&7对 "+e.getEntity().getCustomName()+" &7造成了&4&l "+ArathothI.DecimalFormat.format(e.getDamage())+" &7点伤害!")));
            }
        }
    }
}
