package pers.zhangyang.easytalk.yaml;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easylibrary.base.YamlBase;
import pers.zhangyang.easylibrary.exception.NotApplicableException;
import pers.zhangyang.easylibrary.exception.UnsupportedMinecraftVersionException;
import pers.zhangyang.easylibrary.util.ItemStackUtil;
import pers.zhangyang.easylibrary.util.VersionUtil;

import java.util.ArrayList;
import java.util.List;

public class GuiYaml extends YamlBase {

    public static final GuiYaml INSTANCE = new GuiYaml();

    private GuiYaml() {
        super("display/" + SettingYaml.INSTANCE.getDisplay() + "/gui.yml");
    }


}
