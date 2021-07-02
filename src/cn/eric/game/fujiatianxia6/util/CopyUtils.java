package cn.eric.game.fujiatianxia6.util;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @ClassName CopyUtils
 * @Description: TODO
 * @Author YCKJ2725
 * @Date 2021/2/22
 * @Version V1.0
 **/
public class CopyUtils {

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T obj) {
        T cloneObj = null;
        try {
            //写入字节流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obs = new ObjectOutputStream(out);
            obs.writeObject(obj);
            obs.close();

            //分配内存，写入原始对象，生成新对象
            ByteArrayInputStream ios = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(ios);
            //返回生成的新对象
            cloneObj = (T) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObj;
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> deepCopyList(List<T> src) {
        List<T> dest = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            dest = (List<T>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dest;
    }

    /**
     * @MethodName: copy
     * @Description: 拷贝属性
     * @Param: [orignal, clasz]
     * @Return: T
     * @Author: YCKJ2725
     * @Date: 2021/7/2 16:48
     **/
    public static <T> T copyProperties(Object original, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        Field[] declaredFieldsSource = original.getClass().getDeclaredFields();

        Field[] declaredFieldsTarget = clazz.getDeclaredFields();

        T newInstance = clazz.newInstance();
        for (Field field : declaredFieldsSource) {
            field.setAccessible(true);
            for (Field fieldT : declaredFieldsTarget) {
                if (field.getName().equals(fieldT.getName())) {
                    fieldT.setAccessible(true);
                    fieldT.set(newInstance, field.get(original));
                }
            }
        }

        return newInstance;
    }
}
