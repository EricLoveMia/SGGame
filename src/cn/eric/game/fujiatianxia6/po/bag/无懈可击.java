package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 无懈可击
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 无懈可击 extends SilkBag {

    public 无懈可击(int id, String name, int type) {
        super(id, name, type);
    }

    @Override
    public void run(General origin, General targetGeneral, City targetCity) {
        // 自对自身的锦囊无效

    }

}
