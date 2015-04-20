import java.awt.*;
public class PokeBlock {
	public static final double LENGTH    = 0.78;
	public static final double HEIGHT    = 0.36;
	public static final double PICX      = 0.05;
	public static final double PICY      = 0.25;
	public static final double BIGPICX   = 0.10;
	public static final double BIGPICY   = 0.40;
	public static final double BIGHEIGHT = 0.58;
	public static final Color  KICKING   = new Color(51, 153, 153);
	public static final Color  FAINT     = new Color(209, 117, 117);
	public static final Color  KSELECT   = new Color(50, 200, 200);
	public static final Color  FSELECT   = new Color(209, 200, 200);
	
	public String name;
	public String image;
	public boolean faint;
	public double tempHealth;
	public double health;
	public double percent;

	public PokeBlock (Pokemon poke) {
		name = poke.getName();
		image = name + ".png";
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
				
				if (isCursor) StdDraw.setPenColor(Color.RED);
				else StdDraw.setPenColor(Color.BLACK);
				StdDraw.polygon(xBounds, yBounds);

				StdDraw.picture(x + BIGPICX, y + BIGPICY, image, .25, .25);
			}
			else {
				
				if (faint && isCursor) 		 StdDraw.setPenColor(FSELECT);
				else if (faint)				 StdDraw.setPenColor(FAINT);
				else if (!faint && isCursor) StdDraw.setPenColor(KSELECT);
				else   						 StdDraw.setPenColor(KICKING);
				
				double[] xBounds = {x, x, x + LENGTH, x + LENGTH};
				double[] yBounds = {y, y + HEIGHT, y + HEIGHT, y};
				StdDraw.filledPolygon(xBounds, yBounds);
				
				if (isCursor)   StdDraw.setPenColor(Color.RED);
				else 		    StdDraw.setPenColor(Color.BLACK);
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
			
			StdDraw.setPenColor(Color.BLACK);
			StdDraw.polygon(xBounds, yBounds);
		}
	}
}