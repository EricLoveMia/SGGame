package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.po.General;

import java.util.Random;
import java.util.Scanner;

public class OneOnOne {
	private General attackG;
	private General defenceG;
	
	
	private int attackA;
	private int defenceA;
	private int strengthA;
	private int vitalityA;
	
	private int attackB;
	private int defenceB;
	private int strengthB;
	private int vitalityB;
	
	private int attackAFactor = 100;  // 进攻因子
	private int attackBFactor = 100;   // 
	private int defenceAFactor = 100;  // 防守因子
	private int defenceBFactor = 100;  //
	
	public int getAttackAFactor() {
		return attackAFactor;
	}

	public void setAttackAFactor(int attackAFactor) {
		this.attackAFactor = attackAFactor;
	}

	public int getAttackBFactor() {
		return attackBFactor;
	}

	public void setAttackBFactor(int attackBFactor) {
		this.attackBFactor = attackBFactor;
	}

	public int getDefenceAFactor() {
		return defenceAFactor;
	}

	public void setDefenceAFactor(int defenceAFactor) {
		this.defenceAFactor = defenceAFactor;
	}

	public int getDefenceBFactor() {
		return defenceBFactor;
	}

	public void setDefenceBFactor(int defenceBFactor) {
		this.defenceBFactor = defenceBFactor;
	}

	public General getAttackG() {
		return attackG;
	}

	public void setAttackG(General attackG) {
		this.attackG = attackG;
	}

	public General getDefenceG() {
		return defenceG;
	}

	public void setDefenceG(General defenceG) {
		this.defenceG = defenceG;
	}

	public int getAttackA() {
		return attackA;
	}

	public void setAttackA(int attackA) {
		this.attackA = attackA;
	}

	public int getDefenceA() {
		return defenceA;
	}

	public void setDefenceA(int defenceA) {
		this.defenceA = defenceA;
	}

	public int getStrengthA() {
		return strengthA;
	}

	public void setStrengthA(int strengthA) {
		this.strengthA = strengthA;
	}

	public int getVitalityA() {
		return vitalityA;
	}

	public void setVitalityA(int vitalityA) {
		this.vitalityA = vitalityA;
	}

	public int getAttackB() {
		return attackB;
	}

	public void setAttackB(int attackB) {
		this.attackB = attackB;
	}

	public int getDefenceB() {
		return defenceB;
	}

	public void setDefenceB(int defenceB) {
		this.defenceB = defenceB;
	}

	public int getStrengthB() {
		return strengthB;
	}

	public void setStrengthB(int strengthB) {
		this.strengthB = strengthB;
	}

	public int getVitalityB() {
		return vitalityB;
	}

	public void setVitalityB(int vitalityB) {
		this.vitalityB = vitalityB;
	}

	public OneOnOne(){
		
	}
	
	public OneOnOne(int attackA, int defenceA, int vitalityA, int attackB, int defenceB, int vitalityB) {
		this.attackA = attackA;
		this.defenceA = defenceA;
		this.vitalityA = vitalityA;
		this.strengthA = 0;
		this.attackB = attackB;
		this.defenceB = defenceB;
		this.vitalityB = vitalityB;
		this.strengthB = 0;
		
	}

    public boolean fight(int times) {
        int c1 = 0; // A的选择
        int c2 = 0; // B的选择

        //一方生命值低于0 结束
        while (vitalityA > 0 && vitalityB > 0 && times > 0) {
            System.out.println("剩余" + times-- + "个回合");
            // 进攻方先选
            if (strengthA >= 100) {
                while (true) {
                    if (GeneralFactory.getGeneralById(getAttackG().getBelongTo()).isReboot()) {
                        c1 = 4;
                        break;
                    } else {
                        System.out.println("请选择 1、进攻 2、防御 3、集气 4、奋力一击");
                        Scanner input = new Scanner(System.in);
                        int choise = input.nextInt();
                        if (choise <= 0 || choise > 4) {
                            System.out.println("请输入1-4的数字");
                        } else {
                            c1 = choise;
                            break;
                        }
                    }
                }
            } else {
                while (true) {
                    if (GeneralFactory.getGeneralById(getAttackG().getBelongTo()).isReboot()) {
                        c1 = new Random().nextInt(3) + 1;
                        break;
                    } else {
                        System.out.println("请选择 1、进攻 2、防御 3、集气");
                        Scanner input = new Scanner(System.in);
                        int choise = input.nextInt();
                        if (choise <= 0 || choise > 3) {
                            System.out.println("请输入1-3的数字");
                        } else {
                            c1 = choise;
                            break;
                        }
                    }
                }
            }

            // 防守方自动选
            if (strengthB >= 100) {
                c2 = 4;
            } else {
                c2 = 1;
            }

            //根据不同的情况 计算战果
            if (c1 == 1 && c2 == 1) {
                //双方进攻
                System.out.println("进攻方 进攻");
                System.out.println("防守方 进攻");

                //根据技能重新获得损失的血量 以及攻击力和防御力
                resetFD((int) (attackA - defenceB * 0.5) / 10 * 3, (int) (attackB - defenceA * 0.5) / 10 * 3, 1, 1);

                vitalityB = vitalityB - FD.defenceLostHealth;
                strengthB = strengthB + 20;
                vitalityA = vitalityA - FD.attackLostHealth;
                strengthA = strengthA + 20;
            } else if (c1 == 2 && c2 == 1) {
                //攻防
                System.out.println("进攻方 防守 防守方 进攻");
                //根据技能重新获得损失的血量
                resetFD(0, (int) (attackB - defenceA * 0.65) / 10 * 3, 2, 1);
                vitalityA = vitalityA - FD.attackLostHealth;
                strengthA = strengthA + 40;
            } else if (c1 == 3 && c2 == 1) {
                //气 攻
                System.out.println("进攻方 集气 防守方 进攻");
                //根据技能重新获得损失的血量
                resetFD(0, (int) (attackB - defenceA * 0.5) / 10 * 3, 3, 1);

                vitalityA = vitalityA - FD.attackLostHealth;
                strengthA = strengthA + 50;
            } else if (c1 == 1) {
                //攻 大招
                System.out.println("进攻方 进攻");
                System.out.println("防守方 大招");

                //根据技能重新获得损失的血量
                resetFD((int) (attackA - defenceB * 0.5) / 10 * 3, (int) (attackB - defenceA * 0.5) / 10 * 5, 1, 4);

                vitalityB = vitalityB - FD.defenceLostHealth;
                //strengthB = strengthB + 20;
                vitalityA = vitalityA - FD.attackLostHealth;
                strengthA = strengthA + 30;
                strengthB = 0;
            } else if (c1 == 2) {
                //防 大招
                System.out.println("进攻方 防守");
                System.out.println("防守方 大招");

                //根据技能重新获得损失的血量
                resetFD(0, (int) (attackB - defenceA * 0.65) / 10 * 5, 2, 4);

                vitalityA = vitalityA - FD.attackLostHealth;
                strengthA = strengthA + 30;
                strengthB = 0;
            } else if (c1 == 3) {
                //防 大招
                System.out.println("进攻方 集气");
                System.out.println("防守方 大招");
                //根据技能重新获得损失的血量
                resetFD(0, (int) (attackB - defenceA * 0.5) / 10 * 5, 3, 4);

                vitalityA = vitalityA - FD.attackLostHealth;
                strengthA = strengthA + 70;
                strengthB = 0;
            } else if (c1 == 4 && c2 == 4) {
                //防 大招
                System.out.println("进攻方 大招");
                System.out.println("防守方 大招");

                //根据技能重新获得损失的血量
                resetFD((int) (attackA - defenceB * 0.5) / 10 * 5, (int) (attackB - defenceA * 0.5) / 10 * 5, 3, 4);

                vitalityB = vitalityB - FD.defenceLostHealth;
                //strengthB = strengthB + 30;
                vitalityA = vitalityA - FD.attackLostHealth;
                //strengthA = strengthA + 30;

                strengthA = 0;
                strengthB = 0;
            } else if (c1 == 4) {
                //防 大招
                System.out.println("进攻方 大招");
                System.out.println("防守方 进攻");

                //根据技能重新获得损失的血量
                resetFD((int) (attackA - defenceB * 0.5) / 10 * 5, (int) (attackB - defenceA * 0.5) / 10 * 3, 4, 1);

                vitalityB = vitalityB - FD.defenceLostHealth;
                strengthB = strengthB + 30;
                vitalityA = vitalityA - FD.attackLostHealth;
                //strengthA = strengthA + 20;
                strengthA = 0;
            }

            System.out.println(toString());
            // 是否有回合结束触发的技能
            if ((SkillFactory.getSkillByID(attackG.getSkill()).getTime() == 13) || (SkillFactory.getSkillByID(defenceG.getSkill()).getTime() == 13)) {
                SkillFactory.changeAfter(1, 3, attackG, defenceG, this);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return vitalityA > 0;
    }

    public static class FightData {
		FightData(int attackLostHealth,int defenceLostHealt){
			this.attackLostHealth = attackLostHealth;
			this.defenceLostHealth = defenceLostHealth;
		}
		int attackLostHealth = 0;
		int defenceLostHealth = 0;
		int attackType = 1; //1 普通攻击 2 防御 3 集气 4 大招
		int defenceType = 1;
	}
	
	public static FightData FD = new FightData(0,0);
	
	// 两个开打
	public boolean fight(){
		int c1 = 0; // A的选择
		int c2 = 0; // B的选择

		//一方生命值低于0 结束
		while(vitalityA>0 && vitalityB>0 ){
			// 进攻方先选
			if(strengthA >= 100){
				while(true){
					if(GeneralFactory.getGeneralById(getAttackG().getBelongTo()).isReboot()){
						c1 = 4;
						break;
					}else {
						System.out.println("请选择 1、进攻 2、防御 3、集气 4、奋力一击");
						Scanner input = new Scanner(System.in);
						int choise = input.nextInt();
						if (choise <= 0 || choise > 4) {
							System.out.println("请输入1-4的数字");
						} else {
							c1 = choise;
							break;
						}
					}
				}
			}else{
				while(true){
					if(GeneralFactory.getGeneralById(getAttackG().getBelongTo()).isReboot()){
						c1 = new Random().nextInt(3) + 1;
						break;
					}else {
						System.out.println("请选择 1、进攻 2、防御 3、集气");
						Scanner input = new Scanner(System.in);
						int choise = input.nextInt();
						if (choise <= 0 || choise > 3) {
							System.out.println("请输入1-3的数字");
						} else {
							c1 = choise;
							break;
						}
					}
				}					
			}
			
			// 防守方自动选
			if(strengthB>=100){
				c2 = 4;
			}else if(vitalityA <= (int) (attackB - defenceA*0.5)/10 * 3){
				c2 = 1;
			}else if(c1 == 4){
				c2 = 2;
			}else{	
				c2 =  new Random().nextInt(3) + 1;
			}
			
			//根据不同的情况 计算战果
			if(c1 == 1 && c2 == 1){
				//双方进攻
				System.out.println("进攻方 进攻");
				System.out.println("防守方 进攻");
                
				//根据技能重新获得损失的血量 以及攻击力和防御力
				resetFD((int) (attackA - defenceB*0.5)/10 * 3,(int) (attackB - defenceA*0.5)/10 * 3,1,1);
				
				vitalityB = vitalityB - FD.defenceLostHealth;
				strengthB = strengthB + 20;
				vitalityA = vitalityA - FD.attackLostHealth;
				strengthA = strengthA + 20;
			}else if(c1 == 1 && c2 == 2){
				//攻防
				System.out.println("进攻方 进攻 防守方 防守");
								
				//根据技能重新获得损失的血量
				resetFD((int) (attackA - defenceB*0.65)/10 * 3,0,1,2);
				vitalityB = vitalityB - FD.defenceLostHealth;				
				strengthB = strengthB + 30;
			}else if(c1 == 2 && c2 == 1){
				//攻防
				System.out.println("进攻方 防守 防守方 进攻");
				//根据技能重新获得损失的血量
				resetFD(0,(int) (attackB - defenceA*0.65)/10 * 3,2,1);
				vitalityA = vitalityA - FD.attackLostHealth;
				strengthA = strengthA + 40;
			}else if(c1 == 2 && c2 == 2){
				System.out.println("进攻方 防守 防守方 防守");
			}else if(c1 == 1 && c2 == 3){
				//攻 气
				System.out.println("进攻方 进攻 防守方 集气");
				//根据技能重新获得损失的血量
				resetFD((int) (attackA - defenceB*0.5)/10 * 3,0,1,3);
				vitalityB = vitalityB - FD.defenceLostHealth;
				strengthB = strengthB + 50;					
			}else if(c1 == 3 && c2 == 1){
				//气 攻
				System.out.println("进攻方 集气 防守方 进攻");
				//根据技能重新获得损失的血量
				resetFD(0,(int) (attackB - defenceA*0.5)/10 * 3,3,1);
				
				vitalityA = vitalityA - FD.attackLostHealth;
				strengthA = strengthA + 50;					
			}else if(c1 == 2 && c2 == 3){
				//防 气
				System.out.println("进攻方 防守 防守方 集气");					
				strengthB = strengthB + 50;					
			}else if(c1 == 3 && c2 == 2){
				//防 气
				System.out.println("进攻方 集气 防守方 防守");					
				strengthA = strengthA + 50;					
			}else if(c1 == 1 && c2 == 4){
				//攻 大招
				System.out.println("进攻方 进攻");
				System.out.println("防守方 大招");

				//根据技能重新获得损失的血量
				resetFD((int) (attackA - defenceB*0.5)/10 * 3,(int) (attackB - defenceA*0.5)/10 * 5,1,4);
				
				vitalityB = vitalityB - FD.defenceLostHealth;
				//strengthB = strengthB + 20;
				vitalityA = vitalityA - FD.attackLostHealth;
				strengthA = strengthA + 30;
				strengthB = 0;
			}else if(c1 == 2 && c2 == 4){
				//防 大招
				System.out.println("进攻方 防守");
				System.out.println("防守方 大招");
				
				//根据技能重新获得损失的血量
				resetFD(0,(int) (attackB - defenceA*0.65)/10 * 5,2,4);
				
				vitalityA = vitalityA - FD.attackLostHealth;
				strengthA = strengthA + 30;
				strengthB = 0;
			}else if(c1 == 3 && c2 == 4){
				//防 大招
				System.out.println("进攻方 集气");
				System.out.println("防守方 大招");
				//根据技能重新获得损失的血量
				resetFD(0,(int) (attackB - defenceA*0.5)/10 * 5,3,4);
				
				vitalityA = vitalityA - FD.attackLostHealth;
				strengthA = strengthA + 70;
				strengthB = 0;
			}else if(c1 == 4 && c2 == 4){
				//防 大招
				System.out.println("进攻方 大招");
				System.out.println("防守方 大招");

				//根据技能重新获得损失的血量
				resetFD((int) (attackA - defenceB*0.5)/10 * 5,(int) (attackB - defenceA*0.5)/10 * 5,3,4);
				
				vitalityB = vitalityB - FD.defenceLostHealth;
				//strengthB = strengthB + 30;
				vitalityA = vitalityA - FD.attackLostHealth;
				//strengthA = strengthA + 30;
				
				strengthA = 0;
				strengthB = 0;
			}else if(c1 == 4 && c2 == 1){
				//防 大招
				System.out.println("进攻方 大招");
				System.out.println("防守方 进攻");

				//根据技能重新获得损失的血量
				resetFD((int) (attackA - defenceB*0.5)/10 * 5,(int) (attackB - defenceA*0.5)/10 * 3,4,1);
				
				vitalityB = vitalityB - FD.defenceLostHealth;
				strengthB = strengthB + 30;
				vitalityA = vitalityA - FD.attackLostHealth;
				//strengthA = strengthA + 20;
				strengthA = 0;
			}else if(c1 == 4 && c2 == 2){
				//防 大招
				System.out.println("进攻方 大招");
				System.out.println("防守方 防御");
				
				//根据技能重新获得损失的血量
				resetFD((int) (attackA - defenceB*0.65)/10 * 5,0,4,2);
				
				vitalityB = vitalityB - FD.defenceLostHealth;
				strengthB = strengthB + 30;
				strengthA = 0;
			}else if(c1 == 4 && c2 == 3){
				//防 大招
				System.out.println("进攻方 大招");
				System.out.println("防守方 集气");
				
				//根据技能重新获得损失的血量
				resetFD((int) (attackA - defenceB*0.5)/10 * 5,0,4,3);
				
				vitalityB = vitalityB - FD.defenceLostHealth;
				strengthB = strengthB + 70;
				strengthA = 0;
			}
			
			System.out.println(toString());
			// 是否有回合结束触发的技能
			if( (SkillFactory.getSkillByID(attackG.getSkill()).getTime() == 13) || (SkillFactory.getSkillByID(defenceG.getSkill()).getTime() == 13)){
				SkillFactory.changeAfter(1,3,attackG,defenceG,this);				
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

        return vitalityA > vitalityB;
    }

	/**
	 * 
	* @Title: resetFD
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param attackLostHealth 攻击方损失的HP
	* @param @param defenceLostHealth 防守方损失的HP
	* @param @param attackType 攻击方类型  1进攻 2技能 3防守 4集气
	* @param @param defenceType 防守方类型
	* @param @return    设定文件
	* @return FightData    返回类型
	* @throws
	 */
	private void resetFD(int defenceLostHealth, int attackLostHealth, int attackType,  int defenceType) {
		FD.attackLostHealth = attackLostHealth;
		FD.defenceLostHealth = defenceLostHealth;
		FD.attackType = attackType;
		FD.defenceType = defenceType;
		//2、如果有技能释放的话 攻击前的技能标为12
		if(SkillFactory.getSkillByID(attackG.getSkill()).getTime() == 12 || SkillFactory.getSkillByID(defenceG.getSkill()).getTime() == 12){
			SkillFactory.changeMiddle(1,3,attackG,defenceG,FD);
		}
	}

	@Override
	public String toString() {
		return "单挑 [进攻方：attackA=" + attackA + ", defenceA=" + defenceA + ", strengthA=" + strengthA
				+ ", vitalityA=" + vitalityA + ", 防御方：attackB=" + attackB + ", defenceB=" + defenceB + ", strengthB="
				+ strengthB + ", vitalityB=" + vitalityB + "]";
	}
	
	
}