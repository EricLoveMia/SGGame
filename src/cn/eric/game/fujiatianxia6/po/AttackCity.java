package cn.eric.game.fujiatianxia6.po;

import cn.eric.game.fujiatianxia6.factory.GeneralFactory;
import cn.eric.game.fujiatianxia6.factory.SkillFactory;

import java.util.*;

/**
 * 
* @ClassName: AttackCity
* @Description: 攻城类
* @author 00322027
* @date 2018年2月24日 下午3:56:32
*
 */
public class AttackCity {
    private General Leader = null;
	
	private General AttackChief = null;
	private General AttackVice = null;
	private General AttackCounsellor = null;
	
	private Integer soliders = 0; // 剑兵数量
	private Integer cavalrys = 0; // 骑兵数量
	private Integer infantry = 0; // 枪兵数量
	private Integer archers = 0;  // 弓箭手数量

	private Integer attackSoliderTotal;
	private Integer deffenceSoliderTotal;

	private General DefenceChief = null;
	private General DefenceVice = null;
	private General DefenceCounsellor = null;

	private Double attackWeaponAdd = 1.0;
	private Double defenceBulidingAdd = 1.0;

	private City city;

	private Integer attackSoliderTotalCopy = 0;
	private int attactlevelAddition = 0;
	private int defencelevelAddition = 0;

	private int attackSkillProbability = 0;
	private int defenceSkillProbability = 0;

	private int defenceLost = 0;
	private int attackLost = 0;

	public General getLeader() {
		return Leader;
	}

	public Integer getAttackSoliderTotal() {
		int attackSoliderTotal = 0;
		//
		// 得到 骑 枪 弓的级别
		HashMap<String,Integer> levelMap = new HashMap<>();
		for(Arms arms : Leader.getArmsTotal()){
			levelMap.put(arms.getName(), arms.getLevel());
		}

		int swordLevel = levelMap.get("剑兵") + attactlevelAddition;
		int cavalrysLevel = levelMap.get("骑兵") + attactlevelAddition;
		int infantrysLevel = levelMap.get("枪兵") + attactlevelAddition;
		int archersLevel = levelMap.get("弓兵") + attactlevelAddition;

		// 根据城市的属性 计算总战力
		if(city.getTopography() == 1){
			attackSoliderTotal = (int) (soliders * (1 + swordLevel * 0.1)
					+ cavalrys * FightConfig.factorOfArchersInLand * (1 + cavalrysLevel * 0.1)
					+ infantry * FightConfig.factorOfInfantryInLand * (1 + infantrysLevel*0.1)
					+ archers * FightConfig.factorOfArchersInLand * (1 + archersLevel*0.1));
		}else if(city.getTopography() == 2){
			attackSoliderTotal = (int) (soliders * (1 + swordLevel * 0.1)
					+ cavalrys * FightConfig.factorOfCavalrysInMountain * (1 + cavalrysLevel*0.1)
					+ infantry * FightConfig.factorOfInfantryInMountain * (1 + infantrysLevel*0.1)
					+ archers * FightConfig.factorOfArchersInMountain * (1 + archersLevel*0.1));
		}else if(city.getTopography() == 3){
			attackSoliderTotal = (int) (soliders * (1 + swordLevel * 0.1)
					+ cavalrys * FightConfig.factorOfCavalrysInRiver * (1 + cavalrysLevel*0.1)
					+ infantry * FightConfig.factorOfInfantryInRiver * (1 + infantrysLevel*0.1)
					+ archers * FightConfig.factorOfArchersInRiver * (1 + archersLevel*0.1));
		}	
		return attackSoliderTotal;
	}

	public void setAttackSoliderTotal(Integer attackSoliderTotal) {
		this.attackSoliderTotal = attackSoliderTotal;
	}

	public Integer getDeffenceSoliderTotal() {
		General general = GeneralFactory.getGeneralById(city.getBelongTo().toString());
		// 得到 骑 枪 弓的级别
		HashMap<String,Integer> levelMap = new HashMap<>();
		for(Arms arms : general.getArmsTotal()){
			levelMap.put(arms.getName(), arms.getLevel());
		}

		int swordLevel = levelMap.get("剑兵") + defencelevelAddition;
		int cavalrysLevel = levelMap.get("骑兵") + defencelevelAddition;
		int infantrysLevel = levelMap.get("枪兵") + defencelevelAddition;
		int archersLevel = levelMap.get("弓兵") + defencelevelAddition;

		int deffenceSoliderTotal = 0;
		if(city.getTopography() == 1){
			deffenceSoliderTotal = (int) (city.getSoilders() * (1 + swordLevel * 0.1)
					+ city.getCavalrys() * FightConfig.factorOfArchersInLand * (1 + cavalrysLevel*0.1)
					+ city.getInfantry() * FightConfig.factorOfInfantryInLand * (1 + infantrysLevel*0.1)
					+ city.getArchers() * FightConfig.factorOfArchersInLand * (1 + archersLevel*0.1));
		}else if(city.getTopography() == 2){
			deffenceSoliderTotal = (int) (city.getSoilders() * (1 + swordLevel * 0.1)
					+ city.getCavalrys() * FightConfig.factorOfCavalrysInMountain * (1 + cavalrysLevel*0.1)
					+ city.getInfantry() * FightConfig.factorOfInfantryInMountain * (1 + infantrysLevel*0.1)
					+ city.getArchers() * FightConfig.factorOfArchersInMountain * (1 + archersLevel*0.1));
		}else if(city.getTopography() == 3){
			deffenceSoliderTotal = (int) (city.getSoilders() * (1 + swordLevel * 0.1)
					+ city.getCavalrys() * FightConfig.factorOfCavalrysInRiver * (1 + cavalrysLevel*0.1)
					+ city.getInfantry() * FightConfig.factorOfInfantryInRiver * (1 + infantrysLevel*0.1)
					+ city.getArchers() * FightConfig.factorOfArchersInRiver * (1 + archersLevel*0.1));
		}


		// 加上城中建筑的加成
		if(city.getBildings().size() > 0){
			for (Building bilding : city.getBildings()) {
				if(bilding.id == 1 || bilding.id == 2){
					defenceBulidingAdd = defenceBulidingAdd + 0.1 + 0.1 * bilding.level;
				}
			}
		}

		return deffenceSoliderTotal;
	}

	public void setDeffenceSoliderTotal(Integer deffenceSoliderTotal) {
		this.deffenceSoliderTotal = deffenceSoliderTotal;
	}

	/**
	 * 第一版 只有soliders
	 * 第二版 加入骑 枪 弓  损失计算 第一步 计算当前城内的各兵种比例 并得到损失比例 剑兵的损失比例放大，其他兵种的损失比例缩小；
	 * 第二步 计算当前城内的兵种加成后的兵力值（根据城市的属性）每次都要重新计算加成兵力值
	 * 第三步 损失的数量，按照损失比例进行计算
	 *
	 * @param siegeWeapon
	 */
	public boolean attack(SiegeWeapon siegeWeapon) {
		int count = 0;
		// 攻城至一方的兵力完全消耗完为止
		// 攻城前技能触发
		SkillFactory.changeBefore(3, 3, null, null, this);

		// 攻城器械加成
		if (siegeWeapon != null) {
			attackWeaponAdd = attackWeaponAdd + siegeWeapon.getPower() / 50000;
		}
		// 获得加成值
		attackSoliderTotal = getAttackSoliderTotal();
		deffenceSoliderTotal = getDeffenceSoliderTotal();
		this.attackSoliderTotalCopy = this.attackSoliderTotal;
		System.out.println("进攻方加成总兵力" + attackSoliderTotal + "防守方加成总兵力" + deffenceSoliderTotal);
		// 
		while (attackSoliderTotal > 0 && deffenceSoliderTotal > 0) {
			count++;
			// 当守城人数大于攻城人数时，攻城失败
			if (attackSoliderTotal <= deffenceSoliderTotal) {
				System.out.println("攻城人数已小于守城人数");
				return false;
			}
			// 每次攻城，攻城方先消耗守城兵力的10%-20% 根据守将的统帅  (int)(5000 - 2000 * (0.1 + (float)((int)(Math.random() * 99))/1000 ))
			if(count == 1 && checkSkillOfAttack()) {
				System.out.println("第" + count + "波进攻，进攻方推进到城墙，未损失兵力，剩余总兵力" + attackSoliderTotal);
			}else {
				attackSoliderTotal = (int) (attackSoliderTotal - defenceBulidingAdd * deffenceSoliderTotal * (0.2 + (float) ((int) (Math.random() * (Integer.parseInt(DefenceChief.getCommand())))) / 1000));
				System.out.println("第" + count + "波进攻，进攻方推进到城墙，剩余总兵力" + attackSoliderTotal);
			}
			if (attackSoliderTotal <= 0) {
				System.out.println("攻城人数已全部损失，撤军");
				// 进攻方结算
				Leader.setArmy(Leader.getArmy() - soliders);
				Leader.setCavalrys(Leader.getCavalrys() - cavalrys);
				Leader.setInfantry(Leader.getInfantry() - infantry);
				Leader.setArchers(Leader.getArchers() - archers);
				return false;
			}
			// 一回合，守城方损失 进攻方兵力*(0.2*统帅)*(0.1*武力)的  (int) (5000 * ((0.2 * 99/100) + (0.1 * 99/100)))
			defenceLost = (int) (attackWeaponAdd * attackSoliderTotal * ((0.06 * Integer.parseInt(AttackChief.getCommand()) / 100) + (0.04 * Integer.parseInt(AttackChief.getCommand()) / 100)));
			if (deffenceSoliderTotal < defenceLost) {
				defenceLost = deffenceSoliderTotal;
			}

			// 攻城方损失守城方损失兵力的1.75倍*(守城方统帅-攻城方统帅) * 守方建筑加成
			attackLost = (int) (defenceBulidingAdd * defenceLost * 1.75 * (1 + (float) (Integer.parseInt(DefenceChief.getCommand()) - Integer.parseInt(AttackChief.getCommand())) / 100));
			if (attackSoliderTotal <= attackLost) {
				attackLost = attackSoliderTotal;
			}
			// 技能释放
			SkillFactory.changeMiddle(3, 3, null, null, this);
			SkillFactory.changeAfter(3, 3, null, null, this);

			// 城市内开始结算剩余兵力
			resetCitySoilders(defenceLost);
			deffenceSoliderTotal = deffenceSoliderTotal - defenceLost;
			attackSoliderTotal = attackSoliderTotal - attackLost;
			System.out.println("第" + count + "波进攻结束，防守方总兵力 " + deffenceSoliderTotal + ",剩余剑兵：" + city.getSoilders() + "骑兵：" + city.getCavalrys() + "枪兵：" + city.getInfantry() + "弓箭手：" + city.getArchers());
			// 进攻方开始结算剩余兵力
			System.out.println("第" + count + "波进攻结束，进攻方剩余总兵力" + attackSoliderTotal);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(attackSoliderTotal <= 0){
			System.out.println("攻城人数已全部损失，撤军");
			// 进攻方结算
			Leader.setArmy(Leader.getArmy() - soliders);
			Leader.setCavalrys(Leader.getCavalrys() - cavalrys);
			Leader.setInfantry(Leader.getInfantry() - infantry);
			Leader.setArchers(Leader.getArchers() - archers);
			return false;			
		}
         System.out.println("守城人数已全部损失，占领城市");
         // 进攻方结算
         resetLeaderArmy();
         // 防守方结算
         city.setSoilders(0);
         city.setArchers(0);
         city.setCavalrys(0);
         city.setInfantry(0);
         // 守城将领被俘虏 概率30%
         List<General> denfenceGenerals = city.getDenfenceGenerals();
         Iterator<General> generalIterator = denfenceGenerals.iterator();
         while (generalIterator.hasNext()) {
             General denfenceGeneral = generalIterator.next();
             if (new Random().nextInt(100) < 30) {
                 System.out.println(denfenceGeneral.getName() + "被俘虏");
                 GeneralFactory.beCatch(denfenceGeneral, getAttackChief());
             } else {
                 System.out.println(denfenceGeneral.getName() + "回归阵营");
                 denfenceGeneral.setCityId("");
             }
             generalIterator.remove();
         }
         city.setDenfenceGenerals(new ArrayList<>());
         return true;
     }

	private boolean checkSkillOfAttack() {

		if (getAttackChief() != null) {
			if("29".equals(getAttackChief().getSkill())){
				return true;
			}
		}
		if (getAttackCounsellor() != null){
			if("29".equals(getAttackCounsellor().getSkill())){
				return true;
			}
		}
		if (getAttackVice() != null){
            return "29".equals(getAttackVice().getSkill());
		}
		return false;
	}

	/**
	 *  战后结算进攻方损失
	 */
	private void resetLeaderArmy() {
		int temp = attackSoliderTotalCopy - attackSoliderTotal; // 损失的总战斗力

		// 得到 骑 枪 弓的级别
		HashMap<String,Integer> levelMap = new HashMap<>();
		for(Arms arms : Leader.getArmsTotal()){
			levelMap.put(arms.getName(), arms.getLevel());
		}

		double perS = 1 * (1 + levelMap.get("剑兵")*0.1);
		double perC = 1 * (1 + levelMap.get("骑兵")*0.1);
		double perI = 1 * (1 + levelMap.get("枪兵")*0.1);
		double perA = 1 * (1 + levelMap.get("弓兵")*0.1);
		// 获得兵力比例
		// 先损失剑兵
		int solidersV = (int) (soliders * perS);
		if(solidersV > 0){
			if(solidersV >= temp){
				Leader.setArmy((int) (Leader.getArmy() - temp/perS));
				temp = 0;
			}else{
				Leader.setArmy(Leader.getArmy() - soliders);
				temp = temp - solidersV;
			}
		}
		
		if(temp > 0){
			// 按照一个骑兵 一个枪兵 一个弓兵这样损失
			// 首先获得加成百分比
			if(city.getTopography() == 1){
				perC = perC * FightConfig.factorOfCavalrysInLand;
				perI = perI * FightConfig.factorOfInfantryInLand ;
				perA = perA * FightConfig.factorOfArchersInLand ;
			}else if(city.getTopography() == 2){
				perC = perC * FightConfig.factorOfCavalrysInMountain;
				perI = perI * FightConfig.factorOfInfantryInMountain;
				perA = perA * FightConfig.factorOfArchersInMountain ;
			}else if(city.getTopography() == 3){
				perC = perC * FightConfig.factorOfCavalrysInRiver;
				perI = perI * FightConfig.factorOfInfantryInRiver ;
				perA = perA * FightConfig.factorOfArchersInRiver ;
			}
			
			// 获得各兵种的加成战斗力
			int countC = (int) (cavalrys * perC);
			int countI = (int) (infantry * perI);
			int countA = (int) (archers * perA);
			
			int lostC = 0;
			int lostI = 0;
			int lostA = 0;
			// 根据加成战力力再计算损失
			if(countC > 0){
				lostC = temp * countC / (countC + countI + countA);
			}
			if(countI > 0){
				lostI = temp * countI / (countC + countI + countA);
			}
			if(countA > 0){
				lostA = temp * countA / (countC + countI + countA);
			}
			
			Leader.setCavalrys((int) ((Leader.getCavalrys() - lostC/perC)));
			Leader.setInfantry((int) (Leader.getInfantry() - lostI/perI));
			Leader.setArchers((int) (Leader.getArchers() - lostA/perA));
		}
	}

	/**
	 * 
	* @Title: resetCitySoilders
	* @Description: 重新计算城内的兵力
	* @param @param temp    损失的兵力
	* @return void    返回类型
	* @throws
	 */
	private void resetCitySoilders(int temp) {
		// 去掉城内建筑加成
		double perC = 1;
		double perI = 1;
		double perA = 1;
		double perS = 1;
		General general = GeneralFactory.getGeneralById(city.getBelongTo().toString());
		// 得到 骑 枪 弓的级别
		HashMap<String,Integer> levelMap = new HashMap<>();
		for(Arms arms : general.getArmsTotal()){
			levelMap.put(arms.getName(), arms.getLevel());
		}
		perS = perS * (1 + levelMap.get("剑兵")*0.1);
		perC = perC * (1 + levelMap.get("骑兵")*0.1);
		perI = perI * (1 + levelMap.get("枪兵")*0.1);
		perA = perA * (1 + levelMap.get("弓兵")*0.1);
		
		// 获得兵力比例
		// 先损失剑兵

		if(city.getSoilders() > 0){
			if(city.getSoilders() * perS >= temp){
				city.setSoilders((int) (city.getSoilders() - temp/perS));
				return;
			}else{
				temp = (int) (temp - city.getSoilders() * perS);
				city.setSoilders(0);
			}
		}
		
		// 首先获得加成百分比
		if(city.getTopography() == 1){
			perC = perC * FightConfig.factorOfCavalrysInLand; perI = perI * FightConfig.factorOfInfantryInLand ; perA = perA * FightConfig.factorOfArchersInLand ;
		}else if(city.getTopography() == 2){
			perC = perC * FightConfig.factorOfCavalrysInLand; perI = perI * FightConfig.factorOfInfantryInMountain; perA = perA * FightConfig.factorOfArchersInLand ;
		}else if(city.getTopography() == 3){
			perC = perC * FightConfig.factorOfCavalrysInLand; perI = perI * FightConfig.factorOfInfantryInLand ; perA = perA * FightConfig.factorOfArchersInLand ;
		}
		
		// 获得城内各兵种的加成战斗力
		int countC = (int) (city.getCavalrys() * perC);
		int countI = (int) (city.getInfantry() * perI);
		int countA = (int) (city.getArchers() * perA);
		
		int lostC = 0;
		int lostI = 0;
		int lostA = 0;
		// 根据加成战力力再计算损失
		if(countC > 0){
			lostC = temp * countC / (countC + countI + countA);
		}
		if(countI > 0){
			lostI = temp * countI / (countC + countI + countA);
		}
		if(countA > 0){
			lostA = temp * countA / (countC + countI + countA);
		}
		
		city.setCavalrys((city.getCavalrys() <= lostC) ? 0: (int) (city.getCavalrys() - lostC / perC));
		city.setInfantry((city.getInfantry() <= lostI) ? 0: (int) (city.getInfantry() - lostI / perI));
		city.setArchers((city.getArchers() <= lostA) ? 0: (int) (city.getArchers() - lostA / perA));
	}

	public void setLeader(General leader) {
		Leader = leader;
	}

	public General getAttackChief() {
		return AttackChief;
	}

	public void setAttackChief(General attackChief) {
		AttackChief = attackChief;
	}

	public General getAttackVice() {
		return AttackVice;
	}

	public void setAttackVice(General attackVice) {
		AttackVice = attackVice;
	}

	public General getAttackCounsellor() {
		return AttackCounsellor;
	}

	public void setAttackCounsellor(General attackCounsellor) {
		AttackCounsellor = attackCounsellor;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}


	public Integer getSoliders() {
		return soliders;
	}

	public void setSoliders(Integer soliders) {
		this.soliders = soliders;
	}

	public General getDefenceChief() {
		return DefenceChief;
	}

	public void setDefenceChief(General defenceChief) {
		DefenceChief = defenceChief;
	}

	public General getDefenceVice() {
		return DefenceVice;
	}

	public void setDefenceVice(General defenceVice) {
		DefenceVice = defenceVice;
	}

	public General getDefenceCounsellor() {
		return DefenceCounsellor;
	}

	public void setDefenceCounsellor(General defenceCounsellor) {
		DefenceCounsellor = defenceCounsellor;
	}

	public Integer getCavalrys() {
		return cavalrys;
	}

	public void setCavalrys(Integer cavalrys) {
		this.cavalrys = cavalrys;
	}

	public Integer getInfantry() {
		return infantry;
	}

	public void setInfantry(Integer infantry) {
		this.infantry = infantry;
	}

	public Integer getArchers() {
		return archers;
	}

	public void setArchers(Integer archers) {
		this.archers = archers;
	}

	public Integer getAttackSoliderTotalCopy() {
		return attackSoliderTotalCopy;
	}

	public void setAttackSoliderTotalCopy(Integer attackSoliderTotalCopy) {
		this.attackSoliderTotalCopy = attackSoliderTotalCopy;
	}

	public Double getDefenceBulidingAdd() {
		return defenceBulidingAdd;
	}

	public void setDefenceBulidingAdd(Double defenceBulidingAdd) {
		this.defenceBulidingAdd = defenceBulidingAdd;
	}

	public int getAttactlevelAddition() {
		return attactlevelAddition;
	}

	public void setAttactlevelAddition(int attactlevelAddition) {
		this.attactlevelAddition = attactlevelAddition;
	}

	public int getDefencelevelAddition() {
		return defencelevelAddition;
	}

	public void setDefencelevelAddition(int defencelevelAddition) {
		this.defencelevelAddition = defencelevelAddition;
	}

	public int getDefenceLost() {
		return defenceLost;
	}

	public void setDefenceLost(int defenceLost) {
		this.defenceLost = defenceLost;
	}

	public int getAttackLost() {
		return attackLost;
	}

	public void setAttackLost(int attackLost) {
		this.attackLost = attackLost;
	}

	public Double getAttackWeaponAdd() {
		return attackWeaponAdd;
	}

	public void setAttackWeaponAdd(Double attackWeaponAdd) {
		this.attackWeaponAdd = attackWeaponAdd;
	}

	public int getAttackSkillProbability() {
		return attackSkillProbability;
	}

	public void setAttackSkillProbability(int attackSkillProbability) {
		this.attackSkillProbability = attackSkillProbability;
	}

	public int getDefenceSkillProbability() {
		return defenceSkillProbability;
	}

	public void setDefenceSkillProbability(int defenceSkillProbability) {
		this.defenceSkillProbability = defenceSkillProbability;
	}
}
