package cn.eric.game.fujiatianxia6.service.event;

import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.event.reward.EventReward;

/**
 * @ClassName FightEventSingleA
 * @Description: 单一事件
 * @Author eric
 * @Date 2020/9/18
 * @Version V1.0
 **/
public class EventSingleWrapper extends EventSingle {

    public EventSingleWrapper(EventReward reward) {
        super.setReward(reward);
    }


    @Override
    public void reward(General general, Boolean success) {
        this.getReward().reward(general, success);
    }
}
