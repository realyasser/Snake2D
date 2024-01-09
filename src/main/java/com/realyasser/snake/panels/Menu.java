package com.realyasser.snake.panels;

import com.realyasser.snake.Main;
import com.realyasser.snake.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class Menu extends JPanel implements MouseListener {

    public Menu() {

        this.setBackground(new Color(0, 0, 0));
        this.setPreferredSize(new Dimension(Window.width, Window.height));

        this.setLayout(null);

        JLabel GameTitle = new JLabel();

        GameTitle.setText("Snake Game");
        GameTitle.setForeground(new Color(255, 255, 255));
        Font TitleFont = new Font("Arial", Font.BOLD, 50);
        GameTitle.setFont(TitleFont);
        Rectangle2D dim = TitleFont.getStringBounds("Snake Game", new FontRenderContext(null, true, true));
        GameTitle.setSize(new Dimension((int) dim.getWidth(), (int) dim.getHeight()));

        GameTitle.setBounds((Window.width-GameTitle.getWidth())/2, 100, GameTitle.getWidth(), GameTitle.getHeight());

        JButton PlayButton = new JButton();
        PlayButton.setText("Play");

        Font buttonFont = new Font("Arial", Font.PLAIN, 24);
        PlayButton.setFont(buttonFont);
        PlayButton.setBackground(new Color(255, 255, 255));

        Rectangle2D Buttondim = buttonFont.getStringBounds("Play", new FontRenderContext(null, true, true));

        PlayButton.setSize(new Dimension(((int) Buttondim.getWidth()) + 120, ((int) Buttondim.getHeight())+18));
        PlayButton.setBounds((Window.width-PlayButton.getWidth())/2, 240, PlayButton.getWidth(), PlayButton.getHeight());

        PlayButton.addMouseListener(this);

        this.add(GameTitle, BorderLayout.CENTER); // OK JUST LOOk
        this.add(PlayButton, BorderLayout.CENTER);

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

        Window.Display.show(Main.window.getContentPane(), "game");
        if(Window.game.requestFocusInWindow()) {
            Window.game.start();
        } else {
            JOptionPane.showMessageDialog(null, "The game couldn't be focused. Game failed to start.", "Unable to start game", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
}
