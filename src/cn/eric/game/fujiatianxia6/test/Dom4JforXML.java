package cn.eric.game.fujiatianxia6.test;

import cn.eric.game.fujiatianxia6.factory.CampaignFactory;
import cn.eric.game.fujiatianxia6.po.CampaignMap;
import cn.eric.game.fujiatianxia6.po.General;
import cn.eric.game.fujiatianxia6.po.Skill;
import cn.eric.game.fujiatianxia6.po.Weapon;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Dom4JforXML {

    private static List<General> listGeneral = new ArrayList<General>();

    private static List<Skill> skills = new ArrayList<>();

    private static List<Weapon> weapons = new ArrayList<>();

    private static List<CampaignMap> campaignMaps = new ArrayList<>();

    public static List<General> loadGenerals(String filePath) throws DocumentException {
        SAXReader reader = new SAXReader();

        Document document = reader.read(new File(filePath));

        Element root = document.getRootElement();

        listNodes2(root);
        return listGeneral;
    }


    @Test
    public void test() throws DocumentException {
        SAXReader reader = new SAXReader();

        Document document = reader.read(new File("data/base/Generals.xml"));

        Element root = document.getRootElement();

        listNodes(root);
    }

    //
    private void listNodes(Element node) {
        System.out.println("当前节点的名称：" + node.getName());
        //首先获取当前节点的所有属性节点  
        List<Attribute> list = node.attributes();
        //遍历属性节点  
        for (Attribute attribute : list) {
            //System.out.println("属性"+attribute.getName() +":" + attribute.getValue());  
        }
        //如果当前节点内容不为空，则输出  
        if (!(node.getTextTrim().equals(""))) {
            //System.out.println("节点"+ node.getName() + "：" + node.getText());
        }
        //同时迭代当前节点下面的所有子节点  
        //使用递归  
        Iterator<Element> iterator = node.elementIterator();
        while (iterator.hasNext()) {
            Element e = iterator.next();
            listNodes(e);
        }

    }

    //@Test
    public static List<General> test2() throws DocumentException {
        listGeneral = new ArrayList<General>();
        SAXReader reader = new SAXReader();

        Document document = reader.read(new File("data/base/Generals.xml"));

        Element root = document.getRootElement();

        listNodes2(root);
        return listGeneral;
    }

    @Test
    public static List<Skill> createSkills() throws DocumentException {
        SAXReader reader = new SAXReader();

        Document document = reader.read(new File("data/base/Skills.xml"));

        Element root = document.getRootElement();

        listNodesSkill(root);
        return skills;
    }

    public static List<CampaignMap> createCampaignMap() throws DocumentException {
        SAXReader reader = new SAXReader();

        Document document = reader.read(new File("data/base/Maps.xml"));

        Element root = document.getRootElement();

        listNodesMap(root);
        return campaignMaps;
    }


    public static List<Weapon> createWeapons() throws DocumentException {
        SAXReader reader = new SAXReader();

        Document document = reader.read(new File("data/base/Weapons.xml"));

        Element root = document.getRootElement();

        listNodesWeapon(root);
        return weapons;
    }


    public static List<CampaignFactory.CampaignList> createCampaign() throws DocumentException {
        List<CampaignFactory.CampaignList> list = new ArrayList<>();
        SAXReader reader = new SAXReader();

        Document document = reader.read(new File("data/base/Campaign.xml"));

        Element root = document.getRootElement();

        return listNodesCampaign(root);
    }

    private static List<CampaignFactory.CampaignList> listNodesCampaign(Element node) {
        List<CampaignFactory.CampaignList> list = new ArrayList<>();
        //使用递归
        Iterator<Element> iterator = node.elementIterator();
        while (iterator.hasNext()) {
            CampaignFactory.CampaignList s = new CampaignFactory.CampaignList();
            Element e = iterator.next();
            //遍历属性节点
            Iterator iterator2 = e.elementIterator();
            while (iterator2.hasNext()) {
                Element e2 = (Element) iterator2.next();
                //如果当前节点内容不为空，则输出
                if (!(e2.getTextTrim().equals(""))) {
                    switch (e2.getName()) {
                        case "lordId":
                            s.setLordId(e2.getText());
                            break;
                        case "maps":
                            s.setMaps(e2.getText());
                            break;
                        case "enemies":
                            s.setEnemies(e2.getText());
                            break;
                        case "memo":
                            s.setMemo(e2.getText());
                            break;
                        default:
                            break;
                    }

                }
            }
            list.add(s);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private static void listNodesSkill(Element node) {
        //使用递归  
        Iterator<Element> iterator = node.elementIterator();
        while (iterator.hasNext()) {
            Skill s = new Skill();
            Element e = iterator.next();
            //遍历属性节点  
            Iterator iterator2 = e.elementIterator();
            while (iterator2.hasNext()) {
                Element e2 = (Element) iterator2.next();
                //如果当前节点内容不为空，则输出  
                if (!(e2.getTextTrim().equals(""))) {
                    switch (e2.getName()) {
                        case "name":
                            s.setName(e2.getText());
                            break;
                        case "id":
                            s.setId(Integer.parseInt(e2.getText()));
                            break;
                        case "memo":
                            s.setMemo(e2.getText());
                            break;
                        case "data":
                            s.setData(Integer.parseInt(e2.getText()));
                            break;
                        case "time":
                            s.setTime(Integer.parseInt(e2.getText()));
                            break;
                        default:
                            break;
                    }

                }
            }
            skills.add(s);
        }
    }

    private static void listNodesMap(Element root) {
        //使用递归
        Iterator<Element> iterator = root.elementIterator();
        while (iterator.hasNext()) {
            CampaignMap campaignMap = new CampaignMap();
            Element e = iterator.next();
            //遍历属性节点
            Iterator iterator2 = e.elementIterator();
            while (iterator2.hasNext()) {
                Element e2 = (Element) iterator2.next();
                //如果当前节点内容不为空，则输出
                if (!(e2.getTextTrim().equals(""))) {
                    switch (e2.getName()) {
                        case "name":
                            campaignMap.setName(e2.getText());
                            break;
                        case "id":
                            campaignMap.setId(e2.getText());
                            break;
                        case "size":
                            campaignMap.setSize(Integer.parseInt(e2.getText()));
                            break;
                        case "memo":
                            campaignMap.setMemo(e2.getText());
                            break;
                        case "city":
                            campaignMap.setCity(Arrays.asList(e2.getText().split(",")));
                            break;
                        case "luckyTurn":
                            campaignMap.setLuckyTurn(Arrays.asList(e2.getText().split(",")));
                            break;
                        case "wine":
                            campaignMap.setWine(Arrays.asList(e2.getText().split(",")));
                            break;
                        case "pause":
                            campaignMap.setPause(Arrays.asList(e2.getText().split(",")));
                            break;
                        case "soldiers":
                            campaignMap.setSoldiers(Arrays.asList(e2.getText().split(",")));
                            break;
                        case "cityId":
                            campaignMap.setCityId(Arrays.asList(e2.getText().split(",")));
                            break;
                        default:
                            break;
                    }
                }
            }
            campaignMaps.add(campaignMap);
        }
    }

    private static void listNodesWeapon(Element node) {
        //使用递归
        Iterator<Element> iterator = node.elementIterator();
        while (iterator.hasNext()) {
            Weapon weapon = new Weapon();
            Element e = iterator.next();
            //遍历属性节点
            Iterator iterator2 = e.elementIterator();
            while (iterator2.hasNext()) {
                Element e2 = (Element) iterator2.next();
                //如果当前节点内容不为空，则输出
                if (!(e2.getTextTrim().equals(""))) {
                    switch (e2.getName()) {
                        case "name":
                            weapon.setName(e2.getText());
                            break;
                        case "id":
                            weapon.setId(Integer.parseInt(e2.getText()));
                            break;
                        case "memo":
                            weapon.setMemo(e2.getText());
                            break;
                        case "data":
                            weapon.setData(Integer.parseInt(e2.getText()));
                            break;
                        case "generalId":
                            weapon.setGeneralId(e2.getText());
                            break;
                        case "attack":
                            weapon.setAttack(Integer.parseInt(e2.getText()));
                            break;
                        case "command":
                            weapon.setCommand(Integer.parseInt(e2.getText()));
                            break;
                        case "intelligence":
                            weapon.setIntelligence(Integer.parseInt(e2.getText()));
                            break;
                        case "charm":
                            weapon.setCharm(Integer.parseInt(e2.getText()));
                            break;
                        case "politics":
                            weapon.setPolitics(Integer.parseInt(e2.getText()));
                            break;
                        case "price":
                            weapon.setPrice(Integer.parseInt(e2.getText()));
                            break;
                        default:
                            break;
                    }

                }
            }
            weapons.add(weapon);
        }
    }


    private static void listNodes2(Element node) {
        //System.out.println("当前节点的名称：" + node.getName());  
        //首先获取当前节点的所有属性节点  
        List<Attribute> list = node.attributes();
        //遍历属性节点  
        for (Attribute attribute : list) {
            //System.out.println("属性"+attribute.getName() +":" + attribute.getValue());  
        }
        //同时迭代当前节点下面的所有子节点  
        //使用递归  
        Iterator<Element> iterator = node.elementIterator();
        while (iterator.hasNext()) {
            General g = new General();
            Element e = iterator.next();
            //System.out.println("当前节点的名称：" + e.getName());
            //首先获取当前节点的所有属性节点  
            List<Attribute> listA = e.attributes();
            //遍历属性节点  
            for (Attribute attribute : listA) {
                //System.out.println("属性"+attribute.getName() +":" + attribute.getValue()); 
                g.setId(attribute.getValue());
            }
            Iterator iterator2 = e.elementIterator();
            while (iterator2.hasNext()) {
                Element e2 = (Element) iterator2.next();
                //如果当前节点内容不为空，则输出  
                if (!(e2.getTextTrim().equals(""))) {
                    switch (e2.getName()) {
                        case "name":
                            g.setName(e2.getText());
                            break;
                        case "king":
                            g.setKing(e2.getText());
                            break;
                        case "attack":
                            g.setAttack(e2.getText());
                            break;
                        case "command":
                            g.setCommand(e2.getText());
                            break;
                        case "intelligence":
                            g.setIntelligence(e2.getText());
                            break;
                        case "charm":
                            g.setCharm(e2.getText());
                            break;
                        case "politics":
                            g.setPolitics(e2.getText());
                            break;
                        case "vitality":
                            g.setVitality(e2.getText());
                            break;
                        case "belongTo":
                            g.setBelongTo(e2.getText());
                            break;
                        case "relation":
                            g.setRelations(e2.getText());
                            break;
                        case "skill":
                            g.setSkill(e2.getText());
                            break;
                        case "status":
                            g.setStatus(e2.getText());
                            break;
                        case "arms":
                            g.setArms(e2.getText());
                            break;
                        case "landfc":
                            g.setLandfc(e2.getText());
                            break;
                        case "mountainfc":
                            g.setMountainfc(e2.getText());
                            break;
                        case "riverfc":
                            g.setRiverfc(e2.getText());
                            break;
                        case "city":
                            g.setCityId(e2.getText());
                            break;
                        default:
                            break;
                    }

                }
            }
            listGeneral.add(g);
        }
    }

}
