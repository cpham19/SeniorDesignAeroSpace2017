package app;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class DataController
{
	public static String columns = "time,ultrasonic,L0,L1,L2,xAccel,yAccel,zAccel,xGyro,yGyro,zGyro,xMag,yMag,zMag,state";

	public DataController()
	{

	}

	// return an ArrayList of DataPackets from the file assume path is local
	public static ArrayList<DataPacket> readFromCSV(String filename)
	{
		String filePath = System.getProperty("Files//" + filename + ".csv");
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
				String state = element[14];

				DataPacket packet = new DataPacket(time, ultrasonic, L0, L1, L2, xAccel, yAccel, zAccel,
						  xGyro, yGyro, zGyro, xMag, yMag, zMag, state);
				data.add(packet);
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

	// Record DataPacket to CSV file
	public static void writeToCSV(String filename, DataPacket packet) throws Exception
	{
		CSVWriter cw = null;
		FileWriter fw = null;

		try
		{
			fw = new FileWriter("Files//" + filename + ".csv", true);
			cw = new CSVWriter(fw);

			cw.writeNext(packet.toStringArray());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				cw.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		//System.out.println(filename + ".csv is written successfully.");
	}

	// Clear the contents of CSV file
	public static void clearCSV(String filename) throws Exception
	{
		CSVWriter cw = new CSVWriter(new OutputStreamWriter(new FileOutputStream("Files//" + filename + ".csv"), "UTF-8"));
		cw.flush();

		cw.writeNext(columns.split(","));

		cw.close();

		System.out.println(filename + ".csv has been cleared successfully.");
	}

	// Write the data from CSV to the database
	public static void writeToDatabase(String tableName, DataPacket packet)
	{
		String url = "jdbc:mysql://localhost:3306/aria_db";
		String username = "root";
		String password = "";
		Connection c = null;

		try
		{
			c = DriverManager.getConnection(url, username, password);

			String sql = "insert into " + tableName + " (time, ultrasonic, L0, L1, L2, xAccel, yAccel, zAccel, xGyro, yGyro, zGyro, xMag, yMag, zMag, state) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = c.prepareStatement(sql);

			pstmt.setDouble(1, packet.getTime());
			pstmt.setDouble(5, packet.getUltrasonic());
			pstmt.setBoolean(6, packet.isL0());
			pstmt.setBoolean(7, packet.isL1());
			pstmt.setBoolean(8, packet.isL2());
			pstmt.setDouble(9, packet.getxAccel());
			pstmt.setDouble(10, packet.getyAccel());
			pstmt.setDouble(11, packet.getzAccel());
			pstmt.setDouble(12, packet.getxGyro());
			pstmt.setDouble(13, packet.getyGyro());
			pstmt.setDouble(14, packet.getzGyro());
			pstmt.setDouble(15, packet.getxMag());
			pstmt.setDouble(16, packet.getyMag());
			pstmt.setDouble(17, packet.getzMag());
			pstmt.setString(18, packet.getState());
			pstmt.executeUpdate();

			c.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (c != null)
				{
					c.close();
				}
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
	}

	// Read the data from the database
	public static ArrayList<DataPacket> readFromDatabase(String tableName)
	{
		String url = "jdbc:mysql://localhost:3306/aria_db";
		String username = "root";
		String password = "";
		Connection c = null;

		ArrayList<DataPacket> data = new ArrayList<>();

		try
		{
			c = DriverManager.getConnection(url, username, password);

			Statement stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery("select * from " + tableName);

			while (rs.next())
			{
				DataPacket packet = new DataPacket(rs.getDouble("time"), rs.getDouble("ultrasonic"), rs.getBoolean("L0"), rs.getBoolean("L1"), rs.getBoolean("L2"),
						rs.getDouble("xAccel"), rs.getDouble("yAccel"), rs.getDouble("zAccel"), rs.getDouble("xGyro"), rs.getDouble("yGyro"), rs.getDouble("zGyro"),
						rs.getDouble("xMag"), rs.getDouble("yMag"), rs.getDouble("zMag"), rs.getString("state"));

				data.add(packet);
			}

			System.out.println("Read from the database successfully.");

			c.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (c != null)
				{
					c.close();
				}
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}

		return data;
	}

	// Clear the database
	public static void clearDatabase(String tableName)
	{
		String url = "jdbc:mysql://localhost:3306/aria_db";
		String username = "root";
		String password = "";
		Connection c = null;

		try
		{
			String sql = "DELETE FROM " + tableName;

			c = DriverManager.getConnection(url, username, password);

			PreparedStatement pstmt = c.prepareStatement(sql);

			pstmt.executeUpdate();

			System.out.println("TABLE " + tableName + " has been cleared.");

			c.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if (c != null)
				{
					c.close();
				}
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
	}

	public static double getSensorValue()
	{
		return (double) (Math.random() * 30) + 1;
	}
}
