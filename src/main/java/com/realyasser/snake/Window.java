package com.realyasser.snake;

import com.realyasser.snake.panels.Game;
import com.realyasser.snake.panels.Menu;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Window extends JFrame {

    public static final int width = 525;
    public static final int height = 525;

    public static CardLayout Display = new CardLayout();
    public static Game game;

    Window () {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Snake game (1.0)");

        this.setBackground(new Color(0,0,0));

        this.setLayout(Display);

        this.add(new Menu(), "menu");
        try {
            game = new Game();
            this.add(game, "game");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }


        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
}
