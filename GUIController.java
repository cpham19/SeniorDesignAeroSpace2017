import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

public class GUIController extends JFrame
{
	// this is for future lookup so that we can identify Anomaly zones, 
	private int AreaMapwidth = 500;
	private int AreaMapheight = 500;
	private int stepValue = 8;
	private int drawLength = 15;
	AreaMap grid = new AreaMap(AreaMapwidth, AreaMapheight, stepValue, drawLength);
	// text Panel ( For the printing of the incoming values )
	ArrayList<String> texts = new ArrayList<String>();
	TextPanel textDisplay = new TextPanel();
	// Map Panel ( The printing of the grid )
	MapPanel explorationMap = new MapPanel(grid, this.getWidth(), this.getHeight());
	
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
	// set bounds of all Buttons
	// and add them to the Frame
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
	
}
