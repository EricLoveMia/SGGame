package cn.eric.game.fujiatianxia6.service.event.reward;

import cn.eric.game.fujiatianxia6.po.General;

/**
 * @ClassName EventReward
 * @Description: 事件奖励类
 * @Author YCKJ2725
 * @Date 2020/9/18
 * @Version V1.0
 **/
public abstract class EventReward {

    /**
     * 奖励名称
     */
    private String name;

    /**
     * 奖励值
     */
    private String dataGive;

    /**
     * 奖励值
     */
    private String dataPunish;

    /**
     * 给与奖励
     */
    public abstract void reward(General general, Boolean success);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataGive() {
        return dataGive;
    }

    public void setDataGive(String dataGive) {
        this.dataGive = dataGive;
    }

    public String getDataPunish() {
        return dataPunish;
    }

    public void setDataPunish(String dataPunish) {
        this.dataPunish = dataPunish;
    }
}
