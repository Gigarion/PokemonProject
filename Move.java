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
    private int statAcc;
    
    public Move (String name, int targetable, String stat, int dam, int acc, int sAcc) {
        this.name = name;
        this.whoHit = targetable;
        this.status = stat;
        this.damage = dam;
        this.accuracy = acc;
        this.statAcc = sAcc;
    }
    
    public static Move setMove(Scanner s) throws IOException {
        /***************************************
        * setMove requires the following format:
        * 
        * name of move
        * who is targeted, see above
        * if status, what status?
        * damage of move (negative if healing)
        * accuracy of move 
        * adjusted accuracy of any status effect of the move
        ****************************************/
        File toRead = new File("moves\\" + s.next() + ".txt");
        Scanner move = new Scanner(toRead);
        String n = move.next();
        int t = Integer.parseInt(move.next());
        String st = move.next();
        int d = Integer.parseInt(move.next());
        int a = Integer.parseInt(move.next());
        int sa = Integer.parseInt(move.next());
        move.close();
        Move toReturn = new Move(n, t, st, d, a, sa);
        return toReturn;
    }

    public void toFile(PrintWriter p) {
        p.println(name);
        p.println(whoHit);
        p.println(status);
        p.println(damage);
        p.println(accuracy);
        p.println(statAcc);
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
        if (Math.random() * 100.0 < statAcc)  {
            t.receive(damage, status);
            System.out.println("working");
        }
        else t.receive(damage, "no");
    }
}