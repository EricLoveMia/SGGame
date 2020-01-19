package cn.eric.game.fujiatianxia6.controller;

import cn.eric.game.fujiatianxia6.factory.*;
import cn.eric.game.fujiatianxia6.service.Game;
import cn.eric.game.fujiatianxia6.service.LoadService;

import java.util.Scanner;

public class StartGame {

	/**
	 * 启动游戏
	 * @param args
	 */
	public static void main(String[] args) throws InterruptedException {
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
			GeneralFactory generalFactory = new GeneralFactory(); //初始化武将
			SkillFactory.init();// 初始化技能
			BuildingFactory.initBuildings(); // 初始化建筑
			WeaponFactory.init(); // 初始化专属武器库
			// 初始化城市
			CityFactory.init();
			// 创建游戏类
			Game game = new Game();
			// 开始游戏
			game.start();
		}else if(choose == 2){
			System.out.println("※※※※※※※※※※※※※※※数据读取中※※※※※※※※※※※※※※※\n\n\n");
			LoadService.loadAutoSave();
		}else{
			System.out.println("※※※※※※※※※※※※※※※再见※※※※※※※※※※※※※※※\n\n\n");
		}
	}
}
