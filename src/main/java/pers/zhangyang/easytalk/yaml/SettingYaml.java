package pers.zhangyang.easytalk.yaml;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easylibrary.base.YamlBase;

import java.util.List;


public class SettingYaml extends YamlBase {

    public static final SettingYaml INSTANCE = new SettingYaml();

    private SettingYaml() {
        super("setting.yml");
    }


    @NotNull
    public String getDisplay() {
        String display = getStringDefault("setting.display");

        if(pers.zhangyang.easylibrary.yaml.SettingYaml.class.getClassLoader().getResource("display/"+display)==null){
            display = backUpConfiguration.getString("setting.display");
        }
        assert display != null;
        return display;
    }

    @NotNull
    public String getShoutSymbol() {
        String display = getStringDefault("setting.shout.symbol");
        if (display.isEmpty()){
            display=yamlConfiguration.getString("setting.shout.symbol");
        }
        assert display != null;
        return display;
    }
    public double getPublicChatVisibleRange() {
        double display = getDoubleDefault("setting.shout.publicChatVisibleRange");
        if (display<0){
            display=yamlConfiguration.getDouble("setting.shout.publicChatVisibleRange");
        }
        return display;
    }

    public int getShoutCost() {
        int display = getIntegerDefault("setting.shout.cost");
        if (display<0){
            display=yamlConfiguration.getInt("setting.shout.cost");
        }
        return display;
    }
    @Nullable
    public List<String> getShowItemSymbol() {
        List<String> showItemSymbol=SettingYaml.INSTANCE.getStringList("setting.showItemSymbol");
        if (showItemSymbol!=null) {
            showItemSymbol.remove("");
        }
        return showItemSymbol;
    }

}
