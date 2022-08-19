package sk.stuba.fei.uim.oop.game;

import sk.stuba.fei.uim.oop.controls.GameLogic;

import javax.swing.*;
import java.awt.*;

public class Game {

    public Game() {
        JFrame frame = new JFrame("Reversi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(930,670);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        GameLogic logic = new GameLogic();
        frame.setFocusable(true);
        frame.addKeyListener(logic);
        logic.getBoard().addMouseListener(logic);
        logic.getBoard().addMouseMotionListener(logic);
        frame.add(logic.getBoard());

        JPanel sideMenu = new JPanel();
        sideMenu.setBackground(Color.LIGHT_GRAY);
        JButton buttonRestart = new JButton("RESTART");
        buttonRestart.addActionListener(logic);
        buttonRestart.setFocusable(false);

        sideMenu.setLayout(new GridLayout(4, 1));
        sideMenu.add(logic.getLabelGameInfo());
        sideMenu.add(buttonRestart);
        sideMenu.add(logic.getLabelBoardSize());

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 6, 12, 6);
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(2);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setFocusable(false);
        slider.addChangeListener(logic);
        sideMenu.add(slider);

        frame.add(sideMenu, BorderLayout.LINE_END);
        frame.setVisible(true);
    }
}
