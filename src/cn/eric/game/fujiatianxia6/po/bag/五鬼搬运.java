package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.po.store.Goods;

import java.util.Collections;
import java.util.List;

/**
 * @version 1.0.0
 * @description: 五鬼搬运
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 五鬼搬运 extends SilkBag {

    public 五鬼搬运(int id, String name, int type, int aim) {
        super(id, name, type, aim);
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 从另一个主公手中夺取一个特产
        List<Goods> goodsList = targetGeneral.getTransportTeam().getGoodsList();
        if (goodsList == null || goodsList.size() == 0) {
            System.out.println("主公" + targetGeneral.getName() + "没有特产品");
            return false;
        }
        // 随机获得一个特产
        Collections.shuffle(goodsList);
        Goods remove = goodsList.remove(0);
        System.out.println("主公" + origin.getName() + "获得特产" + remove.getName());
        origin.getTransportTeam().getGoodsList().add(remove);
        return true;
    }
}
