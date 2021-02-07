package ink.rainbowbridge.v1.arathoth.support;

import ink.rainbowbridge.v1.arathoth.ALocale;
import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.enums.PlaceHolderType;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

/**
 * 支持新版本PAPI
 * @Author 寒雨
 * @Since 2021/2/7 23:39
 */
public class SupportPAPI_v2 extends PlaceholderExpansion {
    @Override
    public String onPlaceholderRequest(Player p, String params) {
        String s = params;
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

    @Override
    public String getIdentifier() {
        return "ArathothI";
    }

    @Override
    public String getAuthor() {
        return "寒雨";
    }

    @Override
    public String getVersion() {
        return ArathothI.getInstance().getDescription().getVersion();
    }
}
