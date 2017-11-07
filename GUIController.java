package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

import app.TextPanel;

public class GUIController extends JFrame
{
	// this is for future lookup so that we can identify Anomaly zones,
	private int AreaMapwidth = 500;
	private int AreaMapheight = 500;
	private int stepValue = 8;
	private int drawLength = 15;
	private int directionButtonWidth = 100;
	private int directionButtonHeight = 100;
	private int directionButtonLeftXLoc = 60;
	private int directionButtonLeftYLoc = 150;
	private int directionButtonSpacing = 50;
	private int manualButtonX = 0;
	private int manualButtonY = 570;
	private String currentState;
	private int carSpeed = 0;
	private int servoAngle = 0;
	SerialIOController sioc;

	// Area Map for value recording
	AreaMap grid = new AreaMap(AreaMapwidth, AreaMapheight, stepValue, drawLength);
	// text Panel ( For the printing of the incoming values )
	ArrayList<String> texts = new ArrayList<String>();
	TextPanel textDisplay = new TextPanel();
	// Map Panel ( The printing of the grid )
	MapPanel explorationMap = new MapPanel(grid, texts, this.getWidth(), this.getHeight());
	//Buttons
	private JButton up = new JButton("Forward");
	private JButton left = new JButton("Left");
	private JButton down = new JButton("Backward");
	private JButton right = new JButton("Right");
	private JButton stop = new JButton("Stop");
	private JLabel carSpeedLabel = new JLabel("CarSpeed: " + carSpeed);
	private JLabel servoAngleLabel = new JLabel("Servo Angle: " + carSpeed);
	private JButton decreaseCarSpeed = new JButton();
	private JButton increaseCarSpeed = new JButton();
	private JButton rotateServoLeft = new JButton();
	private JButton rotateServoRight = new JButton();
	private JButton autoPilotMode = new JButton("Autopilot");
	private JButton manualMode = new JButton("Manual");
	private JButton startRecording = new JButton("Record");
	private JButton stopRecording = new JButton("Stop Record");

	// public constructor
	public GUIController(int width, int height, SerialIOController sioc)
	{
		this.setLayout(null);
		// INITIALIZE ANYTHING THAT NEEDS TO BE INTIALIZED
		init(width, height);
		// Add Event Listeners

		// set Visible // and close on exit (To prevent memory leaks, etc )
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Desktop Application Module");
		this.setResizable(false);

		this.sioc = sioc;
	}
	//
	public void InitializeButtons()
	{
		// change the buttons color for looks
		left.setBackground(Color.WHITE);
		right.setBackground(Color.WHITE);
		up.setBackground(Color.WHITE);
		down.setBackground(Color.WHITE);
		stop.setBackground(Color.RED);
		autoPilotMode.setBackground(Color.WHITE);
		manualMode.setBackground(Color.WHITE);
		startRecording.setBackground(Color.WHITE);
		stopRecording.setBackground(Color.WHITE);

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
		left.setBounds(directionButtonLeftXLoc, directionButtonLeftYLoc, directionButtonWidth, directionButtonHeight);
		right.setBounds(directionButtonLeftXLoc + 200, left.getY(), directionButtonWidth, directionButtonHeight);
		int UDx = left.getX()+ directionButtonWidth + directionButtonSpacing - directionButtonHeight/2-6;
		int UpY = left.getY() - directionButtonSpacing - directionButtonWidth + directionButtonHeight/2;
		up.setBounds(UDx, UpY, directionButtonWidth + 10, directionButtonHeight);
		down.setBounds(UDx, UpY + 200, directionButtonWidth + 10, directionButtonHeight);
		stop.setBounds(directionButtonLeftXLoc + 100, directionButtonLeftYLoc, 100, 100);
		decreaseCarSpeed.setBounds(manualButtonX, manualButtonY - 100, 100, 100);
		increaseCarSpeed.setBounds(manualButtonX + 100, manualButtonY - 100, 100, 100);
		rotateServoLeft.setBounds(manualButtonX + 200, manualButtonY - 100, 100, 100);
		rotateServoRight.setBounds(manualButtonX + 300, manualButtonY - 100, 100, 100);
		manualMode.setBounds(manualButtonX, manualButtonY, 100, 100);
		autoPilotMode.setBounds(manualButtonX + 100, manualButtonY, 100, 100);
		startRecording.setBounds(manualButtonX + 200, manualButtonY, 100, 100);
		stopRecording.setBounds(manualButtonX + 300, manualButtonY, 100, 100);

		// set bound of all labels
		carSpeedLabel.setBounds(manualButtonX, manualButtonY - 200, 200, 100);
		servoAngleLabel.setBounds(manualButtonX + 200, manualButtonY - 200, 200, 100);

		// set font size of labels
		carSpeedLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		servoAngleLabel.setFont(new Font("Serif", Font.PLAIN, 20));

		// and add them to the Frame
		this.add(left);
		this.add(right);
		this.add(up);
		this.add(down);
		this.add(stop);
		this.add(carSpeedLabel);
		this.add(servoAngleLabel);
		this.add(decreaseCarSpeed);
		this.add(increaseCarSpeed);
		this.add(rotateServoLeft);
		this.add(rotateServoRight);
		this.add(manualMode);
		this.add(autoPilotMode);
		this.add(startRecording);
		this.add(stopRecording);

		// add action listeners to the buttons
		left.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Left";
				sioc.SerialWrite('l');
			}
		});

		right.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Right";
				sioc.SerialWrite('r');
			}
		});

		up.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Forward";
				sioc.SerialWrite('f');
			}
		});

		down.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Back";
				sioc.SerialWrite('b');
			}
		});

		stop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Stop";
				sioc.SerialWrite('s');
			}
		});

		manualMode.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Manual Mode";
				sioc.SerialWrite('m');
			}
		});

		autoPilotMode.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Autopilot Mode";
				sioc.SerialWrite('t');
			}
		});

		decreaseCarSpeed.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Decreasing speed";
				sioc.SerialWrite('d');
			}
		});

		increaseCarSpeed.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Increasing speed";
				sioc.SerialWrite('i');
			}
		});

		rotateServoLeft.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Rotate Servo Left";
				sioc.SerialWrite('1');
			}
		});

		rotateServoRight.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Rotate Servo Right";
				sioc.SerialWrite('2');
			}
		});

		startRecording.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Recording";
				sioc.initialize();
			}
		});

		stopRecording.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Stopped Recording";
				sioc.close();
			}
		});
	}

	public void InitializeTextFields()
	{
		// set bounds of all textFields
		// and add them to the Frame
	}

	public void InitializePanels()
	{
		// When adding things to the JFrame, you must set the bounds of it beforehand. ( Further
		// detail why would be nice, but not necessary.
		explorationMap.setBounds(0, 0, this.getWidth(),this.getHeight());
		this.add(explorationMap);

	}
	public void init(int width, int height)
	{
		// set size of self
		this.setSize(width,height);

		// init buttons
		InitializeButtons();

		// init TextFields

		// init Panels
		InitializePanels();
		// init
	}
	public void updateTextDisplay()
	{

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

	public void setCarSpeed(int carSpeed) {
		this.carSpeed = carSpeed;
		carSpeedLabel.setText("Car Speed: " + carSpeed);
	}

	public void setServoAngle(int servoAngle) {
		this.servoAngle = servoAngle;
		servoAngleLabel.setText("Servo angle: " + servoAngle);
	}

	public String getCurrentState()
	{
		return currentState;
	}

}
