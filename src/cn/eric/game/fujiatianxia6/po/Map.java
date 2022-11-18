package cn.eric.game.fujiatianxia6.po;

import cn.eric.game.fujiatianxia6.enums.ConsoleColors;
import cn.eric.game.fujiatianxia6.factory.CityFactory;

import java.util.List;

/**
 * <p>Title: Map<／p>
 * <p>Description: 地图类<／p>
 * <p>Company: want-want<／p>
 *
 * @author 00322027
 * @date 2017年8月3日 上午10:02:14
 */
public class Map {

    private CampaignMap campaignMap;

    private int size;
    public int[] map;
    public Object[] mapObj;
    private int[] luckyTurn;
    private int[] wine;
    private int[] pause;
    private int[] soldiers;

    private Map(){}

    public Map(CampaignMap campaignMap) {
        this.campaignMap = campaignMap;
        this.size = campaignMap.getSize();
        this.map = new int[size];
        this.mapObj = new Object[size];

        this.luckyTurn = new int[campaignMap.getLuckyTurn().size()];
        for (int i = 0; i < campaignMap.getLuckyTurn().size(); i++) {
            luckyTurn[i] = Integer.parseInt(campaignMap.getLuckyTurn().get(i));
        }

        this.wine = new int[campaignMap.getWine().size()];
        for (int i = 0; i < campaignMap.getWine().size(); i++) {
            wine[i] = Integer.parseInt(campaignMap.getWine().get(i));
        }

        this.pause = new int[campaignMap.getPause().size()];
        for (int i = 0; i < campaignMap.getPause().size(); i++) {
            pause[i] = Integer.parseInt(campaignMap.getPause().get(i));
        }

        this.soldiers = new int[campaignMap.getSoldiers().size()];
        for (int i = 0; i < campaignMap.getSoldiers().size(); i++) {
            soldiers[i] = Integer.parseInt(campaignMap.getSoldiers().get(i));
        }
    }
    // 地图  每个城市占据三格  特殊占据2个格 起始位置1格 每次通过加钱加将 酒馆4个8格 轮盘4个8格 暂停4个8格 募兵处4个8 35格

//    // 城市
//    public int[] city = {1, 2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 16, 21, 22, 23, 24, 25, 26, 33, 34, 35, 36, 37, 38, 41, 42, 43, 44, 45, 46, 51, 52, 53, 54, 55, 56, 59, 60, 61, 62, 63, 64, 67, 68, 69, 70, 71, 72, 77, 78, 79, 80, 81, 82, 85, 86, 87, 90, 91, 92, 93, 94, 95, 96, 97, 98};
//
//    public Object[] mapObj = new Object[size];
//
//    // 幸运轮盘
//    int[] luckyTurn = {7, 8, 27, 28, 47, 48, 73, 74};
//    // 酒馆
//    int[] wine = {9, 10, 29, 30, 49, 50, 75, 76};
//    // 暂停
//    int[] pause = {17, 18, 31, 32, 57, 58, 83, 84};
//    //
//    int[] soldiers = {19, 20, 39, 40, 65, 66, 88, 89};
//    // 开始点
//    int[] begin = {0};
//    // 结束点
//    int[] end = {size - 1};

    public static String[] graphs = {ConsoleColors.RED + "壹" + ConsoleColors.RESET, ConsoleColors.BLUE + "贰" + ConsoleColors.RESET,
            ConsoleColors.GREEN + "叁" + ConsoleColors.RESET, ConsoleColors.CYAN + "肆" + ConsoleColors.RESET};

    /**
     * 生成地图:
     * 关卡代号为：1：幸运轮盘 2：酒馆  3: 暂停 4：兵站  0：普通 5 起点  6 终点
     */
    public void createMap() {
        int i;

        //在对战地图上设置幸运轮盘
        for (i = 0; i < luckyTurn.length; i++) {
            map[luckyTurn[i]] = 1;
        }

        //在对战地图上设置地雷
        for (i = 0; i < wine.length; i++) {
            map[wine[i]] = 2;
        }

        //在对战地图上设置暂停
        for (i = 0; i < pause.length; i++) {
            map[pause[i]] = 3;
        }

        //在对战地图上设置募兵所
        for (i = 0; i < soldiers.length; i++) {
            map[soldiers[i]] = 4;
        }

        //
        map[0] = 5;
        map[size - 1] = 6;
    }

    /**
     * 显示地图关卡对应的图形
     *
     * @param i          地图当前位置的关卡代号
     * @param index      当前地图位置编号
     * @return 地图当前位置的对应图片
     */
    public String getGraph(int i, int index, int[] playerPos) {
        String graph = "";
        for (int j = 0; j < playerPos.length; j++) {
            if (index == playerPos[j]) {
                return graphs[j];
            }
        }
        switch (i) {
            case 1:   //幸运轮盘
                graph = ConsoleColors.YELLOW + "运" + ConsoleColors.RESET + "";
                break;
            case 2:   //酒馆
                graph = ConsoleColors.YELLOW +"酒"+ ConsoleColors.RESET + "";
                break;
            case 3:   //赌场
                graph = ConsoleColors.YELLOW + "赌" + ConsoleColors.RESET + "";
                break;
            case 4:   //募兵
                graph = ConsoleColors.YELLOW + "兵"+ ConsoleColors.RESET + "";
                break;
            case 5:   //开始
                graph = ConsoleColors.RESET + "起"+ ConsoleColors.RESET + "";
                break;
            case 6:   //结束
                graph = ConsoleColors.RESET + "终"+ ConsoleColors.RESET + "";
                break;
            case 11:  //
                graph = ConsoleColors.RED +"一"+ ConsoleColors.RESET + "";
                break;
            case 21:  //
                graph = ConsoleColors.BLUE +"二"+ ConsoleColors.RESET + "";
                break;
            case 31:  //
                graph = ConsoleColors.GREEN +"三"+ ConsoleColors.RESET + "";
                break;
            case 41:  //
                graph = ConsoleColors.CYAN +"四"+ ConsoleColors.RESET + "";
                break;
            default:
                graph = ConsoleColors.RESET +"空"+ ConsoleColors.RESET + "";
                break;
        }
        return graph;
}

    /**
     * 输出地图的奇数行（第1、3行）
     *
     * @param start      输出的起始点在地图上的位置
     * @param end        输出的结束点在地图上的位置
     */
    public void showLine1(int start, int end, int[] playerPos) {
        System.out.print(" ");
        for (int i = start; i <= end; i++) {
            System.out.print(getGraph(map[i], i, playerPos));
            System.out.print(" ");
        }
    }

    /**
     * 输出地图的奇数行（第1、3行）
     *
     * @param start 输出的起始点在地图上的位置
     * @param end   输出的结束点在地图上的位置
     */
    public void showLine1New(int start, int end, int[] playerPos) {
        StringBuilder builder = new StringBuilder();
        for (int i = start; i <= end; i++) {
            //System.out.print(getGraph(map[i], i, playerPos));
            //System.out.print(" ");
            if (i == start) {
                builder.append("≔≕≔≕≔≕");
            } else {
                builder.append("≔≕≔≕");
            }
        }
        builder.append(System.lineSeparator());
        for (int i = start; i <= end; i++) {
            if (i == start) {
                builder.append("|");
            } else {
                builder.append(" ").append(getGraph(map[i], i, playerPos)).append(" ").append("|");
            }
        }
        builder.append(System.lineSeparator());
        for (int i = start; i <= end; i++) {
            if (i == start) {
                builder.append("≔≕≔≕≔≕");
            } else {
                builder.append("≔≕≔≕");
            }
        }
        System.out.println(builder.toString());
    }

    /**
     * 输出地图的偶数行（第2行）
     *
     * @param start 输出的起始点在地图上的位置
     * @param end   输出的结束点在地图上的位置
     */
    public void showLine2(int start, int end, int[] playerPos) {
        System.out.print(" ");
        for (int i = end; i >= start; i--) {
            System.out.print(getGraph(map[i], i, playerPos));
            System.out.print(" ");
        }
    }

    /**
     * 输出地图的竖列
     *
     * @param start      输出的起始点在地图上的位置 25-49  还有 75-99
     * @param end        输出的结束点在地图上的位置
     * @param playerPos  玩家的当前位置
     */
    public void showRLine(int start, int end, int[] playerPos) {
        System.out.println();
        int add = 0;
        if(size / 2 > 40){
           add = 1;
        }
        int h = 1;
        for (int i = start; i <= end; i++) {
            System.out.print(getGraph(map[size - h], size - h, playerPos));
            // 输出24个空格
            for (int j = (int) (1.3 * (size / 4) + add); j > 0; j--) {
                System.out.print("  ");
            }
            System.out.print(getGraph(map[i], i, playerPos));
            h = h + 1;
            System.out.println();
        }
    }

    /**
     * 输出地图的竖列
     *
     * @param start     输出的起始点在地图上的位置 25-49  还有 75-99
     * @param end       输出的结束点在地图上的位置
     * @param playerPos 玩家的当前位置
     */
    public void showRLineNew(int start, int end, int[] playerPos) {
        int add = 0;
        if (size / 2 > 40) {
            add = 1;
        }
        int h = 1;
        for (int i = start; i <= end; i++) {
            StringBuilder builder = new StringBuilder();

            builder.append("|");
            builder.append(" ").append(getGraph(map[size - h], size - h, playerPos)).append(" ").append("|");
            // 输出24个空格
            for (int j = (int) (1.66 * (size / 4) + add); j > 0; j--) {
                builder.append("  ");
            }
            builder.append("|").append(" ").append(getGraph(map[i], i, playerPos)).append(" ").append("|");
            builder.append(System.lineSeparator());
            builder.append("≔≕≔≕≔");
            // 输出24个空格
            for (int j = (int) (1.66 * (size / 4) + add); j > 0; j--) {
                builder.append("  ");
            }
            builder.append(" ").append("≔≕≕≔≔");

            h = h + 1;
            System.out.println(builder.toString());
        }
    }

    /**
     * 输出地图的左竖列
     *
     * @param start 输出的起始点在地图上的位置
     * @param end   输出的结束点在地图上的位置
     */
    public void showLLine(int start, int end, int[] playerPos) {
        for (int i = start; i < end; i++) {
            System.out.println(getGraph(map[i], i, playerPos));
        }
    }

    /**
     * 显示对战地图
     *
     * @param playerPos 所有玩家的位置
     */
    public void showMap(int[] playerPos) {
        int line = size / 4;
        //显示地图第一行
        showLine1(0, line - 1, playerPos);
        // showLine1New(0, line - 1, playerPos);
        // 换行
        showRLine(line, line * 2 - 1, playerPos);
        // showRLineNew(line, line * 2 - 1, playerPos);
        // 显示地图竖行
        // 显示地图第二行
        showLine2(line * 2, line * 3 - 1, playerPos);
        // showRLine(25,49, playerPos);  //显示地图竖行
        System.out.println();
        if (campaignMap != null) {
            System.out.println("战役:" + campaignMap.getName());
        }
    }

    public void initCity() {
        // 初始化酒馆
        // 初始化幸运
        // 初始化募兵所
        List<String> cityIds = campaignMap.getCityId();
        int index = 0;
        int citySize = 3;
        for (int i = 0; i < map.length; i++) {
            //如果是城市
            if (map[i] == 0) {
                mapObj[i] = CityFactory.getCityById(cityIds.get(index));
                if (--citySize == 0) {  //表示需要一个新的城市
                    index++;
                    citySize = 3;
                }
            }
        }
    }

    public void reloadCity() {
        List<String> cityIds = campaignMap.getCityId();
        // 初始化城市
        int cityIndex = 0;
        int citySize = 3;
        for (int i = 0; i < map.length; i++) {
            //如果是城市
            if (map[i] == 0 || map[i] == 11 || map[i] == 21 || map[i] == 31 || map[i] == 41) {
                mapObj[i] = CityFactory.getCityById(cityIds.get(cityIndex));
                if (--citySize == 0) {  //表示需要一个新的城市
                    cityIndex++;
                    citySize = 3;
                }
            }
        }
    }

    public CampaignMap getCampaignMap() {
        return campaignMap;
    }

    public int getSize() {
        return size;
    }

    public void setCampaignMap(CampaignMap campaignMap) {
        this.campaignMap = campaignMap;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int[] getMap() {
        return map;
    }

    public void setMap(int[] map) {
        this.map = map;
    }

    public Object[] getMapObj() {
        return mapObj;
    }

    public void setMapObj(Object[] mapObj) {
        this.mapObj = mapObj;
    }

    public int[] getLuckyTurn() {
        return luckyTurn;
    }

    public void setLuckyTurn(int[] luckyTurn) {
        this.luckyTurn = luckyTurn;
    }

    public int[] getWine() {
        return wine;
    }

    public void setWine(int[] wine) {
        this.wine = wine;
    }

    public int[] getPause() {
        return pause;
    }

    public void setPause(int[] pause) {
        this.pause = pause;
    }

    public int[] getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(int[] soldiers) {
        this.soldiers = soldiers;
    }

    public String[] getGraphs() {
        return graphs;
    }

    public void setGraphs(String[] graphs) {
        Map.graphs = graphs;
    }
}
