package cn.eric.game.fujiatianxia6.po;

/**
 * @version 1.0.0
 * @description: 攻城武器
 * 1飞钩　　11连弩车 
 * 2冲天炮　12雷呜车 
 * 3汔油弹　13铁甲车 
 * 4撞木　　14风火轮 
 * 5连弩枪　15火龙枪 
 * 6冲车　　16轰天雷 
 * 7投石器　17毒蜂炮 
 * 8巨弩车　18铁火牛 
 * 9散石机　19加农炮 
 * 10箭塔 
 * @author: eric
 * @date: 2022-10-17 11:28
 **/
public class SiegeWeapon {

    private int id;

    private String name;

    private int price;

    private int power;

    public SiegeWeapon(int id, String name, int price, int power) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.power = power;
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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
