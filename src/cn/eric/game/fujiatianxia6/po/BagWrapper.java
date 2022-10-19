package cn.eric.game.fujiatianxia6.po;

import cn.eric.game.fujiatianxia6.po.bag.SilkBag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0.0
 * @description: 主公的包裹
 * @author: eric
 * @date: 2022-10-17 15:48
 **/
public class BagWrapper implements Serializable {

    public BagWrapper() {
        silkBags = new ArrayList<>();
    }

    public BagWrapper(List<SilkBag> silkBags) {
        this.silkBags = silkBags;
    }

    private List<SilkBag> silkBags;

    public List<SilkBag> getSilkBags() {
        return silkBags;
    }

    public void setSilkBags(List<SilkBag> silkBags) {
        this.silkBags = silkBags;
    }
}
