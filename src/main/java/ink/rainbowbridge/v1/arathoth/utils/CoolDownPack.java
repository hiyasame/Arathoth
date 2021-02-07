package ink.rainbowbridge.v1.arathoth.utils;


import ink.rainbowbridge.v1.arathoth.ArathothI;

/**
 * 简单冷却包
 * @Author 寒雨
 * @Since 2021/2/7 18:34
 */
public class CoolDownPack {
    private long start;
    private long cd;
    private boolean ForceEnd;
    public CoolDownPack(long ticks){
        this.start = System.currentTimeMillis();
        this.cd = ticks*50;
        this.ForceEnd = false;
    }

    /**
     * 获取剩余秒数
     * @return s
     */
    public Double getSecondLeft(){
        if (isEnd()){
            return 0.0D;
        }
        return Double.valueOf(ArathothI.DecimalFormat.format((System.currentTimeMillis() - start - cd)/1000));
    }

    /**
     * 冷却是否结束
     * @return boolean
     */
    public boolean isEnd(){
        if (!isForceEnd() && System.currentTimeMillis() < start + cd){
            return false;
        }
        return true;
    }

    public long getCd() {
        return cd;
    }

    public boolean isForceEnd() {
        return ForceEnd;
    }

    /**
     * 强制结束冷却
     * @param forceEnd boolean
     */
    public void setForceEnd(boolean forceEnd) {
        ForceEnd = forceEnd;
    }
}
