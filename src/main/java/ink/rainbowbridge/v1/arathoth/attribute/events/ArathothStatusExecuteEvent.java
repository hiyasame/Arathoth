package ink.rainbowbridge.v1.arathoth.attribute.events;

import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.data.ArathothStatusData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


/**
 * 执行属性事件
 * @Author 寒雨
 * @Since 2021/1/25 14:50
 */
public class ArathothStatusExecuteEvent extends Event implements Cancellable {
    private boolean isCancelled;
    public final static HandlerList handlerList = new HandlerList();
    public ArathothAttribute attr;
    public LivingEntity executor;
    public ArathothStatusData data;

    /**
     *
     * @param attr 执行的属性实例
     * @param executor 执行者
     * @param data data
     */
    public ArathothStatusExecuteEvent(ArathothAttribute attr, LivingEntity executor, ArathothStatusData data){
        this.attr = attr;
        this.executor = executor;
        this.data = data;
    }
    //TODO Getter & Setter

    public void setData(ArathothStatusData data) {
        this.data = data;
    }

    public LivingEntity getExecutor() {
        return executor;
    }

    public ArathothStatusData getData() {
        return data;
    }

    public ArathothAttribute getAttr() {
        return attr;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
