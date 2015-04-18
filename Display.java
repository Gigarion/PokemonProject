import java.io.*;
public class Display {
    private static final String[] BMENU = {"fight", "pokemon", "bag", "run", "main"};
    private static final String[] WORLDMENU = {"null"};
    private static String currentMenu;

    private static final String cImage = "cursor.gif";

    private static final double[] MESSX = {-1, -1,  .5, .5};
    private static final double[] MESSY = {-1,-.8, -.8, -1};

    private static final double[] MENUX = {.5,  1,   1,  .5};
    private static final double[] MENUY = {-1, -1, -.8, -.8};

    private static final double BORDER = 0.004;

    private static final int SCREENH = 768;
    private static final int SCREENW = 1024;

    private static String background;

    private static final double EPX = 0.75;
    private static final double EPY = 0.75;
    private static final double PPX = -0.75;
    private static final double PPY = -0.6;

    private static final double ENWID = .15;
    private static final double ENHI = .3;

    private static final double PWID = .225;
    private static final double PHI = .45;

    private static String decide;

    private static boolean battle;
    private static boolean menu;
    private static boolean world;
    private static boolean message;
    private static boolean enPoke;
    private static boolean mainPoke;

    private static Player main;
    private static Player enemy;

    private static int enPokeOut;
    private static int mainPokeOut;

    private static int cursor;


    // Thus ends the hella constant block

    public static void setEnemy(Player e) {
        enemy = e;
    }

    public static void setMain(Player m) {
        main = m;
    }

    public static void setBackground(String s) {
        background = s;
    }
    
    public static String getBackground() {
        return background;
    }
    
    public static void messageUpdate() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledPolygon(MESSX, MESSY);
        StdDraw.setPenRadius(BORDER);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.polygon(MESSX, MESSY);
        StdDraw.setPenRadius();
        StdDraw.text(-.75, -.875, Message.getMessage());
    }

    public static void menuUpdate() {
        StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledPolygon(MENUX, MENUY);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(BORDER);
            StdDraw.polygon(MENUX, MENUY);

            StdDraw.text(0.6, -0.88, BMENU[0]);
            StdDraw.text(0.63, -0.95, BMENU[1]);
            StdDraw.text(0.8, -0.88, BMENU[2]);
            StdDraw.text(0.8, -0.95, BMENU[3]);

            int spot = getBattleCursorLocation();
            switch(spot) {
                case 0: StdDraw.picture(0.55, -0.88, cImage); break;
                case 1: StdDraw.picture(0.55, -0.95, cImage); break;
                case 2: StdDraw.picture(0.75, -0.88, cImage); break;
                case 3: StdDraw.picture(0.75, -0.95, cImage); break;
                default: StdDraw.picture(0.55, -0.88, cImage); break;
            }


            //StdDraw.text();
    }

    public static void update() {
        StdDraw.picture(0, 0, background);
        if (enPoke) {
            StdDraw.picture(EPX, EPY, enemy.getPokemon(enPokeOut) + ".png");
        }
        
        if (mainPoke) 
            StdDraw.picture(PPX, PPY, main.getPokemon(mainPokeOut) + ".png");

        if (message) {
            messageUpdate();
        }
        if (menu) {
            menuUpdate();
        }
        
    }
    
    private static void fadeOut(String newBackground) throws IOException {
        for (double i = 0; i < 2; i += 0.05) {
            StdDraw.picture(0, 0, background);
            StdDraw.filledCircle(0, 0, i);
            StdDraw.show(10);
        }
        background = newBackground;
        for (double i = 2; i >= 0; i -= 0.05) {
            StdDraw.picture(0, 0, background);
            StdDraw.filledCircle(0, 0, i);
            StdDraw.show(10);
        }
        StdDraw.picture(0, 0, background);
        StdDraw.show();
    }
    
    public static void openSequence(Player enemy, Player main, String newBackground) throws IOException {
        fadeOut(newBackground);
        battle = true;
        menu = true;
        showPlayers(enemy, main);
    }

    public static void timeDelay() {
        double a = System.currentTimeMillis();
        while (System.currentTimeMillis() - a < 500) {
        }
    }

    private static void showPlayers(Player enemy, Player main) {
        StdDraw.picture(0, 0, background);
        StdDraw.picture(EPX, EPY, enemy.getImage(), ENWID, ENHI);
        StdDraw.picture(PPX, PPY, main.getImage(), PWID, PHI);
        StdDraw.show();
        message = true;
        menu = true;
        Message.challenge(enemy, main);
        menuUpdate();
        StdDraw.show();
        do  {
        } while (!StdDraw.hasNextKeyTyped());
        message = true;
        
        for (double i = 0; i < 2; i += 0.02) {
            update();
            StdDraw.picture(EPX + i, EPY, enemy.getImage(), ENWID, ENHI);
            StdDraw.picture(PPX - i, PPY, main.getImage(), PWID, PHI);
            StdDraw.show(10);
            StdDraw.clear(StdDraw.GRAY);
        }
        
        StdDraw.picture(0, 0, background);
        String enemyOut = enemy.getName() + " sent out a " + enemy.getPokemon(0) + "!";
        Message.customSet(enemyOut);
        messageUpdate();
        for (double i = 2; i > 0; i -= 0.02) {
            update();
            StdDraw.picture(EPX + i, EPY, enemy.getPokemon(0) + ".png");
            StdDraw.show(10);
            StdDraw.clear(StdDraw.GRAY);
        }
        enPoke = true;
        enPokeOut = 0;
        update();
        StdDraw.show();

        String playerOut = "Go, " + main.getPokemon(0) + "!";
        Message.customSet(playerOut);
        messageUpdate();
        for (double i = 2; i > 0; i -= 0.02) {
            update();
            StdDraw.picture(PPX - i, PPY, main.getPokemon(0) + ".png");
            StdDraw.show(10);
            StdDraw.clear(StdDraw.GRAY);
        }
        mainPoke = true;
        mainPokeOut = 0;
        decide = "What will " + main.getName() + " do?";
        Message.customSet(decide);
        update();
        StdDraw.show();
    }
    
    public static void setBounds() {
        StdDraw.setCanvasSize(SCREENW, SCREENH);
        StdDraw.setXscale(-1, 1);
        StdDraw.setYscale(-1, 1);
    }

    public static void upCursor() {
        if (cursor != 0) cursor--;
    }

    public static void downCursor() {
        if (cursor != 3) cursor++;
    }

    public static void rightCursor() {
        cursor += 2;
    }

    public static void leftCursor() {
        cursor -= 2;
    }

    public static void enterBattle() {
        battle = true;
        world = false;
    }

    public static int getBattleCursorLocation() {
        int toReturn = Math.abs(cursor % 4);
        return toReturn;
    }

    public static String getMenuType() {
        return currentMenu;
    }

    public static void battleMenuAction() {
        int spot = getBattleCursorLocation();
        currentMenu = BMENU[spot];
    }

    public static void main(String[] args) throws IOException {
        double a = System.currentTimeMillis();
        cursor = 0;
        while(System.currentTimeMillis() - a < 1000) {}
        setBounds();
        StdDraw.clear(StdDraw.GRAY);
        setBackground("shield.png");
        menu = true;
        //StdAudio.loop("music1.mid");
        Player pipeGuy = new Player("pipe.png");
        Player shieldGuy = new Player("shield.png");
        openSequence(pipeGuy, shieldGuy, "pipe.png");
        message = true;
        update();
    }
}