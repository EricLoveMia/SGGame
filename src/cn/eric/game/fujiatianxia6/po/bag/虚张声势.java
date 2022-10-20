package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 虚张声势
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 虚张声势 extends SilkBag {

    public 虚张声势(int id, String name, String memo, boolean active, int type, int aim) {
        super(id, name, memo, active, type, aim);
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 增加声望
        System.out.println(origin.getName() + "增加声望200点");
        origin.setReputation(origin.getReputation() + 200);
        return true;
    }
}
