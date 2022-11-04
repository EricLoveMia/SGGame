package cn.eric.game.fujiatianxia6.service.event;

import cn.eric.game.fujiatianxia6.enums.FightSingleAttackTypeEnum;
import cn.eric.game.fujiatianxia6.factory.GeneralFactory;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.Fight;
import cn.eric.game.fujiatianxia6.service.event.reward.EventReward;
import cn.eric.game.fujiatianxia6.service.event.reward.MoneyReward;

/**
 * @ClassName FightEvent
 * @Description: 单挑事件
 * @Author YCKJ2725
 * @Date 2020/9/18
 * @Version V1.0
 **/
public class FightEventAttack extends Event {

    private final General generalDead = new General();

    @Override
    public void initialize() {
        // 获得单挑事件info
        EventReward reward = new MoneyReward("金钱奖励", "5000", "2000");
        EventSingleWrapper eventSingleWrapper = new EventSingleWrapper(reward);
        eventSingleWrapper.setMemo("单挑上古武将，10回合造成100点伤害，获得奖励，否则惩罚");
        this.setEventSingle(eventSingleWrapper);
        generalDead.setName("神龟将军");
        generalDead.setAttack("1");
        generalDead.setCommand("190");
        generalDead.setIntelligence("100");
        generalDead.setPolitics("100");
        generalDead.setVitality("1000");
        generalDead.setSkill("99");
    }

    @Override
    public void startPlay(General general) {
        // 开始单挑
        System.out.println("开始单挑,10回合造成100点伤害 获得奖励 挑战成功奖励 " + this.getEventSingle().getReward().getDataGive() + "," +
                "挑战失败惩罚" + this.getEventSingle().getReward().getDataPunish());
        System.out.println("请选择出战的武将，0 放弃");
        General generalByChoose;
        // 机器人自动选择
        generalByChoose = GeneralFactory.getGeneralByChoose(general,
                GeneralFactory.getAoundGeneral(general.getGenerals()), 3, null);
        boolean pure = Fight.fightPure(generalByChoose, generalDead, 10, FightSingleAttackTypeEnum.DEFENCE.getCode());
        if (pure) {
            this.setSuccess(Boolean.TRUE);
        } else {
            this.setSuccess(Boolean.FALSE);
        }
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
