/*ESTE CODIGO E BASEADO NO PROJETO CRIADO PELO David Bernier, CON-
 * FORME INFORMACOES ABAIXO.
 * 
 * Do sketch original, somente as definicoes e a funcao 'trigger'
 * foram utilizadas.
 */

/*
    Realtek RX2 (RX2B) Control via Arduino
 
    Written by David Bernier, Aug. 26, 2013
    https://github.com/monsieurDavid/
    GPL v3 License
    
    This is a hack to send two different pulse trains to a RX2-driven
    RC toy car by an Arduino.
    
    Non-PWM code based upon:
    http://forum.arduino.cc/index.php?topic=171238.msg1274631#msg1274631
    
    Pins:
      Pin 9 or 10 to toy's antenna
      Pin 10 or 9 to LED
      Arduino's GND to toy's GND

*/

/*
 * criado: 201605022157
 * atualizado: 201605022157
 *
 * Este sketch e a segunda versao para controlar um carro de contro-
 * le remoto atraves do arduino. Neste modo a parte eletronica do
 * carrinho continua a mesma nao precisando retirar nada. O arduino
 * tera um pino conectado a antena do carrinho que enviara os sinais
 * para serem decodificados pela parte eletronica. Nesta versao
 * o controle original podera continuar ser utilizado.
 * 
 * FUNCIONAMENTO GERAL
 * 
 * Sera utilizado um aplicativo android para enviar os comandos para
 * o arduino. Estes comandos serao interpretados e suas respectivas
 * funcoes executadas.
 * 
 * Os comandos serao enviados no formato de byte;
 * 01 byte sera suficiente para todas as operacoes
 * Cada funcao do controle enviara o bit correspondente do comando
 * como 1 e 0 para parar cancelar a operacao. 
 * EX: o bit 1 representa o comando para andar para frente. 
 * Ao apertar o botao para frente no app sera enviado 00000001
 * marcando como 1 a funcao para frente. Ao soltar o botao do app
 * sera enviado o byte 00000000 que cancelara as operacoes.
 * 
 * bit 1 - para frente
 * bit 2 - para tras
 * bit 3 - para esquerda
 * bit 4 - para a direita
 * bit 5 - farol
 * bit 6 - buzina
 * 
 * 
 * FUNCIONALIDADES:
 *  - para frente;
 *  - para tras;
 *  - esquerda;
 *  - direita;
 *  - buzina;
 *  - farol e lanterna.
 */



/*
 * Para que a serial do Arduino continue livre para DEBUG, utiliza-
 * remos uma serial via software, utilizando os pinos 7,8 que não
 * são PWM.
 */
#include <SoftwareSerial.h>

SoftwareSerial mySerial(7, 8); // RX, TX



//Fixed durations as per RX2B datasheet see http://www.datasheetdir.com/RX-2B+download

//W1
#define ENDCODE         4
#define FORWARD        10
#define FORWARD_TURBO  16
#define TURBO          22
#define FORWARD_LEFT   28
#define FORWARD_RIGHT  34
#define BACKWARD       40
#define BACKWARD_RIGHT 46
#define BACKWARD_LEFT  52
#define LEFT           58
#define RIGHT          64

//output pins
#define ANTENNA     9
#define STATUS_LED 13



int headlight = 11; // o farol sera ligado no pino 11 pwm e tera 3
                    // niveis de intensidade.
int backlight = 12; // sao as luzes traseiras 
int buzzer = 10; // buzina do carrinho.

/*
 * rightState e leftState serao utilizadas para saber se o carro
 * estava com a direcao acionada. Estes estados servirao para 
 * realizar a estabilizacao da direcao via software, caso o carro
 * nao possua uma estabilizacao mecanica. Isto e, a direcao volta ao
 * centro automaticamente.
 */
byte rightState = 0;
byte leftState = 0;

byte lightsState = 0; // verifica qual a intensidade das luzes

byte buzzerState = 0; // verifica qual o estado do buzzer

byte command;  // nesta variavel sera guardado o byte enviado pelo
               // aplicativo
               
byte lights = 0;   // recebera o bit de comando das luzes
byte buzzerOn = 0; // recebera o bit de comando da buzina
byte front, back, left, right; // estas variaveis receberao os bits
                               // de direcao correspondentes

bool stability; // controle de estabilizacao
byte stabilityTimeLeft; // variavel de tempo de estabilizacao da Esquerda
byte stabilityTimeRight; // variavel de tempo de estabilizacao da Direita



int mode;

//interrupt
unsigned long currentMicros  = 0;

void setup() {

 // pinos das lampadas e da buzina
 pinMode(headlight, OUTPUT);
 pinMode(backlight, OUTPUT);
 pinMode(buzzer,OUTPUT);


// desativando som da buzina e luzes
 digitalWrite(headlight,LOW);
 digitalWrite(backlight,LOW);
 noTone(buzzer);

stability = 1;
stabilityTimeLeft = 16;
stabilityTimeRight = 16;

// Ativando a serial de conexao com o bluetooth.    
  mySerial.begin(9600);

}



void loop() 
{  

 /* Se nada for recebido pela serial, o estado do carrinho continua*/ 
  if (mySerial.available())
    {
    command = mySerial.read(); // recebe o byte da serial

    /*
     * Utilizando a operacao &, somente o bit correspondente e retor-
     *nara e sera transformado em 0 ou 1 pela operacao 'bool'
     */
    front = bool(command & 1);     //bit 1
    back = bool(command & 2);      //bit 2
    left = bool(command & 4);      //bit 3
    right = bool(command & 8);     //bit 4
    lights =  bool(command & 16);  //bit 5
    buzzerOn = bool(command & 32); //bit 6



    
    //Se o comando de direcao for enviado, sera ativado o estado
    // correspondente, direita ou esquerda
    if(right)
        rightState = 1;

    if(left)
        leftState = 1;

    
    //Neste ponto os motores serao acionados.
    
    if(front){
      mode = FORWARD;
      if(right){
        mode = FORWARD_RIGHT;
      } else if(left){
        mode = FORWARD_LEFT;
        }          
      }
      else if(back){
        mode = BACKWARD;
        if(right){
          mode = BACKWARD_RIGHT;
        } else if(left){
          mode = BACKWARD_LEFT;
          }          
      }
      else if(right){
        mode = RIGHT;
      }
        else if (left){
          mode = LEFT;
        }
        else{
          mode = ENDCODE;
        }


        trigger(mode); //executa a funcao de envio de codigo


/* Se a estabilizacao por software estiver ativada as funcionalidades
 * serao executadas.
 * O motor da direcao serao ativados por um breve periodo de tempo e 
 * desativado logo em seguida.
 */
    if(stability){
      // estavilizacao para a direita.
      if((!right) && (rightState)){
          trigger(LEFT); // envia codigo para esquerda
          delay(stabilityTimeRight);  
          trigger(ENDCODE);
          rightState = 0;
        }

      //estabilizacao para a esquerda.
      if((!left) && (leftState)){
          trigger(RIGHT);
          delay(stabilityTimeLeft);  
          trigger(ENDCODE);
          leftState = 0;
        }
    } //if(stability){


/*
 * Acionamento das luzes. O farol tera 3 niveis. Como esta com PWM
 * sera acionado em multiplos de 85 indicado pelo lightsState
 */

    // Cada vez que receber 1 no bit lights o bloco e executado
    if(lights){
      // vai acumulando 85 ate chegar a 340 que retorna novamente
      // a 0 (zero).
      lightsState = (lightsState + 85)%340; 
      analogWrite(headlight,lightsState); //PWM
      digitalWrite(backlight,lightsState);//direto, unico nivel.      
      }


    // Cada vez que receber 1 no bit buzzer a buzina e ativada ou
    // desativada.
    if(buzzerOn){

      buzzerState = !buzzerState; // troca o estado

      if(buzzerState){
        tone(buzzer,440); // frequencia da buzzina 440Hz.
      }
      else{
        noTone(buzzer);
      }
      
    } //if(buzzerOn){   

    
    
  } //if (mySerial.available())
  
 } // void loop ()
 


void trigger(int mode) {
  
  //start code sequence
  for (int w2 = 0; w2 < 4; w2++) {
    currentMicros = micros();
    while (micros() - currentMicros < 1500) {
      digitalWrite(ANTENNA, HIGH);
      digitalWrite(STATUS_LED, HIGH);
    }
    while (micros() - currentMicros < 2000) {
      digitalWrite(ANTENNA, LOW);
      digitalWrite(STATUS_LED, LOW);
    }
  }
  
  //function code sequence
  for (int w1 = 0; w1 < mode; w1++) {
    currentMicros = micros();
    while (micros() - currentMicros < 500) {
      digitalWrite(ANTENNA, HIGH);
      digitalWrite(STATUS_LED, HIGH);
    }
    while (micros() - currentMicros < 1000) {
      digitalWrite(ANTENNA, LOW);
      digitalWrite(STATUS_LED, LOW);
    }
  }

}
