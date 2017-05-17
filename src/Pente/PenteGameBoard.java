package Pente;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PenteGameBoard extends JPanel implements MouseListener{

	private int bWidthPixels;
	private int bWidthSquares;
	private int bSquareWidth;
	private Square [][] theBoard;
	//Color boardSquareColor = new Color(150, 111, 51);

	private Square testSquare;
	private int currentTurn = PenteMain.BLACKSTONE;

	//This is for counting captures;
	private int whiteStoneCaptures = 0;
	private int blackStoneCaptures = 0;

	Ralph computerMoveGenerator = null;
	boolean playingAgainstRalph = false;
	int ralphsStoneColor;



	public PenteGameBoard(int bWPixel,int bWSquares) {
		bWidthPixels = bWPixel - 10;
		bWidthSquares = bWSquares;
		bSquareWidth = (int)(Math.ceil(bWidthPixels/bWidthSquares))+2;

		this.setSize(bWidthPixels, bWidthPixels);
		this.setBackground(Color.CYAN);

		//testSquare = new Square (0, 0, bSquareWidth);
		theBoard = new Square[bWSquares][bWSquares];


		for(int row = 0; row < bWidthSquares; ++row){
			for(int col = 0; col < bWidthSquares; ++col){
				theBoard[row][col] = new Square((col * bSquareWidth), (row * bSquareWidth), bSquareWidth, row, col);
			}
		}
		String computerAnswer = JOptionPane.showInputDialog("Hi, would you like to play against the compputer? (y or n)");
		if(computerAnswer.equals("y")){
			computerMoveGenerator = new Ralph(this, currentTurn);
			playingAgainstRalph = true;
			ralphsStoneColor = currentTurn;
			System.out.println(ralphsStoneColor);
		}


		theBoard[(int)(bWidthSquares/2)][(int)(bWidthSquares/2)].setState(PenteMain.BLACKSTONE);
		this.changeTurn();
		this.addMouseListener(this);
	}

	public void paintComponent(Graphics g){
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, bWidthPixels, bWidthPixels);

		//testSquare.drawMe(g);
		for(int row = 0; row < bWidthSquares; ++row){
			for(int col = 0; col < bWidthSquares; ++col){
				theBoard[row][col].drawMe(g);
			}
		}
	}

	public void changeTurn(){
		if(currentTurn == PenteMain.WHITESTONE){
			currentTurn = PenteMain.BLACKSTONE;
		}
		else{
			currentTurn = PenteMain.WHITESTONE;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		playGame(e);
		repaint();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void playGame(MouseEvent e){
		Square s = findSquare(e.getX(), e.getY());

		if(s.getState() == PenteMain.EMPTY){
			s.setState(currentTurn);
			this.repaint();
			this.checkForCaptures(s);

			this.checkForWins2(s);
			this.changeTurn();
			if(playingAgainstRalph == true){
				Square cs = computerMoveGenerator.doComputerMove(s.getRow(), s.getCol());
				cs.setState(currentTurn);
				this.repaint();
				this.checkForCaptures(cs);
				this.checkForWinOnCaptures();
				this.checkForWins2(cs);
				this.changeTurn();
				this.requestFocus();


				repaint();
			}
			else{
				JOptionPane.showMessageDialog(null, "You can't click on a space with a stone.");
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "You didn't click on a square");
		}

	}

	public void checkForWins2(Square s) {
		boolean done = false;
		int[] myDys = {-1, 0, 1};
		int whichDy = 0;

		while(!done && whichDy < 3){
			if(checkForWinAllInOne(s, myDys[whichDy], 1) == true){
				weHaveAWinner();
				done = true;
			}
			whichDy++;
		}
		if(!done){
			if(checkForWinAllInOne(s, 1, 0) == true){
				weHaveAWinner();

			}
		}

	}

	public boolean checkForWinAllInOne(Square s, int dy, int dx) {

		boolean isThereAWin = false;
		int sRow = s.getRow();
		int sCol = s.getCol();
		System.out.println("In checkForCaptures sRow and sCol is ["
				+ sRow + ", " + sCol + "]");
		int howManyRight = 0;
		int howManyLeft = 0;
		int step = 1;

		while ((sCol + (step * dx) < bWidthSquares) && (sRow + (step * dy) < bWidthSquares)
				&& (sCol + (step * dx) >= 0) && (sRow + (step * dy) >=0) &&
				(theBoard[sRow + (step * dy)] [sCol + (step * dx)].getState() == currentTurn)){
			howManyRight++;
			step++;
		}
		/*

		while ((sCol + step < bWidthSquares) 
				&& (theBoard[sRow + (step * dy)] [sCol + (step * dx)].getState() == currentTurn)){
			howManyRight++;
			step++;
		}
		 */

		step = 1; 

		while ((sCol - (step * dx) < bWidthSquares) && (sRow - (step * dy) < bWidthSquares)
				&& (sCol - (step * dx) >= 0) && (sRow - (step * dy) >=0) &&
				(theBoard[sRow - (step * dy)] [sCol - (step * dx)].getState() == currentTurn)){
			howManyRight++;
			step++;
		}

		/*
		while((sCol - step >= 0) &&
				(theBoard[sRow - (step * dy)] [sCol - (step * dx)].getState() == currentTurn)){
			howManyLeft ++;
			step ++;
		}
		 */


		if((howManyRight + howManyLeft + 1) >= 5){
			isThereAWin = true;
		}
		return isThereAWin;
	}

	public Square findSquare(int mouseX, int mouseY) {
		Square clickedOnSquare = null;
		for(int row = 0; row < bWidthSquares; ++row){
			for(int col = 0; col < bWidthSquares; ++col){
				if(theBoard[row][col].youClickedMe(mouseX, mouseY) == true){
					clickedOnSquare = theBoard[row][col];
				}

			}
		}
		return clickedOnSquare;
	}


	public void checkForCaptures(Square s){
		int sRow = s.getRow();

		int sCol = s.getCol();

		//System.out.println("In checkForCaptures sRow and sCol is [" + sRow + ", " + sCol + "]");

		int theOpposite = this.getTheOppositeState(s);

		//for a right-horizontal-check

		for( int dy = -1 ; dy <= 1; ++ dy){

			if((dy > 0 && sRow < bWidthSquares - 3) || (dy < 0 && sRow >= 3) || dy == 0){

				// *** ****** THIS DOES LEFT AND RIGHT

				for( int dx = -1 ; dx <= 1 ; ++ dx) {

					if((dx > 0 && sCol < bWidthSquares - 3) || (dx < 0 && sCol >= 3) || dx == 0){

						// Make sure the Row is first!!!

						if(theBoard[sRow + (1 * dy) ][sCol+ (1 * dx) ].getState() == theOpposite){

							//You are going to have to code from here.

							// You ARE AWESOME AND YOU CAN WRITE THIS CODE!!!

							if(theBoard[sRow + (2 * dy)][sCol+ (2 * dx) ].getState() == theOpposite){

								if(theBoard[sRow + (3 * dy)][sCol+ (3 * dx) ].getState() == currentTurn){

									//System.out.println("We have a Horizontal Capture checking right");

									this.takeStones(sRow + (1 * dy), sCol+ (1 * dx), sRow + (2 * dy), 

											sCol+ (2 * dx), currentTurn);

									//repaint();

								}

							}

						}

					}


				} // end of dx loop

			}  //End of sRow edge check


		} //end of dy loop



	}

	public int getTheOppositeState(Square s){
		if(s.getState() == PenteMain.BLACKSTONE){
			return PenteMain.WHITESTONE;
		}
		else{
			return PenteMain.BLACKSTONE;
		}
	}

	public void takeStones(int r1, int c1, int r2, int c2, int taker){
		theBoard[r1][c1].setState(PenteMain.EMPTY);
		theBoard[r2][c2].setState(PenteMain.EMPTY);

		if(taker == PenteMain.BLACKSTONE){
			++blackStoneCaptures;
		}
		else{
			++whiteStoneCaptures;
		}

		this.checkForWinOnCaptures();
	}

	public void checkForWinOnCaptures() {
		if(blackStoneCaptures >= 5){
			JOptionPane.showMessageDialog(null,"Black wins!!! with 5 captures");
		}
		if(whiteStoneCaptures >= 5){
			JOptionPane.showMessageDialog(null,"White wins!!! with 5 captures");
		}

	}

	public void checkForWin(Square s){
		int sRow = s.getRow();
		int sCol = s.getCol();
		int theOpposite = this.getTheOppositeState(s);

		for(int dy = -1; dy <= 1; ++dy){
			if(((dy > 0 && sRow < bWidthSquares -4) || (dy<0 && sRow >= 4))){
				if(theBoard[sRow + (1 * dy)][sCol].getState() == currentTurn && theBoard[sRow + (2 * dy)][sCol].getState() == currentTurn && theBoard[sRow + (3*dy)][sCol].getState() == currentTurn && theBoard[sRow + (4*dy)][sCol].getState() == currentTurn ){
					this.weHaveAWinner();
					repaint();
				}
			}
		}
		for(int dy = -1; dy <= 1; ++dy){
			if(((dy > 0 && sCol < bWidthSquares -4) || (dy<0 && sCol >= 4))){
				if(theBoard[sRow][sCol + (1 * dy)].getState() == currentTurn && theBoard[sRow][sCol + (2*dy)].getState() == currentTurn && theBoard[sRow ][sCol + (3*dy)].getState() == currentTurn && theBoard[sRow][sCol + (4 *dy)].getState() == currentTurn ){
					//theBoard[sRow][sCol + 1].setState(PenteMain.EMPTY);
					//theBoard[sRow][sCol + 2].setState(PenteMain.EMPTY);
					this.weHaveAWinner();
					repaint();
				}
			}
		}
		for(int dy = -1; dy <= 1; ++dy){
			if(((dy > 0 && sRow < bWidthSquares -4) || (dy<0 && sRow >= 4))){
				if(theBoard[sRow + (1 * dy)][sCol + (1 * dy)].getState() == currentTurn && theBoard[sRow + (2 * dy)][sCol + (2*dy)].getState() == currentTurn && theBoard[sRow + (3 * dy)][sCol + (3*dy)].getState() == currentTurn && theBoard[sRow + (4 * dy)][sCol + (4 *dy)].getState() == currentTurn ){

					this.weHaveAWinner();
					repaint();
				}
			}
		}


		for(int dy = -1; dy <= 1; ++dy){
			if(((dy > 0 && sRow < bWidthSquares -4) || (dy<0 && sRow >= 4))){
				if(theBoard[sRow + (-1 * dy)][sCol + (1 * dy)].getState() == currentTurn && theBoard[sRow + (-2 * dy)][sCol + (2*dy)].getState() == currentTurn && theBoard[sRow + (-3 * dy)][sCol + (3*dy)].getState() == currentTurn && theBoard[sRow + (-4 * dy)][sCol + (4 *dy)].getState() == currentTurn ){
					this.weHaveAWinner();
					repaint();
				}
			}
		}

	}


	public void weHaveAWinner() {


		if(currentTurn == PenteMain.BLACKSTONE){
			JOptionPane.showMessageDialog(null, "Congratulations! BlackStone player! You Won!!! Great Job!");
		}
		else{
			JOptionPane.showMessageDialog(null, "Congratulations! WhiteStone Player! You Won!!! Great Job!");
		}

	}

	public int getBoardWidthInSquares(){
		return bWidthSquares;
	}



	public Square[][] getActualGameBoard() {
		// TODO Auto-generated method stub
		return  theBoard;
	}
}
