package pers.zhangyang.easytalk.listener;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.util.PermUtil;
import pers.zhangyang.easylibrary.util.PlayerUtil;
import pers.zhangyang.easylibrary.util.ReplaceUtil;
import pers.zhangyang.easytalk.yaml.FormatYaml;
import pers.zhangyang.easytalk.yaml.SettingYaml;
import pers.zhangyang.easytalk.yaml.TextComponentYaml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EventListener
public class PlayerPublicChat implements Listener {

    @EventHandler(priority = EventPriority.HIGH,ignoreCancelled = true)
    public void on(AsyncPlayerChatEvent event){
        event.setCancelled(true);
        Player player=event.getPlayer();

        Integer perm=null;
        if (player.isOp()){
            List<Integer> integerList=FormatYaml.INSTANCE.getPublicChatFormatNameList();
            if (integerList.size()!=0) {
                integerList.sort((o1, o2) -> o2 - o1);
                perm = integerList.get(0);
            }
        }else {
             perm=PermUtil.getNumberPerm("EasyTalk.publicChatFormat.",player);
        }

        if (perm==null){
            return;
        }


        String format= FormatYaml.INSTANCE.getString("format.publicChat."+perm);
        if (format==null){
            return;
        }

        format= ChatColor.translateAlternateColorCodes('&',format);

        List<TextComponent> textComponentList=new ArrayList<>();
        for (String v: format.split(",")){

            if (v.equalsIgnoreCase("message")){

                TextComponent t=new TextComponent(event.getMessage());
                t.setText(ChatColor.translateAlternateColorCodes('&',t.getText()));
                textComponentList.add(t);

                continue;
            }

            TextComponent t=TextComponentYaml.INSTANCE.getTextComponent("textComponent."+v);
            if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                t.setText(PlaceholderAPI.setPlaceholders(player,t.getText()));
            }
            t.setText(ChatColor.translateAlternateColorCodes('&',t.getText()));
            textComponentList.add(t);

        }

        TextComponent textComponent=new TextComponent();
        for (TextComponent t:textComponentList){
            textComponent.addExtra(t);
        }



        for (Player p:Bukkit.getOnlinePlayers()){
            p.spigot().sendMessage(textComponent);
        }

    }
}
