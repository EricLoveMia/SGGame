package cn.eric.game.fujiatianxia6.service;

import java.util.*;
import java.util.function.Function;

import cn.eric.game.fujiatianxia6.factory.*;
import cn.eric.game.fujiatianxia6.po.*;
import cn.eric.game.fujiatianxia6.po.Map;

public class Game {
    Map map;  //地图
    int playerPos1; //对战中玩家1的当前位置
    int playerPos2; //对战中玩家2的当前位置
    String[] goAndStop = new String[2];   //走或停标识设置
    General[] players = new General[2];  //对战角色

    /**
     * 初始化游戏的一局
     */
    public void init() {
        map = new Map();
        map.createMap();  //生成地图
        playerPos1 = 0;   //设置玩家1起始位置
        playerPos2 = 50;   //设置玩家2起始位置
        goAndStop[0] = "on";  //记录玩家1下一次走或停
        goAndStop[1] = "on";  //设置玩家2下一次走或停
    }

    /**
     * 初始化武将库  初始化君主携带武将 金钱  兵力
     */
    public void initData() {

    }

    /**
     * 开始游戏
     */
    public void start() {
        //初始化
        init();
        System.out.println("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※");
        System.out.println("//                                                //");
        System.out.println("//                                                //");
        System.out.println("//                富甲天下6                         //");
        System.out.println("//                                                //");
        System.out.println("//                                                //");
        System.out.println("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※\n\n\n");


        System.out.println("\n~~~~~~~~~~~~~~~~~~~两  人  对  战~~~~~~~~~~~~~~~~~~~");
        System.out.println("\n请选择角色: 1. 刘备 容易收服武将，诸葛亮bug  \n " + "2. 曹操 野战单挑都厉害  \n 3. 孙权 水战无敌，周瑜bug  \n 4. 董卓 三国第一武将在手，单挑无敌，群雄归附初始兵钱加倍 \n");
        Scanner input = new Scanner(System.in);
        System.out.print("请玩家1选择角色:  ");
        int role1 = input.nextInt();
        int role2;
        do {
            System.out.print("请玩家2选择角色： ");
            role2 = input.nextInt();  //双方选择角色代号
        } while (role2 == role1);  //不允许角色重复
        setRole(1, role1);   //设置玩家1代表的角色
        setRole(2, role2);   //设置玩家2代表的角色
        initGeneralResources(role1, role2);
        play();   //开始两人对战
    }

    /**
     * 设置对战角色
     *
     * @param no   玩家次序 1：玩家1 2：玩家2
     * @param role 角色代号
     */
    public void setRole(int no, int role) {
        switch (role) {
            case 1:
                players[no - 1] = GeneralFactory.getGeneral("刘备");
                break;
            case 2:
                players[no - 1] = GeneralFactory.getGeneral("曹操");
                break;
            case 3:
                players[no - 1] = GeneralFactory.getGeneral("孙权");
                break;
            case 4:
                players[no - 1] = GeneralFactory.getGeneral("董卓");
                break;
            default:
                break;
        }
    }

    /**
     * 两人对战玩法
     */
    public void play() {
        System.out.println("\n\n\n\n");

        System.out.print("\n\n****************************************************\n");
        System.out.print("                     Game  Start                    \n");
        System.out.print("****************************************************\n\n");

        //显示对战双方士兵样式
        System.out.println("^_^" + players[0].getName() + "：　A");
        System.out.println("^_^" + players[1].getName() + "：  B\n");

        //显示对战地图
        System.out.println("\n图例： " + "☁ 暂停  ◎幸运轮盘  ♚酒馆  ♞募兵  ♙空城  ♔ " + players[0].getName() + "的城池 ♗ " + players[1].getName() + "的城池\n");

        map.showMap(playerPos1, playerPos2);
        map.initCity();
        //游戏开始
        int step;  //存储骰子数目
        System.out.println(players[0].getMoney() + " " + players[1].getMoney());
        while (players[0].getMoney() > 0 && players[1].getMoney() > 0) {    //有任何一方的钱少于0
            //轮流掷骰子
            if (goAndStop[0].equals("on")) {
                //玩家1掷骰子
                step = throwShifter(1);   //掷骰子
                System.out.println("\n-----------------");  //显示结果信息
                System.out.println("骰子数： " + step);
                if (playerPos1 + step < 0) {
                    playerPos1 = 0;
                } else if (playerPos1 + step > 99) { //如果大于99格，则表示已经走完一圈，要重新计算
                    playerPos1 = playerPos1 + step - 100;
                } else {
                    playerPos1 = playerPos1 + step;
                }
                map.showMap(playerPos1, playerPos2); //显示当前地图
                playerPos1 = getCurPos(1, playerPos1, step);   //计算这一次移动后的当前位置
                System.out.println("\n您当前位置：  " + playerPos1);
                System.out.println("对方当前位置：" + playerPos2);
                System.out.println("-----------------\n");
//        		  if(playerPos1 == 99){  //如果走到终点
//            		  break;   //退出
//            	  }
            } else {
                System.out.println("\n" + players[0].getName() + "停掷一次！\n");   //显示此次暂停信息
                goAndStop[0] = "on";   //设置下次可掷状态
            }

            System.out.println("\n\n\n\n");

            if (goAndStop[1].equals("on")) {
                //玩家2掷骰子
                step = throwShifter(2); //掷骰子
                System.out.println("\n-----------------"); //显示结果信息
                System.out.println("骰子数： " + step);

                if (playerPos2 + step < 0) {
                    playerPos2 = 0;
                } else if (playerPos2 + step > 99) {
                    playerPos2 = playerPos2 + step - 100;
                } else {
                    playerPos2 = playerPos2 + step;
                }
                map.showMap(playerPos1, playerPos2);

                playerPos2 = getCurPos(2, playerPos2, step);   //计算这一次移动后的当前位置
                System.out.println("\n您当前位置：  " + playerPos2);
                System.out.println("对方当前位置：" + playerPos1);
                System.out.println("-----------------\n");
//        		  if(playerPos2 == 99){  //如果走到终点
//            		  break;   //退出
//            	  }
            } else {
                System.out.println("\n" + players[1].getName() + "停掷一次！\n");  //显示此次暂停信息
                goAndStop[1] = "on";  //设置下次可掷状态
            }

            System.out.println("\n\n\n\n");
            //各城市开始计算收益，包括钱 兵 武器
            //金钱收益  当前城市金钱 * 繁华指数 * 太守的政治
            CityFactory.computeMoney();
            //兵增加 暂时不增加，后续增加城市内建筑时，如有徽兵所时会缓慢增加
            CityFactory.computeSoilders();
            //武器增加 暂时不增加 如城市内有兵工厂会缓慢增加
            // 根据建筑检查增加骑兵和枪兵 弓兵
            BuildingFactory.computeHorse();
            BuildingFactory.computeInAndAr();
            // 研究队列 都减1 如果到0 就从研究队列中去除
            ResearchService.researchAll();
            //查看每回合结束后触发技能的武将是否触发 例如制衡
            SkillFactory.changeAfter(9, 0, players[0], null, null);
            SkillFactory.changeAfter(9, 0, players[1], null, null);

            //TODO
            //查看
        }

        //游戏结束
        System.out.println("\n\n\n\n");
        System.out.print("****************************************************\n");
        System.out.print("                      Game  Over                    \n");
        System.out.print("****************************************************\n\n");
        //judge();
    }


    /**
     * 掷骰子
     *
     * @param no 玩家次序
     * @return step 掷出的骰子数目
     */
    public int throwShifter(int no) {
        int step = 0;
        System.out.print("\n\n" + players[no - 1].getName() + ", 请您按任意字母键后回车启动掷骰子： ");
        if(players[no - 1].isReboot()){
            return (int) (Math.random() * 10) % 6 + 1;
        }
        Scanner input = new Scanner(System.in);
        String answer = input.next();
        step = (int) (Math.random() * 10) % 6 + 1;   //产生一个1~6的数字,即掷的骰子数目
        return step;
    }

    /**
     * 计算玩家此次移动后的当前位置
     *
     * @param no       玩家次序
     * @param position 移动前位置
     * @param step     掷的骰子数目
     * @return position 移动后的位置
     */
    public int getCurPos(int no, int position, int step) {
//    	  position = position + step;  //第一次移动后的位置
//    	  if(position >= 99){
//    		  return 99;
//    	  }
        Scanner input = new Scanner(System.in);
        int base = no * 10 + 1;
        switch (map.map[position]) {   //根据地图中的关卡代号进行判断
            case 0:    //走到普通格
                City city = (City) map.mapObj[position];
                System.out.println(city.toString());
                System.out.println("进入" + city.getName() + "的领地，购买费用" + city.getPurchase() + "金币，请问是否购买");
                Scanner input2 = new Scanner(System.in);
                System.out.println(" 1 毫不犹豫买  2 日子都活不了了，买啥买");
                int choise = 2;
                if(players[no - 1].isReboot()) {
                    if(players[no - 1].getMoney() > city.getPurchase()
                            && (players[no - 1].getArmy() > 4000)
                            && GeneralFactory.getaoundGeneral(players[no - 1].getGenerals()).size() > 2) {
                        choise = 1;
                    }
                }else {
                    choise = input.nextInt();
                }
                if (choise == 1) {
                    if ((players[no - 1].getMoney() - city.getPurchase() >= 0)) {
                        System.out.println("购买成功");
                        players[no - 1].setMoney(players[no - 1].getMoney() - city.getPurchase());
                        getCity(position, base, city);
                        // 选择武将及放置的兵力
                        chooseDefenceGeneralAndSoilders(city, players[no - 1]);
                    } else {
                        System.out.println("钱不够");
                    }
                } else {
                    System.out.println("现在不买，以后更买不起了");
                }
                break;
            case 1:   //幸运轮盘
                System.out.println("\n◆◇◆◇◆欢迎进入幸运轮盘◆◇◆◇◆");
                //Scanner inputLucky = new Scanner(System.in);
                System.out.println("   1. 立即获得一个武将  2. 获得2000兵  3.随机给自己的一个城市升级，如果满级则失效  4.增加2000块钱  5.随机获得500非剑兵  6.给你鼓鼓掌，祝你一统天下");
                int lucky = (int) (Math.random() * 10) % 6 + 1;
                switch (lucky) {
                    case 1:
                        System.out.println("立即获得一个武将");
                        Tavern.getGeneralFromTavern(players[no - 1], 1);
                        break;
                    case 2:
                        System.out.println("获得2000兵");
                        players[no - 1].setArmy(players[no - 1].getArmy() + 2000);
                        break;
                    case 3:
                        System.out.println("随机给自己的一个城市升级，如果满级则失效");
                        CityFactory.updateCityRandom(players[no - 1]);
                        break;
                    case 4:
                        System.out.println("增加2000块钱");
                        players[no - 1].setMoney(players[no - 1].getMoney() + 2000);
                        break;
                    case 5:
                        int type = new Random().nextInt(3);
                        switch (type){
                            case 0:
                                System.out.printf("恭喜获得500骑兵");
                                players[no - 1].setCavalrys(players[no - 1].getCavalrys() + 500);
                                break;
                            case 1:
                                System.out.printf("恭喜获得500枪兵");
                                players[no - 1].setInfantry(players[no - 1].getInfantry() + 500);
                                break;
                            case 2:
                                System.out.printf("恭喜获得500弓兵");
                                players[no - 1].setArchers(players[no - 1].getArchers() + 500);
                                break;
                            default:
                                System.out.printf("恭喜获得500枪兵");
                                players[no - 1].setInfantry(players[no - 1].getInfantry() + 500);
                                break;
                        }
                        //break;
                    case 6:
                        System.out.println("恭喜你，可以购买一个专属武器");
                        WeaponFactory.purchaseWeapon(players[no - 1]);
                        break;
                    default:
                        break;
                }

                System.out.println("=============================\n");
                System.out.println(":~)  " + "幸福的我都要哭了...");
                break;
            case 2:   //酒馆
                List<General> wineGenerals = Tavern.getGenerals(players[no - 1].getId());
                System.out.println("~:-(  " + "进入酒馆，请选择您要邀请的武将...");
                General generalByChoose = GeneralFactory.getGeneralByChoose(players[no - 1], wineGenerals,0);
                if (generalByChoose != null) {
                    players[no - 1].getGenerals().add(generalByChoose);
                    generalByChoose.setBelongTo(players[no - 1].getId());
                    System.out.println(generalByChoose.getName() + "拜入" + players[no - 1].getName() + "账下");
                }
                break;
            case 3:  //下一次暂停一次
                goAndStop[no - 1] = "off";  //设置下次暂停掷骰子
                System.out.println("~~>_<~~  要停战一局了。");
                break;
            case 4:   //募兵
                while (true) {
                    // Scanner inputBuySoilder = new Scanner(System.in);
                    System.out.println("|-P  " + "请选择您要购买的兵力数量，一个兵一个金币");
                    int choiseBuySoilder = 0;
                    if (players[no - 1].isReboot()) {
                        if(players[no - 1].getArmy() < 1000){
                            choiseBuySoilder = players[no - 1].getMoney() / 2;
                        }else {
                            choiseBuySoilder = players[no - 1].getMoney() / 5;
                        }
                    } else {
                        choiseBuySoilder = input.nextInt();
                    }
                    if ((players[no - 1].getMoney() - choiseBuySoilder < 0)) {
                        System.out.println("您没有这么多钱");
                    } else {
                        if (choiseBuySoilder > 3000) {
                            System.out.println("您补充了" + choiseBuySoilder + "兵力，一统天下指日可待");
                        } else {
                            System.out.println("您补充了" + choiseBuySoilder + "兵力，想一桶天下 做梦吧");
                        }
                        players[no - 1].setMoney(players[no - 1].getMoney() - choiseBuySoilder);
                        players[no - 1].setArmy(players[no - 1].getArmy() + choiseBuySoilder);
                        break;
                    }
                }
                break;
            case 5:   //开始位置
                System.out.println("|-P  " + "站在开始位置，金钱加2000，抽取武将1名");
                players[no - 1].setMoney(players[no - 1].getMoney() + 2000);
                Tavern.getGeneralFromTavern(players[no - 1], 1);
                break;
            case 6:   //结束位置
                System.out.println("|-P  " + "站在结束位置，金钱加3000，抽取武将2名");
                players[no - 1].setMoney(players[no - 1].getMoney() + 3000);
                Tavern.getGeneralFromTavern(players[no - 1], 2);
                break;
            default:
                City defence = (City) map.mapObj[position];
                System.out.println(defence.toString());
                System.out.println(defence.getDenfenceGenerals().toString());
                if (base == map.map[position]) {
                    System.out.println("======主公好====== 选择0 放弃增减武将和兵力金钱\n");
                    // TODO  选择武将及放置的兵力

                    chooseDefenceGeneralAndSoilders(defence, players[no - 1]);
                    // 升级建筑
                    BuildingFactory.upgradedBuild(defence, players[no - 1]);
                    try {
                        BuildingFactory.buildInCity(defence, players[no - 1]);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                } else {
                    int passMoney = (int) (defence.getMoney() * 0.1);

                    System.out.println("======快交过路费：" + passMoney + "\n");
                    System.out.println("请选择：1、野战 2、单挑 3、攻城 4、乖乖交钱\n");
                    int choiseBuySoilder;
                    if (players[no - 1].isReboot()) {
                        // 机器人自动选择
                        int defencCount = Optional.ofNullable(defence.getSoilders()).orElse(0) + Optional.ofNullable(defence.getArchers()).orElse(0) + Optional.ofNullable(defence.getInfantry()).orElse(0) + Optional.ofNullable(defence.getCavalrys()).orElse(0);
                        int attCount = Optional.ofNullable(players[no - 1].getArmy()).orElse(0) + Optional.ofNullable(players[no - 1].getArchers()).orElse(0) + Optional.ofNullable(players[no - 1].getInfantry()).orElse(0) + Optional.ofNullable(players[no - 1].getCavalrys()).orElse(0);

                        if (attCount > defencCount * 5) {
                            choiseBuySoilder = 3;
                        } else {
                            // 如果人数少于防守方，只选择单挑,二十几率交钱
                            if (attCount < defencCount) {
                                if (new Random().nextInt(100) <= 20) {
                                    choiseBuySoilder = 4;
                                } else {
                                    choiseBuySoilder = 2;
                                }
                            } else {
                                // 如果人数大于防守方，又不够攻城，默认野战 二十几率交钱
                                if (new Random().nextInt(100) <= 20) {
                                    choiseBuySoilder = 4;
                                } else {
                                    choiseBuySoilder = 1;
                                }
                            }
                        }
                    } else {
                        choiseBuySoilder = input.nextInt();
                        while (choiseBuySoilder > 4 || choiseBuySoilder <= 0) {
                            System.out.println("输入错误 请选择：1、野战 2、单挑 3、攻城 4、乖乖交钱\n");
                            choiseBuySoilder = input.nextInt();
                        }
                    }
                    switch (choiseBuySoilder) {
                        case 1:
                            System.out.println("野战开始");
                            boolean resultField = Fight.fieldOperationsFight(players[no - 1], defence);
                            if (resultField) {
                                System.out.println("您胜利了，不需要交过路费");
                            } else {
                                System.out.println("您失败了，要交双倍过路费:" + passMoney * 2);
                                doublePay(no, passMoney);
                            }
                            break;
                        case 2:
                            System.out.println("单挑开始");
                            boolean result = Fight.oneOnOneFight(players[no - 1], defence);
                            if (result) {
                                System.out.println("您胜利了，不需要交过路费");
                            } else {
                                System.out.println("您失败了，要交双倍过路费:" + passMoney * 2);
                                doublePay(no, passMoney);
                            }
                            break;
                        case 3:
                            System.out.println("攻城开始");
                            boolean attackCityResult = Fight.attackCity(players[no - 1], defence);
                            if (attackCityResult) {
                                System.out.println("您胜利了，不需要交过路费");
                                System.out.println("占领城市，请选择守城武将和兵力");
                                getCity(position, base, defence);
                                // 选择武将及放置的兵力
                                chooseDefenceGeneralAndSoilders(defence, players[no - 1]);
                            } else {
                                System.out.println("您失败了，要交双倍过路费 :" + passMoney * 2);
                                doublePay(no, passMoney);
                            }
                            break;
                        case 4:
                            System.out.println("交了" + passMoney + "过路费");
                            if (players[no - 1].getMoney() < passMoney) {
                                System.out.println("您没钱了，游戏失败");
                                players[2 - no].setMoney(players[2 - no].getMoney() + players[no - 1].getMoney());
                                players[no - 1].setMoney(0);
                            } else {
                                players[no - 1].setMoney(players[no - 1].getMoney() - passMoney);
                                players[2 - no].setMoney(players[2 - no].getMoney() + passMoney);
                            }
                            break;
                        default:
                            break;
                    }
                }
                break;
        }
        // 是否研究 判断是否有还在研究的项目
        if (ResearchService.HasFree(players[no - 1])) {
            ResearchService.research(players[no - 1]);
        } else {
            System.out.println("研究进行中");
        }


        //返回此次掷骰子后玩家的位置坐标
        if (position < 0) {
            return 0;
        } else if (position > 99) {
            return 99;
        } else {
            return position;
        }
    }

    //获得城市 变色
    private void getCity(int position, int base, City city) {
        map.map[position] = base;
        //相应的两个也要变为 base
        //向前向后找2格
        int backpositon = position - 2 > 0 ? position - 2 : 1;
        int upposion = position + 2 < 99 ? position + 2 : 99;
        for (int i = backpositon; i <= upposion; i++) {
            Object object = map.mapObj[i];
            if (object instanceof City) {
                City cityCur = (City) object;
                if (cityCur.getId().equals(city.getId())) {  // 如果是同一个城市，则改变数值
                    map.map[i] = base;
                }
            }
        }
    }

    // 双倍赔偿
    private void doublePay(int no, int passMoney) {
        if (players[no - 1].getMoney() < passMoney) {
            System.out.println("您没钱了，游戏失败");
            players[2 - no].setMoney(players[2 - no].getMoney() + players[no - 1].getMoney());
            players[no - 1].setMoney(0);
        } else {
            players[no - 1].setMoney(players[no - 1].getMoney() - passMoney);
            players[2 - no].setMoney(players[2 - no].getMoney() + passMoney);
        }
    }

    /**
     * @param @param city
     * @param @param general  参数说明
     * @return void    返回类型
     * @throws
     * @Title: chooseDefenceGeneralAndSoilders
     * @Description: 给城市配置武将及兵力
     */
    private void chooseDefenceGeneralAndSoilders(City city, General general) {
        // 设置武将
        General generalByChoose = GeneralFactory.getGeneralByChoose(general, null,4);
        if (null != generalByChoose) {
            generalByChoose.setCityid(city.getId());
            List<General> generals = city.getDenfenceGenerals();
            generals.add(generalByChoose);
            city.setDenfenceGenerals(generals);
            System.out.println("武将设置完成" + generalByChoose.getName() + "派驻" + city.getName());
        }
        int choise = 0;
        Scanner input = null;
        // TODO roboot
        if(general.isReboot()){
            setSoildersAndMoneyByRoboot(city,general);
        }else {
            // 设置兵力
            while (true) {
                System.out.println("请选择您要放在城中的士兵数量，当前您存有的士兵数是" + general.getArmy() + "骑兵" + general.getCavalrys() + "枪兵" + general.getInfantry() + "弓箭手" + general.getArchers());
                System.out.print("当前城市地形是：" + (city.getTopography() == 1 ? "平原" : city.getTopography() == 2 ? "山地" : "水道") + "适应兵种  平原 骑>枪>弓  山地 枪>弓>骑   水道 弓>枪>骑  战力分别是 200% 160% 120% 普通兵种战力100%");
                System.out.println("当前城中的士兵数是" + city.getSoilders() + "骑兵" + city.getCavalrys() + "枪兵" + city.getInfantry() + "弓箭手" + city.getArchers());
                System.out.println("选择骑兵数量：");
                input = new Scanner(System.in);
                choise = input.nextInt();
                if (choise < 0 || (choise > general.getCavalrys())) {
                    System.out.println("太小或太大");
                } else {
                    general.setCavalrys(general.getCavalrys() - choise);
                    city.setCavalrys((city.getCavalrys() == null ? 0 : city.getCavalrys()) + choise);
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
                    general.setInfantry(general.getInfantry() - choise);
                    city.setInfantry((city.getInfantry() == null ? 0 : city.getInfantry()) + choise);
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
                    general.setArchers(general.getArchers() - choise);
                    city.setArchers((city.getArchers() == null ? 0 : city.getArchers()) + choise);
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
                    general.setArmy(general.getArmy() - choise);
                    city.setSoilders((city.getSoilders() == null ? 0 : city.getSoilders()) + choise);
                    System.out.println("剑士设置完成");
                    break;
                }
            }
            // 投入资金
            while (true) {
                input = new Scanner(System.in);
                System.out.println("请选择您要放在城中的金钱数量，当前您手中的金钱为：" + general.getMoney());
                System.out.println("当前城中的金钱数量:" + city.getMoney());
                choise = input.nextInt();
                if ((choise < 0 && (choise * -1) > city.getMoney()) || (choise > general.getMoney())) {
                    System.out.println("太小或太大");
                } else {
                    general.setMoney(general.getMoney() - choise);
                    city.setMoney(city.getMoney() + choise);
                    System.out.println("金钱设置完成");
                    break;
                }
            }
        }
        city.setBelongTo(Integer.parseInt(general.getId()));
        city.toString();
    }

    // 机器人设置兵力和金钱
    private void setSoildersAndMoneyByRoboot(City city, General general) {

        // 查看城市的类别  1 小城  2 中城  3 大城  4 特大城
        int type = city.getType();
        // 地形 1 平原 2 山地 3 水道   适应兵种  平原 骑>枪>弓  山地 枪>弓>骑   水道 弓>枪>骑
        Integer topography = city.getTopography();
        switch (topography){
            case 1:
                if(general.getCavalrys() > 2000){
                    city.setCavalrys(1000);
                    general.setCavalrys(general.getCavalrys() - 1000);
                }else{
                    setSoilders(city, general);
                }
                break;
            case 2:
                if(general.getInfantry() > 2000){
                    city.setInfantry(1000);
                    general.setInfantry(general.getInfantry() - 1000);
                }else{
                    setSoilders(city, general);
                }
                break;
            case 3:
                if(general.getArchers() > 2000){
                    city.setArchers(1000);
                    general.setArchers(general.getArchers() - 1000);
                }else{
                    setSoilders(city, general);
                }
                break;
            default:
                break;
        }

        // 设置剑兵 放剑兵的 1/5 最多4000
        int number = general.getArmy()/5>4000?4000:general.getArmy()/5;
        city.setSoilders(number);
        general.setArmy(general.getArmy() - number);


        // 设置资金 尝试用函数
        Function<Integer,Integer> setCityMoney = a -> {
            if(a > 5000){
                return 2000;
            }else if(a > 3000){
                return 1000;
            }else if(a > 1000){
                return 300;
            }else{
                return 0;
            }
        };

        Integer cityMoney = (Integer) FunctionService.setMoneyByFunction(general.getMoney(), setCityMoney);

        city.setMoney(city.getMoney() + cityMoney);
        general.setMoney(general.getMoney() - cityMoney);

    }

    private void setSoilders(City city, General general) {
        if (general.getArmy() > 2000) {
            city.setSoilders(2000);
            general.setArmy(general.getArmy() - 2000);
        } else {
            // 放一半的步兵
            city.setSoilders(general.getArmy() / 2);
            general.setArmy(general.getArmy() / 2);
        }
    }

    /**
     * @param @param role1
     * @param @param role2    设定文件
     * @return void    返回类型
     * @throws
     * @Title: initGeneralResources
     * @Description: 初始化资源
     */
    private void initGeneralResources(int role1, int role2) {
        // 给角色1 加武将
        players[0].setGenerals(GeneralFactory.setBeginGenerals(String.valueOf(role1)));
        System.out.println(players[0].getName() + "的所属武将有");
        for (Iterator iterator = players[0].getGenerals().iterator(); iterator.hasNext(); ) {
            General g = (General) iterator.next();
            System.out.println("姓名：" + g.getName() + "武力：" + g.getAttack() + "智力：" + g.getIntelligence() + "统帅" + g.getCommand() + "兵种" + g.getArms() + "平原战力" + g.getLandfc() + "山地战力" + g.getMountainfc() + "河流战力" + g.getRiverfc() + "\n" + "技能" + SkillFactory.getSkillByID(g.getSkill()).getName() + ":" + SkillFactory.getSkillByID(g.getSkill()).getMemo());
        }
        players[0].setMoney(40000);
        players[0].setArmy(20000);
        if ("董卓".equals(players[0].getName())) {
            players[0].setMoney(100000);
            players[0].setArmy(40000);
            players[0].setCavalrys(5000); // 骑兵
            players[0].setInfantry(3000); // 枪兵
            players[0].setArchers(5000); // 工兵
        }
        if ("刘备".equals(players[0].getName())) {
            players[0].setCavalrys(1000); // 骑兵
            players[0].setInfantry(3000); // 枪兵
            players[0].setArchers(2000); // 弓兵
        }
        if ("曹操".equals(players[0].getName())) {
            players[0].setCavalrys(4000); // 骑兵
            players[0].setInfantry(1000); // 枪兵
            players[0].setArchers(1000); // 弓兵
        }
        if ("孙权".equals(players[0].getName())) {
            players[0].setCavalrys(0); // 骑兵
            players[0].setInfantry(1000); // 枪兵
            players[0].setArchers(5000); // 弓兵
        }
        ArmsService.setArms(players[0]);
        // 给角色2 加武将
        System.out.println(role2);
        players[1].setGenerals(GeneralFactory.setBeginGenerals(String.valueOf(role2)));
        System.out.println(players[1].getName() + "的所属武将有");
        for (Iterator iterator = players[1].getGenerals().iterator(); iterator.hasNext(); ) {
            General g = (General) iterator.next();
            System.out.println("姓名：" + g.getName() + "武力：" + g.getAttack() + "智力：" + g.getIntelligence() + "统帅" + g.getCommand() + "兵种" + g.getArms() + "平原战力" + g.getLandfc() + "山地战力" + g.getMountainfc() + "河流战力" + g.getRiverfc() + "\n" + "技能" + SkillFactory.getSkillByID(g.getSkill()).getName() + ":" + SkillFactory.getSkillByID(g.getSkill()).getMemo());
        }
        players[1].setMoney(20000);
        players[1].setArmy(10000);
        if ("董卓".equals(players[1].getName())) {
            players[1].setMoney(40000);
            players[1].setArmy(15000);
            players[1].setCavalrys(5000); // 骑兵
            players[1].setInfantry(3000); // 枪兵
            players[1].setArchers(5000); // 工兵
        }
        if ("刘备".equals(players[1].getName())) {
            players[1].setCavalrys(1000); // 骑兵
            players[1].setInfantry(3000); // 枪兵
            players[1].setArchers(2000); // 弓兵
        }
        if ("曹操".equals(players[1].getName())) {
            players[1].setCavalrys(4000); // 骑兵
            players[1].setInfantry(1000); // 枪兵
            players[1].setArchers(1000); // 弓兵
        }
        if ("孙权".equals(players[1].getName())) {
            players[1].setCavalrys(0); // 骑兵
            players[1].setInfantry(1000); // 枪兵
            players[1].setArchers(5000); // 弓兵
        }
        // 目前默认第二个是机器人
        players[1].setReboot(true);
        ArmsService.setArms(players[1]);
    }
}
