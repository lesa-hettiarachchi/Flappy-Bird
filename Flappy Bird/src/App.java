
import javax.swing.JFrame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ramani
 */
public class App {
    public static void main(String[] args) throws Exception{
        
        int boardWidth = 360;
        int boardHight = 600;
        
        JFrame frame = new JFrame("Flappy Bird");
        
        frame.setSize(boardWidth,boardHight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        FlappyBird  flappybird = new FlappyBird();
        frame.add(flappybird);
        frame.pack();
        
        flappybird.requestFocus();
        frame.setVisible(true);
        
        
    }
}
