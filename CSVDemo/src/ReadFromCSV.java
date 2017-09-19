import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.opencsv.CSVReader;

public class ReadFromCSV 
{
	public static void main(String[] args)
	{	
		String filePath = System.getProperty("user.dir") + "/Sample.csv";
		CSVReader reader = null;
		
		try
		{
			reader = new CSVReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String[] element = null;
			int count = 1;
			
			while ((element = reader.readNext()) != null)
			{
				System.out.println("Member #" + count + " {ID:" + element[0] + ", FirstName:" + element[1] + ", LastName:" + element[2] + ", Position:" + element[3] + "}");
				count++;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
