package ink.rainbowbridge.v1.arathoth.api;

import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.AttributeManager;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothCondition;
import ink.rainbowbridge.v1.arathoth.attribute.data.ArathothStatusData;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ArathothI - API
 *
 * @Author 寒雨
 * @Since 2021/1/16 23:37
 */
public class ArathothAPI {
    /**
     * 取随机数方法
     * 继承自Arathoth-Zero
     *
     * @param value1 primary
     * @param value2 regular
     * @return 浮动值
     */
    public Double getRandom(Double value1, Double value2) {
        Double random = Math.floor(value1 + ArathothI.random.nextDouble() * (value2 - value1));
        return random;
    }

    /**
     * 自带的解析数值属性方法
     * 你可以选择自己写，不一定非要用这个
     * @param patterns pattern
     * @param uncoloredlore lore
     * @return data
     */
    public ArathothStatusData NumberParse(List<String> patterns, List<String> uncoloredlore) {
        ArathothStatusData data = new ArathothStatusData();
        for (String str : patterns) {
            Pattern min = Pattern.compile(str.replace("[VALUE]", "((?:\\-|\\+)?(\\d+(?:\\.\\d+)?))"));
            Pattern max = Pattern.compile(str.replace("[VALUE]", "((?:\\-|\\+)?(\\d+(?:\\.\\d+)?))(\\-)(\\d+(?:\\.\\d+)?)"));
            Pattern pct = Pattern.compile(str.replace("[VALUE]", "((?:\\-|\\+)?(\\d+(?:\\.\\d+)?))\\%"));
            for (String lore : uncoloredlore) {
                Matcher m1 = min.matcher(lore);
                Matcher m2 = max.matcher(lore);
                Matcher m3 = pct.matcher(lore);
                if (m2.find()) {
                    data.setMin(data.getMin() + Double.valueOf(m2.group(1)));
                    data.setMax(data.getMax() + Double.valueOf(m2.group(6)));
                } else {
                    if (m1.find()) {
                        data.setMin(data.getMin() + Double.valueOf(m1.group(1)));
                        data.setMax(data.getMax() + Double.valueOf(m1.group(1)));
                    }
                }
                if (m3.find()) {
                    data.setPercent(data.getPercent() + Double.valueOf(m3.group(1)));
                }
            }
        }
        return data;
    }

    /**
     * 获取属性实例
     * @param name 属性名
     * @return 实例
     */
    public ArathothAttribute getAttrInstance(String name){
        for(ArathothAttribute attr : AttributeManager.Registered.keySet()){
            if (attr.getName().equals(name)){
                return attr;
            }
        }
        return null;
    }

    /**
     * 获取条件实例
     * @param name 条件名
     * @return 实例
     */
    public ArathothCondition getCondInstance(String name){
        for(ArathothCondition attr : AttributeManager.RegisteredCondition.keySet()){
            if (attr.getName().equals(name)){
                return attr;
            }
        }
        return null;
    }

    /**
     * 获取所有已经注册的属性
     * @return set
     */
    public Set<ArathothAttribute> getAttrInstSet(){
        return AttributeManager.Registered.keySet();
    }

    /**
     * 获取所有已经注册的条件
     * @return set
     */
    public Set<ArathothCondition> getCondInstSet(){
        return AttributeManager.RegisteredCondition.keySet();
    }

    /**
     * 为弓箭设置元数据
     *
     * @param e 弓箭
     * @param name 属性名
     * @param data 属性data
     */
    public void setArrowData(Entity e, String name, ArathothStatusData data){
        e.setMetadata(name,new FixedMetadataValue(ArathothI.getInstance(),data));
    }

    /**
     *  获取弓箭data
     *
     * @param e 弓箭
     * @param name 属性名
     * @return 属性data
     */
    public ArathothStatusData getArrowData(Entity e,String name){
        return (ArathothStatusData) ArathothI.getMetadata(e,name,ArathothI.getInstance());
    }
    /**
     * 概率判断方法
     *
     * @param rate 概率
     * @return 是否通过
     */
    public boolean Chance(Double rate){
        if (ArathothI.random.nextDouble() < rate){
            return true;
        }
        return false;
    }
}
