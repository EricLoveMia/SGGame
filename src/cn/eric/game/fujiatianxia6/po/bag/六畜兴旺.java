package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description:
 * @author: eric
 * @date: 2022-10-17 11:45
 **/
public class 六畜兴旺 extends SilkBag {


    public 六畜兴旺(int id, String name, int type) {
        super(id, name, type);
    }

    @Override
    public void run(General origin, General targetGeneral, City targetCity) {
        // 直接走到指定的位置

    }

}
