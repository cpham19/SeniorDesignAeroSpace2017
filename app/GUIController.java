package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

public class GUIController extends JFrame
{
	// this is for future lookup so that we can identify Anomaly zones,
	private int AreaMapwidth = 500;
	private int AreaMapheight = 500;
	private int stepValue = 8;
	private int drawLength = 15;
	private int buttonWidth = 100;
	private int buttonHeight = 100;
	private int directionButtonLeftXLoc = 60;
	private int directionButtonLeftYLoc = 120;
	private int directionButtonSpacing = 50;
	private int manualButtonX = 0;
	private int manualButtonY = 500;
	private String currentState;
	static SerialIOController sioc;
	PyScriptRunner runner;

	// Area Map for value recording
	AreaMap grid = new AreaMap(AreaMapwidth, AreaMapheight, stepValue, drawLength);
	// Map Panel ( The printing of the grid )
	MapPanel explorationMap = new MapPanel(grid, this.getWidth(), this.getHeight());
	//Buttons
	private JButton up = new JButton("Forward");
	private JButton left = new JButton("Left");
	private JButton down = new JButton("Backward");
	private JButton right = new JButton("Right");
	private JButton stop = new JButton("Stop");
	public static JTextField middleUltraSonicTF = new JTextField("M Ult. Sonic: " + 0);
	public static JTextField leftUltraSonicTF = new JTextField("L Ult. Sonic: " + 0);
	public static JTextField rightUltraSonicTF = new JTextField("R Ult. Sonic: " + 0);
	public static JTextField backUltraSonicTF = new JTextField("B Ult. Sonic: " + 0);
	public static JTextField L0TF = new JTextField("L0: " + 0);
	public static JTextField L1TF = new JTextField("L1: " + 0);
	public static JTextField L2TF = new JTextField("L2: " + 0);
	public static JTextField xAccelTF = new JTextField("xAccel: " + 0);
	public static JTextField yAccelTF = new JTextField("yAccel: " + 0);
	public static JTextField zAccelTF = new JTextField("zAccel: " + 0);
	public static JTextField xGyroTF = new JTextField("xGyro: " + 0);
	public static JTextField yGyroTF = new JTextField("yGyro: " + 0);
	public static JTextField zGyroTF = new JTextField("zGyro: " + 0);
	public static JTextField xMagTF = new JTextField("xMag: " + 0);
	public static JTextField yMagTF = new JTextField("yMag: " + 0);
	public static JTextField zMagTF = new JTextField("zMag: " + 0);
	public static JTextField carSpeedTF = new JTextField("CarSpeed: " + 0);
	public static JTextField servoAngleTF = new JTextField("Servo Angle: " + 0);
	public static JTextField stateTF = new JTextField("State: None");
	private JButton clearCSVButton = new JButton("Clear CSV");
	private JButton decreaseCarSpeed = new JButton();
	private JButton increaseCarSpeed = new JButton();
	private JButton rotateServoLeft = new JButton();
	private JButton rotateServoRight = new JButton();
	private JButton autoPilotMode = new JButton("Autopilot");
	private JButton manualMode = new JButton("Manual");
	private JButton collectMode = new JButton("Collect Data");
	private JButton trainingMode = new JButton();
	public static JTextArea outputTextArea = new JTextArea();
	private JScrollPane scroll = new JScrollPane(outputTextArea);

	// public constructor
	public GUIController(int width, int height, SerialIOController sioc, PyScriptRunner runner)
	{
		// set size of self
		this.setSize(width,height);
		this.setLayout(null);
		// INITIALIZE ANYTHING THAT NEEDS TO BE INTIALIZED
		initialize();

		// set Visible // and close on exit (To prevent memory leaks, etc )
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Desktop Application Module");
		this.setResizable(false);

		this.sioc = sioc;
		this.runner = runner;
	}
	//
	public void initializeButtons()
	{
		// change the buttons color for looks
		left.setBackground(Color.WHITE);
		right.setBackground(Color.WHITE);
		up.setBackground(Color.WHITE);
		down.setBackground(Color.WHITE);
		stop.setBackground(Color.RED);
		autoPilotMode.setBackground(Color.WHITE);
		manualMode.setBackground(Color.WHITE);
		collectMode.setFont(new Font("Dialog", Font.PLAIN, 10));
		collectMode.setBackground(Color.WHITE);
		trainingMode.setBackground(Color.WHITE);
		clearCSVButton.setBackground(Color.WHITE);

		decreaseCarSpeed.setLayout(new BorderLayout());
		decreaseCarSpeed.add(BorderLayout.NORTH, new JLabel("Decrease"));
		decreaseCarSpeed.add(BorderLayout.CENTER, new JLabel("Car"));
		decreaseCarSpeed.add(BorderLayout.SOUTH, new JLabel("Speed"));
		decreaseCarSpeed.setBackground(Color.WHITE);
		increaseCarSpeed.setLayout(new BorderLayout());
		increaseCarSpeed.add(BorderLayout.NORTH, new JLabel("Increase"));
		increaseCarSpeed.add(BorderLayout.CENTER, new JLabel("Car"));
		increaseCarSpeed.add(BorderLayout.SOUTH, new JLabel("Speed"));
		increaseCarSpeed.setBackground(Color.WHITE);

		trainingMode.setLayout(new BorderLayout());
		trainingMode.add(BorderLayout.NORTH, new JLabel("Training"));
		trainingMode.add(BorderLayout.CENTER, new JLabel("Testing"));
		trainingMode.setBackground(Color.WHITE);

		rotateServoLeft.setLayout(new BorderLayout());
		rotateServoLeft.add(BorderLayout.NORTH, new JLabel("Rotate"));
		rotateServoLeft.add(BorderLayout.CENTER, new JLabel("Servo"));
		rotateServoLeft.add(BorderLayout.SOUTH, new JLabel("Left"));
		rotateServoLeft.setBackground(Color.WHITE);

		rotateServoRight.setLayout(new BorderLayout());
		rotateServoRight.add(BorderLayout.NORTH, new JLabel("Rotate"));
		rotateServoRight.add(BorderLayout.CENTER, new JLabel("Servo"));
		rotateServoRight.add(BorderLayout.SOUTH, new JLabel("Right"));
		rotateServoRight.setBackground(Color.WHITE);

		// set bounds of all Buttons
		left.setBounds(directionButtonLeftXLoc, directionButtonLeftYLoc, buttonWidth, buttonHeight);
		right.setBounds(directionButtonLeftXLoc + 200, left.getY(), buttonWidth, buttonHeight);
		int UDx = left.getX()+ buttonWidth + directionButtonSpacing - buttonHeight/2-6;
		int UpY = left.getY() - directionButtonSpacing - buttonWidth + buttonHeight/2;
		up.setBounds(UDx, UpY, buttonWidth + 10, buttonHeight);
		down.setBounds(UDx, UpY + 200, buttonWidth + 10, buttonHeight);
		stop.setBounds(directionButtonLeftXLoc + 100, directionButtonLeftYLoc, buttonWidth, buttonHeight);
		decreaseCarSpeed.setBounds(manualButtonX, manualButtonY - 100, buttonWidth, buttonHeight);
		increaseCarSpeed.setBounds(manualButtonX + 100, manualButtonY - 100, buttonWidth, buttonHeight);
		rotateServoLeft.setBounds(manualButtonX + 200, manualButtonY - 100, buttonWidth, buttonHeight);
		rotateServoRight.setBounds(manualButtonX + 300, manualButtonY - 100, buttonWidth, buttonHeight);
		clearCSVButton.setBounds(manualButtonX + 300, manualButtonY - 150, buttonWidth, buttonHeight - 50);
		manualMode.setBounds(manualButtonX, manualButtonY, buttonWidth, buttonHeight);
		manualMode.setVisible(false);
		autoPilotMode.setBounds(manualButtonX + 100, manualButtonY, buttonWidth, buttonHeight);
		collectMode.setBounds(manualButtonX + 200, manualButtonY, buttonWidth, buttonHeight);
		trainingMode.setBounds(manualButtonX + 300, manualButtonY, buttonWidth, buttonHeight);

		// set bound of all textfields
		stateTF.setBounds(decreaseCarSpeed.getX() + 400, decreaseCarSpeed.getY(), 100, 50);
		carSpeedTF.setBounds(decreaseCarSpeed.getX() + 500, decreaseCarSpeed.getY(), 100, 50);
		servoAngleTF.setBounds(decreaseCarSpeed.getX() + 600, decreaseCarSpeed.getY(), 100, 50);
		middleUltraSonicTF.setBounds(decreaseCarSpeed.getX() + 700, decreaseCarSpeed.getY(), 100, 50);
		leftUltraSonicTF.setBounds(decreaseCarSpeed.getX() + 800, decreaseCarSpeed.getY(), 100, 50);
		L0TF.setBounds(decreaseCarSpeed.getX() + 400, decreaseCarSpeed.getY() + 50, 100, 50);
		L1TF.setBounds(decreaseCarSpeed.getX() + 500, decreaseCarSpeed.getY() + 50, 100, 50);
		L2TF.setBounds(decreaseCarSpeed.getX() + 600, decreaseCarSpeed.getY() + 50, 100, 50);
		xAccelTF.setBounds(decreaseCarSpeed.getX() + 700, decreaseCarSpeed.getY() + 50, 100, 50);
		rightUltraSonicTF.setBounds(decreaseCarSpeed.getX() + 800, decreaseCarSpeed.getY() + 50, 100, 50);
		yAccelTF.setBounds(decreaseCarSpeed.getX() + 400, decreaseCarSpeed.getY() + 100, 100, 50);
		zAccelTF.setBounds(decreaseCarSpeed.getX() + 500, decreaseCarSpeed.getY() + 100, 100, 50);
		xGyroTF.setBounds(decreaseCarSpeed.getX() + 600, decreaseCarSpeed.getY() + 100, 100, 50);
		yGyroTF.setBounds(decreaseCarSpeed.getX() + 700, decreaseCarSpeed.getY() + 100, 100, 50);
		backUltraSonicTF.setBounds(decreaseCarSpeed.getX() + 800, decreaseCarSpeed.getY() + 100, 100, 50);
		zGyroTF.setBounds(decreaseCarSpeed.getX() + 400, decreaseCarSpeed.getY() + 150, 100, 50);
		xMagTF.setBounds(decreaseCarSpeed.getX() + 500, decreaseCarSpeed.getY() + 150, 100, 50);
		yMagTF.setBounds(decreaseCarSpeed.getX() + 600, decreaseCarSpeed.getY() + 150, 100, 50);
		zMagTF.setBounds(decreaseCarSpeed.getX() + 700, decreaseCarSpeed.getY() + 150, 100, 50);


		// set editables of all textfields
		carSpeedTF.setEditable(false);
		servoAngleTF.setEditable(false);
		stateTF.setEditable(false);
		middleUltraSonicTF.setEditable(false);
		leftUltraSonicTF.setEditable(false);
		rightUltraSonicTF.setEditable(false);
		backUltraSonicTF.setEditable(false);
		L0TF.setEditable(false);
		L1TF.setEditable(false);
		L2TF.setEditable(false);
		xAccelTF.setEditable(false);
		yAccelTF.setEditable(false);
		zAccelTF.setEditable(false);
		xGyroTF.setEditable(false);
		yGyroTF.setEditable(false);
		zGyroTF.setEditable(false);
		xMagTF.setEditable(false);
		yMagTF.setEditable(false);
		zMagTF.setEditable(false);

		// set font size of textfields
		carSpeedTF.setFont(new Font("Serif", Font.PLAIN, 12));
		servoAngleTF.setFont(new Font("Serif", Font.PLAIN, 12));
		stateTF.setFont(new Font("Serif", Font.PLAIN, 12));
		middleUltraSonicTF.setFont(new Font("Serif", Font.PLAIN, 12));
		leftUltraSonicTF.setFont(new Font("Serif", Font.PLAIN, 12));
		rightUltraSonicTF.setFont(new Font("Serif", Font.PLAIN, 12));
		backUltraSonicTF.setFont(new Font("Serif", Font.PLAIN, 12));
		L0TF.setFont(new Font("Serif", Font.PLAIN, 12));
		L1TF.setFont(new Font("Serif", Font.PLAIN, 12));
		L2TF.setFont(new Font("Serif", Font.PLAIN, 12));
		xAccelTF.setFont(new Font("Serif", Font.PLAIN, 12));
		yAccelTF.setFont(new Font("Serif", Font.PLAIN, 12));
		zAccelTF.setFont(new Font("Serif", Font.PLAIN, 12));
		xGyroTF.setFont(new Font("Serif", Font.PLAIN, 12));
		yGyroTF.setFont(new Font("Serif", Font.PLAIN, 12));
		zGyroTF.setFont(new Font("Serif", Font.PLAIN, 12));
		xMagTF.setFont(new Font("Serif", Font.PLAIN, 12));
		yMagTF.setFont(new Font("Serif", Font.PLAIN, 12));
		zMagTF.setFont(new Font("Serif", Font.PLAIN, 12));

		// and add them to the Frame
		this.add(left);
		this.add(right);
		this.add(up);
		this.add(down);
		this.add(stop);
		this.add(carSpeedTF);
		this.add(servoAngleTF);
		this.add(stateTF);
		this.add(middleUltraSonicTF);
		this.add(leftUltraSonicTF);
		this.add(rightUltraSonicTF);
		this.add(backUltraSonicTF);
		this.add(L0TF);
		this.add(L1TF);
		this.add(L2TF);
		this.add(xAccelTF);
		this.add(yAccelTF);
		this.add(zAccelTF);
		this.add(xGyroTF);
		this.add(yGyroTF);
		this.add(zGyroTF);
		this.add(xMagTF);
		this.add(yMagTF);
		this.add(zMagTF);
		this.add(decreaseCarSpeed);
		this.add(increaseCarSpeed);
		this.add(rotateServoLeft);
		this.add(rotateServoRight);
		this.add(clearCSVButton);
		this.add(manualMode);
		this.add(autoPilotMode);
		this.add(collectMode);
		this.add(trainingMode);

		// add action listeners to the buttons
		left.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Left";
				sioc.SerialWrite('l');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed left button.\n");
			}
		});

		right.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Right";
				sioc.SerialWrite('r');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed right button.\n");
			}
		});

		up.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Forward";
				sioc.SerialWrite('f');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed forward button.\n");
			}
		});

		down.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Backward";
				sioc.SerialWrite('b');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed backward button.\n");
			}
		});

		stop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Stop";
				sioc.SerialWrite('s');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed stop button.\n");
			}
		});

		manualMode.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				up.setVisible(true);
				up.setEnabled(true);
				down.setVisible(true);
				down.setEnabled(true);
				left.setVisible(true);
				left.setEnabled(true);
				right.setVisible(true);
				right.setEnabled(true);
				stop.setVisible(true);
				stop.setEnabled(true);
				increaseCarSpeed.setVisible(true);
				increaseCarSpeed.setEnabled(true);
				decreaseCarSpeed.setVisible(true);
				decreaseCarSpeed.setEnabled(true);
				rotateServoLeft.setVisible(true);
				rotateServoLeft.setEnabled(true);
				rotateServoRight.setVisible(true);
				rotateServoRight.setEnabled(true);
				autoPilotMode.setVisible(true);
				autoPilotMode.setEnabled(true);
				collectMode.setVisible(true);
				collectMode.setEnabled(true);
				trainingMode.setVisible(true);
				trainingMode.setEnabled(true);
				manualMode.setVisible(false);
				sioc.initialize();
				sioc.SerialWrite('s');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Manual button.\n");
			}
		});

		autoPilotMode.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (collectMode.getText().equals("Stop")) {
					JOptionPane.showMessageDialog(null, "Stop collecting data if you want to use Autopilot mode.");
				}
				else {
					sioc.close();
					JOptionPane.showMessageDialog(null, "Unplug the SerialPort. Plug in the bluetooth module. Reset the car. Press okay when you're done.");
					//runner.runAutomaticScript();
					manualMode.setVisible(true);
					up.setVisible(false);
					up.setEnabled(false);
					down.setVisible(false);
					down.setEnabled(false);
					left.setVisible(false);
					left.setEnabled(false);
					right.setVisible(false);
					right.setEnabled(false);
					stop.setVisible(false);
					stop.setEnabled(false);
					increaseCarSpeed.setVisible(false);
					increaseCarSpeed.setEnabled(false);
					decreaseCarSpeed.setVisible(false);
					decreaseCarSpeed.setEnabled(false);
					rotateServoLeft.setVisible(false);
					rotateServoLeft.setEnabled(false);
					rotateServoRight.setVisible(false);
					rotateServoRight.setEnabled(false);
					autoPilotMode.setVisible(false);
					autoPilotMode.setEnabled(false);
					collectMode.setVisible(false);
					collectMode.setEnabled(false);
					trainingMode.setVisible(false);
					trainingMode.setEnabled(false);
					outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Autopilot button.\n");
					JOptionPane.showMessageDialog(null, "Plug the SerialPort. Unplug in the bluetooth module. Reset the car. Press okay when you're done.");
				}
			}
		});

		decreaseCarSpeed.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				sioc.SerialWrite('d');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Decrease Car Speed button.\n");
			}
		});

		increaseCarSpeed.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				sioc.SerialWrite('i');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Increase Car Speed button.\n");
			}
		});

		rotateServoLeft.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				sioc.SerialWrite('1');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Rotate Servo Left button.\n");
			}
		});

		rotateServoRight.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				sioc.SerialWrite('2');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Rotate Servo Right button.\n");
			}
		});

		clearCSVButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				try {
					sioc.dc.clearCSV(sioc.csvName);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Clear CSV button.\n");
			}
		});

		collectMode.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (collectMode.getText().equals("Collect Data")) {
					outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Collect Data button.\n");
					collectMode.setText("Stop");
					collectMode.setBackground(Color.RED);
					sioc.collectData = true;
				}
				else {
					outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Stop button.\n");
					collectMode.setText("Collect Data");
					collectMode.setBackground(Color.WHITE);
					sioc.collectData = false;
				}
			}
		});

		trainingMode.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (collectMode.getText().equals("Stop")) {
					JOptionPane.showMessageDialog(null, "Stop collecting data if you want to use Training mode.");
				}
				else {
					outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Training and Testing button.\n");
					runner.runTrainingScript();
				}
			}
		});
	}

	public void initializeTextArea()
	{
		outputTextArea.setLineWrap(true);
		outputTextArea.setWrapStyleWord(true);
		outputTextArea.setVisible(true);
		outputTextArea.setEditable(false);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
//            public void adjustmentValueChanged(AdjustmentEvent e) {
//                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
//            }
//        });


		scroll.setBounds(0, 600, 910, 180);
		this.add(scroll);
		outputTextArea.append("Please give the car three seconds to initialize.\n");
	}

	public void initializePanels()
	{
		// When adding things to the JFrame, you must set the bounds of it beforehand. ( Further
		// detail why would be nice, but not necessary.
		explorationMap.setBounds(0, 0, this.getWidth(),this.getHeight());
		this.add(explorationMap);
	}

	public void initialize()
	{
		// initialize buttons
		initializeButtons();

		// initialize TextArea
		initializeTextArea();

		// initialize Panels
		initializePanels();
	}

	/*
	 * Taking in a car vector ( Which holds REAL data information regarding position
	 */
	public void updateGrid(CarVector currentPosition)
	{

	}
	public void setGrid(AreaMap map)
	{
		this.grid = map;
	}
	// To be called by the MAIN controller
	/*
	 *  -This method will return the current message that needs to be received by the Arduino
	 *  -This method should be invoked by the main controller to accordingly send out information
	 * regarding the current button that has been chosen
	 */

	public static String getCurrentLocalDateTimeStamp() {
		String hour = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH"));

		int number = Integer.parseInt(hour);

		if (number > 13) {
			number = number - 12;
		}
		return number + LocalDateTime.now().format(DateTimeFormatter.ofPattern(":mm:ss:a: "));
	}
}
