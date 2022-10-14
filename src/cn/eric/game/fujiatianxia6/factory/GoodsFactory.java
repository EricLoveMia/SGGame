package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.po.store.Goods;
import cn.eric.game.fujiatianxia6.service.Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 * @description: 商品工厂
 * @author: eric
 * @date: 2022-10-09 10:39
 **/
public class GoodsFactory {

    private static final List<Goods> goods;
    private static final Map<Integer, Goods> goodsMap;
    private static final Map<String, Goods> goodsNameMap;

    static {
        String goodsStr = Util.readFileContentAsBuffer("data/base/" + "goods.json");
        goods = JSONObject.parseArray(goodsStr, Goods.class);
        goodsMap = goods.stream().collect(Collectors.toMap(Goods::getId, e -> e));
        goodsNameMap = goods.stream().collect(Collectors.toMap(Goods::getName, e -> e));
    }

    public static Goods getById(int id) {
        return goodsMap.get(id);
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(goods));
    }

    public static Goods getByName(String name) {
        if (name == null || "".equals(name)) {
            return null;
        }
        return goodsNameMap.get(name);
    }

    public static Integer getIdByName(String name) {
        if (name == null || "".equals(name)) {
            return null;
        }
        return goodsNameMap.get(name).getId();
    }
}
