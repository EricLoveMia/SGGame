package cn.eric.game.proxy.jdk;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Company: ClickPaaS
 *
 * @version 1.0.0
 * @description: 执行累
 * @author: 钱旭
 * @date: 2022-03-11 15:47
 **/
public class Main {

    public static void main(String[] args) throws IOException {
        JDKProxy proxy = new JDKProxy(new JDKProxyTarget());
        ProxyInterface instanceProxy = proxy.instanceProxy();
        instanceProxy.targetMethod();

        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{ProxyInterface.class});
        FileOutputStream outputStream = new FileOutputStream("output/$Proxy0.class");
        outputStream.write(bytes);
        outputStream.close();
    }
}
