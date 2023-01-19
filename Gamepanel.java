import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

class GamePanel extends JPanel implements ActionListener{

static final int Screenwidth = 1280;
static final int height = 720;
static final int unitsize = 50;
static final int GAME_UNITS = (Screenwidth*height)/unitsize;
static final int DELAY = 175;
final int x[] = new int[GAME_UNITS];
final int y[] = new int[GAME_UNITS];
int bodyParts = 6;
int applesEaten;
int appleX;
int appleY;
char direction = 'R';
boolean running = false;
Timer timer;
Random random;

GamePanel(){
random = new Random();
this.setPreferredSize(new Dimension(Screenwidth,height));
this.setBackground(Color.black);
this.setFocusable(true);
this.addKeyListener(new MyKeyAdapter());
startGame();
}
public void startGame() {
newApple();
running = true;
timer = new Timer(DELAY,this);
timer.start();
}
public void paintComponent(Graphics g) {
super.paintComponent(g);
draw(g);
}
public void draw(Graphics g) {

if(running) {

for(int i=0;i<height/unitsize;i++) {
g.drawLine(i*unitsize, 0, i*unitsize, height);
g.drawLine(0, i*unitsize, Screenwidth, i*unitsize);
}
g.setColor(Color.red);
g.fillOval(appleX, appleY, unitsize, unitsize);

for(int i = 0; i< bodyParts;i++) {
if(i == 0) {
g.setColor(Color.green);
g.fillRect(x[i], y[i], unitsize, unitsize);
}
else {
g.setColor(new Color(45,180,0));
//g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
g.fillRect(x[i], y[i], unitsize, unitsize);
}
}
g.setColor(Color.red);
g.setFont( new Font("Ink Free",Font.BOLD, 40));
FontMetrics metrics = getFontMetrics(g.getFont());
g.drawString("Score: "+applesEaten, (Screenwidth - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
}
else {
gameOver(g);
}

}
public void newApple(){
appleX = random.nextInt((int)(Screenwidth/unitsize))*unitsize;
appleY = random.nextInt((int)(height/unitsize))*unitsize;
}
public void move(){
for(int i = bodyParts;i>0;i--) {
x[i] = x[i-1];
y[i] = y[i-1];
}

switch(direction) {
case 'U':
y[0] = y[0] - unitsize;
break;
case 'D':
y[0] = y[0] + unitsize;
break;
case 'L':
x[0] = x[0] - unitsize;
break;
case 'R':
x[0] = x[0] + unitsize;
break;
}

}
public void checkApple() {
if((x[0] == appleX) && (y[0] == appleY)) {
bodyParts++;
applesEaten++;
newApple();
}
}
public void checkCollisions() {
//checks if head collides with body
for(int i = bodyParts;i>0;i--) {
if((x[0] == x[i])&& (y[0] == y[i])) {
running = false;
}
}
//check if head touches left border
if(x[0] < 0) {
running = false;
}
//check if head touches right border
if(x[0] > Screenwidth) {
running = false;
}
//check if head touches top border
if(y[0] < 0) {
running = false;
}
//check if head touches bottom border
if(y[0] > height) {
running = false;
}

if(!running) {
timer.stop();
}
}
public void gameOver(Graphics g) {
//Score
g.setColor(Color.red);
g.setFont( new Font("Ink Free",Font.BOLD, 40));
FontMetrics metrics1 = getFontMetrics(g.getFont());
g.drawString("Score: "+applesEaten, (Screenwidth - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
//Game Over text
g.setColor(Color.red);
g.setFont( new Font("Ink Free",Font.BOLD, 75));
FontMetrics metrics2 = getFontMetrics(g.getFont());
g.drawString("Game Over", (Screenwidth - metrics2.stringWidth("Game Over"))/2, height/2);
}
@Override
public void actionPerformed(ActionEvent e) {

if(running) {
move();
checkApple();
checkCollisions();
}
repaint();
}

public class MyKeyAdapter extends KeyAdapter{
@Override
public void keyPressed(KeyEvent e) {
switch(e.getKeyCode()) {
case KeyEvent.VK_LEFT:
if(direction != 'R') {
direction = 'L';
}
break;
case KeyEvent.VK_RIGHT:
if(direction != 'L') {
direction = 'R';
}
break;
case KeyEvent.VK_UP:
if(direction != 'D') {
direction = 'U';
}
break;
case KeyEvent.VK_DOWN:
if(direction != 'U') {
direction = 'D';
}
break;
}
}
}
}
