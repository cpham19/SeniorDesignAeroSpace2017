package app;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.PrintWriter;
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
	public static String columns = "middleUltrasonic,leftUltrasonic,rightUltrasonic,backUltrasonic,L0,L1,L2,xAccel,yAccel,zAccel,xGyro,yGyro,zGyro,xMag,yMag,zMag,servoAngle,state";

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
				double middleUltrasonic = Double.parseDouble(element[0]);
				double leftUltrasonic = Double.parseDouble(element[1]);
				double rightUltrasonic = Double.parseDouble(element[2]);
				double backUltrasonic = Double.parseDouble(element[3]);
				int L0 = Integer.parseInt(element[4]);
				int L1 = Integer.parseInt(element[5]);
				int L2 = Integer.parseInt(element[6]);
				double xAccel = Double.parseDouble(element[7]);
				double yAccel = Double.parseDouble(element[8]);
				double zAccel = Double.parseDouble(element[9]);
				double xGyro = Double.parseDouble(element[10]);
				double yGyro = Double.parseDouble(element[11]);
				double zGyro = Double.parseDouble(element[12]);
				double xMag = Double.parseDouble(element[13]);
				double yMag = Double.parseDouble(element[14]);
				double zMag = Double.parseDouble(element[15]);
				int servoAngle = Integer.parseInt(element[16]);
				int state = Integer.parseInt(element[17]);

				DataPacket packet = new DataPacket(middleUltrasonic, leftUltrasonic, rightUltrasonic, backUltrasonic, L0, L1, L2, xAccel, yAccel, zAccel,
						  xGyro, yGyro, zGyro, xMag, yMag, zMag, servoAngle, state);

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

			String sql = "insert into " + tableName + " (time, middleUltrasonic, leftUltrasonic, rightUltrasonic, backUltrasonic, L0, L1, L2, xAccel, yAccel, zAccel, xGyro, yGyro, zGyro, xMag, yMag, zMag, servoAngle, state) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = c.prepareStatement(sql);

			pstmt.setDouble(1, packet.getMiddleUltrasonic());
			pstmt.setDouble(2, packet.getLeftUltrasonic());
			pstmt.setDouble(3, packet.getRightUltrasonic());
			pstmt.setDouble(4, packet.getBackUltrasonic());
			pstmt.setInt(5, packet.isL0());
			pstmt.setInt(6, packet.isL1());
			pstmt.setInt(7, packet.isL2());
			pstmt.setDouble(8, packet.getxAccel());
			pstmt.setDouble(9, packet.getyAccel());
			pstmt.setDouble(10, packet.getzAccel());
			pstmt.setDouble(11, packet.getxGyro());
			pstmt.setDouble(12, packet.getyGyro());
			pstmt.setDouble(13, packet.getzGyro());
			pstmt.setDouble(14, packet.getxMag());
			pstmt.setDouble(15, packet.getyMag());
			pstmt.setDouble(16, packet.getzMag());
			pstmt.setInt(17,  packet.getServoAngle());
			pstmt.setInt(18, packet.getState());
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
				DataPacket packet = new DataPacket(rs.getDouble("middleUltrasonic"), rs.getDouble("leftUltrasonic"), rs.getDouble("rightUltrasonic"), rs.getDouble("backUltrasonic"), rs.getInt("L0"), rs.getInt("L1"), rs.getInt("L2"),
						rs.getDouble("xAccel"), rs.getDouble("yAccel"), rs.getDouble("zAccel"), rs.getDouble("xGyro"), rs.getDouble("yGyro"), rs.getDouble("zGyro"),
						rs.getDouble("xMag"), rs.getDouble("yMag"), rs.getDouble("zMag"), rs.getInt("servoAngle"), rs.getInt("state"));

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
