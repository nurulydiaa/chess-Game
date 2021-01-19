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
	   
	}
	
	public boolean validMove(int start, int end) {
		boolean valid = false;
		int product = end-start;
		if((start%8 == 0 && (product == -1 || product == 7 || product == -9 )
				|| start%8==7 && (product == 1 || product == -7 || product == 9)))
			valid = false;
		else if((product == -8 || product == 8 || product == 1 || product == -1 
				|| product == 9 || product == 7 || product == -7 || product == -9)) {
			valid = true;
		}
			
		return valid; 
	}
	
	/* Castling: possible if knight has not moved at all, nor the rook, and the spaces between the 2 are empty. 
	 * The positions were they are placed, are if castle with left rook... King moves 2 to the left, and Rook on his right
	 * If it castles on the right, again, moves 2 to the right, but rook is on his left. 
	 * If those conditions are met, then when the King is selected, the areas highlighted for valid moves for the king
	 * Include the 2 spots on the left and right of the king. */

}
