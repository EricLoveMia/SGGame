package cn.eric.game.fujiatianxia6.service.mission;

import cn.eric.game.fujiatianxia6.interfaces.Mission;
import cn.eric.game.fujiatianxia6.interfaces.Reward;
import cn.eric.game.fujiatianxia6.po.General;

import java.util.List;

/**
 * @ClassName GetCitysMission
 * @Description: 夺取城市任务
 * @Author YCKJ2725
 * @Date 2020/3/16
 * @Version V1.0
 **/
public class GetCitysMission extends AbstractMission{

    /**
     * id
     */
    private int id;

    /**
     * 获取城市的数量
     */
    private int goalNum;

    /**
     * 剩余数量
     */
    private int surplusNum;

    /**
     * 归属主公
     */
    private String generalId;

    /**
     * 说明
    **/
    private String desc;

    /**
     * 奖励
     **/
    private Reward reward;

    @Override
    public void desc() {
        System.out.println("获取城市任务");
        System.out.println(this.desc);
    }

    @Override
    public Integer getCompleteType() {
        return super.getCompleteType();
    }

    @Override
    public Integer getCompleteNumber() {
        return super.getCompleteNumber();
    }

    @Override
    public List<Mission> getMissions() {
        return super.getMissions();
    }

    @Override
    public List<Mission> getCurrentMission(General general) {
        return super.getCurrentMission(general);
    }

    @Override
    public void getMissionReward(Mission mission, General general) {
        super.getMissionReward(mission, general);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoalNum() {
        return goalNum;
    }

    public void setGoalNum(int goalNum) {
        this.goalNum = goalNum;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public int getSurplusNum() {
        return surplusNum;
    }

    public void setSurplusNum(int surplusNum) {
        this.surplusNum = surplusNum;
    }

    public String getGeneralId() {
        return generalId;
    }

    public void setGeneralId(String generalId) {
        this.generalId = generalId;
    }
}
