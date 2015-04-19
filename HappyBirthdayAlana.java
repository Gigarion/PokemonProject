    /******************************************************\
    * ////////////////////////Caleb Gum\\\\\\\\\\\\\\\\\\\\\
    * The running class for Alana's Birthday Party Program 
    \******************************************************/

import java.io.*;
import java.util.*;

public class HappyBirthdayAlana {
    private static final int UP = 87;
    private static final int DOWN = 83;
    private static final int LEFT = 65;
    private static final int RIGHT = 68;
    public static final int ENTER = 32;

    private static boolean win;
    private static boolean lose;

    public static void winBattle() {
        win = true;
    }

    public static void loseBattle() {
        lose = true;
    }
    
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
        Display.setBackground("shield.png");
        String battleBackground = s.next();
        Display.update();
        StdAudio.loop(s.next());
        // set this battle's background and audio
       
        Player enemy = new Player(s);
        Display.setEnemy(enemy);
        Display.setMain(player);
        s.close();
        
        win = false;
        lose = false;
        
        Display.openSequence(battleBackground);
        while (!(win || lose)) {
            
            if (StdDraw.isKeyPressed(UP)) {
                Display.upCursor();
                Display.update();
                Display.timeDelay();
            }                
            else if (StdDraw.isKeyPressed(DOWN)) {
                Display.downCursor();
                if (!(win || lose))
                Display.update();
                Display.timeDelay();
            }                
            else if (StdDraw.isKeyPressed(LEFT)) { 
                Display.leftCursor();
                if (!(win || lose))
                    Display.update();
                Display.timeDelay();
            }                
            else if (StdDraw.isKeyPressed(RIGHT)) {
                Display.rightCursor();
                if (!(win || lose))
                    Display.update();
                Display.timeDelay();
            }                
            else if (StdDraw.isKeyPressed(ENTER)) {
                Display.battleMenuAction();
                Display.update();
                Display.timeDelay();
            }
        }
        System.out.println("im free");
        if (win) {
            StdAudio.play("win.mid");
            Display.winSequence(enemy, player);
        }
        else if (lose) {
            System.out.println("ya lost sucker");
            Display.loseSequence(enemy, player);
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