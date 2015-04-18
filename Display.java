import java.io.*;
public class Display {
    private static final String[] BATTLEMENU = {"fight", "poke", "bag", "run", "main"};
    private static final String[] WORLDMENU = {"null"};
    private static String currentMenu;

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
    private static final double PPY = -0.75;

    private static final double ENWID = .15;
    private static final double ENHI = .3;

    private static final double PWID = .15;
    private static final double PHI = .3;

    private static boolean battle;
    private static boolean menu;
    private static boolean world;
    private static boolean message;

    private static int cursor;




    // Thus ends the hella constant block

    public static void setBackground(String s) {
        background = s;
    }
    
    public static String getBackground() {
        return background;
    }
    
    public static void messageUpdate() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setPenRadius(BORDER);
        StdDraw.filledPolygon(MESSX, MESSY);
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.polygon(MESSX, MESSY);
        StdDraw.text(-.25, -.875, Message.getMessage());
    }

    public static void update() {
        StdDraw.picture(0, 0, background);
        if (message) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledPolygon(MESSX, MESSY);
            StdDraw.setPenRadius(BORDER);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.polygon(MESSX, MESSY);
            StdDraw.setPenRadius();
            StdDraw.text(-.25, -.875, Message.getMessage());
        }
        if (menu) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledPolygon(MENUX, MENUY);
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
        showPlayers(enemy, main);
    }

    private static void showPlayers(Player enemy, Player main) {
        StdDraw.picture(0, 0, background);
        StdDraw.picture(EPX, EPY, enemy.getImage(), ENWID, ENHI);
        StdDraw.picture(PPX, PPY, main.getImage(), PWID, PHI);
        StdDraw.show();
        message = true;
        Message.challenge(enemy, main);
        StdDraw.show();
        do  {
        } while (!StdDraw.hasNextKeyTyped());
        message = false;
        for (double i = 0; i < 2; i += 0.02) {
            StdDraw.picture(0, 0, background);
            StdDraw.picture(EPX + i, EPY, enemy.getImage(), ENWID, ENHI);
            StdDraw.picture(PPX - i, PPY, main.getImage(), PWID, PHI);
            messageUpdate();
            StdDraw.show(10);
            StdDraw.clear(StdDraw.GRAY);
        }
        StdDraw.picture(0, 0, background);
        messageUpdate();
        StdDraw.show();
    }
    
    public static void setBounds() {
        StdDraw.setCanvasSize(SCREENW, SCREENH);
        StdDraw.setXscale(-1, 1);
        StdDraw.setYscale(-1, 1);
    }

    public static void upCursor() {
        cursor++;
    }

    public static void downCursor() {
        cursor--;
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
        int toReturn = cursor % 4;
        return toReturn;
    }

    public static String getMenuType() {
        return currentMenu;
    }

    public static void battleMenuAction() {
        int spot = getBattleCursorLocation();
        currentMenu = BATTLEMENU[spot];
    }

    public static void main(String[] args) throws IOException {
        //StdAudio.play("music1.mid");
        double a = System.currentTimeMillis();

        setBounds();
        setBackground("shield.png");
        Player pipeGuy = new Player("pipe.png");
        Player shieldGuy = new Player("shield.png");
        openSequence(pipeGuy, shieldGuy, "pipe.png");
        message = true;
        update();
    }
}