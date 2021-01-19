package Pieces;

public class Rook extends Piece{
	
	public Rook(int ally) {
		setPieceType("Rook");
		if(ally == 0) {
			setAlly(0);
			setName("White Rook");
			setImageName("WRook");			
		}
		else {
			setAlly(1);
    		setName("Black Rook");
    		setImageName("BRook");
		}
	}
	
	public boolean validMove(int start, int end) {
		boolean valid = false;
		int product = end-start;
		int maxRight = 8-start%8;
		int maxLeft = start%8+1;
		//System.out.println(inverse+" "+start+" "+end+" "+maxRight+" "+maxLeft);
		if(product % 8 == 0) {
			//vertical movement
			valid = true;
		}
		//move left
		else if(product<0 && Math.abs(product)<8 && Math.abs(product)<maxLeft)
			valid = true;
		//move right 
		else if(product>0 && product<8 && product<maxRight)
			valid = true; 
		
		return valid; 
	}

}
