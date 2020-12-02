package cn.eric.game.fujiatianxia6.controller;

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
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
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
			// 选择是个人战役还是自定义战役
			System.err.println("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※\n\n\n");
			System.out.println("请选择 1 一统天下  2 自定义战役  3 不想玩了");
			choose = input.nextInt();
			if(choose == 1) {
				// 先开启刘备战役
				StartCampaign startCampaign = new StartCampaign();
				Campaign campaign = new Campaign();
				startCampaign.init(campaign,GeneralFactory.getGeneral("刘备"));
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
