package Pente;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Ralph {

	PenteGameBoard myBoard;
	int myStoneColor, opponentStoneColor;
	int boardWidthSquares;
	Square[][] theGameBoard;

	boolean timeToMakeAMove = false;
	boolean moveToMake = false;
	int moveToDealWithRow;
	int moveToDealWithCol;

	ArrayList<OpponentGroup> groups4 = new ArrayList<OpponentGroup>();
	ArrayList<OpponentGroup> groups3 = new ArrayList<OpponentGroup>();
	ArrayList<OpponentGroup> groups2 = new ArrayList<OpponentGroup>();
	ArrayList<OpponentGroup> groups1 = new ArrayList<OpponentGroup>();

	RalphHelper vanellope;


	public Ralph(PenteGameBoard b, int stoneColor) {
		// TODO Auto-generated constructor stub
		myBoard = b;
		myStoneColor = stoneColor;
		this.setOpponentStoneColor();
		boardWidthSquares = b.getBoardWidthInSquares();
		theGameBoard = b.getActualGameBoard();
		//JOptionPane.showMessageDialog(null, "Hi, Ralph here. Ready to play!");
		vanellope = new RalphHelper(myBoard, opponentStoneColor);
	}

	public void setOpponentStoneColor(){
		if(myStoneColor == PenteMain.BLACKSTONE){
			opponentStoneColor = PenteMain.WHITESTONE;
		}
		else{
			opponentStoneColor = PenteMain.BLACKSTONE;
		}
	}

	public Square doComputerMove(int lastMoveRow, int lastMoveCol){


		this.accessBoard(lastMoveRow, lastMoveCol);

		vanellope.accessBoard(lastMoveRow, lastMoveCol);

		Square nextMove = null;
		nextMove = vanellope.blockItEverybody(vanellope.getVanellopeGroups4(), 4);
		if(nextMove == null){
			nextMove = this.blockItEverybody(groups4, 4);
			if(nextMove == null){
				nextMove = vanellope.blockItEverybody(vanellope.getVanellopeGroups3(), 3);
				if(nextMove == null){
					nextMove = this.blockItEverybody(groups3, 3);
					if(nextMove == null){
						nextMove = vanellope.blockItEverybody(vanellope.getVanellopeGroups2(), 2);

						if(nextMove == null){
							nextMove = captureATwo();
							if(nextMove ==  null){
								nextMove = this.blockItEverybody(groups2, 2);
								if(nextMove == null){
									nextMove = this.blockItEverybody(vanellope.getVanellopeGroups1(), 1);

									if(nextMove == null){
										nextMove = makeARandomMove();
									}
								}
							}
						}
					}
				}
			}
		}
		return nextMove;



	}

	public Square captureATwo(){
		Square nextMove = null;
		if(groups2.size()>0){
			boolean done = false;
			int groupIndex = 0;
			while(!done && groupIndex <groups2.size()){
				OpponentGroup currentgroup = groups2.get(groupIndex);
				Square e1 = groups2.get(groupIndex).getEnd1Square();
				Square e2 = groups2.get(groupIndex).getEnd2Square();
				groupIndex++;

			}
		}
		return nextMove;
	}



	public Square blockItEverybody(ArrayList<OpponentGroup> whatGroup, int whatGroupSize){

		Square nextMove = null;
		if(whatGroup.size() > 0){
			boolean done = false;
			int groupIndex = 0;

			while(!done && groupIndex < whatGroup.size()){
				OpponentGroup currentGroup = whatGroup.get(groupIndex);
				Square e1 = whatGroup.get(groupIndex).getEnd1Square();
				Square e2 = whatGroup.get(groupIndex).getEnd2Square();



				groupIndex++;



				if(currentGroup.getInMiddleGroupStatus() == true){
					System.out.println("Hello I am chris");
					nextMove = currentGroup.getInMiddleGroupSquare();
				}


				else{

					if(e1 != null && e1.getState() == PenteMain.EMPTY && e2 != null && e2.getState() == PenteMain.EMPTY){
						int r = (int) (Math.random() * 100);

						if(r < 50){
							nextMove = e1;


						}
						else{
							nextMove = e2;
						}
						done = true;
					}
					else{

						if(whatGroupSize == 4){



							if(e1 != null && e1.getState() == PenteMain.EMPTY){
								nextMove = e1;
								done = true;
							}
							else{
								if(e2 != null && e2.getState() == PenteMain.EMPTY){
									nextMove = e2;
									done = true;
								}
							}

						}

					}
				}
			}
		}


		return nextMove;
	}

	public Square specialProcessingForThree(OpponentGroup g){
		Square squareToReturn = null;
		return squareToReturn;
	}

	public Square makeARandomMove() {

		int newMoveRow, newMoveCol;

		do {

			newMoveRow = (int)(Math.random() * boardWidthSquares);
			newMoveCol = (int)(Math.random() * boardWidthSquares);
		}
		while(theGameBoard[newMoveRow][newMoveCol].getState()!= PenteMain.EMPTY);
		System.out.println();
		return theGameBoard[newMoveRow][newMoveCol];
	}






	public void accessBoard(int lastMoveRow, int lastMoveCol){
		groups4.clear();
		groups3.clear();
		groups2.clear();
		groups1.clear();
		//System.out.println("in access board");
		this.lookForGroupsHorizontally(lastMoveRow, lastMoveCol);
		this.lookForGroupsVertically(lastMoveRow, lastMoveCol);
		this.lookForGroupsDiagRightInClass(lastMoveRow, lastMoveCol);
		this.lookForGroupsDiagLeftFromClass(lastMoveRow, lastMoveCol);
		this.doInMiddleCheck(4);
		this.doInMiddleCheck(3);
		//this.doInMiddleCheck(boardWidthSquares);

		//System.out.println("in access board");

		//System.out.println("in access board");
	}



	//Java Code for lookForGroupsDiagRightInClass

	public void lookForGroupsDiagRightInClass( int lastMoveRow, int lastMoveCol ){
		//Do Part 1 of the Diagonal...
		for(int row = 0 ; row < boardWidthSquares; ++row ){
			int curCol = 0;
			int curRow = row;
			while(curCol < boardWidthSquares - row && curRow < boardWidthSquares) {

				Square groupStart = findOpponentDiagRight1( curRow,  curCol, 0);

				if( groupStart != null ) {	
					//You have a start so set up a new group!
					//System.out.println ("Hi I found a group start at " + groupStart.getRow() + ", " + groupStart.getCol() );
					OpponentGroup newGroup = new OpponentGroup(OpponentGroup.DIAG_RIGHT_GROUP);
					newGroup.addSquareToGroup(groupStart);
					int startRow = groupStart.getRow();
					int startCol = groupStart.getCol();

					// Check first edge
					if(startRow - 1 >= 0 && startCol - 1 >= 0) {
						newGroup.setEnd1Square(theGameBoard[startRow-1][startCol-1]);
					} else {
						newGroup.setEnd1Square(null);

					}
					//see if current move is part of this group
					if( startRow == lastMoveRow && startCol == lastMoveCol ){
						newGroup.setCurrentMoveIsInThisGroup(true);
						newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
					}

					//look for additional group members
					startCol++;
					startRow++;
					boolean done = false;

					while( startCol < boardWidthSquares - row && startRow < boardWidthSquares && !done){
						if(theGameBoard[startRow][startCol].getState() == this.opponentStoneColor ) {
							newGroup.addSquareToGroup(theGameBoard[startRow][startCol]);		
							if( startRow == lastMoveRow && startCol == lastMoveCol ){
								newGroup.setCurrentMoveIsInThisGroup(true);
								newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
							}

							startRow++;
							startCol++;
						} else {
							done = true;
						}	
					}	
					//check other edge
					if(startRow < boardWidthSquares && startCol < boardWidthSquares) {
						newGroup.setEnd2Square(theGameBoard[startRow][startCol]);
					} else {
						newGroup.setEnd2Square(null);

					}

					//Important to stop infinite loop
					curCol = startCol;
					curRow = startRow;
					//add group to list
					this.addNewGroupToGroupLists(newGroup);

				} else {
					//get out of loop!!
					curRow = boardWidthSquares;
				}	
			}

		}



		//Do Part 2 of the Diagonal
		for(int col = 1 ; col < boardWidthSquares; ++col ){

			int curCol = col;
			int curRow = 0;

			while(curRow < boardWidthSquares - col && curCol < boardWidthSquares) {

				Square groupStart = findOpponentDiagRight1( curRow,  curCol, 0);

				if(groupStart != null){

					//System.out.println ("Hi I found a group start at " + groupStart.getRow() + ", " + groupStart.getCol() );
					OpponentGroup newGroup = new OpponentGroup(OpponentGroup.DIAG_RIGHT_GROUP);
					newGroup.addSquareToGroup(groupStart);
					int startRow = groupStart.getRow();
					int startCol = groupStart.getCol();

					// Check first edge  same problem so same code from above should work...
					if(startRow - 1 >= 0 && startCol - 1 >= 0) {
						newGroup.setEnd1Square(theGameBoard[startRow-1][startCol-1]);
					} else {
						newGroup.setEnd1Square(null);

					}
					//see if current move is part of this group
					if( startRow == lastMoveRow && startCol == lastMoveCol ){
						newGroup.setCurrentMoveIsInThisGroup(true);
						newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
					}

					//look for additional group members
					startCol++;
					startRow++;
					boolean done = false;

					while( startCol < boardWidthSquares  && startRow < boardWidthSquares-col && !done){
						if(theGameBoard[startRow][startCol].getState() == this.opponentStoneColor ) {
							newGroup.addSquareToGroup(theGameBoard[startRow][startCol]);		
							if( startRow == lastMoveRow && startCol == lastMoveCol ){
								newGroup.setCurrentMoveIsInThisGroup(true);
								newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
							}

							startRow++;
							startCol++;
						} else {
							done = true;
						}		
					}

					//check other edge
					if(startRow  < boardWidthSquares && startCol  < boardWidthSquares) {
						newGroup.setEnd2Square(theGameBoard[startRow][startCol]);
					} else {
						newGroup.setEnd2Square(null);

					}

					//Important to stop infinite loop
					curCol = startCol;
					curRow = startRow;
					//add group to list
					this.addNewGroupToGroupLists(newGroup);


				} else {

					//get out of loop
					curCol = boardWidthSquares;

				}
			}
		}	
	}




	public Square findOpponentDiagRight1( int whatRow, int whatCol, int r ){

		Square opponentStart = null;
		boolean done = false; 
		int currentCol = whatCol;
		int currentRow = whatRow;

		while( !done && currentCol < boardWidthSquares-r && currentRow < boardWidthSquares){
			if(theGameBoard[currentRow][currentCol].getState() == opponentStoneColor ){
				opponentStart = theGameBoard[currentRow][currentCol];
				done = true;
			}
			currentRow++;
			currentCol++;	
		}
		return opponentStart;
	}

	public void lookForGroupsDiagLeftFromClass( int lastMoveRow, int lastMoveCol ){
		//Do Part 1 of the Diagonal...
		for(int row = 0 ; row < boardWidthSquares; ++row ){

			int curCol = boardWidthSquares-1; 
			int curRow = row;


			while(curCol >= row  && curRow < boardWidthSquares) { 

				Square groupStart = findOpponentStartDiagLeft( curRow,  curCol);

				if( groupStart != null ) {	
					//You have a start so set up a new group!
					//System.out.println ("Hi I found a group start at " + groupStart.getRow() + ", " + 
					//groupStart.getCol() );
					OpponentGroup newGroup = new OpponentGroup(OpponentGroup.DIAG_LEFT_GROUP);
					newGroup.addSquareToGroup(groupStart);
					int startRow = groupStart.getRow();
					int startCol = groupStart.getCol();

					// Check first edge
					if(startRow - 1 >= 0 && startCol + 1 < boardWidthSquares) {
						newGroup.setEnd1Square(theGameBoard[startRow-1][startCol+1]);
					} else {
						newGroup.setEnd1Square(null);

					}
					//see if current move is part of this group
					if( startRow == lastMoveRow && startCol == lastMoveCol ){
						newGroup.setCurrentMoveIsInThisGroup(true);
						newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
					}

					//look for additional group members
					startCol--;  
					startRow++;
					boolean done = false;

					while( startCol >= row && startRow < boardWidthSquares && !done){
						if(theGameBoard[startRow][startCol].getState() == this.opponentStoneColor ) {
							newGroup.addSquareToGroup(theGameBoard[startRow][startCol]);		
							if( startRow == lastMoveRow && startCol == lastMoveCol ){
								newGroup.setCurrentMoveIsInThisGroup(true);
								newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
							}

							startRow++;
							startCol--;
						} else {
							done = true;
						}	
					}	
					//check other edge
					if(startRow  < boardWidthSquares && startCol  >= 0) {
						newGroup.setEnd2Square(theGameBoard[startRow][startCol]);
					} else {
						newGroup.setEnd2Square(null);

					}

					//Important to stop infinite loop
					curCol = startCol;
					curRow = startRow;
					//add group to list
					this.addNewGroupToGroupLists(newGroup);

				} else {
					//get out of loop!!
					curRow = boardWidthSquares;
					curCol = row-1; 
				}	
			}	
		}

		//System.out.println("Start of second part of diagonal");
		//Do Part 2 of the Diagonal
		for(int col = boardWidthSquares-2 ; col >= 0; --col ){

			int curCol = col;
			int curRow = 0;

			//System.out.println("At start of searching loop cur row is " + curRow + " and curCol is " 
			//+ curCol);


			while(curRow <= col  && curCol >= 0) {	

				//System.out.println("Right before findOpponentStartDiagLeft curRow and col are "
				// + curRow + ",  " + curCol);
				Square groupStart = findOpponentStartDiagLeft( curRow,  curCol);

				if(groupStart != null){

					//System.out.println ("Hi I found a group start at " + groupStart.getRow() + ", "
					// + groupStart.getCol() );
					OpponentGroup newGroup = new OpponentGroup(OpponentGroup.DIAG_LEFT_GROUP);
					newGroup.addSquareToGroup(groupStart);
					int startRow = groupStart.getRow();
					int startCol = groupStart.getCol();

					// Check first edge  same problem so same code from above should work...
					if(startRow - 1 >= 0 && startCol + 1 < boardWidthSquares) {
						newGroup.setEnd1Square(theGameBoard[startRow-1][startCol+1]);
					} else {
						newGroup.setEnd1Square(null);

					}
					//see if current move is part of this group
					if( startRow == lastMoveRow && startCol == lastMoveCol ){
						newGroup.setCurrentMoveIsInThisGroup(true);
						newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
					}

					//look for additional group members
					startCol--;  // startCol[__];
					startRow++;
					boolean done = false;

					while( !done && startCol >=0  && startRow < boardWidthSquares ){	
						if(theGameBoard[startRow][startCol].getState() == this.opponentStoneColor ) {
							newGroup.addSquareToGroup(theGameBoard[startRow][startCol]);		
							if( startRow == lastMoveRow && startCol == lastMoveCol ){
								newGroup.setCurrentMoveIsInThisGroup(true);
								newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
							}

							startRow++;
							startCol--;
						} else {
							done = true;
						}		
					}

					//check other edge
					if(startRow  < boardWidthSquares && startCol  >= 0) {
						newGroup.setEnd2Square(theGameBoard[startRow ][startCol]);
					} else {
						newGroup.setEnd2Square(null);
					}

					//Important to stop infinite loop
					curCol = startCol;
					curRow = startRow;
					//add group to list
					this.addNewGroupToGroupLists(newGroup);


				} else {

					//get out of loop
					curCol = -1;
					//System.out.println();

				}
			}
		}		
	}


	public void lookForGroupsVertically(int lastMoveRow, int lastMoveCol){

		for(int col = 0; col < boardWidthSquares; ++col){
			int curRow = 0;
			// Step 1 skip over stones until you find an opponent stone
			while(curRow < boardWidthSquares){
				Square groupStart = findOpponentStartVertical(curRow, col);
				if(groupStart != null){
					//Now I can do stuff  
					// Make an object group

					OpponentGroup newGroup = new OpponentGroup(OpponentGroup.VERTICAL_GROUP);
					//Add stone to array
					newGroup.addSquareToGroup(groupStart);
					// Check Edge
					int startRow = groupStart.getRow();
					int startCol = groupStart.getCol();
					System.out.println("in access board");
					if(startRow <=0){
						newGroup.setEnd1Square(null);
					}
					else{
						newGroup.setEnd1Square(theGameBoard[startRow - 1][col]);
					}
					curRow = groupStart.getRow() + 1;
					boolean done = false;

					while(curRow < boardWidthSquares &&
							!done){
						if(theGameBoard[curRow][col].getState() == this.opponentStoneColor){
							newGroup.addSquareToGroup(theGameBoard[curRow][col]);
							curRow++;

						}
						else{
							done = true;
						}

					}


					if(curRow >= boardWidthSquares){
						newGroup.setEnd2Square(null);
					}
					else{
						newGroup.setEnd2Square(theGameBoard[curRow][col]);
					}

					this.addNewGroupToGroupLists(newGroup);

				}
				else{
					curRow = boardWidthSquares;
				}
			}


			// Step 2... when you find one one
			// YEA!! Make an opponent group 
			// Step 3 --- get All stones for the group (on horizontal line)
		}
	}

	public Square findOpponentStartDiagLeft( int whatRow, int whatCol ){

		//System.out.println();
		//System.out.println("At top of findOpponentStartDiagonalLEFT whatRow is " +
		//		whatRow + " and whatCol is " + whatCol);
		Square opponentStart = null;
		boolean done = false; 
		int currentCol = whatCol;
		int currentRow = whatRow;

		while( !done && currentCol >= 0 && currentRow < boardWidthSquares){

			//	System.out.println("In findOpponentDiagLEFT loop, checking currentRow " + currentRow + " and currentCol  " + currentCol );

			if(theGameBoard[currentRow][currentCol].getState() == this.opponentStoneColor){
				opponentStart = theGameBoard[currentRow][currentCol];
				done = true;	
			}
			currentCol--;
			currentRow++;
		}

		//System.out.println(" Hello from bottom of findOpponentDiagRightTop just about to return a start square");
		return opponentStart;
	}
	public Square findOpponentStartVertical(int whatRow, int whatCol){

		Square opponentStart = null;
		boolean done = false;
		int currentRow = whatRow;

		while(!done && currentRow < boardWidthSquares){
			if(theGameBoard[currentRow][whatCol].getState() == this.opponentStoneColor){
				opponentStart = theGameBoard[currentRow][whatCol];
				done = true;
			}
			currentRow++;
		}
		return opponentStart;
	}

	public void lookForGroupsHorizontally(int lastMoveRow, int lastMoveCol) {
		int curCol;
		for(int row = 0; row < boardWidthSquares; ++row){
			curCol = 0;
			// Step 1 skip over stones until you find an opponent stone
			while(curCol < boardWidthSquares){
				Square newStart = findOpponentStartHorizontal(row, curCol);
				if(newStart != null){
					//Now I can do stuff  
					// Make an object group

					OpponentGroup newGroup = new OpponentGroup(OpponentGroup.HORIZONTAL_GROUP);
					//Add stone to array
					newGroup.addSquareToGroup(newStart);
					// Check Edge
					int startRow = newStart.getRow();
					int startCol = newStart.getCol();
					if(startCol <=0){
						newGroup.setEnd1Square(null);
					}
					else{
						newGroup.setEnd1Square(theGameBoard[startRow][startCol -1]);
					}
					// Check to see if the current player move is this stone

					if(startRow == lastMoveRow && startCol == lastMoveCol){
						newGroup.setCurrentMoveIsInThisGroup(true);
						newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength() - 1);
					}


					//Start getting neighbors
					startCol++;
					while(startCol < boardWidthSquares &&
							theGameBoard[startRow][startCol].getState() == this.opponentStoneColor){
						newGroup.addSquareToGroup(theGameBoard[startRow][startCol]);
						if(startRow == lastMoveRow && startCol == lastMoveCol){
							newGroup.setCurrentMoveIsInThisGroup(true);
							newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength() - 1);
						}
						startCol++;
					}
					if(startCol >= boardWidthSquares){
						newGroup.setEnd2Square(null);
					}
					else{
						newGroup.setEnd2Square(theGameBoard[startRow][startCol]);
					}
					curCol = startCol;
					this.addNewGroupToGroupLists(newGroup);

				}
				else{
					curCol = boardWidthSquares;
				}
			}


			// Step 2... when you find one one
			// YEA!! Make an opponent group 
			// Step 3 --- get All stones for the group (on horizontal line)
		}

	}

	public Square findOpponentStartHorizontal(int whatRow, int whatCol){

		Square opponentStart = null;
		boolean done = false;
		int currentCol = whatCol;

		while(!done && currentCol < boardWidthSquares){
			if(theGameBoard[whatRow][currentCol].getState() == this.opponentStoneColor){
				opponentStart = theGameBoard[whatRow][currentCol];
				done = true;
			}
			currentCol++;
		}
		return opponentStart;
	}

	public void doInMiddleCheck( int groupSize ){

		for(int row = 0; row < boardWidthSquares; ++row){
			for(int col = 0; col < boardWidthSquares; ++col){
				if(theGameBoard[row][col].getState() == PenteMain.EMPTY){
					checkForBlockInMiddle(row, col, groupSize);
				}
			}
		}
	}

	public void checkForBlockInMiddle(int row, int col, int groupSize){

		boolean done = false;
		int[] myDys = {-1, 0, 1};
		int whichDy = 0;

		while(!done && whichDy < 3){
			checkForBlockInMiddleAllAround(row, col, groupSize, myDys[whichDy], 1 );
			whichDy++;
		}
		checkForBlockInMiddleAllAround(row, col, groupSize, 1, 0 );	
	}


	public void checkForBlockInMiddleAllAround(int row, int col, int groupSize, int dy, int dx)
	{

		int sRow = row;
		int sCol = col;
		//System.out.println("In checkForBlockInMiddleAllAround sRow and sCol is [" + 
		//sRow + ", " + sCol + "]");

		//for a right-check and left...
		int howManyRight = 0;
		int howManyLeft = 0;

		//loop to check right side of where stone s is
		int step = 1;
		//System.out.println("In checkForWinAllInOne sRow and sCol are [" + sRow + ", " + sCol + "]");
		//System.out.println("In checkForWinAllInOne dy and dx are [" + dy + ", " + dx + "]");
		while((sCol + (step * dx) < boardWidthSquares) && (sRow + (step * dy) < boardWidthSquares) &&
				(sCol + (step * dx) >= 0) && (sRow + (step * dy) >= 0) &&
				(theGameBoard[sRow + (step * dy)][sCol + (step * dx)].getState() == this.opponentStoneColor)){
			howManyRight++;
			step++;
		}
		//Moving Left....
		step = 1;
		while((sCol - (step * dx) >= 0) &&  (sRow - (step * dy) >= 0) &&
				(sCol - (step * dx) < boardWidthSquares) && (sRow - (step * dy) < boardWidthSquares) &&
				(theGameBoard[sRow - (step * dy)][sCol - (step * dx)].getState() == this.opponentStoneColor)){
			howManyLeft++;
			step++;
		}


		if((howManyRight + howManyLeft) >= groupSize){
			//If you have this then you want to set Up an Opponent group for this
			System.out.println("For square at " + row + ", " + col + " we have group of size of " + (howManyRight + howManyLeft));
			OpponentGroup newGroup;

			if( groupSize == 4 ) {
				String middleGroupText = getMiddleGroupText(dx, dy, 4);
				newGroup = new OpponentGroup(OpponentGroup.MIDDLE_4_GROUP);
				newGroup.setGroupRanking(4);
				newGroup.setGroupLength(4);
				newGroup.setGroupTypeText(middleGroupText);

			} else {
				String middleGroupText = getMiddleGroupText(dx, dy, 3);
				newGroup = new OpponentGroup(OpponentGroup.MIDDLE_3_GROUP);
				newGroup.setGroupRanking(3);
				newGroup.setGroupLength(3);
				newGroup.setGroupTypeText(middleGroupText);
			}

			newGroup.setInMiddleGroupStatus(true);
			newGroup.setInMiddleGroupSquare(theGameBoard[row][col]);
			this.addNewGroupToGroupLists(newGroup);		
		}	
	}

	public String getMiddleGroupText(int dx, int dy, int groupSize){
		String gs = "";
		if(groupSize == 4){
			gs = "4";
		} else {
			gs = "3";
		}
		String theType = "";
		if(dx == 1){
			if(dy == 1) theType = "Diag Right";
			if(dy == 0) theType = "Horizontal";
			if(dy == -1) theType = "Diag Left";
		} else {
			theType = "Vertical";
		}

		return "Middle " + gs + ": " + theType;
	}


	public void addNewGroupToGroupLists(OpponentGroup ng){
		//It takes a new OpponentGroup object and adds to
		// one of the 4 OpponentGroup Array Lists that we are running
		/*
		 * adds to : one of
		 * groups4
		 * groups3
		 * groups2
		 * groups1
		 * 
		 */

		switch(ng.getGroupLength()){

		case 1: 
			groups1.add(ng);
			break;
		case 2: 
			System.out.println("I have an " + ng.getGroupList() + " Group with two opponent stones");

			groups2.add(ng);
			break;
		case 3:
			System.out.println("I have an " + ng.getGroupList() + " Group with three opponent stones");

			groups3.add(ng);
			break;
		case 4:
			System.out.println("I have an " + ng.getGroupList() + " Group with four opponent stones");

			groups4.add(ng);
			break;
		default:
			break;
		}
	}
}



