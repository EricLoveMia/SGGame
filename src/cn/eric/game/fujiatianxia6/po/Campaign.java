package cn.eric.game.fujiatianxia6.po;

import cn.eric.game.fujiatianxia6.factory.MapFactory;
import cn.eric.game.fujiatianxia6.service.Game;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName Campaign
 * @Description: 一统天下战役
 * @Author YCKJ2725
 * @Date 2020/12/2
 * @Version V1.0
 **/
public class Campaign {

    /**
     * 战役地图
    **/
    private List<CampaignMap> campaignMaps = new LinkedList<>();

    /**
     * 当前战役地图
     **/
    private CampaignMap currentMap;

    /**
     * 当前游戏
     **/
    private Game currentGame;

    public Campaign() {
        List<CampaignMap> campaignMaps = MapFactory.getCampaignMaps();
        this.campaignMaps = campaignMaps;
    }

    public List<CampaignMap> getCampaignMaps() {
        return campaignMaps;
    }

    public void setCampaignMaps(List<CampaignMap> campaignMaps) {
        this.campaignMaps = campaignMaps;
    }

    public CampaignMap getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(CampaignMap currentMap) {
        this.currentMap = currentMap;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
}
