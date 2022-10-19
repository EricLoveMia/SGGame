package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.factory.GeneralFactory;
import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 虚张声势
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 虚张声势 extends SilkBag {

    public 虚张声势(int id, String name, int type) {
        super(id, name, type);
    }

    @Override
    protected General chooseTargetGeneral(General origin) {
        return null;
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 增加声望
        General general = GeneralFactory.getGeneralById(targetGeneral.getBelongTo());
        System.out.println(general.getName() + "增加声望100点");
        general.setReputation(general.getReputation() + 100);
        return true;
    }
}
