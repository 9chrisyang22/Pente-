package Pente;

import java.util.ArrayList;



public class OpponentGroup {
	
	//Adding Literal Codes for each type of stone group we can have.
	public static final int HORIZONTAL_GROUP = 1;
	public static final int VERTICAL_GROUP = 2;
	public static final int DIAG_RIGHT_GROUP = 3;
	public static final int DIAG_LEFT_GROUP = 4;
	public static final int MIDDLE_4_GROUP = -4;
	public static final int MIDDLE_3_GROUP = -3;
	
	//Class Data Field
	private ArrayList<Square> groupList;  //Expandable list
	private int groupLength = 0;
	private int groupRanking = 0;
	private Square end1Square = null;
	private Square end2Square = null;
	
	//New fields to have this class know what kind of group it is...
	private int groupType;
	private String groupTypeText;
	
	//Handle issue of whether the current move is a part of this group
	private boolean currentMoveIsInGroup=false;
	private int currentMoveArrayListLocation = -1;
	
	//NEW FIELDS AS OF APRIL 30 for IN MIDDLE GROUP
	private boolean inMiddleGroupStatus = false;
	private Square inMiddleSquare = null;

	
	//This is our constructor
	
	public OpponentGroup(int gt) {
		// TODO Auto-generated constructor stub
		groupList = new ArrayList<Square>();
		//System.out.println("In Opponent Group -- just made a new group");
		
		//NEW Added April 17 so group type knows what kind of group it is...Horz, Vert, DiagR or DiagL
		groupType = gt;
		this.setGroupTypeToString();
	}
	
	public void setInMiddleGroupStatus(boolean value){
		inMiddleGroupStatus = value;
	}
	
	public boolean getInMiddleGroupStatus(){
		return inMiddleGroupStatus;
	}
	
	public void setInMiddleGroupSquare(Square whatSquare){
		inMiddleSquare = whatSquare;
	}
	
	public Square getInMiddleGroupSquare(){
		return inMiddleSquare;
	}
	
	
	public void addSquareToGroup(Square whichSquare){
		groupList.add(whichSquare);
		groupLength++;
		groupRanking++;
	}
	
	public void setEnd1Square(Square whatSquare){
		end1Square = whatSquare;
	}
	
	public void setEnd2Square(Square whatSquare){
		end2Square = whatSquare;
	}
	
	public ArrayList<Square> getGroupList(){
		return groupList;
	}
	
	public Square getEnd1Square(){
		return end1Square;
	}
	
	public Square getEnd2Square(){
		return end2Square;
	}
	
	public int getGroupLength(){
		return groupLength;
	}
	
	public void setGroupLength(int l){
		groupLength = l;
	}
	public int getGroupRanking(){
		return groupRanking;
	}
	
	public void setGroupRanking(int newRanking){
		 groupRanking = newRanking;
	}
	
	//**** Added on April 15 to keep info on current move
	public int getOpponentGroupType(){
		return groupType;
	}
		
	public void setCurrentMoveIsInThisGroup(boolean setting){
		currentMoveIsInGroup = true;
	}
	
	public boolean getCurrentMoveIsInGroup(){
		return currentMoveIsInGroup;
	}
	
	public void setCurrentMoveArrayListLocation(int arrayListIndex){
		currentMoveArrayListLocation = arrayListIndex;
	}
	
	public int getArrayListSizeFromArray(){
		return groupList.size();
	}
	
	private void setGroupTypeToString(){
		switch(groupType){
			case MIDDLE_3_GROUP: // -3
				groupTypeText = "Midde-3";
				break;
				
			case MIDDLE_4_GROUP: // -4
				groupTypeText = "Midde-4";
				break;	
				
			case HORIZONTAL_GROUP: // 1
				groupTypeText = "Horizontal";
				break;
			case VERTICAL_GROUP:   // 2
				groupTypeText = "Vertical";
				break;
			case DIAG_RIGHT_GROUP: // 3
				groupTypeText = "Diagonal Right";
				break;
			case DIAG_LEFT_GROUP:   // 4
				groupTypeText = "Diagonal Left";
				break;
			 default:
				groupTypeText = "Something is messed up";
				break;
		
		}
	}
	
	public String getGroupTypeText(){
		return groupTypeText;
	}
	
	public void setGroupTypeText(String newText){
		groupTypeText = newText;
	}

}
