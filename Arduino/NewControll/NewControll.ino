
#include <SoftwareSerial.h>




SoftwareSerial mySerial(7, 8); // RX, TX

int IN1 = 3;
int IN2 = 5;
int IN3 = 6;
int IN4 = 9;

int headlight = 11;
int backlight = 12;
int buzzer = 10;

byte lights = 0;
byte lightsState = 0;
byte buzzerOn = 0;
byte buzzerState = 0;

byte command;
byte front, back, left, right;
void setup()
{
 pinMode(IN1, OUTPUT);
 pinMode(IN2, OUTPUT);
 pinMode(IN3, OUTPUT);
 pinMode(IN4, OUTPUT);

 pinMode(headlight, OUTPUT);
 pinMode(backlight, OUTPUT);
 pinMode(buzzer,OUTPUT);

 digitalWrite(IN1,LOW);
 digitalWrite(IN2,LOW);
 digitalWrite(IN3,LOW);
 digitalWrite(IN4,LOW);

 digitalWrite(headlight,LOW);
 digitalWrite(backlight,LOW);
 noTone(buzzer);


    
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

    lights =  bool(command & 16);
    buzzerOn = bool(command & 32);
    
    digitalWrite(IN3,left);
    digitalWrite(IN4,right); 
    digitalWrite(IN1,front);
    digitalWrite(IN2,back);

    if(lights){
      lightsState = (lightsState + 85)%340; 
      analogWrite(headlight,lightsState);
      digitalWrite(backlight,lightsState);      
      }

    if(buzzerOn){

      buzzerState = !buzzerState;

      if(buzzerState){
        tone(buzzer,440);
      }
      else{
        noTone(buzzer);
      }
      
    }

    
    
    }
    


   /*delay(200); 
    digitalWrite(IN1,LOW);
    digitalWrite(IN2,LOW);
    digitalWrite(IN3,LOW);
    digitalWrite(IN4,LOW);*/


}

