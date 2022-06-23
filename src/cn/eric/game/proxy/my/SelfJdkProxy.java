package cn.eric.game.proxy.my;

import java.lang.reflect.Method;

/**
 * Company: ClickPaaS
 *
 * @version 1.0.0
 * @description: 定义newInstance()方法创建代理对象，并实现InvocationHandler接口，重写invoke()方法，通过反射实现方法最终运行
 * @author: 钱旭
 * @date: 2022-03-11 16:02
 **/
public class SelfJdkProxy implements SelfInvocationHandler{

    private final SelfProxyInterface proxyInterface;

    public SelfJdkProxy(SelfProxyInterface proxyInterface) {
        this.proxyInterface = proxyInterface;
    }

    public Object instanceProxy() {
        System.out.println("代理方法开始...");
        SelfProxyInterface instance = (SelfProxyInterface) SelfProxy.newInstance(
                new SelfClassLoader(),
                proxyInterface.getClass().getInterfaces(), this);
        return instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        try {
            method.invoke(proxyInterface, args);
            System.out.println("代理方法结束...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
