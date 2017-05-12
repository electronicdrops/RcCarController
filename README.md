# Carro de controle remoto RF 
### Controlado por Arduino + Android + Bluetooth
#### Tudo disponível aqui para seu conhecimento!

##### P.S:Caso deseja apenas o aplicativo acesse: https://play.google.com/store/apps/details?id=bluecontrol.game.olivato.bluetoothcontroller&hl=pt_BR 

link dos vídeos de apreentação do projeto no youtube: https://www.youtube.com/watch?v=pfWRfwmsxA0
 
 *Explicações do aplicativo:*
 O app envia 1 (um) byte de dados para o módulo bluetooth do arduino, os valores enviados pelo app são (Caso você não queira ler o código (Nada te impede de alterar também)):

 * Frente (1)
 * Trás (2)
 * Esquerda (4)
 * Direita (8)
 * FrenteEsquerda(5)
 * FrenteDireita(9)
 * TrásEsquerda(6)
 * TrásDireita(10)
  * Farol (16)
  * Buzina (32)
 
Esses códigos são pegos pelo sketch do Arduino e executa suas funções correspondentes.

Existem 3 versões do Sketch do Arduíno para este projeto.

1 - MotorShieldL298D:
   - O sketch que está nesta pasta é correspondente a versão que retira toda a eletrônica do Carrinho de Controle Remoto e comanda os motores através de um shield de Dupla ponte H. Este sketch pode ser alterado para controlar qualquer tipo de carrinho que possua motores e um motorshield.

2 - SmartCar2w:
   - O sketch desta página já está modificado para funcionar com o projeto smart car 2w que é movimentado por dois motores nas rodas, ou por um que tenha 4 rodas mas motores somente em duas delas.

3 - Tx2Rx2Control:
   - O sketch desta página fuciona somente com carrinhos de controle remoto que possuem o par de chip Rx2/Tx2. Neste sketch existe um função que gera os códigos rf necessários para movimentar o carrinho.
