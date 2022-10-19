package cn.eric.game.fujiatianxia6.service.event;

import cn.eric.game.fujiatianxia6.util.WeightRandom;

/**
 * @ClassName EventFactory
 * @Description: 事件工厂
 * @Author YCKJ2725
 * @Date 2020/9/18
 * @Version V1.0
 **/
public class EventFactory {

    private static WeightRandom<Event> eventMap;

    // 注册到工厂中
    public static void register(Integer weight, Event event) {
        if (eventMap == null) {
            eventMap = WeightRandom.create();
        }
        eventMap.add(event, weight);
    }

    public static WeightRandom<Event> getEventMap() {
        return eventMap;
    }
}
