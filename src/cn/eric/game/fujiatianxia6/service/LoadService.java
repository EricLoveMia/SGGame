package cn.eric.game.fujiatianxia6.service;

import cn.eric.game.fujiatianxia6.factory.*;
import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.po.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LoadService
 * @Description: 读取进度服务
 * @Author YCKJ2725
 * @Date 2020/1/15
 * @Version V1.0
 **/
public class LoadService {

    public static void loadAutoSave(){
        // 读取武将数据
        // 初始化武将  TODO  cityId的问题
        GeneralFactory generalFactory = new GeneralFactory("data/save/Generals.xml");
        // 初始化技能
        SkillFactory.init();
        // 初始化建筑
        BuildingFactory.initBuildings();
        // 初始化专属武器库
        WeaponFactory.init();
        // 设定城市
        String cityString = Util.readFileContentAsBuffer("data/save/city.txt");
        List<City> cityList = JSONObject.parseArray(cityString, City.class);
        City[] citys = new City[cityList.size()];
        cityList.toArray(citys);
        // 给城池配置武将（不能直接从文本中读取，这样就变成了两个对象，两个相同的武将了,必须要从武将工厂里面获取）
        for (City city : citys) {
            if(city != null) {
                List<General> denfenceGeneralsNew = new ArrayList<>();
                List<General> denfenceGenerals = city.getDenfenceGenerals();
                if (denfenceGenerals != null && denfenceGenerals.size() > 0) {
                    for (General denfenceGeneral : denfenceGenerals) {
                        // 根据ID 获取
                        denfenceGeneralsNew.add(GeneralFactory.getGeneralById(denfenceGeneral.getId()));
                    }
                }
                if (denfenceGeneralsNew.size() > 0) {
                    city.setDenfenceGenerals(denfenceGeneralsNew);
                }
            }
        }

        CityFactory.setCitys(citys);

        // 设定角色 一定要是从武将库中获取 再赋值
        Game game = new Game();   //创建游戏类

        String goAndStopString = Util.readFileContentAsBuffer("data/save/goAndStop.txt");
        List<String> goAndStopList = JSONObject.parseArray(goAndStopString, String.class);
        String[] goAndSto = new String[goAndStopList.size()];
        goAndStopList.toArray(goAndSto);
        Game.setGoAndStop(goAndSto);

        String numString = Util.readFileContentAsBuffer("data/save/num.txt");
        Game.setNum(Integer.parseInt(numString));

        String playPosString = Util.readFileContentAsBuffer("data/save/playPos.txt");
        String substring = playPosString.substring(2, playPosString.length() - 2);
        String[] playPos = substring.split(",");
        int[] playPosInt = new int[playPos.length];
        for (int i = 0; i < playPos.length; i++) {
            playPosInt[i] = Integer.parseInt(playPos[i]);
        }
        Game.setPlayPos(playPosInt);

        String playersString = Util.readFileContentAsBuffer("data/save/players.txt");
        List<General> playersList = JSONObject.parseArray(playersString, General.class);
        General[] players = new General[playersList.size()];
        int i = 0;
        // 给对应的players 赋值\\
        General generalById;
        for (General general : playersList) {

            generalById = GeneralFactory.getGeneralById(general.getId());
            generalById.setMoney(general.getMoney());
            generalById.setInfantry(general.getInfantry());
            generalById.setArmy(general.getArmy());
            generalById.setReboot(general.isReboot());
            generalById.setArchers(general.getArchers());
            generalById.setCavalrys(general.getCavalrys());
            generalById.setArmsTotal(general.getArmsTotal());
            generalById.setResearch(general.getResearch());
            generalById.setWeapon(general.getWeapon());
            generalById.setReputation(general.getReputation());
            generalById.setGenerals(GeneralFactory.setBeginGenerals(generalById.getId()));
            players[i++] = generalById;
        }
        Game.setPlayers(players);

        String mapString = Util.readFileContentAsBuffer("data/save/map.txt");
        Map map = JSONObject.parseObject(mapString, Map.class);
        // JSONObject 转 city
        map.reloadCity();
        Game.setMap(map);
        game.startWithSave();
    }

}
