public class WorldScreen {
	private double xBack;
	private double yBack;
	private String background;
	private String pImage;
	private Actor[] blocks;
	private Wall walls;
	private final double SHIFT = 0.07;

	private static final int UP = 87;
    private static final int DOWN = 83;
    private static final int LEFT = 65;
    private static final int RIGHT = 68;
    private static final int ENTER = 32;
    private static final int BACK = 66;

	private class Actor {
		private double bx;
		private double by;
		private String image;
		private String[] enterText;
		private String[] postText;
		private Actor (double x, double y, String pic, String name) {
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
		private boolean vertical;

		private static final double WIDTH = 0.1;

		private Wall(double[] x, double[] y, boolean dir) {
			this.xs = x;
			this.ys = y;
			this.vertical = dir;
		}

		private void shift(boolean vert, boolean down) {
			if (vert) {
				if (down) {
					ys[0] -= SHIFT;
					ys[1] -= SHIFT;
				}
				else {
					ys[0] += SHIFT;
					ys[1] += SHIFT;
				}
			}
			else {
				if (down) {
					xs[0] -= SHIFT;
					xs[1] -= SHIFT;
				}
				else {
					xs[0] += SHIFT;
					xs[1] -= SHIFT;
				}
			}
		}

		private boolean runsInto(double x, double y) {
			if (vertical) {
				if ((y > ys[0] && y < ys[1]) || (y < ys[0] && y > ys[1])) {
					if (Math.abs(xs[0] - x) < WIDTH)
						return true;
				}
			}
			else if ((x > xs[0] && x < xs[1]) || (x < xs[0] && x > xs[1])) {
				return true;
			}
			return false;
		}

		private void draw() {
			StdDraw.line(xs[0], ys[0], xs[1], ys[1]);
		}
	}

	public WorldScreen(String image, String player, double startx, double starty) {
		this.background = image;
		this.pImage = player;
		this.xBack = startx;
		this.yBack = starty;
		double[] wallx = {0.5, 0.5};
		double[] wally = {0, 1};
		this.walls = new Wall(wallx, wally, true);
	}

	public void draw() {
		StdDraw.picture(xBack, yBack, background, 4, 4);
		StdDraw.picture(0, 0, pImage);
		walls.draw();
		StdDraw.show();
		//for (int i = 0; i < blocks.length; i++)
		//	blocks[i].draw();
	}

	private void altDraw() {
		StdDraw.picture(xBack, yBack, background, 4, 4);
		StdDraw.picture(0, 0, pImage, .05, .05);
	}
	
	public void up() {
		if (!walls.runsInto(xBack, yBack - SHIFT)) {
			yBack -= SHIFT;
			walls.shift();
		}
		altDraw();
		Display.interval();
		if (!walls.runsInto(xBack, yBack - SHIFT))
			yBack -= SHIFT;
		draw();
	}

	public void down() {
		if (!walls.runsInto(xBack, yBack + SHIFT))
			yBack += SHIFT;
		altDraw();
		Display.interval();
		if (!walls.runsInto(xBack, yBack + SHIFT))
			yBack += SHIFT;
		draw();
	}

	public void right() {
		if (!walls.runsInto(xBack - SHIFT, yBack))
			xBack -= SHIFT;
		altDraw();
		Display.interval();
		if (!walls.runsInto(xBack - SHIFT, yBack))
			xBack -= SHIFT;
		draw();
	}

	public void left() {
		if (!walls.runsInto(xBack + SHIFT, yBack))
			xBack += SHIFT;
		altDraw();
		Display.interval();
		if (!walls.runsInto(xBack + SHIFT, yBack))
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