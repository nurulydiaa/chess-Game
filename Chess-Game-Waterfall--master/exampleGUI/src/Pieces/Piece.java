package Pieces;

public class Piece {
	private String imageName;
	private String name;
	private int aliance; 
	public boolean hasMoved;
	private String pieceType;
	
	public Piece() {
		this.aliance = -1;
		this.name = null;
		this.imageName = null;
	}

	public String getName() {
		return name;
	}
	public void setName(String nm) {
		this.name = nm;
	}
	public void setImageName(String iN) {
		this.imageName = iN;
	}
	public void setAlly(int ally) {
		this.aliance = ally;
	}
	public int getAlly() {
		return aliance;
	}
	public String getImageName() {
		return imageName;
	}
	

	public boolean validMove(int selected, int tileID) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getPieceType() {
		return pieceType;
	}

	public void setPieceType(String pieceType) {
		this.pieceType = pieceType;
	}
    
}
