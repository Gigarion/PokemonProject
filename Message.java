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
    
    public static void pokeMenu() {
        currentState = "Choose a Pokemon";
    }

    public static void challenge(Player enemy, Player main) {
        currentState = enemy.getName() + " has challenged " + main.getName()
            + "!";
        Display.messageUpdate();
    }

    public static void miss(Pokemon poke) {
        currentState = poke.getName() + MISS;
    }

    public static void miss(Player enemy, Pokemon out) {
        currentState = enemy.getName() + "'s " + out.getName() + " missed!";
    }

    public static void endBattle(Player winner, Player loser) {
        currentState = winner.getName() + " has defeated " + loser.getName() + "!";
    }

    public static void retort(Player loser) {
        currentState = loser.getName() + ":  Grr.. Who do you even know here?";
    }

    public static void smirk(Player winner) {
        currentState = winner.getName() + ": Ha! I knew you didn't have it in you";
    }
    public static String getMessage() {
        return currentState;
    }

    public static void alreadyOut(Pokemon poke) {
        currentState = poke.getName() + " is already out!";
    }

    public static void pickPokemon(Pokemon poke) {
        if (!poke.isFaint()) {
            currentState = "Send out " + poke.getName() + "?";
            Display.addDepth();
        }
        else currentState = poke.getName() + "has fainted! You cant sent it out";
    }

    public static void decide(Pokemon poke) {
        currentState = "What will " + poke.getName() + " do?";
    }

    public static void item() {
        currentState = "Use on which Pokemon?";
    }

    public static void useItem(Item toUse, Pokemon poke) {
        currentState = "Use " + toUse.getName() + " on " + poke.getName() + "?";
    }

    public static void usedItem(Item toUse, Pokemon poke) {
        currentState = Display.getPlayer().getName() + " used a " + toUse.getName() + " on " + poke.getName();
    }

    public static void outOfItem(Item toUse) {
        currentState = "Out of " + toUse.getName() + ".";
    } 

    public static void thatsEnough(Pokemon poke) {
        currentState = "That's enough " + poke.getName() + "!";
    }

    public static void flip() {
        currentState = "Switch Pokemon?";
    }

    public static void poison(Pokemon poke) {
        currentState = poke.getName() + " is hurt by poison!";
    }

    public static void paralyze(Pokemon poke) {
        currentState = poke.getName() + " is paralyzed! It can't move!";
    }

    public static void burn(Pokemon poke) {
        currentState = poke.getName() + " is hurt by its burn!";
    }

    public static void noEffect() {
        currentState = "It had no effect!";
    }

    public static void status(Pokemon target, String stat) {
        String expand = "";
        switch(stat) {
            case "PAR": expand = "PARALYZED"; break;
            case "PSN": expand = "POISONED"; break;
            case "BRN": expand = "BURNED"; break;
        }
        currentState = target.getName() + " has been " + expand;
    }

    public static void noItemEffect() {
        currentState = "It would have no effect!";
    }

    public static void ball(Ball ball) {
        currentState = "Throw " + ball.getName() + " ?";
    }

    public static void notWild() {
        currentState = "You can't catch another person's Pokemon!";
    }

    public static void escaped(Pokemon jailbird) {
        currentState = jailbird.getName() + " broke free!";
    }

    public static void winWild(Player enemy) {
        currentState = enemy.getName() + " has been defeated!";
    }

    public static void loseWild(Player enemy, Player main) {
        currentState = enemy.getName() + " has defeated " + main.getName();
    }

    public static void wildAppear(Player enemy) {
        currentState = "A " + enemy.getName() + " has appeared!";
    }

    public static void capture(Player enemy) {
        currentState = enemy.getName() + " has been caught!";
    }

    public static void enBurn(Pokemon p) {
        currentState = "Enemy " + p.getName() + " is hurt by its burn!";
    }

    public static void enPoison(Pokemon p) {
        currentState = "Enemy " + p.getName() + " is hurt by poison!";
    }

    public static void moveToBox(Pokemon p) {
        currentState = p.getName() + " was moved to the pc.";
    }

    public static void save() {
        currentState = "Game Saved!";
    }

    public static void pc() {
        currentState = "pc";
    }
}