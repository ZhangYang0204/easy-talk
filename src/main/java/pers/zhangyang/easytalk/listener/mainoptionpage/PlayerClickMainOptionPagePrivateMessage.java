package pers.zhangyang.easytalk.listener.mainoptionpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easytalk.domain.MainOptionPage;

@EventListener
public class PlayerClickMainOptionPagePrivateMessage implements Listener {


    @GuiDiscreteButtonHandler(guiPage = MainOptionPage.class,slot = {22})
    public void on(InventoryClickEvent event){
        MainOptionPage mainOptionPage= (MainOptionPage) event.getInventory().getHolder();

        assert mainOptionPage != null;
        Player player= (Player) event.getWhoClicked();

        new PlayerInputAfterClickMainOptionPagePrivateChat(player,mainOptionPage.getOwner(),mainOptionPage);



    }

}
