package cn.eric.game.fujiatianxia6.service.event;

import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.event.reward.EventReward;

/**
 * @ClassName EventSingle
 * @Description: 事件定义抽象类
 * @Author YCKJ2725
 * @Date 2020/9/18
 * @Version V1.0
 **/
public abstract class EventSingle {

    private String name;

    private String type;

    private String memo;

    /**
     * 要满足的值
     */
    private String data;

    /**
     * 奖励
     */
    private EventReward reward;

    public abstract void reward(General general, Boolean success);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public EventReward getReward() {
        return reward;
    }

    public void setReward(EventReward reward) {
        this.reward = reward;
    }
}
