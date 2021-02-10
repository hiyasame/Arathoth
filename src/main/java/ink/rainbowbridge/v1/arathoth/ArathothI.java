package ink.rainbowbridge.v1.arathoth;

import ink.rainbowbridge.v1.arathoth.api.ArathothAPI;
import ink.rainbowbridge.v1.arathoth.attribute.sub.attributes.*;
import ink.rainbowbridge.v1.arathoth.attribute.sub.conditions.LevelRequired;
import ink.rainbowbridge.v1.arathoth.attribute.sub.conditions.OwnderRequest;
import ink.rainbowbridge.v1.arathoth.attribute.sub.conditions.PermRequest;
import ink.rainbowbridge.v1.arathoth.attribute.sub.conditions.Unbreakable;
import ink.rainbowbridge.v1.arathoth.bstats.Metrics;
import ink.rainbowbridge.v1.arathoth.commands.MainCommand;
import ink.rainbowbridge.v1.arathoth.listener.MessageListener;
import ink.rainbowbridge.v1.arathoth.listener.StatusCommandListener;
import ink.rainbowbridge.v1.arathoth.listener.StatusEventCaller;
import ink.rainbowbridge.v1.arathoth.listener.StatusExecuteListener;
import ink.rainbowbridge.v1.arathoth.support.SupportPAPI;
import ink.rainbowbridge.v1.arathoth.support.SupportPAPI_v2;
import ink.rainbowbridge.v1.arathoth.utils.DrawFucker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public final class ArathothI extends JavaPlugin {
    FileConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"config.yml"));
    public static int DebugLevel = 0;
    private static ArathothI instance;
    public static ArathothI getInstance() {return instance;}
    public static Random random = new Random();
    public static java.text.DecimalFormat DecimalFormat = new DecimalFormat("0.0");
    public static String prefix = "&7&l[&8&lArathothI&7&l] &7";
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        load();
        /**
         * Bstats register
         */
        Metrics metrics = new Metrics(this, 9838);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
        DrawFucker.fuck();
        send(Bukkit.getConsoleSender(),"&fYou are using language : &b"+ALocale.getLanguageType());
        send(Bukkit.getConsoleSender(),ALocale.getString("Enable"));
        if (hasPAPI()) {
            send(Bukkit.getConsoleSender(),ALocale.getString("PAPI-FOUND"));
        } else {
            send(Bukkit.getConsoleSender(),ALocale.getString("PAPI-NOTFOUND"));
        }
        boolean success = new SupportPAPI_v2().register();
        if (success) {
            send(Bukkit.getConsoleSender(),ALocale.getString("PAPI-HOOK"));
        } else {
            send(Bukkit.getConsoleSender(),ALocale.getString("PAPI-FAILED"));
        }
        registerDefault();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getScheduler().cancelTasks(this);
    }

    /**
     * 获取API对象
     *
     * @return api
     */
    public static ArathothAPI getAPI(){
        return new ArathothAPI();
    }

    /**
     * 是否载入papi
     * @return boolean
     */
    public boolean hasPAPI(){
        if (getServer().getPluginManager().isPluginEnabled("PlaceHolderAPI"))
            return false;
        else
            return true;
    }

    /**
     * 是否正在debug
     * @return boolean
     */
    public static int getDebugLevel(){
        return DebugLevel;
    }

    public static void send(CommandSender sender , String message){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',prefix+message));
    }

    /**
     * 发送debug信息
     * @param level 级别
     * @param messages 消息
     */
    public static void Debug(int level,String messages){
        if(level <= DebugLevel) {
            String prefix = "&8&l[&3&lArathoth-Debug&8&l] &8";
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messages));
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.isOp()) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + messages));
                }
            }
        }
    }

    /**
     * 注册属性/监听器/命令
     */
    public void registerDefault(){
    Bukkit.getPluginManager().registerEvents(new MessageListener(),this);
    Bukkit.getPluginManager().registerEvents(new StatusEventCaller(), this);
    Bukkit.getPluginManager().registerEvents(new StatusCommandListener(),this);
    Bukkit.getPluginManager().registerEvents(new StatusExecuteListener(), this);
    Bukkit.getPluginCommand("Arathoth").setExecutor(new MainCommand());
    //注册属性
    new AdditionalHealth().register(this);
    new CritArmor().register(this);
    new CritChance().register(this);
    new CritDamage().register(this);
    new CritDefense().register(this);
    new DodgeRate().register(this);
    new HitRate().register(this);
    new LifeSteal().register(this);
    new MagicArmor().register(this);
    new MagicDamage().register(this);
    new MonsterDamage().register(this);
    new Oblivion().register(this);
    new PhysicalArmor().register(this);
    new PhysicalDamage().register(this);
    new PlayerDamage().register(this);
    new Regen().register(this);
    new MonsterArmor().register(this);
    new PlayerArmor().register(this);
    new AttackRange().register(this);
    //条件注册
    new LevelRequired().register(this);
    new PermRequest().register(this);
    new OwnderRequest().register(this);
    new Unbreakable().register(this);
    }

    public static Object getMetadata(Metadatable object, String key, Plugin plugin) {
        List<MetadataValue> values = object.getMetadata(key);
        for (MetadataValue value : values) {
            // Plugins are singleton objects, so using == is safe here
            if (value.getOwningPlugin() == plugin) {
                return value.value();
            }
        }
        return null;
    }

    public void load(){
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists())
            saveDefaultConfig();
        reloadConfig();

        File attributeFile = new File(getDataFolder(), "Attributes");
        if (!attributeFile.exists()) {
            attributeFile.mkdirs();
        }

        File update = new File(attributeFile, "update");
        if (!update.exists()) {
            update.mkdirs();
        }

        File attack = new File(attributeFile, "attack");
        if (!attack.exists()) {
            attack.mkdirs();
        }

        File custom = new File(attributeFile, "custom");
        if (!custom.exists()) {
            custom.mkdirs();
        }

        File defense = new File(attributeFile, "defense");
        if (!defense.exists()) {
            defense.mkdirs();
        }

        File RulesFile = new File(getDataFolder(), "Conditions");
        if (!RulesFile.exists()) {
            RulesFile.mkdirs();
        }

        File language = new File(getDataFolder(), "language.yml");
        if (!language.exists()) {
            saveResource("language.yml",true);
        }

        DebugLevel = config.getInt("Debug-Level");
        if (config.get("DecimalFormat") != null) {
            DecimalFormat = new DecimalFormat(config.getString("DecimalFormat"));
        }
    }
}
