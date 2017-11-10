//www.elegoo.com

#include <Servo.h>  //servo library

// Motors define
#define ENA 5 // Left wheel speed
#define ENB 6 // Right wheel speed
#define IN1 7 // Left wheel forward
#define IN2 8 // Left wheel reverse
#define IN3 9 // Right wheel reverse
#define IN4 11 // Right wheel foward
#define LED 13 // LED

//Line Tracking IO define
#define LT_R digitalRead(10)
#define LT_M digitalRead(4)
#define LT_L digitalRead(2)

Servo myservo;      // create servo object to control servo
int Echo = A4;  
int Trig = A5; 

// For carspeed
// 100 is enough to move the car forward and backward (BUT NOT LEFT AND RIGHT)
// 180 is enough to move the car forward, backward, left, and right
unsigned char carSpeed = 180; // initial speed of car >=0 to <=255
int servoAngle = 90;
char getstr;
char currentInput;
String state = "Stop";

void forward(){ 
  analogWrite(ENA, carSpeed);
  analogWrite(ENB, carSpeed);
  //digitalWrite(ENA,HIGH);
  //digitalWrite(ENB,HIGH);
  digitalWrite(IN1,HIGH);
  digitalWrite(IN2,LOW);
  digitalWrite(IN3,LOW);
  digitalWrite(IN4,HIGH);
  state = "Forward";
  Serial.println("Forward");
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
  state = "Backward";
  Serial.println("Back");
}

void right(){
  analogWrite(ENA,carSpeed);
  analogWrite(ENB,carSpeed);
  //digitalWrite(ENA, HIGH);
  //digitalWrite(ENB,HIGH);
  digitalWrite(IN1,HIGH);
  digitalWrite(IN2,LOW);
  digitalWrite(IN3,LOW);
  digitalWrite(IN4,LOW);
  state = "Right";
  Serial.println("Right");
}

void left(){
  analogWrite(ENA,carSpeed);
  analogWrite(ENB,carSpeed);
  //digitalWrite(ENA,HIGH);
  //digitalWrite(ENB,HIGH);
  digitalWrite(IN1,LOW);
  digitalWrite(IN2,LOW);
  digitalWrite(IN3,LOW);
  digitalWrite(IN4,HIGH);
  state = "Left";
  Serial.println("Left");
}

void stop() {
  digitalWrite(ENA,LOW);
  digitalWrite(ENB,LOW);
  state = "Stop";
  Serial.println("Stop!");
}

void rotateServoLeft() {
  if (servoAngle + 15 <= 180) {
    servoAngle = servoAngle + 15;
    myservo.write(servoAngle);
  }
}

void rotateServoRight() {
  if (servoAngle - 15 >= 15) {
    servoAngle = servoAngle - 15;
    myservo.write(servoAngle);
  }
}

void decreaseCarSpeed() {
  if (carSpeed - 10 >= 100) {
    carSpeed = carSpeed - 10;
  }
}

void increaseCarSpeed() {
  if (carSpeed + 10 <= 250) {
    carSpeed = carSpeed + 10;
  }
}

//Ultrasonic distance measurement Sub function
int Distance_test() {
  digitalWrite(Trig, LOW);   
  delayMicroseconds(2);
  digitalWrite(Trig, HIGH);  
  delayMicroseconds(20);
  digitalWrite(Trig, LOW);   
  float Fdistance = pulseIn(Echo, HIGH);  
  Fdistance= Fdistance / 58;       
  return (int)Fdistance;
}

// End Method ReadSerialInput
void readIncomingSerial()
{
  if(Serial.available()>0)
  {
    currentInput = Serial.read();
    Serial.println("Received input");
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

void setup() { 
  Serial.begin(115200);

  // attach servo on pin 3 to servo object
  myservo.attach(3);

  // These are for Servo
  pinMode(Echo, INPUT);    
  pinMode(Trig, OUTPUT); 
  pinMode(LED, OUTPUT); 

  // These are for the motors
  pinMode(IN1,OUTPUT);
  pinMode(IN2,OUTPUT);
  pinMode(IN3,OUTPUT);
  pinMode(IN4,OUTPUT);
  pinMode(ENA,OUTPUT);
  pinMode(ENB,OUTPUT);

  // These are for linetracking
  pinMode(LT_R,INPUT);
  pinMode(LT_M,INPUT);
  pinMode(LT_L,INPUT);

  stop();
}

void loop() {
    // Turn Ultrasonic Sonic to 90 degrees
    myservo.write(servoAngle);
     
    // Method to read input from user from DAM for controlling car
    readIncomingSerial();
    
    // Method that returns ultrasonic data in centimeters
    int distance = Distance_test();
  
    // Line-tracking data returns 0's and 1's
    int middle = LT_M;
    int left = LT_L;
    int right = LT_R;
    
    // Separate the print values by commas for parsing in DAM
    Serial.print(distance);
    Serial.print(",");
    Serial.print(middle);
    Serial.print(",");
    Serial.print(left);
    Serial.print(",");
    Serial.print(right);
    Serial.print(",");
    Serial.print(random(1, 30));
    Serial.print(",");
    Serial.print(random(1, 30));
    Serial.print(",");
    Serial.print(random(1, 30));
    Serial.print(",");
    Serial.print(random(1, 30));
    Serial.print(",");
    Serial.print(random(1, 30));
    Serial.print(",");
    Serial.print(random(1, 30));
    Serial.print(",");
    Serial.print(random(1, 30));
    Serial.print(",");
    Serial.print(random(1, 30));
    Serial.print(",");
    Serial.print(random(1, 30));
    Serial.print(",");
    Serial.print(state);
    Serial.print(",");
    Serial.print(carSpeed);
    Serial.print(",");
    Serial.print(servoAngle);
    Serial.println();
  
    delay(200);
}

