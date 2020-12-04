package cn.eric.game.fujiatianxia6.service;

import cn.eric.game.fujiatianxia6.CommonContents;
import cn.eric.game.fujiatianxia6.factory.CityFactory;
import cn.eric.game.fujiatianxia6.factory.GeneralFactory;
import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.po.Map;
import cn.eric.game.fujiatianxia6.test.WriteIntoXml;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName SaveService
 * @Description: 保存进度的服务
 * @Author YCKJ2725
 * @Date 2020/1/15
 * @Version V1.0
 **/
public class SaveService {

    /**
     * 武将数据保存为xml
     * 其他数据保存为json
     */
    public static void save(String path) throws CloneNotSupportedException {
        String prePath = "";
        if(path == null) {
            prePath = "data/save/AutoSave/";
        }else{
            prePath = "data/save/" + path + "/";
        }
        // 保存武将数据 需要带武器
        List<General> initGenerals = GeneralFactory.getInitGenerals();
        WriteIntoXml.saveGeneralsToXML(initGenerals,prePath);
        // 保存武器数据 不需要保存 通用
        // TODO 需要给武将配置武器

        // 保存技能数据 不需要保存 通用
        // 保存城市数据
        City[] citys = CityFactory.getCitys();
        List<City> cityList = Arrays.asList(citys);
        String cityJsonString = JSON.toJSONString(cityList);
        Util.writeIntoFile(cityJsonString,prePath + "city.txt");
        // 保存地图数据
        Util.writeIntoFile(Integer.toString(Game.mapNum),prePath + "mapNum.txt");
        // 保存游戏角色数据
        int num = Game.getNum();
        Util.writeIntoFile(Integer.toString(num),prePath + "num.txt");
        String[] goAndStop = Game.getGoAndStop();
        Util.writeIntoFile(JSON.toJSONString(Arrays.asList(goAndStop)),prePath + "goAndStop.txt");
        int[] playPos = Game.getPlayPos();
        Util.writeIntoFile(JSON.toJSONString(Collections.singletonList(playPos)),prePath + "playPos.txt");

        General[] players = Game.getPlayers();
        List<General> generalsForSave = new ArrayList<>();
        // 把属下武将设置为空，读取的时候再装配 不然会产生循环问题
        General clone;
        for (General general : players) {
            clone = (General) general.clone();
            generalsForSave.add(clone);
        }
       // Collections.copy(generalsForSave, generals);   // 浅复制
        //generalsForSave = new ArrayList<>(generals);
        for (General general : generalsForSave) {
            general.setGenerals(null);
        }
        Util.writeIntoFile(JSON.toJSONString(generalsForSave),prePath +  "players.txt");
        // 是否是战役
        if (CommonContents.startCampaign != null) {
            // 保存战役数据
            Util.writeIntoFile(JSON.toJSONString(CommonContents.startCampaign), prePath + "startCampaign.txt");
        }
        // 保存地图数据
        Map map = Game.getMap();
        Util.writeIntoFile(JSON.toJSONString(map),prePath + "map.txt");
    }



}
