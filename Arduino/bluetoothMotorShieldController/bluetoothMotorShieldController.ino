
#include <SoftwareSerial.h>

#define DEBUG true

SoftwareSerial mySerial(10, 11); // RX, TX

int IN1 = 4;
int IN2 = 5;
int IN3 = 6;
int IN4 = 7;

void setup()
{
 pinMode(IN1, OUTPUT);
 pinMode(IN2, OUTPUT);
 pinMode(IN3, OUTPUT);
 pinMode(IN4, OUTPUT);


 
    digitalWrite(IN1,LOW);
    digitalWrite(IN2,LOW);
    digitalWrite(IN3,LOW);
    digitalWrite(IN4,LOW);
  
  // Open serial communications and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for Leonardo only
  }

  if (DEBUG)
     Serial.println("Debug ON");
  // set the data rate for the SoftwareSerial port
  mySerial.begin(9600);
}

void loop() // run over and over
{
  byte command;
  byte front, back, left, right;

  
  if (mySerial.available()){
    command = mySerial.read();

    front = bool(command & 1);
    back = bool(command & 2);
    left = bool(command & 4);
    right = bool(command & 8);
 
    digitalWrite(IN1,front);
    digitalWrite(IN2,back);
    digitalWrite(IN3,left);
    digitalWrite(IN4,right);

    
    if (DEBUG){
        Serial.print("Command: ");
        Serial.println(command);
        Serial.print("Front: ");
        Serial.println(front);
        Serial.print("Back: ");
        Serial.println(back);
        Serial.print("Left: ");
        Serial.println(left);
        Serial.print("Right: ");
        Serial.println(right);
    }
  }


   delay(200); 
    digitalWrite(IN1,LOW);
    digitalWrite(IN2,LOW);
    digitalWrite(IN3,LOW);
    digitalWrite(IN4,LOW);


}

