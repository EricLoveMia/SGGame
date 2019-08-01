package cn.eric.game.fujiatianxia6.service;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.function.Function;

/**
 * @author Eric
 * @version 1.0
 * @ClassName: FunctionService
 * @Description: TODO
 * @company lsj
 * @date 2019/7/29 11:33
 **/
public class FunctionService {


    public static Object setMoneyByFunction(int money, Function function){
        return function.apply(money);
    }
}
