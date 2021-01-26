package ink.rainbowbridge.v1.arathoth.attribute.data;

import com.sun.istack.internal.NotNull;
import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.enums.PlaceHolderType;

/**
 * @Author 寒雨
 * @Since 2021/1/25 14:55
 */
public class ArathothStatusData {
    private Double min;
    private Double max;
    private Double percent;
    private Object special;

    /**
     * 初始化构造方法
     */
    public ArathothStatusData(){
        this.min = 0.0D;
        this.max = 0.0D;
        this.percent = 0.0D;
    }

    /**
     * 初始赋值构造方法
     *
     * @param min 最小值
     * @param max 最大值
     * @param percent 百分比
     */
    public ArathothStatusData(@NotNull Double min, @NotNull Double max, @NotNull Double percent){
        this.min = min;
        this.max = max;
        this.percent = percent;
    }

    /**
     * 解析data获得可以直接使用的属性值
     *
     * @return 结果
     */
    public Double solveData(){
        if(this.min.equals(this.max)){
            return min*(1+percent/100);
        }
        else{
            return ArathothI.getAPI().getRandom(min,max)*(1+percent/100);
        }
    }

    /**
     * 转换为PlaceHolder变量
     *
     * @param type 转换的类型
     * @return Placeholder
     */
    public String getPlaceHolder(PlaceHolderType type){
        switch (type){
            case MIN:{
                return ArathothI.DecimalFormat.format(min);
            }
            case MAX:{
                return ArathothI.DecimalFormat.format(max);
            }
            case PERCENT:{
                return ArathothI.DecimalFormat.format(percent);
            }
            case TOTAL:{
                if (!(min.equals(max))){
                    return ArathothI.DecimalFormat.format(min*(1+percent/100))+"-"+ArathothI.DecimalFormat.format(max*(1+percent/100));}
                else{
                    return ArathothI.DecimalFormat.format(min*(1+percent/100));
                }
            }
        }
        return null;
    }


    public ArathothStatusData importData(ArathothStatusData data){
        return new ArathothStatusData(min+data.getMin(),max+data.getMax(),percent+data.getPercent());
    }

    /**
     * 纠正负值
     */
    public ArathothStatusData FixZeroValue(){
        Double MIN = min;
        Double MAX = max;
        Double PCT = percent;
        if (max < 0){
            MAX = 0.0D;
        }
        if (min < 0){
            MIN = 0.0D;
        }
        if (percent < 0){
            PCT = 0.0D;
        }
        return new ArathothStatusData(MIN,MAX,PCT);
    }

    public Object getSpecial() {
        return special;
    }

    public void setSpecial(Object obj){
        this.special = obj;
    }

    public Double getPercent() {
        return percent;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public void setMin(@NotNull Double min) {
        this.min = min;
    }

    public void setPercent(@NotNull Double percent) {
        this.percent = percent;
    }

    public void setMax(@NotNull Double max) {
        this.max = max;
    }

    public ArathothStatusData SimpleAdd(Double value){
        return new ArathothStatusData(min+value,max+value,percent);
    }

    public ArathothStatusData SimpleDecrease(Double value){
        return new ArathothStatusData(min-value,max-value,percent);
    }

}
