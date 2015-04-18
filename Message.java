  /*****************************************************\
  * Display utility, will use the common text window 
  * bottom screen for the ABPP 
  \*****************************************************/
public class Message {
    
    private static final String MISS = " missed!";
    private static final String FAINT = " fainted!";
    private static final String RUNTEXT = "Weak! You can't run from a Pokemon Battle!";
    private static String currentState;

    
    public static void textBuilder (Pokemon poke, String str) {
        String toBuild = poke.getName() + " " + str;
        currentState = toBuild;
    }

    public static void run() {
        currentState = RUNTEXT;
    }
    
    public static void makeMove(Pokemon poke, Move move) {
        currentState = poke.getName() + " used " + move.getName() + "!";
    }
    
    public static void customSet(String custom) {
        currentState = custom;
    }

    public static void fainted(Player play, Pokemon faint) {
        currentState = play.getName() + " 's " + faint.getName() + FAINT;
    }

    public static void challenge(Player enemy, Player main) {
        currentState = enemy.getName() + " has challenged " + main.getName()
            + "!";
        Display.messageUpdate();
    }

    public static void miss(Pokemon poke) {
        currentState = poke.getName() + MISS;
    }

    public static String getMessage() {
        return currentState;
    }
}