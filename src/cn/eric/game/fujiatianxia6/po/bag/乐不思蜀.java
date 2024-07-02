package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.Game;

/**
 * @version 1.0.0
 * @description: 乐不思蜀
 * @author: eric
 * @date: 2022-10-17 11:40
 **/
public class 乐不思蜀 extends SilkBag {

    public 乐不思蜀(int id, String name, String memo, boolean active, int type, int aim) {
        super(id, name, memo, active, type, aim);
    }

    @Override
    public boolean run(General origin, General targetGeneral, City targetCity) {
        // 目标主公1个回合内无法行动
        String[] goAndStop = Game.getGoAndStop();
        General[] players = Game.getPlayers();
        for (int i = 0; i < players.length; i++) {
            if (players[i].getId().equals(targetGeneral.getId())) {
                goAndStop[i] = "off";
                System.out.println(targetGeneral.getName() + "停止行动一回合");
                break;
            }
        }
        return true;
    }
}
