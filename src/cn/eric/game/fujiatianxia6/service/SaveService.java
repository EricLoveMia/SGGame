package cn.eric.game.fujiatianxia6.service;

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
    public static void save() throws CloneNotSupportedException {
        // 保存武将数据 需要带武器
        List<General> initGenerals = GeneralFactory.getInitGenerals();
        WriteIntoXml.saveGeneralsToXML(initGenerals);
        // 保存武器数据 不需要保存 通用
        // TODO 需要给武将配置武器

        // 保存技能数据 不需要保存 通用
        // 保存城市数据
        City[] citys = CityFactory.getCitys();
        List<City> cityList = Arrays.asList(citys);
        String cityJsonString = JSON.toJSONString(cityList);
        Util.writeIntoFile(cityJsonString,"data/save/city.txt");
        // 保存游戏角色数据
        Integer num = Game.getNum();
        Util.writeIntoFile(num.toString(),"data/save/num.txt");
        String[] goAndStop = Game.getGoAndStop();
        Util.writeIntoFile(JSON.toJSONString(Arrays.asList(goAndStop)),"data/save/goAndStop.txt");
        int[] playPos = Game.getPlayPos();
        Util.writeIntoFile(JSON.toJSONString(Arrays.asList(playPos)),"data/save/playPos.txt");

        General[] players = Game.getPlayers();
        List<General> generalsForSave = new ArrayList<>();
        // 把属下武将设置为空，读取的时候再装配 不然会产生循环问题
        List<General> generals = Arrays.asList(players);
        General clone;
        for (General general : generals) {
            clone = (General) general.clone();
            generalsForSave.add(clone);
        }
       // Collections.copy(generalsForSave, generals);   // 浅复制
        //generalsForSave = new ArrayList<>(generals);
        for (General general : generalsForSave) {
            general.setGenerals(null);
        }
        Util.writeIntoFile(JSON.toJSONString(generalsForSave),"data/save/players.txt");

        // 保存地图数据
        Map map = Game.getMap();
        Util.writeIntoFile(JSON.toJSONString(map),"data/save/map.txt");
    }



}