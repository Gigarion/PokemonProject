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

    private static final String[] STATS = {"no", "PAR", "PSN", "SLP", "BRN"};
    
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

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return "Pokemon\\" + name + ".png";
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
        if (tempHealth > 0) faint = false;
        else faint = true;
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
        File poke = new File(n);
        Scanner readPoke = new Scanner(poke);
        String name = readPoke.next();
        int m = Integer.parseInt(readPoke.next());
        int sp = Integer.parseInt(readPoke.next());
        
        Move[] myMoves = new Move[MOVES];
        for (int i = 0; i < MOVES; i++) {
            myMoves[i] = Move.setMove(readPoke);
        }
        readPoke.close();
        Pokemon toReturn = new Pokemon(name, m, sp, myMoves);
        return toReturn;
    }

    public void toFile(PrintWriter p) throws IOException {
        p.println(name);
        p.println(maxHealth);
        p.println(speed);
        for (int i = 0; i < MOVES; i++)
            moves[i].toFile(p);
    }

    public void receive(int dam, String stat) {
        tempHealth -= dam;
        if (tempHealth <= 0)
            faint = true;
        if (status.equals("no"))
            status = stat;
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