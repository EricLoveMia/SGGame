package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.po.CampaignMap;
import cn.eric.game.fujiatianxia6.test.Dom4JforXML;
import cn.eric.game.fujiatianxia6.util.CopyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName CampaignFactory
 * @Description: 战役工厂
 * @Author YCKJ2725
 * @Date 2021/6/28
 * @Version V1.0
 **/
public class CampaignFactory {

    public static List<CampaignFoundation> campaignFoundations;

    public static void init() {
        System.out.println("初始化战役地图 。。。");
        try {
            List<CampaignList> campaignList = Dom4JforXML.createCampaign();
            campaignFoundations = create(campaignList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<CampaignFoundation> create(List<CampaignList> campaignList) throws InstantiationException, IllegalAccessException {
        campaignFoundations = new ArrayList<>();
        CampaignFoundation foundation;
        for (CampaignList campaign : campaignList) {
            foundation = CopyUtils.copyProperties(campaign, CampaignFoundation.class);
            List<CampaignMap> campaignMaps = new ArrayList<>();
            String maps = campaign.getMaps();
            String enemies = campaign.getEnemies();
            String[] mapArray = maps.split(",");
            String[] enemyArray = enemies.split(";");
            for (int i = 0; i < mapArray.length; i++) {
                String mapId = mapArray[i];
                CampaignMap campaignMap = MapFactory.chooseMap(mapId);
                CampaignMap cloneMap = CopyUtils.clone(campaignMap);
                cloneMap.setDefaultPlayer(Arrays.asList(enemyArray[i].split(",")));
                campaignMaps.add(cloneMap);
            }
            foundation.setCampaignMaps(campaignMaps);
            campaignFoundations.add(foundation);
        }
        return campaignFoundations;
    }

    public static CampaignFoundation getCampaignByGeneralId(String id) {
        Map<String, CampaignFoundation> map =
                campaignFoundations.stream().collect(Collectors.toMap(CampaignFoundation::getLordId, e -> e));
        return map.get(id);
    }

    public static class CampaignList {
        private String lordId;
        private String maps;
        private String enemies;
        private String memo;

        public String getLordId() {
            return lordId;
        }

        public void setLordId(String lordId) {
            this.lordId = lordId;
        }

        public String getMaps() {
            return maps;
        }

        public void setMaps(String maps) {
            this.maps = maps;
        }

        public String getEnemies() {
            return enemies;
        }

        public void setEnemies(String enemies) {
            this.enemies = enemies;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }

    public static class CampaignFoundation {
        /**
         * 战役地图
         **/
        private List<CampaignMap> campaignMaps;

        private String lordId;

        private String memo;

        public List<CampaignMap> getCampaignMaps() {
            return campaignMaps;
        }

        public void setCampaignMaps(List<CampaignMap> campaignMaps) {
            this.campaignMaps = campaignMaps;
        }

        public String getLordId() {
            return lordId;
        }

        public void setLordId(String lordId) {
            this.lordId = lordId;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }
}
