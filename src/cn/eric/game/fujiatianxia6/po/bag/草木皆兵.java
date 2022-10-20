package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 草木皆兵
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 草木皆兵 extends SilkBag {

    public 草木皆兵(int id, String name, String memo, boolean active, int type, int aim) {
        super(id, name, memo, active, type, aim);
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 城市的剑兵增加
        Integer soilders = targetCity.getSoilders();
        int add = 1000 + Integer.parseInt(targetGeneral.getCommand()) + Integer.parseInt(targetGeneral.getIntelligence())
                + Integer.parseInt(targetGeneral.getPolitics()) + Integer.parseInt(targetGeneral.getCharm());
        System.out.println("城市" + targetCity.getName() + "增加剑兵" + add);
        targetCity.setSoilders(soilders + add);
        return true;
    }

}
