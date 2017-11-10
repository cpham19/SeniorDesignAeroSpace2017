package app;

import java.text.DecimalFormat;

public class DataPacket
{
	public double time;
	public double ultrasonic;
	public boolean L0;
	public boolean L1;
	public boolean L2;
	public double xAccel;
	public double yAccel;
	public double zAccel;
	public double xGyro;
	public double yGyro;
	public double zGyro;
	public double xMag;
	public double yMag;
	public double zMag;
	public String state;
	public String[] array;

	public String[] toStringArray() {

		System.out.println(time + "," + ultrasonic + "," + L0 + "," + L1 + "," + L2 + "," + xAccel + "," + yAccel + "," + zAccel + "," +
				xGyro + "," + yGyro + "," + zGyro + "," + xMag + "," + yMag + "," + zMag + "," + state);

		return (time + "," + ultrasonic + "," + L0 + "," + L1 + "," + L2 + "," + xAccel + "," + yAccel + "," + zAccel + "," +
				xGyro + "," + yGyro + "," + zGyro + "," + xMag + "," + yMag + "," + zMag + "," + state).split(",");
	}

	public DataPacket()
	{

	}

	public DataPacket(double time, double ultrasonic, boolean L0, boolean L1, boolean L2, double xAccel, double yAccel, double zAccel,
					  double xGyro, double yGyro, double zGyro, double xMag, double yMag, double zMag, String state)
	{
		DecimalFormat df = new DecimalFormat("##.00");

		this.time = Double.valueOf(df.format(time));
		this.ultrasonic = Double.valueOf(df.format(ultrasonic));
		this.L0 = L0;
		this.L1 = L1;
		this.L2 = L2;
		this.xAccel = Double.valueOf(df.format(xAccel));
		this.yAccel = Double.valueOf(df.format(yAccel));
		this.zAccel = Double.valueOf(df.format(zAccel));
		this.xGyro = Double.valueOf(df.format(xGyro));
		this.yGyro = Double.valueOf(df.format(yGyro));
		this.zGyro = Double.valueOf(df.format(zGyro));
		this.xMag = Double.valueOf(df.format(xMag));
		this.yMag = Double.valueOf(df.format(yMag));
		this.zMag = Double.valueOf(df.format(zMag));
		this.state = state;
	}

	public double getTime()
	{
		return time;
	}

	public void setTime(double time)
	{
		this.time = time;
	}

	public double getUltrasonic()
	{
		return ultrasonic;
	}

	public void setUltrasonic(double ultrasonic)
	{
		this.ultrasonic = ultrasonic;
	}

	public boolean isL0() {
		return L0;
	}

	public void setL0(boolean L0)
	{
		this.L0 = L0;
	}


	public boolean isL1()
	{
		return L1;
	}


	public void setL1(boolean L1)
	{
		this.L1 = L1;
	}

	public boolean isL2()
	{
		return L2;
	}

	public void setL2(boolean L2)
	{
		this.L2 = L2;
	}


	public double getxAccel()
	{
		return xAccel;
	}

	public void setxAccel(double xAccel)
	{
		this.xAccel = xAccel;
	}

	public double getyAccel()
	{
		return yAccel;
	}

	public void setyAccel(double yAccel)
	{
		this.yAccel = yAccel;
	}

	public double getzAccel()
	{
		return zAccel;
	}

	public void setzAccel(double zAccel)
	{
		this.zAccel = zAccel;
	}

	public double getxGyro()
	{
		return xGyro;
	}

	public void setxGyro(double xGyro)
	{
		this.xGyro = xGyro;
	}

	public double getyGyro()
	{
		return yGyro;
	}

	public void setyGyro(double yGyro)
	{
		this.yGyro = yGyro;
	}

	public double getzGyro()
	{
		return zGyro;
	}

	public void setzGyro(double zGyro)
	{
		this.zGyro = zGyro;
	}

	public double getxMag()
	{
		return xMag;
	}

	public void setxMag(double xMag)
	{
		this.xMag = xMag;
	}

	public double getyMag()
	{
		return yMag;
	}

	public void setyMag(double yMag)
	{
		this.yMag = yMag;
	}

	public double getzMag()
	{
		return zMag;
	}

	public void setzMag(double zMag)
	{
		this.zMag = zMag;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

}
