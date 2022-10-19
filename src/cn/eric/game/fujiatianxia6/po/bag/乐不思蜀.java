package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

/**
 * @version 1.0.0
 * @description: 乐不思蜀
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 乐不思蜀 extends SilkBag {

    public 乐不思蜀(int id, String name, int type, int aim) {
        super(id, name, type, aim);
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 目标主公一个回合内无法行动
        return true;
    }
}
