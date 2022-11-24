package cn.eric.game.fujiatianxia6.po;

import java.io.Serializable;

/**
 * 城市建筑物类 最大的城市最多可以造16个建筑，8个防御型 4个一次性 4个周期
 *
 * @author YCKJ2725
 */
public class Building implements Cloneable, Serializable {
	
	public int id;
	public String name;
	public String memo;
	public Integer data;
	/**
	 * 购买费用
	 */
	public Integer purchase;
	/**
	 * 升级费用
     */
    public Integer upgradeLevel;
    /**
     * 1 防御型 增加守城防守伤害 城墙、弩炮各可以增加4个 2 一次性 增加商业值 市场、酒馆  3 周期型 马厩、武器厂、兵器厂、徽兵所
     */
    public int type;
    /**
     * 根据城市等级，不能大于城市等级
     */
    public int level = 1;

    public Building() {
    }

    public Building(int id, String name, String memo, Integer data, int type, Integer purchase, Integer upgradeLevel) {
        this.id = id;
        this.name = name;
        this.memo = memo;
        this.data = data;
        this.type = type;
        this.purchase = purchase;
        this.upgradeLevel = upgradeLevel;
    }

    public Building(int id, String name, String memo, Integer data, Integer purchase, Integer upgradeLevel, int type, int level) {
        this.id = id;
        this.name = name;
        this.memo = memo;
        this.data = data;
        this.purchase = purchase;
        this.upgradeLevel = upgradeLevel;
        this.type = type;
        this.level = level;
    }

    @Override
    public String toString() {
        return "建筑 [id=" + id + ", 名称=" + name + ", 说明=" + memo + ", 购买费用=" + purchase
                + ", 升级费用=" + upgradeLevel + ", 类型=" + (type == 1 ? "防御型" : type == 2 ? "一次性" : "周期") + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
	}
}
