package pers.zhangyang.easytalk.listener.mainoptionpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easytalk.domain.MainOptionPage;
import pers.zhangyang.easytalk.yaml.MessageYaml;
import pers.zhangyang.easytalk.yaml.SettingYaml;

@EventListener
public class PlayerClickMainOptionPageShout implements Listener {


    @GuiDiscreteButtonHandler(guiPage = MainOptionPage.class,slot = {21})
    public void on(InventoryClickEvent event){
        MainOptionPage mainOptionPage= (MainOptionPage) event.getInventory().getHolder();

        assert mainOptionPage != null;
        Player player= (Player) event.getWhoClicked();

        if (!SettingYaml.INSTANCE.getBooleanDefault("setting.shout.enable")){
            MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.notEnableShout"));
            return;
        }



        new PlayerInputAfterClickMainOptionPageShout(player,mainOptionPage.getOwner(),mainOptionPage);



    }

}
