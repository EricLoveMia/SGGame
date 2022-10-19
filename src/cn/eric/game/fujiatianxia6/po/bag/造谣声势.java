package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 造谣声势
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 造谣声势 extends SilkBag {

    public 造谣声势(int id, String name, int type, int aim) {
        super(id, name, type, aim);
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 减少声望
        System.out.println(targetGeneral.getName() + "减少声望100点");
        targetGeneral.setReputation(targetGeneral.getReputation() - 100);
        return true;
    }
}
