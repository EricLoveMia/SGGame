package cn.eric.game.proxy.my;

/**
 * Company: ClickPaaS
 *
 * @version 1.0.0
 * @description:
 * @author: 钱旭
 * @date: 2022-03-11 16:04
 **/
public class SelfTest {

    public static void main(String[] args) {
        SelfJdkProxy proxy = new SelfJdkProxy(new SelfJDKProxyTarget());
        SelfProxyInterface proxyInterface = (SelfProxyInterface) proxy.instanceProxy();
        System.out.println(proxyInterface);
        proxyInterface.targetMethod();
    }

}
