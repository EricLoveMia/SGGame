package cn.eric.game.fujiatianxia6.service;

import cn.eric.game.fujiatianxia6.factory.CityFactory;
import cn.eric.game.fujiatianxia6.factory.GeneralFactory;
import cn.eric.game.fujiatianxia6.po.General;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName CommandLineSerivce
 * @Description: 命令行服务
 * @Author YCKJ2725
 * @Date 2020/1/17
 * @Version V1.0
 **/
public class CommandLineSerivce {

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final Queue<String> commandQueue = new LinkedList<>();

    // 练习一下单例 匿名内部类
    private CommandLineSerivce(){
    }

    private static class CommandLineHolder {
        private static final CommandLineSerivce instance = new CommandLineSerivce();
    }

    public static CommandLineSerivce getInstance(){
        return CommandLineHolder.instance;
    }

    private static final Map<String,String> CommandLineMap;
    static
    {
        CommandLineMap = new HashMap<String, String>();
        CommandLineMap.put("-help", "显示所有命令");
        CommandLineMap.put("-saves all", "显示所有存档");
        CommandLineMap.put("-save {{name}}", "覆盖name档案");
        CommandLineMap.put("-players", "显示所有玩家");
        CommandLineMap.put("-player {{id}}", "显示id玩家的详细信息");
        CommandLineMap.put("-generals {{id}}", "显示id玩家的所属武将信息");
        CommandLineMap.put("-general {{id}}", "显示id武将信息");
        CommandLineMap.put("-citys all", "显示所有城市的信息");
        CommandLineMap.put("-city {{id}}", "显示对应id的城市的信息");
        CommandLineMap.put("-giveMoney {{num}}", "给与玩家num数量的金钱");
        CommandLineMap.put("-giveSoldier {{num}}", "给与玩家num数量的所有种类的兵力");
    }

    // help
    public void showHelp(){
        // 显示所有的命令行信息
        Set<Map.Entry<String, String>> entries = CommandLineMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            System.out.println(key + "," + value);
        }
    }

    public void getCommandLine(String command){
        String[] commandArray = command.split(" ");
        if (commandArray.length == 0) {
            System.out.println("命令输入错误");
        }else{
            String commandMain = commandArray[0];
            switch (commandMain) {
                case "-help":
                    commandAll();
                    break;
                case "-players":
                    commandShowPlayers();
                    break;
                case "-player":
                    if (commandArray.length < 2) {
                        System.out.println("命令输入错误");
                    }
                    commandShowPlayer(commandArray[1]);
                    break;
                case "-generals":
                    commandShowPlayerGenerals(commandArray[1]);
                    break;
                case "-save":
                    commandSave(commandArray);
                    break;
                case "-saves all":
                    commandSavesAll(commandArray);
                    break;
                case "-giveMoney":
                    commandGiveMoney(commandArray[1]);
                    break;
                case "-giveSoldier":
                    commandGiveSoldier(commandArray[1]);
                    break;
                case "-citys all":
                    commandCitysAll(commandArray[1]);
                    break;
                default:
                    break;
            }
        }

    }

    private void commandShowPlayers() {
        General[] players = Game.getPlayers();
        for (General player : players) {
            System.out.println(player.simpleInfo());
        }
    }

    private void commandCitysAll(String command) {
        List<String> cityId = Game.map.getCampaignMap().getCityId();
        for (String city : cityId) {
            System.out.println(CityFactory.getCityById(city).toString());
        }
    }

    private void commandGiveSoldier(String num) {
        System.out.println("做梦呢");
    }

    private void commandGiveMoney(String money) {
        System.out.println("做梦呢");
    }

    private void commandShowPlayerGenerals(String id) {
        General general = GeneralFactory.getGeneralById(id);
        if (general == null) {
            System.err.println("不存在此玩家");
            return;
        }
        List<General> generals = general.getGenerals();
        if (generals == null) {
            System.err.println(general.getName() + "没有武将");
            return;
        }
        GeneralFactory.showGeneralInfo(generals);
    }

    private void commandSavesAll(String[] commandArray) {
        File file = new File("data/save");
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            long time = tempList[i].lastModified();
            cal.setTimeInMillis(time);
            System.out.println(i + ":" + tempList[i].getName() + " 修改时间：" + formatter.format(cal.getTime()));
        }
    }

    private void commandSave(String[] path){
        File file = new File("data/save");
        File[] tempList = file.listFiles();
        if (tempList == null) {
            System.out.println("暂时没有存档，新增存档");
            newSave();
            return;
        }
        // 存在历史存档
        System.out.println("覆盖存档 1 ; 新增存档 2");
        Scanner input = new Scanner(System.in);
        int choose;
        while (true) {
            choose = input.nextInt();
            if (choose > 2 || choose < 0) {
                System.out.println("请重新选择");
                continue;
            }
            break;
        }
        if (choose == 1) {
            System.out.println("覆盖哪个存档 ");
            for (int i = 0; i < tempList.length; i++) {
                long time = tempList[i].lastModified();
                cal.setTimeInMillis(time);
                System.out.println(i + ":" + tempList[i].getName() + " 修改时间：" + formatter.format(cal.getTime()));
            }
            input = new Scanner(System.in);
            while (true) {
                choose = input.nextInt();
                if (choose > tempList.length || choose < 0) {
                    System.out.println("请重新选择");
                    continue;
                }
                try {
                    SaveService.save(tempList[choose].getName());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                break;
            }
        } else if (choose == 2) {
            newSave();
        }
    }

    private void newSave() {
        Scanner input;
        System.out.println("输入存档名称 ");
        input = new Scanner(System.in);
        String name = input.nextLine();
        while (name == null || "".equals(name)) {
            System.out.println("输入存档名称 ");
            name = input.nextLine();
        }
        try {
            SaveService.save(name);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private void commandShowPlayer(String id) {

        General generalById = GeneralFactory.getGeneralById(id);
        if(generalById != null) {
            System.out.println(generalById.memoPlayer());
        }
    }

    /**
     * @MethodName: CommandAll
     * @Description:
     * @Param: []
     * @Return: void
     * @Author: YCKJ2725
     * @Date: 2020/1/17 20:47
    **/
    public void commandAll(){
        // 显示所有的命令行信息
        Set<Map.Entry<String, String>> entries = CommandLineMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            System.out.println(key + "," + value);
        }
    }
}
