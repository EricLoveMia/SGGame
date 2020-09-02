package cn.eric.game.fujiatianxia6.service.mission;

import cn.eric.game.fujiatianxia6.interfaces.Mission;
import cn.eric.game.fujiatianxia6.po.General;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AbstractMission
 * @Description: 任务抽象类
 * @Author YCKJ2725
 * @Date 2020/3/14
 * @Version V1.0
 **/
public class AbstractMission implements Mission {

    public static List<Mission> allMissionList = new ArrayList<>(32);

    public static Map<String,List<Mission>> generalMissionMaps = new HashMap<>();

    @Override
    public void desc() {

    }

    @Override
    public Integer getCompleteType() {
        return null;
    }

    @Override
    public Integer getCompleteNumber() {
        return null;
    }

    @Override
    public List<Mission> getMissions() {
        return null;
    }

    @Override
    public List<Mission> getCurrentMission(General general) {
        return null;
    }

    @Override
    public void getMissionReward(Mission mission, General general) {
        System.out.println("获取奖励");
    }
}
