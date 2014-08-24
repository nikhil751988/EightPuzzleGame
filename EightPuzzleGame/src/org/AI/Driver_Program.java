package org.AI;

/***
 * Main Class
 * @author NIKHIL BHARADWAJ RAMESHA
 */

import java.io.File;
import java.util.Scanner;

public class Driver_Program {

	/**
	 * Main method that drives the program
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//String presentDirectory=System.getProperty("user.dir");
		System.out.println("Enter the input file containing the jumbled puzzle: ");
		Scanner scn = new Scanner(System.in);
		String presentDirectory = scn.next();
		String fileName=presentDirectory+"\\InputFile.txt";
		int [][] goal = {{0,1,2},{3,4,5},{6,7,8}};
		File inpFile=new File(fileName);
		try{
			Recursive_Best_First_Search rbfs = new Recursive_Best_First_Search(File_Parser.fileParser(inpFile), goal);
			rbfs.puzzle_Solver();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		scn.close();
	}

}
