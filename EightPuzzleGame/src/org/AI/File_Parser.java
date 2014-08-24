package org.AI;

/***
 * File Parser Class
 * @author NIKHIL BHARADWAJ RAMESHA 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class File_Parser {

	/***
	 * Method for parsing the input file
	 * @param inputFile
	 * @return
	 * @throws IOException
	 */
	
	public static int [][] fileParser(File inputFile) throws IOException
	{
		BufferedReader bfReader=new BufferedReader(new FileReader(inputFile));
		String readLine;
		int [][] inputArray = new int [3][3];
		int i_index=0;
		while((readLine=bfReader.readLine())!=null)
		{
			String [] terms = readLine.split("\t");
			int j_index=0;
			for(String term : terms)
			{
				inputArray[i_index][j_index++]=Integer.parseInt(term);
			}
			i_index++;
		}
		bfReader.close();
		return inputArray;
	}
	
	

}
