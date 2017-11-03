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
	public static boolean columnsRow = true;

	public DataController()
	{

	}

	// return an ArrayList of DataPackets from the file assume path is local
	public static ArrayList<DataPacket> readFromCSV(String filename)
	{
		String filePath = System.getProperty("user.dir") + "/" + filename + ".csv";
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
				//System.out.println("Data #" + count + " {Time:" + element[0] + ", Acceleration:" + element[1] + ", Tilt:" + element[2] + ", Direction:" + element[3] + ", UltraSonic:" + element[4] + ", L0: " + element[5] + ", L1:" + element[6] + ", L2:" + element[7] +"}");
				DataPacket packet = new DataPacket(Double.valueOf(element[0]), Double.valueOf(element[1]), Double.valueOf(element[2]), Double.valueOf(element[3]), Double.valueOf(element[4]), Boolean.parseBoolean(element[5]), Boolean.parseBoolean(element[6]), Boolean.parseBoolean(element[7]));
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
			fw = new FileWriter(filename + ".csv", true);
			cw = new CSVWriter(fw);

			if (columnsRow) {
				String columns = "Time,Acceleration,Tilt,Direction,UltraSonic,L0,L1,L2";
				cw.writeNext(columns.split(","));
				columnsRow = false;
			}

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
		CSVWriter cw = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filename + ".csv"), "UTF-8"));
		cw.flush();
		cw.close();

		System.out.println(filename + ".csv has beeen cleared successfully.");
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

			String sql = "insert into " + tableName + " (time, acceleration, tilt, direction, ultrasonic, L0, L1, L2) values (?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = c.prepareStatement(sql);

			pstmt.setDouble(1, packet.getTime());
			pstmt.setDouble(2, packet.getAcceleration());
			pstmt.setDouble(3, packet.getTilt());
			pstmt.setDouble(4, packet.getDirection());
			pstmt.setDouble(5, packet.getUltrasonic());
			pstmt.setBoolean(6, packet.isL0());
			pstmt.setBoolean(7, packet.isL1());
			pstmt.setBoolean(8, packet.isL2());
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
				DataPacket packet = new DataPacket(rs.getDouble("time"), rs.getDouble("acceleration"), rs.getDouble("tilt"), rs.getDouble("direction"),
						rs.getDouble("ultrasonic"), rs.getBoolean("L0"), rs.getBoolean("L1"), rs.getBoolean("L2"));

				System.out.println(packet.isL0() + "," + packet.isL1() + "," + packet.isL2());

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
