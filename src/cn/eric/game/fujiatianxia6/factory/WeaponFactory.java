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
    private static Map<String,Weapon> weaponMap= new HashMap<>();

    public static void init(){
        try {
            weapons = new ArrayList<>();
            weaponMap = new HashMap<>();
            weapons = Dom4JforXML.createWeapons();
            weaponMap = weapons.stream().collect(Collectors.toMap(Weapon::getGeneralId,a->a,(key1, key2) -> key2));
            // System.out.println(weaponMap.toString());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void purchaseWeapon(General player) {
        System.out.println("根据您目前手下的武将，可以购买专属的有");

        List<General> generals = player.getGenerals();
        List<Weapon> weaponList = WeaponFactory.findWeaponsByGenerals(generals);

        for (int i = 0; i < weaponList.size(); i++) {
            System.out.println(i+1 + " : 武器" + weaponList.get(i).getName() + "," + weaponList.get(i).getMemo() + ",价格" + weaponList.get(i).getPrice());
        }
        int choise;
        Scanner input;
        while(true){
            System.out.println("请选择武器 0 放弃");
            input = new Scanner(System.in);
            choise = input.nextInt();
            if(choise == 0){
                break;
            }
            if(choise < 0 || (choise > weaponList.size())){
                System.out.println("太小或太大");
            }else {
                if(player.getMoney() < weaponList.get(choise-1).getPrice()){
                    System.out.println("对不起，您的钱不够");
                }else{
                    //
                    System.out.println("购买武器" + weaponList.get(choise-1).getName());
                    // 武将自动装备武器
                    General generalById = GeneralFactory.getGeneralById(weaponList.get(choise - 1).getGeneralId());
                    generalById.setWeapon(weaponList.get(choise-1));
                    // 从map中去掉
                    weaponMap.remove(weaponList.get(choise - 1).getGeneralId());
                    // 减钱
                    generalById.setMoney(player.getMoney() - weaponList.get(choise-1).getPrice());
                    break;
                }
            }
        }
    }

    private static List<Weapon> findWeaponsByGenerals(List<General> generals) {
        List<Weapon> weaponList = new ArrayList<>();
        if (generals != null && generals.size() > 0){
            for (General general : generals) {
                if (weaponMap.get(general.getId()) != null) {
                    weaponList.add(weaponMap.get(general.getId()));
                }
            }
        }
        return weaponList;
    }
}
