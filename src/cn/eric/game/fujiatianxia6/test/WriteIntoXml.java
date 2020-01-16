package cn.eric.game.fujiatianxia6.test;

import cn.eric.game.fujiatianxia6.po.General;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName WriteIntoXml
 * @Description: TODO
 * @Author YCKJ2725
 * @Date 2020/1/15
 * @Version V1.0
 **/
public class WriteIntoXml {

    public static void testCreateXml() {
        List<Emp> list = new ArrayList<Emp>();
        list.add(new Emp(1,"张三",25,"男",5000));
        list.add(new Emp(2,"李四",26,"女",6000));
        list.add(new Emp(3,"王五",27,"男",7000));
        list.add(new Emp(4,"赵六",28,"女",8000));
        list.add(new Emp(5,"钱七",29,"男",9000));
        /*
         * 使用DOM生成XML文档的大致步骤:
         * 1:创建一个Document对象表示一个空文档
         * 2:向Document中添加根元素
         * 3:按照文档应有的结构从根元素开始顺序添加
         *   子元素来形成该文档结构。
         * 4:创建XmlWriter对象
         * 5:将Document对象写出
         *   若写入到文件中则形成一个xml文件
         *   也可以写出到网络中作为传输数据使用
         */

        //1
        Document doc
                = DocumentHelper.createDocument();

        /*
         * 2
         * Document提供了添加根元素的方法:
         * Element addElement(String name)
         * 向当前文档中添加指定名字的根元素，返回
         * 的Element就表示这个根元素。
         * 需要注意，该方法只能调用一次，因为一个
         * 文档只能有一个根元素。
         */
        Element root = doc.addElement("list");

        //3
        for(Emp emp : list){
            /*
             * Element也提供了追加子元素的方法:
             * Element addElement(String name)
             * 调用次数没有限制，元素可以包含若干
             * 子元素。
             */
            Element empEle = root.addElement("emp");

            //添加name信息
            Element nameEle = empEle.addElement("name");
            nameEle.addText(emp.getName());

            //添加age信息
            Element ageEle = empEle.addElement("age");
            ageEle.addText(emp.getAge()+"");

            //添加gender信息
            Element genderEle = empEle.addElement("gender");
            genderEle.addText(emp.getGender());

            //添加salary信息
            Element salEle = empEle.addElement("salary");
            salEle.addText(emp.getSalary()+"");

            /*
             * 向当前元素中添加指定名字以及对应值的属性
             */
            empEle.addAttribute("id", emp.getId()+"");

        }
        try{
            //4
            XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
            FileOutputStream fos
                    = new FileOutputStream("data/save/myemp.xml");
            writer.setOutputStream(fos);

            //5
            writer.write(doc);
            System.out.println("写出完毕!");
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void saveGeneralsToXML(List<General> initGenerals) {
        //1
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("generals");

        //3
        for(General general : initGenerals){
            /*
             * Element也提供了追加子元素的方法:
             * Element addElement(String name)
             * 调用次数没有限制，元素可以包含若干
             * 子元素。
             */
            Element empEle = root.addElement("general");

            //添加name信息
            Element nameEle = empEle.addElement("name");
            nameEle.addText(general.getName());

            //添加king信息
            Element ageEle = empEle.addElement("king");
            ageEle.addText(general.getKing());

            //添加attack信息
            Element genderEle = empEle.addElement("attack");
            genderEle.addText(general.getAttack());

            //添加command信息
            Element salEle = empEle.addElement("command");
            salEle.addText(general.getCommand());

            //添加intelligence信息
            Element intelEle = empEle.addElement("intelligence");
            intelEle.addText(general.getIntelligence());

            //添加charm信息
            Element charmEle = empEle.addElement("charm");
            charmEle.addText(general.getCharm());

            //添加politics信息
            Element politicsEle = empEle.addElement("politics");
            politicsEle.addText(general.getPolitics());

            //添加vitality信息
            Element vitalityEle = empEle.addElement("vitality");
            vitalityEle.addText(general.getVitality());

            //添加arms信息
            Element armsEle = empEle.addElement("arms");
            armsEle.addText(general.getArms());


            //添加landfc信息
            Element landfcEle = empEle.addElement("landfc");
            landfcEle.addText(general.getLandfc());

            //添加mountainfc信息
            Element mountainfcEle = empEle.addElement("mountainfc");
            mountainfcEle.addText(general.getMountainfc());

            //添加riverfc信息
            Element riverfcEle = empEle.addElement("riverfc");
            riverfcEle.addText(general.getRiverfc());

            //添加riverfc信息
            Element skillEle = empEle.addElement("skill");
            skillEle.addText(general.getSkill());

            //添加relation信息
            Element relationEle = empEle.addElement("relation");
            relationEle.addText(general.getRelations());

            //添加belongTo信息
            Element belongToEle = empEle.addElement("belongTo");
            belongToEle.addText(general.getBelongTo());

            //添加status信息
            Element statusEle = empEle.addElement("status");
            statusEle.addText(general.getStatus());

            //添加status信息
            Element cityEle = empEle.addElement("city");
            cityEle.addText(Optional.ofNullable(general.getCityid()).orElse(""));
            /*
             * 向当前元素中添加指定名字以及对应值的属性
             */
            empEle.addAttribute("id", general.getId()+"");

        }
        try{
            //4
            XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
            FileOutputStream fos
                    = new FileOutputStream("data/save/Generals.xml");
            writer.setOutputStream(fos);

            //5
            writer.write(doc);
            System.out.println("写出完毕!");
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private static class Emp{
        private Integer id;
        private String name;
        private int age;
        private String gender;
        private int salary;

        public Emp(Integer id, String name, int age, String gender, int salary) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.salary = salary;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getGender() {
            return gender;
        }

        public int getSalary() {
            return salary;
        }
    }

    public static void main(String[] args) {
        WriteIntoXml.testCreateXml();
    }
}
