import java.io.*;
import java.util.*;
public class WorldScreen {
	private static final double[] MESSX  = {-0.97, -0.97,     0,     0};
	private static final double[] MESSY  = {-0.97, -0.40, -0.40, -0.97};
	private static final double BORDER   = 0.004;
	private boolean message;
	private Player main;
	private int direction;
	private double xBack;
	private double yBack;
	private String background;
	private String[] pImage;
	private String[] wImage;
	private Actor[] actors;
	private Wall[] walls;
	private final double SHIFT = 0.05;

	private static final int UP = 87;
    private static final int DOWN = 83;
    private static final int LEFT = 65;
    private static final int RIGHT = 68;
    private static final int ENTER = 32;
    private static final int BACK = 66;

	private class Actor {
		private static final double RADIUS = 0.06;
		private double bx;
		private double by;
		private String image;
		private String file;
		
		private Actor (double x, double y, String pic, String fName) {
			this.bx = x;
			this.by = y;
			this.image = pic;
			this.file = fName;
			if (file.equals("null")) file = null;
		}

		private boolean runsInto(int dir) {
			switch(dir) {
				case 0: { // up
					if (Math.abs(by - SHIFT) < RADIUS && Math.abs(bx) < RADIUS) return true;
					else return false;
				}
				case 1: { // down
					if (Math.abs(by + SHIFT) < RADIUS && Math.abs(bx) < RADIUS) return true;
					else return false;
				}
				case 2: { // right
					if (Math.abs(bx - SHIFT) < RADIUS && Math.abs(by) < RADIUS) return true;
					else return false;
				}
				case 3: { // left
					if (Math.abs(bx + SHIFT) < RADIUS && Math.abs(by) < RADIUS) return true;
					else return false;
				}
				default: return false;
			}
		}

		private void shift(boolean vert, boolean up) {
			if (vert) {
				if (up) {
					by -= SHIFT;
				}
				else {
					by += SHIFT;
				}
			}
			else {
				if (up) {
					bx -= SHIFT;
				}
				else {
					bx += SHIFT;
				}
			}
		}

		private void draw() {
			StdDraw.picture(bx, by, image);
		}

		private void act() throws IOException {
			File toRead = new File(file);
			Scanner read = new Scanner(toRead);
			int lines = Integer.parseInt(read.nextLine());
			String[] toPrint = new String[lines];
			for (int i = 0; i < lines; i++) {
				toPrint[i] = read.nextLine();
			}
			for (int i = 0; i < lines; i++) {
				message = true;
				if (toPrint[i].contains(".txt")) {
					StdDraw.save("tempBack.png");
					Display.setBackground("tempBack.png");
					Display.setMain(main);
					HBA.battle(toPrint[i], main);
				}
				else {
					Message.customSet(toPrint[i]);
					System.out.println("HEYO");
					message();
					StdDraw.show();
					//do {} while(!StdDraw.isKeyPressed(ENTER));
				}
			}
		}

		private void message() {
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledPolygon(MESSX, MESSY);
			StdDraw.setPenColor();
			StdDraw.setPenRadius(BORDER);
			StdDraw.polygon(MESSX, MESSY);
			StdDraw.textLeft(-.9, -.8, Message.getMessage());
			StdDraw.show();
		}
	}

	private class Wall{
		private double[] xs;
		private double[] ys;
		private boolean vertical;

		private static final double WIDTH = 0.07;

		private Wall(double[] x, double[] y, boolean vert) {
			this.xs = x;
			this.ys = y;
			this.vertical = vert;
		}

		private void shift(boolean vert, boolean up) {
			if (vert) {
				if (up) {
					ys[0] -= SHIFT;
					ys[1] -= SHIFT;
				}
				else {
					ys[0] += SHIFT;
					ys[1] += SHIFT;
				}
			}
			else {
				if (up) {
					xs[0] -= SHIFT;
					xs[1] -= SHIFT;
				}
				else {
					xs[0] += SHIFT;
					xs[1] += SHIFT;
				}
			}
		}

		private boolean runsInto(int dir) {
			switch(dir) {
				case 0: { // up
					if (vertical) {
						if (Math.abs(xs[0]) < WIDTH) {
							if (Math.abs(ys[0] - SHIFT) < WIDTH || Math.abs(ys[1] - SHIFT) < WIDTH) {
								return true;
							}
						}
					}
					else if ((xs[0] < 0 && xs[1] > 0) || (xs[0] > 0 && xs[1] < 0)) {
						if (Math.abs(ys[0] - SHIFT) < WIDTH)
							return true;
					}
					return false;
				}
				case 1: { // down
					if (vertical) {
						if (Math.abs(xs[0]) < WIDTH) {
							if (Math.abs(ys[0] + SHIFT) < WIDTH || Math.abs(ys[1] + SHIFT) < WIDTH)
								return true;
						}
					}
					else if ((xs[0] < 0 && xs[1] > 0) || (xs[0] > 0 && xs[1] < 0)) {
						if (Math.abs(ys[0] + SHIFT) < WIDTH)
							return true;
					}
					return false;
				}
				case 2: { // right
					if (!vertical) {
						if (Math.abs(ys[0]) < WIDTH) {
							if (Math.abs(xs[0] - SHIFT) < WIDTH || Math.abs(xs[1] - SHIFT) < WIDTH)
								return true;
						}
					}
					else if ((ys[0] < 0 && ys[1] > 0) || (ys[0] > 0 && ys[1] < 0)) {
						if (Math.abs(xs[0] - SHIFT) < WIDTH)
							return true;
					}
					return false;
				}
				case 3: { // left
					if (!vertical) {
						if (Math.abs(ys[0]) < WIDTH) {
							if (Math.abs(xs[0] + SHIFT) < 0 || Math.abs(xs[1] + SHIFT) < WIDTH)
								return true;
						}
					}
					else if ((ys[0] < 0 && ys[1] > 0) || (ys[0] > 0 && ys[1] < 0)) {
						if (Math.abs(xs[0] + SHIFT) < WIDTH)
							return true;
					}
					return false;
				}
				default: return false;
			}
		}

		private void draw() {
			StdDraw.setPenRadius(0.02);
			StdDraw.line(xs[0], ys[0], xs[1], ys[1]);
			StdDraw.setPenRadius();
			if (message) {
				message();
			}
		}
	}

	private void message() {
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledPolygon(MESSX, MESSY);
			StdDraw.setPenColor();
			StdDraw.setPenRadius(BORDER);
			StdDraw.polygon(MESSX, MESSY);
			StdDraw.textRight(-.9, -.8, Message.getMessage());
	}

	public WorldScreen(Player player) throws IOException {
		this.direction = 1;
		File world = new File("mainWorld.txt");
		Scanner readWorld = new Scanner(world);
		this.background = readWorld.next();
		this.xBack = readWorld.nextDouble();
		this.yBack = readWorld.nextDouble();
		this.pImage = new String[4];
		this.wImage = new String[4];
		for (int i = 0; i < 4; i++) {
			pImage[i] = readWorld.next();
		}
		for (int i = 0; i < 4; i++) {
			wImage[i] = readWorld.next();
		}

		File wall = new File(readWorld.next());
		Scanner readWall = new Scanner(wall);
		int numWalls = readWall.nextInt();
		walls = new Wall[numWalls];
		for (int i = 0; i < numWalls; i++) {
			boolean vert = Boolean.parseBoolean(readWall.next());
			double[] xs = new double[2];
			double[] ys = new double[2];
			xs[0] = readWall.nextDouble();
			xs[1] = readWall.nextDouble();
			ys[0] = readWall.nextDouble();
			ys[1] = readWall.nextDouble();
			walls[i] = new Wall(xs, ys, vert);
		}
		readWall.close();

		File act = new File(readWorld.next());
		Scanner readAct = new Scanner(act);
		int numActors = readAct.nextInt();
		actors = new Actor[numActors];
		for (int i = 0; i < numActors; i++) {
			double x = readAct.nextDouble();
			double y = readAct.nextDouble();
			String img = readAct.next();
			String fName = readAct.next();
			actors[i] = new Actor(x, y, img, fName);
		}
		readAct.close();
		readWorld.close();
	}
	
	public void run() throws IOException {
		while(true) {
        	if (StdDraw.isKeyPressed(UP)) {
        		up();
        	}
        	else if (StdDraw.isKeyPressed(DOWN)) {
        		down();
        	}
        	else if (StdDraw.isKeyPressed(LEFT)) {
        		left();
        	}
        	else if (StdDraw.isKeyPressed(RIGHT)) {
        		right();
        	}
        	else if (StdDraw.isKeyPressed(ENTER)) {
        		act();
        	}
        	Display.interval();
        }
	}

	public void draw() {
		StdDraw.picture(xBack, yBack, background, 4, 4);
		StdDraw.picture(0, 0, pImage[direction]);
		for (int i = 0; i < walls.length; i++)
			walls[i].draw();
		for (int i = 0; i < actors.length; i++)
			actors[i].draw();
		StdDraw.show();
	}

	private void altDraw() {
		StdDraw.picture(xBack, yBack, background, 4, 4);
		StdDraw.picture(0, 0, wImage[direction], .05, .05);
		for (int i = 0; i < walls.length; i++)
			walls[i].draw();
		for (int i = 0; i < walls.length; i++)
			actors[i].draw();
	}
	
	public void up() {
		if (direction != 0)
			direction = 0;
		else {
			boolean stop = false;
			for (int i = 0; i < walls.length; i++) {
				if (walls[i].runsInto(0))
					stop = true;
			}
			for (int i = 0; i < actors.length; i++) {
				if (actors[i].runsInto(0))
					stop = true;
			}
			if (!stop) {
				yBack -= SHIFT;
				for (int i = 0; i < walls.length; i++)
					walls[i].shift(true, true);
				for (int i = 0; i < actors.length; i++)
					actors[i].shift(true, true);
			}
			altDraw();
			Display.interval();
			if (!stop) {
				yBack -= SHIFT;
				for (int i = 0; i < walls.length; i++)
					walls[i].shift(true, true);
				for (int i = 0; i < actors.length; i++)
					actors[i].shift(true, true); 
			}
		}
		draw();
	}

	public void down() {
		if (direction != 1)
			direction = 1;
		else {
			boolean stop = false;
			for (int i = 0; i < walls.length; i++) {
				if (walls[i].runsInto(1))
					stop = true;
			}
			for (int i = 0; i < actors.length; i++) {
				if (actors[i].runsInto(1))
					stop = true;
			}
			if (!stop) {
				yBack += SHIFT;
				for (int i = 0; i < walls.length; i++)
					walls[i].shift(true, false);
				for (int i = 0; i < actors.length; i++)
					actors[i].shift(true, false);
			}
			altDraw();
			Display.interval();
			if (!stop) {
				yBack += SHIFT;
				for (int i = 0; i < walls.length; i++)
					walls[i].shift(true, false);
				for (int i = 0; i < actors.length; i++)
					actors[i].shift(true, false);
			}
		}
		draw();
	}

	public void right() {
		if (direction != 2)
			direction = 2;
		else {
			boolean stop = false;
			for (int i = 0; i < walls.length; i++) {
				if (walls[i].runsInto(2))
					stop = true;
			}
			for (int i = 0; i < actors.length; i++) {
				if (actors[i].runsInto(2))
					stop = true;
			}
			if (!stop) {
				xBack -= SHIFT;
				for (int i = 0; i < walls.length; i++)
					walls[i].shift(false, true);
				for (int i = 0; i < actors.length; i++)
					actors[i].shift(false, true);
			}
			altDraw();
			Display.interval();
			if (!stop) {
				xBack -= SHIFT;
				for (int i = 0; i < walls.length; i++)
					walls[i].shift(false, true);
				for (int i = 0; i < actors.length; i++)
					actors[i].shift(false, true);
			}
		}
		draw();
	}

	public void left() {
		if (direction != 3)
			direction = 3;
		else {
			boolean stop = false;
			for (int i = 0; i < walls.length; i++) {
				if (walls[i].runsInto(3))
					stop = true;
			}
			for (int i = 0; i < actors.length; i++) {
				if (actors[i].runsInto(3))
					stop = true;
			}
			if (!stop) {
				xBack += SHIFT;
				for (int i = 0; i < walls.length; i++)
					walls[i].shift(false, false);
				for (int i = 0; i < actors.length; i++)
					actors[i].shift(false, false);
			}
			altDraw();
			Display.interval();
			if (!stop) {
				xBack += SHIFT;
				for (int i = 0; i < walls.length; i++)
					walls[i].shift(false, false);
				for (int i = 0; i < actors.length; i++)
					actors[i].shift(false, false);
			}
		}
		draw();
	}

	public void act() throws IOException {
		boolean actable = false;
		int which = 0;
		for (int i = 0; i < actors.length; i++) {
			if (actors[i].runsInto(direction)) { 
				if(actors[i].file != null) actable = true;
				which = i;
			}
		}
		if (actable) actors[which].act();
	}

	public static void main(String[] args) throws IOException {
	}
}