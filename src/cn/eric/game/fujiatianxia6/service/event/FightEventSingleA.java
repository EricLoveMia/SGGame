package cn.eric.game.fujiatianxia6.service.event;

import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.event.reward.EventReward;

/**
 * @ClassName FightEventSingleA
 * @Description: 单挑上古武将，坚持10回合不死获得5000金，失败惩罚2000金
 * @Author YCKJ2725
 * @Date 2020/9/18
 * @Version V1.0
 **/
public class FightEventSingleA extends EventSingle {

    public FightEventSingleA(EventReward reward) {
        super.setReward(reward);
    }


    @Override
    public void reward(General general, Boolean success) {
        this.getReward().reward(general, success);
    }
}
