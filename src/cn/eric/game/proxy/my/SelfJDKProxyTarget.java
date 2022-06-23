package cn.eric.game.proxy.my;

/**
 * Company: ClickPaaS
 *
 * @version 1.0.0
 * @description: 实现类
 * @author: 钱旭
 * @date: 2022-03-11 15:55
 **/
public class SelfJDKProxyTarget implements SelfProxyInterface{
    @Override
    public void targetMethod() {
        System.out.println("代理方法...");
    }
}
