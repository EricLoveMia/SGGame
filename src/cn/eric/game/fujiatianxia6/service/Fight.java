package cn.eric.game.fujiatianxia6.service;

import cn.eric.game.fujiatianxia6.enums.CityTopographyEnum;
import cn.eric.game.fujiatianxia6.factory.CityFactory;
import cn.eric.game.fujiatianxia6.factory.GeneralFactory;
import cn.eric.game.fujiatianxia6.factory.OneOnOne;
import cn.eric.game.fujiatianxia6.factory.SkillFactory;
import cn.eric.game.fujiatianxia6.po.*;

import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

/**
 * @author 00322027
 * @ClassName: Fight
 * @Description: 打架专用类
 * @date 2018年1月12日 下午5:20:20
 */
public class Fight {

    private static boolean attack;

    private static final int deadPercent = 4;
    private static final int catchPercent = 7;

    static BattleField BF;

    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title: oneOnOneFight
     * @Description: 单挑
     */
    public static boolean oneOnOneFight(General attack, City defence) {
        boolean result;
        // 如果没有随身武将，则直接返回0 失败
        if (GeneralFactory.getAoundGeneral(attack.getGenerals()).size() <= 0) {
            return false;
            // 查看城市里面有没有武将 没有武将
        } else if (defence.getDenfenceGenerals().size() <= 0) {
            return true;
        } else {
            System.out.println("进攻方 请选择一个武将来单挑");
            General generalByChoose;
            // 机器人自动选择

            generalByChoose = GeneralFactory.getGeneralByChoose(attack,
                    GeneralFactory.getAoundGeneral(attack.getGenerals()), 3, null);

            assert generalByChoose != null;
            System.out.println(generalByChoose.toString());
            System.out.println("防守方 自动选择武将来单挑");
            General generalByDefenceAuto = GeneralFactory.generalByDefenceAuto(defence);
            System.out.println("出战武将" + generalByDefenceAuto.toString());
            result = fight2(generalByChoose, generalByDefenceAuto);
        }

        return result;

    }

    //单挑判定输赢
    private static boolean fight(General generalByChoose, General generalByDefenceAuto) {
        //1、通过武力、统帅、智力、生命更新 攻 防 气
        return (Integer.parseInt(generalByChoose.getAttack()) * 0.5 + Integer.parseInt(generalByChoose.getVitality()) * 0.3 + Integer.parseInt(generalByChoose.getIntelligence()) * 0.2) >= (Integer.parseInt(generalByDefenceAuto.getAttack()) * 0.5 + Integer.parseInt(generalByDefenceAuto.getVitality()) * 0.3 + Integer.parseInt(generalByDefenceAuto.getIntelligence()) * 0.2);
    }

    //野战
    @SuppressWarnings("resource")
    public static boolean fieldOperationsFight(General general, City defence) {
        //如果守城无将领，则直接认输
        if (defence.getDenfenceGenerals().size() == 0) {
            return true;
        }
        boolean result = false;
        //进入战役地图
        BF = new BattleField();
        try {
            // 1、进攻方挑选将领 主将 副将 军师 野战上阵士兵数1000 如果随身人数不足，则取最大值
            while (BF.getAttackChief() == null) {
                System.out.println("请选择主将");
                General generalChief = GeneralFactory.getGeneralByChoose(general, null, 2, defence);
                if (generalChief == null) {
                    System.out.println("进攻方 主将不能为空，请重新选择");
                } else {
                    BF.setAttackChief(generalChief);
                    generalChief.setCityId(defence.getId());// 暂时放在城里进行交战，交战结束后恢复
                }
            }

            System.out.println("请选择副将");
            General generalVice = GeneralFactory.getGeneralByChoose(general, null, 2, defence);
            if (generalVice != null) {
                generalVice.setCityId(defence.getId());
                BF.setAttackVice(generalVice);
            }

            System.out.println("请选择军师");
            General counsellor = GeneralFactory.getGeneralByChoose(general, null, 2, defence);
            if (counsellor != null) {
                counsellor.setCityId(defence.getId());
                BF.setAttackCounsellor(counsellor);
            }

            // 选择兵种
            System.out.println("请选择兵种 ： 1 普通 2 骑兵 3 枪兵 4 弓兵，当前城市的地形为：" + CityTopographyEnum.getText(defence.getTopography()) + "\n"
                    + "推荐兵种：" + CityTopographyEnum.getMemo(defence.getTopography()));
            Scanner input;
            int type;
            if (general.isReboot()) {
                switch (defence.getTopography()) {
                    case 1:
                        if (general.getCavalrys() > 1000) {
                            type = 2;
                        } else if (general.getInfantry() > 1000) {
                            type = 3;
                        } else if (general.getArchers() > 1000) {
                            type = 4;
                        } else {
                            type = 1;
                        }
                        break;
                    case 2:
                        if (general.getInfantry() > 1000) {
                            type = 3;
                        } else if (general.getArchers() > 1000) {
                            type = 4;
                        } else if (general.getCavalrys() > 1000) {
                            type = 2;
                        } else {
                            type = 1;
                        }
                        break;
                    case 3:
                        if (general.getArchers() > 1000) {
                            type = 4;
                        } else if (general.getInfantry() > 1000) {
                            type = 3;
                        } else if (general.getCavalrys() > 1000) {
                            type = 2;
                        } else {
                            type = 1;
                        }
                        break;
                    default:
                        type = 1;
                }
            } else {
                while (true) {
                    input = new Scanner(System.in);
                    type = input.nextInt();
                    // TODO
                    if (type == 1) {
                        System.out.println("您选择了普通步兵");
                        break;
                    } else if (type == 2) {
                        System.out.println("您选择了骑兵");
                        break;
                    } else if (type == 3) {
                        System.out.println("您选择了枪兵");
                        break;
                    } else if (type == 4) {
                        System.out.println("您选择了弓兵");
                        break;
                    } else {
                        System.out.println("请重新选择");
                    }
                }
            }
            // 2、防守方自动挑选将领 选择统帅最高的作为主将、智力最高的作为军师
            GeneralFactory.chooseFieldGenerals(BF, defence);

            // 3、打吧
            result = BF.fight2(general, defence, type);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //恢复战前的所属关系
            assert BF.getAttackChief() != null;
            BF.getAttackChief().setCityId("");
            if (BF.getAttackVice() != null) {
                BF.getAttackVice().setCityId("");
            }
            if (BF.getAttackCounsellor() != null) {
                BF.getAttackCounsellor().setCityId("");
            }
            //
            defence.getDenfenceGenerals().add(BF.getDefenceChief());
            if (BF.getDefenceCounsellor() != null) {
                defence.getDenfenceGenerals().add(BF.getDefenceCounsellor());
            }
            if (BF.getDefenceVice() != null) {
                defence.getDenfenceGenerals().add(BF.getDefenceVice());
            }
        }
        return result;
    }

    /**
     * @param @param      general
     * @param @param      defence
     * @param @return     设定文件
     * @param siegeWeapon
     * @return boolean    返回类型
     * @throws
     * @Title: attackCity
     * @Description: 攻城方法
     */
    public static boolean attackCity(General general, City defence, SiegeWeapon siegeWeapon) {
        //如果守城无将领，则直接认输
        if (defence.getDenfenceGenerals().size() == 0) {
            return true;
        }
        boolean result = false;
        AttackCity ac = new AttackCity();
        General attackChief = null;
        General defenceChief = null;
        // 选择主将
        // 1、进攻方挑选将领 主将 副将
        try {
            ac.setLeader(general);
            while (ac.getAttackChief() == null) {
                System.out.println("请选择主将");
                attackChief = GeneralFactory.getGeneralByChoose(general, null, 1,null);
                if (attackChief == null) {
                    System.out.println("进攻方 主将不能为空，请重新选择");
                } else {
                    ac.setAttackChief(attackChief);
                    attackChief.setCityId(defence.getId());// 暂时放在城里进行交战，交战结束后恢复
                }
            }
            // 选择副将
            System.out.println("请选择副将");
            General attackCounsellor = GeneralFactory.getGeneralByChoose(general, null, 1,null);
            if (attackCounsellor != null) {
                ac.setAttackCounsellor(attackCounsellor);
                // 暂时放在城里进行交战，交战结束后恢复
                attackCounsellor.setCityId(defence.getId());
            }
            // 选择军师
            System.out.println("请选择军师");
            General attackVice = GeneralFactory.getGeneralByChoose(general, null, 1,null);
            if (attackVice != null) {
                ac.setAttackVice(attackVice);
                // 暂时放在城里进行交战，交战结束后恢复
                attackVice.setCityId(defence.getId());
            }

            // 投入兵力
            if (ac.getLeader().isReboot()) {
                ac.setArchers(ac.getLeader().getArchers());
                ac.setCavalrys(ac.getLeader().getCavalrys());
                ac.setInfantry(ac.getLeader().getInfantry());
                ac.setSoliders(ac.getLeader().getArmy());
            } else {
                System.out.println("是否全军出击 1 是 0 否");
                int choise;
                Scanner input;
                input = new Scanner(System.in);
                choise = input.nextInt();
                if (choise == 1) {
                    ac.setArchers(ac.getLeader().getArchers());
                    ac.setCavalrys(ac.getLeader().getCavalrys());
                    ac.setInfantry(ac.getLeader().getInfantry());
                    ac.setSoliders(ac.getLeader().getArmy());
                } else {
                    while (ac.getSoliders() <= 0) {
                        System.out.println("请选择您要进攻的的士兵数量，当前您存有的士兵数是" + general.getArmy() + "骑兵" + general.getCavalrys() +
                                "枪兵" + general.getInfantry() + "弓箭手" + general.getArchers());
                        System.out.print("当前城市地形是：" + (defence.getTopography() == 1 ? "平原" : defence.getTopography() == 2 ? "山地" : "水道") + "适应兵种  平原 骑>枪>弓  山地 枪>弓>骑   水道 弓>枪>骑  战力分别是 200% 160% 120% 普通兵种战力100%");
                        System.out.println("当前城中的士兵数是" + defence.getSoilders() + "骑兵" + defence.getCavalrys() + "枪兵" + defence.getInfantry() + "弓箭手" + defence.getArchers());

                        // 设置兵力
                        while (true) {
                            System.out.println("选择骑兵数量：");
                            input = new Scanner(System.in);
                            choise = input.nextInt();
                            if (choise < 0 || (choise > general.getCavalrys())) {
                                System.out.println("太小或太大");
                            } else {
                                ac.setCavalrys(choise);
                                System.out.println("骑兵设置完成");
                                break;
                            }
                        }

                        while (true) {
                            System.out.println("选择枪兵数量：");
                            input = new Scanner(System.in);
                            choise = input.nextInt();
                            if (choise < 0 || (choise > general.getInfantry())) {
                                System.out.println("太小或太大");
                            } else {
                                ac.setInfantry(choise);
                                System.out.println("枪兵设置完成");
                                break;
                            }
                        }

                        while (true) {
                            System.out.println("选择弓箭手数量：");
                            input = new Scanner(System.in);
                            choise = input.nextInt();
                            if (choise < 0 || (choise > general.getArchers())) {
                                System.out.println("太小或太大");
                            } else {
                                ac.setArchers(choise);
                                System.out.println("弓箭手设置完成");
                                break;
                            }
                        }

                        while (true) {
                            System.out.println("选择剑士的数量：");
                            input = new Scanner(System.in);
                            choise = input.nextInt();
                            if (choise < 0 || (choise > general.getArmy())) {
                                System.out.println("太小或太大");
                            } else {
                                ac.setSoliders(choise);
                                System.out.println("剑士设置完成");
                                break;
                            }
                        }
                    }
                }
            }

            //防守方主将
            //防守方自动挑选将领 选择统帅最高的作为主将
            GeneralFactory.chooseDefenceCityGenerals(ac, defence);

            ac.setCity(defence);
            //开始攻城
            result = ac.attack(siegeWeapon);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //恢复战前的所属关系
            ac.getAttackChief().setCityId("");
            try {
                Optional.of(ac.getAttackCounsellor()).get().setCityId("");
                Optional.of(ac.getAttackVice()).get().setCityId("");
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return result;
    }

    // 单挑判定输赢
    public static boolean fight2(General generalByChoose, General generalByDefenceAuto) {
        boolean result = true;
        // 1、通过武力、统帅、智力、生命更新 攻 防 命
        OneOnOne o = new OneOnOne(generalByChoose.computeAttack(), generalByChoose.computeDefence(), generalByChoose.computeVitality(), generalByDefenceAuto.computeAttack(), generalByDefenceAuto.computeDefence(), generalByDefenceAuto.computeVitality());

        // 2、如果有技能释放的话 攻击前的技能标为11
        if (SkillFactory.getSkillByID(generalByChoose.getSkill()).getTime() == 11) {
            o = (OneOnOne) SkillFactory.changeBefore(1, 1, generalByChoose, null, o);
        }
        if (SkillFactory.getSkillByID(generalByDefenceAuto.getSkill()).getTime() == 11) {
            o = (OneOnOne) SkillFactory.changeBefore(1, 2, generalByDefenceAuto, null, o);
        }
        // 2、如果有技能释放的话 攻击前的技能标为12
        o.setAttackG(generalByChoose);
        o.setDefenceG(generalByDefenceAuto);

        result = o.fight();
        // 打完之后，进行结算，失败方直接阵亡概率 被俘虏的概率
        PostwarCalculation pc = new PostwarCalculation(generalByChoose, generalByDefenceAuto, result, deadPercent, catchPercent);
        pc.calculation();

        return result;
    }

    // 单挑判定输赢  不计算阵亡和俘虏
    public static boolean fightPure(General generalByChoose, General generalByDefenceAuto, int times, Integer type) {
        boolean result;
        // 1、通过武力、统帅、智力、生命更新 攻 防 命
        OneOnOne o = new OneOnOne(generalByChoose.computeAttack(), generalByChoose.computeDefence(), generalByChoose.computeVitality(), generalByDefenceAuto.computeAttack(), generalByDefenceAuto.computeDefence(), generalByDefenceAuto.computeVitality());

        // 2、如果有技能释放的话 攻击前的技能标为11
        if (SkillFactory.getSkillByID(generalByChoose.getSkill()).getTime() == 11) {
            o = (OneOnOne) SkillFactory.changeBefore(1, 1, generalByChoose, null, o);
        }
        if (SkillFactory.getSkillByID(generalByDefenceAuto.getSkill()).getTime() == 11) {
            o = (OneOnOne) SkillFactory.changeBefore(1, 2, generalByDefenceAuto, null, o);
        }
        // 2、如果有技能释放的话 攻击前的技能标为12
        o.setAttackG(generalByChoose);
        o.setDefenceG(generalByDefenceAuto);

        result = o.fight(times, type);

        return result;
    }

    public static class PostwarCalculation {
        General attack;
        General defence;
        boolean result;
        int deadPercent = 3;
        int catchPercent = 7;

        public PostwarCalculation(General attack, General defence, boolean result, int deadPercent, int catchPercent) {
            super();
            this.attack = attack;
            this.defence = defence;
            this.result = result;
            this.deadPercent = deadPercent;
            this.catchPercent = catchPercent;
        }

        // 计算
        public void calculation() {
            // 技能判定 比如血路 不被俘虏 俘虏概率降为0  比如抓捕 俘虏概率升为50% 先判定赢得一方  再判定输的一方
            // 太麻烦 先不写
            SkillFactory.changeAfter(5, 3, this.attack, this.defence, this);

            // 判定是否阵亡
            if (new Random().nextInt(100) < deadPercent) {
                if (result) {
                    System.err.println(defence.getName() + "不幸阵亡");
                    // 阵亡调用方法
                    GeneralFactory.dead(defence);
                } else {
                    System.err.println(attack.getName() + "不幸阵亡");
                    // 阵亡调用方法
                    GeneralFactory.dead(attack);
                }
                // 判定是否俘虏
            } else if (new Random().nextInt(100) < catchPercent) {
                if (result) {
                    System.err.println(defence.getName() + "被俘虏");
                    // 俘虏调用方法
                    City city = CityFactory.getCityById(defence.getCityId());
                    GeneralFactory.beCatch(defence, attack);
                    // 所属城市去除被俘虏的武将
                    CityFactory.removeGeneral(defence, city);
                } else {
                    System.err.println(attack.getName() + "被俘虏");
                    // 俘虏调用方法
                    GeneralFactory.beCatch(attack, defence);
                }

            } else {
                System.out.println("各自安全回归阵营");
            }

        }
    }

}
