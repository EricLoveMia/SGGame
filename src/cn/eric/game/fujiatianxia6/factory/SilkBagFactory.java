package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.po.bag.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 * @description: 锦囊工厂
 * @author: eric
 * @date: 2022-10-17 15:55
 **/
public class SilkBagFactory {

    private static final List<SilkBag> silkBagList;
    private static final Map<String, SilkBag> silkBagMap;

    static {
        silkBagList = new ArrayList<>();
        silkBagList.add(new 瞒天过海(1, "瞒天过海", "瞒天过海", true, 1, 0));
        silkBagList.add(new 乐不思蜀(2, "乐不思蜀", "乐不思蜀", true, 2, 1));
        silkBagList.add(new 财源广进(6, "财源广进", "财源广进", true, 3, 0));
        silkBagList.add(new 择木而栖(8, "择木而栖", "吸引敌方城池中的士兵来投诚", true, 4, 0));
        silkBagList.add(new 无懈可击(9, "无懈可击", "无懈可击", false, 1, 0));
        silkBagList.add(new 人去楼空(10, "人去楼空", "减少敌方城池中的士兵数量", true, 4, 0));
        silkBagList.add(new 草木皆兵(11, "草木皆兵", "草木皆兵", true, 3, 0));
        silkBagList.add(new 虚张声势(12, "虚张声势", "虚张声势", true, 1, 0));
        silkBagList.add(new 造谣声势(12, "造谣声势", "降低敌方主公的声望", true, 2, 0));
        silkBagList.add(new 过河拆桥(15, "过河拆桥", "虚张声势", true, 4, 2));
        silkBagList.add(new 生财有道(16, "生财有道", "生财有道", true, 3, 0));
        silkBagList.add(new 顺手牵羊(17, "顺手牵羊", "顺手牵羊", true, 2, 2));
        silkBagList.add(new 五鬼搬运(18, "五鬼搬运", "五鬼搬运", true, 2, 3));

        // silkBagList.add(new 临危不乱(18, "临危不乱", 2));

        silkBagMap = silkBagList.stream().collect(Collectors.toMap(SilkBag::getName, e -> e));

    }

    public static SilkBag getByName(String name) throws CloneNotSupportedException {
        SilkBag silkBag = silkBagMap.get(name);
        return silkBag;
    }

    public static List<SilkBag> getAll() {
        return silkBagList;
    }

}
