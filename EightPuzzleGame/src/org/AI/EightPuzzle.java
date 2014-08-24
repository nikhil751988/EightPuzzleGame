package org.AI;

/***
 * @author NIKHIL HARADWAJ RAMESHA
 * 8-Puzzle Class
 */

import java.util.ArrayList;

public class EightPuzzle implements Cloneable{
	public String currentSeq;
	public String goalSeq;
	public int depth;
	public ArrayList<String> sequence;
	public int value;
	public int pathCost;
	
	/***
	 * Default Constructor
	 */
	
	public EightPuzzle(){
		this.currentSeq=null;
		this.goalSeq=null;
		this.depth=0;
		this.sequence=new ArrayList<String>();
		this.value=0;
		this.pathCost=0;
	}
	
	/***
	 * Method to set initial values for few of the class parameters
	 * @param current_State
	 */
	
	public void setInitialValues(int [][] current_State)
	{
		this.currentSeq=convertArrayToString(current_State);
		this.depth=1;
		this.sequence.add(currentSeq);
	}
	
	/***
	 * Method to set Goal State
	 * @param goal_State
	 */
	
	public void setGoalPosition(int [][] goal_State)
	{
		this.goalSeq=convertArrayToString(goal_State);
		this.evaluate(this.goalSeq);
	}
	
	/***
	 * Function to test for the goal state
	 * @return
	 */
	
	public boolean goalTesting()
	{
		if(this.currentSeq.equalsIgnoreCase(this.goalSeq))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/***
	 * Function to evaluate each state
	 * Calls heuristics and path cost function 
	 * @param stateSeq
	 */
	
	public void evaluate(String stateSeq)
	{
		calculate_Heuristics(stateSeq);
		calculate_pathCost();
	}
	
	/***
	 * Function to generate all possible moves from a given state
	 * @return
	 * @throws CloneNotSupportedException
	 */
	
	public ArrayList<EightPuzzle> generate_allPossibleMoves() throws CloneNotSupportedException
	{
		ArrayList<EightPuzzle> possible_moves=new ArrayList<EightPuzzle>();
		int [][]current_state=this.convertStringtoArray(this.currentSeq);
		int row_1=0,col_1=0;
		for(int row=0;row<3;row++)
		{
			for(int col=0;col<3;col++)
			{
				if(current_state[row][col]==0)
				{
					row_1=row;
					col_1=col;
					break;
				}
			}
		}
		this.check_Move(row_1, col_1, row_1-1, col_1, current_state, possible_moves);
		this.check_Move(row_1, col_1, row_1+1, col_1, current_state, possible_moves);
		this.check_Move(row_1, col_1, row_1, col_1-1, current_state, possible_moves);
		this.check_Move(row_1, col_1, row_1, col_1+1, current_state, possible_moves);
		return possible_moves;
	}
	
	/***
	 * Function to check whether the move is possible
	 * @param srcR
	 * @param srcC
	 * @param destR
	 * @param destC
	 * @return
	 */
	
	public boolean isMovePossible(int srcR, int srcC, int destR, int destC)
	{
		boolean pFlag=true;
		if(srcR<0 || srcC<0 || destR<0 || destC<0)
		{
			pFlag=false;
		}
		if(srcR>2 || srcC>2 || destR>2 || destC>2)
		{
			pFlag=false;
		}
		return pFlag;
	}
	
	/***
	 * Function to move the blank in the puzzle to get new state
	 * @param srcR
	 * @param srcC
	 * @param destR
	 * @param destC
	 * @param state
	 * @return
	 */
	
	public int [][] move_the_Blank(int srcR, int srcC, int destR, int destC, int[][] state)
	{
		int temp=state[destR][destC];
		state[destR][destC]=state[srcR][srcC];
		state[srcR][srcC]=temp;
		return state;
	}
	
	/***
	 * Function to check for the possible moves
	 * This method also creates new possible states of the puzzle
	 * @param srcR
	 * @param srcC
	 * @param destR
	 * @param destC
	 * @param state
	 * @param possible_moves
	 * @throws CloneNotSupportedException
	 */
	
	public void check_Move(int srcR, int srcC, int destR, int destC, int [][] state, ArrayList<EightPuzzle> possible_moves) throws CloneNotSupportedException
	{
		if(isMovePossible(srcR, srcC, destR, destC))
		{
			String tempSeq = this.convertArrayToString(state);
			int [][] temp_State = this.convertStringtoArray(tempSeq);
			int [][] newState = new int [3][3];
			newState = this.move_the_Blank(srcR, srcC, destR, destC, temp_State);			
			String newSeq=this.convertArrayToString(newState);
			if(!this.sequence.contains(newSeq))
			{
				EightPuzzle newMove = new EightPuzzle();
				newMove.goalSeq=this.goalSeq;
				newMove.sequence=new ArrayList<String>(this.sequence);
				newMove.value=this.value;
				newMove.pathCost=this.pathCost;
				newMove.currentSeq=newSeq;
				newMove.sequence.add(newSeq);
				newMove.depth++;
				newMove.evaluate(newSeq);
				possible_moves.add(newMove);
			}
		}
	}
	
	/***
	 * Function to calculate Heuristics using Manhattan Distance
	 * @param stateSeq
	 */
	
	public void calculate_Heuristics(String stateSeq)
	{
		int [][]state = convertStringtoArray(stateSeq);
		int [][]goalPos = convertStringtoArray(this.goalSeq);
		this.value=0;
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				String positions=find_Blocks(state, goalPos[i][j]);
				int blockrow = Integer.parseInt(positions.substring(0, positions.indexOf(',')));
				int blockCol = Integer.parseInt(positions.substring(positions.indexOf(',')+1,positions.length()));
				int blockValue= Math.abs(blockrow-i)+Math.abs(blockCol-j);
				this.value=this.value+blockValue;
			}
		}
	}
	
	/***
	 * Function to find the blocks of each tile/number
	 * @param state
	 * @param value
	 * @return
	 */
	
	public String find_Blocks(int [][] state, int value)
	{
		int row_1=0;
		int col_1=0;
		for(int row=0;row<3;row++)
		{
			for(int col=0;col<3;col++)
			{
				if(state[row][col]==value)
				{
					row_1=row;
					col_1=col;
					break;
				}
			}
		}
		return String.valueOf(row_1)+","+String.valueOf(col_1);
	}
	
	/***
	 * Method to assign the calculated path
	 */
	
	public void calculate_pathCost()
	{
		this.pathCost=this.depth;
	}
	
	/***
	 * Method to find the zero element position during printing of each sequence of moves
	 * @param puzzleState
	 * @return
	 */
	
	public String findElementZero(int [][] puzzleState)
	{
		int row=0,col=0;
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(puzzleState[i][j]==0)
				{
					row=i;
					col=j;
					break;
				}
			}
		}
		return String.valueOf(row)+","+String.valueOf(col);
	}
	
	/***
	 * Method to Print the output sequence
	 */
	
	public void printSequence()
	{
		int itr=0;
		int posx=0, posy=0;
		for(String seq : this.sequence)
		{
			int [][] puzzleState = this.convertStringtoArray(seq);
			if(itr==0)
			{
				System.out.println("Inital State");
				String position = findElementZero(puzzleState);
				posx=Integer.parseInt(position.substring(0, position.indexOf(',')));
				posy=Integer.parseInt(position.substring(position.indexOf(',')+1, position.length()));
				itr++;
			}
			else
			{
				String position=findElementZero(puzzleState);
				int tempx=Integer.parseInt(position.substring(0, position.indexOf(',')));
				int tempy=Integer.parseInt(position.substring(position.indexOf(',')+1, position.length()));
				if((posy-tempy)==0)
				{
					int diffx=posx-tempx;
					System.out.println("Move: "+Recursive_Best_First_Search.movement_A.get(diffx));
				}
				else if((posx-tempx)==0)
				{
					int diffy=posy-tempy;
					System.out.println("Move: "+Recursive_Best_First_Search.movement_B.get(diffy));
				}
				posx=tempx;
				posy=tempy;
			}
			for(int i = 0; i<puzzleState.length;i++)
			{
				for(int j = 0;j<puzzleState[i].length;j++)
				{
					System.out.print(puzzleState[i][j]+" ");
				}
				System.out.println();
			}
			System.out.println("\n");
		}
	}
	
	/***
	 * Method used for converting Array to String
	 * @param current_Array
	 * @return
	 */
	
	public String convertArrayToString(int [][] current_Array)
	{
		String seq=null;		
		for(int index1=0;index1<current_Array.length;index1++)
		{
			for(int index2=0;index2<current_Array[index1].length;index2++)
			{
				if(seq==null)
				{
					seq=String.valueOf(current_Array[index1][index2]);
				}
				else
				{
					seq+=String.valueOf(current_Array[index1][index2]);
				}
			}
		}
		return seq;
	}
	
	/***
	 * Method used for converting String to Array 
	 * @param sequence
	 * @return
	 */
	
	public int [][] convertStringtoArray(String sequence)
	{
		int [][] state=new int[3][3];
		int index=0;
		int i=0,j=0;
		while(index<sequence.length())
		{
			if(j%3==0 && j!=0)
			{
				j=0;
				i++;
			}
			state[i][j++]=Character.getNumericValue(sequence.charAt(index));
			index++;
		}
		return state;
	}
}
