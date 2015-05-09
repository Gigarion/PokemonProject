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

    public Move[] getMoves() {
        return moves;
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
    
    public void heal(int health, String cure) {
        tempHealth += health;
        if (tempHealth > maxHealth)
            tempHealth = maxHealth;
        if (cure == status)
            status = "no";
        if (cure.equals("REVIVE")) {
            tempHealth = maxHealth / 2;
        }
    }

    public void reset() {
        faint = false;
        tempHealth = maxHealth;
        status = "no";
    }

    public boolean isFaint() {
        if (tempHealth > 0)
            faint = false;
            
        else { 
            faint = true;
            status = "no";
        }
        return faint;
    }

    public static Pokemon fromFile(File f) throws IOException {
        Scanner readPoke = new Scanner(f);
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

    public void receive(int dam, String stat) {
        tempHealth -= dam;
        if (tempHealth <= 0)
            faint = true;
        if (status.equals("no"))
            status = stat;
        if (stat.equals("cleanse"))
            status = "no";
    }

    public void useMove(int moveNum, Pokemon target) {
        Move toUse = moves[moveNum];
        int whoHit = toUse.getTarget();
        String effect = toUse.getStatus();
        toUse.reduce();

        int damage = toUse.getDamage();
        if (status.equals("BRN"))
            damage /= 2;
        if ((Math.random() * 100) < toUse.getStatAccuracy() && !toUse.getStatus().equals("no")) {
            if (target.getStatus().equals("no"))
                Message.status(target, effect);
            else if (damage == 0 && !effect.equals("no")) Message.noEffect();
            target.receive(damage, effect);
            Display.timeDelay();
        }
        else target.receive(damage, "no");
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