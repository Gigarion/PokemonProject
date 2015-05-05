import java.util.*;
import java.awt.*;
import java.io.*;
public class Ball {

    private static final Font ITEM = new Font(Font.MONOSPACED, 0, 22);
    private static final double NUMX  = 0.92;
    private static final double NAMEX = 0.15;
    private static final double MESSX = -0.95;
    private static final double MESSY = -0.45;
    private static final double[] CURSEX = {0.14, 0.14, 0.93, 0.93};
    private static final double HEIGHT = 0.04;
    private String name;
    private String image;
    private int number;
    private int percent;
    private String id;
    
    public Ball(String id, int num, int rate, String purpose) {
        this.name = id;
        this.image = "images\\" + name + ".png";
        this.number = num;
        this.percent = rate;
        this.id = purpose;
    }
    
    public static Ball fromFile(Scanner s) throws IOException {
        String fileName = s.next();
        int num = Integer.parseInt(s.next());
        
        File ballFile = new File(fileName);
        Scanner readBall = new Scanner(ballFile);

        String n   = readBall.nextLine();
		int per = Integer.parseInt(readBall.nextLine());
        String pur = readBall.nextLine();
        readBall.close();
        
        Ball toReturn = new Ball(n, num, per, pur);
        return toReturn;
    }
    
    public String getName() {
        return name;
    }

    public String getImage() {
    	return image;
    }
    
    public int getRate() {
    	return percent;
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
}