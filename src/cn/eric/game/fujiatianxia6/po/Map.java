package cn.eric.game.fujiatianxia6.po;

import cn.eric.game.fujiatianxia6.factory.CityFactory;

/**
 * <p>Title: Map<／p>
 * <p>Description: 地图类<／p>
 * <p>Company: want-want<／p>
 *
 * @author 00322027
 * @date 2017年8月3日 上午10:02:14
 */
public class Map {
    public int[] map = new int[100];   //地图99格  每个城市占据三格  一共有22个城市66格 特殊占据2个格 34格 起始位置1格 每次通过加钱加将 酒馆4个8格 轮盘4个8格 暂停4个8格 募兵处4个8 35格
    // 城市
    public int[] city = {1, 2, 3, 4, 5, 6, 11, 12, 13, 14, 15, 16, 21, 22, 23, 24, 25, 26, 33, 34, 35, 36, 37, 38, 41, 42, 43, 44, 45, 46, 51, 52, 53, 54, 55, 56, 59, 60, 61, 62, 63, 64, 67, 68, 69, 70, 71, 72, 77, 78, 79, 80, 81, 82, 85, 86, 87, 90, 91, 92, 93, 94, 95, 96, 97, 98};

    public Object[] mapObj = new Object[100];

    // 幸运轮盘
    int[] luckyTurn = {7, 8, 27, 28, 47, 48, 73, 74}; //幸运轮盘
    // 酒馆
    int[] wine = {9, 10, 29, 30, 49, 50, 75, 76};   //酒馆
    // 暂停
    int[] pause = {17, 18, 31, 32, 57, 58, 83, 84};         //暂停
    //
    int[] soldiers = {19, 20, 39, 40, 65, 66, 88, 89};   //募兵处
    //
    int[] begin = {0};
    //
    int[] end = {99};

    String[] graphs = {"壹", "贰", "叁", "肆"};

    /**
     * 生成地图:
     * 关卡代号为：1：幸运轮盘 2：地雷  3: 暂停 4：时空隧道 0：普通
     */
    public void createMap() {
        int i = 0;

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
        map[99] = 6;
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
                graph = "◎";
                break;
            case 2:   //酒馆
                graph = "♚";
                break;
            case 3:   //暂停
                graph = "☁";
                break;
            case 4:   //募兵
                graph = "♞";
                break;
            case 5:   //开始
                graph = "&";
                break;
            case 6:   //结束
                graph = "★";
                break;
            case 11:  //
                graph = "㊀";
                break;
            case 21:  //
                graph = "㊁";
                break;
            case 31:  //
                graph = "㊂";
                break;
            case 41:  //
                graph = "㊃";
                break;
            default:
                graph = "♙";
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
    public void showLine1(int start, int end,int[] playerPos) {
        for (int i = start; i <= end; i++) {
            System.out.print(getGraph(map[i], i, playerPos));
            System.out.print(" ");
        }
    }

    /**
     * 输出地图的偶数行（第2行）
     *
     * @param start      输出的起始点在地图上的位置
     * @param end        输出的结束点在地图上的位置
     */
    public void showLine2(int start, int end, int[] playerPos) {
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
     * @param playerPos1 玩家1的当前位置
     * @param playerPos2 玩家2的当前位置
     */
    public void showRLine(int start, int end, int[] playerPos) {
        int h = 1;
        for (int i = start; i <= end; i++) {
            System.out.print(getGraph(map[100 - h], 100 - h, playerPos));
            for (int j = 32; j > 0; j--) {  //输出24个空格
                System.out.print("  ");
            }
            System.out.print(getGraph(map[i], i, playerPos));
            h = h + 1;
            System.out.println();
        }
    }

    /**
     * 输出地图的左竖列
     *
     * @param start      输出的起始点在地图上的位置
     * @param end        输出的结束点在地图上的位置
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
        showLine1(0, 24, playerPos);   //显示地图第一行
        System.out.println();                     //换行
        showRLine(25, 49, playerPos);  //显示地图竖行
        showLine2(50, 74, playerPos); //显示地图第二行
        // showRLine(25,49, playerPos);  //显示地图竖行
    }

    public void initCity() {
        // 初始化酒馆
        // 初始化幸运
        // 初始化募兵所
        int cityIndex = 1;
        int citySize = 3;
        for (int i = 0; i < map.length; i++) {
            //如果是城市
            if (map[i] == 0) {
                mapObj[i] = CityFactory.citys[cityIndex];
                if (--citySize == 0) {  //表示需要一个新的城市
                    cityIndex++;
                    citySize = 3;
                }
            }
        }
    }

    public void reloadCity() {
        // 初始化募兵所
        int cityIndex = 1;
        int citySize = 3;
        for (int i = 0; i < map.length; i++) {
            //如果是城市
            if (map[i] == 0 || map[i] == 11 || map[i] == 21 || map[i] == 31 || map[i] == 41) {
                mapObj[i] = CityFactory.citys[cityIndex];
                if (--citySize == 0) {  //表示需要一个新的城市
                    cityIndex++;
                    citySize = 3;
                }
            }
        }
    }

}
