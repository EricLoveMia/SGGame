package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.po.store.Goods;
import cn.eric.game.fujiatianxia6.service.Game;

import java.util.*;

/**
 * @version 1.0.0
 * @description: 五鬼搬运
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 五鬼搬运 extends SilkBag {

    public 五鬼搬运(int id, String name, int type) {
        super(id, name, type);
    }

    @Override
    protected General chooseTargetGeneral(General origin) {
        System.out.println("请选择您要五鬼搬运的主公");
        General[] players = Game.getPlayers();
        Map<Integer, General> playerMap = new HashMap<>();
        Integer index = 1;
        for (General player : players) {
            if (player.getStatus().equals("4")) {
                continue;
            }
            List<Goods> goods = player.getTransportTeam().getGoodsList();
            if (goods == null || goods.size() == 0) {
                continue;
            }
            playerMap.put(index++, player);
        }
        if (playerMap.size() == 0) {
            System.out.println("没有其他主公拥有特产品");
            return null;
        }
        for (int i = 1; i < index; i++) {
            System.out.println(i + "：" + playerMap.get(i).getName() + ",特产数" + playerMap.get(i).getTransportTeam().getGoodsList().size());
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
        // 从另一个主公手中夺取一个特产
        List<Goods> goodsList = targetGeneral.getTransportTeam().getGoodsList();
        if (goodsList == null || goodsList.size() == 0) {
            System.out.println("主公" + targetGeneral.getName() + "没有特产品");
            return false;
        }
        // 随机获得一个特产
        Collections.shuffle(goodsList);
        Goods remove = goodsList.remove(0);
        System.out.println("主公" + origin.getName() + "获得特产" + remove.getName());
        origin.getTransportTeam().getGoodsList().add(remove);
        return true;
    }
}
