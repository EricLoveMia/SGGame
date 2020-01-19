package cn.eric.game.fujiatianxia6.service;

import cn.eric.game.fujiatianxia6.factory.GeneralFactory;
import cn.eric.game.fujiatianxia6.po.General;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName CommandLineSerivce
 * @Description: 命令行服务
 * @Author YCKJ2725
 * @Date 2020/1/17
 * @Version V1.0
 **/
public class CommandLineSerivce {

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
        if(commandArray == null || commandArray.length == 0){
            System.out.println("命令输入错误");
        }else{
            String commandMain = commandArray[0];
            switch (commandMain){
                case "-help" :
                    commandAll();
                    break;
                case "-player" :
                    commandShowPlayer(commandArray[1]);
                    break;
                default:
                    break;
            }
        }

    }

    private void commandShowPlayer(String id) {

        General generalById = GeneralFactory.getGeneralById(id);
        System.out.println(generalById.memoPlayer());
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
