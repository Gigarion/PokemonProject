  /*****************************************************\
  * Display utility, will use the common text window 
  * bottom screen for the ABPP 
  \*****************************************************/

public class Message {
    private double[] xBorder = {0, 0, 1, 1};
    private double[] yBorder = {0, 1, 1, 0};
    private static final double borderSize = 0.04;
    
    public static String miss = "missed!";
    public static String currentState;

    
    public static void textBuilder (Pokemon poke, String str) {
        String toBuild = poke.getName() + " " + str;
        currentState = toBuild;
    }
    
    public static void makeMove(Pokemon poke, String moveName) {
        currentState = poke.getName() + " used " + moveName + "!";
    }
    
    public static void challenge(Player enemy, Player main) {
        currentState = enemy.getName() + " has challenged " + main.getName()
            + "!";
    }
}