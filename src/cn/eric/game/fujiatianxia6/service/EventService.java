package cn.eric.game.fujiatianxia6.service;

import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.event.*;
import cn.eric.game.fujiatianxia6.util.WeightRandom;

import java.util.Random;
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
        fightEvent.registr(10);

        FightEventAttack fightEventAttack = new FightEventAttack();
        fightEventAttack.initialize();
        fightEventAttack.registr(10);
        //
        FortuneAttackEvent fortuneAttackEvent = new FortuneAttackEvent();
        fortuneAttackEvent.initialize();
        fortuneAttackEvent.registr(10);

        FortuneCommandEvent fortuneCommandEvent = new FortuneCommandEvent();
        fortuneCommandEvent.initialize();
        fortuneCommandEvent.registr(10);

        FortuneIntelligenceEvent fortuneIntelligenceEvent = new FortuneIntelligenceEvent();
        fortuneIntelligenceEvent.initialize();
        fortuneIntelligenceEvent.registr(10);

        // 计谋事件
        SilkBagEvent silkBagEvent = new SilkBagEvent();
        silkBagEvent.initialize();
        silkBagEvent.registr(50);
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
        // 根据主帅的魅力 触发事件
        Integer charm = Integer.parseInt(general.getCharm());
        if (new Random().nextInt(100) < (30 + charm / 5)) {
            WeightRandom<Event> eventMap = EventFactory.getEventMap();
            return eventMap.next();
        }
        return null;
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
            if (event instanceof SilkBagEvent
                    || event instanceof FortuneAttackEvent
                    || event instanceof FortuneCommandEvent
                    || event instanceof FortuneIntelligenceEvent) {
                event.trigger(general);
            }
        } else {
            if (event instanceof FightEventAttack || event instanceof FightEvent) {
                System.out.println("请问是否挑战 1 是 其他 否");
                Scanner input = new Scanner(System.in);
                int choise = input.nextInt();
                if (choise == 1) {
                    event.trigger(general);
                }
            } else {
                event.trigger(general);
            }
        }
    }
}
