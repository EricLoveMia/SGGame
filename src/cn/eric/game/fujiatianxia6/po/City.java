package cn.eric.game.fujiatianxia6.po;

import cn.eric.game.fujiatianxia6.factory.CityFactory;
import cn.eric.game.fujiatianxia6.factory.GeneralFactory;
import cn.eric.game.fujiatianxia6.factory.GoodsFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 
* <p>Title: City<／p>
 * <p>Description: 城池<／p>
 * <p>Company: want-want<／p>
 * @author 00322027
 * @date 2017年8月3日 下午3:34:37
 */
public class City implements Serializable {
	private String id;
	private int type;   // 类型  1 小城  2 中城  3 大城  4 特大城 5 巨型城
	private String name;   // 城市的名字 徐州 
	private String memo;   // 介绍
	private String state; // 州

	private int purchase; // 购买费用
	private int upgradeLevel; // 升级费用
	private int money; //资金
	private int prosperity; //繁荣度

	private List<General> denfenceGenerals = new ArrayList<General>();  // 防守的武将

	private int cavalrys; // 骑兵数量
	private int infantry; // 枪兵数量
	private int archers;  // 弓箭手数量
	private int soilders;  // 步兵数量

	private int belongTo; //

	/**
	 * 地形  1 平原 2 山地 3 水道   适应兵种  平原 骑>枪>弓  山地 枪>弓>骑   水道 弓>枪>骑  战力分别是 200% 160% 120% 普通兵种战力100%
	 */
	private int topography;

	private int blank = 10;  // 空地数
	private List<Building> bildings = new ArrayList<>();  //
	private int horses;
	private int spears;
	private int bows;

	// 特殊城市  1 一种特产 2 两种特产 0 没有特产
	private int special;

	private CityStore cityStore;

	public City() {

	}

	public City(String id, int type, String name, String state, String memo, Integer purchase, Integer upgradeLevel,
				int prosperity, Integer topography, String goodsName, String specialName, String specialTwoName) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.memo = memo;
		this.state = state;
		this.purchase = purchase;
		this.upgradeLevel = upgradeLevel;
		this.prosperity = prosperity;
		this.topography = topography;
		this.cavalrys = 0;
		this.infantry = 0;
		this.archers = 0;
		this.soilders = 0;
		this.blank = type * 2;
		// 初始化城市的可卖货物
		this.cityStore = new CityStore(GoodsFactory.getByName(goodsName),
				GoodsFactory.getByName(specialName), GoodsFactory.getByName(specialTwoName));
		if (GoodsFactory.getByName(specialTwoName) != null) {
			this.special = 2;
		} else if (GoodsFactory.getByName(specialName) != null) {
			this.special = 1;
		}
	}

	public City(CityFactory.CityTemplate template) {
		this(template.getId(), template.getType(), template.getName(), template.getState(),
				template.getMemo(), template.getPurchase(), template.getUpgradeLevel(),
				template.getProsperity(), template.getTopography(), template.getGoodsId(), template.getSpecialId(),
				template.getSpecialTwoId());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getPurchase() {
		return purchase;
	}
	public void setPurchase(Integer purchase) {
		this.purchase = purchase;
	}
	public Integer getUpgradeLevel() {
		return upgradeLevel;
	}
	public void setUpgradeLevel(Integer upgradeLevel) {
		this.upgradeLevel = upgradeLevel;
	}
	public List<General> getDenfenceGenerals() {
		return denfenceGenerals;
	}
	public void setDenfenceGenerals(List<General> denfenceGenerals) {
		this.denfenceGenerals = denfenceGenerals;
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
	public Integer getSoilders() {
		return soilders;
	}
	public void setSoilders(Integer soilders) {
		this.soilders = soilders;
	}
	public Integer getBelongTo() {
		return belongTo;
	}
	public void setBelongTo(Integer belongTo) {
		this.belongTo = belongTo;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getProsperity() {
		return prosperity;
	}
	public void setProsperity(int prosperity) {
		this.prosperity = prosperity;
	}
	
	public Integer getTopography() {
		return topography;
	}
	public void setTopography(Integer topography) {
		this.topography = topography;
	}
	
	public Integer getBlank() {
		return blank;
	}
	public void setBlank(Integer blank) {
		this.blank = blank;
	}
	public List<Building> getBildings() {
		return bildings;
	}
	public void setBildings(List<Building> bildings) {
		this.bildings = bildings;
	}
	public Integer getHorses() {
		return horses;
	}
	public void setHorses(Integer horses) {
		this.horses = horses;
	}
	public Integer getSpears() {
		return spears;
	}
	public void setSpears(Integer spears) {
		this.spears = spears;
	}
	public Integer getBows() {
		return bows;
	}
	public void setBows(Integer bows) {
		this.bows = bows;
	}
	@Override
	public String toString() {
        return "City [类型=" + type + ", 名称=" + name + ", 介绍=" + memo + ", 购买费用=" + purchase + ", 升级费用="
                + upgradeLevel + ", 当前金钱=" + money + ", 繁荣度=" + prosperity + ", 骑=" + cavalrys
                + ", 枪=" + infantry + ", 弓=" + archers + ", 剑=" + soilders + ", 属于="
                + Optional.ofNullable(GeneralFactory.getGeneralById(belongTo + "")).orElse(new General("空白")).getName() + ", 地形 =" + topography + "]";
	}

	public void setPurchase(int purchase) {
		this.purchase = purchase;
	}

	public void setUpgradeLevel(int upgradeLevel) {
		this.upgradeLevel = upgradeLevel;
	}

	public void setCavalrys(int cavalrys) {
		this.cavalrys = cavalrys;
	}

	public void setInfantry(int infantry) {
		this.infantry = infantry;
	}

	public void setArchers(int archers) {
		this.archers = archers;
	}

	public void setSoilders(int soilders) {
		this.soilders = soilders;
	}

	public void setBelongTo(int belongTo) {
		this.belongTo = belongTo;
	}

	public void setTopography(int topography) {
		this.topography = topography;
	}

	public void setBlank(int blank) {
		this.blank = blank;
	}

	public void setHorses(int horses) {
		this.horses = horses;
	}

	public void setSpears(int spears) {
		this.spears = spears;
	}

	public void setBows(int bows) {
		this.bows = bows;
	}

	public int getSpecial() {
		return special;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public CityStore getCityStore() {
		return cityStore;
	}

	public void setCityStore(CityStore cityStore) {
		this.cityStore = cityStore;
	}

	public boolean checkSpecialBuilding() {
		for (Building building : getBildings()) {
			if (building.id == 10) {
				return true;
			}
		}
		return false;
	}

	public boolean checkSeniorBuilding() {
		for (Building building : getBildings()) {
			if (building.id == 10 && building.level > 1) {
				return true;
			}
		}
		return false;
	}

	public void reset() {
		this.soilders = 0;
		this.belongTo = 0;
		this.money = 0;
		this.cavalrys = 0;
		this.setBildings(new ArrayList<>());
		this.infantry = 0;
		this.archers = 0;
	}
}
