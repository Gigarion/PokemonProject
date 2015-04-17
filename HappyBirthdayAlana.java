    /******************************************************\
    * ////////////////////////Caleb Gum\\\\\\\\\\\\\\\\\\\\\
    * The running class for Alana's Birthday Party Program 
    \******************************************************/

import java.io.*;
import java.util.*;

public class HappyBirthdayAlana {
    
    private void Battle(String fileName, Player player) throws IOException {
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
        String battleBackground = s.next();
        StdAudio.play(s.next());
        // set this battle's background and audio
       
        Player enemy = new Player(s);
        s.close();
        
        boolean win = false;
        boolean lose = false;
        
        Display.openSequence(enemy, player);
        
        Message.challenge(enemy, player);
        // display challenge message
        
        Display.setBackground(battleBackground);        
        
        while (!(win || lose)) {
            Display.update();
            if (StdDraw.hasNextKeyTyped()) {
                switch
            }
        }
    }
    public static void main(String[] args)throws IOException {
        Pokemon[] team = new Pokemon[6];
        File pokemon = new File("pokemon.txt");
        Scanner toRead = new Scanner(pokemon);
        for (int i = 0; i < 6; i++)
            team[i] = Pokemon.fromFile(toRead);
    }
}