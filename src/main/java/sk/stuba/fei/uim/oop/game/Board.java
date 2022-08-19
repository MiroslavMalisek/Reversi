package sk.stuba.fei.uim.oop.game;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.player.*;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    @Getter @Setter
    private int sizeBoard;
    @Getter
    private Tile [][] board;
    private User playerUser;
    private PC playerPC;

    public Board(User user, PC pc) {
        this.sizeBoard = 6;
        this.board = new Tile[this.sizeBoard][this.sizeBoard];
        this.playerUser = user;
        this.playerPC = pc;
        this.generateBoard();
        this.setLayout(new GridLayout(this.sizeBoard, this.sizeBoard));
    }

    public void generateBoard(){
        for (int i = 0; i < sizeBoard; i++) {
            for (int j = 0; j < sizeBoard; j++) {
                Tile tile;
                if (((i == (sizeBoard/2-1)) && j == (sizeBoard/2-1)) || ((i == (sizeBoard/2)) && j == (sizeBoard/2))) {
                    tile = new Tile(this.playerUser, i, j);
                }else if(((i == (sizeBoard/2-1)) && j == (sizeBoard/2)) || ((i == (sizeBoard/2)) && j == (sizeBoard/2-1))){
                    tile = new Tile(this.playerPC, i, j);
                } else {
                    tile = new Tile(null, i, j);
                }
                this.board[i][j] = tile;
                this.add(tile);
            }
        }
    }

    public void resetBoard(){
        this.clearTiles();
        this.playerUser.reset(this.sizeBoard);
        this.playerPC.reset(this.sizeBoard);
        this.generateBoard();
        this.revalidate();
        this.repaint();
    }

    public void closeGame(){
        ((JFrame)this.getTopLevelAncestor()).dispose();
    }

    public void resizeBoard(int size){
        this.sizeBoard = size;
        this.clearTiles();
        this.playerPC.reset(this.sizeBoard);
        this.playerUser.reset(this.sizeBoard);
        this.board = new Tile[this.sizeBoard][this.sizeBoard];
        this.generateBoard();
        this.setLayout(new GridLayout(sizeBoard, sizeBoard));
        this.revalidate();
        this.repaint();
    }

    private void clearTiles(){
        Component [] cs = this.getComponents();
        for(Component c : cs){
            this.remove(c);
        }
    }

    public void clearPlayableTiles(){
        for (Tile[] tiles : this.board){
            for (Tile tile : tiles){
                tile.setPlayable(false);
            }
        }
        this.revalidate();
        this.repaint();
    }

    public boolean allTilesOccupied(){
        for (Tile[] tiles : this.board){
            for (Tile tile : tiles){
                if (tile.getPlayer() == null){
                    return false;
                }
            }
        }
        this.actualizeNumberStones();
        return true;
    }

    public void actualizeNumberStones(){
        this.playerUser.setNumberStones(0);
        this.playerPC.setNumberStones(0);
        for (Tile[] tiles : board){
            for (Tile tile : tiles){
                if (tile.getPlayer() != null){
                    tile.getPlayer().incrementNumberStones();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
