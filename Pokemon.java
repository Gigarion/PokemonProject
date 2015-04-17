  /***************************************************************\
  *  Name: Caleb Gum
  * 
  * This class contains the constructor and methods for the Pokemon
  * Class for Alana's birthday Program!  This object will have 
  * self contained stats, moves, and possibly abilities.  These
  * will be the basis for the few battles I program.  Modular enough
  * to allow for great enhancement.
  \***************************************************************/

import java.io.*;
import java.util.*;

public class Pokemon {
    private static final int MOVES = 4; // max number of moves because pokemon
    private int maxHealth;
    private int tempHealth;
    private int speed;
    private String name;
    private String sprite;
    private Move[] moves;
    
    public Pokemon (String name, int maxHealth, int speed, int attack, 
                    Move[] moveSet) {
        this.name = name;
        this.sprite = name + ".txt";
        this.maxHealth = maxHealth;
        this.tempHealth = maxHealth;
        this.speed = speed;
        this.moves = moveSet;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public String getName() {
        return name;
    }
    
    public int getHealth() {
        return tempHealth;
    }
    
    public Move getMove(int number) {
        return moves[number];
    }
    
    public void reset() {
        tempHealth = maxHealth;
    }
    
    public static Pokemon fromFile(Scanner s) throws IOException {
        /*********************************************************
        * fromFile requires the following format:
        * 
        * Pokemon name
        * integer maxhealth
        * integer speed
        * integer attack
        * setMove format, see Move.java
        *********************************************************/
        
        String n = s.next();
        int m = Integer.parseInt(s.next());
        int sp = Integer.parseInt(s.next());
        int a = Integer.parseInt(s.next());
        
        Move[] myMoves = new Move[MOVES];
        for (int i = 0; i < MOVES; i++) {
            myMoves[i] = Move.setMove(s);
        }
        s.close();
        
        Pokemon toReturn = new Pokemon(n, m, sp, a, myMoves);
        return toReturn;
    }
    
    public static void main(String[] args) throws IOException {
        File test = new File("pokemon.txt");
        Scanner scan = new Scanner(test);
        Pokemon tester = fromFile(scan);
        for (int i = 0; i < MOVES; i++) {
            System.out.println(tester.getMove(i).getName());
        }
    }
}