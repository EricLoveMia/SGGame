package cn.eric.game.fujiatianxia6.po.bag;

import cn.eric.game.fujiatianxia6.factory.CityFactory;
import cn.eric.game.fujiatianxia6.factory.GeneralFactory;
import cn.eric.game.fujiatianxia6.po.City;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.service.Game;

import java.util.*;

/**
 * @version 1.0.0
 * @description:
 * @date: 2022-10-17 11:34
 **/
public abstract class SilkBag implements Bag, Cloneable {

    private int id;

    private String name;

    // 说明
    private String memo;

    // 1 对自己释放的 2 对其他主公释放的 3 对自己的城市释放的 4 对非自己的城市释放的
    private int type;

    public SilkBag() {
        System.out.println(123);
    }

    public SilkBag(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void execute(General origin) {
        General targetGeneral = null;
        City targetCity = null;
        if (type == 2) {
            // 选择需要释放的主公
            targetGeneral = chooseTargetGeneral(origin);
            if (targetGeneral == null) {
                return;
            }
            List<SilkBag> silkBags = targetGeneral.getBag().getSilkBags();
            // 是否有无懈可击
            Optional<SilkBag> 无懈可击 = silkBags.stream().filter(e -> e.name().equals("无懈可击")).findFirst();
            if (无懈可击.isPresent()) {
                SilkBag bag = 无懈可击.get();
                String choose = "1";
                if (!origin.isReboot()) {
                    System.out.println(origin.getName() + "对您使用锦囊" + name + ",是否使用无懈可击 1 是 其他 否");
                    Scanner input = new Scanner(System.in);
                    choose = input.nextLine();
                }
                if ("1".equals(choose)) {
                    System.out.println("主公" + targetGeneral.getName() + "释放锦囊" + bag.name());
                    silkBags.remove(bag);
                    origin.getBag().getSilkBags().remove(this);
                    return;
                }
            }
        } else if (type == 3) {
            System.out.println("请选择目标城市");
            targetCity = findOneOfSelfCity(origin);
            if (targetCity == null) {
                return;
            }
            targetGeneral = chooseExecute(origin);
        } else if (type == 4) {
            System.out.println("请选择目标城市");
            targetCity = findOneOfOtherCity(origin);
            if (targetCity == null) {
                return;
            }
            Integer belongTo = targetCity.getBelongTo();
            targetGeneral = GeneralFactory.getGeneralById(belongTo + "");
            if (targetGeneral != null) {
                List<SilkBag> silkBags = targetGeneral.getBag().getSilkBags();
                // 是否有无懈可击
                Optional<SilkBag> 无懈可击 = silkBags.stream().filter(e -> e.name().equals("无懈可击")).findFirst();
                if (无懈可击.isPresent()) {
                    SilkBag bag = 无懈可击.get();
                    String choose = "1";
                    if (!origin.isReboot()) {
                        System.out.println(origin.getName() + "对您的城市"
                                + targetCity.getName() + "使用锦囊" + name + ",是否使用无懈可击 1 是 其他 否");
                        Scanner input = new Scanner(System.in);
                        choose = input.nextLine();
                    }
                    if ("1".equals(choose)) {
                        System.out.println("主公" + targetGeneral.getName() + "释放锦囊" + bag.name());
                        silkBags.remove(bag);
                        origin.getBag().getSilkBags().remove(this);
                        return;
                    }
                }
            }
            targetGeneral = chooseExecute(origin);
        }


        boolean result = run(origin, targetGeneral, targetCity);
        if (result) {
            origin.getBag().getSilkBags().remove(this);
        }
    }

    protected General chooseExecute(General origin) {
        System.out.println("请选择释放锦囊的武将");
        List<General> generals = GeneralFactory.getAoundGeneral(origin.getGenerals());
        for (int i = 0; i < generals.size(); i++) {
            System.out.println((i + 1) + ":" + generals.get(i).simpleInfo());
        }
        Scanner input = new Scanner(System.in);
        int choose = input.nextInt();
        return generals.get(choose - 1);
    }

    protected City findOneOfOtherCity(General origin) {
        List<String> cityIds = Game.getMap().getCampaignMap().getCityId();
        Map<Integer, City> cityMap = new HashMap<>();
        Integer index = 1;
        for (String cityId : cityIds) {
            City city = CityFactory.getCityById(cityId);
            // 不是空城，也不是自己的
            if (city.getBelongTo() != 0 && city.getBelongTo() != Integer.parseInt(origin.getId())) {
                cityMap.put(index++, city);
            }
        }
        if (cityMap.size() == 0) {
            System.out.println("没有可施展的城池");
            return null;
        }
        for (int i = 1; i < index; i++) {
            System.out.println(i + "：" + cityMap.get(i).toString());
        }
        Scanner input = new Scanner(System.in);
        int choise = input.nextInt();
        while (choise != 0) {
            if (choise > index || choise < 0) {
                System.out.println("请谨慎选择 0 表示放弃");
            } else {
                return cityMap.get(choise);
            }
            choise = input.nextInt();
        }
        return null;
    }

    protected City findOneOfSelfCity(General origin) {
        List<City> cityList = CityFactory.findCityByLeader(origin);
        if (cityList.size() == 0) {
            System.out.println("您还没有占领任何地盘");
        } else {
            for (int i = 0; i < cityList.size(); i++) {
                System.out.println((i + 1) + cityList.get(i).toString());
            }
            Scanner input = new Scanner(System.in);
            int choise = input.nextInt();
            while (choise != 0) {
                if (choise > cityList.size() || choise < 0) {
                    System.out.println("请谨慎选择 0 表示放弃");
                } else {
                    return cityList.get(choise - 1);
                }
                choise = input.nextInt();
            }
        }
        return null;
    }

    protected abstract General chooseTargetGeneral(General origin);

    protected abstract boolean run(General origin, General targetGeneral, City targetCity);

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String name() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
