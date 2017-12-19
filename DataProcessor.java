package app;

/*
 * This class will be responsible for any and all data processing that needs to be done.
 *
 *
 */
public class DataProcessor
{
	double Range = 32768;
	double AccelScale = 4; // +- 4g
	double ThreshP = 3; // Percentage of full read we want to make our threshold.
	// The Gryoscope can only handle 1000 degrees of rotation per second in this mode
	double GyroScale = 1000; // 1000 Degrees Per Second

	/*The accelerator Threshold will be ( range/scale/100) which will give us 1% of a 1g reading
	next we multiply that by our ThreshP so as to set our threshold to a chosen percentage value;*/
	double AcceleratorThreshold = ( (Range/AccelScale) / 100 ) * ThreshP;
	double GyroScopeThreshold = 80;

	// transform reading from g's into M/s/s
	public double gToAcceleration(double g)
	{
		return (g * 9.8);
	}


	/*Returns a length 3 Array in the for of {X, Y, Z} Acceleration
	 *  information in Gravitation units*/
	public int[] AccelCleanUp(double x, double y, double z)
	{
		int[] ret = new int[3];

		// We only care if it's below the threshold value because that means that the car is not moving
		// and we don't want to inaccurately read movement values when the car isn't moving
		// There will however be small error in the calculation of the position. But that error
		// is small enough that it won't have an impact on our project.
		if( x < AcceleratorThreshold )
		{
			ret[0] = 0; // X position in array set to 0
		}
		if( y < AcceleratorThreshold)
		{
			ret[1] = 0; // Y position in array set to 0;
		}
		if(z < AcceleratorThreshold)
		{
			ret[2] = 0; // Z position in array set to 0;
		}

		return ret;
	}


	public int[] GyroCleanUp(double x, double y, double z)
	{
		int[] ret = new int[3];

		// We only care if it's below the threshold value because that means that the car is not moving
		// and we don't want to inaccurately read movement values when the car isn't moving
		// There will however be small error in the calculation of the position. But that error
		// is small enough that it won't have an impact on our project.
		if( x < GyroScopeThreshold )
		{
			ret[0] = 0; // X position in array set to 0
		}
		if( y < GyroScopeThreshold)
		{
			ret[1] = 0; // Y position in array set to 0;
		}
		if(z < GyroScopeThreshold)
		{
			ret[2] = 0; // Z position in array set to 0;
		}

		return ret;
	}

	// 					implementation not complete
	// This method should be able to take to different sets and find the change between them.
	// or the area under the curve, and this will give us our position, and our rotational value.
	public double integralValue(double[] a, double[] b)
	{
		double ret = 0.0;
		// sum up the small changes

		return ret;
	}
}