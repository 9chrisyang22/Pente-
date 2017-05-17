package Pente;

import javax.swing.JFrame;

public class PenteMain {
	public static final int EMPTY = 0;
	public static final int BLACKSTONE = 1;
	public static final int WHITESTONE = -1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int boardWidth = 720;
		int boardWidthInSquares = 19;
		
		JFrame f = new JFrame("Play Pente");
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.setSize(boardWidth, boardWidth);
		PenteGameBoard p = new PenteGameBoard(boardWidth - 20, boardWidthInSquares);
		
		f.add(p);
		f.setVisible(true);
	}

}
