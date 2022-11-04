package cn.eric.game.fujiatianxia6.service.event.reward;

import cn.eric.game.fujiatianxia6.po.General;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName AbilityReward
 * @Description: 属性增加
 * @Author YCKJ2725
 * @Date 2020/9/18
 * @Version V1.0
 **/
public class AbilityReward extends EventReward {

    public AbilityReward(String name, String dataGive, String dataPunish, String type) {
        super(name, dataGive, dataPunish, type);
    }

    @Override
    public void reward(General general, Boolean success) {
        // 选择一个武将增加 属性
        General luckyDog = null;
        System.out.println("选择一个武将增加" + this.getDataGive() + "点" + EventRewardTypeEnum.getText(this.getType()) + " 0 放弃");
        List<General> generals = general.getGenerals();
        if (general.isReboot()) {
            // 随机一个
            Collections.shuffle(generals);
            luckyDog = generals.get(0);
        } else {
            for (int i = 1; i <= generals.size(); i++) {
                System.out.println(i + ": " + " :姓名：" + generals.get(i - 1).getName() + "武力：" + generals.get(i - 1).getAttack()
                        + "统帅：" + generals.get(i - 1).getCommand() + "智力：" + generals.get(i - 1).getIntelligence());
            }
            while (true) {
                Scanner input = new Scanner(System.in);
                int choise = input.nextInt();
                if (choise == 0) {
                    return;
                } else if (choise < 0 || (choise >= generals.size() + 2)) {
                    System.out.println("请选择合适的数字");
                } else {
                    try {
                        luckyDog = generals.get(choise - 1);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (luckyDog != null) {
            if (EventRewardTypeEnum.ATTACK.getCode().equals(this.getType())) {
                luckyDog.setAttack(Integer.parseInt(luckyDog.getAttack()) + Integer.parseInt(this.getDataGive()) + "");
                System.out.println("武将" + luckyDog.getName() + "攻击力增加至" + luckyDog.getAttack());
            }
            if (EventRewardTypeEnum.COMMAND.getCode().equals(this.getType())) {
                luckyDog.setCommand(Integer.parseInt(luckyDog.getCommand()) + Integer.parseInt(this.getDataGive()) + "");
                System.out.println("武将" + luckyDog.getName() + "统帅增加至" + luckyDog.getCommand());
            }
            if (EventRewardTypeEnum.INTELLIGENCE.getCode().equals(this.getType())) {
                luckyDog.setIntelligence(Integer.parseInt(luckyDog.getIntelligence()) + Integer.parseInt(this.getDataGive()) + "");
                System.out.println("武将" + luckyDog.getName() + "智力增加至" + luckyDog.getIntelligence());
            }
        }
    }
}
