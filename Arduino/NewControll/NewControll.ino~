
#include <SoftwareSerial.h>




SoftwareSerial mySerial(10, 11); // RX, TX

int IN1 = 4;
int IN2 = 5;
int IN3 = 6;
int IN4 = 7;
byte command;
byte front, back, left, right;
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
    
  mySerial.begin(9600);
}

void loop() // run over and over
{
  

  
  if (mySerial.available())
    {
    command = mySerial.read();
    
    front = bool(command & 1);
    back = bool(command & 2);
    left = bool(command & 4);
    right = bool(command & 8);
    digitalWrite(IN3,left);
    digitalWrite(IN4,right); 
    digitalWrite(IN1,front);
    digitalWrite(IN2,back);
    

    
    
    }
    


   /*delay(200); 
    digitalWrite(IN1,LOW);
    digitalWrite(IN2,LOW);
    digitalWrite(IN3,LOW);
    digitalWrite(IN4,LOW);*/


}

