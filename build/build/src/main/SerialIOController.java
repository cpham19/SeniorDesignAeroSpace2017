package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.util.Enumeration;
import java.util.TooManyListenersException;

import main.model.DataPacket;

public class SerialIOController implements SerialPortEventListener
{
	SerialPort serialPort;
	private BufferedReader input;
	private OutputStream output;
	final int timeOut = 2000; // milliseconds
	final int dataRate = 115200; // general Braud width
	final long startingTime = System.currentTimeMillis();
	DataController dc = new DataController();
	private int count = 0;
	private int carSpeed = 0;
	private int servoAngle = 0;
	DataPacket oldPacket;
	boolean firstRecord = true;

	private static final String PORT_NAMES[] = {
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
			"/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM5", // Windows
	};

	public SerialIOController()
	{
		initialize();
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
				// This contains the String messages from the Arduino Board (Ultrasonic, L0, L1, and L2 readings as of now)
				// Ultrasonic, L0, L1, L2
				String[] inputLine = input.readLine().split(",");

				// Parsing String messages (Sensor data) to double and Booleans
				double ultrasonic = Double.parseDouble(inputLine[0]);
				boolean L0 = convertToBoolean(Integer.parseInt(inputLine[1]));
				boolean L1 = convertToBoolean(Integer.parseInt(inputLine[2]));
				boolean L2 = convertToBoolean(Integer.parseInt(inputLine[3]));
				double xAccel = Double.parseDouble(inputLine[4]);
				double yAccel = Double.parseDouble(inputLine[5]);
				double zAccel = Double.parseDouble(inputLine[6]);
				double xGyro = Double.parseDouble(inputLine[7]);
				double yGyro = Double.parseDouble(inputLine[8]);
				double zGyro = Double.parseDouble(inputLine[9]);
				double xMag = Double.parseDouble(inputLine[10]);
				double yMag = Double.parseDouble(inputLine[11]);
				double zMag = Double.parseDouble(inputLine[12]);
				String state = inputLine[13];
				carSpeed = Integer.parseInt(inputLine[14]);
				servoAngle = Integer.parseInt(inputLine[15]);

				// Format Time
				double time = (double) (System.currentTimeMillis() - startingTime) / 1000;

//				if (!firstRecord) {
//					dc.writeToCSV("Sample", oldPacket);
//				}

				DataPacket packet = new DataPacket(time, ultrasonic, L0, L1, L2, xAccel, yAccel, zAccel,
						xGyro, yGyro, zGyro, xMag, yMag, zMag, servoAngle, state);

				// Add DataPacket to CSV file
				dc.writeToCSV("Sample", packet);

//				oldPacket = packet;
//				if (firstRecord) {
//					firstRecord = false;
//				}
				//dc.writeToDatabase("aria_data", packet);

				count++;
			}
			catch (Exception e)
			{
				// System.err.println("The Byte was empty ( let's ignore this false data )");
			}
		}
	}

	public boolean convertToBoolean(int number) {
		if (number == 1) {
			return true;
		}
		else {
			return false;
		}
	}


	public void initialize()
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
		}
		catch (Exception e)
		{
			System.err.println(e.toString());
		}
	}

	public synchronized void listenData() throws TooManyListenersException {
		if (serialPort != null) {
			serialPort.notifyOnDataAvailable(true);
		}
	}

	public synchronized void ignoreData() throws TooManyListenersException {
		if (serialPort != null) {
			serialPort.notifyOnDataAvailable(false);
		}
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public int getCarSpeed() {
		return carSpeed;
	}

	public int getServoAngle() {
		return servoAngle;
	}

	public synchronized void SerialWrite(char c)
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
}

