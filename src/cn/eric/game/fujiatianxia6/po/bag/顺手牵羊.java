package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.Game;

import java.util.*;

/**
 * @version 1.0.0
 * @description: 顺手牵羊
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 顺手牵羊 extends SilkBag {

    public 顺手牵羊(int id, String name, int type) {
        super(id, name, type);
    }

    @Override
    protected City findOneOfOtherCity(General origin) {
        return null;
    }

    @Override
    protected City findOneOfSelfCity(General origin) {
        return null;
    }

    @Override
    protected General chooseTargetGeneral(General origin) {
        System.out.println("请选择您要顺手牵羊的主公");
        General[] players = Game.getPlayers();
        Map<Integer, General> playerMap = new HashMap<>();
        Integer index = 1;
        for (General player : players) {
            if (player.getStatus().equals("4")) {
                continue;
            }
            List<SilkBag> silkBags = player.getBag().getSilkBags();
            if (silkBags == null || silkBags.size() == 0) {
                continue;
            }
            playerMap.put(index++, player);
        }
        if (playerMap.size() == 0) {
            System.out.println("没有其他主公拥有锦囊");
            return null;
        }
        for (int i = 1; i < index; i++) {
            System.out.println(i + "：" + playerMap.get(i).getName() + ",锦囊数" + playerMap.get(i).getBag().getSilkBags().size());
        }
        Scanner input = new Scanner(System.in);
        int choise = input.nextInt();
        while (choise != 0) {
            if (choise > index || choise < 0) {
                System.out.println("请谨慎选择 0 表示放弃");
            } else {
                return playerMap.get(choise);
            }
            choise = input.nextInt();
        }
        return null;
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 从另一个主公手中夺取一个锦囊
        List<SilkBag> silkBags = targetGeneral.getBag().getSilkBags();
        if (silkBags == null || silkBags.size() == 0) {
            System.out.println("主公" + targetGeneral.getName() + "没有锦囊");
            return false;
        }
        // 随机抽取一个锦囊
        Collections.shuffle(silkBags);
        SilkBag remove = silkBags.remove(0);
        System.out.println("主公" + origin.getName() + "获得锦囊" + remove.name());
        origin.getBag().getSilkBags().add(remove);
        return true;
    }
}
