package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

import java.util.Collections;
import java.util.List;

/**
 * @version 1.0.0
 * @description: 过河拆桥
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 过河拆桥 extends SilkBag {

    public 过河拆桥(int id, String name, int type) {
        super(id, name, type);
    }

    @Override
    public void run(General origin, General targetGeneral, City targetCity) {
        // 从另一个主公拆掉一个锦囊
        List<SilkBag> silkBags = targetGeneral.getBag().getSilkBags();
        if (silkBags == null || silkBags.size() == 0) {
            System.out.println("主公" + getTargetGeneral().getName() + "没有锦囊");
            return;
        }
        // 随机拆掉一个锦囊
        Collections.shuffle(silkBags);
        SilkBag remove = silkBags.remove(0);
        System.out.println("主公" + getTargetGeneral().getName() + "遗失锦囊" + remove.name());
    }

}
