import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;
import java.util.stream.*;

public class SerialIOController implements SerialPortEventListener
{


SerialPort serialPort;
private BufferedReader input;
private OutputStream output;
private String currentRead[];
final int timeOut = 2000; // milliseconds
final int dataRate = 9600; // general Braud width

private static final String PORT_NAMES[] = { 
		"/dev/tty.usbserial-A9007UX1", // Mac OS X
                    "/dev/ttyACM0", // Raspberry Pi
		"/dev/ttyUSB0", // Linux
		"COM7", // Windows
};
public SerialIOController()
{
	// basic constructor
	this.initialize();
}


// Basically if there is Serial activity, this is run in Synchronization with
// whatever thread is currently running the port.
public synchronized void serialEvent(SerialPortEvent oEvent)
{
	// We have receieved information
	if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
	{
		try 
		{
			String inputLine=input.readLine();
			System.out.println(inputLine);
			output.write("Hello".getBytes());
		} 
		catch (Exception e)
		{
			System.err.println("The Byte was empty ( let's ignore this false data )");
		}
	}
}


private void initialize()
{
	CommPortIdentifier portId = null;
	Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

	//Cycle through the possible port names to see if we can match up.
	while (portEnum.hasMoreElements())
	{
		CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
		for (String portName : PORT_NAMES)
		{
			if (currPortId.getName().equals(portName)) 
			{
				portId = currPortId;
				break;
			}
		}
	}
	if (portId == null)
	{
		System.out.println("Could not find COM port.");
		return;
	}

	try 
	{
		// open serial port, and use class name for the appName.
		serialPort = (SerialPort) portId.open(this.getClass().getName(),timeOut);

		// set port parameters
		serialPort.setSerialPortParams(dataRate,
						SerialPort.DATABITS_8,
						SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

		// open the streams
		input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		output = serialPort.getOutputStream();

		// add event listeners
		serialPort.addEventListener(this);
		serialPort.notifyOnDataAvailable(true);
	} catch (Exception e) {
		System.err.println(e.toString());
	}
}

public synchronized void close() {
	if (serialPort != null) {
		serialPort.removeEventListener();
		serialPort.close();
	}
}

public synchronized void moveFoward()
{
	SerialWrite('F');
}
public synchronized void moveBackward()
{
	SerialWrite('B');
}
public synchronized void rotateRight()
{
	SerialWrite('R');
}
public synchronized void rotateLeft()
{
	SerialWrite('L');
}
public synchronized void enterRoveMode()
{
	SerialWrite('r');
}
public synchronized void enterManualControlMode()
{
	SerialWrite('m');
}
private synchronized void SerialWrite(char c) 
{
	try
	{
	output.write(c);// push the value through the stream
	}
	catch(Exception e)
	{
		System.err.println(e.getMessage());
	}
}
}// End Class

