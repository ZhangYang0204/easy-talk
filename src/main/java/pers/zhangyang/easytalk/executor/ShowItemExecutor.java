package pers.zhangyang.easytalk.executor;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easylibrary.base.ExecutorBase;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.PermUtil;
import pers.zhangyang.easylibrary.util.PlayerUtil;
import pers.zhangyang.easytalk.yaml.FormatYaml;
import pers.zhangyang.easytalk.yaml.MessageYaml;
import pers.zhangyang.easytalk.yaml.TextComponentYaml;

import java.util.ArrayList;
import java.util.List;

public class ShowItemExecutor extends ExecutorBase {
    public ShowItemExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
        super(sender, commandName, args);
    }

    @Override
    protected void run() {
        if (args.length != 0) {
            return;
        }
        if (!(sender instanceof Player)){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notPlayer");
            MessageUtil.sendMessageTo(this.sender, list);
            return;
        }
        Player player= (Player) sender;
        TextComponent messageComponent=new TextComponent(MessageYaml.INSTANCE.getShowItem());

                    ItemStack itemStack= PlayerUtil.getItemInMainHand(player);
                    ItemTag itemTag=ItemTag.ofNbt(itemStack.getItemMeta()==null?null:itemStack.getItemMeta().getAsString());
                    messageComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM,
                            new Item(itemStack.getType().getKey().toString(), itemStack.getAmount(),
                                    itemTag)));





        Integer perm=null;
        if (player.isOp()){
            List<Integer> integerList= FormatYaml.INSTANCE.getPublicChatFormatNameList();
            if (integerList.size()!=0) {
                integerList.sort((o1, o2) -> o2 - o1);
                perm = integerList.get(0);
            }
        }else {
            perm= PermUtil.getNumberPerm("EasyTalk.publicChatFormat.",player);
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


                messageComponent.setText(ChatColor.translateAlternateColorCodes('&',messageComponent.getText()));
                textComponentList.add(messageComponent);
                continue;
            }
            TextComponent t=TextComponentYaml.INSTANCE.getTextComponent("textComponent."+v);
            t.setText(ChatColor.translateAlternateColorCodes('&',t.getText()));
            textComponentList.add(t);

        }

        TextComponent textComponent=new TextComponent();
        for (TextComponent t:textComponentList){
            if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                t.setText(PlaceholderAPI.setPlaceholders(player,t.getText()));
            }
            textComponent.addExtra(t);
        }










        for (Player p: Bukkit.getOnlinePlayers()){
            p.spigot().sendMessage(textComponent);
        }
        }

}
