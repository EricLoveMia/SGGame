package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @version 1.0.0
 * @description:
 * @date: 2022-10-17 11:34
 **/
public abstract class SilkBag implements Bag, Serializable {

    private int id;

    private String name;

    // 1 正面的 2 负面的
    private int type;

    private General origin;

    private General targetGeneral;

    private City targetCity;

    public SilkBag(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public General getTargetGeneral() {
        return targetGeneral;
    }

    public void setTargetGeneral(General targetGeneral) {
        this.targetGeneral = targetGeneral;
    }

    public City getTargetCity() {
        return targetCity;
    }

    public void setTargetCity(City targetCity) {
        this.targetCity = targetCity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void execute(General origin, General targetGeneral, City targetCity) {
        origin.getBag().getSilkBags().remove(this);
        if (type == 2) {
            List<SilkBag> silkBags = targetGeneral.getBag().getSilkBags();
            // 是否有无懈可击
            Optional<SilkBag> 无懈可击 = silkBags.stream().filter(e -> e.name().equals("无懈可击")).findFirst();
            if (无懈可击.isPresent()) {
                SilkBag bag = 无懈可击.get();
                System.out.println("主公" + getTargetGeneral().getName() + "释放锦囊" + bag.name());
                silkBags.remove(bag);
                return;
            }
        }
        run(origin, targetGeneral, targetCity);
    }

    protected abstract void run(General origin, General targetGeneral, City targetCity);

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public General getOrigin() {
        return origin;
    }

    public void setOrigin(General origin) {
        this.origin = origin;
    }

    @Override
    public String name() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
