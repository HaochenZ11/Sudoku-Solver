package codes;

public class Node {
	private Node up, down, left, right;
	private int data=0, boxID=0, rowNumber=0, columnNumber=0,number=0;
	//private boolean[] possible = {true,true,true,true,true,true,true,true,true};
	private boolean[] possible = new boolean[9];
	public Node() {
		up = null;
		down = null;
		left = null;
		right = null;
		data=0;
		for(int x=0; x<9; x++)
			possible[x]=true;
	}
	public boolean[] getPossible() {
		return possible;
	}
	public void setPossible(boolean[] possible) {
		this.possible = possible;
	}               
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getBoxID() {
		return boxID;
	}
	public void setBoxID(int boxID) {
		this.boxID = boxID;
	}
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public int getColumnNumber() {
		return columnNumber;
	}
	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	public void eliminate(int x)
	{
		possible[x] = false;
	}
	public void array()
	{
		
	}
	
	public Node getUp() {
		return up;
	}
	public void setUp(Node up) {
		this.up = up;
	}
	public Node getDown() {
		return down;
	}
	public void setDown(Node down) {
		this.down = down;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}
}
