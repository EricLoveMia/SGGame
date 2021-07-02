package cn.eric.game.fujiatianxia6.po;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName CampaignMap
 * @Description: 战役地图 通过xml导入
 * @Author YCKJ2725
 * @Date 2020/11/30
 * @Version V1.0
 **/
public class CampaignMap implements Serializable {

    private String id;

    private String name;

    private int size;

    private List<String> city;

    private List<String> luckyTurn;

    private List<String> wine;

    private List<String> pause;

    private List<String> soldiers;

    private List<String> cityId;

    /**
     * 默认主公 第一个就是玩家主公
     **/
    private List<String> defaultPlayer = Arrays.asList("1","2","3");

    private String memo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<String> getCity() {
        return city;
    }

    public void setCity(List<String> city) {
        this.city = city;
    }

    public List<String> getLuckyTurn() {
        return luckyTurn;
    }

    public void setLuckyTurn(List<String> luckyTurn) {
        this.luckyTurn = luckyTurn;
    }

    public List<String> getWine() {
        return wine;
    }

    public void setWine(List<String> wine) {
        this.wine = wine;
    }

    public List<String> getPause() {
        return pause;
    }

    public void setPause(List<String> pause) {
        this.pause = pause;
    }

    public List<String> getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(List<String> soldiers) {
        this.soldiers = soldiers;
    }

    public List<String> getCityId() {
        return cityId;
    }

    public void setCityId(List<String> cityId) {
        this.cityId = cityId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public List<String> getDefaultPlayer() {
        return defaultPlayer;
    }

    public void setDefaultPlayer(List<String> defaultPlayer) {
        this.defaultPlayer = defaultPlayer;
    }

    @Override
    public String toString() {
        return "战役 " +
                "id='" + id + '\'' +
                ", 名称='" + name + '\'' +
                ", 地块数量=" + size +
                ", 说明='" + memo + '\'' +
                ", 对战主公='" + defaultPlayer.toString() + "\'" +
                "}";
    }
}
