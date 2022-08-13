package pers.zhangyang.easytalk.domain;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pers.zhangyang.easylibrary.base.BackAble;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.base.SingleGuiPageBase;
import pers.zhangyang.easylibrary.util.CommandUtil;
import pers.zhangyang.easytalk.yaml.GuiYaml;

import java.util.List;

public class MainOptionPage extends SingleGuiPageBase implements BackAble {

    public MainOptionPage(Player viewer, GuiPage backPage, OfflinePlayer owner) {
        super(GuiYaml.INSTANCE.getString("gui.title.mainOptionPage"), viewer, backPage, owner);
    }

    @Override
    public void refresh() {
        ItemStack privateMessage = GuiYaml.INSTANCE.getButtonDefault("gui.button.mainOptionPage.privateChat");
        inventory.setItem(23, privateMessage);

        ItemStack shout = GuiYaml.INSTANCE.getButtonDefault("gui.button.mainOptionPage.shout");
        inventory.setItem(21, shout);

        ItemStack back = GuiYaml.INSTANCE.getButtonDefault("gui.button.mainOptionPage.back");
        inventory.setItem(49, back);

        viewer.openInventory(this.inventory);


    }

    @Override
    public void back() {
        List<String> cmdList = GuiYaml.INSTANCE.getStringList("gui.firstPageBackCommand");
        if (cmdList == null) {
            return;
        }
        CommandUtil.dispatchCommandList(viewer,cmdList);
    }
}
