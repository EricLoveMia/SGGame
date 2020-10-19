package cn.eric.game.fujiatianxia6.service.event;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName EventFactory
 * @Description: 事件工厂
 * @Author YCKJ2725
 * @Date 2020/9/18
 * @Version V1.0
 **/
public class EventFactory {

    private static Map<String, Event> eventMap = new HashMap<>();

    // 注册到工厂中
    public static void register(String name, Event event) {
        eventMap.put(name, event);
    }

    public static Map<String, Event> getEventMap() {
        return eventMap;
    }
}
