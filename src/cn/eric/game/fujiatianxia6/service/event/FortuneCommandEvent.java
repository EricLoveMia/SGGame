package cn.eric.game.fujiatianxia6.service.event;

import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.event.reward.AbilityReward;
import cn.eric.game.fujiatianxia6.service.event.reward.EventReward;

/**
 * @ClassName FortuneCommandEvent
 * @Description: 获得统帅属性增加的幸运事件
 * @Author YCKJ2725
 * @Date 2020/9/18
 * @Version V1.0
 **/
public class FortuneCommandEvent extends Event {

    @Override
    public void initialize() {
        // 获得单挑事件info
        EventReward reward = new AbilityReward("统帅奖励", "1", "0", EventReward.EventRewardTypeEnum.COMMAND.getCode());
        EventSingleWrapper eventSingleA = new EventSingleWrapper(reward);
        eventSingleA.setName("孙武事件");
        eventSingleA.setData("1");
        eventSingleA.setMemo("遇到孙武，可以给指定一名武将增加统帅");
        this.setEventSingle(eventSingleA);
    }

    @Override
    public void startPlay(General general) {

    }

    @Override
    public void endPlay(General general) {
        this.getEventSingle().reward(general, this.getSuccess());
    }

    @Override
    public void registr(int weight) {
        EventFactory.register(weight, this);
    }
}
