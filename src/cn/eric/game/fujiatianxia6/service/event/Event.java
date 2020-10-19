package cn.eric.game.fujiatianxia6.service.event;

import cn.eric.game.fujiatianxia6.po.General;

/**
 * @ClassName Event
 * @Description: 事件抽象类
 * @Author YCKJ2725
 * @Date 2020/9/18
 * @Version V1.0
 **/
public abstract class Event {

    /**
     * 挑战是否成功
     */
    private Boolean success;

    private EventSingle eventSingle;

    public abstract void initialize();

    public abstract void startPlay(General general);

    public abstract void endPlay(General general);

    public abstract void registr();

    // 模板
    public final void trigger(General general) {

        // 初始化事件
        // initialize();

        // 开始事件
        startPlay(general);

        // 结束事件，进行奖励或者惩罚
        endPlay(general);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public EventSingle getEventSingle() {
        return eventSingle;
    }

    public void setEventSingle(EventSingle eventSingle) {
        this.eventSingle = eventSingle;
    }
}
