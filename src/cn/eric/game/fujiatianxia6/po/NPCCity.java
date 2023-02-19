package cn.eric.game.fujiatianxia6.po;

import java.util.List;

/**
 * @version 1.0.0
 * @description: npc城市
 * @author: eric
 * @date: 2022-10-19 10:47
 **/
public class NPCCity {

    private String beloneTo;
    private String cityId;
    private int cavalrys;
    private int infantry;
    private int archers;
    private int soilders;
    private List<String> generalIds;
    private int money;
    private List<String> buildings;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public int getCavalrys() {
        return cavalrys;
    }

    public void setCavalrys(int cavalrys) {
        this.cavalrys = cavalrys;
    }

    public int getInfantry() {
        return infantry;
    }

    public void setInfantry(int infantry) {
        this.infantry = infantry;
    }

    public int getArchers() {
        return archers;
    }

    public void setArchers(int archers) {
        this.archers = archers;
    }

    public int getSoilders() {
        return soilders;
    }

    public void setSoilders(int soilders) {
        this.soilders = soilders;
    }

    public List<String> getGeneralIds() {
        return generalIds;
    }

    public void setGeneralIds(List<String> generalIds) {
        this.generalIds = generalIds;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<String> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<String> buildings) {
        this.buildings = buildings;
    }

    public String getBeloneTo() {
        return beloneTo;
    }

    public void setBeloneTo(String beloneTo) {
        this.beloneTo = beloneTo;
    }
}
