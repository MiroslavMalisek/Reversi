package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.game.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class User extends Player{

    public User(int sizeBoard) {
        this.numberStones = 2;
        this.sizeBoard = sizeBoard;
    }

    @Override
    public void playTile(Tile[][] board, int row, int col) {
        board[row][col].setHiglighted(false);
        for(List<Tile> list : tilesToCapture){
            int rowToBeFound = (int)list.get(0).getClientProperty("row");
            int colToBeFound = (int)list.get(0).getClientProperty("col");
            if ((rowToBeFound == row) && (colToBeFound == col)){
                for (Tile tile : list){
                    int rowComp = (int)tile.getClientProperty("row");
                    int colComp = (int)tile.getClientProperty("col");
                    board[rowComp][colComp].setPlayer(this);
                    board[rowComp][colComp].setPlayable(false);
                    board[rowComp][colComp].repaint();
                }
            }
        }
    }

    @Override
    public void playableTiles(Tile[][] board) {
        this.tilesToCapture = new ArrayList<List<Tile>>();
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
        for(List<Tile> list : tilesToCapture){
            list.get(0).setPlayable(true);
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
            if ((iteration == 1) && ((board[row][col].getPlayer() instanceof User))){
                break;
            }
            if ((iteration > 1) && ((board[row][col].getPlayer() instanceof User))){
                tilesInDirection.add(board[row][col]);
                break;
            }
            tilesInDirection.add(board[row][col]);
        }
        //if there is a playable combination in this direction, store it to arrayList
        if (tilesInDirection.size() > 1){
            if (tilesInDirection.get(tilesInDirection.size()-1).getPlayer() instanceof User){
                this.tilesToCapture.add(tilesInDirection);
            }
        }
    }

    public boolean hasPlayableMove(Tile [][] board){
        return this.tilesToCapture.size() != 0;
    }

    @Override
    public void paint(Graphics g, int radius) {
        g.setColor(Color.WHITE);
        g.fillOval(15, 10, radius-20,radius-20);
    }


}
