/*******************************
  *  ////////Caleb Gum\\\\\\\\\
  * Move class to support the
  * Pokemon class for Alana's 
  * BP
  *******************************/
import java.util.*;
public class Move {
    String name;
    private int whoHit; // who can be hit by this move?  <0 for self only, 
                        // 0 for any target, >0 for opponent only.
    private String status; // detail what status effect if any occurs
    private int damage; // how much damage the move does
    private int accuracy; //  likelihood of hit
    
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
    
    public String getName() {
        return name;
    }
    
    public void makeMove(Pokemon user, Pokemon target) {
        int roll = (int) (Math.random() * 100);
        if (roll <= accuracy) {
            Message.makeMove(user, name);
        }
        else Message.textBuilder(user, Message.miss);
    }
}