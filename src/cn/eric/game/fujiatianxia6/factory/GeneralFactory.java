package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.po.AttackCity;
import cn.eric.game.fujiatianxia6.po.BattleField;
import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.test.Dom4JforXML;
import org.dom4j.DocumentException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Title: GeneralFactory<／p>
 * <p>Description: 武将工厂类<／p>
 * <p>Company: want-want<／p>
 *
 * @author 00322027
 * @date 2017年8月9日 上午9:18:14
 */
public class GeneralFactory {

    private static List<General> initGenerals = new ArrayList<General>(256);

    public static List<General> getInitGenerals() {
        return initGenerals;
    }

    public static void setInitGenerals(List<General> initGenerals) {
        GeneralFactory.initGenerals = initGenerals;
    }

    /**
     * @param
     * @return void    返回类型
     * @throws DocumentException
     * @Title: initGeneralList
     * @Description: 游戏开始时初始化所有武将
     */
    public GeneralFactory() {
    }

    public static void init() {
        try {
            initGenerals = new ArrayList<>(256);
            initGenerals = Dom4JforXML.test2();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public GeneralFactory(String filePath) {
        try {
            initGenerals = Dom4JforXML.loadGenerals(filePath);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param @return 参数说明
     * @return General    返回类型
     * @throws
     * @Title: getGeneral
     * @Description: 根据武将名称返回武将
     */
    public static General getGeneral(String name) {
        for (General general : initGenerals) {
            if (name.equals(general.getName())) {
                return general;
            }
        }
        return null;
    }

    /**
     * @param @return 参数说明
     * @return General    返回类型
     * @throws
     * @Title: getGeneral
     * @Description: 根据武将名称返回武将
     */
    public static General getGeneralById(String id) {
        for (General general : initGenerals) {
            if (id.equals(general.getId())) {
                return general;
            }
        }
        return null;
    }

    /**
     * @param @param  id
     * @param @return 参数说明
     * @return List<General>    返回类型
     * @throws
     * @Title: setBeginGenerals
     * @Description: 根据传入的主公id 返回初始武将
     */
    public static List<General> setBeginGenerals(String id) {
        List<General> beginGenerals = new ArrayList<General>(10);
        for (Iterator iterator = initGenerals.iterator(); iterator.hasNext(); ) {
            General general = (General) iterator.next();
            // status
            if (id.equals(general.getBelongTo()) && "0".equals(general.getStatus())) { //状态为0
                beginGenerals.add(general);
            }
        }
        return beginGenerals;
    }

    /**
     * @param @param  gID
     * @param @return 参数说明
     * @return List<General>    返回类型
     * @throws
     * @Title: getGeneral
     * @Description: 酒馆 根据传入的主公id 随机返回三个武将
     */
    public static List<General> getWineGeneral(Integer id) {
        List<General> wineGenerals = new ArrayList<General>();
        //首先根据传入的id进行剩余武将的排序，并返回前三个
        for (Iterator iterator = initGenerals.iterator(); iterator.hasNext(); ) {
            General general = (General) iterator.next();
            if ("0".equals(general.getBelongTo())) {
                wineGenerals.add(general);
            }
        }
        if (wineGenerals.size() <= 3) {
            return wineGenerals;
        } else {
            //找到最佳3个 排序算法
            List<General> sortBestThreeGeneral = sortBestThreeGeneral(wineGenerals, id, 3);
            return sortBestThreeGeneral;
        }


    }

    // 排序来返回最合适的i个武将
    private static List<General> sortBestThreeGeneral(List<General> wineGenerals, final Integer id, int max) {
        //按照适应性进行排序
        wineGenerals.sort((o1, o2) -> {
            if (Integer.parseInt(o2.getRelations().substring(2 * (id - 1), 2 * (id - 1) + 2))
                    > Integer.parseInt(o1.getRelations().substring(2 * (id - 1), 2 * (id - 1) + 2))) {
                return 1;
            } else if (Integer.parseInt(o2.getRelations().substring(2 * (id - 1), 2 * (id - 1) + 2))
                    < Integer.parseInt(o1.getRelations().substring(2 * (id - 1), 2 * (id - 1) + 2))) {
                return -1;
            } else {
                return 0;
            }
        });
        return wineGenerals.subList(0, max);
    }

    /**
     * @param @param  id
     * @param @return 参数说明
     * @return List<General>    返回类型
     * @throws
     * @Title: getaoundGeneral
     * @Description: 根据传入的主公所属武将 返回随身的所有武将
     */
    public static List<General> getAoundGeneral(List<General> list) {
        List<General> generals = new ArrayList<General>(10);
        for (General general : list) {
            if ((general.getCityId() == null || "".equals(general.getCityId()))) {
                if ("0".equals(general.getStatus())) {
                    generals.add(general);
                }
            }
        }
        return generals;
    }

    /**
     * @param @param  id
     * @param @return 参数说明
     * @return List<General>    返回类型
     * @throws
     * @Title: getaoundGeneral
     * @Description: 根据传入的主公所属武将 返回随身的被俘虏的武将
     */
    public static List<General> getCapturedGenerals(List<General> list) {
        List<General> generals = new ArrayList<General>(10);
        for (General general : list) {
            if ("".equals(general.getCityId()) && "4".equals(general.getStatus())) {
                generals.add(general);
            }
        }
        return generals;
    }

    /***
     *
     * @author Eric
     * @date 17:21 2019/7/29
     * @param leader
     * @param generals
     * @param type 0 酒馆  1 攻城选择武将  2 野战选择武将  3 单挑选择武将 4 配置守城武将 9 其他
     * @throws
     * @return cn.eric.game.fujiatianxia6.po.General
     **/
    public static General getGeneralByChoose(General leader, List<General> generals, int type, City city) {

        List<General> aoundGenerals;
        if (null == generals) {
            aoundGenerals = GeneralFactory.getAoundGeneral(leader.getGenerals());
        } else {
            aoundGenerals = generals;
        }
        // 如果已经没有随身武将 或者没有酒馆武将了，就返回null
        if (aoundGenerals.size() == 0) {
            return null;
        }
        int index = 1;
        General[] temp = new General[aoundGenerals.size() + 1];
        for (Iterator iterator = aoundGenerals.iterator(); iterator.hasNext(); ) {
            General g = (General) iterator.next();
            if (!leader.isReboot()) {
                System.out.println(index + ":" + g.toString());
            }
            temp[index++] = g;
        }
        if (leader.isReboot()) {
            switch (type) {
                case 0:
                    return temp[1];
                case 1:
                    GeneralFactory.sortByCommand(aoundGenerals);
                    //return aoundGenerals.get(aoundGenerals.size()-1);
                    return aoundGenerals.get(0);
                case 2:
                    GeneralFactory.sortByCommand(aoundGenerals);
                    // 根据技能加权
                    return findBySkill(aoundGenerals, city, "0", null);
                case 3:
                    GeneralFactory.sortByAttack(aoundGenerals);
                    //return aoundGenerals.get(aoundGenerals.size()-1);
                    return aoundGenerals.get(0);
                case 4:
                    // 身边至少留三个武将
                    if (aoundGenerals.size() <= 3) {
                        return null;
                    }
                    // 一个城市最多放4个武将
                    if (city.getDenfenceGenerals().size() >= 4) {
                        return null;
                    }
                    // 如果城市已经很多，武将又不是很多，也不设置 要保证主公身边
                    List<City> cityByLeader = CityFactory.findCityByLeader(leader);
                    if (cityByLeader.size() > aoundGenerals.size()
                            && ((cityByLeader.size() - aoundGenerals.size()) > aoundGenerals.size() * 0.6)) {
                        return null;
                    }
                    // 如果士兵数量少于4000 返回null
                    if (leader.getArmy() < 4000) {
                        return null;
                    }
                    // 根据技能值 加权取将军
                    GeneralFactory.sortByCommand(aoundGenerals);
                    return findBySkill(aoundGenerals, city, leader.getId(), null);
                default:
                    return null;
            }
        }
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("请选择武将");
            try {
                int choise = input.nextInt();
                if (choise == 0) {
                    return null;
                } else if (choise < 0 || (choise >= aoundGenerals.size() + 1)) {
                    System.out.println("请选择合适的数字");
                } else {
                    try {
                        return temp[choise];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (InputMismatchException exception) {
                System.out.println("请选择合适的数字");
            }
        }

    }

    private static General findBySkill(List<General> aoundGenerals, City city, String leaderId, List<General> exclude) {
        List<String> superSkills = SkillFactory.superSkills;
        Optional<General> optional = aoundGenerals.stream()
                .filter(e -> !e.getId().equals(leaderId))
                .filter(e -> superSkills.contains(e.getSkill())).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        // 平原
        if (city.getTopography() == 1) {
            List<String> skills = SkillFactory.cavalrysSkills;
            optional = aoundGenerals.stream().filter(e -> !e.getId().equals(leaderId))
                    .filter(e -> skills.contains(e.getSkill())).findFirst();
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        // 山地
        if (city.getTopography() == 2) {
            List<String> skills = SkillFactory.infantrySkills;
            optional = aoundGenerals.stream().filter(e -> !e.getId().equals(leaderId))
                    .filter(e -> skills.contains(e.getSkill())).findFirst();
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        // 水域
        if (city.getTopography() == 3) {
            List<String> skills = SkillFactory.archersSkills;
            optional = aoundGenerals.stream().filter(e -> !e.getId().equals(leaderId))
                    .filter(e -> skills.contains(e.getSkill())).findFirst();
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        // 通用型
        List<String> skills = SkillFactory.allArmsSkills;
        optional = aoundGenerals.stream().filter(e -> !e.getId().equals(leaderId))
                .filter(e -> skills.contains(e.getSkill())).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        // 主公不能作为守城将领
        if (leaderId.equals(aoundGenerals.get(0).getId())) {
            return aoundGenerals.get(1);
        }
        return aoundGenerals.get(0);

    }

    public static void sortByAttack(List<General> aoundGenerals) {
        aoundGenerals.sort(Comparator.comparingInt((General o) -> Integer.parseInt(o.getAttack())).reversed());
    }

    /**
     * @param @param  city
     * @param @return 参数说明
     * @return General    返回类型
     * @throws
     * @Title: generalByDefenceAuto
     * @Description: 防守方自动派出一个武力最高的武将参加单挑
     */
    public static General generalByDefenceAuto(City city) {
        // 获得这个城市里面的所有武将，并按照所有的属性和进行排序
        List<General> denfenceGenerals = city.getDenfenceGenerals();

        Collections.sort(denfenceGenerals, new Comparator<General>() {

            @Override
            public int compare(General g1, General g2) {
                //Integer g1sum =
                //        Integer.parseInt(g1.getAttack()) + Integer.parseInt(g1.getIntelligence()) + Integer
                //        .parseInt(g1.getVitality());
                Integer g1sum = Integer.parseInt(g1.getAttack());
                //Integer g2sum =
                //        Integer.parseInt(g2.getAttack()) + Integer.parseInt(g2.getIntelligence()) + Integer
                //        .parseInt(g2.getVitality());
                Integer g2sum = Integer.parseInt(g2.getAttack());
                return g2sum.compareTo(g1sum);
            }

        });
        //返回第0个元素
        return denfenceGenerals.get(0);
    }

    /**
     * @param
     * @param @param bF
     * @param @param defence    设定文件
     * @param @param type  1 普通 2 骑兵 3 枪兵 4 弓兵
     * @return void    返回类型
     * @throws
     * @Title: chooseFieldGenerals
     * @Description: 自动选择野战的统帅和军师
     */
    public static void chooseFieldGenerals(BattleField bF, City defence) {
        List<General> denfenceGenerals = defence.getDenfenceGenerals();
        //先选择主将 统帅、武力、智力，按照统帅排序后找到第一个
        sortByCommand(denfenceGenerals);
        bF.setDefenceChief(denfenceGenerals.get(0));
        System.out.println("防守主将：" + denfenceGenerals.get(0).toString());
        denfenceGenerals.remove(0);
        //其次军师 统帅加成、智力加成、后期有地形适应性加成
        if (denfenceGenerals.size() > 0) {
            sortByCommand(denfenceGenerals);
            bF.setDefenceCounsellor(denfenceGenerals.get(0));
            System.out.println("防守副将：" + denfenceGenerals.get(0).toString());
            denfenceGenerals.remove(0);
        }
        if (denfenceGenerals.size() > 0) {
            sortByIntel(denfenceGenerals);
            bF.setDefenceVice(denfenceGenerals.get(0));
            System.out.println("防守军师：" + denfenceGenerals.get(0).toString());
            denfenceGenerals.remove(0);
        }
        bF.setCity(defence);
    }

    // 按照智力排序
    public static void sortByIntel(List<General> denfenceGenerals) {
        denfenceGenerals.sort(Comparator.comparingInt((General o) -> Integer.parseInt(o.getIntelligence())).reversed());
    }

    // 按照统帅排序
    public static void sortByCommand(List<General> denfenceGenerals) {
        denfenceGenerals.sort(Comparator.comparingInt((General o) -> Integer.parseInt(o.getCommand())).reversed());
    }

    // 按照政治排序
    public static void sortByPolitics(List<General> denfenceGenerals) {
        denfenceGenerals.sort(Comparator.comparingInt((General o) -> Integer.parseInt(o.getPolitics())).reversed());
    }

    // 按照魅力排序
    public static void sortByCharm(List<General> denfenceGenerals) {
        denfenceGenerals.sort(Comparator.comparingInt((General o) -> Integer.parseInt(o.getCharm())).reversed());
    }


    public static void chooseDefenceCityGenerals(AttackCity ac, City defence) {
        List<General> denfenceGenerals = defence.getDenfenceGenerals();

        //先选择主将 统帅、武力、智力，按照统帅排序后找到第一个
        GeneralFactory.sortByCommand(denfenceGenerals);
        // 主将
        ac.setDefenceChief(denfenceGenerals.get(0));
        System.out.println("防守主将：" + ac.getDefenceChief().toString());
        List<General> exclude = new ArrayList<>();
        if (denfenceGenerals.size() > 1) {
            exclude.add(ac.getDefenceChief());
            ac.setDefenceVice(findBySkill(denfenceGenerals, defence, "0", exclude));
            System.out.println("防守副将：" + ac.getDefenceVice().toString());
        }
        if (denfenceGenerals.size() > 2) {
            exclude.add(ac.getDefenceVice());
            ac.setDefenceCounsellor(findBySkill(denfenceGenerals, defence, "0", exclude));
            System.out.println("防守军师：" + ac.getDefenceCounsellor().toString());
        }

        //denfenceGenerals.remove(0);
    }

    /**
     * @param @param defence    设定文件
     * @return void    返回类型
     * @throws
     * @Title: dead
     * @Description: 阵亡了
     */
    public static void dead(General defence) {
        General leader = GeneralFactory.getGeneralById(defence.getBelongTo());
        List<General> generals = leader.getGenerals();
        for (General general : generals) {
            if (general.getId().equals(defence.getId())) {
                generals.remove(general);
                break;
            }
        }
        City cityById = CityFactory.getCityById(defence.getCityId());
        if (cityById != null) {
            List<General> denfenceGenerals = cityById.getDenfenceGenerals();
            if (denfenceGenerals != null && denfenceGenerals.size() > 0) {
                for (General denfenceGeneral : denfenceGenerals) {
                    if (denfenceGeneral.getId().equals(defence.getId())) {
                        denfenceGenerals.remove(denfenceGeneral);
                        break;
                    }
                }
            }
        }
        defence.setBelongTo("");
        defence.setStatus("3");
        defence.setCityId("");
    }

    /**
     * @param @param lost
     * @param @param win    设定文件
     * @return void    返回类型
     * @throws
     * @Title: beCatch
     * @Description: 俘虏方法
     */
    public static void beCatch(General lost, General win) {
        // 先从原来的主公换成新的主公
        General lordOld = GeneralFactory.getGeneralById(lost.getBelongTo());
        General lordNew = GeneralFactory.getGeneralById(win.getBelongTo());
        // 从原来的主公队列中去除
        List<General> generals = lordOld.getGenerals();
        for (General general : generals) {
            if (general.getId().equals(lost.getId())) {
                generals.remove(general);
                break;
            }
        }
        // 保险起见
        lordOld.setGenerals(generals);
        // 加入到新的队列
        lost.setBelongTo(win.getBelongTo());
        lordNew.getGenerals().add(lost);
        // 状态是4表示还不能使用
        lost.setStatus("4");
        lost.setCityId("");

        // 如果此时有技能触发
        SkillFactory.changeAfter(4, 1, lordNew, lost, null);

    }

    // 所有在野武将
    public static List<General> allFreeGenerals() {
        List<General> collect = initGenerals.stream().filter(a -> "0".equals(a.getBelongTo())).collect(Collectors.toList());

        return collect;
    }

    // 攻防机器人自动选择单挑武将，按照武力高低
    public static General generalByAttactAuto(General attack, List<General> getaoundGeneral) {
        return getaoundGeneral.stream().sorted(Comparator.comparing(General::getAttack, Comparator.reverseOrder()))
                .collect(Collectors.toList()).get(0);
    }

    // 检查已经死掉的主公
    public static void checkDeadGenerals(General[] players) {
        for (int i = 0; i < players.length; i++) {
            checkDead(players[i]);
        }
    }

    public static void checkDead(General player) {
        if (player.getMoney() <= 0 && !"4".equals(player.getStatus())) {
            System.out.println(player.getName() + "已经破产，所有武将下野，所属城市武将下野，士兵减半，城市发展金减半");
            List<General> generals = player.getGenerals();
            List<City> cityList = CityFactory.findCityByLeader(player);

            for (City city : cityList) {
                city.setBelongTo(0);
                city.setMoney(city.getMoney() / 2);
                city.setSoilders(city.getSoilders() / 2);
                city.setInfantry(city.getInfantry() / 2);
                city.setCavalrys(city.getCavalrys() / 2);
                city.setArchers(city.getArchers() / 2);
                city.setDenfenceGenerals(new ArrayList<>(4));
            }

            for (General general : generals) {
                if (general.getId().equals(player.getId())) {
                    // general.setStatus("4");
                } else {
                    general.setBelongTo("0");
                    general.setStatus("0");
                    general.setCityId("");
                }
            }

            player.setCityId("");
            player.setStatus("4");
        }
    }

    public static void getMoneyAndArmyByReputation(General[] players) {

        for (General player : players) {
            if ("4".equals(player.getStatus())) {
                continue;
            }
            // 增加金钱
            player.setMoney(player.getMoney() + player.getReputation() / 3);
            // 增加士兵
            player.setArmy(player.getArmy() + player.getReputation() / 10);
            player.setArchers(player.getArchers() + player.getReputation() / 30);
            player.setInfantry(player.getInfantry() + player.getReputation() / 30);
            player.setCavalrys(player.getCavalrys() + player.getReputation() / 30);
        }
    }

    /**
     * @MethodName: recruit
     * @Description: 招募被俘虏武将
     * @Param: [capturedGenerals, player]
     * @Return: void
     * @Author: YCKJ2725
     * @Date: 2020/2/26 16:30
     **/
    public static void recruit(List<General> capturedGenerals, General player) {
        int id = Integer.parseInt(player.getId());
        System.out.println("被俘虏武将的对您的亲和度如下：");
        int choose = 1;
        for (General capturedGeneral : capturedGenerals) {
            System.out.print(choose + ":亲和度:" + capturedGeneral.getRelations().substring(2 * (id - 1), 2 * (id - 1) + 2));
            System.out.print(capturedGeneral.toString());
            System.out.println();
        }
        System.out.println("请选择您要亲厚的武将，每100块增加1点好感度，好感度越大被感动几率越大，好感度100直接归顺，0放弃");
        if (player.isReboot()) {
            choose = 1;
            General general = capturedGenerals.get(choose - 1);
            String relation = general.getRelations().substring(2 * (id - 1), 2 * (id - 1) + 2);
            System.out.println("亲和度:" + relation + ",赠与1000金");

            int money = 1000;
            if (money > player.getMoney()) {
                System.out.println("金额不足:" + player.getMoney());
            } else {
                player.setMoney(player.getMoney() - money);
                int add = money / 100;
                int newRelation = Integer.parseInt(relation) + add;
                if (newRelation > 99) {
                    newRelation = 99;
                }
                if (newRelation >= 99) {
                    general.setBelongTo(player.getId());
                    general.setStatus("0");
                    System.out.println("武将" + general.getName() + "拜入帐下");
                } else {
                    // 有几率被感动
                    int rate = 99 - (newRelation);
                    if (new Random().nextInt(99) >= rate) {
                        general.setBelongTo(player.getId());
                        general.setStatus("0");
                        System.out.println("武将" + general.getName() + "拜入帐下");
                    } else {
                        System.out.println("武将心如磐石");
                    }
                }
                // 替换新的relation
                String newRelations = general.getRelations().substring(0, 2 * (id - 1)) + newRelation + general.getRelations().substring(2 * (id - 1) + 2);
                System.out.println("新的亲和度：" + newRelations);
                general.setRelations(newRelations);
            }
            return;
        }

        // 不是机器人
        Scanner input = new Scanner(System.in);
        choose = input.nextInt();
        while (choose != 0) {
            if (choose > capturedGenerals.size() + 1) {
                System.out.println("请重新选择");
                input = new Scanner(System.in);
                choose = input.nextInt();
                continue;
            }
            General general = capturedGenerals.get(choose - 1);
            String relation = general.getRelations().substring(2 * (id - 1), 2 * (id - 1) + 2);
            System.out.println("亲和度:" + relation);
            System.out.println("请输入亲厚的金钱，100的倍数");
            int money = input.nextInt();
            if (money > player.getMoney()) {
                System.out.println("金额不足:" + player.getMoney());
                continue;
            } else {
                player.setMoney(player.getMoney() - money);
            }
            int add = money / 100;
            int newRelation = Integer.parseInt(relation) + add;
            if (newRelation > 99) {
                newRelation = 99;
            }
            if (newRelation >= 99) {
                general.setBelongTo(player.getId());
                general.setStatus("0");
                System.out.println("武将" + general.getName() + "拜入帐下");
            } else {
                // 有几率被感动
                int rate = 99 - (newRelation);
                if (new Random().nextInt(99) >= rate) {
                    general.setBelongTo(player.getId());
                    general.setStatus("0");
                    System.out.println("武将" + general.getName() + "拜入帐下");
                    break;
                } else {
                    System.out.println("武将心如磐石");
                }
            }
            // 替换新的relation
            String newRelations = general.getRelations().substring(0, 2 * (id - 1)) + newRelation + general.getRelations().substring(2 * (id - 1) + 2);
            System.out.println("新的亲和度：" + newRelations);
            general.setRelations(newRelations);
            System.out.println("请选择您要亲厚的武将，每100块增加1点好感度，好感度越大被感动几率越大，好感度99直接归顺，0放弃");
            input = new Scanner(System.in);
            choose = input.nextInt();
        }
    }

    //  LYF DL GL YY ZM
    public static void militarySpending(General[] players) {
        for (General player : players) {
            if ("4".equals(player.getStatus())) {
                continue;
            }
            // 一个士兵 0.02金币  骑兵 枪兵 弓兵  0.04 金币  后期高级兵种  0.3金币
            int cost = (int) (player.getArmy() * 0.02 + (player.getCavalrys() + player.getInfantry() + player.getArchers()) * 0.04);
            System.out.println("玩家" + player.getName() + "--->消耗军资:" + cost);
            player.setMoney(player.getMoney() - cost);
            if (player.getMoney() < 0) {
                int armyReduce = Math.min(200, player.getArmy());
                int cavalryReduce = Math.min(100, player.getCavalrys());
                int infantryReduce = Math.min(100, player.getInfantry());
                int archerReduce = Math.min(100, player.getArchers());
                System.out.println("玩家没有足够的军资,逃跑士兵" + armyReduce + ",逃跑骑兵" + cavalryReduce
                        + ",逃跑枪兵" + infantryReduce + ",逃跑弓兵" + archerReduce);

                player.setArmy(player.getArmy() - armyReduce);
                player.setCavalrys(player.getCavalrys() - cavalryReduce);
                player.setInfantry(player.getCavalrys() - infantryReduce);
                player.setArchers(player.getCavalrys() - archerReduce);
                player.setMoney(0);
            }
        }
    }

    /**
     * @MethodName: resetWildGeneral
     * @Description: 将无主公上阵的武将设定为在野
     * @Param: [players]
     * @Return: void
     * @Author: YCKJ2725
     * @Date: 2020/11/25 19:59
     **/
    public static void resetWildGeneral(General[] players) {
        List<General> playersList = Arrays.asList(players);
        for (General initGeneral : initGenerals) {
            // 不是主公 belongto不为0 且主公未上阵
            String belongTo = initGeneral.getBelongTo();
            if (!"1".equals(initGeneral.getKing()) && !"0".equals(belongTo)) {
                if (!playersList.contains(getGeneralById(belongTo))) {
                    initGeneral.setBelongTo("0");
                }
            }
        }
    }

    public static void showGeneralInfo(List<General> generals) {
        for (General general : generals) {
            System.out.println(general.toString());
        }
    }
}
