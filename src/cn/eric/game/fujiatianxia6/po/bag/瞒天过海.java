package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.Game;

/**
 * @version 1.0.0
 * @description: 瞒天过海
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 瞒天过海 extends SilkBag {

    public 瞒天过海(int id, String name, int type) {
        super(id, name, type);
    }

    @Override
    protected General chooseTargetGeneral(General origin) {
        return null;
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 其他人暂停，自己继续
        String[] goAndStop = Game.getGoAndStop();
        General[] players = Game.getPlayers();
        for (int i = 0; i < players.length; i++) {
            if (players[i].getId().equals(origin.getId())) {
                goAndStop[i] = "on";
            } else {
                goAndStop[i] = "off";
            }
        }
        return true;
    }

}
