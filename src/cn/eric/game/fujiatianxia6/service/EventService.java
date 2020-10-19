package cn.eric.game.fujiatianxia6.service;

import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.event.Event;
import cn.eric.game.fujiatianxia6.service.event.EventFactory;
import cn.eric.game.fujiatianxia6.service.event.FightEvent;

import java.util.Scanner;

/**
 * @ClassName EventService
 * @Description: 事件服务类
 * @Author YCKJ2725
 * @Date 2020/9/18
 * @Version V1.0
 **/
public class EventService {

    static {
        // 初始化所有事件
        // 单挑事件
        FightEvent fightEvent = new FightEvent();
        fightEvent.initialize();
        fightEvent.registr();

        // 脑筋急转弯事件

        //
    }

    /**
     * @MethodName: roundTrigger
     * @Description: 触发事件
     * @Param: [general]
     * @Return: cn.eric.game.fujiatianxia6.service.event.Event
     * @Author: YCKJ2725
     * @Date: 2020/10/13 17:56
     **/
    public static Event roundTrigger(General general) {
        return EventFactory.getEventMap().get("单挑事件");
    }

    /**
     * @MethodName: beginEvent
     * @Description: 执行事件
     * @Param: [general, event]
     * @Return: void
     * @Author: YCKJ2725
     * @Date: 2020/10/13 17:58
     **/
    public static void beginEvent(General general, Event event) {
        System.out.println("触发事件" + event.getEventSingle().getMemo());
        if (general.isReboot()) {
            event.trigger(general);
        } else {
            System.out.println("请问是否挑战 1 是 其他 否");
            Scanner input = new Scanner(System.in);
            int choise = input.nextInt();
            if (choise == 1) {
                event.trigger(general);
            }
        }
    }
}
