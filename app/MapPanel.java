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

	AreaMap grid;

	public MapPanel(AreaMap grid, int width, int height)
	{
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
	}

	public void paintGridArea(Graphics g)
	{
		//draw the black grid background
		g.setColor(Color.black);
		g.fillRect(gridMinX,gridMinY, 500, 500);
		//draw the mapped grid points
		paintGridPoints(g,grid);
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
}// end class
