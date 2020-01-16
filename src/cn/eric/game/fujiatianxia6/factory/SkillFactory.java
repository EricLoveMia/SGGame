package cn.eric.game.fujiatianxia6.factory;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import cn.eric.game.fujiatianxia6.po.AttackCity;
import cn.eric.game.fujiatianxia6.po.BattleField;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.po.Skill;
import cn.eric.game.fujiatianxia6.service.Fight;
import org.dom4j.DocumentException;

import cn.eric.game.fujiatianxia6.factory.OneOnOne.FightData;
import cn.eric.game.fujiatianxia6.test.Dom4JforXML;

public class SkillFactory {

    private static List<Skill> skills;

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
        if ("".equals(id) && id == null) {
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

        if ((attackCity.getAttackChief() != null && "13".equals(attackCity.getAttackChief().getSkill()) && (new Random().nextInt(101) <= SkillFactory.getSkillByID(attackCity.getAttackChief().getSkill()).getData())) || (attackCity.getAttackVice() != null && "13".equals(attackCity.getAttackVice().getSkill()) && (new Random().nextInt(101) <= SkillFactory.getSkillByID(attackCity.getAttackVice().getSkill()).getData())) || (attackCity.getAttackCounsellor() != null && "13".equals(attackCity.getAttackCounsellor().getSkill()) && (new Random().nextInt(101) <= SkillFactory.getSkillByID(attackCity.getAttackCounsellor().getSkill()).getData()))) { //神算 触发几率
            // 只有进攻方的技能触发
            System.out.println("神算技能触发，防守方无法释放技能");
            if (attackCity.getAttackChief() != null) {
                attackCity = AttackCity_Change_Before(1, attackCity.getAttackChief(), attackCity);
            }
            if (attackCity.getAttackCounsellor() != null) {
                attackCity = AttackCity_Change_Before(1, attackCity.getAttackCounsellor(), attackCity);
            }
            if (attackCity.getAttackVice() != null) {
                attackCity = AttackCity_Change_Before(1, attackCity.getAttackVice(), attackCity);
            }
        } else if ((attackCity.getDefenceChief() != null && "13".equals(attackCity.getDefenceChief().getSkill()) && (new Random().nextInt(101) <= SkillFactory.getSkillByID(attackCity.getDefenceChief().getSkill()).getData())) || (attackCity.getDefenceVice() != null && "13".equals(attackCity.getDefenceVice().getSkill()) && (new Random().nextInt(101) <= SkillFactory.getSkillByID(attackCity.getDefenceVice().getSkill()).getData())) || (attackCity.getDefenceCounsellor() != null && "13".equals(attackCity.getDefenceCounsellor().getSkill()) && (new Random().nextInt(101) <= SkillFactory.getSkillByID(attackCity.getDefenceCounsellor().getSkill()).getData()))) { //神算 触发几率
            // 只有进攻方的技能触发
            System.out.println("神算技能触发，进攻方无法释放技能");
            if (attackCity.getDefenceChief() != null) {
                attackCity = AttackCity_Change_Before(2, attackCity.getDefenceChief(), attackCity);
            }
            if (attackCity.getDefenceCounsellor() != null) {
                attackCity = AttackCity_Change_Before(2, attackCity.getDefenceCounsellor(), attackCity);
            }
            if (attackCity.getDefenceVice() != null) {
                attackCity = AttackCity_Change_Before(2, attackCity.getDefenceVice(), attackCity);
            }
        } else {
            // 没有神算
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
        if ("16".equals(general.getSkill())) {  // 威风
            System.out.println("武将：" + general.getName() + "触发技能：威风 对方所有兵种等级降一级");
            if (attOrDef == 1) {
                virgin.setDefencelevelAddition(-1);
            } else {
                virgin.setAttactlevelAddition(-1);
            }
        }
        if ("39".equals(general.getSkill())) {  // 昂扬
            System.out.println("武将：" + general.getName() + "触发技能：昂扬 所有兵种等级升一级");
            if (attOrDef == 1) {
                virgin.setAttactlevelAddition(1);
            } else {
                virgin.setDefencelevelAddition(1);
            }
        }
        // 造谣 逃走兵力

        return virgin;
    }

    // 野战修改
    private static Object BattleField_Change(int attOrDef, General general, Object virgin) {
        // TODO Auto-generated method stub
        BattleField BF = (BattleField) virgin;
        if ("5".equals(general.getSkill())) {  // 待伏
            System.out.println("武将：" + general.getName() + "触发技能：待伏，发动伏兵，根据智力增加兵力");
            if (attOrDef == 1) {
                BF.setAttackAmyNum((int) (BF.getAttackAmyNum() * (1 + (float) ((int) (Integer.parseInt(general.getIntelligence()))) / 2000)));
            } else {
                BF.setDefenceAmyNum((int) (BF.getDefenceAmyNum() * (1 + (float) ((int) (Integer.parseInt(general.getIntelligence()))) / 2000)));
            }
        }
        if ("12".equals(general.getSkill()) && new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {  // 深谋
            System.out.println("武将：" + general.getName() + "触发技能：深谋 野战或者攻城前，有几率直接对敌方造成伤害，伤害值与智力值有关，初始几率为60%，获得专属提升为100%（司马懿）");
            int lostNum = 0;
            if (attOrDef == 1) {
                lostNum = (int) (BF.getAttackAmyNum() * (Float.parseFloat(general.getIntelligence())) / 1000);
                System.out.println("防守方损失兵力：" + lostNum);
                BF.setDefenceAmyNum(BF.getDefenceAmyNum() - lostNum);
            } else {
                lostNum = (int) (BF.getDefenceAmyNum() * (Float.parseFloat(general.getIntelligence())) / 1000);
                System.out.println("进攻方损失兵力：" + lostNum);
                BF.setAttackAmyNum(BF.getAttackAmyNum() - lostNum);
            }
        }
        if ("16".equals(general.getSkill())) {  // 威风
            System.out.println("武将：" + general.getName() + "触发技能：威风 对方所有兵种等级降一级");
            if (attOrDef == 1) {
                BF.setDefencelevelAddition(-1);
            } else {
                BF.setAttactlevelAddition(-1);
            }
        }
        if ("39".equals(general.getSkill())) {  // 昂扬
            System.out.println("武将：" + general.getName() + "触发技能：昂扬 所有兵种等级升一级");
            if (attOrDef == 1) {
                BF.setAttactlevelAddition(1);
            } else {
                BF.setDefencelevelAddition(1);
            }
        }
        // 攻心
        if ("23".equals(general.getSkill()) && new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
            System.out.println("武将：" + general.getName() + SkillFactory.getSkillByID(general.getSkill()).getMemo());
            int lostNum;
            if (attOrDef == 1) {
                lostNum = (int) (BF.getAttackAmyNum() * ((Float.parseFloat(general.getCommand())) / 50 + (Float.parseFloat(general.getIntelligence())) / 50) / 100);
                System.out.println("防守方有" + lostNum + "兵力进入进攻方部队");
                BF.setDefenceAmyNum(BF.getDefenceAmyNum() - lostNum);
                BF.setAttackAmyNum(BF.getAttackAmyNum() + lostNum);
            } else {
                lostNum = (int) (BF.getDefenceAmyNum() * (Float.parseFloat(general.getIntelligence())) / 1000);
                System.out.println("进攻方有" + lostNum + "兵力进入防守方部队");
                BF.setAttackAmyNum(BF.getAttackAmyNum() - lostNum);
                BF.setDefenceAmyNum(BF.getDefenceAmyNum() + lostNum);
            }
        }
        // 反间  王允 貂蝉
        if ("24".equals(general.getSkill()) && new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
            System.out.println("武将-" + general.getName() + "技能触发：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
            int lostNum;
            if (attOrDef == 1) {
                lostNum = (int) (BF.getDefenceAmyNum() * (Float.parseFloat(general.getIntelligence()) * 2) / 1000);
                System.out.println("防守方互相攻击造成损失" + lostNum);
                BF.setDefenceAmyNum(BF.getDefenceAmyNum() - lostNum);
            } else {
                lostNum = (int) (BF.getDefenceAmyNum() * (Float.parseFloat(general.getIntelligence()) * 2) / 1000);
                System.out.println("进攻方互相攻击造成损失" + lostNum);
                BF.setAttackAmyNum(BF.getAttackAmyNum() - lostNum);
            }
        }
        return virgin;
    }

    // 野战修改 野战进行中
    private static Object BattleField_ChangeMiddle(int attOrDef, General general, Object virgin) {
        // TODO Auto-generated method stub
        BattleField BF = (BattleField) virgin;
        // 奸雄
        if ("2".equals(general.getSkill())) {
            if (new Random().nextInt(100) <= Objects.requireNonNull(SkillFactory.getSkillByID(general.getSkill())).getData()) {
                // 如果是进攻方 进攻方损失降低到0
                if (attOrDef == 1) {
                    if (IntelligenceCompare(general, BF.getDefenceChief(), BF.getDefenceCounsellor(), BF.getDefenceVice())) {
                        System.out.println("武将：" + general.getName() + "触发技能：奸雄，野战或攻城时，若敌方所有武将加成后智力小于自己，敌方每回合有15%的几率陷入混乱，无法造成伤害，获得专属武器将会提升几率(30%)");
                        BF.setAttLost(0);
                    }
                } else {
                    if (IntelligenceCompare(general, BF.getAttackChief(), BF.getAttackCounsellor(), BF.getAttackVice())) {
                        System.out.println("武将：" + general.getName() + "触发技能：奸雄，野战或攻城时，若敌方所有武将加成后智力小于自己，敌方每回合有15%的几率陷入混乱，无法造成伤害，获得专属武器将会提升几率(30%)");
                        BF.setDefLost(0);
                    }
                }
            }
        }

        // 大吼
        if ("6".equals(general.getSkill())) {
            System.out.println("武将：" + general.getName() + "触发技能：大吼，野战，城战时，额外增加对方的损失，根据武力值决定，获得专属武器，会额外提升损失的兵力");
            // 增加最大30%的损失 专属后 最大50%
            int addLost = 0;
            if (attOrDef == 1) {
                addLost = (int) (BF.getDefLost() * (Float.parseFloat(general.getAttack())) / 1000) * SkillFactory.getSkillByID(general.getSkill()).getData() / 10;
                BF.setDefLost(BF.getDefLost() + addLost);
            } else {
                addLost = (int) (BF.getAttLost() * (Float.parseFloat(general.getAttack())) / 1000) * SkillFactory.getSkillByID(general.getSkill()).getData() / 10;
                BF.setAttLost(BF.getAttLost() + addLost);
            }
            System.out.println("增加伤亡：" + addLost);
        }

        // 火神
        if ("15".equals(general.getSkill()) && new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
            System.out.println("武将：" + general.getName() + "触发技能：火神 野战或者攻城中，有机率施展火攻术，造成极大伤害，威力值与统帅、武力值有关，初始几率为10%，获得专属提升为20%");
            // 增加最大100%的损失
            int addLost = 0;
            if (attOrDef == 1) {
                addLost = (int) (BF.getDefLost() * ((Float.parseFloat(general.getCommand())) / 150 + (Float.parseFloat(general.getAttack())) / 50));
                BF.setDefLost(BF.getDefLost() + addLost);
            } else {
                addLost = (int) (BF.getAttLost() * ((Float.parseFloat(general.getCommand())) / 150 + (Float.parseFloat(general.getAttack())) / 50));
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
                    System.out.println("武将：" + general.getName() + "触发技能 骑兵会心一击");
                    addLost = (int) (BF.getAttLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getAttLost() + ")");
                    BF.setAttLost(BF.getAttLost() + addLost);
                }
            }
        }

        // 攻心
        if ("23".equals(general.getSkill()) && new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
            System.out.println("武将：" + general.getName() + SkillFactory.getSkillByID(general.getSkill()).getMemo());
            int lostNum;
            if (attOrDef == 1) {
                lostNum = (int) (BF.getAttackAmyNum() * ((Float.parseFloat(general.getCommand())) / 50 + (Float.parseFloat(general.getIntelligence())) / 50) / 100);
                System.out.println("防守方有" + lostNum + "兵力进入进攻方部队");
                BF.setDefenceAmyNum(BF.getDefenceAmyNum() - lostNum);
                BF.setAttackAmyNum(BF.getAttackAmyNum() + lostNum);
            } else {
                lostNum = (int) (BF.getDefenceAmyNum() * (Float.parseFloat(general.getIntelligence())) / 1000);
                System.out.println("进攻方有" + lostNum + "兵力进入防守方部队");
                BF.setAttackAmyNum(BF.getAttackAmyNum() - lostNum);
                BF.setDefenceAmyNum(BF.getDefenceAmyNum() + lostNum);
            }
        }
        // 反间  王允 貂蝉
        if ("24".equals(general.getSkill()) && new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
            System.out.println("武将-" + general.getName() + "技能触发：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
            int lostNum;
            if (attOrDef == 1) {
                lostNum = (int) (BF.getDefenceAmyNum() * (Float.parseFloat(general.getIntelligence()) * 2) / 1000);
                System.out.println("防守方互相攻击造成损失" + lostNum);
                BF.setDefenceAmyNum(BF.getDefenceAmyNum() - lostNum);
            } else {
                lostNum = (int) (BF.getDefenceAmyNum() * (Float.parseFloat(general.getIntelligence()) * 2) / 1000);
                System.out.println("进攻方互相攻击造成损失" + lostNum);
                BF.setAttackAmyNum(BF.getAttackAmyNum() - lostNum);
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getAttLost() * 0.2) + "(" + BF.getAttLost() + ")");
                BF.setAttLost((int) (BF.getAttLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
                    System.out.println("武将：" + general.getName() + "触发技能 会心一击");
                    addLost = (int) (BF.getDefLost() * 0.5);
                    System.out.println("增加伤亡：" + addLost + "(" + BF.getDefLost() + ")");
                    BF.setDefLost(BF.getDefLost() + addLost);
                }
            }
            if (attOrDef == 2 && (BF.getDefenceType() == 2 || BF.getDefenceType() == 3)) {
                System.out.println("武将：" + general.getName() + "触发技能：" + SkillFactory.getSkillByID(general.getSkill()).getMemo());
                System.out.println("减少伤亡" + (int) (BF.getDefLost() * 0.2) + "(" + BF.getDefLost() + ")");
                BF.setDefLost((int) (BF.getDefLost() * 0.8));
                // 几率增加损失
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
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
            if (attOrDef == 1 && BF.getAttackType() == 3) {
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
                    System.out.println("武将：" + general.getName() + "触发技能：白马，每回合有50%的几率发动骑射，专属后100%几率,伤害和统帅武力有关");
                    addLost = (int) ((int) (BF.getDefLost() * (Float.parseFloat(general.getAttack())) / 2000) * (Float.parseFloat(general.getCommand()) / 30));
                    BF.setDefLost(BF.getDefLost() + addLost);
                    System.out.println("增加伤亡：" + addLost);
                }
            } else if (attOrDef == 2 && BF.getDefenceType() == 3) {
                if (new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
                    System.out.println("武将：" + general.getName() + "触发技能：白马，每回合有50%的几率发动骑射，专属后100%几率,伤害和统帅武力有关");
                    addLost = (int) ((int) (BF.getAttLost() * (Float.parseFloat(general.getAttack())) / 2000) * (Float.parseFloat(general.getCommand()) / 30));
                    BF.setAttLost(BF.getAttLost() + addLost);
                    System.out.println("增加伤亡：" + addLost);
                }
            }
        }
        return BF;
    }

    private static boolean IntelligenceCompare(General general, General chief, General counsellor, General vice) {
        if (Integer.parseInt(general.getIntelligence()) > Integer.parseInt(chief.getIntelligence())) {
        } else {
            return false;
        }
        if (counsellor != null && Integer.parseInt(general.getIntelligence()) > Integer.parseInt(counsellor.getIntelligence())) {
        } else {
            return false;
        }
        if (vice != null && Integer.parseInt(general.getIntelligence()) > Integer.parseInt(vice.getIntelligence())) {
        } else {
            return false;
        }

        return true;
    }


    // 单挑修改
    private static Object OneOnOne_Change(int attOrDef, General general, Object virgin) {
        //
        OneOnOne o = (OneOnOne) virgin;
        if ("4".equals(general.getSkill())) { //单挑时对方防御力减少20%
            System.out.println("武将：" + general.getName() + "触发技能：武圣，对方防御力减少百分之" + SkillFactory.getSkillByID(general.getSkill()).getData());
            if (attOrDef == 1) {
                o.setDefenceB(o.getDefenceB() * (100 - SkillFactory.getSkillByID(general.getSkill()).getData()) / 100);
            } else {
                o.setDefenceA(o.getDefenceA() * (100 - SkillFactory.getSkillByID(general.getSkill()).getData()) / 100);
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
        if (type == 2) { // 如果是野战要注意把6个武将的技能都照顾到
            // 如果有神算触发，则另一方的技能不能触发
            BattleField BF = (BattleField) virgin;
            if ((BF.getAttackChief() != null && "13".equals(BF.getAttackChief().getSkill()) && (new Random().nextInt(101) <= SkillFactory.getSkillByID(BF.getAttackChief().getSkill()).getData())) || (BF.getAttackVice() != null && "13".equals(BF.getAttackVice().getSkill()) && (new Random().nextInt(101) <= SkillFactory.getSkillByID(BF.getAttackVice().getSkill()).getData())) || (BF.getAttackCounsellor() != null && "13".equals(BF.getAttackCounsellor().getSkill()) && (new Random().nextInt(101) <= SkillFactory.getSkillByID(BF.getAttackCounsellor().getSkill()).getData()))) { //神算 触发几率
                // 只有进攻方的技能触发
                System.out.println("神算技能触发，防守方无法释放技能");
                battleField_Middle_Attack(BF);
            } else if ((BF.getDefenceChief() != null && "13".equals(BF.getDefenceChief().getSkill()) && (new Random().nextInt(101) <= SkillFactory.getSkillByID(BF.getDefenceChief().getSkill()).getData())) || (BF.getDefenceVice() != null && "13".equals(BF.getDefenceVice().getSkill()) && (new Random().nextInt(101) <= SkillFactory.getSkillByID(BF.getDefenceVice().getSkill()).getData())) || (BF.getDefenceCounsellor() != null && "13".equals(BF.getDefenceCounsellor().getSkill()) && (new Random().nextInt(101) <= SkillFactory.getSkillByID(BF.getDefenceCounsellor().getSkill()).getData()))) { //神算 触发几率
                // 只有进攻方的技能触发
                System.out.println("神算技能触发，进攻方无法释放技能");
                battleField_Middle_Defence(BF);
            } else {
                // 没有神算
                battleField_Middle_Attack(BF);
                battleField_Middle_Defence(BF);
            }
        }
    }

    private static void battleField_Middle_Defence(BattleField BF) {
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
    }

    // 单挑中修改
    private static void OneOnOne_ChangeMiddle(int attOrDef, General generalA, General generalD, FightData sigleFight) {

        // 鬼将
        if ("7".equals(generalA.getSkill()) && sigleFight.attackType == 1) {//鬼将 单挑时，普通攻击造成1.2倍的伤害 1表示普通攻击
            System.out.println("武将" + generalA.getName() + "技能：" + SkillFactory.getSkillByID(generalA.getSkill()).getName() + "触发,造成" + SkillFactory.getSkillByID(generalA.getSkill()).getData() + "%的伤害");
            sigleFight.defenceLostHealth = sigleFight.defenceLostHealth * SkillFactory.getSkillByID(generalA.getSkill()).getData() / 100;
        }
        if ("7".equals(generalD.getSkill()) && sigleFight.defenceType == 1) {//鬼将 单挑时，普通攻击造成1.2倍的伤害 1表示普通攻击
            System.out.println("武将" + generalD.getName() + "技能：" + SkillFactory.getSkillByID(generalD.getSkill()).getName() + "触发,造成" + "%的伤害");
            sigleFight.attackLostHealth = sigleFight.attackLostHealth * SkillFactory.getSkillByID(generalD.getSkill()).getData() / 100;
        }

        // 猛将
        if ("11".equals(generalA.getSkill()) && new Random().nextInt(101) <= SkillFactory.getSkillByID(generalA.getSkill()).getData()) {//鬼将 单挑时，普通攻击造成1.2倍的伤害 1表示普通攻击
            System.out.println("武将" + generalA.getName() + "技能：" + SkillFactory.getSkillByID(generalA.getSkill()).getName() + "触发,额外造成" + SkillFactory.getSkillByID(generalA.getSkill()).getData() + "%的伤害");
            sigleFight.defenceLostHealth = sigleFight.defenceLostHealth * (SkillFactory.getSkillByID(generalA.getSkill()).getData() + 100) / 100;
        }
        if ("11".equals(generalD.getSkill()) && new Random().nextInt(101) <= SkillFactory.getSkillByID(generalA.getSkill()).getData()) {//鬼将 单挑时，普通攻击造成1.2倍的伤害 1表示普通攻击
            System.out.println("武将" + generalD.getName() + "技能：" + SkillFactory.getSkillByID(generalD.getSkill()).getName() + "触发,额外造成" + SkillFactory.getSkillByID(generalA.getSkill()).getData() + "%的伤害");
            sigleFight.attackLostHealth = sigleFight.attackLostHealth * (SkillFactory.getSkillByID(generalD.getSkill()).getData() + 100) / 100;
        }

    }

    /**
     * @param @param  type 1 单挑 2 野战 3 攻城  4 俘虏后 5 单挑结束后 9 回合结束后
     * @param @param  AttOrDef 1 进攻方  2 防守方 3 所有一起算
     * @param @param  generalA 进攻方的英雄
     * @param @param  generalD 防御方的英雄
     * @param @param  virgin 未改变的
     * @param @return 设定文件
     * @return Object    返回类型
     * @throws
     * @Title: changeAfter
     * @Description: TODO(这里用一句话描述这个方法的作用)
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
        // 野战
        if (type == 3) {
            attackCity_After_Attack((AttackCity) virgin);
            attackCity_After_Defence((AttackCity) virgin);
        }
        // 单挑结束后
        if (type == 5) {
            Fight.PostwarCalculation pc = (Fight.PostwarCalculation) virgin;
            OneOnOne_ChangeAfterComplete(generalA, generalD, pc);
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

    private static void attackCity_After_Defence(General general, AttackCity virgin) {
        // 坚守
        if ("14".equals(general.getSkill())) {
            int addLost;
            System.out.println("武将：" + general.getName() + "触发技能：坚守 野战或者攻城防守中，极大的减少本队损失的人数，减少的额度与统帅值有关，最大20%");
            addLost = (int) (virgin.getDefenceLost() * (Float.parseFloat(general.getCommand())) / 500);
            System.out.println("减少损失" + addLost + "(" + virgin.getDefenceLost() + ")");
            virgin.setDefenceLost(virgin.getDefenceLost() - addLost);
        }

        // 铁壁
        if ("30".equals(general.getSkill())) {
            int defLost = virgin.getDefenceLost();
            Skill skillByID = SkillFactory.getSkillByID(general.getSkill());
            int data = skillByID.getData() + (Integer.parseInt(general.getCommand()) + Integer.parseInt(general.getAttack()) + Integer.parseInt(general.getCharm())) / 10;
            defLost = defLost * (1 - data / 100);
            System.out.println(general.getName() + "技能" + skillByID.getName() + "触发,损失兵力减少" + data + "%(" + defLost + ")");
            virgin.setDefenceLost(defLost);
        }

        // 医疗
        if ("26".equals(general.getSkill()) || "27".equals(general.getSkill())) {
            int defLost = virgin.getDefenceLost();
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
            int data = defLost * (skillByID.getData() + extra) / 100;
            System.out.println(general.getName() + "技能" + skillByID.getName() + "触发,救治兵力" + data + "(" + defLost + ")");
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

    private static void attackCity_After_Attack(General general, AttackCity virgin) {

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
            int data = attackLost * (skillByID.getData() + extra) / 100;
            System.out.println(general.getName() + "技能" + skillByID.getName() + "触发,救治兵力" + data + "(" + attackLost + ")");
            virgin.setAttackLost(attackLost - data);
        }

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
        if ("1".equals(general.getSkill()) && new Random().nextInt(100) <= SkillFactory.getSkillByID(general.getSkill()).getData()) {
            System.out.println("仁德触发，随机获得一个武将");
            List<General> generals = GeneralFactory.allFreeGenerals();
            if (generals == null || generals.size() == 0) {
                System.out.println("已经没有在野武将了");
                return;
            }
            int index = new Random().nextInt(generals.size());

            for (General initGeneral : GeneralFactory.getInitGenerals()) {
                if (initGeneral.getId() == generals.get(index).getId()) {
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
        // 金刚
        if ("25".equals(general.getSkill())) {
            int defLost = virgin.getDefLost();
            Skill skillByID = SkillFactory.getSkillByID(general.getSkill());
            int data = skillByID.getData() - (Integer.parseInt(general.getCommand()) + Integer.parseInt(general.getIntelligence())) / 2;
            if (defLost > data) {
                System.out.println(general.getName() + "技能" + skillByID.getName() + "触发,损失兵力减少至" + data + "(" + defLost + ")");
                virgin.setDefLost(data);
            }
        }
        // 医疗
        if ("26".equals(general.getSkill()) || "27".equals(general.getSkill())) {
            int defLost = virgin.getDefLost();
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
            int data = defLost * (skillByID.getData() + extra) / 100;
            System.out.println(general.getName() + "技能" + skillByID.getName() + "触发,救治兵力" + data + "(" + defLost + ")");
            virgin.setDefLost(defLost - data);
        }
        // 坚守
        if ("14".equals(general.getSkill())) {
            int addLost;
            System.out.println("武将：" + general.getName() + "触发技能：坚守 野战或者攻城防守中，极大的减少本队损失的人数，减少的额度与统帅值有关，最大20%");
            addLost = (int) (virgin.getDefLost() * (Float.parseFloat(general.getCommand())) / 500);
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
        // 金刚
        if ("25".equals(general.getSkill())) {
            int attLost = virgin.getAttLost();
            Skill skillByID = SkillFactory.getSkillByID(general.getSkill());
            int data = skillByID.getData() - (Integer.parseInt(general.getCommand()) + Integer.parseInt(general.getIntelligence())) / 2;
            if (attLost > data) {
                System.out.println(general.getName() + "技能" + skillByID.getName() + "触发,损失兵力减少至" + data + "(" + attLost + ")");
                virgin.setAttLost(data);
            }
        }
        // 医疗
        if ("26".equals(general.getSkill()) || "27".equals(general.getSkill())) {
            int attLost = virgin.getAttLost();
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
            int data = attLost * (skillByID.getData() + extra) / 100;
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
        // 裸衣
        if ("9".equals(generalA.getSkill())) {//裸衣
            int addAtt = (100 - fight.getVitalityA()) * SkillFactory.getSkillByID(generalA.getSkill()).getData() / 100;
            System.out.println("武将" + generalA.getName() + "技能：" + SkillFactory.getSkillByID(generalA.getSkill()).getName() + "触发,增加" + addAtt + "攻击力");
            fight.setAttackA(fight.getAttackA() + addAtt);
        }
        if ("9".equals(generalD.getSkill())) {//鬼将 单挑时，普通攻击造成1.2倍的伤害 1表示普通攻击
            int addAtt = (100 - fight.getVitalityB()) * SkillFactory.getSkillByID(generalD.getSkill()).getData() / 100;
            System.out.println("武将" + generalD.getName() + "技能：" + SkillFactory.getSkillByID(generalD.getSkill()).getName() + "触发,增加" + addAtt + "攻击力");
            fight.setAttackB(fight.getAttackB() + addAtt);
        }

        // 恶来
        if ("10".equals(generalA.getSkill())) {//恶来
            int addDef = (100 - fight.getVitalityA()) * SkillFactory.getSkillByID(generalA.getSkill()).getData() / 100;
            System.out.println("武将" + generalA.getName() + "技能：" + SkillFactory.getSkillByID(generalA.getSkill()).getName() + "触发,增加" + addDef + "防御力");
            fight.setDefenceA(fight.getDefenceA() + addDef);
        }
        if ("10".equals(generalD.getSkill())) {//恶来
            int addDef = (100 - fight.getVitalityB()) * SkillFactory.getSkillByID(generalD.getSkill()).getData() / 100;
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

        for (Iterator iterator = denfenceGenerals.iterator(); iterator.hasNext(); ) {
            General general = (General) iterator.next();
            data = citySkillChange(data, general, type);
        }

        return data;
    }

    // 单一的城市技能触发
    private static int citySkillChange(int data, General general, int type) {
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
        // 繁殖
        if ("42".equals(general.getSkill())) {
            System.out.println(general.getSkill() + "技能繁殖触发，骑兵数量增加50%");
            data = (int) (data + data * 0.5);
        }
        return data;
    }

    private static int citySkillNotHorseChange(int data, General general) {
        // 能吏
        if ("43".equals(general.getSkill())) {
            System.out.println(general.getSkill() + "技能能吏触发，弓兵、枪兵数量增加50%");
            data = (int) (data + data * 0.5);
        }
        return data;
    }

    //
    private static int citySkillMoneyChange(int data, General general) {
        // 富豪
        if ("8".equals(general.getSkill())) {
            System.out.println(general.getSkill() + "技能富豪触发，增加发展金50%");
            data = (int) (data + data * 0.5);
        }
        return data;
    }
}
