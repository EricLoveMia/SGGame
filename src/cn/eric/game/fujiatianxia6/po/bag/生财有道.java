package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 生财有道
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 生财有道 extends SilkBag {

    public 生财有道(int id, String name, int type) {
        super(id, name, type);
    }

    @Override
    public void run(General origin, General targetGeneral, City targetCity) {
        // 城市直接增加发展金
        int add = 1000 + (Integer.parseInt(getTargetGeneral().getPolitics()) + Integer.parseInt(getTargetGeneral().getIntelligence())) * 2;
        System.out.println("城市" + getTargetCity().getName() + "增加发展金" + add + "金");
        getTargetCity().setMoney(getTargetCity().getMoney() + add);

    }

}
