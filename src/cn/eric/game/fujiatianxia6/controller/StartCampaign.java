package cn.eric.game.fujiatianxia6.controller;

import cn.eric.game.fujiatianxia6.po.Campaign;
import cn.eric.game.fujiatianxia6.po.CampaignMap;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.Game;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName StartCampaign
 * @Description:
 * @Author YCKJ2725
 * @Date 2020/12/2
 * @Version V1.0
 **/
public class StartCampaign {

    private Campaign campaign;

    private Game game;

    private General general;

    //
    public void init(Campaign campaign, General general){
        // 载入战役内容
        this.campaign = campaign;
        // 载入当前战役地图  当前game
        if(campaign.getCurrentGame() != null) {
            this.game = campaign.getCurrentGame();
        }
        this.general = general;
    }

    public void start(){
        List<CampaignMap> campaignMaps = campaign.getCampaignMaps();
        if(game == null){
            CampaignMap campaignMap = campaignMaps.get(0);
            // 确定玩家数和AI
            campaignMap.setDefaultPlayer(Arrays.asList("1","2","3"));
            game = new Game(campaignMap);
            game.startCampaign();
        }
    }


    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }
}
