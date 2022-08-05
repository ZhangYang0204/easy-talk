package pers.zhangyang.easytalk.yaml;

import org.bukkit.configuration.ConfigurationSection;
import pers.zhangyang.easylibrary.base.YamlBase;

import java.util.ArrayList;
import java.util.List;

public class FormatYaml extends YamlBase {

    public static final FormatYaml INSTANCE = new FormatYaml();

    private FormatYaml() {
        super("format.yml");
    }

    public List<Integer> getPublicChatFormatNameList(){
        List<Integer> integerList=new ArrayList<>();
        ConfigurationSection configurationSection=yamlConfiguration.getConfigurationSection("format.publicChat");
        if (configurationSection==null){
            return integerList;
        }
        for (String key:configurationSection.getKeys(false)){
            try {
                int var=Integer.parseInt(key);
                integerList.add(var);
            }catch (NumberFormatException ignored){
            }
        }
        return integerList;

    }
    public List<Integer> getPrivateChatFormatNameList(){
        List<Integer> integerList=new ArrayList<>();
        ConfigurationSection configurationSection=yamlConfiguration.getConfigurationSection("format.privateChat");
        if (configurationSection==null){
            return integerList;
        }
        for (String key:configurationSection.getKeys(false)){
            try {
                int var=Integer.parseInt(key);
                integerList.add(var);
            }catch (NumberFormatException ignored){
            }
        }
        return integerList;
    }
}
