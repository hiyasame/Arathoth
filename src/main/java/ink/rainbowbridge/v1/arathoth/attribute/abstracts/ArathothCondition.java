package ink.rainbowbridge.v1.arathoth.attribute.abstracts;

import ink.rainbowbridge.v1.arathoth.ALocale;
import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.AttributeManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * ArathothI 之后改名叫Condition 还是这个合适
 * @Author 寒雨
 * @Since 2021/1/26 4:48
 */
public abstract class ArathothCondition {
    /**
     * 获取属性名
     * @return name
     */
    public String getName(){
        return this.getClass().getSimpleName();
    }

    /**
     * 获取属性配置文件
     * @return config
     */
    public final FileConfiguration getConfig(){
        File file = new File(ArathothI.getInstance().getDataFolder(), "Conditions." + getName() + ".yml");
        if(load()){
            return setDefaultConfig(YamlConfiguration.loadConfiguration(file));
        }
        return YamlConfiguration.loadConfiguration(file);
    }
    /**
     * 重写它来操作默认config
     * @param config 属性config
     */
    public abstract FileConfiguration setDefaultConfig(FileConfiguration config);

    /**
     * 载入配置方法，不建议重写
     * @return first
     */
    public final boolean load() {
        File file = new File(ArathothI.getInstance().getDataFolder(), "Conditions." + getName() + ".yml");
        FileConfiguration config = null;
        boolean first = false;
        if (!file.exists()) {
            FileWriter fw = null;
            PrintWriter out = null;
            try {
                file.createNewFile();
                fw = new FileWriter(file);
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)));
                out.write("# Arathoth Conditions Configuration\n");
                out.write("# @Author Freeze003(寒雨)\n");
                out.write(getName() + ":\n");
                out.write(" Enable: true\n");
                out.write(" Patterns:\n");
                out.write(" - '[VALUE] " + getName() + "'");
                out.flush();
                out.close();
                fw.close();
                first = true;
            } catch (IOException e) {

            }
        }
        return first;
    }

    /**
     * 返回属性描述，默认无描述
     * @return str
     */
    public String getDescription(){
        return ALocale.getString("Attribute-DefaultDescription");
    }

    /**
     * 注册方法，不要重写
     * 重写onRegistering()方法
     * @param plugin 来源插件
     */
    public final void register(Plugin plugin){
        AttributeManager.RegisteredCondition.put(this,plugin);
        ArathothI.send(Bukkit.getConsoleSender(),ALocale.getString("CONDITION-LOAD").replace("{name}",getName()).replace("{plugin}",plugin.getName()));
        load();
        onRegistering();
    }

    /**
     * 可供操作的注册方法
     */
    public abstract void onRegistering();


    /**
     * 获取pattern
     * @return pattern
     */
    public final List<String> getPatterns(){
        return getConfig().getStringList(getName()+".Patterns");
    }


    /**
     * 属性是否启用
     * @return boolean
     */
    public final boolean isEnable(){
        return getConfig().getBoolean(getName()+".Enable",false);
    }

    /**
     * 条件通过判断，不通过的物品不计算属性
     * @param item item
     * @param player p
     * @return boolean
     */
    public abstract boolean pass(ItemStack item ,Player player);

}
