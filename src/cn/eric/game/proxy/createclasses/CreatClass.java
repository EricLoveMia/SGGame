package cn.eric.game.proxy.createclasses;

import cn.eric.game.proxy.my.SelfClassLoader;
import cn.eric.game.proxy.my.SelfInvocationHandler;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Company: ClickPaaS
 *
 * @version 1.0.0
 * @description:
 * @author: 钱旭
 * @date: 2022-03-11 15:59
 **/
public class CreatClass {
    private static final String ENTER = "\r\n";

    public static Object newInstance(SelfClassLoader classLoader, Class<?>[] interfaces, SelfInvocationHandler h) {
        try {
            // 动态生成源代码
            String srcClass = generateSrc(interfaces);
            // 输出Java文件
            String filePath = CreatClass.class.getResource("").getPath()  + "$ProxyO.java";
            System.out.println(filePath);
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(srcClass);
            fileWriter.flush();
            fileWriter.close();
            // 编译Java文件为class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            Iterable iterable = fileManager.getJavaFileObjects(filePath);
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, iterable);
            task.call();
            fileManager.close();
            // 加载编译生成的class文件到JVM
            Class<?> proxyClass = classLoader.findClass("$ProxyO");
            Constructor<?> constructor = proxyClass.getConstructor(SelfInvocationHandler.class);
            // 删掉虚拟代理类
            File file = new File(filePath);
            file.delete();
            // 返回字节码重组以后的代理对象
            return constructor.newInstance(h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String generateSrc(Class<?>[] interfaces) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("package cn.eric.game.proxy.my;" + ENTER + ENTER);
        stringBuilder.append("import cn.eric.game.proxy.my.SelfProxyInterface;" + ENTER);
        stringBuilder.append("import java.lang.reflect.Method;" + ENTER);
        stringBuilder.append("public class $ProxyO implements " + interfaces[0].getName() + "{" + ENTER);
        stringBuilder.append("SelfInvocationHandler h;" + ENTER);
        stringBuilder.append("public $ProxyO(SelfInvocationHandler h) {" + ENTER);
        stringBuilder.append("this.h = h;" + ENTER);
        stringBuilder.append("}" + ENTER);

        for (Method method : interfaces[0].getMethods()) {
            stringBuilder.append("public " + method.getReturnType().getName() + " " + method.getName() + "() {" + ENTER);
            stringBuilder.append("try {" + ENTER);
            stringBuilder.append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + method.getName() + "\", new Class[]{});" + ENTER);
            stringBuilder.append("this.h.invoke(this, m, null);" + ENTER);
            stringBuilder.append("} catch(Throwable able) {" + ENTER);
            stringBuilder.append("able.getMessage();" + ENTER);
            stringBuilder.append("}" + ENTER);
            stringBuilder.append("}" + ENTER + ENTER);
        }
        stringBuilder.append("}" + ENTER);
        return stringBuilder.toString();
    }
}
