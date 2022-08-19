package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.game.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PC extends Player{

    public PC(int sizeBoard) {
        this.numberStones = 2;
        this.sizeBoard = sizeBoard;
    }

    @Override
    public void playTile(Tile[][] board, int row, int col) {
        this.playableTiles(board);
        if (!tilesToCapture.isEmpty()){
            List<Tile> mostStones = new ArrayList<>(tilesToCapture.get(0));
            for(int i = 1; i < tilesToCapture.size(); i++){
                int rowToBeFound = (int)tilesToCapture.get(i).get(0).getClientProperty("row");
                int colToBeFound = (int)tilesToCapture.get(i).get(0).getClientProperty("col");
                if ((rowToBeFound == (int)mostStones.get(0).getClientProperty("row")) &&
                        (colToBeFound == (int)mostStones.get(0).getClientProperty("col"))){
                    mostStones.addAll(tilesToCapture.get(i));
                }
                if (tilesToCapture.get(i).size() > mostStones.size()){
                    mostStones = tilesToCapture.get(i);
                }
            }
            for (Tile tile : mostStones){
                int rowComp = (int)tile.getClientProperty("row");
                int colComp = (int)tile.getClientProperty("col");
                board[rowComp][colComp].setPlayer(this);
                board[rowComp][colComp].setPlayable(false);
                board[rowComp][colComp].repaint();
            }
        }
    }

    @Override
    public void playableTiles(Tile[][] board) {
        this.tilesToCapture = new ArrayList<java.util.List<Tile>>();
        for(int row = 0; row < sizeBoard; row++){
            for(int col = 0; col < sizeBoard; col++){
                if (board[row][col].getPlayer() == null){
                    this.chceckDirection(board, row, col, -1, -1);
                    this.chceckDirection(board, row, col, -1, 0);
                    this.chceckDirection(board, row, col, -1, 1);
                    this.chceckDirection(board, row, col, 0, -1);
                    this.chceckDirection(board, row, col, 0, 1);
                    this.chceckDirection(board, row, col, 1, -1);
                    this.chceckDirection(board, row, col, 1, 0);
                    this.chceckDirection(board, row, col, 1, 1);
                }
            }
        }

    }

    private void chceckDirection(Tile[][] board, int row, int col, int rowDir, int colDir) {
        List<Tile> tilesInDirection = new ArrayList<>();
        tilesInDirection.add(board[row][col]);
        int iteration = 0;
        while (true) {
            iteration = iteration+1;
            row = row + rowDir;
            col = col + colDir;
            if (this.indexOutOfBoard(row, col)){
                break;
            }
            if (board[row][col].getPlayer() == null){
                break;
            }
            if ((iteration == 1) && ((board[row][col].getPlayer() instanceof PC))){
                break;
            }
            if ((iteration > 1) && ((board[row][col].getPlayer() instanceof PC))){
                tilesInDirection.add(board[row][col]);
                break;
            }
            tilesInDirection.add(board[row][col]);
        }
        //if there is a playable combination in this direction, store it to arrayList
        if (tilesInDirection.size() > 1){
            if (tilesInDirection.get(tilesInDirection.size()-1).getPlayer() instanceof PC){
                this.tilesToCapture.add(tilesInDirection);
            }
        }
    }

    @Override
    public boolean hasPlayableMove(Tile[][] board) {
        this.playableTiles(board);
        return this.tilesToCapture.size() != 0;
    }


    @Override
    public void paint(Graphics g, int radius) {
        g.setColor(Color.BLACK);
        g.fillOval(15, 10, radius-20, radius-20);
    }
}
