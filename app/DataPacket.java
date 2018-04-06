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
	public int previousState;
	public int previousState2;
	public int previousState3;

	public String[] toStringArray() {
		return (leftUltrasonic + "," + upperLeftUltrasonic + "," + middleUltrasonic + "," + upperRightUltrasonic + ","
				+ rightUltrasonic + "," + xAccel + "," + yAccel + "," + zAccel + ","
				+ xGyro + "," + yGyro + "," + zGyro + "," + xMag + "," + yMag + "," + zMag + "," + servoAngle + ","
				+ state + "," + previousState + "," + previousState2 + "," + previousState3).split(",");
	}

	public String toString() {
		return "Left Ultrasonic: " + leftUltrasonic + ", Up. Left Ultrasonic: " + upperLeftUltrasonic
				+ ", Midle Ultrasonic: " + middleUltrasonic + ", Up. Right Ultrasonic: " + upperRightUltrasonic
				+ ", Right Ultrasonic: " + rightUltrasonic
				+ ", xAccel: " + xAccel + ", yAccel: " + yAccel + ", zAccel: " + zAccel + ", xGyro: " + xGyro
				+ ", yGyro: " + yGyro + ", zGyro: " + zGyro + ", xMag: " + xMag + ", yMag: " + yMag + ", zMag: " + zMag
				+ ", Servo Angle: " + servoAngle
				+ ", State: " + state + ", Previous State: " + previousState + ", Previous State #2: " + previousState2 + ", Previous State #3: " + previousState3;
	}

	public DataPacket() {

	}

	public DataPacket(int leftUltrasonic, int upperLeftUltrasonic, int middleUltrasonic,
			int upperRightUltrasonic, int rightUltrasonic, int xAccel, int yAccel,
			int zAccel, int xGyro, int yGyro, int zGyro, int xMag, int yMag, int zMag,
			int servoAngle, int state, int previousState, int previousState2, int previousState3) {
		this.leftUltrasonic = leftUltrasonic;
		this.upperLeftUltrasonic = upperLeftUltrasonic;
		this.middleUltrasonic = middleUltrasonic;
		this.upperRightUltrasonic = upperRightUltrasonic;
		this.rightUltrasonic = rightUltrasonic;
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
		this.previousState = previousState;
		this.previousState2 = previousState2;
		this.previousState3 = previousState3;
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

	public int getPreviousState() {
		return previousState;
	}

	public void setPreviousState(int previousState) {
		this.previousState = previousState;
	}

	public int getPreviousState2() {
		return previousState2;
	}

	public void setPreviousState2(int previousState2) {
		this.previousState2 = previousState2;
	}

	public int getPreviousState3() {
		return previousState3;
	}

	public void setPreviousState3(int previousState3) {
		this.previousState3 = previousState3;
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
