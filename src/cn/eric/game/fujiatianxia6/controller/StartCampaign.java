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
            CampaignMap campaignMap = campaignMaps.get(index++);
            // 确定玩家数和AI
            campaignMap.setDefaultPlayer(Arrays.asList("1","2","3"));
            game = new Game(campaignMap);
            while (game.startCampaign()) {
                campaignMap = campaignMaps.get(index++);
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
        while (game.startWithSave()) {

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
