package app;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DataPacket {
	public int leftUltrasonic;
	public int upperLeftUltrasonic;
	public int middleUltrasonic;
	public int upperRightUltrasonic;
	public int rightUltrasonic;
	public int db1;
	public int db2;
	public int db3;
	public int xAccel;
	public int yAccel;
	public int zAccel;
	public int xGyro;
	public int yGyro;
	public int zGyro;
	public int xMag;
	public int yMag;
	public int zMag;
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

	public DataPacket(int leftUltrasonic, int upperLeftUltrasonic, int middleUltrasonic,
			int upperRightUltrasonic, int rightUltrasonic, int db1, int db2, int db3, int xAccel, int yAccel,
			int zAccel, int xGyro, int yGyro, int zGyro, int xMag, int yMag, int zMag,
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

	public int getLeftUltrasonic() {
		return leftUltrasonic;
	}

	public void setLeftUltrasonic(int leftUltrasonic) {
		this.leftUltrasonic = leftUltrasonic;
	}

	public int getUpperLeftUltrasonic() {
		return upperLeftUltrasonic;
	}

	public void setUpperLeftUltrasonic(int upperLeftUltrasonic) {
		this.upperLeftUltrasonic = upperLeftUltrasonic;
	}

	public int getMiddleUltrasonic() {
		return middleUltrasonic;
	}

	public void setMiddleUltrasonic(int middleUltrasonic) {
		this.middleUltrasonic = middleUltrasonic;
	}

	public int getUpperRightUltrasonic() {
		return upperRightUltrasonic;
	}

	public void setUpperRightUltrasonic(int upperRightUltrasonic) {
		this.upperRightUltrasonic = upperRightUltrasonic;
	}

	public int getRightUltrasonic() {
		return rightUltrasonic;
	}

	public void setRightUltrasonic(int rightUltrasonic) {
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

	public int getxAccel() {
		return xAccel;
	}

	public void setxAccel(int xAccel) {
		this.xAccel = xAccel;
	}

	public int getyAccel() {
		return yAccel;
	}

	public void setyAccel(int yAccel) {
		this.yAccel = yAccel;
	}

	public int getzAccel() {
		return zAccel;
	}

	public void setzAccel(int zAccel) {
		this.zAccel = zAccel;
	}

	public int getxGyro() {
		return xGyro;
	}

	public void setxGyro(int xGyro) {
		this.xGyro = xGyro;
	}

	public int getyGyro() {
		return yGyro;
	}

	public void setyGyro(int yGyro) {
		this.yGyro = yGyro;
	}

	public int getzGyro() {
		return zGyro;
	}

	public void setzGyro(int zGyro) {
		this.zGyro = zGyro;
	}

	public int getxMag() {
		return xMag;
	}

	public void setxMag(int xMag) {
		this.xMag = xMag;
	}

	public int getyMag() {
		return yMag;
	}

	public void setyMag(int yMag) {
		this.yMag = yMag;
	}

	public int getzMag() {
		return zMag;
	}

	public void setzMag(int zMag) {
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
