public class DataPacket 
{
	private double time;
	private double acceleration;
	private double tilt;
	private double direction;
	private double ultrasonic;
	private boolean L0;
	private boolean L1;
	private boolean L2;
	
	public DataPacket()
	{}
	
	public DataPacket(double time, double acceleration, double tilt, double direction, double ultrasonic, boolean L0, boolean L1, boolean L2)
	{
		this.time = time;
		this.acceleration = acceleration;
		this.tilt = tilt;
		this.direction = direction;
		this.ultrasonic = ultrasonic;
		this.L0 = L0;
		this.L1 = L1;
		this.L2 = L2;
	}

	public double getTime() 
	{
		return time;
	}

	public void setTime(double time) 
	{
		this.time = time;
	}

	public double getAcceleration() 
	{
		return acceleration;
	}

	public void setAcceleration(double acceleration) 
	{
		this.acceleration = acceleration;
	}

	public double getTilt() 
	{
		return tilt;
	}

	public void setTilt(double tilt) 
	{
		this.tilt = tilt;
	}

	public double getDirection() 
	{
		return direction;
	}

	public void setDirection(double direction) 
	{
		this.direction = direction;
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
}
