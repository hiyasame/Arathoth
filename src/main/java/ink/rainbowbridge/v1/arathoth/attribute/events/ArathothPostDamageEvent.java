package ink.rainbowbridge.v1.arathoth.attribute.events;

import ink.rainbowbridge.v1.arathoth.attribute.data.ArathothStatusData;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * 该版本灵魂所在，元素算法载体，接管原版伤害
 * @Author 寒雨
 * @Since 2021/1/26 6:59
 */
public class ArathothPostDamageEvent extends Event implements Cancellable {
    private boolean isCancelled;
    public final static HandlerList handlerList = new HandlerList();
    private final EntityDamageByEntityEvent bukkitEvent;
    private ArathothStatusData magic;
    private ArathothStatusData fire;
    private ArathothStatusData water;
    private ArathothStatusData lava;
    private ArathothStatusData Void;
    private ArathothStatusData wither;
    public ArathothPostDamageEvent(EntityDamageByEntityEvent event){
        this.bukkitEvent = event;
        this.magic = new ArathothStatusData();
        this.fire = new ArathothStatusData();
        this.water = new ArathothStatusData();
        this.lava = new ArathothStatusData();
        this.Void = new ArathothStatusData();
        this.wither = new ArathothStatusData();
    }

    /**
     * 获取最终值，事件呼叫完毕后会将其加到FinalDamage中
     * @return value
     */
    public Double outFinalValue(){
       return magic.FixZeroValue().importData(fire.FixZeroValue()).importData(water.FixZeroValue()).importData(lava.FixZeroValue()).importData(Void.FixZeroValue()).importData(wither.FixZeroValue()).solveData();
    }
    //TODO getter & setter
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

    public EntityDamageByEntityEvent getBukkitEvent() {
        return bukkitEvent;
    }

    public ArathothStatusData getMagic() {
        return magic;
    }

    public void setMagic(ArathothStatusData magic) {
        this.magic = magic;
    }

    public ArathothStatusData getFire() {
        return fire;
    }

    public void setFire(ArathothStatusData fire) {
        this.fire = fire;
    }

    public ArathothStatusData getWater() {
        return water;
    }

    public void setWater(ArathothStatusData water) {
        this.water = water;
    }

    public ArathothStatusData getLava() {
        return lava;
    }

    public void setLava(ArathothStatusData lava) {
        this.lava = lava;
    }

    public ArathothStatusData getVoid() {
        return Void;
    }

    public void setVoid(ArathothStatusData aVoid) {
        Void = aVoid;
    }

    public ArathothStatusData getWither() {
        return wither;
    }

    public void setWither(ArathothStatusData wither) {
        this.wither = wither;
    }
}
