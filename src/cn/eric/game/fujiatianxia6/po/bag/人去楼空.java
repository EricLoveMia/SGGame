package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 人去楼空
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 人去楼空 extends SilkBag {

    public 人去楼空(int id, String name, String memo, boolean active, int type, int aim) {
        super(id, name, memo, active, type, aim);
    }


    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 减少城市的人口

        return false;
    }

}
