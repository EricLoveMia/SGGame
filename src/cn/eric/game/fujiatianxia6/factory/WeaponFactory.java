package cn.eric.game.fujiatianxia6.factory;

import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.po.Weapon;
import cn.eric.game.fujiatianxia6.test.Dom4JforXML;
import org.dom4j.DocumentException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Eric
 * @version 1.0
 * @ClassName: WeaponFactory
 * @Description: TODO
 * @company lsj
 * @date 2019/7/15 14:54
 **/
public class WeaponFactory {


    private static List<Weapon> weapons = new ArrayList<>();
    private static Map<String, Weapon> weaponMap = new HashMap<>();
    private static List<Weapon> commonWeapons = new ArrayList<>();

    public static void init(){
        try {
            weapons = new ArrayList<>();
            weaponMap = new HashMap<>();
            weapons = Dom4JforXML.createWeapons();
            commonWeapons = weapons.stream().filter(e -> e.getGeneralId().equals("-1")).collect(Collectors.toList());
            weaponMap = weapons.stream().collect(Collectors.toMap(Weapon::getGeneralId, a -> a, (key1, key2) -> key2));
            // System.out.println(weaponMap.toString());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void purchaseWeapon(General player) {
        System.out.println("根据您目前手下的武将，可以购买专属的有");

        List<General> generals = player.getGenerals();
        List<Weapon> weaponList = WeaponFactory.findWeaponsByGenerals(generals);
        weaponList.addAll(commonWeapons);
        int index = 1;
        for (; index <= weaponList.size(); index++) {
            System.out.println(index + " : 武器" + weaponList.get(index - 1).getName()
                    + "," + weaponList.get(index - 1).getMemo() + ",价格" + weaponList.get(index - 1).getPrice());
        }

        int choise;
        Scanner input;
        while (true) {
            System.out.println("请选择武器 0 放弃");
            input = new Scanner(System.in);
            choise = input.nextInt();
            if (choise == 0) {
                break;
            }
            if(choise < 0 || (choise > weaponList.size())){
                System.out.println("太小或太大");
            }else {
                Weapon weapon = weaponList.get(choise - 1);
                if (player.getMoney() < weapon.getPrice()) {
                    System.out.println("对不起，您的钱不够");
                } else {
                    System.out.println("购买武器" + weapon.getName());
                    String generalId = weapon.getGeneralId();
                    General generalForWeapon;
                    if (!"-1".equals(generalId)) {
                        // 武将自动装备武器
                        generalForWeapon = GeneralFactory.getGeneralById(generalId);
                        // 专属只能买一个
                        weaponMap.remove(generalId);
                        generalForWeapon.setWeapon(weapon);
                    } else {
                        chooseGeneralForWeapon(player, weapon);
                    }
                    // 减钱
                    player.setMoney(player.getMoney() - weapon.getPrice());
                    break;
                }
            }
        }
    }

    private static void chooseGeneralForWeapon(General player, Weapon weapon) {
        if (weapon == null) {
            return;
        }
        System.out.println("请选择武器" + weapon.getName() + "的归属 0 表示卖出武器");
        List<General> generals = player.getGenerals();
        for (int i = 1; i <= generals.size(); i++) {
            System.out.println(i + " : 武将" + generals.get(i - 1).getName()
                    + ", 持有武器-" + Optional.ofNullable(generals.get(i - 1).getWeapon()).orElse(new Weapon()).getName());
        }
        int choise;
        Scanner input;
        while (true) {
            System.out.println("请选择武将，不能放弃");
            input = new Scanner(System.in);
            choise = input.nextInt();
            if (choise == 0) {
                int price = weapon.getPrice() / 2;
                player.setMoney(player.getMoney() + price);
                return;
            }
            if (choise < 0 || (choise > generals.size())) {
                System.out.println("太小或太大");
            } else {
                General general = generals.get(choise - 1);
                Weapon generalWeapon = general.getWeapon();
                general.setWeapon(weapon);
                if (generalWeapon != null) {
                    chooseGeneralForWeapon(player, generalWeapon);
                }
            }
        }
    }

    private static List<Weapon> findWeaponsByGenerals(List<General> generals) {
        List<Weapon> weaponList = new ArrayList<>();
        if (generals != null && generals.size() > 0) {
            for (General general : generals) {
                if (weaponMap.get(general.getId()) != null) {
                    weaponList.add(weaponMap.get(general.getId()));
                }
            }
        }
        return weaponList;
    }
}
