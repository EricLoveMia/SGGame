package cn.eric.game.fujiatianxia6.service.event.reward;

import cn.eric.game.fujiatianxia6.po.General;

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
        super.setName(name);
        super.setDataGive(dataGive);
        super.setDataPunish(dataPunish);
        super.setType(type);
    }

    @Override
    public void reward(General general, Boolean success) {
        // 选择一个武将增加 属性
        System.out.println("选择一个武将增加" + this.getDataGive() + "点" + EventRewardTypeEnum.getText(this.getType()) + " 0 放弃");
        List<General> generals = general.getGenerals();
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
                    General generalAddAttack = generals.get(choise - 1);
                    if (EventRewardTypeEnum.ATTACK.getCode().equals(this.getType())) {
                        generalAddAttack.setAttack(Integer.parseInt(generalAddAttack.getAttack()) + Integer.parseInt(this.getDataGive()) + "");
                        System.out.println("武将" + generalAddAttack.getName() + "攻击力增加至" + generalAddAttack.getAttack());
                    }
                    if (EventRewardTypeEnum.COMMAND.getCode().equals(this.getType())) {
                        generalAddAttack.setCommand(Integer.parseInt(generalAddAttack.getCommand()) + Integer.parseInt(this.getDataGive()) + "");
                        System.out.println("武将" + generalAddAttack.getName() + "统帅增加至" + generalAddAttack.getCommand());
                    }
                    if (EventRewardTypeEnum.INTELLIGENCE.getCode().equals(this.getType())) {
                        generalAddAttack.setIntelligence(Integer.parseInt(generalAddAttack.getIntelligence()) + Integer.parseInt(this.getDataGive()) + "");
                        System.out.println("武将" + generalAddAttack.getName() + "智力增加至" + generalAddAttack.getIntelligence());
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
