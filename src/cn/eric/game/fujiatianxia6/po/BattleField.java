package cn.eric.game.fujiatianxia6.po;

import java.util.HashMap;
import java.util.Optional;

import cn.eric.game.fujiatianxia6.factory.GeneralFactory;
import cn.eric.game.fujiatianxia6.factory.SkillFactory;
import cn.eric.game.fujiatianxia6.service.ArmsService;
import cn.eric.game.fujiatianxia6.service.Util;

public class BattleField {

    private General AttackChief = null;
    private General AttackVice = null;
    private General AttackCounsellor = null;

    private General DefenceChief = null;
    private General DefenceVice = null;
    private General DefenceCounsellor = null;

    private City city;

    private boolean result;

    private int attackAmyNum = 0;

    private int defenceAmyNum = 0;
    private int defenceCavalrysNum = 0;
    private int defenceInfantryNum = 0;
    private int defenceArchersNum = 0;

    private int attLost = 0;
    private int defLost = 0;

    private int attackType = 0; // 1 普通 2 骑兵 3 枪兵 4 弓兵
    private double attackFactor = 1;
    private int defenceType = 0; //  1 普通 2 骑兵 3 枪兵 4 弓兵
    private double defenceFactor = 1;

    public General getAttackChief() {
        return AttackChief;
    }

    public void setAttackChief(General attackChief) {
        AttackChief = attackChief;
    }

    public General getAttackVice() {
        return AttackVice;
    }

    public void setAttackVice(General attackVice) {
        AttackVice = attackVice;
    }

    public General getAttackCounsellor() {
        return AttackCounsellor;
    }

    public void setAttackCounsellor(General attackCounsellor) {
        AttackCounsellor = attackCounsellor;
    }

    public General getDefenceChief() {
        return DefenceChief;
    }

    public void setDefenceChief(General defenceChief) {
        DefenceChief = defenceChief;
    }

    public General getDefenceVice() {
        return DefenceVice;
    }

    public void setDefenceVice(General defenceVice) {
        DefenceVice = defenceVice;
    }

    public General getDefenceCounsellor() {
        return DefenceCounsellor;
    }

    public void setDefenceCounsellor(General defenceCounsellor) {
        DefenceCounsellor = defenceCounsellor;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getAttackAmyNum() {
        return attackAmyNum;
    }

    public void setAttackAmyNum(int attackAmyNum) {
        this.attackAmyNum = attackAmyNum;
    }

    public int getDefenceAmyNum() {
        return defenceAmyNum;
    }

    public void setDefenceAmyNum(int defenceAmyNum) {
        this.defenceAmyNum = defenceAmyNum;
    }

    public int getAttLost() {
        return attLost;
    }

    public void setAttLost(int attLost) {
        this.attLost = attLost;
    }

    public int getDefLost() {
        return defLost;
    }

    public void setDefLost(int defLost) {
        this.defLost = defLost;
    }

    /**
     * @param @return 设定文件
     * @return boolean    返回类型
     * @throws
     * @Title: fight
     * @Description: 野战打架
     */
    public boolean fight() {
        if (AttackChief != null && DefenceChief != null) {
            if (Integer.parseInt(AttackChief.getCommand()) >= Integer.parseInt(DefenceChief.getCommand())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * @param attactType 进攻兵种
     * @param defence
     * @param general
     * @param @return    设定文件
     * @return boolean    返回类型
     * @throws
     * @Title: fight
     * @Description: 野战打架
     */
    public boolean fight2(General general, City defence, int attactType) {
        this.setAttackType(attactType);
        String armType = "";
        if (AttackChief != null && DefenceChief != null) {
            // 设定进攻方的兵力，默认1000
            switch (attactType) {
                case 1:
                    if (general.getArmy() < 1000) {
                        attackAmyNum = general.getArmy();
                    } else {
                        attackAmyNum = 1000;
                    }
                    armType = "剑士";
                    attackFactor = 1.0;
                    break;
                case 2: // 骑兵
                    if (general.getCavalrys() < 1000) {
                        attackAmyNum = general.getCavalrys();
                    } else {
                        attackAmyNum = 1000;
                    }
                    armType = "骑兵";
                    // 根据地形选择加成
                    if (defence.getTopography() == 1) {
                        attackFactor = FightConfig.factorOfCavalrysInLand;
                    } else if (defence.getTopography() == 2) {
                        attackFactor = FightConfig.factorOfCavalrysInMountain;
                    } else {
                        attackFactor = FightConfig.factorOfCavalrysInRiver;
                    }
                    attackFactor = attackFactor * (1 + ArmsService.getArmsByGeneralAndName(general, "骑兵").getLevel() * 0.1);
                    break;
                case 3: // 枪兵
                    if (general.getInfantry() < 1000) {
                        attackAmyNum = general.getInfantry();
                    } else {
                        attackAmyNum = 1000;
                    }
                    armType = "枪兵";
                    if (defence.getTopography() == 2) {
                        attackFactor = FightConfig.factorOfInfantryInMountain;
                    } else {
                        attackFactor = FightConfig.factorOfInfantryInLand;
                    }
                    attackFactor = attackFactor * (1 + ArmsService.getArmsByGeneralAndName(general, "枪兵").getLevel() * 0.1);
                    break;
                case 4: // 弓兵
                    if (general.getArchers() < 1000) {
                        attackAmyNum = general.getArchers();
                    } else {
                        attackAmyNum = 1000;
                    }
                    armType = "弓兵";
                    if (defence.getTopography() == 3) {
                        attackFactor = FightConfig.factorOfArchersInRiver;
                    } else if (defence.getTopography() == 1) {
                        attackFactor = FightConfig.factorOfArchersInMountain;
                    } else {
                        attackFactor = FightConfig.factorOfArchersInLand;
                    }
                    attackFactor = attackFactor * (1 + ArmsService.getArmsByGeneralAndName(general, "弓兵").getLevel() * 0.1);
                    break;
                default:
                    if (general.getArmy() < 1000) {
                        attackAmyNum = general.getArmy();
                    } else {
                        attackAmyNum = 1000;
                    }
                    armType = "剑士";
                    break;
            }

            System.out.println("进攻方主将：" + AttackChief.getName() + "统帅：" + AttackChief.getCommand() + "技能：" + SkillFactory.getSkillByID(AttackChief.getSkill()).getName() + "-->:" + SkillFactory.getSkillByID(AttackChief.getSkill()).getMemo() + "\n" + "兵种" + armType + "战斗力：" + attackAmyNum + "因子" + attackFactor);

            // 设定防守方的兵力，默认1000
            getDefenceAmyTypeAndNum(city);
            System.out.println("防守方主将：" + DefenceChief.getName() + "统帅：" + DefenceChief.getCommand() + "技能：" + SkillFactory.getSkillByID(DefenceChief.getSkill()).getName() + "-->:" + SkillFactory.getSkillByID(DefenceChief.getSkill()).getMemo() + "\n" + "兵种" + (defenceType == 1 ? "剑士" : defenceType == 2 ? "骑兵" : defenceType == 3 ? "枪兵" : "弓兵") + "兵力：" + defenceAmyNum);

            // 开战 野战
            // 野战前技能触发 伏兵等
            if (AttackChief != null && SkillFactory.getSkillByID(AttackChief.getSkill()).getTime() == 2) {
                SkillFactory.changeBefore(2, 1, AttackChief, null, this);
            }
            if (AttackVice != null && SkillFactory.getSkillByID(AttackVice.getSkill()).getTime() == 2) {
                SkillFactory.changeBefore(2, 1, AttackVice, null, this);
            }
            if (AttackCounsellor != null && SkillFactory.getSkillByID(AttackCounsellor.getSkill()).getTime() == 2) {
                SkillFactory.changeBefore(2, 1, AttackVice, null, this);
            }
            // 防守方技能触发
            if (DefenceChief != null && SkillFactory.getSkillByID(DefenceChief.getSkill()).getTime() == 2) {
                SkillFactory.changeBefore(2, 2, DefenceChief, null, this);
            }
            if (DefenceVice != null && SkillFactory.getSkillByID(DefenceVice.getSkill()).getTime() == 2) {
                SkillFactory.changeBefore(2, 2, DefenceChief, null, this);
            }
            if (DefenceCounsellor != null && SkillFactory.getSkillByID(DefenceCounsellor.getSkill()).getTime() == 2) {
                SkillFactory.changeBefore(2, 2, DefenceChief, null, this);
            }

            // 野战打3个回合，3个回个结束后，兵力不足或者兵力较少的一方失败

            // 进攻方损失 消耗兵力的0%-20% 根据守将的统帅决定
            int count = 0;
            int attLostCount = 0;
            int defLostCount = 0;
            while (attackAmyNum > 0 && defenceAmyNum > 0) {

                //取十次里面的最大随机数
                float randomAtt = Util.getMaxFloatNum(10);
                float randomDef = Util.getMaxFloatNum(10);
                attLost = (int) (defenceAmyNum * defenceFactor * (3 * (float) ((int) (randomAtt * (Math.max(Integer.parseInt(DefenceChief.getAttack())
                        , Integer.parseInt(Optional.ofNullable(Optional.ofNullable(DefenceCounsellor).orElse(new General()).getAttack()).orElse("0")))))) / 2000));
                defLost = (int) (attackAmyNum * attackFactor * (3 * (float) ((int) (randomDef * (Math.max(Integer.parseInt(AttackChief.getAttack())
                        , Integer.parseInt(Optional.ofNullable(Optional.ofNullable(AttackCounsellor).orElse(new General()).getAttack()).orElse("0")))))) / 2000));

                // 野战中技能触发
                SkillFactory.changeMiddle(2, 3, null, null, this);

                // 技能放完后，损失的数量要减去对方统帅 统帅越高，免伤越多
                if (Integer.parseInt(AttackChief.getCommand()) >= 100) {
                    attLost = (int) (attLost * 0.5);
                } else if (Integer.parseInt(AttackChief.getCommand()) >= 90) {
                    attLost = (int) (attLost * 0.6);
                } else if (Integer.parseInt(AttackChief.getCommand()) >= 80) {
                    attLost = (int) (attLost * 0.75);
                } else if (Integer.parseInt(AttackChief.getCommand()) >= 70) {
                    attLost = (int) (attLost * 0.95);
                }

                if (Integer.parseInt(DefenceChief.getCommand()) >= 100) {
                    defLost = (int) (defLost * 0.5);
                } else if (Integer.parseInt(DefenceChief.getCommand()) >= 90) {
                    defLost = (int) (defLost * 0.6);
                } else if (Integer.parseInt(DefenceChief.getCommand()) >= 80) {
                    defLost = (int) (defLost * 0.75);
                } else if (Integer.parseInt(DefenceChief.getCommand()) >= 70) {
                    defLost = (int) (defLost * 0.95);
                }

                // 野战每回合结束前触发 例如治疗 金刚 藤甲
                SkillFactory.changeAfter(2, 3, null, null, this);

                attackAmyNum = attackAmyNum - attLost;
                defenceAmyNum = defenceAmyNum - defLost;
                attLostCount += attLost;
                defLostCount += defLost;
                count++;
                System.out.println("第" + count + "次交战，进攻方损失兵力" + attLost + " 剩余兵力" + attackAmyNum + "，防守方损失兵力" + defLost + "剩余兵力" + defenceAmyNum);
                if (count >= 3) {
                    break;
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 野战后技能触发

            // 重新计算兵力
            if (attactType == 1) {
                general.setArmy((general.getArmy() - attLostCount) < 0 ? 0 : (general.getArmy() - attLostCount));
            } else if (attactType == 2) {
                general.setCavalrys((general.getCavalrys() - attLostCount) < 0 ? 0 : (general.getCavalrys() - attLostCount));
            } else if (attactType == 3) {
                general.setInfantry((general.getInfantry() - attLostCount) < 0 ? 0 : (general.getInfantry() - attLostCount));
            } else if (attactType == 4) {
                general.setArchers((general.getArchers() - attLostCount) < 0 ? 0 : (general.getArchers() - attLostCount));
            }
            //
            if (defenceType == 1) {
                city.setSoilders((city.getSoilders() - defLostCount) < 0 ? 0 : (city.getSoilders() - defLostCount));
            } else if (defenceType == 2) {
                city.setCavalrys((city.getCavalrys() - defLostCount) < 0 ? 0 : (city.getCavalrys() - defLostCount));
            } else if (defenceType == 3) {
                city.setInfantry((city.getInfantry() - defLostCount) < 0 ? 0 : (city.getInfantry() - defLostCount));
            } else if (defenceType == 4) {
                city.setArchers((city.getArchers() - defLostCount) < 0 ? 0 : (city.getArchers() - defLostCount));
            }

            // 如果攻击方剩余的兵力多，则获胜
            if (attackAmyNum >= defenceAmyNum) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    // 计算出什么兵种合适
    @SuppressWarnings("unused")
    private void getDefenceAmyTypeAndNum(City city) {
        //
        General general = GeneralFactory.getGeneralById(city.getBelongTo().toString());
        // 得到 骑 枪 弓的级别
        HashMap<String, Integer> levelMap = new HashMap<>();
        for (Arms arms : general.getArmsTotal()) {
            levelMap.put(arms.getName(), arms.getLevel());
        }

        int amyAttack = 0;
        int cavalrysAttack = 0;
        int infantryAttack = 0;
        int archersAttack = 0;

        if (city.getSoilders() < 1000) {
            defenceAmyNum = city.getSoilders();
        } else {
            defenceAmyNum = 1000;
        }

        if (city.getCavalrys() < 1000) {
            defenceCavalrysNum = city.getCavalrys();
        } else {
            defenceCavalrysNum = 1000;
        }

        if (city.getInfantry() < 1000) {
            defenceInfantryNum = city.getInfantry();
        } else {
            defenceInfantryNum = 1000;
        }

        if (city.getArchers() < 1000) {
            defenceArchersNum = city.getArchers();
        } else {
            defenceArchersNum = 1000;
        }

        // 根据城市的地形，获得各种兵种的加成
        // 适应兵种  平原 骑>枪>弓  山地 枪>弓>骑   水道 弓>枪>骑  战力分别是 200% 160% 120%
        switch (city.getTopography()) {
            case 1: // 平原
                setDefenceAmyType(1.0, FightConfig.factorOfCavalrysInLand * (1 + levelMap.get("骑兵") * 0.1), FightConfig.factorOfInfantryInLand * (1 + levelMap.get("枪兵") * 0.1), FightConfig.factorOfArchersInLand * (1 + levelMap.get("弓兵") * 0.1));
                break;
            case 2: // 山地
                setDefenceAmyType(1.0, FightConfig.factorOfCavalrysInMountain * (1 + levelMap.get("骑兵") * 0.1), FightConfig.factorOfInfantryInMountain * (1 + levelMap.get("枪兵") * 0.1), FightConfig.factorOfArchersInMountain * (1 + levelMap.get("弓兵") * 0.1));
                break;
            case 3: // 水道
                setDefenceAmyType(1.0, FightConfig.factorOfCavalrysInRiver * (1 + levelMap.get("骑兵") * 0.1), FightConfig.factorOfInfantryInRiver * (1 + levelMap.get("枪兵") * 0.1), FightConfig.factorOfArchersInRiver * (1 + levelMap.get("弓兵") * 0.1));
                break;
            default:// 平原
                setDefenceAmyType(1.0, FightConfig.factorOfCavalrysInLand * (1 + levelMap.get("骑兵") * 0.1), FightConfig.factorOfInfantryInLand * (1 + levelMap.get("枪兵") * 0.1), FightConfig.factorOfArchersInLand * (1 + levelMap.get("弓兵") * 0.1));
                break;
        }
    }

    // 根据城市的地形，获得各种兵种的加成 计算出战的兵种
    private void setDefenceAmyType(double factor1, double factor2, double factor3, double factor4) {
        int amyAttack;
        int cavalrysAttack;
        int infantryAttack;
        int archersAttack;
        int tempType = 0;
        int tempType1 = 0;
        int tempType2 = 0;
        int temp1 = 0;
        int temp2 = 0;

        amyAttack = (int) (defenceAmyNum * factor1);
        cavalrysAttack = (int) (defenceCavalrysNum * factor2);
        infantryAttack = (int) (defenceInfantryNum * factor3);
        archersAttack = (int) (defenceArchersNum * factor4);
        if (amyAttack > cavalrysAttack) {
            temp1 = amyAttack;
            tempType1 = 1;
        } else {
            temp1 = cavalrysAttack;
            tempType1 = 2;
        }
        if (infantryAttack > archersAttack) {
            temp2 = infantryAttack;
            tempType2 = 3;
        } else {
            temp2 = archersAttack;
            tempType2 = 4;
        }

        if (temp1 > temp2) {
            tempType = tempType1;
        } else {
            tempType = tempType2;
        }
        defenceType = tempType;
        switch (defenceType) {
            case 1:
                defenceFactor = factor1;
                defenceCavalrysNum = 0;
                defenceInfantryNum = 0;
                defenceArchersNum = 0;
                break;
            case 2:
                defenceAmyNum = defenceCavalrysNum;
                defenceFactor = factor2;
                defenceCavalrysNum = 0;
                defenceInfantryNum = 0;
                defenceArchersNum = 0;
                break;
            case 3:
                defenceAmyNum = defenceInfantryNum;
                defenceFactor = factor3;
                defenceCavalrysNum = 0;
                defenceInfantryNum = 0;
                defenceArchersNum = 0;
                break;
            case 4:
                defenceAmyNum = defenceArchersNum;
                defenceFactor = factor4;
                defenceCavalrysNum = 0;
                defenceInfantryNum = 0;
                defenceArchersNum = 0;
                break;
            default:
                defenceFactor = factor1;
                defenceCavalrysNum = 0;
                defenceInfantryNum = 0;
                defenceArchersNum = 0;
                break;
        }
    }

    public int getDefenceCavalrysNum() {
        return defenceCavalrysNum;
    }

    public void setDefenceCavalrysNum(int defenceCavalrysNum) {
        this.defenceCavalrysNum = defenceCavalrysNum;
    }

    public int getDefenceInfantryNum() {
        return defenceInfantryNum;
    }

    public void setDefenceInfantryNum(int defenceInfantryNum) {
        this.defenceInfantryNum = defenceInfantryNum;
    }

    public int getDefenceArchersNum() {
        return defenceArchersNum;
    }

    public void setDefenceArchersNum(int defenceArchersNum) {
        this.defenceArchersNum = defenceArchersNum;
    }

    public int getAttackType() {
        return attackType;
    }

    public void setAttackType(int attackType) {
        this.attackType = attackType;
    }

    public double getAttackFactor() {
        return attackFactor;
    }

    public void setAttackFactor(double attackFactor) {
        this.attackFactor = attackFactor;
    }

    public int getDefenceType() {
        return defenceType;
    }

    public void setDefenceType(int defenceType) {
        this.defenceType = defenceType;
    }

    public double getDefenceFactor() {
        return defenceFactor;
    }

    public void setDefenceFactor(double defenceFactor) {
        this.defenceFactor = defenceFactor;
    }
}
