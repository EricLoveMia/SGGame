package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

import java.util.Collections;
import java.util.List;

/**
 * @version 1.0.0
 * @description: 顺手牵羊
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 顺手牵羊 extends SilkBag {

    public 顺手牵羊(int id, String name, String memo, boolean active, int type, int aim) {
        super(id, name, memo, active, type, aim);
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 从另一个主公手中夺取一个锦囊
        List<SilkBag> silkBags = targetGeneral.getBag().getSilkBags();
        if (silkBags == null || silkBags.size() == 0) {
            System.out.println("主公" + targetGeneral.getName() + "没有锦囊");
            return false;
        }
        // 随机抽取一个锦囊
        Collections.shuffle(silkBags);
        SilkBag remove = silkBags.remove(0);
        System.out.println("主公" + origin.getName() + "获得锦囊" + remove.name());
        origin.getBag().getSilkBags().add(remove);
        return true;
    }
}
