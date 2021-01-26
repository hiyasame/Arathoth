package ink.rainbowbridge.v1.arathoth.support;


import ink.rainbowbridge.v1.arathoth.ALocale;
import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.enums.PlaceHolderType;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * papi hook
 * 重写
 * @Author 寒雨
 * @Since 2021/1/17 9:05
 */
public class SupportPAPI extends EZPlaceholderHook {
    public SupportPAPI(Plugin plugin) {
        super(plugin, "ArathothI");
    }
    @Override
    public String onPlaceholderRequest(Player p, String s) {
        if(s.startsWith("min_")){
        try{
            return ArathothI.getAPI().getAttrInstance(s.replace("min_","")).getPlaceHolder(p, PlaceHolderType.MIN);
        }catch(Exception e){
            return ALocale.getString("PlaceHolderError");
        }
        }
        else if(s.startsWith("max_")){
            try{
                return ArathothI.getAPI().getAttrInstance(s.replace("max_","")).getPlaceHolder(p, PlaceHolderType.MAX);
            }catch(Exception e){
                return ALocale.getString("PlaceHolderError");
            }
        }
        else if(s.startsWith("percent_")){
            try{
                return ArathothI.getAPI().getAttrInstance(s.replace("percent_","")).getPlaceHolder(p, PlaceHolderType.PERCENT);
            }catch(Exception e){
                return ALocale.getString("PlaceHolderError");
            }
        }
        else if(s.startsWith("total_")){
            try{
                return ArathothI.getAPI().getAttrInstance(s.replace("total_","")).getPlaceHolder(p, PlaceHolderType.TOTAL);
            }catch(Exception e){
                return ALocale.getString("PlaceHolderError");
            }
        }
        return ALocale.getString("PlaceHolderError");
    }
}
