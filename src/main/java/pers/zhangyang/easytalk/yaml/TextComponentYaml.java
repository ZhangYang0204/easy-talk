package pers.zhangyang.easytalk.yaml;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easylibrary.base.YamlBase;
import pers.zhangyang.easylibrary.util.VersionUtil;

import java.util.ArrayList;
import java.util.List;

public class TextComponentYaml extends YamlBase {
    public static final TextComponentYaml INSTANCE = new TextComponentYaml();

    private TextComponentYaml() {
        super("textComponent.yml");
    }


    @NotNull
    public TextComponent getTextComponent(String path, Player player){

        String text=getString(path+".text");
        String hoverEvent=getString(path+".hoverEvent");
        String clickEvent=getString(path+".clickEvent");
        if (text==null){
            text="";
        }
        TextComponent textComponent=new TextComponent(text);
        if ("SHOW_TEXT".equalsIgnoreCase(hoverEvent)){
            List<String> contentList=getStringList(path+".hoverEventContent");
            if (contentList!=null){

                if (VersionUtil.getMinecraftBigVersion()==1&&VersionUtil.getMinecraftMiddleVersion()<16){
                    BaseComponent[] baseComponents=new BaseComponent[contentList.size()];
                    for (int i=0;i<contentList.size();i++){
                        String s= contentList.get(i);
                        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                            s=PlaceholderAPI.setPlaceholders(player,s);
                        }
                        s= ChatColor.translateAlternateColorCodes('&',s);
                        baseComponents[i]=new TextComponent(s);
                    }
                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,baseComponents));
                }else {

                    List<Content> textList = new ArrayList<>();
                    for (String s : contentList) {
                        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                            s = PlaceholderAPI.setPlaceholders(player, s);
                        }
                        s = ChatColor.translateAlternateColorCodes('&', s);
                        textList.add(new Text(s));
                    }
                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, textList));

                }

            }
        }
        if ("RUN_COMMAND".equalsIgnoreCase(clickEvent)){
            String content=getString(path+".clickEventContent");
            if (content!=null){

                if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                    content=PlaceholderAPI.setPlaceholders(player,content);
                }
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,content));
            }
        }
        if ("SUGGEST_COMMAND".equalsIgnoreCase(clickEvent)){
            String content=getString(path+".clickEventContent");
            if (content!=null){


                if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                    content=PlaceholderAPI.setPlaceholders(player,content);
                }
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,content));
            }
        }

        if ("COPY_TO_CLIPBOARD".equalsIgnoreCase(clickEvent)&&VersionUtil.getMinecraftBigVersion()==1
                &&VersionUtil.getMinecraftMiddleVersion()>=16){
            String content=getString(path+".clickEventContent");
            if (content!=null){
                if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                    content=PlaceholderAPI.setPlaceholders(player,content);
                }
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,content));
            }

        }
        return textComponent;
    }
}
