package pers.zhangyang.easytalk.yaml;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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


    @Nullable
    public List<String> getShowItemSymbol() {
        List<String> showItemSymbol=SettingYaml.INSTANCE.getStringList("setting.showItemSymbol");
        if (showItemSymbol!=null) {
            showItemSymbol.remove("");
        }
        return showItemSymbol;
    }

}
