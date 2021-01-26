package ink.rainbowbridge.v1.arathoth.attribute.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * 致力于为Update类属性简化操作的事件
 * 监听它便可实现UPDATE操作
 * @Author 寒雨
 * @Since 2021/1/26 6:28
 */
public class ArathothUpdateExecuteEvent extends Event {
    public final static HandlerList handlerList = new HandlerList();
    public LivingEntity executor;
    public ArathothUpdateExecuteEvent(LivingEntity executor){
        this.executor = executor;
    }

    /**
     * 获取UPDATE生物
     * @return entity
     */
    public LivingEntity getExecutor(){
        return executor;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
