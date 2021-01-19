package Pieces;

public class Knight extends Piece{
	
	public Knight(int ally) {
		setPieceType("Knight");
		if(ally == 0) {
			setAlly(0);
			setName("White Knight");
			setImageName("WKnight");
		}
		else {
			setAlly(1);
    		setName("Black Knight");
    		setImageName("BKnight");
		}
	}
	
	public boolean validMove(int start, int end) {
		boolean valid = false;
		int product = end-start;
		//boundry for on space 2 or 7
		if ((start%8 <= 1 && (product == 6 || product == -10)
				|| start%8>=6 && (product == -6 || product == 10)))
			valid = false;
		//boundary for on space 1 or 8
		else if((start%8 == 0 && (product == 15 || product == -17)
				|| start%8==7 && (product == -15 || product == 17)))
			valid = false;
		else if(product == 15 || product == 17 || product == -15 || product == -17
				|| product == 10 || product == -6 || product == 6 || product == -10) {
			valid = true;
		}
			
		return valid; 
	}

}
