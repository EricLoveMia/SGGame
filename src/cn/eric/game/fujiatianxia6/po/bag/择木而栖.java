package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 择木而栖
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 择木而栖 extends SilkBag {

    public 择木而栖(int id, String name, String memo, boolean active, int type, int aim) {
        super(id, name, memo, active, type, aim);
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 减少城市的兵力
        Integer soilders = targetCity.getSoilders();
        int reduce = Integer.parseInt(targetGeneral.getIntelligence()) * 4
                + Integer.parseInt(targetGeneral.getCharm()) * 2;
        if(soilders <= reduce) {
            reduce = soilders;
        }
        System.out.println("从城市" + targetCity.getName() + "中投诚剑兵" + reduce);
        targetCity.setSoilders(soilders - reduce);
        origin.setArmy(origin.getArmy() + reduce);
        return true;
    }
}
