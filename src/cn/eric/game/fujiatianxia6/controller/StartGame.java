package cn.eric.game.fujiatianxia6.controller;

import cn.eric.game.fujiatianxia6.factory.WeaponFactory;
import cn.eric.game.fujiatianxia6.po.BuildingFactory;
import cn.eric.game.fujiatianxia6.po.GeneralFactory;
import cn.eric.game.fujiatianxia6.po.Skill;
import cn.eric.game.fujiatianxia6.po.SkillFactory;
import cn.eric.game.fujiatianxia6.service.Game;

public class StartGame {

	/**
	 * 启动游戏
	 * @param args
	 */
	public static void main(String[] args) {
		GeneralFactory generalFactory = new GeneralFactory(); //初始化武将
		SkillFactory.init();// 初始化技能
		BuildingFactory.initBuildings(); // 初始化建筑
		WeaponFactory.init(); // 初始化专属武器库
		Game game = new Game();   //创建游戏类
		
  	    game.start();             //开始游戏
	}
}
