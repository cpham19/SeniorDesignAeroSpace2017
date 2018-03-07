package appForKabirsCar;

/*
 * This class will hold the information pertaining to the
 * mapping of the zone in which the car finds itself in.
 */
public class AreaMap
{
// this will be the pixel grid to project
private int xSize;
private int ySize;
private int[][] grid;
private int step;
private int drawSize;

public static final int Black = 0; // undiscovered
public static final int White = 1; // discovered and safe
public static final int Red = 2; // discovered and dangerous
public static final int Green = 3; // discovered and curious


public AreaMap(int xS, int yS, int step, int drawSize)
{
	this.xSize = xS;
	this.ySize = yS;
	this.step = step;
	this.drawSize = drawSize;

	grid = new int[xSize][ySize];
}

public int[][] getGrid()
{
	return grid;
}
public int getStep()
{
	return step;
}
public int getDrawSize()
{
	return drawSize;
}
// Transform a (real) x and (real) y value into a pixel location.
public int[] XYToPixelLocation(int x, int y)
{
	int[] ret = new int[2];

	int midX = xSize /2;
	int midY = ySize /2;
	int yAdd = y*8;
	int xAdd = x*8;

	ret[0] = midX + xAdd;
	ret[1] = midY + yAdd;

	return ret;
}

// set all the positions(pixel) around the spot to a specific color value
public void setPositionValueToColorValue(int x, int y, int ColorValue)
{
	// double for loop with added conditions
	for(int ix = -(step-1);ix <step-1; ix++)
	{
		for(int iy = -(step); iy<step-1; iy++)
		{
			if(ix >= 0 && ix < xSize && iy >= 0 && iy < ySize)
			{
				grid[ix][iy] = ColorValue;
			}
		}
	}
}
public void setPositionValueToColorValue(int arr[], int ColorValue)
{
	if(arr.length == 2)
	{
		setPositionValueToColorValue(arr[0], arr[1], ColorValue);
	}
}



}
