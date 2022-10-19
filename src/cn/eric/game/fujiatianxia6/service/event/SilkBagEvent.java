package cn.eric.game.fujiatianxia6.service.event;

import cn.eric.game.fujiatianxia6.factory.SilkBagFactory;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.po.bag.SilkBag;
import cn.eric.game.fujiatianxia6.service.event.reward.EventReward;
import cn.eric.game.fujiatianxia6.service.event.reward.SilkBagReward;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @version 1.0.0
 * @description: 锦囊类
 * @author: eric
 * @date: 2022-10-18 11:27
 **/
public class SilkBagEvent extends Event {

    private List<String> messages;

    private String name;

    @Override
    public void initialize() {
        messages = new ArrayList<>();
        messages.add("%s将军熬夜苦读三国演义，灵机一动想到%s之计");
        messages.add("%s将军对著流星许愿，一不小心想出了%s之计");
        messages.add("%s将军被债主上门避债，情急之下想到了%s之计");
        messages.add("%s将军偷看隔壁大婶换衣服，灵机一动想到%s之计");
        messages.add("%s将军被卤蛋噎著而昏迷，急救苏醒后突然想到%s之计");
        messages.add("%s将军被神明附体，授予主公%s之计");
        messages.add("%s将军看见溪中小鱼逆游而上，灵机一动想到%s之计");
        messages.add("%s将军七天七夜不睡觉，苦思之后得到%s之计");
        messages.add("%s将军在茅房里认真出恭时，灵机一动想到%s之计");
        messages.add("%s将军被树上掉落的苹果砸到头，灵机一动想到%s之计");

        EventReward reward = new SilkBagReward("获得锦囊", "", "0", null);
        EventSingleWrapper eventSingleWrapper = new EventSingleWrapper(reward);
        eventSingleWrapper.setMemo("锦囊事件");
        this.setEventSingle(eventSingleWrapper);
    }

    @Override
    public void startPlay(General general) {
        // 获得一个锦囊
        Collections.shuffle(messages);
        String str = messages.get(0);
        int size = general.getGenerals().size();
        General gen = general.getGenerals().get(new Random().nextInt(size));
        // 随机一个锦囊
        List<SilkBag> all = SilkBagFactory.getAll();
        Collections.shuffle(all);
        SilkBag silkBag = all.get(0);
        System.out.println(String.format(str, gen.getName(), silkBag.getName()));
        this.name = silkBag.getName();
        this.setSuccess(true);
        this.getEventSingle().getReward().setDataGive(this.name);
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
