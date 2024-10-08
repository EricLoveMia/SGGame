package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.factory.OneOnOne.FightData;
import cn.eric.game.fujiatianxia6.po.*;
import cn.eric.game.fujiatianxia6.service.Fight;
import cn.eric.game.fujiatianxia6.service.Util;
import cn.eric.game.fujiatianxia6.test.Dom4JforXML;
import cn.eric.game.fujiatianxia6.util.ScannerInput;
import org.dom4j.DocumentException;

import java.util.*;

public class SkillFactory {

    private static List<Skill> skills;

    // 神技
    public static List<String> superSkills = Arrays.asList("2", "15", "38", "49", "50", "54");
    // 骑兵相关技能
    public static List<String> cavalrysSkills = Arrays.asList("21", "22", "44", "37", "42", "76", "75");
    // 枪兵相关技能
    public static List<String> infantrySkills = Arrays.asList("19", "20", "37", "43", "75", "77");
    // 弓兵相关技能
    public static List<String> archersSkills = Arrays.asList("18", "17", "51", "45", "46", "47", "43");
    // 通用相关技能
    public static List<String> allArmsSkills = Arrays.asList("5", "6", "12", "14", "16", "23", "24", "52", "25", "26", "27", "30", "34", "36", "39", "40", "48", "56", "8", "9", "10", "11", "70");

    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title: init
     * @Description: 初始化
     */
    public static void init() {
        try {
            skills = Dom4JforXML.createSkills();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static List<Skill> getSkills() {
        return skills;
    }

    //根据ID返回技能
    public static Skill getSkillByID(String id) {
        if ("".equals(id) || id == null) {
            return null;
        }
        for (Iterator iterator = skills.iterator(); iterator.hasNext(); ) {
            Skill skill = (Skill) iterator.next();
            if (skill.getId() == Integer.parseInt(id)) {
                return skill;
            }
        }
        return null;
    }

    /**
     * @param @param  type 1 单挑 2 野战 3 攻城 4
     * @param @param  AttOrDef 1 进攻方  2 防守方
     * @param @param  general 进攻方的英雄
     * @param @param  virgin 未改变的
     * @param @return 设定文件
     * @return Object    返回类型
     * @throws
     * @Title: change
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public static Object changeBefore(int type, int AttOrDef, General general, General generalB, Object virgin) {
        Object result = new Object();
        if (type == 1) {
            result = OneOnOne_Change(AttOrDef, general, virgin);
        }
        if (type == 2) {
            result = BattleField_Change(AttOrDef, general, virgin);
        }
        if (type == 3) {
            result = AttackCity_Change_Before(AttOrDef, virgin);
        }
        return result;
    }

    private static Object AttackCity_Change_Before(int attOrDef, Object virgin) {

        AttackCity attackCity = (AttackCity) virgin;

        if (skllZhuGe(attackCity.getAttackChief(), attackCity.getAttackVice(), attackCity.getAttackCounsellor())) { //神算 触发几率
            // 只有进攻方的技能触发
            System.out.println("神算技能触发，防守方无法释放技能");
            if (attackCity.getLeader() != null) {
                AttackCity_Change_Before(1, attackCity.getLeader(), attackCity);
            }
            if (attackCity.getAttackChief() != null) {
                AttackCity_Change_Before(1, attackCity.getAttackChief(), attackCity);
            }
            if (attackCity.getAttackCounsellor() != null) {
                AttackCity_Change_Before(1, attackCity.getAttackCounsellor(), attackCity);
            }
            if (attackCity.getAttackVice() != null) {
                AttackCity_Change_Before(1, attackCity.getAttackVice(), attackCity);
            }
        } else if (skllZhuGe(attackCity.getDefenceChief(), attackCity.getDefenceVice(), attackCity.getDefenceCounsellor())) { //神算 触发几率
            // 只有进攻方的技能触发
            System.out.println("神算技能触发，进攻方无法释放技能");
            General defenceLeader = GeneralFactory.getGeneralById(attackCity.getCity().getBelongTo() + "");
            if (defenceLeader != null) {
                AttackCity_Change_Before(2, defenceLeader, attackCity);
            }
            if (attackCity.getDefenceChief() != null) {
                AttackCity_Change_Before(2, attackCity.getDefenceChief(), attackCity);
            }
            if (attackCity.getDefenceCounsellor() != null) {
                AttackCity_Change_Before(2, attackCity.getDefenceCounsellor(), attackCity);
            }
            if (attackCity.getDefenceVice() != null) {
                AttackCity_Change_Before(2, attackCity.getDefenceVice(), attackCity);
            }
        } else {
            // 没有神算
            if (attackCity.getLeader() != null) {
                AttackCity_Change_Before(1, attackCity.getLeader(), attackCity);
            }
            General defenceLeader = GeneralFactory.getGeneralById(attackCity.getCity().getBelongTo() + "");
            if (defenceLeader != null) {
                AttackCity_Change_Before(2, defenceLeader, attackCity);
            }
            if (attackCity.getAttackChief() != null) {
                attackCity = AttackCity_Change_Before(1, attackCity.getAttackChief(), attackCity);
            }
            if (attackCity.getDefenceChief() != null) {
                attackCity = AttackCity_Change_Before(2, attackCity.getDefenceChief(), attackCity);
            }
            if (attackCity.getAttackCounsellor() != null) {
                attackCity = AttackCity_Change_Before(1, attackCity.getAttackCounsellor(), attackCity);
            }
            if (attackCity.getDefenceCounsellor() != null) {
                attackCity = AttackCity_Change_Before(2, attackCity.getDefenceCounsellor(), attackCity);
            }
            if (attackCity.getAttackVice() != null) {
                attackCity = AttackCity_Change_Before(1, attackCity.getAttackVice(), attackCity);
            }
            if (attackCity.getDefenceVice() != null) {
                attackCity = AttackCity_Change_Before(2, attackCity.getDefenceVice(), attackCity);
            }
        }

        return attackCity;
    }

    private static AttackCity AttackCity_Change_Before(int attOrDef, General general, AttackCity virgin) {
        String skillId = general.getSkill();
        Skill skill = SkillFactory.getSkillByID(skillId);
        if (skill == null) {
            return virgin;
        }
        int data = skill.getData();
        if (general.getWeapon() != null) {
            data = data + general.getWeapon().getData();
        }
        // 清流
        if ("71".equals(general.getSkill())) {
            System.out.println("武将：" + general.getName() + "触发技能：" + skill.getName() + "," + skill.getMemo());
            if (attOrDef == 1) {
                virgin.setAttackSkillProbability(virgin.getAttackSkillProbability() + data);
            } else {
                virgin.setDefenceSkillProbability(virgin.getDefenceSkillProbability() + data);
            }
        }
        if ("28".equals(general.getSkill())) {  // 辅佐
            System.out.println("武将：" + general.getName() + "触发技能：辅佐 提高上阵武将的技能发动几率");
            if (attOrDef == 1) {
                virgin.setAttackSkillProbability(virgin.getAttackSkillProbability() + data);
            } else {
                virgin.setDefenceSkillProbability(virgin.getDefenceSkillProbability() + data);
            }
        }

        if ("16".equals(skillId)) {  // 威风
            System.out.println("武将：" + general.getName() + "触发技能：威风 对方所有兵种等级降" + data + "级");
            if (attOrDef == 1) {
                virgin.setDefencelevelAddition(-data);
            } else {
                virgin.setAttactlevelAddition(-data);
            }
        }
        if ("39".equals(skillId)) {  // 昂扬
            System.out.println("武将：" + general.getName() + "触发技能：昂扬 所有兵种等级升" + data + "级");
            if (attOrDef == 1) {
                virgin.setAttactlevelAddition(data);
            } else {
                virgin.setDefencelevelAddition(data);
            }
        }
        if ("12".equals(general.getSkill()) && new Random().nextInt(100) <= data) {  // 深谋
            System.out.println("武将：" + general.getName() + "触发技能：深谋 野战或者攻城前，有几率直接对敌方造成伤害，伤害值与智力值有关，初始几率为60%，获得专属提升为100%（司马懿）");
            int lostNum = 0;
            if (attOrDef == 1) {
                lostNum = Math.min(1000, (int) (virgin.getAttackSoliderTotal() * (Float.parseFloat(general.getIntelligence())) / 1000));
                System.out.println("防守方损失兵力：" + lostNum);
                virgin.setDeffenceSoliderTotal(virgin.getDeffenceSoliderTotal() - lostNum);
            } else {
                lostNum = Math.min(1000,(int) (virgin.getDeffenceSoliderTotal() * (Float.parseFloat(general.getIntelligence())) / 1000));
                System.out.println("进攻方损失兵力：" + lostNum);
                virgin.setAttackSoliderTotal(virgin.getAttackSoliderTotal() - lostNum);
            }
        }


        if ("35".equals(general.getSkill())) {  // 倾城
            System.out.println("武将：" + general.getName() + "触发技能：辅佐 提高上阵武将的技能发动几率");
            if (attOrDef == 1) {
                virgin.setAttackSkillProbability(virgin.getAttackSkillProbability() + data);
            } else {
                virgin.setDefenceSkillProbability(virgin.getDefenceSkillProbability() + data);
            }
        }


        // 造谣 逃走兵力
        return virgin;
    }

    // 野战修改
    private static Object BattleField_Change(int attOrDef, General general, Object virgin) {
        // 技能不能为空
        Skill skill = SkillFactory.getSkillByID(general.getSkill());
        if (skill == null) {
            return virgin;
        }
        // 补充专属武器上的数值
        int data = skill.getData();
        if (general.getWeapon() != null && general.getWeapon().getData() != null) {
            data += general.getWeapon().getData();
        }
        BattleField BF = (BattleField) virgin;
        // 清流
        if ("71".equals(general.getSkill())) {
            System.out.println("武将：" + general.getName() + "触发技能：" + skill.getName() + "," + skill.getMemo());
            if (attOrDef == 1) {
                BF.setAttactSkillProbability(BF.getAttactSkillProbability() + data);
            } else {
                BF.setDefenceSkillProbability(BF.getDefenceSkillProbability() + data);
            }
        }
        if ("28".equals(general.getSkill())) {  // 辅佐
            System.out.println("武将：" + general.getName() + "触发技能：辅佐 提高上阵武将的技能发动几率");
            if (attOrDef == 1) {
                BF.setAttactSkillProbability(BF.getAttactSkillProbability() + data);
            } else {
                BF.setDefenceSkillProbability(BF.getDefenceSkillProbability() + data);
            }
        }
        if ("5".equals(general.getSkill())) {  // 待伏
            System.out.println("武将：" + general.getName() + "触发技能：待伏，发动伏兵，根据智力增加兵力");
            if (attOrDef == 1) {
                BF.setAttackAmyNum((int) (BF.getAttackAmyNum() * (1 + (float) Integer.parseInt(general.getIntelligence()) / 2000)));
            } else {
                BF.setDefenceAmyNum((int) (BF.getDefenceAmyNum() * (1 + (float) Integer.parseInt(general.getIntelligence()) / 2000)));
            }
        }
        if ("12".equals(general.getSkill()) && new Random().nextInt(100) <= data) {  // 深谋
            System.out.println("武将：" + general.getName() + "触发技能：深谋 野战或者攻城前，有几率直接对敌方造成伤害，伤害值与智力值有关，初始几率为60%，获得专属提升为100%（司马懿）");
            int lostNum = 0;
            if (attOrDef == 1) {
                lostNum = Math.min(1000, (int) (BF.getAttackAmyNum() * (Float.parseFloat(general.getIntelligence())) / 1000));
                System.out.println("防守方损失兵力：" + lostNum);
                BF.setDefenceAmyNum(BF.getDefenceAmyNum() - lostNum);
            } else {
                lostNum = Math.min(1000,(int) (BF.getDefenceAmyNum() * (Float.parseFloat(general.getIntelligence())) / 1000));
                System.out.println("进攻方损失兵力：" + lostNum);
                BF.setAttackAmyNum(BF.getAttackAmyNum() - lostNum);
            }
        }
        if ("16".equals(general.getSkill())) {  // 威风
            System.out.println("武将：" + general.getName() + "触发技能：威风 对方所有兵种等级降" + data + "级");
            if (attOrDef == 1) {
                BF.setDefencelevelAddition(BF.getDefencelevelAddition() - data);
            } else {
                BF.setAttactlevelAddition(BF.getAttactlevelAddition() - data);
            }
        }

        if ("35".equals(general.getSkill())) {  // 倾城
            System.out.println("武将：" + general.getName() + "触发技能：辅佐 提高上阵武将的技能发动几率");
            if (attOrDef == 1) {
                BF.setAttactSkillProbability(BF.getAttactSkillProbability() + data);
            } else {
                BF.setDefenceSkillProbability(BF.getDefenceSkillProbability() + data);
            }
        }
        if ("39".equals(general.getSkill())) {  // 昂扬
            System.out.println("武将：" + general.getName() + "触发技能：昂扬 所有兵种等级升" + data + "级");
            if (attOrDef == 1) {
                BF.setAttactlevelAddition(BF.getAttactlevelAddition() + data);
            } else {
                BF.setDefencelevelAddition(BF.getDefencelevelAddition() + data);
            }
        }
//        // 攻心
//        if ("23".equals(general.getSkill()) && new Random().nextInt(100) <= data) {
//            System.out.println("武将：" + general.getName() + skill.getMemo());
//            int lostNum;
//            if (attOrDef == 1) {
//                lostNum = (int) (BF.getAttackAmyNum() * ((Float.parseFloat(general.getCommand())) / 50 + (Float.parseFloat(general.getIntelligence())) / 50) / 100);
//                System.out.println("防守方有" + lostNum + "兵力进入进攻方部队");
//                BF.setDefenceAmyNum(BF.getDefenceAmyNum() - lostNum);
//                BF.setAttackAmyNum(BF.getAttackAmyNum() + lostNum);
//            } else {
//                lostNum = (int) (BF.getDefenceAmyNum() * (Float.parseFloat(general.getIntelligence())) / 1000);
//                System.out.println("进攻方有" + lostNum + "兵力进入防守方部队");
//                BF.setAttackAmyNum(BF.getAttackAmyNum() - lostNum);
//                BF.setDefenceAmyNum(BF.getDefenceAmyNum() + lostNum);
//            }
//        }
//        // 反间  王允 貂蝉
//        if ("24".equals(general.getSkill()) && new Random().nextInt(100) <= data) {
//            System.out.println("武将-" + general.getName() + "技能触发：" + skill.getMemo());
//            int lostNum;
//            if (attOrDef == 1) {
//                lostNum = (int) (BF.getDefenceAmyNum() * (Float.parseFloat(general.getIntelligence()) * 2) / 1000);
//                System.out.println("防守方互相攻击造成损失" + lostNum);
//                BF.setDefenceAmyNum(BF.getDefenceAmyNum() - lostNum);
//            } else {
//                lostNum = (int) (BF.getDefenceAmyNum() * (Float.parseFloat(general.getIntelligence()) * 2) / 1000);
//                System.out.println("进攻方互相攻击造成损失" + lostNum);
//                BF.setAttackAmyNum(BF.getAttackAmyNum() - lostNum);
//            }
//        }
        // 鬼谋
        if ("40".equals(general.getSkill()) && new Random().nextInt(100) <= data) {
            System.out.println("武将：" + general.getName() + "触发技能：鬼谋 野战攻城前，有50%几率直接造成敌方军团混乱，整体战斗力下降,伤害值与智力值有关，初始几率为50%，获得专属提升为100%（郭嘉）");
            int lostNum = 0;
            if (attOrDef == 1) {
                lostNum = (int) (BF.getDefenceAmyNum() * (Float.parseFloat(general.getIntelligence())) / 1000);
                System.out.println("防守方战斗力下降：" + lostNum);
                BF.setDefenceAmyNum(BF.getDefenceAmyNum() - lostNum);
            } else {
                lostNum = (int) (BF.getAttackAmyNum() * (Float.parseFloat(general.getIntelligence())) / 1000);
                System.out.println("进攻方战斗力下降：" + lostNum);
                BF.setAttackAmyNum(BF.getAttackAmyNum() - lostNum);
            }
        }
        // 奇谋
        if ("79".equals(general.getSkill()) && new Random().nextInt(100) <= data) {
            int lostNum = 0;
            float random = Util.getMaxFloatNum(10);
            if (attOrDef == 1 && intelligenceCompare(general, BF.getDefenceChief(), BF.getDefenceCounsellor(), BF.getDefenceVice())) {
                System.out.println("武将：" + general.getName() + "触发技能：" + skill.getName() + "," + skill.getMemo());
                lostNum = (int) (BF.getAttackAmyNum() * BF.getAttackFactor() * ((1.5 * (random * (Math.max(Integer.parseInt(BF.getAttackChief().getAttack())
                        , Integer.parseInt(Optional.ofNullable(Optional.ofNullable(BF.getAttackCounsellor()).orElse(new General()).getAttack()).orElse("0")))))) +
                        (1.5 * (random * (Math.max(Integer.parseInt(BF.getAttackChief().getCommand())
                                , Integer.parseInt(Optional.ofNullable(Optional.ofNullable(BF.getAttackCounsellor()).orElse(new General()).getCommand()).orElse("0"))))))) / 2000);
                System.out.println("造成一次无伤伤害" + lostNum);
                BF.setDefenceAmyNum(BF.getDefenceAmyNum() - lostNum);
            } else if (attOrDef == 2 && intelligenceCompare(general, BF.getAttackChief(), BF.getAttackCounsellor(), BF.getAttackVice())) {
                System.out.println("武将：" + general.getName() + "触发技能：" + skill.getName() + "," + skill.getMemo());
                lostNum = (int) (BF.getDefenceAmyNum() * BF.getDefenceFactor() * ((1.5 * (random * (Math.max(Integer.parseInt(BF.getDefenceChief().getAttack())
                        , Integer.parseInt(Optional.ofNullable(Optional.ofNullable(BF.getDefenceCounsellor()).orElse(new General()).getAttack()).orElse("0")))))) +
                        (1.5 * (random * (Math.max(Integer.parseInt(BF.getDefenceChief().getCommand())
                                , Integer.parseInt(Optional.ofNullable(Optional.ofNullable(BF.getDefenceCounsellor()).orElse(new General()).getCommand()).orElse("0"))))))) / 2000);
                System.out.println("造成一次无伤伤害" + lostNum);
                BF.setAttackAmyNum(BF.getAttackAmyNum() - lostNum);
            }
        }
        return virgin;
    }

    // 野战修改 野战进行中
    private static Object BattleField_ChangeMiddle(int attOrDef, General general, Object virgin) {
        // 增加辅佐
        BattleField BF = (BattleField) virgin;
        int data = SkillFactory.getSkillByID(general.getSkill()).getData();
        if (general.getWeapon() != null && general.getWeapon().getData() != null) {
            data += general.getWeapon().getData();
        }
        int attactSkillProbability = BF.getAttactSkillProbability();
        int defenceSkillProbability = BF.getDefenceSkillProbability();
        // 奸雄
        if ("2".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());
            // 如果是进攻方 进攻方损失降低到0
            if (attOrDef == 1 && new Random().nextInt(100) <= (data + attactSkillProbability)) {
                if (IntelligenceCompare(general, BF.getDefenceChief(), BF.getDefenceCounsellor(), BF.getDefenceVice())) {
                    System.out.println("武将：" + general.getName() + "触发技能：" + skill.getName() + "," + skill.getMemo());
                    BF.setAttLost(0);
                }
            }
            if (attOrDef == 2 && new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                if (IntelligenceCompare(general, BF.getAttackChief(), BF.getAttackCounsellor(), BF.getAttackVice())) {
                    System.out.println("武将：" + general.getName() + "触发技能：" + skill.getName() + "," + skill.getMemo());
                    BF.setDefLost(0);
                }
            }
        }

        // 大吼
        if ("6".equals(general.getSkill())) {
            System.out.println("武将：" + general.getName() + "触发技能：大吼，野战，城战时，额外增加对方的损失，根据武力值决定，获得专属武器，会额外提升损失的兵力");
            // 增加最大30%的损失 专属后 最大50% 有几率直接吓退敌军 10% 20%
            int addLost = 0;
            if (attOrDef == 1) {
                addLost = (int) (BF.getDefLost() * (Float.parseFloat(general.getAttack())) / 1000) * data / 10;
                BF.setDefLost(BF.getDefLost() + addLost);
                if (new Random().nextInt(100) <= (data - 20)) {
                    System.out.println("吓退敌军");
                    BF.setResult(true);
                }
            } else {
                addLost = (int) (BF.getAttLost() * (Float.parseFloat(general.getAttack())) / 1000) * data / 10;
                BF.setAttLost(BF.getAttLost() + addLost);
                if (new Random().nextInt(100) <= (data - 20)) {
                    System.out.println("吓退敌军");
                    BF.setResult(false);
                }
            }
            System.out.println("增加伤亡：" + addLost);
        }

        // 火神
        if ("15".equals(general.getSkill())) {
            // 增加最大100%的损失
            int addLost = 0;
            if (attOrDef == 1 && new Random().nextInt(100) <= (data + attactSkillProbability)) {
                System.out.println("武将：" + general.getName() + "触发技能：火神 野战或者攻城中，有机率施展火攻术，造成极大伤害，威力值与统帅、武力值有关，初始几率为10%，获得专属提升为20%");
                addLost = (int) (BF.getDefLost() * ((Float.parseFloat(general.getCommand())) / 200 + (Float.parseFloat(general.getAttack())) / 200));
                BF.setDefLost(BF.getDefLost() + addLost);
            }
            if (attOrDef == 2 && new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                System.out.println("武将：" + general.getName() + "触发技能：火神 野战或者攻城中，有机率施展火攻术，造成极大伤害，威力值与统帅、武力值有关，初始几率为10%，获得专属提升为20%");
                addLost = (int) (BF.getAttLost() * ((Float.parseFloat(general.getCommand())) / 200 + (Float.parseFloat(general.getAttack())) / 200));
                BF.setAttLost(BF.getAttLost() + addLost);
            }
            System.out.println("增加伤亡：" + addLost);
        }
        // 1 普通 2 骑兵 3 枪兵 4 弓兵
        // 弓将 野战中 弓兵有几率释放会心一击，增加30%的伤害，并减少伤害10%
        if ("17".equals(general.getSkill())) {
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1 && BF.getAttackType() == 4) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getAttLost() * 0.1) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * 0.9));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + attactSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 弓兵会心一击");
                    addLost = (int) (BF.getDefLost() * 0.3);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getDefLost() + ")");
                    BF.setDefLost(BF.getDefLost() + addLost);
                }
            }
            if (attOrDef == 2 && BF.getDefenceType() == 4) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getDefLost() * 0.1) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * 0.9));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 弓兵会心一击");
                    addLost = (int) (BF.getAttLost() * 0.3);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getAttLost() + ")");
                    BF.setAttLost(BF.getAttLost() + addLost);
                }
            }
        }
        // 弓神 野战中 弓兵有几率释放会心一击，增加50%的伤害，并减少伤害20%
        if ("18".equals(general.getSkill())) {
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1 && BF.getAttackType() == 4) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getAttLost() * 0.2) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + attactSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 弓兵会心一击");
                    addLost = (int) (BF.getDefLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getDefLost() + ")");
                    BF.setDefLost(BF.getDefLost() + addLost);
                }
            }
            if (attOrDef == 2 && BF.getDefenceType() == 4) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getDefLost() * 0.2) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 弓兵会心一击");
                    addLost = (int) (BF.getAttLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getAttLost() + ")");
                    BF.setAttLost(BF.getAttLost() + addLost);
                }
            }
        }

        // 枪将 野战中 枪兵有几率释放会心一击，增加30%的伤害，并减少伤害10%
        if ("19".equals(general.getSkill())) {
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1 && BF.getAttackType() == 3) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getAttLost() * 0.1) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * 0.9));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + attactSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 枪兵会心一击");
                    addLost = (int) (BF.getDefLost() * 0.3);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getDefLost() + ")");
                    BF.setDefLost(BF.getDefLost() + addLost);
                }
            }
            if (attOrDef == 2 && BF.getDefenceType() == 3) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getDefLost() * 0.1) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * 0.9));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 枪兵会心一击");
                    addLost = (int) (BF.getAttLost() * 0.3);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getAttLost() + ")");
                    BF.setAttLost(BF.getAttLost() + addLost);
                }
            }
        }
        // 枪神 野战中 枪兵有几率释放会心一击，增加50%的伤害，并减少伤害20%
        if ("20".equals(general.getSkill())) {
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1 && BF.getAttackType() == 3) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getAttLost() * 0.2) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + attactSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 枪兵会心一击");
                    addLost = (int) (BF.getDefLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getDefLost() + ")");
                    BF.setDefLost(BF.getDefLost() + addLost);
                }
            }
            if (attOrDef == 2 && BF.getDefenceType() == 3) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getDefLost() * 0.2) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 枪兵会心一击");
                    addLost = (int) (BF.getAttLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getAttLost() + ")");
                    BF.setAttLost(BF.getAttLost() + addLost);
                }
            }
        }
        // 骑将 野战中 骑兵有几率释放会心一击，增加30%的伤害，并减少伤害10%
        if ("21".equals(general.getSkill())) {
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1 && BF.getAttackType() == 2) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getAttLost() * 0.1) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * 0.9));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + attactSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 骑兵会心一击");
                    addLost = (int) (BF.getDefLost() * 0.3);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getDefLost() + ")");
                    BF.setDefLost(BF.getDefLost() + addLost);
                }
            }
            if (attOrDef == 2 && BF.getDefenceType() == 2) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getDefLost() * 0.1) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * 0.9));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 骑兵会心一击");
                    addLost = (int) (BF.getAttLost() * 0.3);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getAttLost() + ")");
                    BF.setAttLost(BF.getAttLost() + addLost);
                }
            }
        }

        // 骑神 野战中 骑兵有几率释放会心一击，增加50%的伤害，并减少伤害20%
        if ("22".equals(general.getSkill())) {
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1 && BF.getAttackType() == 2) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getAttLost() * 0.2) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + attactSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 骑兵会心一击");
                    addLost = (int) (BF.getDefLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getDefLost() + ")");
                    BF.setDefLost(BF.getDefLost() + addLost);
                }
            }
            if (attOrDef == 2 && BF.getDefenceType() == 2) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getDefLost() * 0.2) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 骑兵会心一击");
                    addLost = (int) (BF.getAttLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getAttLost() + ")");
                    BF.setAttLost(BF.getAttLost() + addLost);
                }
            }
        }

        // 攻心
        if ("23".equals(general.getSkill())) {
            int lostNum;
            if (attOrDef == 1 && new Random().nextInt(100) <= (data + attactSkillProbability)) {
                System.out.println("武将：" + general.getName() + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                lostNum = (int) (BF.getAttackAmyNum() * ((Float.parseFloat(general.getAttack())) / 70 + (Float.parseFloat(general.getCommand())) / 30 + (Float.parseFloat(general.getIntelligence())) / 30) / 100);
                System.out.println("防守方有" + lostNum + "兵力进入进攻方部队");
                BF.setDefLost(BF.getDefLost() + lostNum);
                // BF.setDefenceAmyNum(BF.getDefenceAmyNum() - lostNum);
                BF.setAttackAmyNum(BF.getAttackAmyNum() + lostNum);
            } else if (attOrDef == 2 && new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                System.out.println("武将：" + general.getName() + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                lostNum = (int) (BF.getDefenceAmyNum() * ((Float.parseFloat(general.getAttack())) / 70 + (Float.parseFloat(general.getCommand())) / 30 + (Float.parseFloat(general.getIntelligence())) / 30) / 100);
                System.out.println("进攻方有" + lostNum + "兵力进入防守方部队");
                BF.setAttLost(BF.getAttLost() + lostNum);
                // BF.setAttackAmyNum(BF.getAttackAmyNum() - lostNum);
                BF.setDefenceAmyNum(BF.getDefenceAmyNum() + lostNum);
            }
        }
        // 反间  王允 貂蝉
        if ("24".equals(general.getSkill())) {
            int lostNum;
            if (attOrDef == 1 && new Random().nextInt(100) <= (data + attactSkillProbability)) {
                System.out.println("武将-" + general.getName() + "技能触发：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                lostNum = (int) (BF.getDefenceAmyNum() * (Float.parseFloat(general.getIntelligence()) + Float.parseFloat(general.getCharm())) / 1500);
                System.out.println("防守方互相攻击造成损失" + lostNum);
                BF.setDefLost(BF.getDefLost() + lostNum);
                // BF.setDefenceAmyNum(BF.getDefenceAmyNum() - lostNum);
            } else if (attOrDef == 2 && new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                System.out.println("武将-" + general.getName() + "技能触发：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                lostNum = (int) (BF.getAttackAmyNum() * (Float.parseFloat(general.getIntelligence()) + Float.parseFloat(general.getCharm())) / 1500);
                System.out.println("进攻方互相攻击造成损失" + lostNum);
                BF.setAttLost(BF.getAttLost() + lostNum);
                // BF.setAttackAmyNum(BF.getAttackAmyNum() - lostNum);
            }
        }

        // 38 霸王
        if ("38".equals(general.getSkill())) {
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getAttLost() * 0.2) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + attactSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 会心一击");
                    addLost = (int) (BF.getDefLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getDefLost() + ")");
                    BF.setDefLost(BF.getDefLost() + addLost);
                }
            }
            if (attOrDef == 2) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getDefLost() * 0.2) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 会心一击");
                    addLost = (int) (BF.getAttLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getAttLost() + ")");
                    BF.setAttLost(BF.getAttLost() + addLost);
                }
            }
        }

        // 37 勇将
        if ("37".equals(general.getSkill())) {
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1 && (BF.getDefenceType() == 2 || BF.getDefenceType() == 3)) {
                System.out.println("武将：" + general.getName() + "触发技能：" + Objects.requireNonNull(SkillFactory.getSkillByID(general.getSkill())).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getAttLost() * 0.2) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + attactSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 会心一击");
                    addLost = (int) (BF.getDefLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getDefLost() + ")");
                    BF.setDefLost(BF.getDefLost() + addLost);
                }
            }
            if (attOrDef == 2 && (BF.getDefenceType() == 2 || BF.getDefenceType() == 3)) {
                System.out.println("武将：" + general.getName() + "触发技能：" + Objects.requireNonNull(SkillFactory.getSkillByID(general.getSkill())).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getDefLost() * 0.2) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 会心一击");
                    addLost = (int) (BF.getAttLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getAttLost() + ")");
                    BF.setAttLost(BF.getAttLost() + addLost);
                }
            }
        }

        // 白马
        if ("44".equals(general.getSkill())) {
            // 增加最大30%的损失
            int addLost = 0;
            if (attOrDef == 1 && BF.getAttackType() == 2) {
                if (new Random().nextInt(100) <= (data + attactSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能：白马，每回合有50%的几率发动骑射，专属后100%几率,伤害和统帅武力有关");
                    addLost = (int) ((int) ((Float.parseFloat(general.getAttack()) * 1.3 + Float.parseFloat(general.getCommand()) * 1.1 )) * 0.35);
                    BF.setDefLost(BF.getDefLost() + addLost);
                    System.out.println("增加伤亡：" + addLost);
                }
            } else if (attOrDef == 2 && BF.getDefenceType() == 2) {
                if (new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能：白马，每回合有50%的几率发动骑射，专属后100%几率,伤害和统帅武力有关");
                    addLost = (int) ((int) ((Float.parseFloat(general.getAttack()) * 1.3 + Float.parseFloat(general.getCommand()) * 1.1 )) * 0.35);
                    BF.setAttLost(BF.getAttLost() + addLost);
                    System.out.println("增加伤亡：" + addLost);
                }
            }
        }

        // 地形相关
        // 水战 45 操舵 46 水将 47 水神  地形  1 平原 2 山地 3 水道
        if (("45".equals(general.getSkill())
                || "46".equals(general.getSkill())
                || "47".equals(general.getSkill()))
                && BF.getCity().getTopography() == 3) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());
            System.out.println("武将：" + general.getName() + "触发技能：" + skill.getName() + " : " + skill.getMemo());
            if (attOrDef == 1) {
                BF.setAttLost((int) (BF.getAttLost() * (1 - (double) data / 100)));
                BF.setDefLost((int) (BF.getDefLost() * (1 + (double) data / 100)));
            } else {
                BF.setDefLost((int) (BF.getDefLost() * (1 - (double) data / 100)));
                BF.setAttLost((int) (BF.getAttLost() * (1 + (double) data / 100)));
            }
        }

        // 毒神
        if ("49".equals(general.getSkill())) {
            // 增加的损失数
            int addLost = 0;
            float rate = (Float.parseFloat(general.getCommand()) + Float.parseFloat(general.getIntelligence())) / 350;
            if (attOrDef == 1 && new Random().nextInt(100) <= (data + attactSkillProbability)) {
                System.out.println("武将：" + general.getName() + "触发技能毒神：" + Objects.requireNonNull(SkillFactory.getSkillByID(general.getSkill())).getMemo());

                addLost = (int) (BF.getDefLost() * rate);
                System.out.println("增加伤亡：" + addLost + "(" + BF.getDefLost() + ")");
                BF.setDefLost(BF.getDefLost() + addLost);
            }
            if (attOrDef == 2 && new Random().nextInt(100) <= (data + attactSkillProbability)) {
                System.out.println("武将：" + general.getName() + "触发技能毒神：" + Objects.requireNonNull(SkillFactory.getSkillByID(general.getSkill())).getMemo());
                addLost = (int) (BF.getAttLost() * rate);
                System.out.println("增加伤亡：" + addLost + "(" + BF.getAttLost() + ")");
                BF.setAttLost(BF.getAttLost() + addLost);
            }
        }

        // 龙胆
        if ("50".equals(general.getSkill())) {
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1) {
                System.out.println("武将：" + general.getName() + "触发技能龙胆：" + Objects.requireNonNull(SkillFactory.getSkillByID(general.getSkill())).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getAttLost() * 0.2) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + attactSkillProbability) || BF.getAttackAmyNum() <= BF.getDefenceAmyNum()) {
                    System.out.println("武将：" + general.getName() + "触发技能 会心一击");
                    addLost = (int) (BF.getDefLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getDefLost() + ")");
                    BF.setDefLost(BF.getDefLost() + addLost);
                }
            }
            if (attOrDef == 2) {
                System.out.println("武将：" + general.getName() + "触发技能龙胆：" + Objects.requireNonNull(SkillFactory.getSkillByID(general.getSkill())).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getDefLost() * 0.2) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= (data + defenceSkillProbability) || BF.getAttackAmyNum() >= BF.getDefenceAmyNum()) {
                    System.out.println("武将：" + general.getName() + "触发技能 会心一击");
                    addLost = (int) (BF.getAttLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getAttLost() + ")");
                    BF.setAttLost(BF.getAttLost() + addLost);
                }
            }
        }
        // 射程
        if ("51".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());

            double percent = (double) data / 100;
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1 && BF.getAttackType() == 4) {
                System.out.println("武将：" + general.getName() + "触发技能" + skill.getName() + " ：" + skill.getMemo());
                System.out.println("减少伤亡" + (int) (BF.getAttLost() * percent) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * (1 - percent)));
            }
            if (attOrDef == 2 && BF.getDefenceType() == 4) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getDefLost() * percent) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * (1 - percent)));
            }
        }
        if ("52".equals(general.getSkill())) {
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1) {
                System.out.println("武将：" + general.getName() + "触发技能沈着：" + Objects.requireNonNull(SkillFactory.getSkillByID(general.getSkill())).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getAttLost() * 0.1) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * 0.9));
                // 几率收拢残兵
                if (new Random().nextInt(100) <= (data + attactSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 收拢残兵");
                    double count = Double.parseDouble(general.getCommand())
                            + Double.parseDouble(general.getIntelligence()) + Double.parseDouble(general.getCharm());
                    double percent = count / 1000;
                    addLost = (int) (BF.getAttLost() * percent);
                    System.out.println("收拢残兵：" + addLost + "(" + BF.getAttLost() + ")");
                    BF.setAttLost(BF.getAttLost() - addLost);
                }
            }
            if (attOrDef == 2) {
                System.out.println("武将：" + general.getName() + "触发技能沈着：" + Objects.requireNonNull(SkillFactory.getSkillByID(general.getSkill())).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getDefLost() * 0.1) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * 0.9));
                // 几率收拢残兵
                if (new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                    System.out.println("武将：" + general.getName() + "触发技能 收拢残兵");
                    double count =
                            Double.parseDouble(general.getCommand())
                                    + Double.parseDouble(general.getIntelligence())
                                    + Double.parseDouble(general.getCharm());
                    double percent = count / 1000;
                    addLost = (int) (BF.getDefLost() * percent);
                    System.out.println("收拢残兵：" + addLost + "(" + BF.getDefLost() + ")");
                    BF.setDefLost(BF.getDefLost() - addLost);
                }
            }
        }
        // 披荆
        if ("54".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());

            double percent = (double) data / 100;
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1) {
                System.out.println("武将：" + general.getName() + "触发技能" + skill.getName() + " ：" + skill.getMemo());
                System.out.println("增加伤害" + (int) (BF.getDefLost() * percent) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * (1 + percent)));
            }
            if (attOrDef == 2) {
                System.out.println("武将：" + general.getName() + "触发技能：" + skill.getName() + " ：" + skill.getMemo());
                System.out.println("增加伤害" + (int) (BF.getAttLost() * percent) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * (1 + percent)));
            }
        }
        // 虎将
        if ("53".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());
            double percent = (double) data / 100;
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1) {
                System.out.println("武将：" + general.getName() + "触发技能" + skill.getName() + " ：" + skill.getMemo());
                System.out.println("增加伤害" + (int) (BF.getDefLost() * percent) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * (1 + percent)));
                // 几率溃散
                int amyNum = BF.getDefenceAmyNum();
                if (new Random().nextInt(100) <= (data + attactSkillProbability)) {
                    System.out.println("造成溃散" + amyNum * 0.2);
                    BF.setDefLost((int) (BF.getDefLost() + amyNum * 0.2));
                }
            }
//            if (attOrDef == 2) {
//                System.out.println("武将：" + general.getName() + "触发技能：" + skill.getName() + " ：" + skill.getMemo());
//                System.out.println("增加伤害" + (int) (BF.getAttLost() * percent) + "(" + BF.getAttLost() + ")");
//                BF.setAttLost((int) (BF.getAttLost() * (1 + percent)));
//            }
        }
        // 不屈
        if ("56".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());

            double percent = data * 1.0 / 100 + (Integer.parseInt(general.getCommand())
                    + Integer.parseInt(general.getIntelligence()) + Integer.parseInt(general.getAttack())) * 1.0 / 3000;
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1 && BF.getAttackAmyNum() < BF.getDefenceAmyNum()) {
                System.out.println("武将：" + general.getName() + "触发技能" + skill.getName() + " ：" + skill.getMemo());
                System.out.println("减少伤亡" + (int) (BF.getAttLost() * percent) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * (1 - percent)));
            }
            if (attOrDef == 2 && BF.getAttackAmyNum() > BF.getDefenceAmyNum()) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getDefLost() * percent) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * (1 - percent)));
            }
        }
        // 荆棘
        if ("60".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());

            double percent = data * 1.0 / 100 + (Double.parseDouble(general.getCommand())
                    + Double.parseDouble(general.getIntelligence()) + Double.parseDouble(general.getAttack())) / 1500;
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1) {
                System.out.println("武将：" + general.getName() + "触发技能" + skill.getName() + " ：" + skill.getMemo());
                System.out.println("增加反弹伤害" + (int) (BF.getAttLost() * percent));
                BF.setDefLost((int) (BF.getDefLost() + BF.getAttLost() * percent));
            }
            if (attOrDef == 2) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("增加反弹伤害" + (int) (BF.getDefLost() * percent));
                BF.setAttLost((int) (BF.getAttLost() + BF.getDefLost() * percent));
            }
        }
        // 无畏
        if ("65".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());

            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1) {
                System.out.println("武将：" + general.getName() + "触发技能" + skill.getName() + " ：" + skill.getMemo());
                int num = (BF.getDefLost() / 100) * (100) + 100;
                System.out.println("伤害提升到" + num + "[" + BF.getDefLost() + "]");
                BF.setDefLost(num);
            }
            if (attOrDef == 2) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                int num = (BF.getAttLost() / 100) * (100) + 100;
                System.out.println("伤害提升到" + num + "[" + BF.getAttLost() + "]");
                BF.setAttLost(num);
            }
        }
        // 毒言
        if ("74".equals(general.getSkill())) {
            // 增加的损失数
            int addLost = 0;
            if (attOrDef == 1 && new Random().nextInt(100) <= (data + attactSkillProbability)) {
                System.out.println("武将：" + general.getName() + "触发技能毒言：" + Objects.requireNonNull(SkillFactory.getSkillByID(general.getSkill())).getMemo());
                addLost = (int) (BF.getDefLost() * 0.3);
                System.out.println("增加伤亡：" + addLost + "(" + BF.getDefLost() + ")");
                BF.setDefLost(BF.getDefLost() + addLost);
            }
            if (attOrDef == 2 && new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                System.out.println("武将：" + general.getName() + "触发技能毒言：" + Objects.requireNonNull(SkillFactory.getSkillByID(general.getSkill())).getMemo());
                addLost = (int) (BF.getAttLost() * 0.3);
                System.out.println("增加伤亡：" + addLost + "(" + BF.getAttLost() + ")");
                BF.setAttLost(BF.getAttLost() + addLost);
            }
        }
        // 勇者
        if ("75".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());
            // 增加的损失数
            if (attOrDef == 1 && (BF.getAttackType() == 2 || BF.getAttackType() == 3) && new Random().nextInt(100) <= (data + attactSkillProbability)) {
                System.out.println("武将：" + general.getName() + "触发技能" + skill.getName() + " ：" + skill.getMemo());
                int add = Integer.parseInt(general.getCommand()) + Integer.parseInt(general.getAttack()) / 10;
                System.out.println("增加伤害" + add + "(" + BF.getDefLost() + ")");
                BF.setDefLost(BF.getDefLost() + add);
            }
            // 增加的损失数
            if (attOrDef == 2 && (BF.getAttackType() == 2 || BF.getAttackType() == 3) && new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                System.out.println("武将：" + general.getName() + "触发技能" + skill.getName() + " ：" + skill.getMemo());
                int add = Integer.parseInt(general.getCommand()) + Integer.parseInt(general.getAttack()) / 10;
                System.out.println("增加伤害" + add + "(" + BF.getAttLost() + ")");
                BF.setAttLost(BF.getAttLost() + add);
            }
        }
        // 突袭
        if ("76".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());
            // 增加的损失数
            if (attOrDef == 1 && attackCompare(general, BF.getDefenceChief(), BF.getDefenceCounsellor(), BF.getDefenceVice())) {
                System.out.println("武将：" + general.getName() + "触发技能" + skill.getName() + " ：" + skill.getMemo());
                int add = (Integer.parseInt(general.getCommand()) + Integer.parseInt(general.getAttack())) / 8;
                System.out.println("增加伤害" + add + "(" + BF.getDefLost() + ")");
                BF.setDefLost(BF.getDefLost() + add);
                // 几率溃散
                int amyNum = BF.getDefenceAmyNum();
                if (new Random().nextInt(100) <= (data + attactSkillProbability)) {
                    float intelligence = Float.parseFloat(general.getIntelligence());
                    float percents = intelligence * intelligence / 50000;
                    System.out.println("造成溃散" + amyNum * percents);
                    BF.setDefLost((int) (BF.getDefLost() + amyNum * percents));
                }
            }
        }
        // 乱战 枪兵
        if ("77".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());
            if (attOrDef == 1 && BF.getAttackType() == 3 && new Random().nextInt(100) <= (data + attactSkillProbability)) {
                // 如果是进攻方 进攻方损失降低到0
                if (attackCompare(general, BF.getDefenceChief(), BF.getDefenceCounsellor(), BF.getDefenceVice())) {
                    System.out.println("武将：" + general.getName() + "触发技能：" + skill.getName() + "," + skill.getMemo());
                    BF.setAttLost(0);
                }
            }
            if (attOrDef == 2 && BF.getAttackType() == 3 && new Random().nextInt(100) <= (data + defenceSkillProbability)) {
                if (attackCompare(general, BF.getAttackChief(), BF.getAttackCounsellor(), BF.getAttackVice())) {
                    System.out.println("武将：" + general.getName() + "触发技能：" + skill.getName() + "," + skill.getMemo());
                    BF.setDefLost(0);
                }
            }
        }
        // 奇袭
        if ("78".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());
            // 增加的损失数
            if (attOrDef == 1 && BF.getAttackType() == 1  && attackCompare(general, BF.getDefenceChief(), BF.getDefenceCounsellor(), BF.getDefenceVice())) {
                System.out.println("武将：" + general.getName() + "触发技能" + skill.getName() + " ：" + skill.getMemo());
                int add = Integer.parseInt(general.getCommand()) + Integer.parseInt(general.getAttack()) / 3;
                System.out.println("增加伤害" + add + "(" + BF.getDefLost() + ")");
                BF.setDefLost(BF.getDefLost() + add);
                // 几率溃散
                int amyNum = BF.getDefenceAmyNum();
                if (new Random().nextInt(100) <= (data + attactSkillProbability)) {
                    float intelligence = Float.parseFloat(general.getIntelligence());
                    float command = Float.parseFloat(general.getCommand());
                    float percents = intelligence * command / 50000;
                    System.out.println("造成溃散" + amyNum * percents);
                    BF.setDefLost((int) (BF.getDefLost() + amyNum * percents));
                }
            }
        }
        // 铁骑
        if ("82".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());
            // 增加的损失数
            if (attOrDef == 1 && BF.getAttackType() == 1) {
                System.out.println("武将：" + general.getName() + "主公技能触发技能" + skill.getName() + " ：" + skill.getMemo());
                int add = (int) (BF.getDefLost() *0.3);
                System.out.println("增加防守方伤害" + add + "(" + BF.getDefLost() + ")");
                BF.setDefLost(BF.getDefLost() + add);
            }
            else if (attOrDef == 2 && BF.getAttackType() == 1){
                System.out.println("武将：" + general.getName() + "主公技能触发技能" + skill.getName() + " ：" + skill.getMemo());
                int add = (int) (BF.getAttLost() * 0.3);
                System.out.println("增加进攻方伤害" + add + "点 (" + BF.getDefLost() + ")");
                BF.setAttLost(BF.getAttLost() + add);
            }
        }
        return BF;
    }

    private static boolean IntelligenceCompare(General general, General chief, General counsellor, General vice) {
        if (Integer.parseInt(general.getIntelligence()) < Integer.parseInt(chief.getIntelligence())) {
            return false;
        }
        if (counsellor != null && Integer.parseInt(general.getIntelligence()) < Integer.parseInt(counsellor.getIntelligence())) {
            return false;
        }
        return vice == null || Integer.parseInt(general.getIntelligence()) >= Integer.parseInt(vice.getIntelligence());
    }

    private static boolean attackCompare(General general, General chief, General counsellor, General vice) {
        if (Integer.parseInt(general.getAttack()) < Integer.parseInt(chief.getAttack())) {
            return false;
        }
        if (counsellor != null && Integer.parseInt(general.getAttack()) < Integer.parseInt(counsellor.getAttack())) {
            return false;
        }
        return vice == null || Integer.parseInt(general.getAttack()) >= Integer.parseInt(vice.getAttack());
    }

    private static boolean commandCompare(General general, General chief, General counsellor, General vice) {
        if (Integer.parseInt(general.getCommand()) < Integer.parseInt(chief.getCommand())) {
            return false;
        }
        if (counsellor != null && Integer.parseInt(general.getCommand()) < Integer.parseInt(counsellor.getCommand())) {
            return false;
        }
        return vice == null || Integer.parseInt(general.getCommand()) >= Integer.parseInt(vice.getCommand());
    }

    private static boolean charmCompare(General general, General chief, General counsellor, General vice) {
        if (Integer.parseInt(general.getCharm()) < Integer.parseInt(chief.getCharm())) {
            return false;
        }
        if (counsellor != null && Integer.parseInt(general.getCharm()) < Integer.parseInt(counsellor.getCharm())) {
            return false;
        }
        return vice == null || Integer.parseInt(general.getCharm()) >= Integer.parseInt(vice.getCharm());
    }

    private static boolean intelligenceCompare(General general, General chief, General counsellor, General vice) {
        if (Integer.parseInt(general.getIntelligence()) < Integer.parseInt(chief.getIntelligence())) {
            return false;
        }
        if (counsellor != null && Integer.parseInt(general.getIntelligence()) < Integer.parseInt(counsellor.getIntelligence())) {
            return false;
        }
        return vice == null || Integer.parseInt(general.getIntelligence()) >= Integer.parseInt(vice.getIntelligence());
    }


    // 单挑修改
    private static Object OneOnOne_Change(int attOrDef, General general, Object virgin) {
        //
        int data = SkillFactory.getSkillByID(general.getSkill()).getData();
        if (general.getWeapon() != null && general.getWeapon().getData() != null) {
            data += general.getWeapon().getData();
        }
        OneOnOne o = (OneOnOne) virgin;
        if ("4".equals(general.getSkill())) { //单挑时对方防御力减少20%
            System.out.println("武将：" + general.getName() + "触发技能：武圣，对方防御力减少百分之" + data);
            if (attOrDef == 1) {
                o.setDefenceB(o.getDefenceB() * (100 - data) / 100);
            } else {
                o.setDefenceA(o.getDefenceA() * (100 - data) / 100);
            }
        }
        return o;
    }

    /**
     * @param @param  type 1 单挑 2 野战 3 攻城 4
     * @param @param  AttOrDef 1 进攻方  2 防守方 3 所有一起算
     * @param @param  generalA 进攻方的英雄
     * @param @param  generalD 防御方的英雄
     * @param @param  virgin 未改变的
     * @param @return 设定文件
     * @return Object    返回类型
     * @throws
     * @Title: changeMiddle
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public static void changeMiddle(int type, int AttOrDef, General generalA, General generalD, Object virgin) {
        if (type == 1) {
            FightData sigleFight = (FightData) virgin;
            OneOnOne_ChangeMiddle(AttOrDef, generalA, generalD, sigleFight);
        }
        if (type == 2) {
            // 如果是野战要注意把6个武将的技能都照顾到  如果有神算触发，则另一方的技能不能触发
            BattleField BF = (BattleField) virgin;
            if (skllZhuGe(BF.getAttackChief(), BF.getAttackVice(), BF.getAttackCounsellor())) { //神算 触发几率
                // 只有进攻方的技能触发
                System.out.println("神算技能触发，防守方无法释放技能");
                battleField_Middle_Attack(BF);
            } else if (skllZhuGe(BF.getDefenceChief(), BF.getDefenceVice(), BF.getDefenceCounsellor())) { //神算 触发几率
                // 只有进攻方的技能触发
                System.out.println("神算技能触发，进攻方无法释放技能");
                battleField_Middle_Defence(BF);
            } else {
                // 没有神算  找辅佐等技能
                battleField_Middle_Attack(BF);
                battleField_Middle_Defence(BF);
            }
        }
        if (type == 3) {
            // 如果是攻城要注意把6个武将的技能都照顾到  如果有神算触发，则另一方的技能不能触发
            AttackCity ac = (AttackCity) virgin;

            if (skllZhuGe(ac.getAttackChief(), ac.getAttackVice(), ac.getAttackCounsellor())) { //神算 触发几率
                // 只有进攻方的技能触发
                System.out.println("神算技能触发，防守方无法释放技能");
                attackCity_Middle_Attack(ac);
            } else if (skllZhuGe(ac.getDefenceChief(), ac.getDefenceVice(), ac.getDefenceCounsellor())) { //神算 触发几率
                // 只有进攻方的技能触发
                System.out.println("神算技能触发，进攻方无法释放技能");
                attackCity_Middle_Defence(ac);
            } else {
                // 没有神算  找辅佐等技能
                attackCity_Middle_Attack(ac);
                attackCity_Middle_Defence(ac);
            }
        }
    }

    private static void attackCity_Middle_Defence(AttackCity ac) {
        if (ac.getDefenceChief() != null) {
            AttackCity_Change_Middle(2, ac.getDefenceChief(), ac);
        }
        if (ac.getDefenceVice() != null) {
            AttackCity_Change_Middle(2, ac.getDefenceVice(), ac);
        }
        if (ac.getDefenceCounsellor() != null) {
            AttackCity_Change_Middle(2, ac.getDefenceCounsellor(), ac);
        }
    }

    private static void AttackCity_Change_Middle(int attOrDef, General general, AttackCity ac) {
        // 增加辅佐
        Skill skill = SkillFactory.getSkillByID(general.getSkill());
        int data = skill.getData();
        if (general.getWeapon() != null && general.getWeapon().getData() != null) {
            data += general.getWeapon().getData();
        }
        int attactSkillProbability = ac.getAttackSkillProbability();
        int defenceSkillProbability = ac.getDefenceSkillProbability();
        // 攻城
        if ("67".equals(general.getSkill())) {
            // 如果是进攻方 增加防守方的损失
            if (attOrDef == 1) {
                double percent = data * 1.0 / 100 + (Double.parseDouble(general.getAttack()) +
                        Double.parseDouble(general.getCommand()) + Double.parseDouble(general.getIntelligence())) / 1500;
                int defenceLost = ac.getDefenceLost();
                int add = (int) (defenceLost * percent);
                System.out.println("武将" + general.getName() + "技能：" + skill.getName() + "触发" + skill.getMemo());
                System.out.println("增加伤害" + add);
                ac.setDefenceLost(defenceLost + add);
            }
        }

    }

    private static void attackCity_Middle_Attack(AttackCity ac) {
        if (ac.getAttackChief() != null) {
            AttackCity_Change_Middle(1, ac.getAttackChief(), ac);
        }
        if (ac.getAttackVice() != null) {
            AttackCity_Change_Middle(1, ac.getAttackVice(), ac);
        }
        if (ac.getAttackCounsellor() != null) {
            AttackCity_Change_Middle(1, ac.getAttackCounsellor(), ac);
        }
    }

    private static boolean skllZhuGe(General attackChief, General attackVice, General attackCounsellor) {
        return (attackChief != null && "13".equals(attackChief.getSkill())
                && (new Random().nextInt(101) <= SkillFactory.getSkillByID(attackChief.getSkill()).getData()))
                || (attackVice != null && "13".equals(attackVice.getSkill())
                && (new Random().nextInt(101) <= SkillFactory.getSkillByID(attackVice.getSkill()).getData()))
                || (attackCounsellor != null && "13".equals(attackCounsellor.getSkill())
                && (new Random().nextInt(101) <= SkillFactory.getSkillByID(attackCounsellor.getSkill()).getData()));
    }

    private static void battleField_Middle_Defence(BattleField BF) {
        General defenceLord = GeneralFactory.getGeneralById(BF.getDefenceChief().getBelongTo());
        // 曹操的奸雄不能参与防守
        if(defenceLord != null && !Objects.equals(defenceLord.getId(), "2")) {
            BattleField_ChangeMiddle(1, defenceLord, BF);
        }

        if (BF.getDefenceChief() != null) {
            BattleField_ChangeMiddle(2, BF.getDefenceChief(), BF);
        }
        if (BF.getDefenceVice() != null) {
            BattleField_ChangeMiddle(2, BF.getDefenceVice(), BF);
        }
        if (BF.getDefenceCounsellor() != null) {
            BattleField_ChangeMiddle(2, BF.getDefenceCounsellor(), BF);
        }

    }

    private static void battleField_Middle_Attack(BattleField BF) {
        if (BF.getAttackChief() != null) {
            BattleField_ChangeMiddle(1, BF.getAttackChief(), BF);
        }
        if (BF.getAttackVice() != null) {
            BattleField_ChangeMiddle(1, BF.getAttackVice(), BF);
        }
        if (BF.getAttackCounsellor() != null) {
            BattleField_ChangeMiddle(1, BF.getAttackCounsellor(), BF);
        }
        General attackLord = GeneralFactory.getGeneralById(BF.getAttackChief().getBelongTo());
        if(attackLord != null) {
            BattleField_ChangeMiddle(1, attackLord, BF);
        }
    }

    // 单挑中修改
    private static void OneOnOne_ChangeMiddle(int attOrDef, General generalA, General generalD, FightData sigleFight) {
        int dataA = Objects.requireNonNull(SkillFactory.getSkillByID(generalA.getSkill())).getData();
        String skillName = Objects.requireNonNull(SkillFactory.getSkillByID(generalA.getSkill())).getName();
        String skillNameD = Objects.requireNonNull(SkillFactory.getSkillByID(generalD.getSkill())).getName();
        if (generalA.getWeapon() != null && generalA.getWeapon().getData() != null) {
            dataA += generalA.getWeapon().getData();
        }
        int dataD = Objects.requireNonNull(SkillFactory.getSkillByID(generalD.getSkill())).getData();
        if (generalD.getWeapon() != null && generalD.getWeapon().getData() != null) {
            dataD += generalD.getWeapon().getData();
        }
        // 鬼将
        if ("7".equals(generalA.getSkill()) && sigleFight.attackType == 1) {//鬼将 单挑时，普通攻击造成1.2倍的伤害 1表示普通攻击
            System.out.println("武将" + generalA.getName() + "技能：" + skillName + "触发,造成" + dataA + "%的伤害");
            sigleFight.defenceLostHealth = sigleFight.defenceLostHealth * dataA / 100;
        }
        if ("7".equals(generalD.getSkill()) && sigleFight.defenceType == 1) {//鬼将 单挑时，普通攻击造成1.2倍的伤害 1表示普通攻击
            System.out.println("武将" + generalD.getName() + "技能：" + skillNameD + "触发,造成" + "%的伤害");
            sigleFight.attackLostHealth = sigleFight.attackLostHealth * dataD / 100;
        }

        // 猛将
        if (sigleFight.defenceLostHealth > 0 && "11".equals(generalA.getSkill()) && new Random().nextInt(100) <= dataA) {//猛将 单挑时，普通攻击造成1.2倍的伤害 1表示普通攻击
            System.out.println("武将" + generalA.getName() + "技能：" + skillName + "触发,额外造成" + dataA + "%的伤害");
            sigleFight.defenceLostHealth = sigleFight.defenceLostHealth * (dataA + 100) / 100;
        }
        if (sigleFight.attackLostHealth > 0 && "11".equals(generalD.getSkill()) && new Random().nextInt(100) <= dataD) {//猛将 单挑时，普通攻击造成1.2倍的伤害 1表示普通攻击
            System.out.println("武将" + generalD.getName() + "技能：" + skillNameD + "触发,额外造成" + dataD + "%的伤害");
            sigleFight.attackLostHealth = sigleFight.attackLostHealth * (dataD + 100) / 100;
        }

    }

    /**
     * @param @param  type 1 单挑 2 野战 3 攻城  4 俘虏后 5 单挑结束后  6 野战结束后 7 攻城结束后 9 回合结束后
     * @param @param  AttOrDef 1 进攻方  2 防守方 3 所有一起算
     * @param @param  generalA 进攻方的英雄
     * @param @param  generalD 防御方的英雄
     * @param @param  virgin 未改变的
     * @param @return 设定文件
     * @return Object    返回类型
     * @throws
     * @Title: changeAfter
     * @Description:
     */
    public static void changeAfter(int type, int AttOrDef, General generalA, General generalD, Object virgin) {
        if (type == 1) {
            OneOnOne fight = (OneOnOne) virgin;
            OneOnOne_ChangeAfter(AttOrDef, generalA, generalD, fight);
        }

        if (type == 4) { // 俘虏后，判定技能
            beCatched(generalA, generalD);
        }
        // 野战
        if (type == 2) {
            battleField_After_Attack((BattleField) virgin);
            battleField_After_Defence((BattleField) virgin);
        }
        // 攻城
        if (type == 3) {
            attackCity_After_Attack((AttackCity) virgin);
            attackCity_After_Defence((AttackCity) virgin);
        }
        // 单挑结束后
        if (type == 5) {
            Fight.PostwarCalculation pc = (Fight.PostwarCalculation) virgin;
            OneOnOne_ChangeAfterComplete(generalA, generalD, pc);
        }

        // 单挑结束后
        if (type == 7) {
            attackCity_end_attcak((AttackCity) virgin);
            attackCity_end_defence((AttackCity) virgin);
        }

        // 回合结束后
        if (type == 9) {
            rountAfter(generalA);
        }

    }

    /**
     * 攻城回合结束后触发技能 防守
     */
    private static void attackCity_After_Defence(AttackCity virgin) {

        if (virgin.getDefenceChief() != null) {
            attackCity_After_Defence(virgin.getDefenceChief(), virgin);
        }
        if (virgin.getDefenceCounsellor() != null) {
            attackCity_After_Defence(virgin.getDefenceCounsellor(), virgin);
        }
        if (virgin.getDefenceVice() != null) {
            attackCity_After_Defence(virgin.getDefenceVice(), virgin);
        }

    }

    private static void attackCity_end_attcak(AttackCity virgin) {

        if (virgin.getDefenceChief() != null) {
            attackCity_end_check(virgin.getDefenceChief(), virgin);
        }
        if (virgin.getDefenceCounsellor() != null) {
            attackCity_end_check(virgin.getDefenceCounsellor(), virgin);
        }
        if (virgin.getDefenceVice() != null) {
            attackCity_end_check(virgin.getDefenceVice(), virgin);
        }

    }

    private static void attackCity_After_Defence(General general, AttackCity virgin) {
        Skill skill = SkillFactory.getSkillByID(general.getSkill());
        int dataA = skill.getData();
        if (general.getWeapon() != null && general.getWeapon().getData() != null) {
            dataA += general.getWeapon().getData();
        }
        // 坚守
        if ("14".equals(general.getSkill())) {
            int addLost;
            System.out.println("武将：" + general.getName() + "触发技能：坚守 野战或者攻城防守中，极大的减少本队损失的人数，减少的额度与统帅值有关");
            addLost = (int) (virgin.getDefenceLost() * (Float.parseFloat(general.getCommand()) + dataA * 10) / 1000);
            System.out.println("减少损失" + addLost + "(" + virgin.getDefenceLost() + ")");
            virgin.setDefenceLost(virgin.getDefenceLost() - addLost);
        }

        // 铁壁
        if ("30".equals(general.getSkill())) {
            int defLost = virgin.getDefenceLost();
            int data = dataA + (Integer.parseInt(general.getCommand()) + Integer.parseInt(general.getAttack()) + Integer.parseInt(general.getCharm())) / 10;
            defLost = defLost * (1 - data / 100);
            System.out.println(general.getName() + "技能" + skill.getName() + "触发,损失兵力减少" + data + "%(" + defLost + ")");
            virgin.setDefenceLost(defLost);
        }

        // 医疗
        if ("26".equals(general.getSkill()) || "27".equals(general.getSkill())) {
            int defLost = virgin.getDefenceLost();
            int extra;
            int intelligence = Integer.parseInt(general.getIntelligence());
            if (intelligence < 50) {
                extra = 0;
            } else if (intelligence < 70) {
                extra = 5;
            } else if (intelligence < 90) {
                extra = 8;
            } else if (intelligence < 100) {
                extra = 10;
            } else {
                extra = 15;
            }
            int data = defLost * (dataA + extra) / 100;
            System.out.println(general.getName() + "技能" + skill.getName() + "触发,救治兵力" + data + "(" + defLost + ")");
            virgin.setDefenceLost(defLost - data);
        }
    }

    private static void attackCity_After_Attack(AttackCity virgin) {
        if (virgin.getAttackChief() != null) {
            attackCity_After_Attack(virgin.getAttackChief(), virgin);
        }
        if (virgin.getAttackCounsellor() != null) {
            attackCity_After_Attack(virgin.getAttackCounsellor(), virgin);
        }
        if (virgin.getAttackVice() != null) {
            attackCity_After_Attack(virgin.getAttackVice(), virgin);
        }


    }

    private static void attackCity_end_defence(AttackCity virgin) {
        if (virgin.getAttackChief() != null) {
            attackCity_end_check(virgin.getAttackChief(), virgin);
        }
        if (virgin.getAttackCounsellor() != null) {
            attackCity_end_check(virgin.getAttackCounsellor(), virgin);
        }
        if (virgin.getAttackVice() != null) {
            attackCity_end_check(virgin.getAttackVice(), virgin);
        }
    }

    private static void attackCity_end_check(General general, AttackCity virgin) {
        Skill skill = SkillFactory.getSkillByID(general.getSkill());
        int data = skill.getData();
        if (general.getWeapon() != null && general.getWeapon().getData() != null) {
            data += general.getWeapon().getData();
        }
        // 抓捕
        if ("66".equals(general.getSkill())) {
            System.out.println("武将" + general.getName() + "技能" + skill.getName() + "触发，" + skill.getMemo());
            virgin.setCatchPercent(virgin.getCatchPercent() + data + virgin.getAttackSkillProbability());
        }
        // 血路
        if ("41".equals(general.getSkill())) {
            System.out.println("武将" + general.getName() + "技能" + skill.getName() + "触发，" + skill.getMemo());
            virgin.setCatchPercent(virgin.getCatchPercent() + data + virgin.getAttackSkillProbability());
        }

    }

    private static void attackCity_After_Attack(General general, AttackCity virgin) {
        Skill skill = SkillFactory.getSkillByID(general.getSkill());
        int dataA = skill.getData();
        if (general.getWeapon() != null && general.getWeapon().getData() != null) {
            dataA += general.getWeapon().getData();
        }
        // 医疗
        if ("26".equals(general.getSkill()) || "27".equals(general.getSkill())) {
            int attackLost = virgin.getAttackLost();
            Skill skillByID = SkillFactory.getSkillByID(general.getSkill());
            int extra;
            int intelligence = Integer.parseInt(general.getIntelligence());
            if (intelligence < 50) {
                extra = 0;
            } else if (intelligence < 70) {
                extra = 5;
            } else if (intelligence < 90) {
                extra = 8;
            } else if (intelligence < 100) {
                extra = 10;
            } else {
                extra = 15;
            }

            int data = attackLost * (dataA + extra) / 100;
            System.out.println(general.getName() + "技能" + skillByID.getName() + "触发,救治兵力" + data + "(" + attackLost + ")");
            virgin.setAttackLost(attackLost - data);
        }
        //

    }

    /**
     * 单挑结束后触发技能
     */
    private static void OneOnOne_ChangeAfterComplete(General generalA, General generalD, Fight.PostwarCalculation pc) {
        // 如果有抓捕 则catchPercent = 100

    }

    /**
     * 回合结束后触发
     */
    private static void rountAfter(General general) {
        // 刘备仁德
        int data = Objects.requireNonNull(SkillFactory.getSkillByID(general.getSkill())).getData();
        if (general.getWeapon() != null && general.getWeapon().getData() != null) {
            data += general.getWeapon().getData();
        }
        if ("1".equals(general.getSkill()) && new Random().nextInt(100) <= data) {
            System.out.println("仁德触发，随机获得一个武将");
            List<General> generals = GeneralFactory.allFreeGenerals();
            if (generals == null || generals.size() == 0) {
                System.out.println("已经没有在野武将了");
                return;
            }
            int index = new Random().nextInt(generals.size());

            for (General initGeneral : GeneralFactory.getInitGenerals()) {
                if (initGeneral.getId().equals(generals.get(index).getId())) {
                    System.out.println("武将" + initGeneral.getName() + "加入" + general.getName() + "的阵营");
                    general.getGenerals().add(initGeneral);
                    initGeneral.setBelongTo(general.getId());
                    break;
                }
            }
        }
    }

    /**
     * 野战回合结束后，防守方 技能触发
     */
    private static void battleField_After_Defence(BattleField virgin) {
        General defenceChief = virgin.getDefenceChief();
        General defenceCounsellor = virgin.getDefenceCounsellor();
        General defenceVice = virgin.getDefenceVice();
        if (defenceChief != null) {
            battleField_After_Defence(defenceChief, virgin);
        }
        if (defenceCounsellor != null) {
            battleField_After_Defence(defenceCounsellor, virgin);
        }
        if (defenceVice != null) {
            battleField_After_Defence(defenceVice, virgin);
        }

    }

    /**
     * 野战回合结束后，防守方 技能触发
     */
    private static void battleField_After_Defence(General general, BattleField virgin) {
        Skill skillByID = SkillFactory.getSkillByID(general.getSkill());
        assert skillByID != null;
        int skill = skillByID.getData();
        if (general.getWeapon() != null && general.getWeapon().getData() != null) {
            skill += general.getWeapon().getData();
        }

        // 金刚
        if ("25".equals(general.getSkill())) {
            int defLost = virgin.getDefLost();
            int data = -skill - (Integer.parseInt(general.getCommand()) + Integer.parseInt(general.getIntelligence())) / 2;
            if (defLost > data) {
                System.out.println(general.getName() + "技能" + skillByID.getName() + "触发,损失兵力减少至" + data + "(" + defLost + ")");
                virgin.setDefLost(data);
            }
        }
        // 医疗
        if ("26".equals(general.getSkill()) || "27".equals(general.getSkill())) {
            int defLost = virgin.getDefLost();
            int extra;
            int intelligence = Integer.parseInt(general.getIntelligence());
            if (intelligence < 50) {
                extra = 0;
            } else if (intelligence < 70) {
                extra = 5;
            } else if (intelligence < 90) {
                extra = 8;
            } else if (intelligence < 100) {
                extra = 10;
            } else {
                extra = 15;
            }
            int data = defLost * (skill + extra) / 100;
            System.out.println(general.getName() + "技能" + skillByID.getName() + "触发,救治兵力" + data + "(" + defLost + ")");
            virgin.setDefLost(defLost - data);
        }
        // 坚守
        if ("14".equals(general.getSkill())) {
            int addLost;
            System.out.println("武将：" + general.getName() + "触发技能：坚守 野战或者攻城防守中，极大的减少本队损失的人数，减少的额度与统帅值有关，最大20%");
            addLost = (int) (virgin.getDefLost() * (Float.parseFloat(general.getCommand()) + skill * 10) / 1000);
            System.out.println("减少损失" + addLost + "(" + virgin.getDefLost() + ")");
            virgin.setDefLost(virgin.getDefLost() - addLost);
        }

//		// 铁壁
//		if("30".equals(general.getSkill())){
//			int defLost = virgin.getDefLost();
//			Skill skillByID = SkillFactory.getSkillByID(general.getSkill());
//			int data = skillByID.getData() + (Integer.parseInt(general.getCommand() + general.getAttack() + general.getCharm()))/20;
//			defLost = defLost * ( 1 - data/100);
//			System.out.println(general.getName() + "技能" + skillByID.getName() + "触发,损失兵力减少" + data + "%("+defLost+")");
//			virgin.setDefLost(defLost);
//		}
    }

    /**
     * 野战回合结束后，进攻方 技能触发
     */
    private static void battleField_After_Attack(BattleField virgin) {

        General attackChief = virgin.getAttackChief();
        General attackCounsellor = virgin.getAttackCounsellor();
        General attackVice = virgin.getAttackVice();

        if (attackChief != null) {
            battleField_After_Attack(attackChief, virgin);
        }
        if (attackCounsellor != null) {
            battleField_After_Attack(attackCounsellor, virgin);
        }
        if (attackVice != null) {
            battleField_After_Attack(attackVice, virgin);
        }
    }

    private static void battleField_After_Attack(General general, BattleField virgin) {
        Skill skillByID = SkillFactory.getSkillByID(general.getSkill());
        assert skillByID != null;
        int skill = skillByID.getData();
        if (general.getWeapon() != null && general.getWeapon().getData() != null) {
            skill += general.getWeapon().getData();
        }
        // 金刚
        if ("25".equals(general.getSkill())) {
            int attLost = virgin.getAttLost();
            int data = -skill - (Integer.parseInt(general.getCommand()) + Integer.parseInt(general.getIntelligence())) / 2;
            if (attLost > data) {
                System.out.println(general.getName() + "技能" + skillByID.getName() + "触发,损失兵力减少至" + data + "(" + attLost + ")");
                virgin.setAttLost(data);
            }
        }
        // 医疗
        if ("26".equals(general.getSkill()) || "27".equals(general.getSkill())) {
            int attLost = virgin.getAttLost();
            int extra;
            int intelligence = Integer.parseInt(general.getIntelligence());
            if (intelligence < 50) {
                extra = 0;
            } else if (intelligence < 70) {
                extra = 5;
            } else if (intelligence < 90) {
                extra = 8;
            } else if (intelligence < 100) {
                extra = 10;
            } else {
                extra = 15;
            }
            int data = attLost * (skill + extra) / 100;
            System.out.println(general.getName() + "技能" + skillByID.getName() + "触发,救治兵力" + data + "(" + attLost + ")");
            virgin.setAttLost(attLost - data);
        }


    }

    private static void beCatched(General lord, General lost) {
        if ("1".equals(lord.getSkill())) {  //仁德
            System.out.println("武将" + lord.getName() + "技能：" + SkillFactory.getSkillByID(lord.getSkill()).getName() + "触发,武将直接加入阵营");
            lost.setBelongTo(lord.getId());
            lost.setStatus("0");
            System.out.println(lost.getName() + "拜入" + lord.getName() + "帐下");
        }
    }

    // 单挑每回合结束时触发
    private static void OneOnOne_ChangeAfter(int attOrDef, General generalA, General generalD, OneOnOne fight) {
        int dataA = SkillFactory.getSkillByID(generalA.getSkill()).getData();
        Weapon weaponA = generalA.getWeapon();
        if (weaponA != null) {
            dataA = dataA + weaponA.getData();
        }

        int dataD = SkillFactory.getSkillByID(generalD.getSkill()).getData();
        Weapon weaponD = generalD.getWeapon();
        if (weaponD != null) {
            dataD = dataD + weaponD.getData();
        }

        // 裸衣
        if ("9".equals(generalA.getSkill())) {//裸衣
            int addAtt = (100 - fight.getVitalityA()) * dataA / 100;
            if (addAtt > 4) {
                addAtt = 4;
            }
            System.out.println("武将" + generalA.getName() + "技能：" + SkillFactory.getSkillByID(generalA.getSkill()).getName()
                    + "触发,增加" + addAtt + "攻击力,减少防御力" + addAtt / 2);
            fight.setAttackA(fight.getAttackA() + addAtt);
            fight.setDefenceA(fight.getDefenceA() - addAtt / 2);
        }
        if ("9".equals(generalD.getSkill())) {//鬼将 单挑时，普通攻击造成1.2倍的伤害 1表示普通攻击
            int addAtt = (100 - fight.getVitalityB()) * dataD / 100;
            System.out.println("武将" + generalD.getName() + "技能：" + SkillFactory.getSkillByID(generalD.getSkill()).getName()
                    + "触发,增加" + addAtt + "攻击力,减少防御力" + addAtt / 2);
            fight.setAttackB(fight.getAttackB() + addAtt);
            fight.setDefenceB(fight.getDefenceB() - addAtt / 2);
        }

        // 恶来
        if ("10".equals(generalA.getSkill())) {//恶来
            int addDef = (100 - fight.getVitalityA()) * 15 / 100;
            // 最大加防御
            addDef = Math.min(addDef, dataA);
            System.out.println("武将" + generalA.getName() + "技能：" + SkillFactory.getSkillByID(generalA.getSkill()).getName() + "触发,增加" + addDef + "防御力");
            fight.setDefenceA(fight.getDefenceA() + addDef);
        }
        if ("10".equals(generalD.getSkill())) {//恶来
            int addDef = (100 - fight.getVitalityB()) * 15 / 100;
            // 最大加3点防御
            addDef = Math.min(addDef, dataD);
            System.out.println("武将" + generalD.getName() + "技能：" + SkillFactory.getSkillByID(generalD.getSkill()).getName() + "触发,增加" + addDef + "防御力");
            fight.setDefenceB(fight.getDefenceB() + addDef);
        }

    }

    /**
     * @param data             初始值
     * @param denfenceGenerals
     * @param type             1 金钱 2 弓枪兵 3 骑兵
     * @return void
     * @throws
     * @author Eric
     * @date 18:20 2019/7/12
     **/
    public static int CheckCitySkill(int data, List<General> denfenceGenerals, int type) {
        General general = null;
        for (Iterator iterator = denfenceGenerals.iterator(); iterator.hasNext(); ) {
            general = (General) iterator.next();
            data = citySkillChange(data, general, type);
        }
        if (general != null) {
            General leader = GeneralFactory.getGeneralById(general.getBelongTo());
            data = citySkillChange(data, leader, type);
        }
        return data;
    }

    // 单一的城市技能触发
    private static int citySkillChange(int data, General general, int type) {
        if (general == null) {
            return 0;
        }
        Weapon weapon = general.getWeapon();
        if (weapon != null) {
            data = data + weapon.getData();
        }
        // 和城市金钱有关
        if (type == 1) {
            return citySkillMoneyChange(data, general);
        }
        if (type == 2) {
            return citySkillNotHorseChange(data, general);
        }
        if (type == 3) {
            return citySkillHorseChange(data, general);
        }
        return data;
    }

    private static int citySkillHorseChange(int data, General general) {
        // TODO 要加成智力魅力政治
        int sum = Integer.parseInt(general.getIntelligence()) + Integer.parseInt(general.getPolitics());
        float percent = (float) sum / 350;
        // 繁殖
        if ("42".equals(general.getSkill())) {
            System.out.println(general.getName() + "技能繁殖触发，骑兵数量增加" + percent*100 + "%");
            data = (int) (data + data * percent);
        }
        // 清廉
        if ("34".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());
            if (skill != null) {
                System.out.println(general.getName() + "技能能吏触发，" + skill.getMemo());
                data = (int) (data * (double) (100 + skill.getData()) / 100);
            }
        }
        // 勤政
        if ("70".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());
            System.out.println(general.getName() + "技能勤政触发，" + skill.getMemo());
            data = (int) (data * (double) (100 + skill.getData()) / 100);
        }
        // 铁骑
        if ("82".equals(general.getSkill())) {
            System.out.println(general.getName() + "技能铁骑触发，骑兵数量增加" + 30 + "%");
            data = (int) (data + data * 0.3);
        }


        return data;
    }

    private static int citySkillNotHorseChange(int data, General general) {
        // 能吏
        if ("43".equals(general.getSkill())) {
            System.out.println(general.getName() + "技能能吏触发，弓兵、枪兵数量增加50%");
            data = (int) (data + data * 0.5);
        }
        // 清廉
        if ("34".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());
            if (skill != null) {
                System.out.println(general.getName() + "技能能吏触发，" + skill.getMemo());
                data = (int) (data * (double) (100 + skill.getData()) / 100);
            }
        }
        // 勤政
        if ("70".equals(general.getSkill())) {
            Skill skill = SkillFactory.getSkillByID(general.getSkill());
            System.out.println(general.getName() + "技能勤政触发，" + skill.getMemo());
            data = (int) (data * (double) (100 + skill.getData()) / 100);
        }
        return data;
    }

    //
    private static int citySkillMoneyChange(int data, General general) {
        if (general == null) {
            return data;
        }
        Skill skill = SkillFactory.getSkillByID(general.getSkill());
        if (skill == null) {
            return data;
        }
        // 富豪
        if ("8".equals(general.getSkill())) {
            System.out.println(general.getName() + "技能富豪触发," + skill.getMemo());
            data = (int) (data * (double) (100 + skill.getData()) / 100);
        }
        // 清廉
        if ("34".equals(general.getSkill())) {
            System.out.println(general.getName() + "技能清廉触发," + skill.getMemo());
            data = (int) (data * (double) (100 + skill.getData()) / 100);
        }
        // 屯田
        if ("48".equals(general.getSkill())) {
            City city = CityFactory.getCityById(general.getCityId());
            int count = (int) (city.getSoilders() + (city.getCavalrys() + city.getInfantry() + city.getArchers()) * 1.5);
            int addMoney = (int) (count * (double) skill.getData() / 100);
            System.out.println(general.getName() + "技能屯田触发，增加发展金" + addMoney);
            data = data + addMoney;
        }
        // 统业
        if ("3".equals(general.getSkill())) {
            System.out.println(general.getName() + "技能统业触发," + skill.getMemo());
            data = (int) (data * (double) (100 + skill.getData()) / 100);
        }
        // 清流
        if ("71".equals(general.getSkill())) {
            System.out.println(general.getName() + "技能清流触发，" + skill.getMemo());
            data = (int) (data * (double) (100 + skill.getData()) / 100);
        }
        // 勤政
        if ("70".equals(general.getSkill())) {
            System.out.println(general.getName() + "技能勤政触发，" + skill.getMemo());
            data = (int) (data * (double) (100 + skill.getData()) / 100);
        }
        // 管理
        if ("62".equals(general.getSkill())) {
            System.out.println(general.getName() + "技能" + skill.getName() + "触发，" + skill.getMemo());
            data = (int) (data * (double) (100 + skill.getData()) / 100);
        }
        return data;
    }

    public static int checkSkillForAddSoilders(int addSoilders, City city) {
        List<General> denfenceGenerals = city.getDenfenceGenerals();

        for (General denfenceGeneral : denfenceGenerals) {
            // 名声
            if ("36".equals(denfenceGeneral.getSkill())) {
                addSoilders = (int) (addSoilders + Integer.parseInt(denfenceGeneral.getCharm()) * 0.7);
                System.out.println(denfenceGeneral.getName() + "技能名声触发，增加剑士,总数" + addSoilders);
            }
        }
        return addSoilders;
    }

    public static void checkSkillViaCity(City defence, General player) {
        // 统业
        if ("3".equals(player.getSkill())) {
            System.out.println(player.getName() + "技能统业触发，增加发展金" + 1000);
            defence.setMoney(defence.getMoney() + 1000);
        }
    }

    public static int saleGoodsCheck(int salePrice, City defence) {
        List<General> generals = defence.getDenfenceGenerals();
        for (General general : generals) {
            salePrice = saleGoodsCheckGeneral(salePrice, general);
        }
        return salePrice;
    }

    private static int saleGoodsCheckGeneral(int salePrice, General player) {
        Skill skill = SkillFactory.getSkillByID(player.getSkill());
        int data = skill.getData();
        // 经商
        if ("63".equals(player.getSkill())) {
            System.out.println(player.getName() + "技能" + skill.getName() + "触发，" + skill.getMemo());
            salePrice = (int) (salePrice * (1 + data * 1.0 / 100));
        }

        return salePrice;
    }

    public static int buyGoodsCheck(int price, City defence) {
        List<General> generals = defence.getDenfenceGenerals();
        for (General general : generals) {
            price = buyGoodsCheckGeneral(price, general);
        }
        return price;
    }

    private static int buyGoodsCheckGeneral(int price, General player) {
        Skill skill = SkillFactory.getSkillByID(player.getSkill());
        int data = skill.getData();
        // 经商
        if ("63".equals(player.getSkill())) {
            System.out.println(player.getName() + "技能" + skill.getName() + "触发，" + skill.getMemo());
            price = (int) (price * (1 - data * 1.0 / 100));
        }

        return price;
    }

    public static void resetSkill(List<String> skillIds, General general, General player, Integer costMoney) {
        System.out.println("请选择要重修的技能 0 是否取消");
        for (int i = 0; i < skillIds.size(); i++) {
            System.out.println(i+1 + ":" + getSkillByID(skillIds.get(i)).getName() + "：" + getSkillByID(skillIds.get(i)).getMemo());
        }
        int choose = ScannerInput.inputInt(1, skillIds.size(), 0);
        if(choose != 0) {
            String chooseSkillId = skillIds.get(choose - 1);
            general.setSkill(chooseSkillId);
            System.out.println("武将" + general.getName() + "重修技能为" + getSkillByID(chooseSkillId).getName());
            player.setMoney(player.getMoney() - costMoney);
        }

    }
}
