import java.awt.*;
import java.io.*;
import java.util.*;
public class Bag {
	private static final String BACKGROUND = "images\\pbackground.png";
	private static final String CURSOR   = "images\\cursor.gif";
	private static final double[] MESSX  = {-0.97, -0.97,     0,     0};
	private static final double[] MESSY  = {-0.97, -0.40, -0.40, -0.97};
	private static final double[] TITLEX = {-0.97, -0.97,     0,     0};
	private static final double[] TITLEY = { 0.97,  0.80,  0.80,  0.97};
	private static final double[] MENUX  = { 0.10,  0.10,  0.97,  0.97};
	private static final double[] MENUY  = { 0.97, -0.97, -0.97,  0.97};
	private static final double[] INNERX = { 0.13,  0.13,  0.94,  0.94};
	private static final double[] INNERY = { 0.88, -0.88, -0.88,  0.88};
	
	private static final double   ITEMX  = 0.15;
	private static       double[] ITEMY = new double[22];



	private static final double BAGX     = -0.52;
	private static final double BAGY     =  0.3;
	private static final double BORDER   = 0.004;
	private static final Color  ORANGE   = new Color(255, 175, 0);
	private static final Color  LORANGE  = new Color(255, 200, 50);
	private static final Font   BIGFONT  = new Font(Font.MONOSPACED, 1, 25);
	  
	public static void draw(Player p, int cursor) {
		int count = 0;
		for (double i = 0.83; i > -0.88; i -= 0.08) {
			ITEMY[count] = i;
			count++;
		}

		StdDraw.picture(0, 0, BACKGROUND, 4, 4);
		StdDraw.picture(BAGX, BAGY, "images\\bag.png", .6, .7);
		
		StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledPolygon(MESSX, MESSY);
        StdDraw.filledPolygon(TITLEX, TITLEY);
        
        StdDraw.setPenColor(ORANGE);
        StdDraw.filledPolygon(MENUX, MENUY);
        
        StdDraw.setPenRadius(BORDER);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.polygon(MESSX, MESSY);
        StdDraw.polygon(TITLEX, TITLEY);
        StdDraw.polygon(MENUX, MENUY);
        
        StdDraw.setPenColor(LORANGE);
        StdDraw.filledPolygon(INNERX, INNERY);
        
        StdDraw.setPenColor();
        StdDraw.setFont(BIGFONT);
        StdDraw.text(-0.48, 0.88, "ITEMS");

        StdDraw.picture(-.15, 0.88, CURSOR, .05, .05);
        StdDraw.picture(-.82, 0.88, CURSOR, .05, .05, 180);

        Item[] toShow = p.getItems();

        for (int i = 0; i < toShow.length; i++) {
        	if (cursor == i) 	toShow[i].draw(true, ITEMY[i]);
        	else 				toShow[i].draw(false, ITEMY[i]);
        }
	}

	public static void main(String[] args) throws IOException {
		Display.setBounds();
	}
}