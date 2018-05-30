package app;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
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

public class DataController
{
	// Edit this string depending on the sensor readings that are sent from the Arduino Mega
	public static String columns = "leftUltrasonic,upperLeftUltrasonic, middleUltrasonic, upperRightUltrasonic,rightUltrasonic,xAccel,yAccel,zAccel,xGyro,yGyro,zGyro,xMag,yMag,zMag,servoAngle,state,previousState,previousState2,previousState3";

	public DataController()
	{

	}

	// return an ArrayList of DataPackets from the file assume path is local
	public static ArrayList<DataPacket> readFromCSV(String filename)
	{
		String filePath = System.getProperty("src/Files/" + filename + ".csv");
		CSVReader reader = null;
		ArrayList<DataPacket> data = new ArrayList<>();

		try
		{
			reader = new CSVReader(new InputStreamReader(new FileInputStream(filePath)));
			String[] element = null;

			// Assigns the row of columns so that the while loop skips this row
			element = reader.readNext();

			while ((element = reader.readNext()) != null)
			{
				int leftUltrasonic = Integer.parseInt(element[0]);
				int upperLeftUltrasonic = Integer.parseInt(element[1]);
				int middleUltrasonic = Integer.parseInt(element[2]);
				int upperRightUltrasonic = Integer.parseInt(element[3]);
				int rightUltrasonic = Integer.parseInt(element[4]);
				int xAccel = Integer.parseInt(element[5]);
				int yAccel = Integer.parseInt(element[6]);
				int zAccel = Integer.parseInt(element[7]);
				int xGyro = Integer.parseInt(element[8]);
				int yGyro = Integer.parseInt(element[9]);
				int zGyro = Integer.parseInt(element[10]);
				int xMag = Integer.parseInt(element[11]);
				int yMag = Integer.parseInt(element[12]);
				int zMag = Integer.parseInt(element[13]);
				int servoAngle = Integer.parseInt(element[14]);
				int state = Integer.parseInt(element[15]);
				int previousState = Integer.parseInt(element[16]);
				int previousState2 = Integer.parseInt(element[17]);
				int previousState3 = Integer.parseInt(element[18]);

				DataPacket packet = new DataPacket(leftUltrasonic, upperLeftUltrasonic, middleUltrasonic, upperRightUltrasonic, rightUltrasonic,
						xAccel, yAccel, zAccel, xGyro, yGyro, zGyro, xMag, yMag, zMag, servoAngle, state, previousState, previousState2, previousState3);

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
			File file = new File("src/Files/" + filename + ".csv");
			if (!file.exists()) {
				fw = new FileWriter("src/Files/" + filename + ".csv", true);
				cw = new CSVWriter(fw);
				cw.writeNext(columns.split(","));
				cw.writeNext(packet.toStringArray());
			}
			else {
				fw = new FileWriter("src/Files/" + filename + ".csv", true);
				cw = new CSVWriter(fw);
				cw.writeNext(packet.toStringArray());
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
		File file = new File("src/Files/" + filename + ".csv");

		if (file.exists() && file.isFile()) {
			CSVWriter cw = new CSVWriter(new OutputStreamWriter(new FileOutputStream("src/Files/" + filename + ".csv"), "UTF-8"));
			cw.flush();

			cw.writeNext(columns.split(","));

			cw.close();

			 GUIController.outputTextArea.append(filename + ".csv has been cleared successfully.\n");
		}
		else {
			 GUIController.outputTextArea.append(filename + ".csv does not exist.\n");
		}
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

			String sql = "insert into " + tableName + " (leftUltrasonic, upperLeftUltrasonic, middleUltrasonic, upperRightUltrasonic, rightUltrasonic, xAccel, yAccel, zAccel, xGyro, yGyro, zGyro, xMag, yMag, zMag, servoAngle, state, previousState, previousState2, previousState3) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = c.prepareStatement(sql);

			pstmt.setInt(1, packet.getLeftUltrasonic());
			pstmt.setInt(2, packet.getUpperLeftUltrasonic());
			pstmt.setInt(3, packet.getMiddleUltrasonic());
			pstmt.setInt(4, packet.getUpperRightUltrasonic());
			pstmt.setInt(5, packet.getRightUltrasonic());
			pstmt.setInt(6, packet.getxAccel());
			pstmt.setInt(7, packet.getyAccel());
			pstmt.setInt(8, packet.getzAccel());
			pstmt.setInt(9, packet.getxGyro());
			pstmt.setInt(10, packet.getyGyro());
			pstmt.setInt(11, packet.getzGyro());
			pstmt.setInt(12, packet.getxMag());
			pstmt.setInt(13, packet.getyMag());
			pstmt.setInt(14, packet.getzMag());
			pstmt.setInt(15,  packet.getServoAngle());
			pstmt.setInt(16, packet.getState());
			pstmt.setInt(17, packet.getPreviousState());
			pstmt.setInt(18, packet.getPreviousState2());
			pstmt.setInt(19, packet.getPreviousState3());
			pstmt.executeUpdate();

			c.close();
		}
		catch (Exception e)
		{
			GUIController.outputTextArea.append(e.toString() + "\n");
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
				GUIController.outputTextArea.append(e.toString() + "\n");
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
				DataPacket packet = new DataPacket(rs.getInt("leftUltrasonic"), rs.getInt("upperLeftUltrasonic"), rs.getInt("middleUltrasonic"), rs.getInt("upperRightUltrasonic"), rs.getInt("rightUltrasonic"),
						rs.getInt("xAccel"), rs.getInt("yAccel"), rs.getInt("zAccel"), rs.getInt("xGyro"), rs.getInt("yGyro"), rs.getInt("zGyro"),
						rs.getInt("xMag"), rs.getInt("yMag"), rs.getInt("zMag"), rs.getInt("servoAngle"), rs.getInt("state"), rs.getInt("previousState"), rs.getInt("previousState2"), rs.getInt("previousState3"));

				data.add(packet);
			}

			 GUIController.outputTextArea.append("Read from the database successfully.\n");

			c.close();
		}
		catch (Exception e)
		{
			GUIController.outputTextArea.append(e.toString() + "\n");
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
				GUIController.outputTextArea.append(e.toString() + "\n");
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

			GUIController.outputTextArea.append("TABLE " + tableName + " has been cleared.\n");

			c.close();
		}
		catch (Exception e)
		{
			GUIController.outputTextArea.append(e.toString() + "\n");
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
				GUIController.outputTextArea.append(e.toString() + "\n");
			}
		}
	}
}
