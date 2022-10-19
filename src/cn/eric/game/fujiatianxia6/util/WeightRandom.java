package cn.eric.game.fujiatianxia6.util;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @version 1.0.0
 * @description: 权重随机取数工具
 * @author: eric
 * @date: 2022-10-18 16:28
 **/
public class WeightRandom<T> {

    private final TreeMap<Integer, T> weightMap;

    /**
     * 创建权重随机获取器
     *
     * @param <T> 权重随机获取的对象类型
     * @return {@link WeightRandom}
     */
    public static <T> WeightRandom<T> create() {
        return new WeightRandom<>();
    }

    /**
     * 构造
     */
    private WeightRandom() {
        weightMap = new TreeMap<>();
    }

    /**
     * 增加对象
     *
     * @param obj    对象
     * @param weight 权重
     */
    public void add(T obj, int weight) {
        if (weight > 0) {
            int lastWeight = (this.weightMap.size() == 0) ? 0 : this.weightMap.lastKey();
            this.weightMap.put(weight + lastWeight, obj);// 权重累加
        }
    }

    /**
     * 清空权重表
     */
    public void clear() {
        this.weightMap.clear();
    }

    /**
     * 下一个随机对象
     *
     * @return 随机对象
     */
    public T next() {
        int randomWeight = (int) (this.weightMap.lastKey() * Math.random());
        SortedMap<Integer, T> tailMap = this.weightMap.tailMap(randomWeight, false);
        return this.weightMap.get(tailMap.firstKey());
    }
}
