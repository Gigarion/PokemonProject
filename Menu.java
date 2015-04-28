/*****************************************************************************************
  **  Class for the creation and usage of the start menu, hopefully wont take too much space
  **
  *********************/
import java.awt.*;
public class Menu {
    private static final double[] STARTX = { 0.6,  0.6,  0.97,  0.97};
    private static final double[] STARTY = {-0.67, 0.97, 0.97, -0.67};
    private static final double   LISTX  = 0.63;
    private static final double   LISTY  = 0.90;
    private static final double   SHIFT  = 0.12;
    private static final double   WIDTH  = 0.05;
    private static final double[] BORDERX = {0.61,  0.61,  0.96,  0.96};
    private static final double   BORDER = 0.004;
    private static final int   MENUCOUNT = 7;
    private static final String[]  LIST1 = {"POKeDex", "POKeMON", "BAG", "POKeNAV"};
    private static final String[]  LIST2 = {"SAVE", "OPTION"};
    
    public static int getSize() {
        return MENUCOUNT;
    }
    public static void draw(Player p, int cursor) {
        String[] totalList = new String[MENUCOUNT];
        
        for (int i = 0; i < LIST1.length; i++)
            totalList[i] = LIST1[i];
        
        totalList[LIST1.length] = p.getName();
        
        for (int i = 0; i < LIST2.length; i++)
            totalList[i + LIST1.length + 1] = LIST2[i];
        
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledPolygon(STARTX, STARTY);
        StdDraw.setPenColor();
        StdDraw.setPenRadius(BORDER);
        StdDraw.polygon(STARTX, STARTY);
        StdDraw.setPenRadius();
        
        for (int i = 0; i < MENUCOUNT; i++) {
            double yText = LISTY - (i * SHIFT);
            StdDraw.textLeft(LISTX, yText, totalList[i]);
            if (i == cursor) {
                double[] ys = {yText + WIDTH, yText - WIDTH, yText - WIDTH, yText + WIDTH};
                StdDraw.setPenColor(Color.RED);
                StdDraw.polygon(BORDERX, ys);
                StdDraw.setPenColor();
            }
        }
    }
}