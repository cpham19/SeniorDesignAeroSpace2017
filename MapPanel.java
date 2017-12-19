package app;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapPanel extends JPanel
{

	private JButton up;
	private JButton left;
	private JButton down;
	private JButton right;

	private JButton roveMode;
	private JButton manualMode;

	private int xLoc, yLoc;
	private int width;
	private int height;
	private int bufferSize = 10;

	private int gridMinX = 410;
	private int gridMinY = 10;
	private int gridMaxX = 910;
	private int gridMaxY = 510;

	private int textMinX = 410;
	private int textMinY = 520;
	private int textMaxX = 910;
	private int textMaxY = 700;

	private ArrayList<String> textList = new ArrayList<String>();
	AreaMap grid;

	public MapPanel(AreaMap grid, ArrayList<String> list, int width, int height)
	{
		textList.add("Hello");
		textList.add("Hello");
		textList.add("Hello");
		textList.add("Hello");
		textList.add("Hello");
		this.setLayout(null);
		this.setVisible(true);
		this.grid = grid;
		this.width = width;
		this.height = height;
		grid.setPositionValueToColorValue(5, 5, 1);
	}

	// this is where the panel will  be drawn (this is ran automatically)
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		paintBackground(g);

		// paint Grid
		paintGridArea(g);
		// paint Text Area
		paintTextArea(g,textList);


	}

	public void paintGridArea(Graphics g)
	{
		//draw the black grid background
		g.setColor(Color.black);
		g.fillRect(gridMinX,gridMinY, 500, 500);
		//draw the mapped grid points
		paintGridPoints(g,grid);
	}
	public void paintTextArea(Graphics g, ArrayList<String> list)
	{
		//draw the White back drop
		g.setColor(Color.WHITE);
		g.fillRect(textMinX, textMinY, textMaxX-textMinX, 160);
		//draw the boxes for messages
		g.setColor(Color.RED);
		if(list != null)
		{
			for(int i =list.size()-1; i >= 0 && i >= list.size()-8 ;i--)
			{
				g.drawRect(textMinX, textMinY+(20*i), textMaxX-textMinX, 20);
				g.drawString(list.get(i), textMinX+bufferSize, textMinY+(20*i)+bufferSize+5);
			}
		}
		//draw the messages into the boxes
	}
	public void paintBackground(Graphics g)
	{
		// paint base background
		g.setColor(new Color(100,100,250));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		// paint paint rightSide Background
		g.setColor(Color.gray);
		g.fillRect(400, 0, this.getWidth()-400,this.getHeight());
	}


	public void paintGridPoints(Graphics g, AreaMap m)
	{
		for(int x=0; x<(gridMaxX-gridMinX);x+=grid.getStep())
		{
			for(int y=0; y<(gridMaxY-gridMinY);y+=grid.getStep())
			{
				if(m.getGrid()[x][y] == AreaMap.White)
				{
					g.setColor(Color.white);
					g.fillRect(gridMinX+x, gridMinY+y, grid.getDrawSize(), grid.getDrawSize());
				}
				if(m.getGrid()[x][y] == AreaMap.Red)
				{
					g.setColor(Color.red);
					g.fillRect(gridMinX+x, gridMinY+y, grid.getDrawSize(), grid.getDrawSize());
				}
				if(m.getGrid()[x][y] == AreaMap.Green)
				{
					g.setColor(Color.green);
					g.fillRect(gridMinX+x, gridMinY+y, grid.getDrawSize(), grid.getDrawSize());
				}
			}
		}
	}
	public ArrayList<String> getTextList()
	{
		return textList;
	}
	public void addToTextList(String add)
	{
		textList.add(add);
	}

}// end class
