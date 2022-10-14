package cn.eric.game.fujiatianxia6.po.store;

import java.io.Serializable;

/**
 * Company: ClickPaaS
 *
 * @version 1.0.0
 * @description: 商品
 * @author: 钱旭
 * @date: 2022-10-09 10:34
 **/
public class Goods implements Serializable, Cloneable {

    private int id;

    private String name;

    private int price;

    // 保质期
    private int qualityGuaranteePeriod;

    // 等级 1 2 3
    private int quality;

    private int maxProfit;

    // 存储时间
    private int storageTime;

    private int salePrice;

    private String place;

    private String description;

    public Goods(int id, String name, int price, int qualityGuaranteePeriod,
                 int quality, int maxProfit, String place, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qualityGuaranteePeriod = qualityGuaranteePeriod;
        this.quality = quality;
        this.maxProfit = maxProfit;
        this.place = place;
        this.description = description;
        this.storageTime = 0;
        this.salePrice = price;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQualityGuaranteePeriod() {
        return qualityGuaranteePeriod;
    }

    public void setQualityGuaranteePeriod(int qualityGuaranteePeriod) {
        this.qualityGuaranteePeriod = qualityGuaranteePeriod;
    }

    public int getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(int storageTime) {
        this.storageTime = storageTime;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getMaxProfit() {
        return maxProfit;
    }

    public void setMaxProfit(int maxProfit) {
        this.maxProfit = maxProfit;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
