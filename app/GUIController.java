package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

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
	private int directionButtonLeftYLoc = 120;
	private int directionButtonSpacing = 50;
	private int manualButtonX = 0;
	private int manualButtonY = 500;
	private String currentState;
	private static int carSpeed = 0;
	private static int servoAngle = 0;
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
	private static JLabel carSpeedLabel = new JLabel("CarSpeed: " + carSpeed);
	private static JLabel servoAngleLabel = new JLabel("Servo Angle: " + carSpeed);
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
		this.setLayout(null);
		// INITIALIZE ANYTHING THAT NEEDS TO BE INTIALIZED
		init(width, height);

		// set Visible // and close on exit (To prevent memory leaks, etc )
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Desktop Application Module");
		this.setResizable(false);

		this.sioc = sioc;
		this.runner = runner;

		updateInfoSchedule();
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
		collectMode.setBounds(manualButtonX + 200, manualButtonY, 100, 100);
		trainingMode.setBounds(manualButtonX + 300, manualButtonY, 100, 100);

		// set bound of all labels
		carSpeedLabel.setBounds(manualButtonX, manualButtonY - 170, 200, 100);
		servoAngleLabel.setBounds(manualButtonX + 200, manualButtonY - 170, 200, 100);

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
		this.add(collectMode);
		this.add(trainingMode);

		// add action listeners to the buttons
		left.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Left";
				sioc.SerialWrite('l');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed left button. \n");
			}
		});

		right.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Right";
				sioc.SerialWrite('r');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed right button. \n");
			}
		});

		up.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Forward";
				sioc.SerialWrite('f');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed forward button. \n");
			}
		});

		down.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Back";
				sioc.SerialWrite('b');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed backward button. \n");
			}
		});

		stop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Stop";
				sioc.SerialWrite('s');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed stop button. \n");
			}
		});

		manualMode.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Manual Mode";
				sioc.SerialWrite('m');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Manual button. \n");
			}
		});

		autoPilotMode.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Autopilot Mode";
				sioc.SerialWrite('t');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Autopilot button. \n");
			}
		});

		decreaseCarSpeed.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Decreasing speed";
				sioc.SerialWrite('d');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Decrease Car Speed button. \n");
			}
		});

		increaseCarSpeed.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Increasing speed";
				sioc.SerialWrite('i');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Increase Car Speed button. \n");
			}
		});

		rotateServoLeft.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Rotate Servo Left";
				sioc.SerialWrite('1');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Rotate Servo Left button. \n");
			}
		});

		rotateServoRight.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				currentState = "Rotate Servo Right";
				sioc.SerialWrite('2');
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Rotate Servo Right button. \n");
			}
		});

		collectMode.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (collectMode.getText().equals("Collect Data")) {
					try {
						sioc.listenData();
						collectMode.setText("Stop");
						collectMode.setBackground(Color.RED);
						outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Collect Data button. \n");
					} catch (TooManyListenersException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					try
					{
						sioc.ignoreData();
						collectMode.setText("Collect Data");
						collectMode.setBackground(Color.WHITE);
						outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Stop button. \n");
					}
					catch (TooManyListenersException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		trainingMode.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				runner.runPyScript();
				outputTextArea.append(getCurrentLocalDateTimeStamp() + "User pressed Training and Testing button. \n");
			}
		});
	}

	public void initializeTextArea()
	{
		outputTextArea.setLineWrap(true);
		outputTextArea.setWrapStyleWord(true);
		outputTextArea.setVisible(true);
		outputTextArea.setEditable(false);
		DefaultCaret caret = (DefaultCaret) outputTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scroll.setBounds(0, 600, 910, 180);

		this.add(scroll);
	}

	public void initializePanels()
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
		initializeButtons();

		// init TextArea
		initializeTextArea();

		// init Panels
		initializePanels();
		// init
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

	public static void setCarSpeed(int carSpeed) {
		carSpeedLabel.setText("Car Speed: " + carSpeed);
	}

	public static void setServoAngle(int servoAngle) {
		servoAngleLabel.setText("Servo angle: " + servoAngle);
	}

	public String getCurrentState()
	{
		return currentState;
	}

	public static String getCurrentLocalDateTimeStamp() {
		String hour = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH"));

		int number = Integer.parseInt(hour);

		if (number > 13) {
			number = number - 12;
		}
		return number + LocalDateTime.now().format(DateTimeFormatter.ofPattern(":mm:ss:a: "));
	}

	public static void updateInfoSchedule()
	{
		// this creates a Timer schedule that will basically run every 60 milisecond starting at 1 second
		Timer timer = new Timer ();
		timer.schedule(new TimerTask(){
			@Override
			public void run()// randomly set pGrid
			{
				// pull messages from the IO and send them to the GUI
				setCarSpeed(sioc.getCarSpeed());
				setServoAngle(sioc.getServoAngle());
			}
		}, 1,100);
	}
}
