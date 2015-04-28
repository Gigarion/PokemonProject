import java.awt.*;
public class PokeBlock {
    private static final double LENGTH    = 0.78;
    private static final double HEIGHT    = 0.36;
    private static final double PICX      = 0.05;
    private static final double PICY      = 0.25;
    private static final double BIGPICX   = 0.10;
    private static final double BIGPICY   = 0.40;
    private static final double BIGHEIGHT = 0.58;
    private static final Color  KICKING   = new Color(51, 153, 153);
    private static final Color  FAINT     = new Color(209, 117, 117);
    private static final Color  KSELECT   = new Color(50, 200, 200);
    private static final Color  FSELECT   = new Color(209, 200, 200);
    private static final double BORDER = 0.004;
    
    private String name;
    private String image;
    private boolean faint;
    private double tempHealth;
    private double health;
    private double percent;
    
    public PokeBlock (Pokemon poke) {
        name = poke.getName();
        image = "Pokemon\\" + name + ".png";
        health = poke.getMaxHealth();
        tempHealth = poke.getTempHealth();
        percent = (double) tempHealth / health;
        faint = poke.isFaint();
    }
    
    public PokeBlock() {
        name = null;
    }
    
    public void draw(boolean isFirst, boolean isCursor, double x, double y) {
        double halfSpot = x + (LENGTH / 2);
        double endSpot  = (x + LENGTH) - 0.02;
        double length = Math.abs(Math.abs(halfSpot) - Math.abs(endSpot)) * percent;
        double qHeight = y + (HEIGHT / 4);
        StdDraw.setPenColor(StdDraw.WHITE);
        
        if (name != null) {
            
            if (isFirst) {
                double[] xBounds = {x, x, x + LENGTH, x + LENGTH};
                double[] yBounds = {y, y + BIGHEIGHT, y + BIGHEIGHT, y};
                
                if (faint) {
                    if (isCursor) StdDraw.setPenColor(FSELECT);
                    else StdDraw.setPenColor(FAINT);
                    
                    StdDraw.filledPolygon(xBounds, yBounds);
                    StdDraw.setPenColor();
                    StdDraw.text(halfSpot, qHeight, "FAINT");
                }
                else {
                    if (isCursor) StdDraw.setPenColor(KSELECT);
                    else StdDraw.setPenColor(KICKING);
                    StdDraw.filledPolygon(xBounds, yBounds);
                    StdDraw.setPenColor();
                    
                    StdDraw.setPenRadius(0.016);
                    StdDraw.line(halfSpot, qHeight, endSpot, qHeight);
                    
                    StdDraw.setPenRadius(0.01);
                    StdDraw.setPenColor(StdDraw.GREEN);
                    if (percent < .50) StdDraw.setPenColor(StdDraw.ORANGE);
                    if (percent < .15) StdDraw.setPenColor(StdDraw.RED);
                    StdDraw.line(halfSpot, qHeight, halfSpot + length , qHeight);
                    
                    StdDraw.setPenColor();
                    StdDraw.text(halfSpot, qHeight + (BIGHEIGHT / 4), name);
                    StdDraw.text(halfSpot + (LENGTH / 4), qHeight + (BIGHEIGHT / 4), tempHealth + " / " + health);
                }
                StdDraw.setPenRadius(BORDER);
                if (isCursor) StdDraw.setPenColor(Color.RED);
                else StdDraw.setPenColor(Color.BLACK);
                StdDraw.polygon(xBounds, yBounds);
                
                StdDraw.picture(x + BIGPICX, y + BIGPICY, image, .25, .25);
            }
            else {
                
                if (faint && isCursor)    StdDraw.setPenColor(FSELECT);
                else if (faint)     StdDraw.setPenColor(FAINT);
                else if (!faint && isCursor) StdDraw.setPenColor(KSELECT);
                else          StdDraw.setPenColor(KICKING);
                
                double[] xBounds = {x, x, x + LENGTH, x + LENGTH};
                double[] yBounds = {y, y + HEIGHT, y + HEIGHT, y};
                StdDraw.filledPolygon(xBounds, yBounds);
                
                if (isCursor)   StdDraw.setPenColor(Color.RED);
                else       StdDraw.setPenColor(Color.BLACK);
                StdDraw.setPenRadius(BORDER);
                StdDraw.polygon(xBounds, yBounds);
                StdDraw.picture(x + PICX, y + PICY, image);
                
                StdDraw.setPenColor();
                StdDraw.setPenRadius(0.016);
                StdDraw.line(halfSpot, qHeight, endSpot, qHeight);
                StdDraw.setPenRadius(0.01);
                StdDraw.setPenColor(StdDraw.GREEN);
                if (percent < .50)  StdDraw.setPenColor(StdDraw.ORANGE);
                if (percent < .15)  StdDraw.setPenColor(StdDraw.RED);
                if (tempHealth > 0) StdDraw.line(halfSpot, qHeight, halfSpot + length , qHeight);
                
                StdDraw.setPenColor();
                StdDraw.text(halfSpot, qHeight + (HEIGHT / 4), name);
            }
        }
        
        else {
            StdDraw.setPenColor(KICKING);
            double[] xBounds = {x, x, x + LENGTH, x + LENGTH};
            double[] yBounds = {y, y + HEIGHT, y + HEIGHT, y};
            StdDraw.filledPolygon(xBounds, yBounds);
            
            StdDraw.setPenRadius(BORDER);
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.polygon(xBounds, yBounds);
        }
    }
}