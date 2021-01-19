package gui;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

import chessGame.chessGame;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Board {
	
	private Color whiteTiles = Color.decode("#edebd3");
	private Color blackTiles = Color.decode("#545239");
	private Color blueTiles = Color.decode("#09F6BB");
	
	public static int selected = -1; //when no piece is selected equals -1
	chessGame newGame = new chessGame();
	
	private final JFrame gameFrame;
	private final BoardPanel boardPanel;
	private final GravePanel grave; 
	private final SurrenderPanel surrender;
	public int graveCount = 0;
	public boolean[] highlighted = new boolean[64];
	public static int turnTime = 0;

	
	private final static Dimension BOARD_PANEL = new Dimension(80, 80);
	private final static Dimension TILE_PANEL = new Dimension(20, 20);
	
	public Board() {
		this.gameFrame = new JFrame("Chess");
		this.gameFrame.setLayout(new BorderLayout());
		this.gameFrame.setSize(1000, 1000);
		this.grave = new GravePanel();
		this.surrender = new SurrenderPanel();
		this.surrender.setSize(400,400);
		this.boardPanel = new BoardPanel(grave);
		this.grave.setLayout(new BoxLayout(grave, BoxLayout.Y_AXIS));
		this.surrender.setLayout(new BoxLayout(surrender, BoxLayout.Y_AXIS));
		this.gameFrame.add(this.grave, BorderLayout.WEST);
		this.gameFrame.add(this.surrender, BorderLayout.SOUTH);
		this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
		this.gameFrame.setVisible(true);
		this.gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

private class SurrenderPanel extends JPanel{
	SurrenderPanel(){
		//surrender button
		JButton btnSurr = new JButton("Surrender");
		btnSurr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame.gameOver();
			}
		});
		this.add(btnSurr);
		//restart button
		JButton btnRest = new JButton("Restart");
		btnRest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graveCount = 0;
				newGame = new chessGame();
				boardPanel.drawBoard(newGame);
				Board.turnTime = 0;
			}
		});
		this.add(btnRest);
		//timer label
		final JLabel time = new JLabel("Current turn time in seconds: "+Board.turnTime);
		this.add(time);
		Timer timer = new Timer();
       	TimerTask myTask = new TimerTask() {
       		@Override
       		public void run() {
       		   Board.turnTime++;
       		   time.setText("Current turn time in seconds: "+Board.turnTime);
       	   }
       		
       	};
       	timer.schedule(myTask, 1000, 1000);
	}
}
	
private class GravePanel extends JPanel{
	
	GravePanel(){
	  JLabel tittle = new JLabel("Grave Yard Tile");
	  this.add(tittle);
	}
	void graveYardAdd(String piece){
		System.out.println(piece);
	  String description = (piece);
      JLabel add = new JLabel(description);
      this.add(add);
      revalidate();
	}
}
	
	
	
private class BoardPanel extends JPanel{
	final List<TilePanel> boardTiles;
	
	BoardPanel(final GravePanel grave){
		super(new GridLayout(8,8));
		this.boardTiles = new ArrayList<>();
		for(int i = 0; i < 64; i++) {
			highlighted[i] = false;
			final TilePanel tilePanel = new TilePanel(this, grave, i);
			this.boardTiles.add(tilePanel);
			add(tilePanel);
		}
		this.setSize(BOARD_PANEL);
		validate();
	}
	void drawBoard(final chessGame newGame) {
		for (final TilePanel tilePanel: boardTiles) {
			tilePanel.drawTile(newGame);
			add(tilePanel);
		}
		validate();
		repaint();
	}
}

private class TilePanel extends JPanel{
	
	private final int tileID;
	
	TilePanel(final BoardPanel boardPanel, final GravePanel grave,final int tileID){
	  super(new GridBagLayout());
	  this.tileID = tileID; 
	  this.setPreferredSize(TILE_PANEL);
	  assignTileColor();
	  assignTileSprite();
	  
	  addMouseListener(new MouseAdapter() {
		  @Override
		  public void mousePressed(MouseEvent e) {
			if(newGame.turn == 1) {
				 aiCheckForMoves();
				 newGame.changeTurn();
				 newGame.turnUp();
			 }
			else if (Board.selected==-1) {
				 //piece select
				 if(newGame.p[tileID].getName() != null && 
						 newGame.p[tileID].getAlly() == newGame.turn) {
				     Board.selected = tileID;
				     highlightTile(Board.selected);
				 }
				 else 
					 Board.selected = -1;
			 }
			
			 else if(Board.selected == tileID || availableMove(Board.selected, tileID) == false ) {
				 //deselect when moved to same spot or invalid move;
				 Board.selected = -1;
			 }
			 //Cant move to own piece excluding castling;
			 else if(newGame.p[tileID].getAlly() == newGame.p[Board.selected].getAlly()) {
				 System.out.println("cant take own piece");
				 Board.selected = -1;
			 }
			 //in check move
			 else if ((Board.selected!=-1 && newGame.p[tileID].getName()!=null 
					 && newGame.p[Board.selected].validMove(Board.selected, tileID) == true)) {
				 //take piece 

				for(int i = 0; i < 64; i++) {
					  newGame.checkHolder[i] = newGame.p[i];
					}
				  newGame.holder = newGame.p[Board.selected];
				  newGame.p[Board.selected] = newGame.grave[graveCount];
				  newGame.p[tileID] = newGame.holder;
				  //piece is moving into check
				  if(checkForCheck()==newGame.turn) {
						newGame.p[Board.selected] = newGame.checkHolder[Board.selected];
						newGame.p[tileID] = newGame.checkHolder[tileID];
						Board.selected = -1;
					}
				 //piece is not moving into check
				 else {
					     
				        grave.graveYardAdd(newGame.checkHolder[tileID].getName());
				        newGame.holder = newGame.grave[graveCount];
				        newGame.grave[graveCount] = newGame.p[tileID];
				        newGame.p[Board.selected] = newGame.grave[graveCount];
				        newGame.p[tileID] = newGame.p[Board.selected];
				        newGame.p[Board.selected] = newGame.holder;
				        newGame.changeTurn();
				        newGame.turnUp();
				 	    Board.selected = -1;
				 	    graveCount++;
				 	    newGame.p[tileID].hasMoved = true;
				 	    newGame.inCheck = checkForCheck();
				 	    Board.turnTime = 0;
				 }
			 }
			 
			 else{
				 if (newGame.p[Board.selected].validMove(Board.selected, tileID) == false) {
					 //invalid move deselect piece
					 Board.selected = -1;
				 }
				 
				 //move to empty
				 else {
				   for(int i = 0; i < 64; i++) {
					   newGame.checkHolder[i] = newGame.p[i];
					}
					newGame.holder = newGame.p[Board.selected];
					newGame.p[Board.selected] = newGame.p[tileID];
					newGame.p[tileID] = newGame.holder;
					//is moving into check
					if(checkForCheck()==newGame.turn) {
						newGame.p[Board.selected] = newGame.checkHolder[Board.selected];
						newGame.p[tileID] = newGame.checkHolder[tileID];
						Board.selected = -1;
					}
					//is not moving into check
					else {
					    Board.selected = -1;
						newGame.inCheck = -1;
						newGame.changeTurn();
						newGame.p[tileID].hasMoved = true;
						Board.turnTime = 0;
					}
				 }
			 }
			 SwingUtilities.invokeLater(new Runnable() {
				 @Override
				 public void run() {
					 checkForMoves();
					 boardPanel.drawBoard(newGame);
				 }
			 });
			  
		  }

	  });
	  validate();
	  	
    }
	void drawTile(final chessGame newGame) {
		removeAll();
		assignTileColor();
		assignTileSprite();
		validate();
		repaint();
		
	}
	
	private void assignTileSprite() {
		JLabel sprite = new JLabel();
		String img = ("/img/"+newGame.p[this.tileID].getImageName()+".png");
		if(newGame.p[this.tileID].getImageName()!=null)
		   sprite.setIcon(new ImageIcon(getClass().getResource(img)));
		add(sprite);
		validate();
	}
		
	private void assignTileColor() {
		if(highlighted[this.tileID] == true) {
			setBackground(blueTiles);
			highlighted[this.tileID] = false;
		}
		else if((this.tileID/8)% 2==0 )
		    setBackground(this.tileID %2 == 0 ? whiteTiles : blackTiles);
		else 
			setBackground(this.tileID %2 == 0 ? blackTiles : whiteTiles);
	
	}
	private void highlightTile(int selected) {
		
		for(int i = 0; i<64; i++) {
			if(newGame.p[selected].validMove(selected, i) == true && (newGame.p[i].getAlly()==-1 
					|| newGame.p[selected].getAlly() != newGame.p[i].getAlly()) && availableMove(selected, i) == true) {
			   highlighted[i] = true;
			}
	    }
	}
	
	//checks if there is a piece in the way of current move
	private boolean availableMove(int start, int end) {
		int product = end - start;	
		
		if(newGame.p[start].getImageName() == "WPawn"){
			if((product == -7|| product == -9) && newGame.p[end].getAlly()!=1)
				return false;
			else if(product == -8 && newGame.p[end].getAlly()==1)
				return false;
			else if(product == -16 && newGame.p[end].getAlly()==1)
				return false;
			else 
				return true;
		}
		else if(newGame.p[start].getImageName() == "BPawn"){
			if((product == 7|| product == 9) && newGame.p[end].getAlly()!=0)
				return false;
			else if(product == 8 && newGame.p[end].getAlly()==0)
				return false;
			else if(product == 16 && newGame.p[end].getAlly()==0)
				return false;
			else 
				return true;
		}

		else if((newGame.p[start].getPieceType() == "King"|| newGame.p[start].getPieceType() == "Knight")) {
			return true;
		}
		//horizontal
		else if(end/8 == start/8) {
			int firstinRow = (start/8)*8;
			int left = firstinRow;
			int right = firstinRow+7;
			for(int i = 0; i<8; i++) {
				if(newGame.p[i+firstinRow].getAlly() != -1 && (i+firstinRow)>left && (i+firstinRow)<start)
					left = (i+firstinRow);
				if(newGame.p[i+firstinRow].getAlly() != -1 && (i+firstinRow)<right && (i+firstinRow)>start)
					right = (i+firstinRow);
			}
			if( end >right || end <left)
				return false;
		}
		//vertical
		else if(product%8 == 0) {
			int firstinColumn = start%8;
			int up = firstinColumn;
			int down = firstinColumn+56;
			for (int i = firstinColumn; i<=(firstinColumn+56); i=i+8) {
				if(newGame.p[i].getAlly() != -1 && (i) > up && i < start)
					up = i;
				if(newGame.p[i].getAlly() != -1 && (i) < down && i > start)
					down = i;
				if(end>down || end<up)
					return false;
			}
		}
		//diagonal
		else {
			int downRight = 63;
			int downLeft = 63;
			int upRight = 0;
			int upLeft = 0;
			int diagLStart = start;
			int diagRStart = start;
			
			while(diagLStart%8 != 7 && diagLStart>0) {
				diagLStart = diagLStart-7;
			}
			diagLStart = diagLStart + 7;
			
			while(diagRStart%8 != 0 && diagRStart>0) {
				diagRStart = diagRStart-9;
			}
			diagRStart = diagRStart+9;
			for(int i = diagRStart; i<63; i=i+9) {
				if(newGame.p[i].getAlly() != -1 && i>upLeft && i < start)
					upLeft = i;
				if(newGame.p[i].getAlly() != -1 && i<downRight && i > start)
					downRight = i;
			}
			for(int i = diagLStart; i<63; i=i+7) {
				if(newGame.p[i].getAlly() != -1 && i > upRight && i < start)
					upRight = i;
				if(newGame.p[i].getAlly() != -1 && i < downLeft && i > start)
					downLeft = i;
			}
			if(product%7 == 0 &&(end > downLeft || end < upRight))
				return false;
			else if(product%9 == 0 &&(end > downRight || end < upLeft))
				return false;

		}
		return true;
	}
	
	//returns the team that is in check...-1 being null team
	private int checkForCheck() {
		int foundB = -1; 
		int foundW = -1;
		int i = 0;
		int k = 0;
		while(foundB==-1) {
			if( newGame.p[i].getImageName()=="BKing") {
				foundB = i;
			}
			else 
				i++;
		}
		while(foundW==-1) {
			if( newGame.p[k].getImageName() == "WKing") {
				foundW = k;
			}
			else 
				k++;
		}
        for(int j = 0; j<64; j++) {
        	if(availableMove(j, foundB) && newGame.p[j].validMove(j, foundB) && j!=foundB 
        			&& newGame.p[j].getAlly() !=1) {
        		newGame.inCheck = 1;
        		return 1;
        	}
        	if(availableMove(j, foundW) && newGame.p[j].validMove(j, foundW) && j!=foundW 
        			&& newGame.p[j].getAlly() !=0) {
        		newGame.inCheck = 0;
        		return 0;
           }
        }              
		
    	newGame.inCheck = -1;
    	return -1;		
	}
	
	private void checkForMoves() {
		int availableMoves = 0;
		for(int p = 0; p<64; p++) {
			newGame.checkHolder[p] = newGame.p[p];
		}
		for(int p = 0; p<64; p++) {
			
			for(int j = 0; j<64; j++) {
							    
				if(availableMove(p, j) == true && newGame.p[p].validMove(p, j) == true 
						&& newGame.p[p].getAlly() == newGame.turn 
						&& newGame.p[p].getAlly()!=newGame.p[j].getAlly()) {
					if(newGame.p[p].getPieceType() == "King" 
							&& newGame.p[p].getAlly()!=newGame.p[j].getAlly() &&newGame.p[j].getAlly()!=-1) {
						newGame.p[j] = newGame.p[p]; 
						newGame.p[p] = newGame.grave[graveCount];
						
					}
					else {
						newGame.holder = newGame.p[j];
						newGame.p[j] = newGame.p[p];
						newGame.p[p]= newGame.holder;
					}
				   
				   if(checkForCheck()==-1) {
				    availableMoves++;	
				   }
				   newGame.p[j]= newGame.checkHolder[j];				
				   newGame.p[p]= newGame.checkHolder[p];
				  
			    }
				
			}
			
		}

		if(availableMoves == 0 ) {
			boardPanel.drawBoard(newGame);
			newGame.gameOver();
		}
	}
	private boolean aiCheckForMoves() {

		for(int p = 0; p<64; p++) {
			
			for(int j = 0; j<64; j++) {				

				if(availableMove(p, j) == true && newGame.p[p].validMove(p, j) == true 
						&& newGame.p[p].getAlly() == newGame.turn && newGame.p[p].getAlly()!=newGame.p[j].getAlly()
						&& newGame.checkHolder[j].getAlly()!=0) {
					newGame.holder = newGame.p[j];
				    newGame.p[j] = newGame.p[p];
				    newGame.p[p]= newGame.holder;
				    				
				    if(checkForCheck()==-1) {
					   Board.turnTime = 0;
					   newGame.p[j].hasMoved = true;
				       return true;
				    
				   }
				  
			    }
				
				else if(availableMove(p, j) == true && newGame.p[p].validMove(p, j) == true 
						&& newGame.p[p].getAlly() == newGame.turn && newGame.p[p].getAlly()!=newGame.p[j].getAlly()
						&& newGame.checkHolder[j].getAlly() == 0) {
					newGame.p[j] = newGame.p[p];
				    newGame.p[p]= newGame.grave[graveCount];
				    
					if(checkForCheck()==-1) {
						grave.graveYardAdd(newGame.checkHolder[j].getName());
						graveCount++;
						newGame.p[j].hasMoved = true;
						Board.turnTime = 0;
						return true;
						   
				   }
				}
				
				newGame.p[j]= newGame.checkHolder[j];				
				newGame.p[p]= newGame.checkHolder[p];

			}
		}
       return false;
		
	}
  }	
}
