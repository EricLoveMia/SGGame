package cn.eric.game.proxy.my;

import java.lang.reflect.Method;

/**
 * Company: ClickPaaS
 *
 * @version 1.0.0
 * @description:
 * @author: 钱旭
 * @date: 2022-03-11 16:01
 **/
public interface SelfInvocationHandler {
    Object invoke(Object proxy, Method method, Object[] args);
}
