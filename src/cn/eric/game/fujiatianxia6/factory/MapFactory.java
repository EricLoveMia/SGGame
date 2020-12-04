package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.po.CampaignMap;
import cn.eric.game.fujiatianxia6.test.Dom4JforXML;
import org.dom4j.DocumentException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName MapFactory
 * @Description: 战役地图工厂类
 * @Author YCKJ2725
 * @Date 2020/11/30
 * @Version V1.0
 **/
public class MapFactory {

    private static List<CampaignMap> campaignMaps = new LinkedList<>();

    public static void init() {
        try {
            campaignMaps = Dom4JforXML.createCampaignMap();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static int showAllCampaignMap() {
        System.out.println("战役列表");
        for (int i = 0; i < campaignMaps.size(); i++) {
            System.out.println(campaignMaps.get(i).toString());
        }
        return campaignMaps.size();
    }

    public static CampaignMap chooseMap(String id){
        Map<String, CampaignMap> campaignMap = campaignMaps.stream().collect(Collectors.toMap(CampaignMap::getId, e -> e));
        return campaignMap.get(id);
    }

    public static List<CampaignMap> getCampaignMaps() {
        return campaignMaps;
    }

    public static void setCampaignMaps(List<CampaignMap> campaignMaps) {
        MapFactory.campaignMaps = campaignMaps;
    }
}
