package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.po.SiegeWeapon;
import cn.eric.game.fujiatianxia6.service.Util;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 * @description: 攻城武器工厂
 * @author: eric
 * @date: 2022-10-17 11:30
 **/
public class SiegeWeaponFactory {

    private static final List<SiegeWeapon> siegeWeaponList;
    private static final Map<Integer, SiegeWeapon> siegeWeaponMap;

    static {
        String siegeString = Util.readFileContentAsBuffer("data/base/siegeWeapon.json");
        siegeWeaponList = JSONObject.parseArray(siegeString, SiegeWeapon.class);
        siegeWeaponMap = siegeWeaponList.stream().collect(Collectors.toMap(SiegeWeapon::getId, e -> e));
    }

    public static SiegeWeapon getById(int id) {
        return siegeWeaponMap.get(id);
    }

    public static List<SiegeWeapon> getAll() {
        return siegeWeaponList;
    }
}
