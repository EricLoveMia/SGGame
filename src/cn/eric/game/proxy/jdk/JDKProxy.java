package cn.eric.game.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * Company: ClickPaaS
 *
 * @version 1.0.0
 * @description: 代理方法
 * @author: 钱旭
 * @date: 2022-03-11 15:43
 **/
public class JDKProxy {

    private final ProxyInterface proxyInterface;

    public JDKProxy(ProxyInterface proxyInterface) {
        this.proxyInterface = proxyInterface;
    }

    public ProxyInterface instanceProxy() {
        System.out.println("开始代理");
        ProxyInterface instance = (ProxyInterface) Proxy.newProxyInstance(
                proxyInterface.getClass().getClassLoader(),
                proxyInterface.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    method.invoke(proxyInterface, args);
                    System.out.println("代理方法结束...");
                    return null;
                }
        );
        return instance;
    }
}
