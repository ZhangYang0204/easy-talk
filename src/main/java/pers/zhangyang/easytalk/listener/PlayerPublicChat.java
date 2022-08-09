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
import pers.zhangyang.easytalk.yaml.FormatYaml;
import pers.zhangyang.easytalk.yaml.MessageYaml;
import pers.zhangyang.easytalk.yaml.SettingYaml;
import pers.zhangyang.easytalk.yaml.TextComponentYaml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EventListener
public class PlayerPublicChat implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void on(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();


        //获得玩家的权限
        Integer perm = null;
        if (player.isOp()) {
            List<Integer> integerList = FormatYaml.INSTANCE.getPublicChatFormatNameList();
            if (integerList.size() != 0) {
                integerList.sort((o1, o2) -> o2 - o1);
                perm = integerList.get(0);
            }
        } else {
            perm = PermUtil.getNumberPerm("EasyTalk.publicChatFormat.", player);
        }
        if (perm == null) {
            return;
        }


        //获得玩家的格式
        String format = FormatYaml.INSTANCE.getString("format.publicChat." + perm);
        if (format == null) {
            return;
        }

        //分割格式
        List<TextComponent> textComponentList = new ArrayList<>();
        for (String v : format.split(",")) {

            if (v.equalsIgnoreCase("message")) {

                String msg= event.getMessage();
                List<String> stringList = new ArrayList<>();
                List<String> finalStringList = new ArrayList<>();
                finalStringList.add(msg);
                List<String> showItemSymbol= SettingYaml.INSTANCE.getShowItemSymbol();
                if (showItemSymbol!=null) {
                    for (String s :showItemSymbol){

                        stringList.clear();
                        stringList.addAll(finalStringList);
                        finalStringList.clear();
                        for (String ss:stringList){
                            Collections.addAll(finalStringList,ss.split(s,-1));
                        }

                    }
                }

                for (String s:finalStringList){
                    if (player.hasPermission("EasyTalk.chatColor")) {
                        textComponentList.add(new TextComponent(ChatColor.translateAlternateColorCodes('&', s)));
                    }else {
                        textComponentList.add(new TextComponent(s));
                    }
                    TextComponent messageComponent=new TextComponent(MessageYaml.INSTANCE.getShowItem());
                    ItemStack itemStack= PlayerUtil.getItemInMainHand(player);
                    ItemTag itemTag=ItemTag.ofNbt(itemStack.getItemMeta()==null?null:itemStack.getItemMeta().getAsString());
                    messageComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM,
                            new Item(itemStack.getType().getKey().toString(), itemStack.getAmount(),
                                    itemTag)));

                    messageComponent.setText(ChatColor.translateAlternateColorCodes('&', messageComponent.getText()));
                    textComponentList.add(messageComponent);
                }
                textComponentList.remove(textComponentList.size()-1);
                continue;
            }

            TextComponent t = TextComponentYaml.INSTANCE.getTextComponent("textComponent." + v);
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                t.setText(PlaceholderAPI.setPlaceholders(player, t.getText()));
            }
            t.setText(ChatColor.translateAlternateColorCodes('&', t.getText()));
            textComponentList.add(t);
        }


        //格式拼接
        TextComponent textComponent = new TextComponent();
        for (TextComponent t : textComponentList) {
            textComponent.addExtra(t);
        }

        //发送
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(textComponent);
        }

    }
}
