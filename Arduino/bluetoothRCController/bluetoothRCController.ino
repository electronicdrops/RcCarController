/*
 * 201605021932
 * Este sketch e a primeira versao de comando de uma carro de
 * controle remoto por arduino.
 * A ideia e utilizar o arduino para realizar as mesmas funcoes
 * do controle remoto padrao e poder adicionar novas funcionalidades
 * que nao se encontram na versao original de fabrica
 * O controle remoto padrao foi substituido por uma bluetooth
 * shield.
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

/*
 * Nesta versao estamos utilizando um motorshield que controla ate 02
 * motores DC atraves dos pinos IN1, IN2,IN3,IN4.
 */

// Pinos de controle do motorshield LN298D
// Os pinos escolhidos sao PWM uma possivel futura funcionalidade.
int IN1 = 3; // para frente
int IN2 = 5; // para tras
int IN3 = 6; // vira esquerda
int IN4 = 9; // vira direita


int headlight = 11; // o farol sera ligado no pino 11 pwm e tera 3
                    // niveis de intensidade.
int backlight = 12; // sao as luzes traseiras 
int buzzer = 10;    // buzina do carrinho.


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

void setup()
{

 //Configurando os pinos de saida do motorshield 
 pinMode(IN1, OUTPUT);
 pinMode(IN2, OUTPUT);
 pinMode(IN3, OUTPUT);
 pinMode(IN4, OUTPUT);

// pinos das lampadas e da buzina
 pinMode(headlight, OUTPUT);
 pinMode(backlight, OUTPUT);
 pinMode(buzzer,OUTPUT);

// desativando todas as ligacoes dos motores
 digitalWrite(IN1,LOW);
 digitalWrite(IN2,LOW);
 digitalWrite(IN3,LOW);
 digitalWrite(IN4,LOW);

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
    digitalWrite(IN3,left);
    digitalWrite(IN4,right); 
    digitalWrite(IN1,front);
    digitalWrite(IN2,back);


/* Se a estabilizacao por software estiver ativada as funcionalidades
 * serao executadas.
 * O motor da direcao serao ativados por um breve periodo de tempo e 
 * desativado logo em seguida.
 */
    if(stability){
      // estavilizacao para a direita.
      if((!right) && (rightState)){
          digitalWrite(IN3,HIGH);
          delay(stabilityTimeRight);  
          digitalWrite(IN3,LOW);
          rightState = 0;
        }

      //estabilizacao para a esquerda.
      if((!left) && (leftState)){
          digitalWrite(IN4,HIGH);
          delay(stabilityTimeLeft);  
          digitalWrite(IN4,LOW);
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

