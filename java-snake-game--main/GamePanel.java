import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


import javax.swing.JPanel;




public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 500;
    static final int SCREEN_HEIGHT = 500;
    static final int UNIT_SIZE = 20;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/ UNIT_SIZE;
    static final int DELAY = 100;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 2;
    int  applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false; 
    Timer timer;
    Random random;

    //this is the game panel where the game will be housed 
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.green);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    //Starts the game 
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }//draws the layout of the game 
    public void draw(Graphics g){
        if(running){
        
            for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++){
                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(appleX,appleY, UNIT_SIZE, UNIT_SIZE);

            for(int i = 0; i < bodyParts; i++) {
                if(i == 0){
                    g.setColor(Color.yellow);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else{
                    g.setColor(new Color(180, 138, 0));
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("BALL: "+ applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Sizdin jiynagan baliniz!!: "+ applesEaten))/2, g.getFont().getSize());
        } 
        else {
            gameOver(g);
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }//Movement for the game 
    public void move(){
        for(int i = bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case 'U':
                y[0] =y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] =y[0] +  UNIT_SIZE;
                break;
            case 'L':
                x[0] =x[0] -  UNIT_SIZE;
                break;
            case 'R':
                x[0] =x[0] +  UNIT_SIZE;
                break;
 
        }
    }
    public void checkApple(){
        if((x[0] == appleX) && (y[0]== appleY)){
            bodyParts++;
            applesEaten++;
            newApple();

        }
    }
    public void checkCollisions(){
        //checks if head collides with body
        for(int i = bodyParts; i >0; i--) {
            if((x[0] == x[i])&&(y[0]== y[i])) {
                running = false;
            }
        }
        //checks if head touches left border
        if(x[0]<0){
            running = false; 
        }
        //chec if head touches right border 
        if(x[0]>SCREEN_WIDTH){
            running = false; 
        }
        //if head touhces top border
        if(y[0]<0){
            running = false; 
        }
        //if head touhces top bottom border
        if(y[0]> SCREEN_WIDTH){
            running = false; 
        }

        if(!running){
            timer.stop();
        }
    }//sets the cases for when the game is over 
    public void gameOver(Graphics g){
        //GameOver Text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.CENTER_BASELINE,50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Siz utildiniz!!!", (SCREEN_WIDTH - metrics.stringWidth("Oyin tawsildi"))/2, SCREEN_HEIGHT/2);
    }
    @Override 
    public void actionPerformed(ActionEvent e){
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override 
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()) {
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