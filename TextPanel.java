import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextField;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

//ok given a list of Strings paint out the last 5 entries in a presentable fashion
public class TextPanel extends JPanel
{
	// Storage location for the 5 text fields
	private ArrayList<String> list;
	
	public TextPanel()// empty constructor just for the visuals
	{
		this.setLayout(null);
		this.setBackground(Color.red);
	}
	public TextPanel(ArrayList<String> list) // consntructor with a list to print
	{
		// paint the texts
		this.setLayout(null);
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.black);
		
		g.setColor(Color.blue);
		g.fillRect(0, 0, 500, 200);
	}
	
	public void drawListOfRecievedMessages(Graphics g, ArrayList<String> list)
	{
		if(list != null)
		{
			for(int i =1; i<=5; i++)
			{
				int index = list.size() - i;
				if(  index >= 0 )
				{
					g.drawString(list.get(index), 0, i*20);
				}
			}	
		}
	}
}
