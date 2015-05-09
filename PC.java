import java.io.*;
import java.util.*;
import java.awt.*;

public class Pc {
	private static final String BACKGROUND = "images\\pbackground.png";
    
    private static final double[] BOXX   = {-0.97, -0.97,  0.97,  0.97};
    private static final double[] BOXY   = { 0.5,  -0.97, -0.97,  0.5};
    private static final double[] TITLEX = {-0.6,  -0.6,   0.6,   0.6};
    private static final double[] TITLEY = { 0.97,  0.6,  0.6,  0.97};

    private static final double[] MESSX  = {-0.97, -0.97, 0,   0};
    private static final double[] MESSY  = {-0.97, -0.6, -0.6, -0.97};
    private static final double MTX = -0.65;
    private static final double MTY = -0.7;
   
    private static final double BORDER   = 0.004;
	private static final double STARTX = -0.85;
	private static final double STARTY = 0.35;
	private static final double DELX = 0.18;
	private static final double DELY = 0.22;
	private static final double CXDHALF = 0.09;
	private static final double CYDHALF = 0.11;

	private static final double PICWID = .15; 
	private static final double PICHI = .2;

	private static final Color  LORANGE  = new Color(255, 200, 50);
	private static final Font   BIGFONT  = new Font(Font.MONOSPACED, 1, 25);
	private static final Font TEXT = new Font(Font.MONOSPACED, 0, 22);

	Pokemon[] inBox;

	public Pc() throws IOException {
		File box = new File("players\\storage.txt");
		Scanner readBox = new Scanner(box);
		int count = 0;
		while(readBox.hasNext()) {
			String temp = readBox.next();
			count++;
		}
		readBox.close();
		Scanner readAgain = new Scanner(box);
		inBox = new Pokemon[count];
		for (int i = 0; i < count; i++) {
			File toAdd = new File(readAgain.next());
			inBox[i] = Pokemon.fromFile(toAdd);
		}
	}

	public int getSize() {
		return inBox.length - 1;
	}

	public void draw(Player main, int cursor) {
		StdDraw.picture(0, 0, BACKGROUND, 4, 4);
		
		StdDraw.setPenColor(LORANGE);
		StdDraw.filledPolygon(BOXX, BOXY);

		StdDraw.setPenColor(Color.WHITE);
		StdDraw.filledPolygon(TITLEX, TITLEY);
		StdDraw.filledPolygon(MESSX, MESSY);

		StdDraw.setPenRadius(BORDER);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.polygon(BOXX, BOXY);
		StdDraw.polygon(MESSX, MESSY);
		StdDraw.polygon(TITLEX, TITLEY);
		StdDraw.setFont(BIGFONT);
		StdDraw.text(0, 0.8, main.getName() + "'s PC");
		if (inBox.length > 0) StdDraw.text(MTX, MTY, inBox[cursor].getName());
		StdDraw.setFont(TEXT);
		
		if (inBox.length > 0) {
			for (int i = 0; i < inBox.length; i++) {
				if (i > 9) StdDraw.picture(STARTX + ((i % 5) * DELX), STARTY - DELY, inBox[i].getImage(), PICWID, PICHI);
				else StdDraw.picture(STARTX + (i * DELX), STARTY, inBox[i].getImage(), PICWID, PICHI);
			}

			double x1 = STARTX + (CXDHALF * (1 + (2 * cursor)));
			double x2 = x1 - (2 * CXDHALF);
			double[] xs = {x1, x1, x2, x2};
			double[] ys = {STARTY + CYDHALF, STARTY - CYDHALF, STARTY - CYDHALF, STARTY + CYDHALF};
			StdDraw.setPenColor(Color.RED);
			StdDraw.polygon(xs, ys);
			StdDraw.setPenColor(Color.BLACK);
		}
	}

	public void swap(Player p, int party, int box) throws IOException {
		Pokemon temp = p.getPokemon(party);
		p.setPokemon(party, inBox[box]);
		inBox[box] = temp;
		File reWrite = new File("players\\storage.txt");
		reWrite.delete();
		reWrite.createNewFile();
		PrintWriter write = new PrintWriter(reWrite);
		for (int i = 0; i < inBox.length; i++)
			write.println("pokemon\\" + inBox[i].getName() + ".txt");
		write.close();
	}

	public static void main(String[] args) throws IOException {
		Pc test = new Pc();
	}
}