package cn.eric.game.fujiatianxia6.service.event.reward;

import cn.eric.game.fujiatianxia6.factory.SilkBagFactory;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.po.bag.SilkBag;

/**
 * @version 1.0.0
 * @description: 锦囊奖励
 * @author: eric
 * @date: 2022-10-18 11:32
 **/
public class SilkBagReward extends EventReward {

    public SilkBagReward(String name, String dataGive, String dataPunish, String type) {
        super(name, dataGive, dataPunish, type);
    }

    @Override
    public void reward(General general, Boolean success) {
        try {
            SilkBag silkBag = SilkBagFactory.getByName(getDataGive());
            general.getBag().getSilkBags().add(silkBag);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }
}
