package cn.eric.game.fujiatianxia6.po;

import cn.eric.game.fujiatianxia6.factory.GoodsFactory;
import cn.eric.game.fujiatianxia6.po.store.Goods;

import java.io.Serializable;

/**
 * @version 1.0.0
 * @description: 城内商店
 * @author: eric
 * @date: 2022-10-09 09:59
 **/
public class CityStore implements Serializable {

    private int commonGoodsId;
    private int commonTotal;
    private int commonRest;

    private boolean hasSpecialty;
    private int specialtyGoodsId;
    private int specialtyTotal;
    private int specialtyRest;

    private boolean hasSenior;
    private int seniorGoodsId;
    private int seniorTotal;
    private int seniorRest;


    @Override
    public String toString() {
        String result = "普通商品 " + GoodsFactory.getById(commonGoodsId).getName() + ",单价" + GoodsFactory.getById(commonGoodsId).getPrice()
                + ",剩余数量（" + commonRest + "/" + commonTotal + "）\n";
        if (hasSpecialty) {
            result += "特产商品 " + GoodsFactory.getById(specialtyGoodsId).getName() + ",单价" + GoodsFactory.getById(specialtyGoodsId).getPrice()
                    + ",剩余数量（" + specialtyRest + "/" + specialtyTotal + "）\n";
        }
        if (hasSenior) {
            result += "特产商品 " + GoodsFactory.getById(seniorGoodsId).getName() + ",单价" + GoodsFactory.getById(seniorGoodsId).getPrice()
                    + ",剩余数量（" + seniorRest + "/" + seniorTotal + "）\n";
        }
        return result;
    }

    public CityStore(int commonGoodsId, int commonTotal, int commonRest, boolean hasSpecialty, int specialtyGoodsId, int specialtyTotal, int specialtyRest, boolean hasSenior, int seniorGoodsId, int seniorTotal, int seniorRest) {
        this.commonGoodsId = commonGoodsId;
        this.commonTotal = commonTotal;
        this.commonRest = commonRest;
        this.hasSpecialty = hasSpecialty;
        this.specialtyGoodsId = specialtyGoodsId;
        this.specialtyTotal = specialtyTotal;
        this.specialtyRest = specialtyRest;
        this.hasSenior = hasSenior;
        this.seniorGoodsId = seniorGoodsId;
        this.seniorTotal = seniorTotal;
        this.seniorRest = seniorRest;
    }

    public CityStore(Goods common, Goods specialty, Goods seniorSpecialty) {
        this.commonGoodsId = common.getId();
        if (common.getPrice() < 500) {
            this.commonTotal = 15;
        } else if (common.getPrice() < 1500) {
            this.commonTotal = 8;
        } else {
            this.commonTotal = 4;
        }
        this.commonRest = this.commonTotal;


        this.hasSpecialty = specialty != null;
        if (specialty != null) {
            this.specialtyGoodsId = specialty.getId();
            if (specialty.getPrice() < 1000) {
                this.specialtyTotal = 16;
            } else if (specialty.getPrice() < 2000) {
                this.specialtyTotal = 8;
            } else if (specialty.getPrice() < 4000) {
                this.specialtyTotal = 4;
            } else if (specialty.getPrice() < 8000) {
                this.specialtyTotal = 2;
            } else {
                this.specialtyTotal = 1;
            }
            this.specialtyRest = 0;
        }

        this.hasSenior = seniorSpecialty != null;
        if (seniorSpecialty != null) {
            this.seniorGoodsId = seniorSpecialty.getId();
            if (seniorSpecialty.getPrice() < 1000) {
                this.seniorTotal = 16;
            } else if (seniorSpecialty.getPrice() < 2000) {
                this.seniorTotal = 8;
            } else if (seniorSpecialty.getPrice() < 4000) {
                this.seniorTotal = 4;
            } else if (seniorSpecialty.getPrice() < 8000) {
                this.seniorTotal = 2;
            } else {
                this.seniorTotal = 1;
            }
            this.seniorRest = 0;
        }
    }

    public int getCommonGoodsId() {
        return commonGoodsId;
    }

    public void setCommonGoodsId(int commonGoodsId) {
        this.commonGoodsId = commonGoodsId;
    }

    public int getCommonTotal() {
        return commonTotal;
    }

    public void setCommonTotal(int commonTotal) {
        this.commonTotal = commonTotal;
    }

    public int getCommonRest() {
        return commonRest;
    }

    public void setCommonRest(int commonRest) {
        this.commonRest = commonRest;
    }

    public boolean isHasSpecialty() {
        return hasSpecialty;
    }

    public void setHasSpecialty(boolean hasSpecialty) {
        this.hasSpecialty = hasSpecialty;
    }

    public int getSpecialtyGoodsId() {
        return specialtyGoodsId;
    }

    public void setSpecialtyGoodsId(int specialtyGoodsId) {
        this.specialtyGoodsId = specialtyGoodsId;
    }

    public int getSpecialtyTotal() {
        return specialtyTotal;
    }

    public void setSpecialtyTotal(int specialtyTotal) {
        this.specialtyTotal = specialtyTotal;
    }

    public int getSpecialtyRest() {
        return specialtyRest;
    }

    public void setSpecialtyRest(int specialtyRest) {
        this.specialtyRest = specialtyRest;
    }

    public boolean isHasSenior() {
        return hasSenior;
    }

    public void setHasSenior(boolean hasSenior) {
        this.hasSenior = hasSenior;
    }

    public int getSeniorGoodsId() {
        return seniorGoodsId;
    }

    public void setSeniorGoodsId(int seniorGoodsId) {
        this.seniorGoodsId = seniorGoodsId;
    }

    public int getSeniorTotal() {
        return seniorTotal;
    }

    public void setSeniorTotal(int seniorTotal) {
        this.seniorTotal = seniorTotal;
    }

    public int getSeniorRest() {
        return seniorRest;
    }

    public void setSeniorRest(int seniorRest) {
        this.seniorRest = seniorRest;
    }
}
