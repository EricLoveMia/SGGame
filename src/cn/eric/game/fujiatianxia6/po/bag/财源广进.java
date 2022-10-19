package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 财源广进
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 财源广进 extends SilkBag {

    public 财源广进(int id, String name, int type) {
        super(id, name, type);
    }

    @Override
    protected General chooseTargetGeneral(General origin) {
        return null;
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 城市直接增加繁荣度
        int add = (Integer.parseInt(targetGeneral.getPolitics()) + Integer.parseInt(targetGeneral.getIntelligence())) / 5;
        System.out.println("城市" + targetCity.getName() + "增加繁荣度" + add + "点");
        targetCity.setProsperity(targetCity.getProsperity() + add);
        return true;
    }
}
