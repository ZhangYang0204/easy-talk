package pers.zhangyang.easytalk;

import org.bstats.bukkit.Metrics;
import pers.zhangyang.easylibrary.EasyPlugin;

public class EasyTalk extends EasyPlugin {
    @Override
    public void onOpen() {

        // bStats统计信息
        new Metrics(EasyTalk.instance, 	15903);
    }

    @Override
    public void onClose() {

    }
}
