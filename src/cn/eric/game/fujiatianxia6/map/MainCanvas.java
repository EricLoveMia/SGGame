package cn.eric.game.fujiatianxia6.map;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;  
  
public class MainCanvas extends Canvas {  
      
    // 游戏地图  
    private final GameMap gameMap;
    private final GraphicsContext gContext;
    private final Image map;
    private final int tileWidth = 32;
    private final int tileHeight = 32;
  
    private final boolean isRunning = true;
    private final long sleep = 100;
    // 主线程  
    private final Thread thread = new Thread(new Runnable() {
  
        @Override  
        public void run() {  
            while (isRunning) {  
                Platform.runLater(new Runnable() {  
  
                    @Override  
                    public void run() {  
                        draw();  
                        update();  
                    }  
                });  
                try {  
                    Thread.sleep(sleep);  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    });  
    public MainCanvas(double width, double height) {  
        super(width, height);  
        map = new Image(getClass().getResourceAsStream("map0.png"));  
        gContext = getGraphicsContext2D();  
  
        // 初始化游戏地图  
        gameMap = new GameMap(tileWidth, tileHeight, map);  
  
        thread.start();  
    }  
  
    public void draw() {  
        gameMap.drawMap(gContext);        
    }  
  
    public void update() {  
  
    }  
}  
