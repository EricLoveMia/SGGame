package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.Building;
import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @version 1.0.0
 * @description: 过河拆桥
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 过河拆桥 extends SilkBag {
    public 过河拆桥(int id, String name, String memo, boolean active, int type, int aim) {
        super(id, name, memo, active, type, aim);
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 拆掉城市中的随机一座建筑
        List<Building> bildings = targetCity.getBildings();
        if(bildings.size() == 0) {
            System.out.println(targetCity.getName() + "没有建筑可以拆");
            return false;
        }

        int choose = new Random().nextInt(bildings.size());
        Building remove = bildings.get(choose);
        if(remove.level > 1) {
            remove.level = remove.level - 1;
            System.out.println(targetCity.getName() + "的建筑" + remove.name + "降一级");
        }
        else {
            bildings.remove(remove);
            System.out.println(targetCity.getName() + "的建筑" + remove.name + "被拆除");
        }

//        List<SilkBag> silkBags = targetGeneral.getBag().getSilkBags();
//        if (silkBags == null || silkBags.size() == 0) {
//            System.out.println("主公" + targetGeneral.getName() + "没有锦囊");
//            return false;
//        }
//        // 随机拆掉一个锦囊
//        SilkBag remove = silkBags.remove(new Random().nextInt(silkBags.size()));
//        System.out.println("主公" + targetGeneral.getName() + "遗失锦囊" + remove.name());
        return true;
    }

}
