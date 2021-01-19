package Pieces;

public class Bishop extends Piece{
	
	public Bishop(int ally) {
		setPieceType("Bishop");
		if (ally == 0) {
		    setAlly(0);
		    setName("White Bishop");
		    setImageName("WBishop");
		}
		else {
			setAlly(1);
			setName("Black Bishop");
			setImageName("BBishop");
		
		}
	}
	
	public boolean validMove(int start, int end) {
		boolean valid = false;
		int product = end-start;
		int maxLeft =  1+start%8;
		int maxRight = 8-start%8;
		if(product > 0 && product%9 == 0 && product < (maxRight*9)) {
			//moving down right 
			valid = true;
		}
		else if(product > 0 && product%7 == 0 && product<(maxLeft*7)) {
			//moving down left
			valid = true;
		}
		else if(product < 0 && Math.abs(product)%9 == 0 && Math.abs(product)<(maxLeft*9)) {
			//moving up left 
			valid = true; 
		}
		else if(product <0 && Math.abs(product)%7 == 0 && Math.abs(product)<(maxRight*7)) {
			//moving up right
			valid = true; 
		}
			
		return valid; 
	}

}
