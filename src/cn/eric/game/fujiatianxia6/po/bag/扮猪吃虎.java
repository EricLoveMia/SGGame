package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 扮猪吃虎 被动
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 扮猪吃虎 extends SilkBag {

    public 扮猪吃虎(int id, String name, String memo, boolean active, int type, int aim) {
        super(id, name, memo, active, type, aim);
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 直接让敌方支付


        return true;
    }
}
