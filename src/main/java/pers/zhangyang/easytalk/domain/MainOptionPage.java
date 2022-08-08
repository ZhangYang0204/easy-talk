package pers.zhangyang.easytalk.domain;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.base.SingleGuiPageBase;
import pers.zhangyang.easytalk.yaml.GuiYaml;

public class MainOptionPage extends SingleGuiPageBase {

    public MainOptionPage(Player viewer, GuiPage backPage, OfflinePlayer owner) {
        super(GuiYaml.INSTANCE.getString("gui.title.mainOptionPage"), viewer, backPage,owner);
    }

    @Override
    public void refresh() {
        ItemStack privateMessage = GuiYaml.INSTANCE.getButton("gui.button.mainOptionPage.privateChat");
        inventory.setItem(22, privateMessage);
        viewer.openInventory(this.inventory);
    }

}
