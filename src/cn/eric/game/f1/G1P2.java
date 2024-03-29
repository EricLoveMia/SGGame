package cn.eric.game.f1;

import javax.swing.*;
import java.awt.*;

public class G1P2 extends JFrame {
	
	public G1P2() {
		super("Radical Racing");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame. EXIT_ON_CLOSE);
		setVisible(true);
		
		Move1 m1 = new Move1();
		Move2 m2 = new Move2();
		m1.start();
		m2.start();
	}

	// 设置游戏屏幕大小 
	final int WIDTH = 900, HEIGHT = 650;
	// 设置车的行驶速度
	double p1Speed = .5,p2Speed = .5;
	
	
	// 举行分别表示五个区域
    Rectangle left = new Rectangle(0, 0,WIDTH / 9,HEIGHT); 
	Rectangle right = new Rectangle((WIDTH / 9) * 8,0,WIDTH / 9,HEIGHT); 
	Rectangle top = new Rectangle(0, 0,WIDTH, HEIGHT / 9);
	Rectangle bottom = new Rectangle(0, (HEIGHT / 9) * 8,(WIDTH / 9)*9,HEIGHT / 9); 
	Rectangle center = new Rectangle((int) ((WIDTH / 9) * 2.5),(int)((HEIGHT/ 9) * 2.5), (WIDTH/ 9) * 5,(HEIGHT/ 9) * 4);
    //绘制跑道上的障碍物
	Rectangle obstacle = new Rectangle(WIDTH / 2, (HEIGHT / 9) * 7,WIDTH / 10,HEIGHT / 9);
	Rectangle obstacle2 = new Rectangle(WIDTH / 3, (HEIGHT / 9) * 5,WIDTH / 10,HEIGHT / 4);
	Rectangle obstacle3 = new Rectangle(2 * (WIDTH / 3), (HEIGHT / 9) * 5,WIDTH / 10, HEIGHT / 4);
	Rectangle obstacle4 = new Rectangle( WIDTH / 3,HEIGHT / 9, WIDTH / 30, HEIGHT / 9);
	Rectangle obstacle5 = new Rectangle(WIDTH / 2, (int) ((HEIGHT / 9) *1.5), WIDTH / 30, HEIGHT / 4);
	// 下面的矩形用于绘制内外跑道的终点线
	Rectangle finish = new Rectangle(WIDTH / 9,(HEIGHT / 2) - HEIGHT /9, (int) ((WIDTH / 9) * 1.5), HEIGHT / 70);
	// 绘制外跑道的起跑线
	Rectangle lineO = new Rectangle (WIDTH / 9, HEIGHT / 2 , (int)((WIDTH/9) * 1.5) / 2, HEIGHT / 140);
	
	Rectangle lineI = new Rectangle(((WIDTH / 9) + ((int) ((WIDTH /9) * 1.5) / 2)),
			( HEIGHT / 2 ) + ( HEIGHT / 10),(int) ((WIDTH / 9 ) * 1.5)/2,HEIGHT/ 140);
    
	// 下面的矩形代表赛车
	Rectangle p1 = new Rectangle(WIDTH / 9,HEIGHT / 2,WIDTH / 30,WIDTH / 30); 
	Rectangle p2 = new Rectangle( ((WIDTH / 9) + ((int) ((WIDTH / 9) * 1.5) / 2)), 
			(HEIGHT / 2) + (HEIGHT / 10), WIDTH / 30, WIDTH / 30);
	public void paint(Graphics g){
    	super.paint(g);
    	// 绘制赛场背景
    	g.setColor(Color.DARK_GRAY);
    	g.fillRect(0, 0, WIDTH, HEIGHT);
    	// 现将场地设置为绿色
    	g.setColor(Color.GREEN);
        
    	g.fillRect(left.x, left.y, left.width, left.height);
    	g.fillRect(right.x, right.y, right.width, right.height);
    	g.fillRect(top.x, top.y, top.width, top.height); 
    	g.fillRect(bottom.x, bottom.y, bottom.width, bottom.height);
    	g.fillRect(center.x, center.y, center.width, center.height);
    	
    	g.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
    	g.fillRect(obstacle5.x, obstacle5.y, obstacle5.width, obstacle5.height);
    	g.fillRect(obstacle2.x, obstacle2.y, obstacle2.width, obstacle2.height);
    	g.fillRect(obstacle3.x, obstacle3.y, obstacle3.width, obstacle3.height);
    	g.fillRect(obstacle4.x, obstacle4.y, obstacle4.width, obstacle4.height);
    	
    	g.setColor(Color.WHITE);
    	
    	g.fillRect(lineO.x, lineO.y,lineO.width, lineO.height);
    	g.fillRect(lineI.x, lineI.y, lineI.width, lineI.height);
    	
    	g.setColor(Color.YELLOW);
    	g.fillRect(finish.x, finish.y, finish.width, finish.height);
    	
    	g.setColor(Color. BLUE);
    	g. fill3DRect(p1.x, p1.y,p1.width, p1.height, true);
    	g.setColor(Color.RED);
    	g.fill3DRect(p2.x, p2.y, p2.width, p2.height, true);
    	
    	
    }

    public static void main(String[] args) {
		new G1P2();
	}

    private class Move1 extends Thread {
		
    	public void run() {
			
			while(true){
				try{
					// 刷新游戏屏幕
					repaint();
					// 赛车行驶中加速
					if(p1Speed <= 5){
						p1Speed += .2;
					}
					p1.y -= p1Speed;					
					Thread.sleep(1000);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}

    }
    private class Move2 extends Thread {
    	public void run() {
    		
    		while(true){
    			try{
    				// 刷新游戏屏幕
    				repaint();
    				// 赛车行驶中加速
    				if(p2Speed <= 5){
    					p2Speed += .2;
    				}
    				p2.y -= p2Speed;					
    				Thread.sleep(1000);
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    		}
    	}
    	
    }
    
}
