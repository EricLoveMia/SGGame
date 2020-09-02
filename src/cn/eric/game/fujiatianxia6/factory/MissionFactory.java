package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.interfaces.Mission;
import cn.eric.game.fujiatianxia6.service.mission.AbstractMission;
import cn.eric.game.fujiatianxia6.service.mission.GetCitysMission;

import java.util.List;

/**
 * @ClassName MissionFactory
 * @Description: 任务工厂
 * @Author YCKJ2725
 * @Date 2020/3/14
 * @Version V1.0
 **/
public class MissionFactory extends AbstractMission {


    public Mission getMission(int type){
        switch (type){
            case 1 :
                return new GetCitysMission();
            default:
                return null;
        }
    }

    /**
     * @MethodName: getMissions
     * @Description: 获取所有的任务
     * @Param: []
     * @Return: java.util.List<cn.eric.game.fujiatianxia6.interfaces.Mission>
     * @Author: YCKJ2725
     * @Date: 2020/3/16 17:52
    **/
    @Override
    public List<Mission> getMissions() {
        return super.getMissions();
    }

    /**
     * @MethodName: initMissions
     * @Description: 初始化任务模板
     * @Param: []
     * @Return: void
     * @Author: YCKJ2725
     * @Date: 2020/3/16 18:45
    **/
    public void initMissionTemplets(){
        // 初始化夺取城市任务模板

    }
}
