package ink.rainbowbridge.v1.arathoth.attribute;

import ink.rainbowbridge.v1.arathoth.ArathothI;

import java.util.HashMap;

/**
 * @Author 寒雨
 * @Since 2021/1/26 2:18
 */
public class SlotManager {
    public static HashMap<Integer,String> getSlots(){
        HashMap<Integer,String> Slots = new HashMap<>();
        for(String i : ArathothI.getInstance().getConfig().getConfigurationSection("Slots.Register").getKeys(false)){
            Slots.put(Integer.parseInt(i),ArathothI.getInstance().getConfig().getString("Slots.Register."+i));
        }
        return Slots;
    }

    public static String getMainKey(){
        return ArathothI.getInstance().getConfig().getString("Slots.MainHand");
    }
}
