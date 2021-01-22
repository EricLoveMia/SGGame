package cn.eric.game.fujiatianxia6.controller;

import cn.eric.game.fujiatianxia6.factory.*;
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

    private int step = 0;

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
            int index = 0;
            CampaignMap campaignMap = campaignMaps.get(campaign.getIndex());
            campaign.setIndex(campaign.getIndex() + 1);
            // 确定玩家数和AI
            campaignMap.setDefaultPlayer(Arrays.asList("1","2","3"));
            game = new Game(campaignMap);
            while (game.startCampaign() && campaign.getIndex() < campaign.getCampaignMaps().size()) {
                campaignMap = campaignMaps.get(campaign.getIndex());
                // 确定玩家数和AI
                campaignMap.setDefaultPlayer(Arrays.asList("1", "2", "3"));
                game = new Game(campaignMap);
            }
            //游戏结束
            System.out.println("\n\n\n\n");
            System.out.print("****************************************************\n");
            System.out.print("                      Game  Over                    \n");
            System.out.print("****************************************************\n\n");
        } else {
            game.startCampaign();
        }
    }

    public void startWithSave() {
        boolean win = game.startWithSave();
        while (win && campaign.getIndex() < campaign.getCampaignMaps().size()) {
            GeneralFactory.init(); //初始化武将
            SkillFactory.init();// 初始化技能
            BuildingFactory.initBuildings(); // 初始化建筑
            WeaponFactory.init(); // 初始化专属武器库
            // 初始化城市
            CityFactory.init();
            // 初始化战役地图
            MapFactory.init();

            campaign.setIndex(campaign.getIndex() + 1);
            CampaignMap campaignMap = campaign.getCampaignMaps().get(campaign.getIndex());
            // 确定玩家数和AI
            campaignMap.setDefaultPlayer(Arrays.asList("1", "2", "3"));
            game = new Game(campaignMap);
            win = game.startCampaign();
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

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
