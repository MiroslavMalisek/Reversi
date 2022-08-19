package sk.stuba.fei.uim.oop.game;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.player.*;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {

    @Setter @Getter
    private int widthTile;
    @Setter @Getter
    private int heightTile;
    @Setter @Getter
    private boolean playable;
    @Setter @Getter
    private boolean higlighted;
    @Setter @Getter
    private Player player;
    private int radius;


    public Tile(Player player, int row, int col) {
        this.player = player;
        this.playable = false;
        this.higlighted = false;
        this.putClientProperty("row", row);
        this.putClientProperty("col", col);
        this.setBackground(new Color(10, 90, 10));
        this.setBorder(BorderFactory.createLineBorder(Color.white));
    }


    @Override
    protected void paintComponent(Graphics g) {
        this.widthTile = this.getWidth();
        this.heightTile = this.getHeight();
        this.radius = this.widthTile > this.heightTile ? this.heightTile : this.widthTile;
        super.paintComponent(g);
        if (this.player != null){
            this.player.paint(g, this.radius);
        }
        if (this.playable){
            this.paintPlayable((Graphics2D) g);
        }
        if (this.higlighted){
            this.paintHighligted((Graphics2D) g);
        }

    }

    private void paintPlayable(Graphics2D g){
        g.setStroke(new BasicStroke((float)widthTile/50));
        g.setColor(Color.LIGHT_GRAY);
        g.drawOval(15, 10, this.radius-20, this.radius-20);
    }

    private void paintHighligted(Graphics2D g){
        g.setStroke(new BasicStroke((float)widthTile/50));
        g.setColor(Color.BLACK);
        g.drawOval(15, 10, this.radius-20, this.radius-20);
    }
}
