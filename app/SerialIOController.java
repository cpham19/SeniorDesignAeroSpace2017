package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.util.Enumeration;

import app.DataPacket;

public class SerialIOController implements SerialPortEventListener {
	SerialPort serialPort;
	public static String csvName = "Sample";
	private BufferedReader input;
	private OutputStream output;
	final int timeOut = 2000; // milliseconds
	final int dataRate = 115200; // general Braud width
	public DataController dc = new DataController();
	private int count = 1;
	public static boolean collectData = false;

	private static final String PORT_NAMES[] = {
			"/dev/tty.usbserial-A9007UX1", // Mac
			"/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM5", // Windows
	};

	public SerialIOController() {
		initialize();
	}

	// Basically if there is Serial activity, this is run in Synchronization
	// with
	// whatever thread is currently running the port.
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		// We have receieved information
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				// This contains the String messages from the Arduino Board
				// (Ultrasonic, L0, L1, and L2 readings as of now)
				// Ultrasonic, L0, L1, L2
				String[] inputLine = input.readLine().split(",");

				// Parsing String messages (Sensor data) to double and Booleans
				int leftUltrasonic = Integer.parseInt(inputLine[0]);
				int upperLeftUltrasonic = Integer.parseInt(inputLine[1]);
				int middleUltrasonic = Integer.parseInt(inputLine[2]);
				int upperRightUltrasonic = Integer.parseInt(inputLine[3]);
				int rightUltrasonic = Integer.parseInt(inputLine[4]);
				int db1 = Integer.parseInt(inputLine[5]);
				int db2 = Integer.parseInt(inputLine[6]);
				int db3 = Integer.parseInt(inputLine[7]);
				int xAccel = Integer.parseInt(inputLine[8]);
				int yAccel = Integer.parseInt(inputLine[9]);
				int zAccel = Integer.parseInt(inputLine[10]);
				int xGyro = Integer.parseInt(inputLine[11]);
				int yGyro = Integer.parseInt(inputLine[12]);
				int zGyro = Integer.parseInt(inputLine[13]);
				int xMag = Integer.parseInt(inputLine[14]);
				int yMag = Integer.parseInt(inputLine[15]);
				int zMag = Integer.parseInt(inputLine[16]);
				int carSpeed = Integer.parseInt(inputLine[17]);
				int servoAngle = Integer.parseInt(inputLine[18]);
				int state = Integer.parseInt(inputLine[19]);

				DataPacket packet = new DataPacket(leftUltrasonic, upperLeftUltrasonic, middleUltrasonic, upperRightUltrasonic, rightUltrasonic,
						db1, db2, db3, xAccel, yAccel, zAccel, xGyro, yGyro, zGyro, xMag, yMag, zMag, servoAngle, state);

				GUIController.leftUltrasonicTF.setText("L UltSonic:" + leftUltrasonic);
				GUIController.upperLeftUltrasonicTF.setText("UpL UltSonic:" + upperLeftUltrasonic);
				GUIController.middleUltrasonicTF.setText("M UltSonic:" + middleUltrasonic);
				GUIController.upperRightUltrasonicTF.setText("UpR UltSonic:" + upperRightUltrasonic);
				GUIController.rightUltrasonicTF.setText("R UltSonic:" + rightUltrasonic);
				GUIController.db1TF.setText("DB #1: " + db1);
				GUIController.db2TF.setText("DB #2: " + db2);
				GUIController.db3TF.setText("DB #3: " + db3);
				GUIController.xAccelTF.setText("xAccel: " + xAccel);
				GUIController.yAccelTF.setText("yAccel: " + yAccel);
				GUIController.zAccelTF.setText("zAccel: " + zAccel);
				GUIController.xGyroTF.setText("xGyro: " + xGyro);
				GUIController.yGyroTF.setText("yGyro: " + yGyro);
				GUIController.zGyroTF.setText("zGyro: " + zGyro);
				GUIController.xMagTF.setText("xMag: " + xMag);
				GUIController.yMagTF.setText("yMag: " + yMag);
				GUIController.zMagTF.setText("zMag: " + zMag);
				GUIController.carSpeedTF.setText("Car Speed: " + carSpeed);
				GUIController.servoAngleTF.setText("Servo Angle: " + servoAngle);
				GUIController.stateTF.setText("State: " + state);

				// Add DataPacket to CSV file

				if (collectData == true && (state != 3 && state != 4)) {
					dc.writeToCSV(csvName, packet);
					// dc.writeToDatabase("aria_data", packet);
					GUIController.outputTextArea.append(count + ") " + packet.toString() + "\n");
					count++;
				}
			} catch (Exception e) {
				// System.err.println("The Byte was empty ( let's ignore this
				// false data )");
			}
		}
	}

	public void initialize() {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// Cycle through the possible port names to see if we can match up.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			GUIController.outputTextArea.append("Could not find COM port.\n");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(), timeOut);

			// set port parameters
			serialPort.setSerialPortParams(dataRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			GUIController.outputTextArea.append("The COM Port is being used by another application!\n");
		}
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public synchronized void SerialWrite(char c) {
		try {
			output.write(c);// push the value through the stream
		} catch (Exception e) {
			GUIController.outputTextArea.append(e.getMessage());
		}
	}
}
