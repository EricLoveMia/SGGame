package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.CityStore;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class CityFactory {

    public static List<City> citys;

    public static Map<String, City> cityMap;

//    static {
//        init();
//    }


    public static void init() {
        citys = new ArrayList<>();
        // 读取模版
        String cityJson = Util.readFileContentAsBuffer("data/base/" + "city.json");
        List<CityTemplate> cityTemplates = JSONObject.parseArray(cityJson, CityTemplate.class);
        // 从模板生成城池信息
        for (CityTemplate cityTemplate : cityTemplates) {
            citys.add(new City(cityTemplate));
        }
        // 转map
        cityMap = citys.stream().collect(Collectors.toMap(City::getId, e -> e));
        System.out.println("初始化城池完成");
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
        for (City city : citys) {
            if (city.getBelongTo() != null && city.getBelongTo() > 0) {
                //如果没有守城的人或者没有资金，跳过
                if (city.getDenfenceGenerals().size() > 0 && city.getMoney() > 0) {
                    int politics = 0;
                    for (Iterator iterator = city.getDenfenceGenerals().iterator(); iterator.hasNext(); ) {
                        General g = (General) iterator.next();
                        politics += Integer.parseInt(g.getPolitics());
                    }
                    // 政治总和/1000 * 繁荣度 + 原来的钱
                    // 看看有没有技能触发
                    int add = politics * city.getProsperity() * city.getType() / 1000;
                    add = SkillFactory.CheckCitySkill(add, city.getDenfenceGenerals(), 1);
                    city.setMoney(city.getMoney() + (add));
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
        for (City city : citys) {
            if (city.getBelongTo() != null && city.getBelongTo() == Integer.parseInt(general.getId())) {
                citysByLeader.add(city);
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
        for (City city : citys) {
            if (city.getBelongTo() != null && city.getBelongTo() > 0) {
                int addSoilders = 0;
                // 如果没有建筑，跳过
                if (city.getBildings() != null && city.getBildings().size() > 0) {
                    for (int j = 0; j < city.getBildings().size(); j++) {
                        if (city.getDenfenceGenerals().size() > 0) {
                            GeneralFactory.sortByCommand(city.getDenfenceGenerals());
                            if (city.getBildings().get(j).id == 9) { // 存在徽兵所
                                // 增加守城主将的魅力 * 2 个普通士兵
                                addSoilders = (int) (city.getSoilders()
                                        + Integer.parseInt(city.getDenfenceGenerals().get(0).getCharm()) * 1.2);
                            }
                        }
                    }
                } else {
                    // 增加守城主将的魅力 * 0.2 个普通士兵
                    if (city.getDenfenceGenerals() != null && city.getDenfenceGenerals().size() > 0) {
                        addSoilders = (int) (city.getSoilders() + Integer.parseInt(city.getDenfenceGenerals().get(0).getCharm()) * 0.2);
                    }
                }
                if (addSoilders == 0) {
                    addSoilders = city.getSoilders();
                }
                addSoilders = SkillFactory.checkSkillForAddSoilders(addSoilders, city);
                city.setSoilders(addSoilders);
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

    public static List<City> getCitys() {
        return citys;
    }

    public static void setCitys(List<City> citys) {
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

    public static void main(String[] args) {
        System.out.println(JSON.toJSON(citys));
    }

    public static void resetGoods() {
        citys.forEach(
                e -> {
                    CityStore cityStore = e.getCityStore();
                    cityStore.setSeniorRest(cityStore.getSeniorTotal());
                    cityStore.setSpecialtyRest(cityStore.getSpecialtyTotal());
                    cityStore.setCommonRest(cityStore.getCommonTotal());
                }
        );
    }

    public static class CityTemplate {
        private String id;
        private int type;   // 类型  1 小城  2 中城  3 大城  4 特大城 5 巨型城
        private String name;   // 城市的名字 徐州
        private String memo;   // 介绍
        private String state; // 州

        private int purchase; // 购买费用
        private int upgradeLevel; // 升级费用
        private int prosperity; //繁荣度

        /**
         * 地形  1 平原 2 山地 3 水道   适应兵种  平原 骑>枪>弓  山地 枪>弓>骑   水道 弓>枪>骑  战力分别是 200% 160% 120% 普通兵种战力100%
         */
        private int topography;

        /**
         * 商品id
         */
        private String goodsId;

        /**
         * 特产id
         */
        private String specialId;

        /**
         * 高级特产id
         */
        private String specialTwoId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getPurchase() {
            return purchase;
        }

        public void setPurchase(int purchase) {
            this.purchase = purchase;
        }

        public int getUpgradeLevel() {
            return upgradeLevel;
        }

        public void setUpgradeLevel(int upgradeLevel) {
            this.upgradeLevel = upgradeLevel;
        }

        public int getProsperity() {
            return prosperity;
        }

        public void setProsperity(int prosperity) {
            this.prosperity = prosperity;
        }

        public int getTopography() {
            return topography;
        }

        public void setTopography(int topography) {
            this.topography = topography;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getSpecialId() {
            return specialId;
        }

        public void setSpecialId(String specialId) {
            this.specialId = specialId;
        }

        public String getSpecialTwoId() {
            return specialTwoId;
        }

        public void setSpecialTwoId(String specialTwoId) {
            this.specialTwoId = specialTwoId;
        }
    }
}
