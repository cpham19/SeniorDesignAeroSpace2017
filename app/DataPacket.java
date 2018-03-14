package app;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DataPacket {
	public double leftUltrasonic;
	public double upperLeftUltrasonic;
	public double middleUltrasonic;
	public double upperRightUltrasonic;
	public double rightUltrasonic;
	public int db1;
	public int db2;
	public int db3;
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

	public String[] toStringArray() {
		return (leftUltrasonic + "," + upperLeftUltrasonic + "," + middleUltrasonic + "," + upperRightUltrasonic + ","
				+ rightUltrasonic + "," + db1 + "," + db2 + "," + db3 + "," + xAccel + "," + yAccel + "," + zAccel + ","
				+ xGyro + "," + yGyro + "," + zGyro + "," + xMag + "," + yMag + "," + zMag + "," + servoAngle + ","
				+ state).split(",");
	}

	public String toString() {
		return "Left Ultrasonic: " + leftUltrasonic + ", Up. Left Ultrasonic: " + upperLeftUltrasonic
				+ ", Midle Ultrasonic: " + middleUltrasonic + ", Up. Right Ultrasonic: " + upperRightUltrasonic
				+ ", Right Ultrasonic: " + rightUltrasonic + ", DB #1: " + db1 + ", DB #2: " + db2 + ", DB #3: " + db3
				+ ", xAccel: " + xAccel + ", yAccel: " + yAccel + ", zAccel: " + zAccel + ", xGyro: " + xGyro
				+ ", yGyro: " + yGyro + ", zGyro: " + zGyro + ", xMag: " + xMag + ", yMag: " + yMag + ", zMag: " + zMag
				+ ", Servo Angle: " + servoAngle + ", State: " + state;
	}

	public DataPacket() {

	}

	public DataPacket(double leftUltrasonic, double upperLeftUltrasonic, double middleUltrasonic,
			double upperRightUltrasonic, double rightUltrasonic, int db1, int db2, int db3, double xAccel, double yAccel,
			double zAccel, double xGyro, double yGyro, double zGyro, double xMag, double yMag, double zMag,
			int servoAngle, int state) {
		this.leftUltrasonic = leftUltrasonic;
		this.upperLeftUltrasonic = upperLeftUltrasonic;
		this.middleUltrasonic = middleUltrasonic;
		this.upperRightUltrasonic = upperRightUltrasonic;
		this.rightUltrasonic = rightUltrasonic;
		this.db1 = db1;
		this.db2 = db2;
		this.db3 = db3;
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

	public double getLeftUltrasonic() {
		return leftUltrasonic;
	}

	public void setLeftUltrasonic(double leftUltrasonic) {
		this.leftUltrasonic = leftUltrasonic;
	}

	public double getUpperLeftUltrasonic() {
		return upperLeftUltrasonic;
	}

	public void setUpperLeftUltrasonic(double upperLeftUltrasonic) {
		this.upperLeftUltrasonic = upperLeftUltrasonic;
	}

	public double getMiddleUltrasonic() {
		return middleUltrasonic;
	}

	public void setMiddleUltrasonic(double middleUltrasonic) {
		this.middleUltrasonic = middleUltrasonic;
	}

	public double getUpperRightUltrasonic() {
		return upperRightUltrasonic;
	}

	public void setUpperRightUltrasonic(double upperRightUltrasonic) {
		this.upperRightUltrasonic = upperRightUltrasonic;
	}

	public double getRightUltrasonic() {
		return rightUltrasonic;
	}

	public void setRightUltrasonic(double rightUltrasonic) {
		this.rightUltrasonic = rightUltrasonic;
	}

	public int getDb1() {
		return db1;
	}

	public void setDb1(int db1) {
		this.db1 = db1;
	}

	public int getDb2() {
		return db2;
	}

	public void setDb2(int db2) {
		this.db2 = db2;
	}

	public int getDb3() {
		return db3;
	}

	public void setDb3(int db3) {
		this.db3 = db3;
	}

	public double getxAccel() {
		return xAccel;
	}

	public void setxAccel(double xAccel) {
		this.xAccel = xAccel;
	}

	public double getyAccel() {
		return yAccel;
	}

	public void setyAccel(double yAccel) {
		this.yAccel = yAccel;
	}

	public double getzAccel() {
		return zAccel;
	}

	public void setzAccel(double zAccel) {
		this.zAccel = zAccel;
	}

	public double getxGyro() {
		return xGyro;
	}

	public void setxGyro(double xGyro) {
		this.xGyro = xGyro;
	}

	public double getyGyro() {
		return yGyro;
	}

	public void setyGyro(double yGyro) {
		this.yGyro = yGyro;
	}

	public double getzGyro() {
		return zGyro;
	}

	public void setzGyro(double zGyro) {
		this.zGyro = zGyro;
	}

	public double getxMag() {
		return xMag;
	}

	public void setxMag(double xMag) {
		this.xMag = xMag;
	}

	public double getyMag() {
		return yMag;
	}

	public void setyMag(double yMag) {
		this.yMag = yMag;
	}

	public double getzMag() {
		return zMag;
	}

	public void setzMag(double zMag) {
		this.zMag = zMag;
	}

	public int getServoAngle() {
		return servoAngle;
	}

	public void setServoAngle(int servoAngle) {
		this.servoAngle = servoAngle;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	// https://stackoverflow.com/questions/16309189/java-use-decimalformat-to-format-doubles-and-integers-but-keep-integers-without
	public String format(Number n) {
		NumberFormat format = DecimalFormat.getInstance();
		format.setRoundingMode(RoundingMode.FLOOR);
		format.setMinimumFractionDigits(0);
		format.setMaximumFractionDigits(2);
		return format.format(n);
	}
}
