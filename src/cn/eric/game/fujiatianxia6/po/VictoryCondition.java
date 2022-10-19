package cn.eric.game.fujiatianxia6.po;

import java.util.List;

/**
 * Company: ClickPaaS
 *
 * @version 1.0.0
 * @description: 胜利条件
 * @author: eric
 * @date: 2022-10-19 10:44
 **/
public class VictoryCondition {

    // 1 默认干掉所有的其他主公  2 占领固定的城池
    private int type = 1;

    private List<String> cityIds;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getCityIds() {
        return cityIds;
    }

    public void setCityIds(List<String> cityIds) {
        this.cityIds = cityIds;
    }
}
