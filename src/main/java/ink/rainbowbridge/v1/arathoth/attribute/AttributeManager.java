package ink.rainbowbridge.v1.arathoth.attribute;

import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothCondition;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

/**
 * 属性管理类
 * @Author 寒雨
 * @Since 2021/1/17 10:29
 */
public class AttributeManager {
    public static HashMap<ArathothAttribute, Plugin> Registered = new HashMap<>();
    public static HashMap<ArathothCondition, Plugin> RegisteredCondition = new HashMap<>();
}
