package ink.rainbowbridge.v1.arathoth;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 语言文件管理 for ArathothI
 *
 * @Author 寒雨
 * @Since 2021/1/17 0:21
 */
public class ALocale {
    /**
     * 获取当前使用语言种类
     *
     * @return type
     */
    public static String getLanguageType(){
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(ArathothI.getInstance().getDataFolder(),"language.yml"));
        return config.getString("Languages.Type");
    }

    /**
     * 获取String类型语言
     * @param path key
     * @return message
     */
    public static String getString(String path){
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(ArathothI.getInstance().getDataFolder(),"language.yml"));
        return config.getString("Languages.Settings."+path,"LocaleError: "+path);
    }

    /**
     * 获取lIST类型语言
     * @param path key
     * @return message
     */
    public static List<String> getStringList(String path){
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(ArathothI.getInstance().getDataFolder(),"language.yml"));
        return config.getStringList("Languages.Settings."+path);
    }
}
