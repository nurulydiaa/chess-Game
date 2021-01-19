package Pieces;

public class King extends Piece{
	
	public King(int ally) {
		setPieceType("King");
		if (ally == 0) {
			setAlly(0);
			setName("White King");
			setImageName("WKing");
		}
		else {
			setAlly(1);
			setName("Black King");
			setImageName("BKing");
		}		
		hasMoved = false;
	   
	}
	
	public boolean validMove(int start, int end) {
		boolean valid = false;
		int product = end-start;
		if((start%8 == 0 && (product == -1 || product == 7 || product == -9 )
				|| start%8==7 && (product == 1 || product == -7 || product == 9))) {
			valid = false;
		}
		else if((product == -8 || product == 8 || product == 1 || product == -1 
				|| product == 9 || product == 7 || product == -7 || product == -9)) {
			valid = true;
		}
		
		//castling
		/*else if((product == 3 || product == 4 || product == -4 || product == -3) && hasMoved == false) {
			valid = true;
		}*/
			
		return valid; 
	}

}
