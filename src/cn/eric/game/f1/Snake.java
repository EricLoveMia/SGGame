package cn.eric.game.f1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @ClassName Snake
 * @Description: TODO
 * @Author YCKJ2725
 * @Date 2021/2/2
 * @Version V1.0
 **/


class View extends JPanel {
    private static final long serialVersionUID = 01111111111L;
    private int width;
    private int height;
    private int cellSize;
    public int[][] grid;

    View(int width, int height, int cellSize, int[][] grid) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.grid = grid;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int max_value = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                max_value = grid[row][col] > max_value ? grid[row][col] : max_value;
            }
        }
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (grid[row][col] != 0) {
                    if (grid[row][col] > 0) {
                        int v = (int) (255 - ((double) grid[row][col] / max_value * 200 + 55));
                        g.setColor(new Color(v, v, v));
                    } else if (grid[row][col] == -1) {
                        g.setColor(Color.red);
                    }
                    g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                    // g.setColor(Color.white);
                    // g.drawString(""+(grid[row][col]), col*cellSize+cellSize/2-5, row*cellSize+cellSize/2+5);
                }
                g.setColor(Color.gray);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width * cellSize + 1, height * cellSize + 1);
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT
}

public class Snake {
    public static void main(String[] args) {
        final int size = 40;
        final int width = 1440 / size;
        final int height = 720 / size;
        int[][] grid = new int[height][width];
        System.out.println(grid[0].length);
        System.out.println(grid.length);
        grid[0][0] = 3; // initial length // -1 food 0 empty >0 : snake
        // grid[3][3] = -1; // the first food in (3,3)
        for (int col = 0; col < 10; col++) {
            grid[0][col + 1] = -1;
        }
        int[][] directions = {
                {-1, 0}, // up
                {1, 0}, // down
                {0, -1}, // left
                {0, 1} // right
        };
        boolean[] isPause = {false};
        int[] snakeHeadPos = {0, 0};
        Direction currDirectionIdx = Direction.RIGHT;
        Queue<Direction> queue = new LinkedBlockingDeque<Direction>();
        View view = new View(width, height, size, grid);

        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        // frame.setTitle("Snake");
        frame.add(view);
        frame.pack();
        frame.setVisible(true);


        frame.addKeyListener(new KeyAdapter() {
            Hashtable<Integer, Direction> keyCode_to_Direction = new Hashtable<Integer, Direction>() {{
                put(KeyEvent.VK_W, Direction.UP);
                put(KeyEvent.VK_A, Direction.LEFT);
                put(KeyEvent.VK_S, Direction.DOWN);
                put(KeyEvent.VK_D, Direction.RIGHT);
                put(KeyEvent.VK_UP, Direction.UP);
                put(KeyEvent.VK_LEFT, Direction.LEFT);
                put(KeyEvent.VK_DOWN, Direction.DOWN);
                put(KeyEvent.VK_RIGHT, Direction.RIGHT);
            }};

            @Override
            public void keyPressed(KeyEvent e) {
                // System.out.println("key code is " + e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    isPause[0] = !isPause[0];
                    System.out.println("isPause : " + isPause[0]);

                }
                if (keyCode_to_Direction.get(e.getKeyCode()) != null) {
                    queue.add(keyCode_to_Direction.get(e.getKeyCode()));
                }

            }

        });

        boolean x = true;
        while (true) {
            System.out.println(isPause[0]);
            if (isPause[0]) {
                continue;
            }
            while (queue.size() > 0 && queue.element() == currDirectionIdx) {
                queue.poll();
            }
            if (queue.size() > 0) {
                Direction nextDir = queue.poll();
                if ((nextDir.ordinal() ^ 1) != currDirectionIdx.ordinal()) {
                    currDirectionIdx = nextDir;
                }
            }
            // System.out.println("turn to " + currDirectionIdx);
            int headValue = grid[snakeHeadPos[0]][snakeHeadPos[1]];
            snakeHeadPos[0] += directions[currDirectionIdx.ordinal()][0] + height;
            snakeHeadPos[1] += directions[currDirectionIdx.ordinal()][1] + width;
            snakeHeadPos[0] %= height;
            snakeHeadPos[1] %= width;
            int frontValue = grid[snakeHeadPos[0]][snakeHeadPos[1]];
            grid[snakeHeadPos[0]][snakeHeadPos[1]] = headValue + 1;

            if (frontValue > 0) {
                break;
            }

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    // System.out.printf("%d %d\n", i, j);
                    if (grid[j][i] > 0) {

                        grid[j][i] -= (frontValue + 1);
                        // grid[j][i]--;

                        if (grid[j][i] < 0) {
                            grid[j][i] = 0;
                        }
                    }
                }
            }

            if (frontValue == -1) { // get the food
                int foodx;
                int foody;
                do {
                    foodx = (int) (Math.random() * height);
                    foody = (int) (Math.random() * width);
                } while (grid[foodx][foody] != 0);
                grid[foodx][foody] = -1;
            }

            // if ( snakeHeadPos[1] == width-1 || snakeHeadPos[1] == 0 ) {
            // 	if (x){
            // 		q.add(Direction.DOWN);
            // 		boolean _ = (snakeHeadPos[1] == 0) ? q.add(Direction.RIGHT) : q.add(Direction.LEFT);
            // 	}
            // 	x = !x;
            // }

            frame.repaint();
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

