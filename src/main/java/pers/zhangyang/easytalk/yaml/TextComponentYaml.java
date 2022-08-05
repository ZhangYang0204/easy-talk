package pers.zhangyang.easytalk.yaml;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easylibrary.base.YamlBase;

import java.util.ArrayList;
import java.util.List;

public class TextComponentYaml extends YamlBase {
    public static final TextComponentYaml INSTANCE = new TextComponentYaml();

    private TextComponentYaml() {
        super("textComponent.yml");
    }


    @NotNull
    public TextComponent getTextComponent(String path){

        String text=getString(path+".text");
        String action=getString(path+".action");
        if (text==null){
            text="";
        }
        TextComponent textComponent=new TextComponent(text);
        if ("SHOW_TEXT".equalsIgnoreCase(action)){
            List<String> contentList=getStringList(path+".content");
            if (contentList==null){
                return textComponent;
            }
            List<Content> textList=new ArrayList<>();
            for (String s:contentList){
                textList.add(new Text(s));
            }
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,textList));
        }
        if ("RUN_COMMAND".equalsIgnoreCase(action)){
            String content=getString(path+".content");
            if (content==null){
                return textComponent;
            }
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,content));
        }
        if ("SUGGEST_COMMAND".equalsIgnoreCase(action)){
            String content=getString(path+".content");
            if (content==null){
                return textComponent;
            }
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,content));
        }
        if ("COPY_TO_CLIPBOARD".equalsIgnoreCase(action)){
            String content=getString(path+".content");
            if (content==null){
                return textComponent;
            }
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD,content));
        }
        return textComponent;
    }
}
