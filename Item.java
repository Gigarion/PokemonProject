import java.util.*;
import java.io.*;
import java.awt.*;
public class Item {
    private static final Font ITEM = new Font(Font.MONOSPACED, 0, 22);
    private static final double NUMX  = 0.92;
    private static final double NAMEX = 0.15;
    private static final double MESSX = -0.95;
    private static final double MESSY = -0.45;
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
    
    public static Item fromFile(Scanner s) throws IOException {
        String fileName = s.next();
        int num = Integer.parseInt(s.next());
        File itemFile = new File(fileName);
        Scanner readItem = new Scanner(itemFile);
        String n   = readItem.nextLine();
        int ah     = Integer.parseInt(readItem.nextLine());
        String st  = readItem.nextLine();
        String pur =readItem.nextLine();
        readItem.close();
        
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
        double[] ys = {y + HEIGHT, y - HEIGHT, y - HEIGHT, y + HEIGHT};
        if (isCursor) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.polygon(CURSEX, ys);
            StdDraw.setPenColor();
            StdDraw.textLeft(MESSX, MESSY, id);
        }
    }
    public boolean couldUse(Pokemon poke) {
        if (heal != 0 && cure.equals("no") && poke.getTempHealth() == poke.getMaxHealth()) {
            return false;
        }
        else if (heal != 0 && cure.equals("cleanse") && poke.getTempHealth() == poke.getMaxHealth() && poke.getStatus().equals("no")) {
            return false;
        }
        else if (cure.equals("REVIVE") && poke.isFaint()) {
            return true;
        }
        else if (heal == 0 && !cure.equals(poke.getStatus())) {
            return false;
        }
        return true;
    }
    public void use(Pokemon poke) {
        poke.heal(heal, cure);
    }
}