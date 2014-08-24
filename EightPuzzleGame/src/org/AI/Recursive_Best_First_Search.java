package org.AI;

/***
 * @author NIKHIL BHARADWAJ RAMESHA
 * Recursive Best First Search Class
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

public class Recursive_Best_First_Search {
	
	public int [] [] initialArray=new int [3][3];
	public int [] [] goalArray = new int [3][3];
	public static HashMap<Integer, String> movement_A=new HashMap<Integer, String>();
	public static HashMap<Integer, String> movement_B=new HashMap<Integer, String>();
	
	/***
	 * Constructor to initialize the parameters
	 * @param array
	 * @param goal
	 */
	
	Recursive_Best_First_Search(int [][] array, int [][] goal)
	{
		this.initialArray=array;
		this.goalArray = goal;
	}
	
	/***
	 * Comparator method which overrides the default behavior for the Priority Queue
	 */
	
	public static Comparator<EightPuzzle> puzzleComparator = new Comparator<EightPuzzle>()
	{
		@Override
		public int compare(EightPuzzle a, EightPuzzle b)
		{
			return (int)((a.value+a.pathCost) - (b.value+b.pathCost));
		}
	};
	
	/***
	 * The method implements RBFS algorithm. The call is recursive
	 * @param current_state
	 * @param bound
	 * @param steps
	 * @return
	 * @throws CloneNotSupportedException
	 */
	
	public Object recursive_best_first_search(EightPuzzle current_state, int bound, int steps) throws CloneNotSupportedException
	{
		if(current_state.goalTesting())
		{
			return current_state;
		}
		steps++;
		
		Queue<EightPuzzle> instance_queue=new PriorityQueue<EightPuzzle>(4, puzzleComparator);
		ArrayList<EightPuzzle> moves= current_state.generate_allPossibleMoves();
				
		for(EightPuzzle move : moves)
		{
			instance_queue.add(move);
		}

		int alt=1000;
		
		if(instance_queue.size()==0)
		{
			return alt;
		}
		
		while(!instance_queue.isEmpty())
		{
			EightPuzzle bestInstance = instance_queue.remove();
			if((bestInstance.value+bestInstance.pathCost) > bound)
			{
				return (bestInstance.value+bestInstance.pathCost);
			}
			if(instance_queue.size()>=1)
			{
				EightPuzzle tempPzl = instance_queue.element();
				if(tempPzl!=null)
				{
					alt = tempPzl.value+tempPzl.pathCost;
				}
			}
			else
			{
				alt=1000;
			}
			int minimum = bound;
			if(alt < bound)
			{
				minimum = alt;
			}
			
			Object retval = recursive_best_first_search(bestInstance, minimum, steps);
			if(retval instanceof Integer)
			{
				bestInstance.value=(Integer)retval - bestInstance.pathCost;
				instance_queue.add(bestInstance);
			}
			else
			{
				return retval;
			}
		}
		return null;
	}
	
	/***
	 * Method called to solve the puzzle
	 * @throws CloneNotSupportedException
	 */
	
	void puzzle_Solver() throws CloneNotSupportedException
	{
		movement_A.put(-1, "DOWN");
		movement_A.put(1, "UP");
		movement_B.put(1, "LEFT");
		movement_B.put(-1, "RIGHT");
		EightPuzzle initial_state=new EightPuzzle();
		initial_state.setInitialValues(this.initialArray);
		initial_state.setGoalPosition(this.goalArray);
		int steps=1;
		
		Object solution_res=this.recursive_best_first_search(initial_state, 1000, steps);
		if(solution_res instanceof EightPuzzle)
		{
			EightPuzzle result = (EightPuzzle)solution_res;
			System.out.println("Solution found as shown below \n");
			result.printSequence();
		}
		else
		{
			System.out.println("No Solution found");
		}
	}

}