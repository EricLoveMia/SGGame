package cn.eric.game.fujiatianxia6.po;

import cn.eric.game.fujiatianxia6.po.store.Goods;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0.0
 * @description: 运输队
 * @author: eric
 * @date: 2022-10-09 10:33
 **/
public class TransportTeam implements Serializable {

    private List<Goods> goodsList;

    private int load;

    private int used;

    public TransportTeam(List<Goods> goodsList, int load, int used) {
        this.goodsList = goodsList;
        this.load = load;
        this.used = used;
    }

    @Override
    public String toString() {
        return "TransportTeam{" +
                "goodsList=" + goodsList +
                ", load=" + load +
                ", used=" + used +
                '}';
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
