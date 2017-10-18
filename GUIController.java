import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.Border;

public class GUIController extends JFrame
{
	// this is for future lookup so that we can identify Anomaly zones, 
	private int AreaMapwidth = 500;
	private int AreaMapheight = 500;
	private int stepValue = 8;
	private int drawLength = 15;
	private int directionButtonWidth = 80;
	private int directionButtonHeight = 35;
	private int directionButtonLeftXLoc = 73;
	private int directionButtonLeftYLoc = 250;
	private int directionButtonSpacing = 50;
	private char currentMessage = 'N';
	// Area Map for value recording
	AreaMap grid = new AreaMap(AreaMapwidth, AreaMapheight, stepValue, drawLength);
	// text Panel ( For the printing of the incoming values )
	ArrayList<String> texts = new ArrayList<String>();
	TextPanel textDisplay = new TextPanel();
	// Map Panel ( The printing of the grid )
	MapPanel explorationMap = new MapPanel(grid, this.getWidth(), this.getHeight());
	//Buttons
	private JButton up = new JButton("U");
	private JButton left= new JButton("L-R");
	private JButton down= new JButton("D");
	private JButton right= new JButton("R-R");
	private JButton roveMode= new JButton("Rove");
	private JButton manualMode= new JButton("Manual");
	
	
	// public constructor
	public GUIController(int width, int height)
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
	}
	// 
	public void InitializeButtons()
	{
	// change the buttons color for looks
		left.setBackground(Color.white);
		right.setBackground(Color.white);
		up.setBackground(Color.white);
		down.setBackground(Color.white);
		roveMode.setBackground(Color.white);
		manualMode.setBackground(Color.white);
		
		
	// set bounds of all Buttons
		left.setBounds(directionButtonLeftXLoc, directionButtonLeftYLoc, directionButtonWidth, directionButtonHeight);
		right.setBounds(left.getX()+left.getX()+(directionButtonSpacing)*2, left.getY(), directionButtonWidth, directionButtonHeight);
		int UDx = left.getX()+ directionButtonWidth + directionButtonSpacing - directionButtonHeight/2-6;
		int UpY = left.getY() - directionButtonSpacing - directionButtonWidth + directionButtonHeight/2;
		up.setBounds(UDx, UpY, directionButtonHeight+7, directionButtonWidth);
		down.setBounds(UDx, UpY+directionButtonWidth+2*(directionButtonSpacing), directionButtonHeight+7, directionButtonWidth);
	// and add them to the Frame
		this.add(left);
		this.add(right);
		this.add(up);
		this.add(down);
	// add action listeners to the buttons
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
	public void updateTextDisplay(ArrayList<String> list)
	{
		
	}
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
	public char getCurrentMessage()
	{
		return currentMessage;
		
	}
	
}
