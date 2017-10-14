import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileInputStream;
import java.io.InputStreamReader;


public class CSV_Controller 
{
CSVReader input;
CSVWriter output;
String fName;

public CSV_Controller()
{
	// initialize input, and ouput
}

// return a double array of Strings from the file assume path is local
public String[][] readFromFile(String fileName)
{
		
	return null;
}	

// return a double array of Strings from the file
public String[][] readFromFile(String fileName, String path)
{
	
	return null;
}

public String[] StringToStringArray(String s)
{
	String[] ret = new String[8];
	
	
	return ret;
}

public void writeToFile(String[] a, String fileName)
{
	//if the file doesn't exist locally create it
}

// assume the file name variable is "fName"
public void writeToFile(String[] row)
{
	
}




}
