package cn.eric.game.fujiatianxia6.factory;

import java.util.*;
import java.util.stream.Collectors;

import cn.eric.game.fujiatianxia6.po.AttackCity;
import cn.eric.game.fujiatianxia6.po.BattleField;
import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;
import org.dom4j.DocumentException;

import cn.eric.game.fujiatianxia6.test.Dom4JforXML;

/**
 * <p>Title: GeneralFactory<／p>
 * <p>Description: 武将工厂类<／p>
 * <p>Company: want-want<／p>
 *
 * @author 00322027
 * @date 2017年8月9日 上午9:18:14
 */
public class GeneralFactory {

    private static List<General> initGenerals = new ArrayList<General>(100);

    public static List<General> getInitGenerals() {
        return initGenerals;
    }

    public static void setInitGenerals(List<General> initGenerals) {
        GeneralFactory.initGenerals = initGenerals;
    }

    /**
     * @param 参数说明
     * @return void    返回类型
     * @throws DocumentException
     * @Title: initGeneralList
     * @Description: 游戏开始时初始化所有武将
     */
    public GeneralFactory() {
        try {
            initGenerals = Dom4JforXML.test2();
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
        int a = 1;
        Collections.sort(wineGenerals, (o1, o2) -> {
            if (Integer.parseInt(o2.getRelations().substring(2 * (id - 1), 2 * (id - 1) + 2)) >= Integer.parseInt(o1.getRelations().substring(2 * (id - 1), 2 * (id - 1) + 2))) {
                return 1;
            } else {
                return -1;
            }
        });
        if(a == 1){
            a = 2;
        }
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
    public static List<General> getaoundGeneral(List<General> list) {
        List<General> generals = new ArrayList<General>(10);
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            General general = (General) iterator.next();
            if ("".equals(general.getCityid()) && "0".equals(general.getStatus())) {
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
    public static General getGeneralByChoose(General leader, List<General> generals,int type) {

        List<General> aoundGenerals;
        if (null == generals) {
            aoundGenerals = GeneralFactory.getaoundGeneral(leader.getGenerals());
        } else {
            aoundGenerals = generals;
        }
        // 如果已经没有随身武将 或者没有酒馆武将了，就返回null
        if(aoundGenerals == null || aoundGenerals.size() == 0){
            return null;
        }
        int index = 1;
        General[] temp = new General[aoundGenerals.size() + 1];
        for (Iterator iterator = aoundGenerals.iterator(); iterator.hasNext(); ) {
            General g = (General) iterator.next();
            System.out.println(index + " :姓名：" + g.getName() + "武力：" + g.getAttack());
            temp[index++] = g;
        }
        if(leader.isReboot()){
            switch (type){
                case 0:
                    return temp[1];
                case 1:
                    GeneralFactory.sortByCommand(aoundGenerals);
                    return aoundGenerals.get(aoundGenerals.size()-1);
                case 2:
                    GeneralFactory.sortByCommand(aoundGenerals);
                    return aoundGenerals.get(aoundGenerals.size()-1);
                case 3:
                    GeneralFactory.sortByAttack(aoundGenerals);
                    return aoundGenerals.get(aoundGenerals.size()-1);
                case 4:
                    if(aoundGenerals.size() <= 2){
                        return null;
                    }
                    GeneralFactory.sortByIntel(aoundGenerals);
                    return aoundGenerals.get(aoundGenerals.size()-1);
                default:
                    return null;
            }
        }
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("请选择武将");
            int choise = input.nextInt();
            if (choise == 0) {
                return null;
            } else if (choise < 0 || (choise > aoundGenerals.size() + 1)) {
                System.out.println("请选择合适的数字");
            } else {
                try {
                    return temp[choise];
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void sortByAttack(List<General> aoundGenerals) {
        Collections.sort(aoundGenerals, new Comparator<General>() {
            @Override
            public int compare(General o1, General o2) {
                if (Integer.parseInt(o1.getAttack()) >= Integer.parseInt(o2.getAttack())) return 1;
                else return -1;
            }
        });
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
                Integer g1sum = Integer.parseInt(g1.getAttack()) + Integer.parseInt(g1.getIntelligence()) + Integer.parseInt(g1.getVitality());
                Integer g2sum = Integer.parseInt(g2.getAttack()) + Integer.parseInt(g2.getIntelligence()) + Integer.parseInt(g2.getVitality());
                return g1sum.compareTo(g2sum);
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
            sortByIntel(denfenceGenerals);
            bF.setAttackCounsellor(denfenceGenerals.get(0));
            System.out.println("防守军师：" + denfenceGenerals.get(0).toString());
            denfenceGenerals.remove(0);
        }
        //再次副将 武力加成、后期有兵种和地形适应性加成

        if (denfenceGenerals.size() > 0) {
            sortByCommand(denfenceGenerals);
            bF.setAttackVice(denfenceGenerals.get(0));
            System.out.println("防守副将：" + denfenceGenerals.get(0).toString());
            denfenceGenerals.remove(0);
        }

        bF.setCity(defence);
    }

    // 按照智力排序
    public static void sortByIntel(List<General> denfenceGenerals) {
        Collections.sort(denfenceGenerals, new Comparator<General>() {
            @Override
            public int compare(General o1, General o2) {
                if (Integer.parseInt(o1.getIntelligence()) >= Integer.parseInt(o2.getIntelligence())) {
                    return 1;
                }
                else {return -1;}
            }
        });
    }

    // 按照统帅排序
    public static void sortByCommand(List<General> denfenceGenerals) {
        Collections.sort(denfenceGenerals, new Comparator<General>() {
            @Override
            public int compare(General o1, General o2) {
                if (Integer.parseInt(o1.getCommand()) >= Integer.parseInt(o2.getCommand())) return 1;
                else return -1;
            }
        });
    }

    // 按照政治排序
    public static void sortByPolitics(List<General> denfenceGenerals) {
        Collections.sort(denfenceGenerals, new Comparator<General>() {
            @Override
            public int compare(General o1, General o2) {
                if (Integer.parseInt(o1.getPolitics()) >= Integer.parseInt(o2.getPolitics())) return 1;
                else return -1;
            }
        });
    }

    public static void chooseDefenceCityGenerals(AttackCity ac, City defence) {
        List<General> denfenceGenerals = defence.getDenfenceGenerals();
        //先选择主将 统帅、武力、智力，按照统帅排序后找到第一个
        Collections.sort(denfenceGenerals, new Comparator<General>() {
            @Override
            public int compare(General o1, General o2) {
                if (Integer.parseInt(o1.getCommand()) >= Integer.parseInt(o2.getCommand())) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
        });
        ac.setDefenceChief(denfenceGenerals.get(0));
        System.out.println("防守主将：" + denfenceGenerals.get(0).toString());

        if(denfenceGenerals.size() > 1){
            ac.setDefenceCounsellor(denfenceGenerals.get(1));
            System.out.println("防守副将：" + denfenceGenerals.get(1).toString());
        }
        if(denfenceGenerals.size() > 2){
            ac.setDefenceVice(denfenceGenerals.get(2));
            System.out.println("防守军师：" + denfenceGenerals.get(2).toString());
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
        defence.setBelongTo("");
        defence.setStatus("3");
        defence.setCityid("");
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
        // 加入到新的队列
        lost.setBelongTo(win.getBelongTo());
        lordNew.getGenerals().add(lost);
        // 状态是4表示还不能使用
        lost.setStatus("4");
        lost.setCityid("");

        // 如果此时有技能触发
        SkillFactory.changeAfter(4, 1, lordNew, lost, null);

    }

    // 所有在野武将
    public static List<General> allFreeGenerals(){
        List<General> collect = initGenerals.stream().filter(a -> "0".equals(a.getBelongTo())).collect(Collectors.toList());

        return collect;
    }

    // 攻防机器人自动选择单挑武将，按照武力高低
    public static General generalByAttactAuto(General attack, List<General> getaoundGeneral) {
        return getaoundGeneral.stream().sorted(Comparator.comparing(General::getAttack,Comparator.reverseOrder()))
                .collect(Collectors.toList()).get(0);
    }

    // 检查已经死掉的主公
    public static void checkDeadGenerals(General[] players) {

        for (int i = 0; i < players.length; i++) {
            checkDead(players[i]);
        }


    }

    private static void checkDead(General player) {
        if(player.getMoney() <= 0){
            System.out.println(player.getName()+"已经破产，所有武将下野，所属城市武将下野，士兵减半，城市发展金减半");
            List<General> generals = player.getGenerals();
            List<City> cityList = CityFactory.findCityByLeader(player);

            for (City city : cityList) {
                city.setBelongTo(0);
                city.setMoney(city.getMoney()/2);
                city.setSoilders(city.getSoilders()/2);
                city.setInfantry(city.getInfantry()/2);
                city.setCavalrys(city.getCavalrys()/2);
                city.setArchers(city.getArchers()/2);
            }

            for (General general : generals) {
                if(general.getId().equals(player.getId())){
                    // general.setStatus("4");
                }else{
                    general.setBelongTo("0");
                    general.setStatus("0");
                    general.setCityid("");
                }
            }

            player.setCityid("");
            player.setStatus("4");
        }
    }
}
