    /******************************************************\
    * ////////////////////////Caleb Gum\\\\\\\\\\\\\\\\\\\\\
    * The running class for Alana's Birthday Party Program 
    \******************************************************/

import java.io.*;
import java.util.*;
import java.awt.*;

public class HappyBirthdayAlana {
    private static final int UP = 87;
    private static final int DOWN = 83;
    private static final int LEFT = 65;
    private static final int RIGHT = 68;
    private static final int ENTER = 70;
    
    private static void battle(String fileName, Player player) throws IOException {
         /****************************************************\
         * Notes on the battle function:
         * 
         * the battle file must be laid out in the
         * following format:
         * 
         * background.extension
         * audiofile.extension
         * name of enemy player
         * size of enemy's team
         * list of pokemon in fromFile format, see pokemon.java
         \*****************************************************/
        
        File battle = new File(fileName);
        if (!battle.exists()) {
            System.out.println("file does not exist, fix it");
            return;
        }
        
        Scanner s = new Scanner(battle);
        // find the battle file and set up the scanner
        Display.setBackground("pipe.png");
        String battleBackground = s.next();
        Display.update();
        StdAudio.loop(s.next());
        // set this battle's background and audio
       
        Player enemy = new Player(s);
        Display.setEnemy(enemy);
        Display.setMain(player);
        s.close();
        
        boolean win = false;
        boolean lose = false;
        
        Display.openSequence(enemy, player, battleBackground);
        
        while (!(win || lose)) {
            
            if (StdDraw.isKeyPressed(UP)) {
                Display.upCursor();
                Display.update();
                Display.timeDelay();
            }                
            else if (StdDraw.isKeyPressed(DOWN)) {
                Display.downCursor();
                Display.update();
                Display.timeDelay();
            }                
            else if (StdDraw.isKeyPressed(LEFT)) {
                Display.leftCursor();
                Display.update();
                Display.timeDelay();
            }                
            else if (StdDraw.isKeyPressed(RIGHT)) {
                Display.rightCursor();
                Display.update();
                Display.timeDelay();
            }                
            else if (StdDraw.isKeyPressed(ENTER)) {
                Display.battleMenuAction();
                System.out.println("enter");
                Display.update();
                Display.timeDelay();
            }
        }
    }
    public static void main(String[] args)throws IOException {
        Display.setBounds();
        File readMain = new File("mainplayer.txt");
        Scanner read = new Scanner(readMain);
        Player player = new Player(read);
        battle("Battle1.txt", player);
    }
}