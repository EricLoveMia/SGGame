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

    public 顺手牵羊(int id, String name, int type) {
        super(id, name, type);
    }

    @Override
    public void run(General origin, General targetGeneral, City targetCity) {
        // 从另一个主公手中夺取一个锦囊
        List<SilkBag> silkBags = targetGeneral.getBag().getSilkBags();
        if (silkBags == null || silkBags.size() == 0) {
            System.out.println("主公" + getTargetGeneral().getName() + "没有锦囊");
            return;
        }
        // 随机抽取一个锦囊
        Collections.shuffle(silkBags);
        SilkBag remove = silkBags.remove(0);
        System.out.println("主公" + getOrigin().getName() + "获得锦囊" + remove.name());
        origin.getBag().getSilkBags().add(remove);
    }
}
