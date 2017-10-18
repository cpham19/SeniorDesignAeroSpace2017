
public class CarVector
{
private double xData, yData, zData;

// A single unit will be the value of the car's square length value (The value of a square representation of the car
// rounded off to cover for round off error ).
public CarVector(double x, double y, double z)
{
	setxData(x);
	setyData(y);
	setzData(z);
}

public void deltaX(double delta)
{
	this.xData = xData + delta;
}
public void deltaY(double delta)
{
	this.yData = yData + delta;
}
public void deltaZ(double delta)
{
	this.zData = zData + delta;
}

public void addVector(CarVector vecAdd)
{
	this.xData += vecAdd.xData;
	this.yData += vecAdd.yData;
	this.zData += vecAdd.zData;
}
public void multiVector(CarVector vec)
{
	this.xData = this.xData*vec.xData;
	this.yData = this.yData*vec.xData;
	this.zData = this.zData*vec.xData;
}
public double getxData() {
	return xData;
}

public void setxData(double xData) {
	this.xData = xData;
}

public double getyData() {
	return yData;
}

public void setyData(double yData) {
	this.yData = yData;
}

public double getzData() {
	return zData;
}


public void setzData(double zData) {
	this.zData = zData;
}


}
