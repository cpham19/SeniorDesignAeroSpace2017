//www.elegoo.com

#include <Servo.h>  //servo library
#include <Wire.h>

// Motors define
#define ENA 5 // Left wheel speed
#define ENB 6 // Right wheel speed
#define IN1 7 // Left wheel forward
#define IN2 8 // Left wheel reverse
#define IN3 9 // Right wheel reverse
#define IN4 11 // Right wheel foward
#define LED 13 // LED

// 9 degrees of freedom define
#define    MPU9250_ADDRESS            0x68
#define    MAG_ADDRESS                0x0C

#define    GYRO_FULL_SCALE_250_DPS    0x00  
#define    GYRO_FULL_SCALE_500_DPS    0x08
#define    GYRO_FULL_SCALE_1000_DPS   0x10
#define    GYRO_FULL_SCALE_2000_DPS   0x18

#define    ACC_FULL_SCALE_2_G        0x00  
#define    ACC_FULL_SCALE_4_G        0x08
#define    ACC_FULL_SCALE_8_G        0x10
#define    ACC_FULL_SCALE_16_G       0x18

// Servo myservo;      // create servo object to control servo

int Echo1 = A0;     // Ultrasonic on the Left
int Trig1 = A1;     // Ultrasonic on the Left
int Echo2 = A2;     // Ultrasonic on the Upper-Left
int Trig2 = A3;     // Ultrasonic on the Upper-Left
int Echo3 = A4;      // Ultrasonic on the Middle
int Trig3 = A5;      // Ultrasonic on the Middle
int Echo4 = A6;     // Ultrasonic on the Upper-Right
int Trig4 = A7;     // Ultrasonic on the Upper-Right
int Echo5 = A8;     // Ultrasonic on the Right
int Trig5 = A9;     // Ultrasonic on the Right

#define microphone analogRead(10)
const int sampleWindow = 50; // Sample window width in mS (50 mS = 20Hz)

// For carspeed
// 100 is enough to move the car forward and backward (BUT NOT LEFT AND RIGHT)
// 180 is enough to move the car forward, backward, left, and right
unsigned char carSpeed = 120; // initial speed of car >=0 to <=255
unsigned char carSpeed2 = 150; // initial speed of car >=0 to <=255
int servoAngle = 90;
char currentInput;
int state = 4;
boolean trainingWheelOn = false;
int previousState = 0;
int previousState2 = 0;
int previousState3 = 0;
boolean firstRecord = true;

class DataPacket {
  private:
    int leftDistance;
    int upperLeftDistance;
    int middleDistance;
    int upperRightDistance;
    int rightDistance;
    int ax;
    int ay;
    int az;
    int gx;
    int gy;
    int gz;
    int mx;
    int my;
    int mz;
    int state;
    int previousState;
    int previousState2;
    int previousState3;
    unsigned char carSpeed;
    int servoAngle;

  public:
    DataPacket(int distance1, int distance2, int distance3, int distance4, int distance5, int ax1, int ay1, int az1, int gx1, int gy1, int gz1, int mx1, int my1, int mz1, unsigned char carSpeed1, int servoAngle1, int state1, int previousState1, int previousStateTwo, int previousStateThree)
    {
        leftDistance = distance1;
        upperLeftDistance = distance2;
        middleDistance = distance3;
        upperRightDistance = distance4;
        rightDistance = distance5;
        ax = ax1;
        ay = ay1;
        az = az1;
        gx = gx1;
        gy = gy1;
        gz = gz1;
        mx = mx1;
        my = my1;
        mz = mz1;
        carSpeed = carSpeed1;
        servoAngle = servoAngle1;
        state = state1;
        previousState = previousState1;
        previousState2 = previousStateTwo;
        previousState3 = previousStateThree;
    }
    
    void print() {
      // Separate the print values by commas for parsing in DAM
      Serial.print(leftDistance);
      Serial.print(",");
      Serial.print(upperLeftDistance);
      Serial.print(",");
      Serial.print(middleDistance);
      Serial.print(",");
      Serial.print(upperRightDistance);
      Serial.print(",");
      Serial.print(rightDistance);
      Serial.print(",");
      Serial.print(ax,DEC);
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
      Serial.print(my-70,DEC);
      Serial.print(",");
      Serial.print(mz-700,DEC);
      Serial.print(",");
      Serial.print(carSpeed);
      Serial.print(",");
      Serial.print(servoAngle);
      Serial.print(",");
      Serial.print(state);
      Serial.print(",");
      Serial.print(previousState);
      Serial.print(",");
      Serial.print(previousState2);
      Serial.print(",");
      Serial.print(previousState3);
      Serial.println();
    }
};

void forward(){ 
  analogWrite(ENA, carSpeed);
  analogWrite(ENB, carSpeed);
  //digitalWrite(ENA,HIGH);
  //digitalWrite(ENB,HIGH);
  digitalWrite(IN1,HIGH);
  digitalWrite(IN2,LOW);
  digitalWrite(IN3,LOW);
  digitalWrite(IN4,HIGH);
  previousState3 = previousState2;
  previousState2 = previousState;
  previousState = state;
  state = 0;
}

void back(){
  analogWrite(ENA, carSpeed);
  analogWrite(ENB, carSpeed);
  //digitalWrite(ENA,HIGH);
  //digitalWrite(ENB,HIGH);
  digitalWrite(IN1,LOW);
  digitalWrite(IN2,HIGH);
  digitalWrite(IN3,HIGH);
  digitalWrite(IN4,LOW);
  previousState3 = previousState2;
  previousState2 = previousState;
  previousState = state;
  state = 3;
}

void right(){
  analogWrite(ENA,carSpeed2);
  analogWrite(ENB,carSpeed2);
  //digitalWrite(ENA, HIGH);
  //digitalWrite(ENB,HIGH);
  digitalWrite(IN1,HIGH);
  digitalWrite(IN2,LOW);
  digitalWrite(IN3,HIGH);
  digitalWrite(IN4,LOW);
  previousState3 = previousState2;
  previousState2 = previousState;
  previousState = state;
  state = 2;
}

void left(){
  analogWrite(ENA,carSpeed2);
  analogWrite(ENB,carSpeed2);
  //digitalWrite(ENA,HIGH);
  //digitalWrite(ENB,HIGH);
  digitalWrite(IN1,LOW);
  digitalWrite(IN2,HIGH);
  digitalWrite(IN3,LOW);
  digitalWrite(IN4,HIGH);
  previousState3 = previousState2;
  previousState2 = previousState;
  previousState = state;
  state = 1;
}

void stop() {
  digitalWrite(ENA,LOW);
  digitalWrite(ENB,LOW);
  previousState3 = previousState2;
  previousState2 = previousState;
  previousState = state;
  state = 4;
}

void rotateServoLeft() {
  if (servoAngle + 15 <= 180) {
    servoAngle = servoAngle + 15;
  }
}

void rotateServoRight() {
  if (servoAngle - 15 >= 15) {
    servoAngle = servoAngle - 15;
  }
}

void decreaseCarSpeed() {
  if (carSpeed - 5 >= 90) {
    carSpeed = carSpeed - 5;
  }
}

void increaseCarSpeed() {
  if (carSpeed + 5 <= 250) {
    carSpeed = carSpeed + 5;
  }
}
 
//Ultrasonic distance measurement for Ultrasonic on Left
int Distance_test1() {
  digitalWrite(Trig1, LOW);   
  delayMicroseconds(2);
  digitalWrite(Trig1, HIGH);  
  delayMicroseconds(20);
  digitalWrite(Trig1, LOW);   
  float Fdistance = pulseIn(Echo1, HIGH);  
  Fdistance= Fdistance / 58;       
  return (int)Fdistance;
}

//Ultrasonic distance measurement for Ultrasonic on Upper-Left
int distance_left() {
  digitalWrite(Trig2, LOW);   
  delayMicroseconds(2);
  digitalWrite(Trig2, HIGH);  
  delayMicroseconds(20);
  digitalWrite(Trig2, LOW);   
  float Fdistance = pulseIn(Echo2, HIGH);  
  Fdistance= Fdistance / 58;       
  return (int)Fdistance;
}

//Ultrasonic distance measurement for Ultrasonic on Middle
int distance_middle() {
  digitalWrite(Trig3, LOW);   
  delayMicroseconds(2);
  digitalWrite(Trig3, HIGH);  
  delayMicroseconds(20);
  digitalWrite(Trig3, LOW);   
  float Fdistance = pulseIn(Echo3, HIGH);  
  Fdistance= Fdistance / 58;       
  return (int)Fdistance;
}

//Ultrasonic distance measurement for Ultrasonic on Upper-Right
int distance_right() {
  digitalWrite(Trig4, LOW);   
  delayMicroseconds(2);
  digitalWrite(Trig4, HIGH);  
  delayMicroseconds(20);
  digitalWrite(Trig4, LOW);   
  float Fdistance = pulseIn(Echo4, HIGH);  
  Fdistance= Fdistance / 58;       
  return (int)Fdistance;
}

//Ultrasonic distance measurement for Ultrasonic on Right
int Distance_test5() {
  digitalWrite(Trig5, LOW);   
  delayMicroseconds(2);
  digitalWrite(Trig5, HIGH);  
  delayMicroseconds(20);
  digitalWrite(Trig5, LOW);   
  float Fdistance = pulseIn(Echo5, HIGH);  
  Fdistance= Fdistance / 58;       
  return (int)Fdistance;
}

void readIncomingSerial()
{
  if(Serial.available()>0)
  {
    currentInput = Serial.read();
    switch(currentInput){
     case 'f': forward(); break;
     case 'b': back();   break;
     case 'l': left();   break;
     case 'r': right();  break;
     case 's': stop();   break;
     case '1': rotateServoLeft(); break;
     case '2': rotateServoRight(); break;
     case 'd': decreaseCarSpeed(); break;
     case 'i': increaseCarSpeed(); break;
     default:  break;
    }
  } 
}

// This function read Nbytes bytes from I2C device at address Address. 
// Put read bytes starting at register Register in the Data array. 
void I2Cread(uint8_t Address, uint8_t Register, uint8_t Nbytes, uint8_t* Data)
{
  // Set register address
  Wire.beginTransmission(Address);
  Wire.write(Register);
  Wire.endTransmission();
  
  // Read Nbytes
  Wire.requestFrom(Address, Nbytes); 
  uint8_t index=0;
  while (Wire.available())
    Data[index++]=Wire.read();
}


// Write a byte (Data) in device (Address) at register (Register)
void I2CwriteByte(uint8_t Address, uint8_t Register, uint8_t Data)
{
  // Set register address
  Wire.beginTransmission(Address);
  Wire.write(Register);
  Wire.write(Data);
  Wire.endTransmission();
}

void setup() { 
  pinMode(LED, OUTPUT);
  // Arduino initializations
  Wire.begin();

  // USB Serial Port 0
  Serial.begin(115200);

  // attach servo on pin 3 to servo object
  // myservo.attach(3);

  // These are for Ultrasonic
  pinMode(Echo1, INPUT);    
  pinMode(Trig1, OUTPUT);  
  pinMode(Echo2, INPUT);
  pinMode(Trig2, OUTPUT);
  pinMode(Echo3, INPUT);
  pinMode(Trig3, OUTPUT);
  pinMode(Echo4, INPUT);
  pinMode(Trig4, OUTPUT);
  pinMode(Echo5, INPUT);
  pinMode(Trig5, OUTPUT);

  // These are for the motors
  pinMode(IN1,OUTPUT);
  pinMode(IN2,OUTPUT);
  pinMode(IN3,OUTPUT);
  pinMode(IN4,OUTPUT);
  pinMode(ENA,OUTPUT);
  pinMode(ENB,OUTPUT);

  // These are for linetracking
  // pinMode(LT_R,INPUT);
  // pinMode(LT_M,INPUT);
  // pinMode(LT_L,INPUT);

  // These are for sound detectors
  // pinMode(microphone, INPUT);

  // Set accelerometers low pass filter at 5Hz
  I2CwriteByte(MPU9250_ADDRESS,29,0x06);
  // Set gyroscope low pass filter at 5Hz
  I2CwriteByte(MPU9250_ADDRESS,26,0x06);
  // Configure gyroscope range
  I2CwriteByte(MPU9250_ADDRESS,27,GYRO_FULL_SCALE_1000_DPS);
  // Configure accelerometers range
  I2CwriteByte(MPU9250_ADDRESS,28,ACC_FULL_SCALE_4_G);
  // Set by pass mode for the magnetometers
  I2CwriteByte(MPU9250_ADDRESS,0x37,0x02);
  // Request continuous magnetometer measurements in 16 bits
  I2CwriteByte(MAG_ADDRESS,0x0A,0x16);
  pinMode(13, OUTPUT);

  // Turn Ultrasonic Sonic to 90 degrees
  //myservo.write(servoAngle);
  
  stop();
}

int encode(int distance) {
  if (distance < 20) {
    return 1;
  }
  else {
    return 0;
  }
}

//void compareSides() {
//  //Turn left if there is more space on the left
//  if (Distance_test1() > Distance_test5()) {
//    left();
//  }
//  // Turn right if there is more space on the right
//  else if (Distance_test5() > Distance_test1()) {
//    right();
//  }
//}

void command() {
  // If close to a wall. turn away from the wall.
  if (distance_middle() < 20) {
      // Turn left if there is more space on the left
      if (Distance_test1() > Distance_test5()) {
        while (distance_middle() < 40) {
          left();
          sendData();
        }
        return;
      }
      // Turn right if there is more space on the right
      else if (Distance_test5() > Distance_test1()) {
        while (distance_middle() < 40) {
          right();
          sendData();
        }
        return;
      }
  }
  // Turn left if the upper left sensor is detecting something
  else if (distance_left() < 20) {
      while (distance_left() < 20) {
        right();
        sendData();
      }
      return;
  }
  // Turn right if the upper right sensor is detecting something
  else if (distance_right() < 20) {
      while (distance_right() < 20) {
        left();
        sendData();
      }
      return;
  }
  
  // If none of the above, go forward.
  forward();
  sendData();
  return;
}

// Robin's Training wheel (somewhat working?)
//void command() {
//  int m = encode(distance_middle());
//  int l = encode(distance_left());
//  int r = encode(distance_right());
//  if (previousState == 0) {
//    if (m == 1 && l == 1 && r == 1) {
//      back();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 1 && r == 0) {
//      right();
//      sendData();
//      return;
//      
//    }
//    else if (m == 1 && l == 0 && r == 1) {
//      left();
//      sendData();
//      return;
//      
//    }
//    else if (m == 1 && l == 0 && r == 0) {
//      if (Distance_test1() > Distance_test5()) {
//        left();
//        sendData();
//        return;
//      }
//      else if (Distance_test5() > Distance_test1()) {
//        right();
//        sendData();
//        return;
//      }
//    }
//    else if (m == 0 && l == 1 && r == 1) {
//      back();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 1 && r == 0) {
//      right();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 0 && r == 1) {
//      left();
//      sendData();
//      return;     
//    }
//    else if (m == 0 && l == 0 && r == 0) {
//      forward();
//      sendData();
//      return;
//    }
//  }
//  if (previousState == 1) {
//    if (m == 1 && l == 1 && r == 1) {
//      back();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 1 && r == 0) {
//      forward();
//      sendData();
//      return;    
//    }
//    else if (m == 1 && l == 0 && r == 1) {
//      left();
//      sendData();
//      return;    
//    }
//    else if (m == 1 && l == 0 && r == 0) {
//      left();
//      sendData();
//      return;     
//    }
//    else if (m == 0 && l == 1 && r == 1) {
//      back();
//      sendData();
//      return;    
//    }
//    else if (m == 0 && l == 1 && r == 0) {
//      back();
//      sendData();
//      return;    
//    }
//    else if (m == 0 && l == 0 && r == 1) {
//      forward();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 0 && r == 0) {
//      back();
//      sendData();
//      return;
//    }
//  }
//  if (previousState == 2) {
//    if (m == 1 && l == 1 && r == 1) {
//      back();
//      sendData();
//      return;    
//    }
//    else if (m == 1 && l == 1 && r == 0) {
//      right();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 0 && r == 1) {
//      back();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 0 && r == 0) {
//      right();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 1 && r == 1) {
//      back();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 1 && r == 0) {
//      right();
//      sendData();
//      return;    
//    }
//    else if (m == 0 && l == 0 && r == 1) {
//      back();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 0 && r == 0) {
//      forward();
//      sendData();
//      return;
//    }
//  }
//  if (previousState == 3) {
//    if (m == 1 && l == 1 && r == 1) {
//      back();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 1 && r == 0) {
//      back();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 0 && r == 1) {
//      back();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 0 && r == 0) {
//      if (Distance_test1() > Distance_test5()) {
//        left();
//        sendData();
//        return;
//      }
//      else if (Distance_test5() > Distance_test1()) {
//        right();
//        sendData();
//        return;
//      }
//    }
//    else if (m == 0 && l == 1 && r == 1) {
//      back();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 1 && r == 0) {
//      forward();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 0 && r == 1) {
//      left();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 0 && r == 0) {
//       if (Distance_test1() > Distance_test5()) {
//        left();
//        sendData();
//        return;
//      }
//      else if (Distance_test5() > Distance_test1()) {
//        right();
//        sendData();
//        return;
//      }
//    }
//  }
//
//  forward();
//  sendData();
//  return;
//}

// Kabir's training wheel (NOT WORKING)
//void command() {
//  int m = encode(distance_middle());
//  int l = encode(distance_left());
//  int r = encode(distance_right());
//  
//  if ((previousState == 0 && previousState2 == 0) || (previousState == 0 && previousState2 == 1) || (previousState == 0 && previousState2 == 2)) {
//    if (m == 0 && l == 0 && r == 0) {
//      forward();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 1 && r == 1) {
//      // compare sides
//      compareSides();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 1 && r == 0) {
//      right();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 0 && r == 1) {
//      left();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 1 && r == 1) {
//      // compare sides
//      compareSides();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 0 && r == 1) {
//      left();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 1 && r == 0) {
//      right();
//      sendData();
//      return;
//    }
//    else if (m == 1 && left == 0 && r == 0) {
//      // compare sides
//      compareSides();
//      sendData();
//      return;
//    }
//  }
//  if ((previousState == 2 && previousState2 == 0) || (previousState == 2 && previousState2 == 1) || (previousState == 2 && previousState2 == 2)) {
//    if (m == 0 && l == 0 && r == 0) {
//      forward();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 1 && r == 1) {
//      // compare sides
//      right();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 1 && r == 0) {
//      right();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 0 && r == 1) {
//      // Not Left
//      right();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 1 && r == 1) {
//      // compare sides
//      right();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 0 && r == 1) {
//      // ?
//      left();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 1 && r == 0) {
//      right();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 0 && r == 0) {
//      // compare sides
//      right();
//      sendData();
//      return;
//    }
//  }
//  if ((previousState == 1 && previousState2 == 0) || (previousState == 1 && previousState2 == 1) || (previousState == 1 && previousState2 == 2)) {
//    if (m == 0 && l == 0 && r == 0) {
//      forward();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 1 && r == 1) {
//      // compare sides
//      left();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 1 && r == 0) {
//      // Not Right
//      left();
//      sendData();
//      return;
//    }
//    else if (m == 0 && l == 0 && r == 1) {
//      left();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 1 && r == 1) {
//      // compare sides
//      left();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 0 && r == 1) {
//      left();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 1 && r == 0) {
//      // ?
//      right();
//      sendData();
//      return;
//    }
//    else if (m == 1 && l == 0 && r == 0) {
//      // compare sides
//      left();
//      sendData();
//      return;
//    }
//  }
//
//  forward();
//  sendData();
//  return;
//}


double microphone_volt() {
   unsigned long startMillis= millis();  // Start of sample window
   unsigned int peakToPeak = 0;   // peak-to-peak level

   unsigned int signalMax = 0;
   unsigned int signalMin = 1024;

   // collect data for 50 mS
   while (millis() - startMillis < sampleWindow)
   {
      unsigned int sample = microphone;
      if (sample < 1024)  // toss out spurious readings
      {
         if (sample > signalMax)
         {
            signalMax = sample;  // save just the max levels
         }
         else if (microphone < signalMin)
         {
            signalMin = sample;  // save just the min levels
         }
      }
   }
   peakToPeak = signalMax - signalMin;  // max - min = peak-peak amplitude

   double volts = (peakToPeak * 5.0) / 1024;  // convert to volts

   if (volts > 1.6) {
    if (trainingWheelOn == true) {
      trainingWheelOn = false;
    }
    else {
      trainingWheelOn = true;
    }
   }

   return volts;
}

void sendData() {
    // Method that returns ultrasonic data in centimeters
    int distance1 = Distance_test1()/10;
    int distance2 = encode(distance_left());
    int distance3 = encode(distance_middle());
    int distance4 = encode(distance_right());
    int distance5 = Distance_test5()/10;

    // For Encoding
    // int l = encode(distance_left());
    // int m = encode(distance_middle());
    // int r = encode(distance_right());
    
    // 9 degrees of freedom data
    
    // Read accelerometer and gyroscope
    uint8_t Buf[14];
    I2Cread(MPU9250_ADDRESS,0x3B,14,Buf);
  
    // Create 16 bits values from 8 bits data
  
    // Accelerometer
    int16_t ax=-(Buf[0]<<8 | Buf[1]);
    int16_t ay=-(Buf[2]<<8 | Buf[3]);
    int16_t az=Buf[4]<<8 | Buf[5];
  
    // Gyroscope
    int16_t gx=-(Buf[8]<<8 | Buf[9]);
    int16_t gy=-(Buf[10]<<8 | Buf[11]);
    int16_t gz=Buf[12]<<8 | Buf[13];

    // Read register Status 1 and wait for the DRDY: Data Ready
  
    uint8_t ST1;
    do
    {
      I2Cread(MAG_ADDRESS,0x02,1,&ST1);
    }
    while (!(ST1&0x01));
  
    // Read magnetometer data  
    uint8_t Mag[7];  
    I2Cread(MAG_ADDRESS,0x03,7,Mag);
  
    // Create 16 bits values from 8 bits data
    
    // Magnetometer
    int16_t mx=-(Mag[3]<<8 | Mag[2]);
    int16_t my=-(Mag[1]<<8 | Mag[0]);
    int16_t mz=-(Mag[5]<<8 | Mag[4]);

    // Create a DataPacket object and print its data to the DAM
    DataPacket packet(distance1, distance2, distance3, distance4, distance5, ax, ay, az, gx, gy, gz, mx, my, mz, carSpeed, servoAngle, state, previousState, previousState2, previousState3);
    packet.print();

    delay(100);
}


void loop() {
    // Training wheel
    //command();

    // Receive user input from DAM
    readIncomingSerial();

    // Send data
    sendData();
}

