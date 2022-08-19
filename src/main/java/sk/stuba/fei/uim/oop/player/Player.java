package sk.stuba.fei.uim.oop.player;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.game.Tile;

import java.awt.*;
import java.util.List;

public abstract class Player {
    @Getter @Setter
    protected int numberStones;
    protected int sizeBoard;
    @Getter @Setter
    protected List<List<Tile>> tilesToCapture;

    public void reset(int sizeBoard){
        this.numberStones = 2;
        this.sizeBoard = sizeBoard;
    }
    public void incrementNumberStones(){
        this.numberStones++;
    }

    protected boolean indexOutOfBoard(int row, int col){
        return ((row < 0) || (col < 0) || (row >= sizeBoard) || (col >= sizeBoard));
    }

    public abstract void playTile(Tile[][] board, int row, int col);
    public abstract void playableTiles(Tile[][] board);
    public abstract boolean hasPlayableMove(Tile[][] board);
    public abstract void paint(Graphics g, int radius);
}
