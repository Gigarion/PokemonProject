/*******************************
  *  ////////Caleb Gum\\\\\\\\\
  * Move class to support the
  * Pokemon class for Alana's 
  * BP
  *******************************/
import java.util.*;
import java.io.*;

public class Move {
    String name;
    private int whoHit; // who can be hit by this move?  <0 for self only, 
                        // 0 for any target, >0 for opponent only.
    private String status; // detail what status effect if any occurs
    private int damage; // how much damage the move does
    private int accuracy; //  likelihood of hit
    private static final int SPER = 20;
    private static final int PLYZ = 45;
    
    public Move (String name, int targetable, String stat, int dam, int acc) {
        this.name = name;
        this.whoHit = targetable;
        this.status = stat;
        this.damage = dam;
        this.accuracy = acc;
    }
    
    public static Move setMove(Scanner s) {
        /***************************************
        * setMove requires the following format:
        * 
        * name of move
        * who is targeted, see above
        * if status, what status?
        * damage of move (negative if healing)
        * accuracy of move 
        ****************************************/
        String n = s.next();
        int t = Integer.parseInt(s.next());
        String st = s.next();
        int d = Integer.parseInt(s.next());
        int a = Integer.parseInt(s.next());
        Move toReturn = new Move(n, t, st, d, a);
        return toReturn;
    }

    public void toFile(PrintWriter p) {
        p.println(name);
        p.println(whoHit);
        p.println(status);
        p.println(damage);
        p.println(accuracy);
    }
    
    public String getName() {
        return name;
    }
    
    public int getTarget() {
        return whoHit;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void listStats() {
        System.out.println("Name: "         + name);
        System.out.println("whoHit: "     + whoHit);
        System.out.println("status: "     + status);
        System.out.println("damage: "     + damage);
        System.out.println("accuracy: " + accuracy);
    }

    public void makeMove(Pokemon t) {
        t.receive(damage);
    }
}