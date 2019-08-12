package cn.eric.game.fujiatianxia6.service;

import cn.eric.game.fujiatianxia6.po.Arms;
import cn.eric.game.fujiatianxia6.po.General;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eric
 * @version 1.0
 * @ClassName: ArmsService
 * @Description: TODO
 * @company lsj
 * @date 2019/2/28 9:31
 **/
public class ArmsService {
    
    public static void setArms(General player) {
        List<Arms> armsList = new ArrayList<>();
        Arms arms1 = new Arms("骑兵",1);
        Arms arms2 = new Arms("弓兵",1);
        Arms arms3 = new Arms("枪兵",1);
        Arms arms4 = new Arms("剑兵",1);
        armsList.add(arms1);
        armsList.add(arms2);
        armsList.add(arms3);
        armsList.add(arms4);

        // 设定特殊兵种

        player.setArmsTotal(armsList);
    }

    /**
     * @description: 通过主公和兵种获得对应兵种下的级别
     * @author Eric
     * @date 18:03 2019/7/11
     * @param general
     * @param type
     * @throws
     * @return cn.eric.game.fujiatianxia6.po.Arms
     **/
    public static Arms getArmsByGeneralAndName(General general, String type) {

        List<Arms> armsTotal = general.getArmsTotal();
        for (Arms arms : armsTotal) {
            if(type.equals(arms.getName())){
                return arms;
            }
        }
        return null;
    }
}
