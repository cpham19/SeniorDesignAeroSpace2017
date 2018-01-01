
#ifndef Data_h
#define Data_h
#include "Arduino.h"

class DataPacket {
  private:
    int distance;
    int left;
    int middle;
    int right;
    double ax;
    double ay;
    double az;
    double gx;
    double gy;
    double gz;
    double mx;
    double my;
    double mz;
    String state;
    unsigned char carSpeed;
    int servoAngle;

  public:
    DataPacket(int distance1, int left1, int middle1, int right1, double ax1, double ay1, double az1, double gx1, double gy1, double gz1, double mx1, double my1, double mz1, String state1, unsigned char carSpeed1, int servoAngle1)
    {
        distance = distance1;
        left = left1;
        middle = middle1;
        right = right1;
        ax = ax1;
        ay = ay1;
        az = az1;
        gx = gx1;
        gy = gy1;
        gz = gz1;
        mx = mx1;
        my = my1;
        mz = mz1;
        state = state1;
        carSpeed = carSpeed1;
        servoAngle = servoAngle1;
    }

    void print() {
      // Separate the print values by commas for parsing in DAM
      Serial.print(distance);
      Serial.print(",");
      Serial.print(left);
      Serial.print(",");
      Serial.print(middle);
      Serial.print(",");
      Serial.print(right);
      Serial.print(",");
      Serial.print(((double)ax/8000),DEC);
      Serial.print(",");
      Serial.print(ay,DEC);
      Serial.print(",");
      Serial.print(az,DEC);
      Serial.print(",");
      Serial.print(gx,DEC);
      Serial.print(",");
      Serial.print(gy,DEC);
      Serial.print(",");
      Serial.print(gz,DEC);
      Serial.print(",");
      Serial.print(mx+200,DEC);
      Serial.print(",");
      Serial.print(my-200,DEC);
      Serial.print(",");
      Serial.print(mz-700,DEC);
      Serial.print(",");
      Serial.print(state);
      Serial.print(",");
      Serial.print(carSpeed);
      Serial.print(",");
      Serial.print(servoAngle);
      Serial.println();
    }
};

#endif
