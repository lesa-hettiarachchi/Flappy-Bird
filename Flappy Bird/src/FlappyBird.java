/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
/**
 *
 * @author lesandu
 */
public class FlappyBird extends JPanel implements ActionListener, KeyListener{

    /**
     * Creates new form FlappyBird
     */
    
    int boardWidth = 360;
    int boardHeight = 600;
    
    //Images
    Image backgroundImage;
    Image birdImage;
    Image topPipeImage;
    Image bottomPipeImage;
    
    //Bird
    int birdX =boardWidth/8;
    int birdY = boardHeight/2;
    int birdHight = 24;
    int birdWidth = 34; 

    
       
    class Bird{
        int x = birdX;
        int y = birdY;
        int hight = birdHight; 
        int width = birdWidth;
        Image img;

        Bird(Image img){
            this.img=img;
        }
    
    }
    
    //pipe
    int pipeX =boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    
    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed=false;
        
        Pipe(Image img){
            this.img=img;
        }
    }
    
    
    // game logic
    Bird bird;
    int velocityY=0;
    int velocityX=-4;
    int gravity = 1;
    
    ArrayList<Pipe> pipes;
    Random random = new Random();
    
    Timer gameLoop;
    Timer placePipesTimer;
    
    boolean gameOver = false; 
    double score =0;
    
    
    public FlappyBird() {
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setBackground(Color.blue);
        
        setFocusable(true);
        addKeyListener(this);
        
        //load Images
        
        backgroundImage = new ImageIcon(getClass().getResource("./view/flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./view/flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("./view/toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("./view/bottompipe.png")).getImage();
        
        //bird
        bird = new Bird (birdImage);
        pipes = new ArrayList<Pipe>();
        
        //place pipes
        placePipesTimer = new Timer(1500, (ActionEvent e) -> {
            placePipes();
        });
        placePipesTimer.start();
         
        //game timer
        gameLoop = new Timer(1000/60,this);
        gameLoop.start();
        
        
        
        
    }
    
    public void placePipes(){
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openningSpace = boardHeight/4;
        
        
        Pipe topPipe = new Pipe(topPipeImage);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);
      
        Pipe bottomPipe = new Pipe(bottomPipeImage);
        bottomPipe.y = topPipe.y+pipeHeight+openningSpace;
        pipes.add(bottomPipe);
      
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    
    }
    
    public void draw(Graphics g){
//        background

        g.drawImage(backgroundImage,0,0,boardWidth,boardHeight,null);
        g.drawImage(birdImage, bird.x, bird.y,bird.width,bird.hight,null); 
        
        //pipes
        for(int i =0;i<pipes.size();i++){
            Pipe pipe = pipes.get(i) ;
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width,pipe.height,null);
        }
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + (int) score, 10, 30);

        if (gameOver) {
            g.drawString("Game Over!", 120, boardHeight / 2);
            g.drawString("PRESS ENTER TO RESTART", 20, boardHeight / 2 + 30);
        }
    }

    public void move(){
        //bird
        velocityY+=gravity;
        bird.y+=velocityY;
        bird.y=Math.max(bird.y,0);
        
        for (int i =0; i<pipes.size();i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;
            
            if (!pipe.passed && bird.x > pipe.x+pipe.width){
                pipe.passed = true;
                score+=0.5;
            }
            
            if (collision(bird,pipe)){
                gameOver = true;
            }
        }
        
        if (bird.y>boardHeight){
            gameOver=true;
        }
    }
    
    public boolean collision(Bird a,Pipe b){
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.hight > b.y;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            velocityY=-9;
            
        }
        
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            if(gameOver){
                bird.y = birdY;
                velocityY=0;
                pipes.clear();
                score =0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyReleased(KeyEvent e){}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
