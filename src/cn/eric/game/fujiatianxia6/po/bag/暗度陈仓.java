package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 暗度陈仓
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 暗度陈仓 extends SilkBag {

    public 暗度陈仓(int id, String name, int type, int aim) {
        super(id, name, type, aim);
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 直接走到指定的位置

        return true;
    }

}
