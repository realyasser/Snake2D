package com.realyasser.snake.panels;

import com.realyasser.snake.Main;
import com.realyasser.snake.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;

public class Game extends JPanel implements ActionListener  {

    private static final int Tile = Window.width/15;
    private static final int CenterOfAxis = Tile;
    private static final int GTiles = Window.width/Tile-2;
    private static final int CenterOfArea = (int) GTiles/2;
    private static int AppleLocX = CenterOfArea;
    private static int AppleLocY = AppleLocX;


    private static final int SnakeHeadDefaultLocX = AppleLocX;
    private static final int SnakeHeadDefaultLocY = AppleLocY+3;


    private static int[] x;
    private static int[] y;


    private int SnakeSize = 3, StartSize = SnakeSize;


    private final Image wall;
    private final Image apple;
    private Font PauseFont, scoreFont;


    private Timer t;


    private enum Direction {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }

    private enum State {
        LOADING,
        PAUSE,
        PLAYING,
        GAMEOVER
    }

    private static State state = State.LOADING;

    private Direction direction = Direction.UP;

    public Game() throws IOException, FontFormatException {

        PauseFont = new Font("Arial", Font.BOLD, 35);
        scoreFont = new Font("Arial", Font.PLAIN, 18);
        wall = new ImageIcon(getClass().getResource("/wall.png")).getImage();
        apple = new ImageIcon(getClass().getResource("/apple.png")).getImage();

        this.setBackground(new Color(0,0,0));
        this.setPreferredSize(new Dimension(Window.width, Window.height));
        this.setFocusable(true);
        this.addKeyListener(new Keys());

    }

    @Override
    public void paint(Graphics graphics) {

        super.paint(graphics);

        Graphics2D g = (Graphics2D) graphics;

        int j = 0;
        boolean eastdir = true;
        for(int i = 0; i < (15 * 2); i++){
            g.setColor(Color.RED);

            g.drawImage(wall, j*Tile*(eastdir ? 1 : 0), j*Tile*(eastdir ? 0: 1), Tile, Tile, Color.ORANGE, null);
            g.drawImage(wall, Tile*((int) Math.pow(j, (eastdir ? 1: 0)))*((int) Math.pow(14, (eastdir ? 0 : 1))), ((int) Math.pow(14, (eastdir ? 1: 0)))*Tile*((int) Math.pow(j, (eastdir ? 0: 1))), Tile, Tile, Color.ORANGE, null);

            if (j == 15) {
                j=0;
                eastdir = !eastdir;
            };
            j++;
        }

        g.setColor(Color.red);
        g.drawImage(apple, AppleLocX*Tile+Tile, AppleLocY*Tile+Tile, Tile, Tile, Color.BLACK, null);

        g.setColor(Color.YELLOW);

        for (int i = 0; i < SnakeSize; i++){
            g.fillRect(x[i]*Tile+CenterOfAxis, y[i]*Tile+CenterOfAxis, Tile, Tile);
        }

        if (state == State.PAUSE) {
            g.setFont(PauseFont);
            g.setColor(Color.WHITE);
            g.drawString("PAUSED", (Window.width-g.getFontMetrics(PauseFont).stringWidth("PAUSED"))/2, Tile+40);
        } else if (state == State.GAMEOVER) {
            g.setFont(PauseFont);
            g.setColor(Color.RED);
            g.drawString("GAME OVER", (Window.width-g.getFontMetrics(PauseFont).stringWidth("GAME OVER"))/2, Tile+40);

            g.setFont(scoreFont);
            String scoretext = "Score: "+(SnakeSize-StartSize);
            g.drawString(scoretext, (Window.width-g.getFontMetrics(scoreFont).stringWidth(scoretext))/2, Tile+80);
        }

    }

    public void start() {

        // Too lazy to make a method to set default values lol

        AppleLocY = AppleLocX = CenterOfArea;
        SnakeSize = StartSize;
        direction = Direction.UP;

        x = new int[GTiles*GTiles];
        y = new int[GTiles*GTiles];

        for (int i = 0; i < SnakeSize; i++){
            x[i] += SnakeHeadDefaultLocX;
        }

        for (int i = 0; i < SnakeSize; i++){
            y[i] = SnakeHeadDefaultLocY+i;
        }

        t = new Timer(180, this);
        t.start();
        state = State.PLAYING;
    }

    void SnakeMove(){

        for (int i = SnakeSize;i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case UP:
                y[0] = y[0]-1;
                break;
            case DOWN:
                y[0] = y[0]+1;
                break;
            case LEFT:
                x[0] = x[0]-1;
                break;
            case RIGHT:
                x[0] = x[0]+1;
                break;
        }

    }

    void CheckApple() {
        if(x[0] == AppleLocX && y[0] == AppleLocY){
            SnakeSize++;
            SpawnApple();
        }
    }

    void SpawnApple(){
        AppleLocX = new Random().nextInt(13);
        AppleLocY = new Random().nextInt(13);

        for (int i = SnakeSize; i > 0; i--){
            if (x[i] == AppleLocX && y[i] == AppleLocY) {
                SpawnApple();
                break;
            }
        }

    }

    void CheckHits(){

        if(x[0] < 0 || y[0] < 0 || x[0] > GTiles-1 || y[0] > GTiles-1){
            state = State.GAMEOVER;
            return;
        }

        for(int i = SnakeSize; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){// Can you work please
                state = State.GAMEOVER;
                break;
            }
        }

    }

    class Keys implements KeyListener {

        public void keyTyped(KeyEvent e) {

        }

        public void keyPressed(KeyEvent e) {


            switch (e.getKeyCode()){
                case 38:
                case 87:
                    if (direction != Direction.DOWN) direction = Direction.UP;
                    break;
                case 37:
                case 65:
                    if (direction != Direction.RIGHT) direction = Direction.LEFT;
                    break;
                case 39:
                case 68:
                    if (direction != Direction.LEFT) direction = Direction.RIGHT;
                    break;
                case 40:
                case 83:
                    if (direction != Direction.UP) direction = Direction.DOWN;
                    break;
                case 32:
                    if (state == State.PLAYING) {
                        state = State.PAUSE;
                    } else if (state == State.PAUSE) {
                        state = State.PLAYING;
                    } else if (state == State.GAMEOVER){
                        Window.Display.show(Main.window.getContentPane(), "menu");
                    }
                    break;

            }
        }

        public void keyReleased(KeyEvent e) {

        }

    }

    public void actionPerformed(ActionEvent e) {
        if (state == State.PLAYING){
            SnakeMove();
            if (SnakeSize < (GTiles*GTiles)) {
                CheckApple();
            } else {
                JOptionPane.showMessageDialog(this, "You won the game! Amazing!", "Score: "+(SnakeSize-StartSize)+". THE BEST SCORE EVER", JOptionPane.INFORMATION_MESSAGE);
                t.stop();
                Window.Display.show(Main.window.getContentPane(), "menu");
                return;
            }
            CheckHits();
        } else if(state == State.GAMEOVER) {
            t.stop();
        }
        repaint();
    }
}
