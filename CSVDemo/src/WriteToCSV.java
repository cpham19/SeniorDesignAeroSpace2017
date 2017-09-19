import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import com.opencsv.CSVWriter;

public class WriteToCSV 
{
	public static void main(String[] args)
	{
		CSVWriter writer = null;
		
		try
		{
			writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream("Sample.csv"), "UTF-8"));
			String[] row = new String[] {"1209","Russ","Abbott","Advisor"};
			writer.writeNext(row);
			row = new String[] {"1210","Blake","Patton","Team Lead"};
			writer.writeNext(row);
			row = new String[] {"1211","Karina","Martinez","Customer Liason, Documentation Lead"};
			writer.writeNext(row);
			row = new String[] {"1212","Calvin","Pham","QA Lead"};
			writer.writeNext(row);
			row = new String[] {"1213","William","Garcia","Documentation Lead"};
			writer.writeNext(row);
			row = new String[] {"1214","Robin","Chan","Software Lead"};
			writer.writeNext(row);
			System.out.println("Success!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				writer.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
