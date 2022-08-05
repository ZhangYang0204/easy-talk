package pers.zhangyang.easytalk.executor;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easylibrary.base.ExecutorBase;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.PermUtil;
import pers.zhangyang.easytalk.yaml.FormatYaml;
import pers.zhangyang.easytalk.yaml.MessageYaml;
import pers.zhangyang.easytalk.yaml.TextComponentYaml;

import java.util.ArrayList;
import java.util.List;

public class PrivateChatExecutor extends ExecutorBase {
    public PrivateChatExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
        super(sender, commandName, args);
    }

    @Override
    protected void run() {
        if (args.length<2){
            return;
        }
        if (!(sender instanceof Player)){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notPlayer");
            MessageUtil.sendMessageTo(this.sender, list);
            return;
        }
        Player player= (Player) sender;
        String message=args[1];
        for (int i=2;i<args.length;i++){
            message+=" "+args[i];
        }

        Player target= Bukkit.getPlayer(args[0]);
        if (target==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notOnline");
            MessageUtil.sendMessageTo(player, list);
            return;
        }



        Integer perm=null;
        if (player.isOp()){
            List<Integer> integerList= FormatYaml.INSTANCE.getPublicChatFormatNameList();
            if (integerList.size()!=0) {
                integerList.sort((o1, o2) -> o2 - o1);
                perm = integerList.get(0);
            }
        }else {
            perm= PermUtil.getNumberPerm("EasyTalk.privateChatFormat.",player);
        }

        if (perm==null){
            return;
        }


        String format= FormatYaml.INSTANCE.getString("format.privateChat."+perm);
        if (format==null){
            return;
        }

        format= ChatColor.translateAlternateColorCodes('&',format);

        List<TextComponent> textComponentList=new ArrayList<>();
        for (String v: format.split(",")){

            if (v.equalsIgnoreCase("message")){
                TextComponent t=new TextComponent(message);
                t.setText(ChatColor.translateAlternateColorCodes('&',t.getText()));
                textComponentList.add(t);
                continue;
            }

            TextComponent t=TextComponentYaml.INSTANCE.getTextComponent("textComponent."+v);
            if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                t.setText(PlaceholderAPI.setPlaceholders(player,t.getText()));
            }
           t.setText(t.getText().replace("{self_name}",player.getName()).replace(
                   "{target_name}",target.getName()
           ));
            t.setText(ChatColor.translateAlternateColorCodes('&',t.getText()));
            textComponentList.add(t);

        }

        TextComponent textComponent=new TextComponent();
        for (TextComponent t:textComponentList){
            textComponent.addExtra(t);
        }


        target.spigot().sendMessage(textComponent);
        player.spigot().sendMessage(textComponent);


    }
}
