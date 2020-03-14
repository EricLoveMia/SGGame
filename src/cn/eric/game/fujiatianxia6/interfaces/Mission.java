package cn.eric.game.fujiatianxia6.interfaces;

import cn.eric.game.fujiatianxia6.po.General;

import java.util.List;

/**
 * 任务
 * @Author: YCKJ2725
 * @Date: 2020/3/13 15:49
**/
public interface Mission {

    /**
     * 任务说明
     */
    public void desc();

    /**
     * 获取任务完成类型  1 占领XX座城市  2  获取XX个武将  3  歼灭XX个敌方兵力  4  总携带金额达到XX  5 单挑XX次  6 野战XX次
     */
    public Integer getCompleteType();

    /**
     * 获取任务完成需要的数量
     */
    public Integer getCompleteNumber();

    /**
     * 获取所有任务列表
     */
    public List<Mission> getMissions();

    /**
     * 获取某个主公的当前任务
     */
    public List<Mission> getCurrentMission(General general);

    /**
     * 获取奖励 不返回
     */
    public void getMissionReward(Mission mission,General general);
}
