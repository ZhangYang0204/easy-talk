package pers.zhangyang.easytalk.yaml;

import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easylibrary.base.YamlBase;

public class MessageYaml extends YamlBase {

    public static final MessageYaml INSTANCE = new MessageYaml();

    private MessageYaml() {
        super("display/" + SettingYaml.INSTANCE.getDisplay() + "/message.yml");
    }

    @NotNull
    public String getShowItem() {
        String display = getStringDefault("message.component.showItem");
        if (display.isEmpty()){
            display=backUpConfiguration.getString("message.component.showItem");
        }
        assert display != null;
        return display;
    }

}
