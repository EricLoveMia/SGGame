package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 临危不乱
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 临危不乱 extends SilkBag {

    public 临危不乱(int id, String name, String memo, boolean active, int type, int aim) {
        super(id, name, memo, active, type, aim);
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 在遇到负面事件的时候免除效果

        return false;
    }
}
