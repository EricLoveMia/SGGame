package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;

import java.util.*;

public class CityFactory {

    public static City[] citys = new City[50];

    public static void init() { //22
        citys = new City[50];
        City city1 = new City(1, 1, "襄平", "幽州 位于辽东半岛今辽阳市。自董卓任命公孙度为太守以来，至238年止都由公孙氏管理", 100, 1000, 300, 1);
        City city2 = new City(2, 1, "北平", "幽州 公孙瓒以此地为治所，并利用渔阳的盐铁之利，发展成一座商业都市", 100, 1000, 400, 1);
        City city3 = new City(3, 1, "蓟", "幽州 幽州牧－刘虞的据点。刘虞采用怀柔政策，致力于此地与乌丸族间的和睦", 100, 1000, 300, 2);
        City city4 = new City(4, 1, "南皮", "冀州 渤海郡的中心都市，袁绍的据点。204年袁谭逃到此地，与曹操间发生争战", 100, 1000, 400, 2);
        City city5 = new City(5, 1, "平原", "冀州 连接冀州和青州、徐州方面的要地，曾受刘备、袁谭等人统治，后来曹植封侯于此地", 100, 1000, 300, 1);
        City city6 = new City(6, 2, "邺", "冀州 冀州最富饶的都市。191年，袁绍自韩馥的手中夺取此地，204年，曹操大破审配而取代之", 200, 1000, 450, 3);
        City city7 = new City(7, 1, "北海", "青州 孔融曾任此地的太守，抵挡黄巾军。而在官渡之战前，则由袁谭统治", 100, 1000, 350, 3);
        City city8 = new City(8, 1, "濮阳", "兖州 水运及交通相当发达，深具商业、战略价值之地。194年，曹操和吕布在这里展开激烈的殊死战", 100, 1000, 350, 2);
        City city9 = new City(9, 1, "陈留", "兖州 此处为交通发达、人材物资集中的要地。189年，曹操从这里起兵，发布董卓的檄文", 100, 1000, 350, 1);
        City city10 = new City(10, 1, "下邳", "徐州 被泗水与沂水环绕的徐州要冲。198年，曹操利用水攻在此最终歼灭吕布", 100, 1000, 300, 3);
        City city11 = new City(11, 1, "小沛", "徐州 徐州对西方的最前线基地。在徐州争夺战中，吕布和刘备在此地大动干戈，曹操也加入了战局", 100, 1000, 300, 1);
        City city12 = new City(12, 1, "许昌", "豫州 原名许。196年9月，曹操自洛阳迎献帝迁都至此，221年1月，曹丕将此处易名为许昌", 100, 1000, 500, 1);
        City city13 = new City(13, 1, "汝南", "豫州 袁氏郡望.200年官渡作战期间,刘备脱离袁绍,与刘辟等在此活动,袭扰曹操后方.201年,遭曹操攻陷", 100, 1000, 350, 2);
        City city14 = new City(14, 2, "洛阳", "司隶 自光武帝－刘秀由长安迁都至此后,此处便成东汉之首都,繁盛一时.220年曹丕即位,又成了魏国的首都", 100, 1000, 600, 1);
        City city15 = new City(15, 2, "长安", "雍州 高祖－刘邦在此地建立了西汉的首都。211年的渭水之战中，马超与曹操在此交锋", 100, 1000, 600, 2);
        City city16 = new City(16, 2, "建业", "扬州 原名秣陵。212年，孙权听从张纮遗言，建都于此以抗魏军，同年改名", 100, 1000, 500, 3);
        City city17 = new City(17, 1, "吴", "扬州 山越族的严白虎占据之地。196年，被孙权攻破，此后到212年之间，都是孙氏的都城", 100, 1000, 450, 3);
        City city18 = new City(18, 1, "柴桑", "扬州 东吴的水军基地，周瑜便是在这里训练水军。赤壁之战时孙权屯驻于此，并接获曹操‘会猎江东’的书简", 100, 1000, 350, 3);
        City city19 = new City(19, 1, "襄阳", "荆州 曾为刘表的据点。208年起归属曹操。219年，曹仁死守樊城，挡住关羽的水攻", 100, 1000, 500, 1);
        City city20 = new City(20, 1, "长沙", "荆州 197年，孙坚平定区星的叛乱后，便成为本地的太守，后来改由韩玄担任", 100, 1000, 400, 3);
        City city21 = new City(21, 1, "汉中", "益州 道教五斗米道的中心地。215年，曹操自张鲁手中取得，但在219年，又遭刘备夺去", 100, 1000, 500, 1);
        City city22 = new City(22, 2, "成都", "益州 214年，刘备降服刘璋后，即以次为根据地。221年刘备即位，这里便成为蜀汉的首都", 200, 1000, 500, 2);
        City city23 = new City(23, 2, "彭城", "徐州 西汉设彭城郡，东汉设彭城国、建都彭城, 三国时，曹操迁徐州刺史部于彭城，彭城始称徐州。", 200, 1000, 400, 1);
        City city24 = new City(24, 1, "广陵", "徐州 汉代，今扬州称广陵、江都", 200, 1000, 300, 2);
        City city25 = new City(25, 1, "琅邪", "徐州 徐州属地", 200, 1000, 300, 3);
        City city26 = new City(26, 1, "东海", "徐州 徐州属地", 200, 1000, 300, 3);
        City city27 = new City(27, 1, "赤壁", "荆州 赤壁", 200, 1000, 300, 3);
        City city28 = new City(28, 1, "荆州", "荆州 赤壁之战后，曹操、刘备、孙权三家分荆州：曹操占据南阳、南郡二郡，刘备占据长江以南的零陵、桂阳 、武陵、长沙四郡，孙权则占据江夏郡", 200, 1000, 300, 3);
        City city29 = new City(29, 1, "南郡", "荆州 东汉末年和三国时期治所在公安。唐代南郡更名为江陵郡，后来又改为江陵府。", 200, 1000, 300, 1);
        City city30 = new City(30, 1, "江夏", "荆州 魏、吴各置江夏郡，魏江夏郡初治石阳县（今武汉市黄陂区西南），后迁上昶城（今湖北省云梦西南）", 200, 1000, 300, 3);
        City city31 = new City(31, 1, "武陵", "荆州 三国时的武陵郡，治所仍在临沅(今常德市武陵区和鼎城区的大部)，初属蜀，后属吴", 200, 1000, 300, 2);
        City city32 = new City(32, 1, "零陵", "荆州 汉献帝建安三年(198)，荆州牧刘表攻占零陵，零陵境域属刘表势力范围", 200, 1000, 300, 2);
        City city33 = new City(33, 1, "桂阳", "荆州 郡治在今天的郴州市区，领十一个县，其范围基本包括今天的郴州各个区县，甚至还有广东的北部的一部分，如阳山、含洭、曲江、浈阳、桂阳等县",
                200, 1000, 300, 1);
        City city34 = new City(34, 1, "南乡", "荆州 南乡", 200, 1000, 300, 1);

        citys[1] = city1;
        citys[2] = city2;
        citys[3] = city3;
        citys[4] = city4;
        citys[5] = city5;
        citys[6] = city6;
        citys[7] = city7;
        citys[8] = city8;
        citys[9] = city9;
        citys[10] = city10;
        citys[11] = city11;
        citys[12] = city12;
        citys[13] = city13;
        citys[14] = city14;
        citys[15] = city15;
        citys[16] = city16;
        citys[17] = city17;
        citys[18] = city18;
        citys[19] = city19;
        citys[20] = city20;
        citys[21] = city21;
        citys[22] = city22;
        citys[23] = city23;
        citys[24] = city24;
        citys[25] = city25;
        citys[26] = city26;
        citys[27] = city27;
        citys[28] = city28;
        citys[29] = city29;
        citys[30] = city30;
        citys[31] = city31;
        citys[32] = city32;
        citys[33] = city33;
        citys[34] = city34;

        for (int i = 35; i < 50; i++) {
            citys[i] = null;
        }
        //}
    }

    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title: computeMoney
     * @Description: 计算金钱
     */
    public static void computeMoney() {
        // 所有已经有主的城市
        for (int i = 1; i < citys.length; i++) {
            if (citys[i] != null) {
                if (citys[i].getBelongTo() != null && citys[i].getBelongTo() > 0) {
                    //如果没有守城的人或者没有资金，跳过
                    if (citys[i].getDenfenceGenerals().size() > 0 && citys[i].getMoney() > 0) {
                        int politics = 0;
                        for (Iterator iterator = citys[i].getDenfenceGenerals().iterator(); iterator.hasNext(); ) {
                            General g = (General) iterator.next();
                            politics += Integer.parseInt(g.getPolitics());
                        }
                        // 政治总和/1000 * 繁荣度 + 原来的钱
                        // 看看有没有技能触发
                        int add = politics * citys[i].getProsperity() * citys[i].getType() / 1000;
                        add = SkillFactory.CheckCitySkill(add, citys[i].getDenfenceGenerals(), 1);
                        citys[i].setMoney(citys[i].getMoney() + (add));
                    }
                }
            }
        }
    }

    /**
     * @param @param general    设定文件
     * @return void    返回类型
     * @throws
     * @Title: updateCityRandom
     * @Description: 随机给一个城市升级
     */
    public static void updateCityRandom(General general) {
        // 先找到一个城市，必须是级别小于3的
        List<City> findCitys = findCityByLeader(general);

        findCitys.removeIf(next -> next.getType() == 4);

        // 随机升级一个
        if (findCitys.size() > 0) {
            City city = findCitys.get(new Random().nextInt(findCitys.size()));
            //city.setUpgradeLevel(city.getUpgradeLevel() + 1);
            city.setType(city.getType() + 1);
            System.out.println("城市" + city.getName() + "升级完成，级别为：" + city.getType());
        } else {
            System.out.println("没有可以升级的城市");
        }
    }

    public static List<City> findCityByLeader(General general) {
        List<City> citysByLeader = new ArrayList<>();
        for (int i = 1; i < citys.length; i++) {
            if (citys[i] != null) {
                if (citys[i].getBelongTo() != null && citys[i].getBelongTo() == Integer.parseInt(general.getId())) {
                    citysByLeader.add(citys[i]);
                }
            }
        }
        return citysByLeader;
    }

    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title: computeSoilders
     * @Description: 计算士兵
     */
    public static void computeSoilders() {
        // 所有已经有主的城市
        for (int i = 1; i < citys.length; i++) {
            if (citys[i] != null) {
                if (citys[i].getBelongTo() != null && citys[i].getBelongTo() > 0) {
                    int addSoilders = 0;
                    // 如果没有建筑，跳过
                    if (citys[i].getBildings() != null && citys[i].getBildings().size() > 0) {
                        for (int j = 0; j < citys[i].getBildings().size(); j++) {
                            if (citys[i].getDenfenceGenerals().size() > 0) {
                                GeneralFactory.sortByCommand(citys[i].getDenfenceGenerals());
                                if (citys[i].getBildings().get(j).id == 9) { // 存在徽兵所
                                    // 增加守城主将的魅力 * 2 个普通士兵
                                    addSoilders =
                                            (int) (citys[i].getSoilders() + Integer.parseInt(citys[i].getDenfenceGenerals().get(0).getCharm()) * 1.2);
                                }
                            }
                        }
                    } else {
                        // 增加守城主将的魅力 * 0.2 个普通士兵
                        if (citys[i].getDenfenceGenerals() != null && citys[i].getDenfenceGenerals().size() > 0) {
                            addSoilders = (int) (citys[i].getSoilders() + Integer.parseInt(citys[i].getDenfenceGenerals().get(0).getCharm()) * 0.2);
                        }
                    }
                    if (addSoilders == 0) {
                        addSoilders = citys[i].getSoilders();
                    }
                    addSoilders = SkillFactory.checkSkillForAddSoilders(addSoilders, citys[i]);
                    citys[i].setSoilders(addSoilders);
                }
            }
        }

    }

    private static void checkSkillForAddSoilders(int addSoilders, City city) {

    }

    /**
     * 升级城市
     *
     * @param defence
     * @param player
     * @return void
     * @throws
     * @author Eric
     * @date 9:35 2019/8/16
     **/
    public static void upgradeCity(City defence, General player) {
        System.out.println("是否要升级城市，升级费用" + defence.getUpgradeLevel() + ":  1 是  0 否");
        System.out.println("您目前的金钱有:" + player.getMoney());
        int choise = 1;
        if (player.isReboot()) {
            if (player.getMoney() / 2 > defence.getUpgradeLevel()) {
                player.setMoney(player.getMoney() - defence.getUpgradeLevel());
                defence.setType(defence.getType() + 1);
                System.out.println("城市升级成功");
                return;
            } else {
                System.out.println("暂不升级");
                return;
            }
        }
        Scanner input = new Scanner(System.in);
        choise = input.nextInt();
        if (choise == 1) {
            if (player.getMoney() > defence.getUpgradeLevel()) {
                player.setMoney(player.getMoney() - defence.getUpgradeLevel());
                defence.setType(defence.getType() + 1);
                System.out.println("城市升级成功");
                return;
            } else {
                System.out.println("对不起，您的钱不够");
                return;
            }
        } else {
            System.out.println("暂不升级");
            return;
        }

    }

    public static City[] getCitys() {
        return citys;
    }

    public static void setCitys(City[] citys) {
        CityFactory.citys = citys;
    }

    public static City getCityById(String cityId) {
        if (cityId == null || "".equals(cityId)) {
            return null;
        }
        for (City city : citys) {
            if (city != null && city.getId() != null) {
                if (cityId.equals(city.getId())) {
                    return city;
                }
            }
        }
        return null;
    }

    public static void removeGeneral(General defence, City city) {
        // 所属城市将其去除
        if (city != null) {
            List<General> denfenceGenerals = city.getDenfenceGenerals();
            if (denfenceGenerals != null && denfenceGenerals.size() > 0) {
                for (General denfenceGeneral : denfenceGenerals) {
                    if (denfenceGeneral.getId().equals(defence.getId())) {
                        denfenceGenerals.remove(denfenceGeneral);
                        break;
                    }
                }
            }
        }
    }
}
