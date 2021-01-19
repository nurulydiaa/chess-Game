package chessGame;
import javax.swing.JOptionPane;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;


public class chessGame {
	
	public String[] gamePlacement;
	public int turn; //0=white 1=black
	public int turnNumber;
	public Piece[] p = new Piece[64];
	public Piece[] grave = new Piece[32];
	public Piece holder = new Piece();
	public int inCheck = -1;
	public Piece[]checkHolder = new Piece[64];
	
	
	public chessGame(){
		
	
    for (int i = 0; i<64; i++) {
    	if (i==0 ||i == 7) {
    		p[i] = new Rook(1);
    	}
    	else if (i==1||i==6){
    		p[i] = new Knight(1);
    	}
    	else if (i==2||i==5){
    		p[i] = new Bishop(1);
    	}
    	else if (i==3){
    		p[i] = new Queen(1);
    	}
    	else if (i==4){
    		p[i] = new King(1);
    	}
    	else if (i==63 ||i == 56){
    		p[i] = new Rook(0);
    	}
    	else if (i==62||i==57){
    		p[i] = new Knight(0);
    	}
    	else if (i==61||i==58){
    		p[i] = new Bishop(0);
    	}
    	else if (i==59){
    		p[i] = new Queen(0);
    	}
    	else if (i==60){
    		p[i] = new King(0);
    	}
    	
    	else if (i>7 && i <16){
    		p[i] = new Pawn(1);
    	}
    	else if(i>47 && i < 56){
    		p[i] = new Pawn(0);
    	}
    	else 
    		p[i] = new Piece();
    	if(i<32)
    	    grave[i] = new Piece();
    }
    turn = 0; 
    turnNumber = 0;
	}
  public void changeTurn() {
	  if(turn == 0)
		  turn = 1;
	  else
		  turn = 0;
  }
  public void turnUp() {
	  turnNumber++;
  }
  
  public void gameOver() {
	  String winner;
	  if(turn != 1) 
		  winner = "Black";
	  else
		  winner = "White";
	  System.out.println("Game over "+winner+" wins");
	  JOptionPane.showMessageDialog(null, "Game over "+winner+" wins");
      System.exit(0);
  }
}

