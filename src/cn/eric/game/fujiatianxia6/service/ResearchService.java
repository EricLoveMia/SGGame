package cn.eric.game.fujiatianxia6.service;

import cn.eric.game.fujiatianxia6.po.Arms;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.po.Rsearch;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Eric
 * @version 1.0
 * @ClassName: ResearchService
 * @Description: TODO
 * @company lsj
 * @date 2019/2/28 9:29
 **/
public class ResearchService {

    public static void researchAll() {
    }

    /**
     *
     * @menu
     * @description:
     * @author Eric
     * @date 18:13 2019/7/11
     * @param player
     * @throws
     * @return boolean
     **/
    public static boolean HasFree(General player) {
        // 先研究
        researchDay(player);
        return player.getResearch() == null || player.getResearch().getTime() == 0;
    }

    private static void researchDay(General player) {
        Rsearch research = player.getResearch();
        if(research != null){
            research.setTime(research.getTime() - 1);
            if(research.getTime() == 0){
                List<Arms> armsTotal = player.getArmsTotal();
                for (Arms arms : armsTotal) {
                    if(research.getName().equals(arms.getName())){
                        System.out.println("恭喜，研究完成" + arms.getName() + "级别变成" + research.getLevel());
                        arms.setLevel(research.getLevel());
                    }
                }
            }
        }
    }

    /**
     * 开始研究
     * @menu
     * @description: 科技研究
     * @author Eric
     * @date 18:13 2019/7/11
     * @param player
     * @throws
     * @return void
     **/
    public static void research(General player) {
        int num = 1;
        //
        System.out.println("您当前的科技情况是");
        for (Arms arms : player.getArmsTotal()) {
            System.out.println(num++ + ":" + arms.getName() + "级别：" + arms.getLevel() + ",升级金额" + arms.getLevel() * 1000);
        }

        // 选择要升级的科技
        System.out.println("请选择要升级的科技");
        int choose;
        Scanner input = null;
        if(player.isReboot()){
            if(player.getMoney() < 10000){
                choose = 0;
            }else {
                choose = new Random().nextInt(4) + 1;
            }
        }else {
            input = new Scanner(System.in);
            choose = input.nextInt();
        }
        while(choose>4 || choose<=0){
            if(choose == 0){
                return;
            }
            System.out.println("输入错误 请选择");
            num = 1;
            for (Arms arms : player.getArmsTotal()) {
                System.out.println(num++ + ":" + arms.getName() + "级别：" + arms.getLevel());
            }
            choose = input.nextInt();
        }

        if(player.getMoney() < player.getArmsTotal().get(choose - 1).getLevel() * 1000){
            System.out.println("对不起，金额不足，升级失败");
            return;
        }else{
            player.setMoney(player.getMoney() - player.getArmsTotal().get(choose - 1).getLevel() * 1000);
        }
        player.setResearch(new Rsearch(player.getArmsTotal().get(choose - 1).getName(),player.getArmsTotal().get(choose - 1).getLevel() + 1));
    }
}
