package cn.eric.game.fujiatianxia6.po;

import cn.eric.game.fujiatianxia6.factory.GeneralFactory;

/**
 * <p>Title: Fight<／p>
 * <p>Description: 打架类<／p>
 *
 * @author eric
 * @date 2017年8月10日 下午1:11:14
 */
public class FightConfig {

	public static final double factorOfCavalrysInLand = 2.0;
	public static final double factorOfCavalrysInMountain = 1.5;
	public static final double factorOfCavalrysInRiver = 1.1;

	public static final double factorOfInfantryInLand = 1.5;
	public static final double factorOfInfantryInMountain = 2.0;
	public static final double factorOfInfantryInRiver = 1.2;

	public static final double factorOfArchersInLand = 1.3;
	public static final double factorOfArchersInMountain = 1.4;
	public static final double factorOfArchersInRiver = 2.0;

	public FightConfig() {
		//初始化打架类
	}

	/**
	 *
	 * @Title: SingleFight
	 * @Description: 单挑
	* @param
	* @return void    返回类型 
	* @throws
	 */
	public void SingleFight(General general,City city){
		//攻击方请派出武将
		System.out.println("攻方派出武将");
		General generalByChoose = GeneralFactory.getGeneralByChoose(general, null, 3, city);
		System.out.println("守方派出武将");
		General generalByDefenceAuto = GeneralFactory.generalByDefenceAuto(city);
	}
}
