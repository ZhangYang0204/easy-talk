package pers.zhangyang.easytalk.listener.mainoptionpage;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pers.zhangyang.easylibrary.base.FiniteInputListenerBase;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.other.vault.Vault;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.PermUtil;
import pers.zhangyang.easylibrary.util.PlayerUtil;
import pers.zhangyang.easylibrary.util.VersionUtil;
import pers.zhangyang.easytalk.yaml.FormatYaml;
import pers.zhangyang.easytalk.yaml.MessageYaml;
import pers.zhangyang.easytalk.yaml.SettingYaml;
import pers.zhangyang.easytalk.yaml.TextComponentYaml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerInputAfterClickMainOptionPageShout extends FiniteInputListenerBase {
    public PlayerInputAfterClickMainOptionPageShout(Player player, OfflinePlayer owner, GuiPage previousPage) {
        super(player, owner, previousPage, 1);
        MessageUtil.sendMessageTo(player, MessageYaml.INSTANCE.getStringList("message.chat.howToShout"));
    }

    @Override
    public void run() {

        if (!owner.isOnline()){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notOnline");
            MessageUtil.sendMessageTo(player, list);
            return;
        }

        Player onlineOwner=owner.getPlayer();


        //获得玩家的权限
        Integer perm = null;
        if (onlineOwner.isOp()) {
            List<Integer> integerList = FormatYaml.INSTANCE.getPublicChatFormatNameList();
            if (integerList.size() != 0) {
                integerList.sort((o1, o2) -> o2 - o1);
                perm = integerList.get(0);
            }
        } else {
            perm = PermUtil.getMaxNumberPerm("EasyTalk.publicChatFormat.", onlineOwner);
        }
        if (perm == null) {
            return;
        }


        //获得玩家的格式
        String format = FormatYaml.INSTANCE.getString("format.publicChat." + perm);
        if (format == null) {
            return;
        }
            format=SettingYaml.INSTANCE.getStringDefault("setting.shout.prefix")+","+format;
        //分割格式
        List<TextComponent> textComponentList = new ArrayList<>();
        for (String v : format.split(",")) {

            if (v.equalsIgnoreCase("message")) {
            if (onlineOwner.hasPermission("EasyTalk.showItem")&& VersionUtil.getMinecraftBigVersion()==1
                    &&VersionUtil.getMinecraftMiddleVersion()>=19){
                String msg= messages[0];

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

                    for (String s : finalStringList) {
                        if (onlineOwner.hasPermission("EasyTalk.chatColor")) {
                            textComponentList.add(new TextComponent(ChatColor.translateAlternateColorCodes('&', s)));
                        } else {
                            textComponentList.add(new TextComponent(s));
                        }
                        TextComponent messageComponent = new TextComponent(MessageYaml.INSTANCE.getShowItem());
                        ItemStack itemStack = PlayerUtil.getItemInMainHand(player);
                        ItemTag itemTag = ItemTag.ofNbt(itemStack.getItemMeta() == null ? null : itemStack.getItemMeta().getAsString());
                        messageComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM,
                                new Item(itemStack.getType().getKey().toString(), itemStack.getAmount(),
                                        itemTag)));

                        messageComponent.setText(ChatColor.translateAlternateColorCodes('&', messageComponent.getText()));
                        textComponentList.add(messageComponent);

                    }
                        textComponentList.remove(textComponentList.size() - 1);
                continue;
            }else {
                TextComponent textComponent=new TextComponent(messages[0]);
                textComponent.setText(ChatColor.translateAlternateColorCodes('&', textComponent.getText()));
                textComponentList.add(textComponent);
            }
            }

            TextComponent t = TextComponentYaml.INSTANCE.getTextComponent("textComponent." + v,onlineOwner);
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                t.setText(PlaceholderAPI.setPlaceholders(onlineOwner, t.getText()));
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


            if (Vault.hook()==null){
                MessageUtil.sendMessageTo(player,MessageYaml.INSTANCE.getStringList("message.chat.notHookVault"));
                return;
            }

            if (!Vault.hook().has(player,SettingYaml.INSTANCE.getShoutCost())){
                MessageUtil.sendMessageTo(player,MessageYaml.INSTANCE.getStringList("message.chat.notEnoughVaultWhenShout"));
                return;
            }

            Vault.hook().withdrawPlayer(player,SettingYaml.INSTANCE.getShoutCost());


            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(textComponent);
            }





    }
}
