package cn.eric.game.fujiatianxia6.service;

import cn.eric.game.fujiatianxia6.po.General;

import java.util.Random;
import java.util.Scanner;

/**
 * @ClassName CasinoService
 * @Description: 赌场服务
 * @Author YCKJ2725
 * @Date 2021/6/21
 * @Version V1.0
 **/
public class CasinoService {
    public static void guess(General general) {
        System.out.println(general.getName() + " 进入赌场");
        // 一共三个骰子，共18点  3到10为小，11到18为大
        int[] dice = new int[3];
        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 20; j++) {
                dice[i] = rand.nextInt(6) + 1;
            }
        }
        // 可猜大小  单双
        System.out.println("一共三个骰子，共18点  3到10为小，11到18为大");
        System.out.println("猜大小 奖励比例1:1 猜单双 奖励比例 1:1 ,请下注 0 为放弃");
        // 下注
        int counter;
        int bigOrSmall = 1;
        int singleOrDouble = 1;
        if (general.isReboot()) {
            if (general.getMoney() > 5000) {
                counter = 1000;
            } else if (general.getMoney() > 3000) {
                counter = 500;
            } else if (general.getMoney() > 1000) {
                counter = 200;
            } else {
                return;
            }
            // 大小

        } else {
            Scanner input = new Scanner(System.in);
            counter = input.nextInt();
            if (counter == 0) {
                return;
            }
            while (counter > general.getMoney() || counter < 0) {
                System.out.println("太大 或小于0 请重新下注");
                counter = input.nextInt();
                if (counter == 0) {
                    break;
                }
            }
            if (counter == 0) {
                return;
            }
            System.out.println("请选大 1  小 2");
            input = new Scanner(System.in);
            bigOrSmall = input.nextInt();
            while (bigOrSmall > 2 || bigOrSmall < 1) {
                System.out.println("请选大 1  小 2");
                bigOrSmall = input.nextInt();
            }

            System.out.println("请单 1  双 2");
            singleOrDouble = input.nextInt();
            while (singleOrDouble > 2 || singleOrDouble < 1) {
                System.out.println("请选单 1  双 2");
                singleOrDouble = input.nextInt();
            }
        }
        // 赌博特技
        if ("72".equals(general.getSkill())) {
            if (new Random().nextInt(100) < 90) {
                // 大 单
                if (bigOrSmall == 1 && singleOrDouble == 1) {
                    dice[0] = 6;
                    dice[1] = 6;
                    dice[2] = 1;
                }
                // 大双
                else if (bigOrSmall == 1) {
                    dice[0] = 6;
                    dice[1] = 6;
                    dice[2] = 1;
                }
                // 小单
                else if (singleOrDouble == 1) {
                    dice[0] = 1;
                    dice[1] = 3;
                    dice[2] = 1;
                }
                // 小双
                else {
                    dice[0] = 2;
                    dice[1] = 2;
                    dice[2] = 2;
                }
            }
        }

        int sum = dice[0] + dice[1] + dice[2];
        System.out.println("三个数字分别是" + dice[0] + " " + dice[1] + " " + dice[2] + " ,合计:" + sum);
        boolean booleanBigOrSmall = sum >= 10;
        boolean booleanOddOrEven = sum % 2 == 0;
        System.out.println("大小 " + (booleanBigOrSmall ? "大" : "小") + ",单双 " + (booleanOddOrEven ? "双" : "单"));
        System.out.println("下注 " + (bigOrSmall == 1 ? "大" : "小") + ",单双 " + (singleOrDouble == 2 ? "双" : "单"));
        int win;
        // 先看大小  bigOrSmall == 1 大
        if ((booleanBigOrSmall && bigOrSmall == 1) || (!booleanBigOrSmall && bigOrSmall == 2)) {
            win = counter + counter;
        } else {
            general.setMoney(general.getMoney() - counter);
            return;
        }
        // 再看单双 singleOrDouble == 1 单
        if ((!booleanOddOrEven && singleOrDouble == 1) || (booleanOddOrEven && singleOrDouble == 2)) {
            win = win * 2;
            System.out.println("赌博获利" + (win - counter) + " 金");
            general.setMoney(general.getMoney() + win - counter);
        } else {
            general.setMoney(general.getMoney() - counter);
            return;
        }


    }
}
