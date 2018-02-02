package app;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DataPacket
{
	public double time;
	public double ultrasonic;
	public int L0;
	public int L1;
	public int L2;
	public double xAccel;
	public double yAccel;
	public double zAccel;
	public double xGyro;
	public double yGyro;
	public double zGyro;
	public double xMag;
	public double yMag;
	public double zMag;
	public int servoAngle;
	public int state;
	public String[] array;

	public String[] toStringArray() {
		return (time + "," + ultrasonic + "," + L0 + "," + L1 + "," + L2 + "," + xAccel + "," + yAccel + "," + zAccel + "," +
				xGyro + "," + yGyro + "," + zGyro + "," + xMag + "," + yMag + "," + zMag + "," + servoAngle + "," + state).split(",");
	}

	public String toString() {
		return "Time: " + time + " Ulrasonic: " + ultrasonic + " L0: " + L0 + " L1: " + L1 + " L2: " + L2 + " xAccel: " + xAccel + " yAccel: " + yAccel + " zAccel: " + zAccel + " xGyro: " +
				xGyro + " yGyro: " + yGyro + " zGyro: " + zGyro + " xMag: " + xMag + " yMag: " + yMag + " zMag: " + zMag + " Servo Angle: " + servoAngle + " State: " + state;
	}

	public DataPacket()
	{

	}

	public DataPacket(double time, double ultrasonic, int L0, int L1, int L2, double xAccel, double yAccel, double zAccel,
			double xGyro, double yGyro, double zGyro, double xMag, double yMag, double zMag, int servoAngle, int state)
	{
		this.time = time;
		this.ultrasonic = ultrasonic;
		this.L0 = L0;
		this.L1 = L1;
		this.L2 = L2;
		this.xAccel = xAccel;
		this.yAccel = yAccel;
		this.zAccel = zAccel;
		this.xGyro = xGyro;
		this.yGyro = yGyro;
		this.zGyro = zGyro;
		this.xMag = xMag;
		this.yMag = yMag;
		this.zMag = zMag;
		this.servoAngle = servoAngle;
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

	public int isL0() {
		return L0;
	}

	public void setL0(int L0)
	{
		this.L0 = L0;
	}


	public int isL1()
	{
		return L1;
	}


	public void setL1(int L1)
	{
		this.L1 = L1;
	}

	public int isL2()
	{
		return L2;
	}

	public void setL2(int L2)
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

	public int getServoAngle()
	{
		return servoAngle;
	}

	public void setServo(int servoAngle)
	{
		this.servoAngle = servoAngle;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	//https://stackoverflow.com/questions/16309189/java-use-decimalformat-to-format-doubles-and-integers-but-keep-integers-without
	public String format(Number n) {
		NumberFormat format = DecimalFormat.getInstance();
		format.setRoundingMode(RoundingMode.FLOOR);
		format.setMinimumFractionDigits(0);
		format.setMaximumFractionDigits(2);
		return format.format(n);
	}
}
