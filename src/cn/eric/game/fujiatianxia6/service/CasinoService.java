package cn.eric.game.fujiatianxia6.service;

import cn.eric.game.fujiatianxia6.po.General;

import java.util.Random;

/**
 * @ClassName CasinoService
 * @Description: TODO
 * @Author YCKJ2725
 * @Date 2021/6/21
 * @Version V1.0
 **/
public class CasinoService {
    public static void guess(General general) {
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
        System.out.println("猜大小 奖励比例1:1 猜单双 奖励比例 1:1  猜总和 奖励比例 1：8 ");
        int sum = dice[0] + dice[1] + dice[2];
        System.out.println("三个数字分别是" + dice[0] + " " + dice[1] + " " + dice[2] + " ,合计:" + sum);
        boolean booleanBigOrSmall = sum > 10;
        boolean booleanOddOrEven = sum % 2 == 0;
        System.out.println("大小 " + (booleanBigOrSmall ? "大" : "小") + ",单双 " + (booleanOddOrEven ? "双" : "单"));
    }
}
