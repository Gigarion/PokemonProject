public class WorldScreen {
	private double xBack;
	private double yBack;
	private String background;
	private String pImage;
	private Block[] blocks;
	private Wall[] walls;
	private final double SHIFT = 0.07;

	private static final int UP = 87;
    private static final int DOWN = 83;
    private static final int LEFT = 65;
    private static final int RIGHT = 68;
    private static final int ENTER = 32;
    private static final int BACK = 66;

	private class Block {
		private double bx;
		private double by;
		private String image;
		private String[] enterText;
		private String[] postText;
		private Block (double x, double y, String pic, String name) {
			this.bx = x;
			this.by = y;
			this.image = pic;
		}

		private void draw() {
			StdDraw.picture(bx, by, image);
		}
	}

	private class Wall{
		private double[] xs;
		private double[] ys;
		private static final double WIDTH = 0.07;
		private Wall(double[] x, double[] y) {
			xs = x;
			ys = y;
		}
		private boolean runsInto(double x, double y) {
			if (Math.abs(xs - x) < WIDTH && Math.abs(ys - y) < WIDTH) {
				return true;
			}
			else return false;
		}
	}

	public WorldScreen(String image, String player, double startx, double starty) {
		this.background = image;
		this.pImage = player;
		this.xBack = startx;
		this.yBack = starty;
	}

	public void draw() {
		StdDraw.picture(xBack, yBack, background, 4, 4);
		StdDraw.picture(0, 0, pImage);
		StdDraw.show();
		//for (int i = 0; i < blocks.length; i++)
		//	blocks[i].draw();
	}

	private void altDraw() {
		StdDraw.picture(xBack, yBack, background, 4, 4);
		StdDraw.picture(0, 0, pImage, .05, .05);
	}
	
	public void up() {
		yBack -= SHIFT;
		altDraw();
		Display.interval();
		yBack -= SHIFT;
		draw();
	}

	public void down() {
		yBack += SHIFT;
		altDraw();
		Display.interval();
		yBack += SHIFT;
		draw();
	}

	public void right() {
		xBack -= SHIFT;
		altDraw();
		Display.interval();
		xBack -= SHIFT;
		draw();
	}

	public void left() {
		xBack += SHIFT;
		altDraw();
		Display.interval();
		xBack += SHIFT;
		draw();
	}

	public static void main(String[] args) {
		Display.setBounds();
		WorldScreen mainWorld = new WorldScreen("hall.png", "caleb.png", 0, 0);
        mainWorld.draw();

        while(true) {
        	if (StdDraw.isKeyPressed(UP)) {
        		mainWorld.up();
        	}
        	else if (StdDraw.isKeyPressed(DOWN)) {
        		mainWorld.down();
        	}
        	else if (StdDraw.isKeyPressed(LEFT)) {
        		mainWorld.left();
        	}
        	else if (StdDraw.isKeyPressed(RIGHT)) {
        		mainWorld.right();
        	}
        	Display.interval();
        }
	}
}