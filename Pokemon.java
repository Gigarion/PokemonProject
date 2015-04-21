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
    private Move[] moves;
    private boolean faint;
    private String status;

    private static final String[] STATS = {"no", "plyz", "psn", "slp"};
    
    public Pokemon (String name, int maxHealth, int speed, Move[] moveSet) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.tempHealth = maxHealth;
        this.speed = speed;
        this.moves = moveSet;
        this.faint = false;
        this.status = "no";
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getTempHealth() {
        return tempHealth;
    }

    public Move getMove(int number) {
        return moves[number];
    }

    public void receive(int dam) {
        tempHealth -= dam;
        if (tempHealth <= 0)
            faint = true;
    }
    
    public void heal(int health) {
        tempHealth += health;
        if (tempHealth > maxHealth)
            tempHealth = maxHealth;
    }

    public void reset() {
        faint = false;
        tempHealth = maxHealth;
    }

    public boolean isFaint() {
        return faint;
    }

    public static Pokemon fromFile(Scanner s) throws IOException {
        /*********************************************************
        * fromFile requires the following format:
        * 
        * Pokemon name
        * integer maxhealth
        * integer speed
        * setMove format, see Move.java
        *********************************************************/
        
        String n = s.next();
        int m = Integer.parseInt(s.next());
        int sp = Integer.parseInt(s.next());
        
        Move[] myMoves = new Move[MOVES];
        for (int i = 0; i < MOVES; i++) {
            myMoves[i] = Move.setMove(s);
        }
        
        Pokemon toReturn = new Pokemon(n, m, sp, myMoves);
        //System.out.println(n);
        return toReturn;
    }

    public void toFile(PrintWriter p) throws IOException {
        p.println(name);
        p.println(maxHealth);
        p.println(speed);
        for (int i = 0; i < MOVES; i++)
            moves[i].toFile(p);
    }

    public static void main(String[] args) throws IOException {
        File test = new File("pokemon.txt");
        Scanner scan = new Scanner(test);
        Pokemon tester = fromFile(scan);
        for (int i = 0; i < MOVES; i++) {
            System.out.println(tester.getMove(i).getName());
        }
        scan.close();
    }
}