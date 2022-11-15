package cn.eric.game.fujiatianxia6.service.event;

import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.event.reward.AbilityReward;
import cn.eric.game.fujiatianxia6.service.event.reward.EventReward;

import java.util.Scanner;

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
        System.out.println("增加一定的金钱，可以增大效果哦 0 放弃 ,当前金钱" + general.getMoney());
        System.out.println("1、花费2000金增加1点属性（共增加2点）");
        System.out.println("2、花费5000金增加2点属性（共增加3点）");
        System.out.println("3、花费10000金增加3点属性（共增加4点）");
        if (general.isReboot()) {
            return;
        }
        Scanner input = new Scanner(System.in);
        try {
            int choise = input.nextInt();
            switch (choise) {
                case 1:
                    this.getEventSingle().setData("2");
                    this.getEventSingle().getReward().setDataGive("2");
                    this.getEventSingle().getReward().setCostMoney(2000);
                    break;
                case 2:
                    this.getEventSingle().setData("3");
                    this.getEventSingle().getReward().setDataGive("3");
                    this.getEventSingle().getReward().setCostMoney(5000);
                    break;
                case 3:
                    this.getEventSingle().setData("4");
                    this.getEventSingle().getReward().setDataGive("4");
                    this.getEventSingle().getReward().setCostMoney(10000);
                    break;
                default:
                    break;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void endPlay(General general) {
        this.getEventSingle().reward(general, this.getSuccess());
        this.getEventSingle().setData("1");
        this.getEventSingle().getReward().setDataGive("1");
        this.getEventSingle().getReward().setCostMoney(0);
    }

    @Override
    public void registr(int weight) {
        EventFactory.register(weight, this);
    }
}
