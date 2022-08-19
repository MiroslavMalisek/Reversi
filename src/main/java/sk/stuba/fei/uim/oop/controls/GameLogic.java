package sk.stuba.fei.uim.oop.controls;

import lombok.Getter;
import sk.stuba.fei.uim.oop.game.*;
import sk.stuba.fei.uim.oop.player.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameLogic extends UniversalAdapter{
    @Getter
    private Board board;
    @Getter
    private User playerUser;
    @Getter
    private PC playerPC;
    private JComponent previousComp;
    @Getter
    private JLabel labelGameInfo;
    @Getter
    private JLabel labelBoardSize;
    private int slideValBefore;

    public GameLogic() {
        this.playerUser = new User(6);
        this.playerPC = new PC(6);
        this.board = new Board(this.playerUser, this.playerPC);
        this.playerUser.playableTiles(this.board.getBoard());
        this.labelGameInfo = new JLabel();
        this.labelGameInfo.setText("Your turn...");
        this.labelGameInfo.setHorizontalAlignment(JLabel.CENTER);
        this.labelBoardSize = new JLabel();
        this.labelBoardSize.setText("Actual size of board: " + this.board.getSizeBoard() + "x" + this.board.getSizeBoard());
        this.labelBoardSize.setHorizontalAlignment(JLabel.CENTER);
        this.slideValBefore = 6;
    }

    public void reset(){
        //System.out.println("reset");
        this.board.resetBoard();
        this.labelGameInfo.setText("Your turn...");
        this.playerUser.playableTiles(this.board.getBoard());
    }

    public void resizeBoard(int size){
        //System.out.println("resize");
        this.board.resizeBoard(size);
        this.labelGameInfo.setText("Your turn... ");
        this.labelBoardSize.setText("Actual size of board: " + this.board.getSizeBoard() + "x" + this.board.getSizeBoard());
        this.playerUser.playableTiles(this.board.getBoard());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.reset();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JComponent c = (JComponent)this.board.getComponentAt(e.getX(), e.getY());
        int row = (int) c.getClientProperty("row");
        int col = (int) c.getClientProperty("col");
        if (this.board.getBoard()[row][col].isPlayable()){
            this.playerUser.playTile(this.board.getBoard(), row, col);
            this.board.clearPlayableTiles();
            if (!this.playerPC.hasPlayableMove(this.board.getBoard())){
                //System.out.println("Player PC doesnt have valid moves");
                this.playerUser.playableTiles(this.board.getBoard());
                if (!this.playerUser.hasPlayableMove(this.board.getBoard())){
                    //if no player has valid moves
                    this.board.actualizeNumberStones();
                    this.getWinner();
                }
            }else {
                this.playerPC.playTile(this.board.getBoard(), 0, 0);
                while (true){
                    //System.out.println("while");
                    if(this.board.allTilesOccupied()){
                        this.getWinner();
                        break;
                    }
                    this.playerUser.playableTiles(this.board.getBoard());
                    if (!this.playerUser.hasPlayableMove(this.board.getBoard())){
                        if (!this.playerPC.hasPlayableMove(this.board.getBoard())){
                            this.board.actualizeNumberStones();
                            this.getWinner();
                            break;
                        }else {
                            this.playerPC.playTile(this.board.getBoard(), 0, 0);
                        }
                    }else {
                        break;
                    }
                    this.board.revalidate();
                    this.board.repaint();
                }
            }
            if (board.allTilesOccupied()){
               this.getWinner();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        JComponent actualComp = (JComponent)this.board.getComponentAt(e.getX(), e.getY());
        if (actualComp instanceof Tile){
            if (this.previousComp instanceof Tile){
                if (actualComp != this.previousComp) {
                    this.highlightTile(actualComp);
                    this.dehightlightTile();
                    this.previousComp = actualComp;
                }
            }else {
                this.highlightTile(actualComp);
                this.previousComp = actualComp;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JComponent actualComp = (JComponent)this.board.getComponentAt(e.getX(), e.getY());
        if (actualComp instanceof Tile){
            this.highlightTile(actualComp);
        }
        this.previousComp = actualComp;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if ((this.previousComp instanceof Tile)) {
            this.dehightlightTile();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 82){
            this.reset();
        }else if (e.getKeyCode() == 27){
            this.board.closeGame();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int val = ((JSlider) e.getSource()).getValue();
        if ((val != this.slideValBefore) && ((val == 6) || (val == 8) || (val == 10) || (val == 12))){
            this.resizeBoard(val);
            this.slideValBefore = val;
        }

    }

    private void highlightTile(JComponent actualComp){
        int row = (int) actualComp.getClientProperty("row");
        int col = (int) actualComp.getClientProperty("col");
        if (this.board.getBoard()[row][col].isPlayable()) {
            this.board.getBoard()[row][col].setHiglighted(true);
            this.board.getBoard()[row][col].repaint();
        }
    }

    private void dehightlightTile(){
        int row = (int) previousComp.getClientProperty("row");
        int col = (int) previousComp.getClientProperty("col");
        if ((row<this.board.getSizeBoard()) && (col<this.board.getSizeBoard())) {
            this.board.getBoard()[row][col].setHiglighted(false);
            this.board.getBoard()[row][col].repaint();
        }
    }

    private void getWinner(){
        if (this.playerPC.getNumberStones() == this.playerUser.getNumberStones()){
            this.labelGameInfo.setText("Game over...\n Draw!");
        }else if (this.playerPC.getNumberStones() > this.playerUser.getNumberStones()){
            this.labelGameInfo.setText("Game over...\n Winner is PC!");
        }else if (this.playerPC.getNumberStones() < this.playerUser.getNumberStones()){
            this.labelGameInfo.setText("Game over...\n You are the winner!");
        }
    }

}
