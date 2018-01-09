package test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import app.DataPacket;

public class Data
{
	public static ArrayList<DataPacket> createList()
	{
		String filePath = System.getProperty("user.dir") + "/Sample.csv";
		CSVReader reader = null;
		ArrayList<DataPacket> data = new ArrayList<>();

		try
		{
			reader = new CSVReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String[] element = null;

			// Assigns the row of columns so that the while loop skips this row
			element = reader.readNext();

			while ((element = reader.readNext()) != null)
			{
				double time = Double.parseDouble(element[0]);
				double ultrasonic = Double.parseDouble(element[1]);
				boolean L0 = Boolean.parseBoolean(element[2]);
				boolean L1 = Boolean.parseBoolean(element[3]);
				boolean L2 = Boolean.parseBoolean(element[4]);
				double xAccel = Double.parseDouble(element[5]);
				double yAccel = Double.parseDouble(element[6]);
				double zAccel = Double.parseDouble(element[7]);
				double xGyro = Double.parseDouble(element[8]);
				double yGyro = Double.parseDouble(element[9]);
				double zGyro = Double.parseDouble(element[10]);
				double xMag = Double.parseDouble(element[11]);
				double yMag = Double.parseDouble(element[12]);
				double zMag = Double.parseDouble(element[13]);
				int servoAngle = Integer.parseInt(element[14]);
				String state = element[15];

//				DataPacket packet = new DataPacket(time, ultrasonic, L0, L1, L2, xAccel, yAccel, zAccel,
//						  xGyro, yGyro, zGyro, xMag, yMag, zMag, servoAngle, state);
//				data.add(packet);
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

		return data;
	}
}

