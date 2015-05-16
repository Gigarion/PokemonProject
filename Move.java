
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
    private int ppMax;
    private int ppTemp;
    
    public Move (String name, int targetable, String stat, int dam, int acc, int sAcc, int pp) {
        this.name = name;
        this.whoHit = targetable;
        this.status = stat;
        this.damage = dam;
        this.accuracy = acc;
        this.statAcc = sAcc;
        this.ppMax = pp;
        this.ppTemp = ppMax;
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
        String n = move.nextLine();
        int t = Integer.parseInt(move.nextLine());
        String st = move.nextLine();
        int d = Integer.parseInt(move.nextLine());
        int a = Integer.parseInt(move.nextLine());
        int sa = Integer.parseInt(move.nextLine());
        int p = Integer.parseInt(move.nextLine());
        move.close();
        Move toReturn = new Move(n, t, st, d, a, sa, p);
        return toReturn;
    }

    public void toFile(PrintWriter p) {
        p.println(name);
        p.println(whoHit);
        p.println(status);
        p.println(damage);
        p.println(accuracy);
        p.println(statAcc);
        p.println(ppMax);
    }
    
    public String getName() {
        return name;
    }
    
    public int getTarget() {
        return whoHit;
    }

    public int getDamage() {
        return damage;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public String getStatus() {
        return status;
    }

    public int getStatAccuracy() {
        return statAcc;
    }

    public int[] getPP() {
        int[] toReturn = {ppTemp, ppMax};
        return toReturn;
    }

    public boolean canUse() {
        if (ppTemp == 0) return false;
        else return true;
    }

    public void reduce() {
        ppTemp--;
    }

    public void reset() {
        ppTemp = ppMax;
    }
    public void listStats() {
        System.out.println("Name: "         + name);
        System.out.println("whoHit: "     + whoHit);
        System.out.println("status: "     + status);
        System.out.println("damage: "     + damage);
        System.out.println("accuracy: " + accuracy);
    }
}