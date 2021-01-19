package Pieces;

public class Pawn extends Piece{
	private boolean valid = false;
	
	public Pawn(int ally) {
		setPieceType("Pawn");
		hasMoved = false;
		if(ally == 0) {
			setAlly(0);
			setName("White Pawn");
			setImageName("WPawn");
		}
		else {
			setAlly(1);
			setName("Black Pawn");
			setImageName("BPawn");
		}
	}
	
	public boolean validMove(int start, int end) {
		
		valid = false;
		int product = end-start;
		if(this.getAlly()==0) {
			if(product == -8) {
				valid = true;
			}
		//double jump	
		   else if(product == -16 && hasMoved == false) {
				valid = true;
			}
	    //taking
		   else if((product == -7 && start%8==7) || (product == -9 && start%8 == 0))
				valid = false;	
		   else if(product == -7 || product == -9) {
			    valid = true;
		}
		}
		else if(this.getAlly()==1) {
			if(product == 8) {
				valid = true;
			}
			else if(product == 16 && hasMoved == false) {
				valid = true;
			}
			//taking
			else if((product == 7 && start%8==0) || (product == 9 && start%8 == 7))
				valid = false;
			else if(product == 7 || product == 9) {
				valid = true;
			}
		}

		return valid;
    }


}
