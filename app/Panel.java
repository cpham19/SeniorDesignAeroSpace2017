package app;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel extends JPanel
{
	private int width;
	private int height;

	public Panel(int width, int height)
	{
		this.setLayout(null);
		this.setVisible(true);
		this.width = width;
		this.height = height;
	}

	// this is where the panel will  be drawn (this is ran automatically)
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		paintBackground(g);
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
}// end class
