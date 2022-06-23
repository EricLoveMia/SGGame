package cn.eric.game.proxy.jdk;

/**
 * Company: ClickPaaS
 *
 * @version 1.0.0
 * @description: 实现类
 * @author: 钱旭
 * @date: 2022-03-11 15:42
 **/
public class JDKProxyTarget implements ProxyInterface{
    @Override
    public void targetMethod() {
        System.out.println("代理方法...");
    }
}
