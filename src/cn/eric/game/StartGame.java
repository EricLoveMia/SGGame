package cn.eric.game;

import cn.eric.game.fujiatianxia6.CommonContents;
import cn.eric.game.fujiatianxia6.controller.StartCampaign;
import cn.eric.game.fujiatianxia6.factory.*;
import cn.eric.game.fujiatianxia6.po.Campaign;
import cn.eric.game.fujiatianxia6.service.Game;
import cn.eric.game.fujiatianxia6.service.LoadService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class StartGame {

	/**
     * 启动游戏
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
        System.out.println("//                                                //");
        System.out.println("//                                                //");
        System.out.println("//                富甲天下6                         //");
        System.out.println("//                                                //");
        System.out.println("//                                                //");
        System.out.println("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※\n\n\n");
        System.out.println("请选择 1 重新开始（覆盖进度）  2 读取进度  3 不想玩了");
		Scanner input = new Scanner(System.in);
		int choose = input.nextInt();
		if(choose == 1) {
			GeneralFactory.init(); //初始化武将
			SkillFactory.init();// 初始化技能
			BuildingFactory.initBuildings(); // 初始化建筑
			WeaponFactory.init(); // 初始化专属武器库
			// 初始化城市
			CityFactory.init();
			// 初始化战役地图
			MapFactory.init();
			// 初始化战役内容
			CampaignFactory.init();
			// 选择是个人战役还是自定义战役
			System.err.println("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※\n\n\n");
			System.out.println("请选择 1 一统天下  2 自定义战役  3 不想玩了");
			choose = input.nextInt();
			if(choose == 1) {
				System.out.println("请选择人物");
				System.out.println("\n请选择角色: 1. 刘备 容易收服武将，诸葛亮bug  \n " + "2. 曹操 野战单挑都厉害 " +
						" \n 3. 孙权 水战无敌，周瑜陆逊野战bug  \n 4. 董卓 三国第一武将在手，单挑无敌，群雄归附初始兵钱加倍 \n  " +
						"5. 袁绍 四世三公 文臣武将诸多 \n 6. 袁术 赌神 \n 7.刘表 经营有方，武将谋士平衡 \n 8.马腾 骑兵纵横天下 ");
				int lord = input.nextInt();
				while (lord < 1 || lord > 8) {
					System.out.println("请重新选择");
					lord = input.nextInt();
				}
				// 根据id 选择开启的战役
				CampaignFactory.CampaignFoundation campaignByGeneralId = CampaignFactory.getCampaignByGeneralId(lord + "");
				// 开启刘备战役
				StartCampaign startCampaign = new StartCampaign();
				CommonContents.startCampaign = startCampaign;
				Campaign campaign = new Campaign();
				campaign.setCampaignMaps(campaignByGeneralId.getCampaignMaps());
				campaign.setIndex(0);
				startCampaign.init(campaign, GeneralFactory.getGeneralById(lord + ""));
				startCampaign.start();
			} else if(choose == 2){
				// 创建游戏类
				Game game = new Game();
				// 开始游戏
				game.start();
			} else {
				System.out.println("※※※※※※※※※※※※※※※再见※※※※※※※※※※※※※※※\n\n\n");
			}
		}else if(choose == 2){
			System.out.println("请问需要读取哪个存档");
			File file = new File("data/save");
			File[] tempList = file.listFiles();
			for (int i = 0; i < tempList.length; i++) {
				long time = tempList[i].lastModified();
				cal.setTimeInMillis(time);
				System.out.println( i + ":" + tempList[i].getName() + " 修改时间：" + formatter.format(cal.getTime()));
			}
			input = new Scanner(System.in);
			while (true) {
				choose = input.nextInt();
				if (choose > tempList.length || choose < 0) {
					System.out.println("请重新选择");
					continue;
				}
				break;
			}
			LoadService.loadAutoSave(tempList[choose].getName());
			//LoadService.loadAutoSave(null);
		}else{
			System.out.println("※※※※※※※※※※※※※※※再见※※※※※※※※※※※※※※※\n\n\n");
		}
	}
}
