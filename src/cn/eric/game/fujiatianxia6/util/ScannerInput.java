package cn.eric.game.fujiatianxia6.util;

import java.util.Scanner;

/**
 * @version 1.0.0
 * @description:
 * @author: eric
 * @date: 2022-09-19 13:54
 **/
public class ScannerInput {

    public static int inputInt(int min, int max, int quit){
        int result = quit;
        int errTimes = 0;
        while (true) {
            if(errTimes >= 5){
                break;
            }
            try {
                Scanner input = new Scanner(System.in);
                input.reset();
                int nextInt = input.nextInt();
                if((nextInt > max || nextInt < min) &&  nextInt != quit ){
                    System.out.println("输入有误，最大值" + max + ",最小值" + min + "，退出" + quit);
                    errTimes++;
                    continue;
                }
                result = nextInt;
                break;
            } catch (Exception exception) {
                System.out.println("输入有误，请重新输入");
                errTimes++;
            }
        }
        return result;
    }
}
