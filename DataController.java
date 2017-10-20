import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataController
{
	public static void writeToDatabase(DataPacket packet)
	{
		String url = "jdbc:mysql://localhost:3306/aria_db";
		String username = "root";
		String password = "";
		Connection c = null;
	
		try
		{
			String sql = "insert into ARIA_DATA (time, acceleration, tilt, direction, ultrasonic, L0, L1, L2) values (?, ?, ?, ?, ?, ?, ?, ?)"; 
			
			c = DriverManager.getConnection(url, username, password);
			
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from ARIA_DATA");
			
			while(rs.next())
			{
				System.out.println(rs.getDouble(1) + ", " + rs.getDouble(2) + ", " + rs.getDouble(3) + ", " +  rs.getDouble(4) + ", " + rs.getDouble(5) + ", " + rs.getBoolean(6) + ", " + rs.getBoolean(7) + ", " + rs.getBoolean(8));
			}
			
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
}
