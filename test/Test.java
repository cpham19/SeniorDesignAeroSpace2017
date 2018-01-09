package test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Test {
	public static void main(String[] args) {
		double value = 10.235;

		System.out.println(Double.parseDouble(format(value)));

	}


	public static String format(Number n) {
		NumberFormat format = DecimalFormat.getInstance();
		format.setRoundingMode(RoundingMode.FLOOR);
		format.setMinimumFractionDigits(2);
		format.setMaximumFractionDigits(2);
		return format.format(n);
	}
}
