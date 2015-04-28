import java.awt.*;
public class pDrawTest {
    private static final String BACKGROUND = "background.txt";
    private static final double[] FPBX = {-.88, -.88, -.1, -.1};
    private static final double[] FPBY = {.68, .1, .1, .68};
    private static final double[] SPBX = {0, 0, .78, .78};
    private static double[] SPBY = {1.36, 1, 1, 1.36};
    
    public static void main(String[] args) {
        Display.setBounds();
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledPolygon(FPBX, FPBY);
        for (double i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++)
                SPBY[j] -= 0.39;
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledPolygon(SPBX, SPBY);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.004);
            StdDraw.polygon(SPBX, SPBY);
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.004);
        StdDraw.polygon(FPBX, FPBY);
        StdDraw.picture(-.8, .55, "shield.png", 0.12, 0.17);
        Font toWrite = new Font("pokefont", 3, 22);
        StdDraw.setFont(toWrite);
        StdDraw.textLeft(-.6, .55, "SHIELD");
    }
}