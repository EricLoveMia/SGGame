package cn.eric.game.fujiatianxia6.service;

import cn.eric.game.fujiatianxia6.CommonContents;
import cn.eric.game.fujiatianxia6.controller.StartCampaign;
import cn.eric.game.fujiatianxia6.factory.*;
import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.po.Map;
import cn.eric.game.fujiatianxia6.po.bag.SilkBag;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName LoadService
 * @Description: 读取进度服务
 * @Author YCKJ2725
 * @Date 2020/1/15
 * @Version V1.0
 **/
public class LoadService {

    public static void loadAutoSave(String path) throws CloneNotSupportedException {
        System.out.println("※※※※※※※※※※※※※※※数据读取中※※※※※※※※※※※※※※※\n\n\n");
        String prePath = "";
        if (path == null) {
            prePath = "data/save/AutoSave/";
        } else {
            prePath = "data/save/" + path + "/";
        }
        // 读取武将数据
        // 初始化武将  TODO  cityId的问题
        new GeneralFactory(prePath + "Generals.xml");
        // 初始化技能
        SkillFactory.init();
        // 初始化建筑
        BuildingFactory.initBuildings();
        // 初始化专属武器库
        WeaponFactory.init();
        // 设定城市
        String cityString = Util.readFileContentAsBuffer(prePath + "city.txt");
        List<City> cityList = JSONObject.parseArray(cityString, City.class);
//        City[] citys = new City[cityList.size()];
//        cityList.toArray(citys);
        // 设定主公
        String playersString = Util.readFileContentAsBuffer(prePath + "players.txt");
        // List<General> playersList = JSONObject.parseArray(playersString, General.class);
        List<General> playersList = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(playersString);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            General general = JSON.parseObject(jsonObject.toJSONString(), General.class);
            JSONObject bag = jsonObject.getJSONObject("bag");
            if (bag != null) {
                JSONArray silkBags = bag.getJSONArray("silkBags");
                List<SilkBag> bags = new ArrayList<>();
                for (Object silkBag : silkBags) {
                    JSONObject b = (JSONObject) silkBag;
                    bags.add(SilkBagFactory.getByName(b.getString("name")));
                }
                general.getBag().setSilkBags(bags);
            }
            playersList.add(general);
        }

        // playersList = JSONArray.parseArray(playersString, General.class);

        List<String> deadPlayerIds = playersList.stream()
                .filter(e -> e.getStatus().equals("4")).map(General::getId).collect(Collectors.toList());
        // 武将调整
        List<General> allGenerals = GeneralFactory.getInitGenerals();
        if (allGenerals != null && allGenerals.size() > 0) {
            for (General general : allGenerals) {
                if (deadPlayerIds.contains(general.getBelongTo())) {
                    if (!general.getId().equals(general.getBelongTo())) {
                        general.setBelongTo("0");
                        general.setStatus("0");
                        general.setCityId("");
                    }
                }
                // 如果有专属武器
                if (general.getWeapon() != null && !"-1".equals(general.getWeapon().getGeneralId())) {
                    WeaponFactory.removeFromMap(general.getId());
                }
            }
        }
        // 给城池配置武将（不能直接从文本中读取，这样就变成了两个对象，两个相同的武将了,必须要从武将工厂里面获取）
        List<General> denfenceGeneralsNew;
        for (City city : cityList) {
            if (city != null) {
                if (city.getBelongTo() == 0) {
                    continue;
                }
                denfenceGeneralsNew = new ArrayList<>();
                // 已经亡国
                if (deadPlayerIds.contains(city.getBelongTo() + "")) {
                    city.setBelongTo(0);
                    city.setDenfenceGenerals(new ArrayList<>(4));
                    continue;
                }
                // 如果没有亡国
                if (allGenerals != null && allGenerals.size() > 0) {
                    for (General general : allGenerals) {
                        if (general.getCityId() != null && city.getId().equals(general.getCityId())) {
                            // 根据ID 获取
                            denfenceGeneralsNew.add(general);
                        }
                    }
                }
                if (denfenceGeneralsNew.size() > 0) {
                    city.setDenfenceGenerals(denfenceGeneralsNew);
                }
            }
        }

        CityFactory.setCitys(cityList);
        // 初始化地图
        MapFactory.init();
        // 设定角色 一定要是从武将库中获取 再赋值
        Game game = new Game();   //创建游戏类

        String goAndStopString = Util.readFileContentAsBuffer(prePath + "goAndStop.txt");
        List<String> goAndStopList = JSONObject.parseArray(goAndStopString, String.class);
        String[] goAndSto = new String[goAndStopList.size()];
        goAndStopList.toArray(goAndSto);
        Game.setGoAndStop(goAndSto);

        String numString = Util.readFileContentAsBuffer(prePath + "num.txt");
        Game.setNum(Integer.parseInt(numString));

        String mapNumString = Util.readFileContentAsBuffer(prePath + "mapNum.txt");
        Game.setMapNum(Integer.parseInt(mapNumString));

        String stepCount = Util.readFileContentAsBuffer(prePath + "stepCount.txt");
        Game.setStepCount(Integer.parseInt(stepCount));

        String playPosString = Util.readFileContentAsBuffer(prePath + "playPos.txt");
        String substring = playPosString.substring(2, playPosString.length() - 2);
        String[] playPos = substring.split(",");
        int[] playPosInt = new int[playPos.length];
        for (int i = 0; i < playPos.length; i++) {
            playPosInt[i] = Integer.parseInt(playPos[i]);
        }
        Game.setPlayPos(playPosInt);


        General[] players = new General[playersList.size()];
        int i = 0;
        // 给对应的players 赋值\\
        General generalById;
        for (General general : playersList) {

            generalById = GeneralFactory.getGeneralById(general.getId());
            generalById.setMoney(general.getMoney());
            generalById.setCityId(general.getCityId());
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
            generalById.setTransportTeam(general.getTransportTeam());
            generalById.setBag(general.getBag());
            players[i++] = generalById;
        }
        Game.setPlayers(players);

        String mapString = Util.readFileContentAsBuffer(prePath + "map.txt");
        Map map = JSONObject.parseObject(mapString, Map.class);
        // JSONObject 转 city
        map.reloadCity();
        Game.setMap(map);

        // 载入战役信息
        try {
            String startCampaignString = Util.readFileContentAsBuffer(prePath + "startCampaign.txt");
            StartCampaign startCampaign = JSONObject.parseObject(startCampaignString, StartCampaign.class);
            startCampaign.setGame(game);
            CommonContents.startCampaign = startCampaign;
            startCampaign.startWithSave();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }


        game.startWithSave();
    }

}
