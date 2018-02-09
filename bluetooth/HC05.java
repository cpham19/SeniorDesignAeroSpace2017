package bluetooth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

public class HC05 {
	
	boolean scanFinished = false;
	String hc05Url ="btspp://00140306152C:1;authenticate=false;encrypt=false;master=false"; //Replace this with your bluetooth URL
	
	public static void main(String[] args) {
		try {
			new HC05().go();
		} catch (Exception ex) {
			Logger.getLogger(HC05.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void go() throws Exception {
		StreamConnection streamConnection = (StreamConnection) Connector.open(hc05Url);
		
		OutputStream os = streamConnection.openOutputStream();
		BufferedReader input = new BufferedReader(new InputStreamReader(streamConnection.openInputStream()));
		
		Thread.sleep(3000);
		
		int i = 0;
		while (i < 10) {
			String[] line = input.readLine().split(",");
			System.out.println("received " + line[0] + "," + line[1] + "," + line[2] + "," + line[3] + "," + line[4] + "," + line[5] + "," + line[6] + "," + line[7] + "," + line[8] + "," + line[9] + "," + line[10] + "," + line[11] + "," + line[12] + "," + line[13] + "," + line[14] + "," + line[15]);
			Thread.sleep(400);
			i++;
		}
		
		os.write("f".getBytes());
		os.write("s".getBytes());
		 
		input.close();
		os.close();
		streamConnection.close();
	}
}