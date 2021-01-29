package cn.eric.game.fujiatianxia6.service;

import cn.eric.game.fujiatianxia6.factory.*;
import cn.eric.game.fujiatianxia6.po.Map;
import cn.eric.game.fujiatianxia6.po.*;
import cn.eric.game.fujiatianxia6.service.event.Event;

import java.util.*;
import java.util.function.Function;

public class Game {

    static Map map;  //地图
    static int mapNum;
    static int num;

//    int playerPos1; //对战中玩家1的当前位置
//    int playerPos2; //对战中玩家2的当前位置
//    int playerPos3; //对战中玩家3的当前位置
//    int playerPos4; //对战中玩家4的当前位置

    static int[] playPos;
    static String[] goAndStop = new String[num];   //走或停标识设置
    static General[] players = new General[num];  //对战角色

    public Game() {
    }

    public Game(CampaignMap campaignMap) {
        initCampaingMap(campaignMap);
    }

    public boolean startWithSave() {
        //map = new Map();
        //map.createMap();  //生成地图
        return play();
    }

    public boolean startCampaign() {
        System.out.println("\n~~~~~~~~~~~~~~~~~~~"+map.getCampaignMap().getMemo()+"~~~~~~~~~~~~~~~~~~~");
        int[] roles = new int[num];
        List<String> defaultPlayer = map.getCampaignMap().getDefaultPlayer();
        // 设置玩家代表的角色
        for (int i = 0; i < defaultPlayer.size(); i++) {
            setRole(i + 1, Integer.parseInt(defaultPlayer.get(i)));
            roles[i] = Integer.parseInt(defaultPlayer.get(i));
        }
        initGeneralResources(roles);
        initGeneralWild();
        map.initCity();
        return play();   //开始对战
    }
    /**
     * 初始化游戏的一局
     */
    public void init() {
        System.out.println("请选择玩家数量(包括AI)");
        Scanner input = new Scanner(System.in);
        num = input.nextInt();
        while(num < 2 || num > 4){
            System.out.println("请输入2-4 的数字");
            num = input.nextInt();
        }

        // 玩家的当前位置
        playPos = new int[num];
        // 走或停标识设置
        goAndStop = new String[num];
        // 对战角色
        players = new General[num];

        System.out.println("请选择战役地图 ");
        int mapSize = MapFactory.showAllCampaignMap();
        input = new Scanner(System.in);
        mapNum = input.nextInt();
        while(mapNum < 0 || mapNum > mapSize){
            System.out.println("请输入0- " + (mapSize-1) + " 的数字");
            mapNum = input.nextInt();
        }
        CampaignMap campaignMap = MapFactory.chooseMap(mapNum + "");
        map = new Map(campaignMap);
        map.createMap();  //生成地图
        int randomNum = new Random().nextInt(map.getSize()-1);
        for (int i = 0; i < num; i++) {
            // 出生点随机
            playPos[i] = (i * (map.getSize() / num) + randomNum) % map.getSize();
            goAndStop[i] = "on";
        }
    }

    /**
     * 初始化战役游戏的一局
     * @param campaignMap
     */
    public void initCampaingMap(CampaignMap campaignMap) {
        num = campaignMap.getDefaultPlayer().size();

        // 玩家的当前位置
        playPos = new int[num];
        // 走或停标识设置
        goAndStop = new String[num];
        // 对战角色
        players = new General[num];
        map = new Map(campaignMap);
        map.createMap();  //生成地图
        int randomNum = new Random().nextInt(map.getSize()-1);
        for (int i = 0; i < num; i++) {
            // 出生点随机
            playPos[i] = (i * (map.getSize() / num) + randomNum) % map.getSize();
            goAndStop[i] = "on";
        }
    }

    /**
     * 初始化武将库  初始化君主携带武将 金钱  兵力
     */
    public void initData() {

    }

    /**
     * 开始游戏
     */
    public boolean start() {
        //初始化
        init();

        System.out.println("\n~~~~~~~~~~~~~~~~~~~多  人  对  战~~~~~~~~~~~~~~~~~~~");
        System.out.println("\n请选择角色: 1. 刘备 容易收服武将，诸葛亮bug  \n " + "2. 曹操 野战单挑都厉害 " +
                " \n 3. 孙权 水战无敌，周瑜bug  \n 4. 董卓 三国第一武将在手，单挑无敌，群雄归附初始兵钱加倍 \n  5. 袁绍 四世三公 文臣武将诸多");
        Scanner input = new Scanner(System.in);
        int[] roles = new int[num];
        int role = 0;
        for (int i = 0; i < num; i++) {
            while(hasbechoose(roles,role)) {
                System.out.print("请玩家" + (i+1) + "选择角色:  ,不能与其他角色重复");
                role = input.nextInt();
            }
            // 设置玩家代表的角色
            setRole(i + 1, role);
            roles[i] = role;
        }

        initGeneralResources(roles);
        initGeneralWild();
        map.initCity();
        return play();   //开始对战
    }

    // 将无主的将领修改为在野 （君主除外）
    private void initGeneralWild() {
        GeneralFactory.resetWildGeneral(players);

    }

    private boolean hasbechoose(int[] roles, int role) {
        for (int i = 0; i < roles.length; i++) {
            if(roles[i] == role){
                return true;
            }
        }
        return false;
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
            case 5:
                players[no - 1] = GeneralFactory.getGeneral("袁绍");
                break;
            case 6:
                players[no - 1] = GeneralFactory.getGeneral("刘表");
                break;
            case 7:
                players[no - 1] = GeneralFactory.getGeneral("刘璋");
                break;
            case 8:
                players[no - 1] = GeneralFactory.getGeneral("韩遂");
                break;
            default:
                break;
        }
    }

    /**
     * 两人对战玩法
     */
    public boolean play() {
        System.out.println("\n\n\n\n");

        System.out.print("\n\n****************************************************\n");
        System.out.print("                     Game  Start                    \n");
        System.out.print("****************************************************\n\n");

        //显示对战双方士兵样式
//        System.out.println("^_^" + players[0].getName() + "：　A");
//        System.out.println("^_^" + players[1].getName() + "：  B\n");

        //显示对战地图
        System.out.println("\n 图例： " + "㊖ 赌场  ◎幸运轮盘  ♚酒馆  ♞募兵  ♙空城  \n");
        map.showMap(playPos);
        //游戏开始
        int step;  //存储骰子数目
        // TODO 有任何一方的钱少于0
        while (hasWin()) {
            //轮流掷骰子
            for (int i = 0; i < players.length; i++) {
                if ("4".equals(players[i].getStatus())) {
                    System.out.println(players[i].getName() + "已经破产");
                    continue;
                }
                if ("on".equals(goAndStop[i])) {
                    //玩家1掷骰子
                    step = throwShifter(i+1);   //掷骰子
                    System.out.println("\n-----------------");  //显示结果信息
                    System.out.println("骰子数： " + step);
                    if (playPos[i] + step < 0) {
                        playPos[i] = 0;
                    } else if (playPos[i] + step > (map.getSize() - 1)) { //如果大于99格，则表示已经走完一圈，要重新计算
                        playPos[i] = playPos[i] + step - map.getSize();
                    } else {
                        playPos[i] = playPos[i] + step;
                    }
                    // 计算这一次移动后的当前位置
                    playPos[i] = getCurPos(i+1, playPos[i], step);
                    System.out.println("-----------------\n");
                } else {
                    // 显示此次暂停信息
                    System.out.println("\n" + players[i].getName() + "停掷一次！\n");
                    // 设置下次可掷状态
                    goAndStop[i] = "on";
                }
                // 触发事件
                Event event = EventService.roundTrigger(players[i]);
                if (event != null) {
                    EventService.beginEvent(players[i], event);
                }
                System.out.println("\n\n\n\n");
            }
            //各城市开始计算收益，包括钱 兵 武器
            //金钱收益  当前城市金钱 * 繁华指数 * 太守的政治
            CityFactory.computeMoney();
            //兵增加 如有徽兵所时会缓慢增加
            CityFactory.computeSoilders();
            //武器增加 暂时不增加 如城市内有兵工厂会缓慢增加
            // 根据建筑检查增加骑兵和枪兵 弓兵
            BuildingFactory.computeHorse();
            BuildingFactory.computeInAndAr();
            // 研究队列 都减1 如果到0 就从研究队列中去除
            ResearchService.researchAll();
            //查看每回合结束后触发技能的武将是否触发 例如制衡
            for (int i = 0; i < players.length; i++) {
                SkillFactory.changeAfter(9, 0, players[i], null, null);
            }

            // 根据声望 获得金钱和兵力
            GeneralFactory.getMoneyAndArmyByReputation(players);

            // 消耗军费
            GeneralFactory.militarySpending(players);

            // 如果玩家的金钱小于0 所属城市归0 所属武将下野
            GeneralFactory.checkDeadGenerals(players);
            // 自动保存进度
            try {
                SaveService.save(null);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            // 显示当前地图
            map.showMap(playPos);
        }
        if(players[0].getMoney() > 0){
            System.out.println("\n\n\n\n");
            System.out.print("****************************************************\n");
            System.out.print("                      WIN                    \n");
            System.out.print("****************************************************\n\n");
            return true;
        }else{
            System.out.println("\n\n\n\n");
            System.out.print("****************************************************\n");
            System.out.print("                      LOSER                   \n");
            System.out.print("****************************************************\n\n");
            return false;
        }
        //judge();
    }

    private boolean hasWin() {
        boolean player = players[0].getMoney() > 0;
        if (player) {
            for (int i = 1; i < players.length; i++) {
                if (!"4".equals(players[i].getStatus())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }


    /**
     * 掷骰子
     *
     * @param no 玩家次序
     * @return step 掷出的骰子数目
     */
    public int throwShifter(int no) {
        int step = 0;
        if(!players[no - 1].isReboot()){
            System.out.println();
            System.out.println("可以输入命令查看相关信息，输入-help获取所有命令，输入0 | a | j 继续");
            Scanner input = new Scanner(System.in);
            String choose = input.nextLine();
            while (!"0".equals(choose) && !"a".equals(choose) && !"j".equals(choose)) {
                CommandLineSerivce.getInstance().getCommandLine(choose);
                choose = input.nextLine();
            }
        }
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
                            && (players[no - 1].getArmy() > 3000)
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
                        // getCity(position, base, city);
                        // 选择武将及放置的兵力
                        int i = chooseDefenceGeneralAndSoilders(city, players[no - 1]);
                        if(i==1){
                            getCity(position, base, city);
                        }
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
                                System.out.print("恭喜获得500骑兵");
                                players[no - 1].setCavalrys(players[no - 1].getCavalrys() + 500);
                                break;
                            case 1:
                                System.out.print("恭喜获得500枪兵");
                                players[no - 1].setInfantry(players[no - 1].getInfantry() + 500);
                                break;
                            case 2:
                                System.out.print("恭喜获得500弓兵");
                                players[no - 1].setArchers(players[no - 1].getArchers() + 500);
                                break;
                            default:
                                System.out.print("恭喜获得500枪兵");
                                players[no - 1].setInfantry(players[no - 1].getInfantry() + 500);
                                break;
                        }
                        break;
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
                General generalByChoose = GeneralFactory.getGeneralByChoose(players[no - 1], wineGenerals,0,null);
                if (generalByChoose != null) {
                    players[no - 1].getGenerals().add(generalByChoose);
                    generalByChoose.setBelongTo(players[no - 1].getId());
                    System.out.println(generalByChoose.getName() + "拜入" + players[no - 1].getName() + "账下");
                }
                break;
            case 3:
                // 下一次暂停一次
                // 进入赌坊
                goAndStop[no - 1] = "off";  //设置下次暂停掷骰子
                System.out.println("~~>_<~~  要停战一局了。");
                break;
            case 4:   //募兵
                while (true) {
                    // 当前的随行士兵数获得单价
                    int price = players[no - 1].getArmy() / 10000 + 1;
                    System.out.println("|-P  " + "请选择您要购买的兵力数量，一个兵" + price + "个金币,最多买10000个士兵");
                    int choiseBuySoilder = 0;
                    if (players[no - 1].isReboot()) {
                        if(players[no - 1].getArmy() < 20000){
                            choiseBuySoilder =
                                    players[no - 1].getMoney() / 2 / price > 10000?10000:(choiseBuySoilder =
                                            players[no - 1].getMoney() / 2 / price);
                        }else {
                            if(players[no - 1].getMoney() > 30000){
                                choiseBuySoilder = players[no - 1].getMoney() / 2 / price > 10000?10000:(choiseBuySoilder =
                                        players[no - 1].getMoney() / 2 / price);
                            }else{
                                choiseBuySoilder = players[no - 1].getMoney() / 5 / price > 10000?10000:(choiseBuySoilder =
                                        players[no - 1].getMoney() / 5 / price);
                            }
                        }
                    } else {
                        choiseBuySoilder = input.nextInt();
                    }
                    if(choiseBuySoilder > 10000){
                        continue;
                    }
                    if ((players[no - 1].getMoney() - choiseBuySoilder * price < 0)) {
                        System.out.println("您没有这么多钱");
                    } else {
                        if (choiseBuySoilder > 3000) {
                            System.out.println("您补充了" + choiseBuySoilder + "兵力，一统天下指日可待");
                        } else {
                            System.out.println("您补充了" + choiseBuySoilder + "兵力，想一桶天下 做梦吧");
                        }
                        players[no - 1].setMoney(players[no - 1].getMoney() - choiseBuySoilder * price);
                        players[no - 1].setArmy(players[no - 1].getArmy() + choiseBuySoilder * price);
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
                    // 查看技能
                    SkillFactory.checkSkillViaCity(defence,players[no - 1]);
                    // 选择武将及放置的兵力
                    chooseDefenceGeneralAndSoilders(defence, players[no - 1]);
                    // 升级城市
                    CityFactory.upgradeCity(defence,players[no - 1]);
                    // 升级建筑
                    BuildingFactory.upgradedBuild(defence, players[no - 1]);
                    try {
                        BuildingFactory.buildInCity(defence, players[no - 1]);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (defence.getBelongTo() == 0) {
                        System.out.println("该城市没有归属主公，直接占领");
                        if(players[no - 1].isReboot()){

                            // 选择武将及放置的兵力
                            int i = chooseDefenceGeneralAndSoilders(defence, players[no - 1]);
                            if(i == 1){
                                getCity(position, base, defence);
                            }else{
                                getCity(position, 0, defence);
                            }
                        }else{
                           // getCity(position, base, defence);
                            // 选择武将及放置的兵力
                            int i = chooseDefenceGeneralAndSoilders(defence, players[no - 1]);
                            if(i == 1){
                                getCity(position, base, defence);
                            }else{
                                getCity(position, 0, defence);
                            }
                        }
                    } else {
                        int passMoney = (int) (defence.getMoney() * defence.getType() * 0.1 * (defence.getProsperity()/1000 + 1));

                        System.out.println("======快交过路费：" + passMoney + "\n");
                        System.out.println("请选择：1、野战 2、单挑 3、攻城 4、乖乖交钱\n");
                        int choiseBuySoilder;
                        if (players[no - 1].isReboot()) {
                            // 机器人自动选择
                            int defencCount = Optional.ofNullable(defence.getSoilders()).orElse(0) + Optional.ofNullable(defence.getArchers()).orElse(0) + Optional.ofNullable(defence.getInfantry()).orElse(0) + Optional.ofNullable(defence.getCavalrys()).orElse(0);
                            int attCount = Optional.ofNullable(players[no - 1].getArmy()).orElse(0) + Optional.ofNullable(players[no - 1].getArchers()).orElse(0) + Optional.ofNullable(players[no - 1].getInfantry()).orElse(0) + Optional.ofNullable(players[no - 1].getCavalrys()).orElse(0);

                            // 如果多于城内的兵力5倍直接攻城
                            if (attCount > defencCount * 5) {
                                choiseBuySoilder = 3;
                            // 如果多于城内的兵力4倍 3倍 概率攻城
                            } else if (attCount > defencCount * 4 && new Random().nextInt(100) <= 80)  {
                                choiseBuySoilder = 3;
                            } else if (attCount > defencCount * 3 && new Random().nextInt(100) <= 40)  {
                                choiseBuySoilder = 3;
                            } else {
                                // 如果人数少于防守方，只选择单挑, 二十几率交钱
                                if (attCount < defencCount) {
//                                    if (new Random().nextInt(100) <= 20) {
//                                        choiseBuySoilder = 4;
//                                    } else {
                                        choiseBuySoilder = 2;
//                                    }
                                } else {
                                    // 如果人数大于防守方，又不够攻城，默认野战 二十几率交钱
//                                    if (new Random().nextInt(100) <= 20) {
//                                        choiseBuySoilder = 4;
//                                    } else {
                                        choiseBuySoilder = 1;
//                                    }
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
                                    System.out.println("您胜利了，不需要交过路费 声望提升40 获得100特色兵力");
                                    // TODO
                                    Integer topography = defence.getTopography();
                                    if (topography == 1) {
                                        players[no - 1].setCavalrys(players[no - 1].getCavalrys() + 100);
                                    } else if (topography == 2) {
                                        players[no - 1].setInfantry(players[no - 1].getInfantry() + 100);
                                    } else if (topography == 3) {
                                        players[no - 1].setArchers(players[no - 1].getArchers() + 100);
                                    }
                                    players[no - 1].setReputation(Optional.ofNullable(players[no - 1].getReputation()).orElse(0) + 40);
                                } else {
                                    System.out.println("您失败了 声望下降20，要交双倍过路费:" + passMoney * 2);
                                    players[no - 1].setReputation(Optional.ofNullable(players[no - 1].getReputation()).orElse(0) - 20);
                                    doublePay(no, passMoney, defence);
                                }
                                break;
                            case 2:
                                System.out.println("单挑开始");
                                boolean result = Fight.oneOnOneFight(players[no - 1], defence);
                                if (result) {
                                    System.out.println("您胜利了，不需要交过路费 声望提升15");
                                    players[no - 1].setReputation(Optional.ofNullable(players[no - 1].getReputation()).orElse(0) + 15);
                                } else {
                                    System.out.println("您失败了 声望下降5，要交双倍过路费:" + passMoney * 2);
                                    players[no - 1].setReputation(Optional.ofNullable(players[no - 1].getReputation()).orElse(0) - 5);
                                    doublePay(no, passMoney, defence);
                                }
                                break;
                            case 3:
                                System.out.println("攻城开始");
                                boolean attackCityResult = Fight.attackCity(players[no - 1], defence);
                                if (attackCityResult) {
                                    System.out.println("您胜利了，不需要交过路费 声望提升 200 ");
                                    players[no - 1].setReputation(Optional.ofNullable(players[no - 1].getReputation()).orElse(0) + 200);
                                    System.out.println("占领城市，请选择守城武将和兵力");

                                    // 选择武将及放置的兵力
                                    int i = chooseDefenceGeneralAndSoilders(defence, players[no - 1]);
                                    if(i == 1){
                                        getCity(position, base, defence);
                                    }

                                } else {
                                    System.out.println("您失败了 声望下降50 ，要交双倍过路费 :" + passMoney * 2);
                                    players[no - 1].setReputation(Optional.ofNullable(players[no - 1].getReputation()).orElse(0) - 50);
                                    doublePay(no, passMoney, defence);
                                }
                                break;
                            case 4:
                                System.out.println("交了" + passMoney + "过路费");
                                General generalById = GeneralFactory.getGeneralById(defence.getBelongTo().toString());
                                if (passMoney == 0 || generalById == null) {
                                    break;
                                }
                                if (players[no - 1].getMoney() < passMoney) {
                                    System.out.println("您没钱了，游戏失败");
                                    generalById.setMoney(generalById.getMoney() + players[no - 1].getMoney());
                                    players[no - 1].setMoney(players[no - 1].getMoney() - passMoney);
                                    players[no - 1].setStatus("4");
                                } else {
                                    players[no - 1].setMoney(players[no - 1].getMoney() - passMoney);
                                    generalById.setMoney(generalById.getMoney() + passMoney);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
                break;
        }
        if(!"4".equals(players[no - 1].getStatus())){
            // 是否研究 判断是否有还在研究的项目
            if (ResearchService.HasFree(players[no - 1])) {
                ResearchService.research(players[no - 1]);
            } else {
                System.out.println("研究进行中");
            }
            // 招募俘虏武将
            List<General> capturedGenerals = GeneralFactory.getCapturedGenerals(players[no - 1].getGenerals());
            int size = capturedGenerals.size();
            if(size > 0) {
                System.out.println("俘虏的武将有" + size + "个");
                GeneralFactory.recruit(capturedGenerals,players[no - 1]);
            }
        }
        //返回此次掷骰子后玩家的位置坐标
        if (position > map.getSize()-1 ) {
            return map.getSize()-1;
        } else {
            return position;
        }
    }

    //获得城市 变色
    private void getCity(int position, int base, City city) {
        map.map[position] = base;
        int size = map.getSize() - 1;
        //相应的两个也要变为 base
        //向前向后找2格
        int backpositon = position - 2 > 0 ? position - 2 : 1;
        int upposion = Math.min(position + 2, size);
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
    private void doublePay(int no, int passMoney,City defence) {
        passMoney = passMoney * 2;
        General generalById = GeneralFactory.getGeneralById(defence.getBelongTo().toString());
        if (players[no - 1].getMoney() < passMoney) {
            System.out.println("您没钱了，游戏失败");
            generalById.setMoney(generalById.getMoney() + players[no - 1].getMoney());
            players[no - 1].setMoney(0);
            players[no - 1].setStatus("4");
        } else {
            players[no - 1].setMoney(players[no - 1].getMoney() - passMoney);
            generalById.setMoney(generalById.getMoney() + passMoney);
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
    private int chooseDefenceGeneralAndSoilders(City city, General general) {
        // 设置武将
        General generalByChoose = GeneralFactory.getGeneralByChoose(general, null,4,city);
        if (null != generalByChoose) {
            generalByChoose.setCityId(city.getId());
            List<General> generals = city.getDenfenceGenerals();
            generals.add(generalByChoose);
            city.setDenfenceGenerals(generals);
            System.out.println("武将设置完成" + generalByChoose.getName() + "派驻" + city.getName());
        }else{
            // 如果AI不设置武将
            if(city.getDenfenceGenerals().size() == 0) {
                System.out.println("未设置武将，不能占领城市，拿走城市内的发展金和剩余兵力");
                general.setMoney(general.getMoney() + city.getMoney());
                general.setArmy(general.getArmy() + city.getSoilders());
                general.setArchers(general.getArchers() + city.getArchers());
                general.setInfantry(general.getInfantry() + city.getInfantry());
                general.setCavalrys(general.getCavalrys() + city.getCavalrys());

                city.setMoney(0);
                city.setSoilders(0);
                city.setInfantry(0);
                city.setArchers(0);
                city.setCavalrys(0);
                city.setBelongTo(0);
                return 0;
            }
        }

        int choise;
        Scanner input;
        // 如果是AI
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
                if ((choise < 0 && choise * -1 > city.getCavalrys()) || (choise > general.getCavalrys())) {
                    System.out.println("数量太大");
                } else {
                    general.setCavalrys(general.getCavalrys() - choise);
                    city.setCavalrys((city.getCavalrys() == null ? 0 : city.getCavalrys()) + choise);
                    System.out.println("骑兵设置完成");
                    break;
                }
            }

            while (true) {
                try {
                    System.out.println("选择枪兵数量：");
                    input = new Scanner(System.in);
                    choise = input.nextInt();
                    if ((choise < 0 && choise * -1 > city.getInfantry()) || (choise > general.getInfantry())) {
                        System.out.println("太小或太大");
                    } else {
                        general.setInfantry(general.getInfantry() - choise);
                        city.setInfantry((city.getInfantry() == null ? 0 : city.getInfantry()) + choise);
                        System.out.println("枪兵设置完成");
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("输入有误");
                }
            }

            while (true) {
                System.out.println("选择弓箭手数量：");
                input = new Scanner(System.in);
                choise = input.nextInt();
                if ((choise < 0 && choise * -1 > city.getArchers()) || (choise > general.getArchers())) {
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
                if ((choise < 0 && choise * -1 > city.getSoilders()) || (choise > general.getArmy())) {
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
        System.out.println(city.toString());
        return 1;
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
                    if (city.getCavalrys() > 3000) {
                        break;
                    }
                    city.setCavalrys(city.getCavalrys() + 1000);
                    general.setCavalrys(general.getCavalrys() - 1000);
                } else if (general.getCavalrys() > 1000) {
                    // 如果城市大于2000
                    if (city.getCavalrys() > 2000) {
                        break;
                    }
                    int add;
                    // 如果城市大于1000 自身保留1000 其他的放入城中
                    if (city.getCavalrys() > 1000) {
                        add = general.getCavalrys() - 1000;
                        city.setCavalrys(city.getCavalrys() + add);
                        general.setCavalrys(1000);
                        break;
                    }
                    add = 1000 - Optional.ofNullable(city.getCavalrys()).orElse(0);
                    city.setCavalrys(Optional.ofNullable(city.getCavalrys()).orElse(0) + add);
                    general.setCavalrys(general.getCavalrys() - add);
                } else {
                    // 如果自己数量不够1000了，看城市当中的情况
                    if (city.getCavalrys() < 1000) {
                        break;
                    }
                    if (city.getCavalrys() > 2000) {
                        general.setCavalrys(general.getCavalrys() + 1000);
                        city.setCavalrys(city.getCavalrys() - 1000);
                        break;
                    }
                    if (city.getCavalrys() > 1000) {
                        int add = city.getCavalrys() - 1000;
                        general.setCavalrys(general.getCavalrys() + add);
                        city.setCavalrys(city.getCavalrys() - add);
                        break;
                    }
                }
                break;
            case 2:
                if(general.getInfantry() > 2000){
                    if (city.getInfantry() > 3000) {
                        break;
                    }
                    city.setInfantry(city.getInfantry() + 1000);
                    general.setInfantry(general.getInfantry() - 1000);
                    break;
                } else if (general.getInfantry() > 1000) {
                    if (city.getInfantry() > 2000) {
                        break;
                    }
                    if (city.getInfantry() > 1000) {
                        city.setInfantry(city.getInfantry() + 500);
                        general.setInfantry(general.getInfantry() - 500);
                        break;
                    }
                    int add = 1000 - Optional.ofNullable(city.getInfantry()).orElse(0);
                    city.setInfantry(Optional.ofNullable(city.getInfantry()).orElse(0) + add);
                    general.setInfantry(general.getInfantry() - add);
                } else {
                    // 如果自己数量不够1000了，看城市当中的情况
                    if (city.getInfantry() < 1000) {
                        break;
                    }
                    if (city.getInfantry() > 2000) {
                        general.setInfantry(general.getInfantry() + 1000);
                        city.setCavalrys(city.getCavalrys() - 1000);
                        break;
                    }
                    if (city.getInfantry() > 1000) {
                        int add = city.getInfantry() - 1000;
                        general.setInfantry(general.getInfantry() + add);
                        city.setInfantry(city.getInfantry() - add);
                        break;
                    }
                }
                break;
            case 3:
                if(general.getArchers() > 2000){
                    if (city.getArchers() > 3000) {
                        break;
                    }
                    city.setArchers(city.getArchers() + 1000);
                    general.setArchers(general.getArchers() - 1000);
                    break;
                } else if (general.getArchers() > 1000) {
                    if (city.getArchers() > 2000) {
                        break;
                    }
                    if (city.getArchers() > 1000) {
                        city.setArchers(city.getArchers() + 500);
                        general.setArchers(general.getArchers() - 500);
                        break;
                    }
                    int add = 1000 - Optional.ofNullable(city.getArchers()).orElse(0);
                    city.setArchers(Optional.ofNullable(city.getArchers()).orElse(0) + add);
                    general.setArchers(general.getArchers() - add);
                } else {
                    // 如果自己数量不够1000了，看城市当中的情况
                    if (city.getArchers() < 1000) {
                        break;
                    }
                    if (city.getArchers() > 2000) {
                        general.setArchers(general.getArchers() + 1000);
                        city.setArchers(city.getCavalrys() - 1000);
                        break;
                    }
                    if (city.getArchers() > 1000) {
                        int add = city.getArchers() - 1000;
                        general.setArchers(general.getArchers() + add);
                        city.setArchers(city.getArchers() - add);
                        break;
                    }
                }
                break;
            default:
                break;
        }

        // 设置剑兵 放剑兵的 1/5 最多4000 可以回收
        int number;
        if(city.getSoilders() >= 8000){
            if (general.getArmy() < 8000) {
                number = 0 - Math.min(8000 - general.getArmy(), 4000);
            } else {
                number = Math.min(general.getArmy() / 5, 4000);
                number = number / 4;
            }
        } else if (city.getSoilders() >= 4000) {
            if (general.getArmy() < 4000) {
                number = 0 - Math.min(4000 - general.getArmy(), 2000);
            } else {
                number = Math.min(general.getArmy() / 5, 4000);
                number = number / 2;
            }
        } else if (city.getSoilders() >= 2000) {
            if (general.getArmy() < 2000) {
                number = general.getArmy();
            } else {
                number = 2000;
            }
        } else {
            number = general.getArmy();
        }
        city.setSoilders(city.getSoilders() + number);
        general.setArmy(general.getArmy() - number);


        // 设置资金 尝试用函数
        Function<Integer,Integer> setCityMoney = a -> {
            if(a > 5000){
                return 2000;
            }else if(a > 3000){
                return 1000;
            }else{
                return -1;
            }
        };

        Integer cityMoney = (Integer) FunctionService.setMoneyByFunction(general.getMoney(), setCityMoney);

        if(cityMoney == -1){
            general.setMoney((int) (general.getMoney() + city.getMoney() * 0.9));
            city.setMoney((int) (city.getMoney() * 0.1));
        }else{
            city.setMoney(city.getMoney() + cityMoney);
            general.setMoney(general.getMoney() - cityMoney);
        }

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
    private void initGeneralResources(int[] roles) {
        for (int i = 0; i < roles.length; i++) {
            players[i].setGenerals(GeneralFactory.setBeginGenerals(String.valueOf(roles[i])));
            System.out.println(players[i].getName() + "的所属武将有");
            for (Iterator iterator = players[i].getGenerals().iterator(); iterator.hasNext(); ) {
                General g = (General) iterator.next();
                System.out.println("姓名：" + g.getName() + "武力：" + g.getAttack() + "智力：" + g.getIntelligence() + "统帅" + g.getCommand() + "兵种" + g.getArms() + "平原战力" + g.getLandfc() + "山地战力" + g.getMountainfc() + "河流战力" + g.getRiverfc() + "\n" + "技能" + SkillFactory.getSkillByID(g.getSkill()).getName() + ":" + SkillFactory.getSkillByID(g.getSkill()).getMemo());
            }
            players[i].setMoney(40000);
            players[i].setArmy(20000);
            players[i].setStatus("0");
            switch (players[i].getName()) {
                case "董卓":
                    players[i].setMoney(50000);
                    players[i].setArmy(25000);
                    players[i].setReputation(350);
                    players[i].setCavalrys(6000); // 骑兵
                    players[i].setInfantry(2000); // 枪兵
                    players[i].setArchers(2000); // 弓兵
                    break;
                case "刘备":
                    players[i].setReputation(200);
                    players[i].setCavalrys(1000); // 骑兵
                    players[i].setInfantry(3000); // 枪兵
                    players[i].setArchers(2000); // 弓兵
                    break;
                case "曹操":
                    players[i].setReputation(300);
                    players[i].setCavalrys(4000); // 骑兵
                    players[i].setInfantry(1000); // 枪兵
                    players[i].setArchers(1000); // 弓兵
                    break;
                case "孙权":
                    players[i].setReputation(300);
                    players[i].setCavalrys(0); // 骑兵
                    players[i].setInfantry(1000); // 枪兵
                    players[i].setArchers(5000); // 弓兵
                    break;
                case "袁绍":
                    players[i].setMoney(50000);
                    players[i].setArmy(20000);
                    players[i].setReputation(400);
                    players[i].setCavalrys(2500); // 骑兵
                    players[i].setInfantry(2500); // 枪兵
                    players[i].setArchers(2500); // 弓兵
                    break;
                default:
                    players[i].setReputation(250);
                    players[i].setCavalrys(2000); // 骑兵
                    players[i].setInfantry(2000); // 枪兵
                    players[i].setArchers(2000); // 弓兵
                    break;
            }


            if(i>0){
                players[i].setReboot(true);
            }
            ArmsService.setArms(players[i]);
        }
    }

    public static Map getMap() {
        return map;
    }

    public static void setMap(Map map) {
        Game.map = map;
    }

    public static int getNum() {
        return num;
    }

    public static void setNum(int num) {
        Game.num = num;
    }

    public static int[] getPlayPos() {
        return playPos;
    }

    public static void setPlayPos(int[] playPos) {

            Game.playPos = playPos;

    }

    public static String[] getGoAndStop() {
        return goAndStop;
    }

    public static void setGoAndStop(String[] goAndStop) {
        Game.goAndStop = goAndStop;
    }

    public static General[] getPlayers() {
        return players;
    }

    public static void setPlayers(General[] players) {
        Game.players = players;
    }

    public static int getMapNum() {
        return mapNum;
    }

    public static void setMapNum(int mapNum) {
        Game.mapNum = mapNum;
    }
}
