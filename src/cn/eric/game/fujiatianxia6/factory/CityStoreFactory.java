package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.service.Util;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 * @description: 商店模板
 * @author: eric
 * @date: 2022-10-09 10:09
 **/
public class CityStoreFactory {

    private static final List<CityStoreTemplate> cityStoreList;
    private static final Map<Integer, CityStoreTemplate> cityStoreTemplateMap;

    static {
        String cityString = Util.readFileContentAsBuffer("data/base/cityStore.json");
        cityStoreList = JSONObject.parseArray(cityString, CityStoreTemplate.class);
        cityStoreTemplateMap = cityStoreList.stream().collect(Collectors.toMap(CityStoreTemplate::getId, e -> e));
    }

    public static CityStoreTemplate getById(int id) {
        return cityStoreTemplateMap.get(id);
    }

    public static class CityStoreTemplate {
        int id;
        int commonGoodsId;
        int specialtyGoodsId;
        int commonBaseNum;
        int specialtyBaseNum;
        boolean specialty;

        public CityStoreTemplate(int id, int commonGoodsId, int specialtyGoodsId, int commonBaseNum,
                                 int specialtyBaseNum, boolean specialty) {
            this.id = id;
            this.commonGoodsId = commonGoodsId;
            this.specialtyGoodsId = specialtyGoodsId;
            this.commonBaseNum = commonBaseNum;
            this.specialtyBaseNum = specialtyBaseNum;
            this.specialty = specialty;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCommonGoodsId() {
            return commonGoodsId;
        }

        public void setCommonGoodsId(int commonGoodsId) {
            this.commonGoodsId = commonGoodsId;
        }

        public int getSpecialtyGoodsId() {
            return specialtyGoodsId;
        }

        public void setSpecialtyGoodsId(int specialtyGoodsId) {
            this.specialtyGoodsId = specialtyGoodsId;
        }

        public boolean isSpecialty() {
            return specialty;
        }

        public void setSpecialty(boolean specialty) {
            this.specialty = specialty;
        }

        public int getCommonBaseNum() {
            return commonBaseNum;
        }

        public void setCommonBaseNum(int commonBaseNum) {
            this.commonBaseNum = commonBaseNum;
        }

        public int getSpecialtyBaseNum() {
            return specialtyBaseNum;
        }

        public void setSpecialtyBaseNum(int specialtyBaseNum) {
            this.specialtyBaseNum = specialtyBaseNum;
        }
    }

}
