import java.util.*;
import java.awt.*;
public class Item {
	private static final Font ITEM = new Font(Font.MONOSPACED, 0, 22);
	private static final double NUMX  = 0.92;
	private static final double NAMEX = 0.15;
	private static final double[] CURSEX = {0.14, 0.14, 0.93, 0.93};
	private static final double HEIGHT = 0.04;
	private String name;
	private int number;
	private int heal;
	private String cure;
	private String id;
	
	public Item(String id, int num, int addHealth, String statCure, String purpose) {
		this.name = id;
		this.number = num;
		this.heal = addHealth;
		this.cure = statCure;
		this.id   = purpose;
	}

	public static Item fromFile(Scanner s) {
		String n   = s.next();
		int num    = Integer.parseInt(s.next());
		int ah     = Integer.parseInt(s.next());
		String st  = s.next();
		String pur = s.next();

		Item toReturn = new Item(n, num, ah, st, pur);
		return toReturn;
	}

	public String getName() {
		return name;
	}

	public void reduce() {
		number--;
	}

	public int getNumber() {
		return number;
	}

	public void draw(boolean isCursor, double y) {
		StdDraw.setFont(ITEM);
		StdDraw.textLeft(NAMEX, y, name);
		String num = "X " + number;
		StdDraw.textRight(NUMX, y, num);
		StdDraw.setPenColor(Color.RED);
		double[] ys = {y + HEIGHT, y - HEIGHT, y - HEIGHT, y + HEIGHT};
		if (isCursor) StdDraw.polygon(CURSEX, ys);
		StdDraw.setPenColor();
	}

	public void use(Pokemon poke) {
		poke.heal(heal);
	}
}