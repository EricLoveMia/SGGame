package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.po.Building;
import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

import java.util.*;
import java.util.stream.Collectors;

// 城市建筑物工厂类
public class BuildingFactory {
	
	public static List<Building> buildings = new ArrayList<>();
	
	public static void initBuildings(){
        buildings = new ArrayList<>();
        buildings.add(new Building(1, "城墙", "防守时增加进攻方损失15%，每升一级增加5%的损失，仅守城适用", 15, 1, 5000, 5000));
        buildings.add(new Building(2, "弩炮", "防守时增加进攻方损失8%，每升一级增加2%的损失,守城野战适用", 8, 1, 5000, 5000));
        buildings.add(new Building(3, "市场", "增加繁荣度50%，每升一级增加10%的繁荣度", 50, 2, 5000, 5000));
        buildings.add(new Building(4, "道路", "增加繁荣度50%，每升一级增加10%的繁荣度", 50, 2, 5000, 5000));
        buildings.add(new Building(5, "武器阁", "购买武将专属的地方，每升一级降低购买价格10%", 50, 4, 30000, 30000));
        buildings.add(new Building(6, "屯兵所", "每回合根据守军数量增加钱粮", 50, 4, 5000, 5000));
        buildings.add(new Building(7, "马厩", "每回合结束驯养马匹，将城市内的剑兵转换成骑兵，升级增加转换率", 50, 3, 5000, 5000));
        buildings.add(new Building(8, "兵器厂", "每回合结束生产枪和弓，并将城市内的剑兵转换成弓兵和枪兵，升级增加转换率", 25, 3, 5000, 5000));
        buildings.add(new Building(9, "徽兵所", "每回合结束招募剑兵，升级增加招募的数量", 25, 3, 5000, 5000));
        buildings.add(new Building(10, "特产店", "购买特产的地方", 25, 3, 5000, 5000));
    }
	
	/**
	 * 
	* @Title: showBuildings
	* @Description: 显示可选建筑的名称
	 * @param
	 * @return void    返回类型
	 * @throws
	 */
	public static void showBuildings() {
		System.out.println("建筑为");
		for (Building building : buildings) {
			System.out.println(building.toString());
		}
	}

	public static void showBuildingsWithCity(City city) {
		System.out.println("建筑为");
		for (Building building : buildings) {
			// 特产坊
			if (city.getSpecial() == 0 && building.id == 10) {
				continue;
			}
			System.out.print(building.toString());
			if ((countBuildsInCity(city, building.id) > 0)) {
				System.out.print(" 不可选");
			} else {
				System.out.print(" 可选");
			}
			System.out.println();
		}
	}

	/**
	 * @param @param city    设定文件
	 * @return void    返回类型
	 * @throws
	 * @Title: showBuildings
	 * @Description: 展示目前城市已有的建筑名称
	 */
	public static void showBuildings(City city) {
		System.out.println("城市内已有建筑为");
		if( city!=null && city.getBildings().size()>0){
			for (Building building : city.getBildings()) {
				System.out.println(building.toString());
			}
		}
	}
	
	public static void buildInCity(City city, General general) throws CloneNotSupportedException {
		// 升级已有的建筑
//		if(city.getBildings().size() > 0) {
//			System.out.println("是否要升级建筑");
//			for (Building building : city.getBildings()) {
//				System.out.println(building.toString());
//			}
//			System.out.println("选择要升级的建筑");
//			// TODO
//		}

		System.out.println("是否要建设新的建筑");
		// 首先看还有没有空地
		if (city.getBlank() <= 0) {
			System.out.println("没有空地了，无法建设");
		} else {
			while (true) {
				System.out.println("请选择您想要建设的建筑,0表示不建设");
				showBuildingsWithCity(city);
				// 检查是否是机器人
				int choise;
				// TODO
				if (general.isReboot()) {
					// 如果没钱
					if (general.getMoney() < 10000) {
						break;
					}
					// 如果都被建满了
					if (city.getBildings().size() >= 9) {
						break;
					}
					choise = new Random().nextInt(8) + 1;
					while ((choise == 1 && (countBuildsInCity(city, 1) >= 1)) || (choise == 2 && (countBuildsInCity(city, 2) >= 1))
							|| (choise == 3 && (countBuildsInCity(city, 3) >= 1)) || (choise == 4 && (countBuildsInCity(city, 4) >= 1))
							|| (choise == 5 && (countBuildsInCity(city, 5) >= 1)) || (choise == 6 && (countBuildsInCity(city, 6) >= 1))
							|| (choise == 7 && (countBuildsInCity(city, 7) >= 1)) || (choise == 8 && (countBuildsInCity(city, 8) >= 1))
							|| (choise == 9 && (countBuildsInCity(city, 9) >= 1)) || (choise == 10 && (countBuildsInCity(city, 10) >= 1))) {
						choise = new Random().nextInt(8) + 1;
					}
				}else {
					Scanner input = new Scanner(System.in);
					choise = input.nextInt();
				}


				if(choise==0){
					break;
				}else if(choise<0 || choise>12){
					System.out.println("数字错误，请重新选择");
				} else if ((choise == 1 && (countBuildsInCity(city, 1) < 1)) || (choise == 2 && (countBuildsInCity(city, 2) < 1))
						|| (choise == 3 && (countBuildsInCity(city, 3) < 1)) || (choise == 4 && (countBuildsInCity(city, 4) < 1))
						|| (choise == 5 && (countBuildsInCity(city, 5) < 1)) || (choise == 6 && (countBuildsInCity(city, 6) < 1))
						|| (choise == 7 && (countBuildsInCity(city, 7) < 1)) || (choise == 8 && (countBuildsInCity(city, 8) < 1))
						|| (choise == 9 && (countBuildsInCity(city, 9) < 1)) || (choise == 10 && (countBuildsInCity(city, 10) < 1))) {

					Building clone = (Building) getBuildById(choise).clone();
					if (general.getMoney() < clone.purchase) {
						System.out.println("对不起您的钱不够");
						if (general.isReboot()) {
							break;
						}
					} else {
						try {
							city.getBildings().add(clone);
							general.setMoney(general.getMoney() - clone.purchase);
							resetCityByNewBilding(city,choise);
						}catch(Exception e){
							e.printStackTrace();
							System.out.println("出错啦");
						}
						break;
					}
				}else{
					if(general.isReboot()){
						System.out.println("不能重复建设");
						break;
					}
					System.out.println("不能重复建设，请重新选择");
				}
			}
		}
	}
	
	// 根据新建的
	private static void resetCityByNewBilding(City city, int choise) {
		// 增加繁荣度
		if (choise == 3 || choise == 4) {
			city.setProsperity(city.getProsperity() + 200);
		}
		// 如果是特产坊
		if (choise == 10) {
			city.getCityStore().setSpecialtyRest(city.getCityStore().getSpecialtyTotal());
		}
	}

	// 根据ID返回建筑
	public static Building getBuildById(int choise) {
		if(choise>0){
			for (Building building : buildings) {
				if(building.id == choise){
					return building;
				}
			}
		}
		return null;
	}

	// 计算城市内该建筑的数量
	public static int countBuildsInCity(City city, int id) {
		int count = 0;
		if( city!=null && city.getBildings() != null && city.getBildings().size()>0){
			for (Building building : city.getBildings()) {
				if(building.id == id){
					count++;
				}
			}
		}
		return count;
	}

	/** 马厩 剑兵转换成骑兵 */
	public static void computeHorse() {
		List<City> citys = CityFactory.citys;
		for (City city : citys) {
			// 看看城市里面的建筑
			List<Building> bildings = city.getBildings();
			if (bildings != null) {
				for (Building building : bildings) {
					if (building.id == 7) {
						computeArmyToCavalrys(city, building);
					}
				}
			}
		}
	}

    /** 马厩 剑兵转换成弓箭手和骑兵 */
    public static void computeInAndAr() {
		List<City> citys = CityFactory.citys;
		for (City city : citys) {
			// 看看城市里面的建筑
			List<Building> bildings = city.getBildings();
			if (bildings != null) {
				for (Building building : bildings) {
					if (building.id == 8) {
						computeWeapons(city, building);
					}
				}
			}
        }
    }


    /** 剑兵转骑兵 */
	private static void computeArmyToCavalrys(City city, Building building) {

//		Integer belongTo = city.getBelongTo();
//		General general = GeneralFactory.getGeneralById(belongTo.toString());
        // 查看城市里面有多少剑兵
        Integer soilders = city.getSoilders();
        if (soilders == null || soilders <= 0) {
            return;
        }
        // 查看建筑的级别
        int buildingLevel = building.level;

        // 转换的数量是 10 + 级别 * 4;  最高5级 最多一回合20个
        List<General> denfenceGenerals = city.getDenfenceGenerals();
        if (denfenceGenerals == null || denfenceGenerals.size() == 0) {
            return;
        }
        GeneralFactory.sortByCharm(denfenceGenerals);
        General general = denfenceGenerals.get(0);
        int add = (int) (Integer.parseInt(general.getCharm()) * 0.5 * buildingLevel);
        add = SkillFactory.CheckCitySkill(add, denfenceGenerals, 3);

        // 如果已经超过了本城的数量
        if (add > city.getSoilders()) {
            add = city.getSoilders();
        }
        // 增加骑兵 减少步兵
		city.setCavalrys(Optional.ofNullable(city.getCavalrys()).orElse(0) + add );
        city.setSoilders(city.getSoilders() - add);
		System.out.println("城市" + city.getName() + " 减少了剑兵" + add + "个，增加了骑兵" + add + "个");
	}


	public static void computeWeapons(City city, Building building) {
        // 查看城市里面有多少剑兵
        Integer soilders = city.getSoilders();
        if(soilders <= 0){
            return;
        }
        // 查看建筑的级别
        int buildingLevel = building.level;
        // 转换的数量是 10 + 级别 * 2;  最高5级 最多一回合20个
		List<General> denfenceGenerals = city.getDenfenceGenerals();
        if (denfenceGenerals != null && denfenceGenerals.size() > 0) {
            GeneralFactory.sortByCharm(denfenceGenerals);
            General general = denfenceGenerals.get(0);
            int add = (int) (Integer.parseInt(general.getCharm()) * 0.4 * buildingLevel);

            add = SkillFactory.CheckCitySkill(add, city.getDenfenceGenerals(), 2);

            // 如果已经超过了本城的数量
            if (add * 2 > city.getSoilders()) {
                add = 0;
            }
            // 增加弓兵和枪兵 减少步兵
            city.setInfantry(Optional.ofNullable(city.getInfantry()).orElse(0) + add);
            city.setArchers(Optional.ofNullable(city.getArchers()).orElse(0) + add);
            city.setSoilders(city.getSoilders() - add * 2);
            System.out.println("城市" + city.getName() + "增加了枪兵" + add + "个");
            System.out.println("城市" + city.getName() + "增加了弓兵" + add + "个");
        }
	}

	// 升级建筑
    public static void upgradedBuild(City defence, General player) {
		// 查看城市中有哪些建筑
		showBuildings(defence);
		System.out.println("请选择需要升级的建筑 0 放弃");
		int choise = 0;
		Map<Integer, Building> collect = defence.getBildings().stream().collect(Collectors.toMap(building -> building.id, building -> building));

		if(player.isReboot()){
			if(collect.size() == 0){
				choise = 0;
			}else {
				// 查看金额够升级哪个的 先看级别 再看金钱 升级费用不能超过总金额的1/4
				Set<Map.Entry<Integer, Building>> entries = collect.entrySet();
				Iterator<Map.Entry<Integer, Building>> iterator = entries.iterator();
				while (iterator.hasNext()) {
					Map.Entry<Integer, Building> next = iterator.next();
					if (next.getValue().level < defence.getType()) {
						if (next.getValue().upgradeLevel < player.getMoney() / 4) {
							choise = next.getKey();
							break;
						}
					}
				}
			}
		} else {
			Scanner input = new Scanner(System.in);
			choise = input.nextInt();
		}
		if (choise == 0) {
			return;
		}
		Building building = collect.get(choise);
		if (building == null) {
			System.out.println("对不起，没有对应的建筑");
			return;
		}
		if (building.upgradeLevel > player.getMoney()) {
			System.out.println("对不起，您没有足够的金钱");
			return;
		}
		if (building.level >= defence.getType()) {
			System.out.println("对不起，建筑等级无法超过城市的等级");
			return;
		}
		// 升级
		building.level = building.level + 1;
		player.setMoney(player.getMoney() - building.upgradeLevel);
		System.out.println(building.name + "升级成功，目前等级" + building.level);
		checkEffect(building, defence);
	}

	private static void checkEffect(Building building, City city) {
        if (building.id == 10 && building.level == 2) {
            city.getCityStore().setSeniorRest(city.getCityStore().getSeniorTotal());
        }

        if (building.id == 3 || building.id == 4) {
            city.setProsperity(city.getProsperity() + 100);
        }
    }
}
