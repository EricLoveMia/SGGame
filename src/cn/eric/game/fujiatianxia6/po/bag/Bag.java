package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 锦囊
 * @author: eric
 * @date: 2022-10-17 11:33
 **/
public interface Bag {

    void execute(General origin, General targetGeneral, City targetCity);

    String name();
}
