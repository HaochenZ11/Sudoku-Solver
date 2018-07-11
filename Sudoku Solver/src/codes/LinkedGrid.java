package codes;
import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class LinkedGrid {
	public Node root;
	public LinkedGrid(int dimension) //creates a linked grid
	{
		Node temp = new Node();
		root = temp;
		Node marker = root;//marker is also temp
		
		for (int x=0; x < dimension-1; x++)//creates a first row of linked nodes
		{
			temp = new Node();
			marker.setRight(temp);

			temp.setLeft(marker);
			marker = temp;
		}//links the first row
		marker = root;
		Node rowMarker = marker;
		
		for(int z=1; z < dimension; z++)//creates rows of nodes top to bottom
		{
			temp = new Node();
			rowMarker.setDown(temp);
			temp.setUp(rowMarker);
			marker = temp;//moves the marker down
			rowMarker = temp;
			for(int y=0; y < dimension-1; y++)//creates a row of nodes left to right
			{
				temp = new Node();
				temp.setLeft(marker);
				marker.setRight(temp);
				temp.setUp(marker.getUp().getRight());
				marker.getUp().getRight().setDown(temp);
				marker = temp;//marker moves down the row from left to right
			}		
		}
	}
	public void solve(Node n, int x)//solves for one node
	{	
		n.setData(x+1);
		Node temp = n;
		while(temp != null)
		{
			temp.eliminate(x);
			temp = temp.getUp();
			
		}
		temp = n;
		while(temp != null)
		{
			temp.eliminate(x);
			temp = temp.getRight();
			
		}
		temp = n;
		while(temp != null)
		{
			temp.eliminate(x);
			temp = temp.getLeft();
			
		}
		temp = n;
		while(temp != null)
		{
			temp.eliminate(x);
			temp = temp.getDown();
			
		}
		temp=root;
		Node rowMarker = root;
		while(temp != null)
		{
			while(temp!=null)
			{
				if(temp.getBoxID()==n.getBoxID())
				{
					temp.eliminate(x);
				}
				temp = temp.getRight();
			}
			temp = rowMarker.getDown();
			if(rowMarker.getDown() !=null)
			{
				rowMarker = temp;
			}
		}
	}//and in a square
	public void setRow()//sets the row numbers
	{
		int x=1;
		Node temp=root;
		Node rowMarker = root;
		while(temp!=null)//check at beginning of new row
		{
			while(temp!=null)//check at end of row
			{
				temp.setRowNumber(x);
				temp = temp.getRight();
			}
			temp = rowMarker.getDown();
			x+=1;
			if(rowMarker.getDown() !=null)//
			{
				rowMarker = temp;
			}
		}
	}
	public void setColumn()//assigns column numbers to the nodes
	{
		int x=1;
		Node temp=root;
		Node columnMarker = root;
		while(temp!=null)//check at beginning of new row
		{
			while(temp!=null)//check at end of row
			{
				temp.setColumnNumber(x);
				temp = temp.getDown();
			}
			temp = columnMarker.getRight();//moves to next column
			x+=1;
			if(columnMarker.getRight() !=null)
			{
				columnMarker = temp;
			}
		}
	}
	public void setBoxID()
	{	Node temp=root;
		Node rowMarker = root;
	
		while(temp!=null)//check at beginning of new row
		{
			while(temp!=null)//check at end of row
			{
				if(temp.getColumnNumber() < 4 && temp.getRowNumber() < 4)//sets the box ID for node depending on location in grid
				{
					temp.setBoxID(1);
				}
				else if(temp.getColumnNumber() < 7 && temp.getColumnNumber() > 3 && temp.getRowNumber() < 4)
				{	
					temp.setBoxID(2);
				}
				else if( temp.getColumnNumber() > 6 && temp.getRowNumber() < 4)
				{
					temp.setBoxID(3);
				}
				else if(temp.getColumnNumber() < 4 && temp.getRowNumber() > 3 && temp.getRowNumber() < 7)
				{
					temp.setBoxID(4);
				}
				else if(temp.getColumnNumber() < 7 && temp.getColumnNumber() > 3 && temp.getRowNumber() > 3 && temp.getRowNumber() < 7)
				{
					temp.setBoxID(5);
				}
				else if(temp.getRowNumber() > 3 && temp.getRowNumber() < 7 && temp.getColumnNumber() > 6)
				{
					temp.setBoxID(6);
				}
				else if(temp.getColumnNumber() < 4 && temp.getRowNumber() > 6)
				{
					temp.setBoxID(7);
				}
				else if(temp.getColumnNumber() < 7 && temp.getColumnNumber() > 3  && temp.getRowNumber() > 6)
				{
					temp.setBoxID(8);
				}
				else if(temp.getColumnNumber() > 6 && temp.getRowNumber() > 6)
				{
					temp.setBoxID(9);
				}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
				temp = temp.getRight();
			}
			temp = rowMarker.getDown();
			if(rowMarker.getDown() !=null)//
			{
				rowMarker = temp;
			}
		}	
	}
	public void logic()
	{	Node temp = root;
		Node rowMarker = root;
		int sum1=0, num=0;
		
		//METHOD ONE 
		//checks if only one number is possible for a node
		while(temp!=null)
		{
			while(temp!=null)
			{	sum1=0;
				for(int x=0; x <9; x++)
				{
					if(temp.getPossible()[x]== true)//if a number is possible
					{
						sum1++;
						num = x;
					}
				}
				if(sum1 ==1)
				{	
					solve(temp, num);//set the node to be that number
					if(check()==false)
					{
						logic();//recursive code after solving one node
					}
				}
				temp = temp.getRight();
			}
			temp = rowMarker.getDown();
			if(rowMarker.getDown() !=null)
			{
				rowMarker = temp;
			}
		}
		//METHOD TWO 
		//checks if only one cell can be a certain number
		temp=root;
		Node columnMarker = root, one=root;
		int sum2=0;
		while(temp!=null)//FOR COLUMNS: goes through columns left to right
		{	sum2=0;
			for(int x=0; x < 9; x++)//for each address in the array
			{	
				sum2=0;//resets the sum
				while(temp!=null)//going down one column
				{	
					if(temp.getPossible()[x]==true)
					{	one= temp;
						sum2++;
					}
					temp = temp.getDown();//goes down to node below
				}
				if(sum2==1)//if only one node can be a certain number
				{		
					solve(one, x);//solves for that node
					if(check()==false)
					{
						logic();//recursive code after solving one node
					}
				}
				temp = columnMarker;
			}
			temp = columnMarker.getRight();//moves to next column
			if(columnMarker.getRight() !=null)
			{
				columnMarker = temp;
			}
		}//end while loop for columns
		temp=root;
		rowMarker=root;
		Node two=root;
		int sum3=0;
		while(temp!=null)//FOR ROWS: goes down the grid
		{	
			sum3=0;
			for(int x=0; x < 9; x++)//for each address in the array
			{	
				sum3=0;
				while(temp!=null)//goes across a row
				{	
					if(temp.getPossible()[x]==true)
					{	two= temp;
						sum3++;
					}
					temp = temp.getRight();
				}
				if(sum3==1)
				{
					solve(two, x);
					if(check()==false)
					{
						logic();//recursive code after solving one node
					}
				}
			}
			temp = rowMarker.getDown();
			if(rowMarker.getDown() !=null)
			{
				rowMarker = temp;
			}
		}	
		temp=root;
		rowMarker=root;
		Node three=root;
		int sum4=0,boxNum;
		for(boxNum=1; boxNum<10; boxNum++)//goes through grid each time for nodes in a certain box
		{	
			sum4=0;
			for(int x=0; x < 9; x++)//for each address in the array of possibilities
			{	
				sum4=0;
				while(temp!=null)//FOR BOXES
				{
					while(temp!=null)
					{	if(temp.getBoxID()==boxNum)//if a node is in the box that is being checked
						{
							if(temp.getPossible()[x]==true)
							{	three= temp;
								sum4++;
							}
						}
						temp = temp.getRight();
					}
					if(sum4==1)
					{
							solve(three, x);
							if(check()==false)
							{
								logic();//recursive code after solving one node
							}
					}
					temp = rowMarker.getDown();
					if(rowMarker.getDown() !=null)
					{
						rowMarker = temp;
					}
				}//ends loop through boxes
			}
		}//ends the whole loop
		
		//METHOD THREE
		//if two cells share 2 possibilities that are the only ones for each node, eliminate the possibilities for the other nodes
		
		if(check()==false)//if no nodes have been solved, go to guessing
		{
			guess();
		}
	}
	public void guess()
	{
		
		/*int guess=0;
		Node temp = root;
		Node rowMarker = root;
		while(temp!=null)
		{
			while(temp!=null)
			{
				System.out.print(temp.getData()+" ");
				temp = temp.getRight();
			}
			temp = rowMarker.getDown();
			if(rowMarker.getDown() !=null)//if there is another row below
			{
				rowMarker = temp;
			}
		}	
		try
		{	
			while(temp!=null)
			{
				while(temp!=null)//goes across a row of nodes
				{
					if(temp.getData()==0)//if the node is unsolved
					{
						for(int x=0; x < 9; x++)//goes through the possibilities for a node
						{
							if(temp.getPossible()[x]==true)//if a number is possible
							{
								temp.setData(x+1);//makes the guess and stores it
								guess=x+1;
								solve(temp, x);
								logic();//continues solving the puzzle
							}
						}
					}
					temp = temp.getRight();
				}
				temp = rowMarker.getDown();
				if(rowMarker.getDown() !=null)//if there is another row below
				{
					rowMarker = temp;
				}
			}
		}
		catch(Exception e)
		{
			
		}	*/
	}
	public void setGrid()
	{
		Scanner input = new Scanner(System.in);
		int size = 81;
		int counter = 0;
		Node temp = root;
		Node rowMarker = root;
		int[] list = new int [size];
		boolean [] set= {false,false,false,false,false,false,false,false,false};
		System.out.println("Enter grid numbers: " );
		for(int x =0; x < size; x++)
		{
			list[x] = input.nextInt();
		}
		while(temp!=null)
		{
			while(temp!=null)
			{
				temp.setData(list[counter]);
				if(list[counter] !=0)
				{
					solve(temp, list[counter]-1);
					temp.setPossible(set);
				}				
				temp = temp.getRight();
				counter++;
			}
			temp = rowMarker.getDown();
			if(rowMarker.getDown() !=null)
			{
				rowMarker = temp;
			}
		}
	}
	public boolean check()
	{
		Node temp = root;
		Node rowMarker = root;
		while(temp!=null)
		{
			while(temp!=null)
			{
				if(temp.getData()==0)
				{
					return false;
				}
				temp = temp.getRight();
			}
			temp = rowMarker.getDown();
			if(rowMarker.getDown() !=null)//if there is another row below
			{
				rowMarker = temp;
			}
		}
		return true;
	}
	public void display()
	{	
		int counter = 0;
		Node temp = root;
		Node rowMarker = root;
		while(temp!=null)
		{
			while(temp!=null)
			{
				System.out.print(temp.getData()+" ");
				temp = temp.getRight();
			}
			temp = rowMarker.getDown();
			if(rowMarker.getDown() !=null)//if there is another row below
			{
				rowMarker = temp;
			}
			System.out.println();
		}	
		System.out.println();
	}
}
