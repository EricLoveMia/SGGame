package cn.eric.game.fujiatianxia6.service.event.reward;

import cn.eric.game.fujiatianxia6.po.General;

/**
 * @ClassName MoneyReward
 * @Description: 金钱奖励与惩罚
 * @Author YCKJ2725
 * @Date 2020/9/18
 * @Version V1.0
 **/
public class MoneyReward extends EventReward {

    public MoneyReward(String name, String dataGive, String dataPunish) {
        super.setName(name);
        super.setDataGive(dataGive);
        super.setDataPunish(dataPunish);
    }

    @Override
    public void reward(General general, Boolean success) {
        if (success) {
            System.out.println("获得金钱奖励" + this.getDataGive());
            general.setMoney(general.getMoney() + Integer.parseInt(this.getDataGive()));
        } else {
            System.out.println("获得金钱惩罚" + this.getDataGive());
            general.setMoney(general.getMoney() - Integer.parseInt(this.getDataPunish()));
        }
    }
}
