package ink.rainbowbridge.v1.arathoth.attribute.abstracts;

import ink.rainbowbridge.v1.arathoth.ALocale;
import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.AttributeManager;
import ink.rainbowbridge.v1.arathoth.attribute.SlotManager;
import ink.rainbowbridge.v1.arathoth.attribute.data.ArathothStatusData;
import ink.rainbowbridge.v1.arathoth.attribute.enums.PlaceHolderType;
import ink.rainbowbridge.v1.arathoth.attribute.enums.StatusType;
import ink.rainbowbridge.v1.arathoth.attribute.events.ArathothStatusExecuteEvent;
import ink.rainbowbridge.v1.arathoth.utils.ColorUtils;
import ink.rainbowbridge.v1.arathoth.utils.ItemUtils;
import ink.rainbowbridge.v1.arathoth.utils.NameUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 真·属性抽象类
 *
 * @Author 寒雨
 * @Since 2021/1/25 14:36
 */
public abstract class ArathothAttribute {
    private FileConfiguration config;
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
    public final FileConfiguration getConfig() {
        return config;
    }

    /**
     * 获取属性类型: UPDATE,ATTACK,DEFENSE,CUSTOM
     * 如选择CUSTOM需要自行注册监听器监听属性
     * @return type
     */
    public StatusType getType(){
        return StatusType.CUSTOM;
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
        File file = new File(new File(new File(ArathothI.getInstance().getDataFolder(),"Attributes"), getType().toString().toLowerCase()),getName()+".yml");
        boolean first = false;
        if (!file.exists()) {
            FileWriter fw = null;
            PrintWriter out = null;
            try {
                fw = new FileWriter(file);
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)));
                out.write("# Arathoth Attributes Configuration\n");
                out.write("# @Author Freeze003(寒雨)\n");
                out.write(getName() + ":\n");
                out.write(" Enable: true\n");
                out.write(" Patterns:\n");
                out.write(" - '[VALUE] " + getName() + "'");
                out.flush();
                out.close();
                fw.close();
                first = true;
                FileConfiguration config1 = YamlConfiguration.loadConfiguration(file);
                FileConfiguration edited = setDefaultConfig(config1);
                this.config = edited;
                edited.save(file);
            } catch (IOException e) {
                e.printStackTrace();
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
        AttributeManager.Registered.put(this,plugin);
        ArathothI.send(Bukkit.getConsoleSender(),ALocale.getString("ATTR-LOAD").replace("{name}",getName()).replace("{plugin}",plugin.getName()));
        load();
        onRegistering();
    }

    /**
     * 可供操作的注册方法
     */
    public abstract void onRegistering();

    /**
     * 运行属性抽象方法
     * @param event eve
     * @param executor entity
     * @param data data
     */
    public abstract void onExecute(Event event, LivingEntity executor,ArathothStatusData data);

    /**
     * 获取pattern
     * @return pattern
     */
    public final List<String> getPatterns(){
        return getConfig().getStringList(getName()+".Patterns");
    }

    /**
     * 执行方法，call ExecuteEvent
     * @param event 当前事件
     * @param executor 属性触发者
     * @param data 弹射物，若不为弹射物实体触发则为null
     */
    public final void execute(Event event, LivingEntity executor, ArathothStatusData data){
        ArathothStatusExecuteEvent eve = new ArathothStatusExecuteEvent(this,executor,data);
        Bukkit.getPluginManager().callEvent(eve);
        if (!isEnable()){ return; }
        if (eve.isCancelled()){ return; }
        long time = System.currentTimeMillis();
        onExecute(event,executor,data);
        ArathothI.Debug(3,"执行属性: &f"+eve.getAttr().getName()+" &8, 执行者: &f"+ NameUtils.getEntityName(eve.getExecutor())+" &8耗时: &f"+(System.currentTimeMillis() - time)+"ms");
        ArathothI.Debug(4,"&8Min: &f"+ArathothI.DecimalFormat.format(eve.getData().getMin())+" &8Max: &f"+ArathothI.DecimalFormat.format(eve.getData().getMax())+" &8Pct: &f"+ArathothI.DecimalFormat.format(eve.getData().getPercent()));
    }

    /**
     * 解析数值方法，在onParseValue中操作可自定义部分
     * @param target 对象
     * @return data
     */
    public final ArathothStatusData ParseValue(LivingEntity target){
        if(target instanceof Player){
            Player p =(Player)target;
            List<ItemStack> items = new ArrayList<>();
            for(Integer slot : SlotManager.getSlots().keySet()){
                if (ItemUtils.isApproveItem(p.getInventory().getItem(slot))){
                    //TODO 暂时没加条件判断，等条件写好再说
                    if(p.getInventory().getItem(slot).getItemMeta().getLore().get(0).contains(SlotManager.getSlots().get(slot)) && passCondition(p.getInventory().getItem(slot),p)){
                        items.add(p.getInventory().getItem(slot));
                    }
                }
            }
            if (ItemUtils.isApproveItem(p.getInventory().getItemInMainHand())){
                if(p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).contains(SlotManager.getMainKey()) && passCondition(p.getInventory().getItemInMainHand(),p)){
                    items.add(p.getInventory().getItemInMainHand());
                }
            }
            List<String> uncoloredlore = new ArrayList<>();
            for (ItemStack item : items){
                uncoloredlore.addAll(item.getItemMeta().getLore());
            }
            uncoloredlore = ColorUtils.Uncolor(uncoloredlore);
            ArathothStatusData data = onParseValue(uncoloredlore);
            for (PermissionAttachmentInfo info : p.getEffectivePermissions()){
                String perm = info.getPermission();
                //TODO 权限属性
                if (perm.startsWith("Arathoth.PermAttr."+getName()+".")){
                    String[] base = perm.replace("Arathoth.PermAttr."+getName()+".","").split("\\.");
                    try{
                        switch(base[0].toLowerCase()){
                            case "number":{
                                data.setMin(data.getMin()+Double.valueOf(base[1].replace("p",".")));
                                data.setMax(data.getMax()+Double.valueOf(base[1].replace("p",".")));
                            }
                            case "percent":{
                                data.setPercent(data.getPercent()+Double.valueOf(base[1].replace("p",".")));
                            }
                        }
                    }catch(Exception exp){}
                }
            }
            return data;
        }
        else{
            List<ItemStack> items = new ArrayList<>();
            items.add(target.getEquipment().getItemInOffHand());
            items.add(target.getEquipment().getItemInMainHand());
            items.add(target.getEquipment().getChestplate());
            items.add(target.getEquipment().getHelmet());
            items.add(target.getEquipment().getLeggings());
            items.add(target.getEquipment().getBoots());
            List<String> uncoloredlore = new ArrayList<>();
            for (ItemStack item : items){
                if (ItemUtils.isApproveItem(item)){
                    uncoloredlore.addAll(item.getItemMeta().getLore());
                }
            }
            uncoloredlore = ColorUtils.Uncolor(uncoloredlore);
            return onParseValue(uncoloredlore);
        }
    }

    /**
     * 属性是否启用
     * @return boolean
     */
    public final boolean isEnable(){
        return getConfig().getBoolean(getName()+".Enable",false);
    }

    /**
     * 如果你想制定特殊的属性匹配规则，重写此方法
     * @param UncoloredLore lore
     * @return data
     */
    public ArathothStatusData onParseValue(List<String> UncoloredLore){
        return ArathothI.getAPI().NumberParse(getPatterns(),UncoloredLore);
    }

    /**
     * 通过重写该方法自定义papi变量
     * 不重写返回数值变量
     * @param p 玩家
     * @param type type
     * @return papi
     */
    public String getPlaceHolder(Player p,PlaceHolderType type){
        return ParseValue(p).getPlaceHolder(type);
    }

    private boolean passCondition(ItemStack item,Player player){
        for(ArathothCondition condition : ArathothI.getAPI().getCondInstSet()){
            if (!condition.pass(item,player)){
                return false;
            }
        }
        return true;
    }
}
