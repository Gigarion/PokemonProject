import java.io.*;
import java.util.*;
import java.awt.*;

public class WorldScreen {
    private static final Font TEXT = new Font(Font.MONOSPACED, 0, 22);
    private static final double[] MESSX = {-.97, -.97,  .97, .97};
    private static final double[] MESSY = {-.97,-.7, -.7, -.97};
    
    private static final double ACTWID = .07;
    private static final double ACTHI = .1;

    private static final double SPRITE = 0.12;

    private static final double STARTX = 0;
    private static final double STARTY = 1.25;

    private static final double BORDER   = 0.004;
    private static final String WORLDSONG = "music\\fortree-city.mid";
    private int cursor;
    private Player main;
    private int direction;
    private double xBack;
    private double yBack;
    private String background;
    private String[] pImage;
    private String[] wImage;
    private Actor[] actors;
    private Wall[] walls;
    private Item toUse;
    private int depth;
    private int shelf;
    private int holdCursor;
    private boolean fromItem;
    private boolean start;
    private boolean lock;
    private int currentMenu;
    private Pc pc;
    private boolean fromPc;
    private final double SHIFT = 0.05;
    
    private static final int UP = 87;
    private static final int DOWN = 83;
    private static final int LEFT = 65;
    private static final int RIGHT = 68;
    private static final int ENTER = 32;
    private static final int BACK = 66;
    private static final int START = 80;
    private static final int MUSIC = 77;
    
    private class Actor {
        private static final double RADIUS = 0.06;
        private double bx;
        private double by;
        private String image;
        private boolean hasInteracted;
        private String file;
        
        private Actor (double x, double y, String pic, String fName) {
            this.bx = x;
            this.by = y;
            this.image = pic;
            this.file = fName;
            hasInteracted = false;
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
            if (image.contains("pc"))
                StdDraw.picture(bx, by, image, ACTWID, (2 * ACTHI));
            else if (image.contains("center"))
                StdDraw.picture(bx, by, image, (1.2 * ACTWID), (1.2 * ACTHI));
            else if (!image.equals("null"))
                StdDraw.picture(bx, by, image, ACTWID, ACTHI);
        }
        
        private void act() throws IOException {
            boolean lock = false;
            File toRead = new File(file);
            Scanner read = new Scanner(toRead);
            int lines = Integer.parseInt(read.nextLine());
            String[] toPrint = new String[lines];
            for (int i = 0; i < lines; i++) {
                toPrint[i] = read.nextLine();
            }
            read.close();
            for (int i = 0; i < lines; i++) {
                File temp = new File("tempBack.png");
                if (StdDraw.isKeyPressed(BACK) && lock == false) {
                    return;
                }
                else if (toPrint[i].equals("reset")) {
                    Display.timeDelay();
                    StdAudio.play("music\\heal.wav");
                    main.heal();
                    Display.timeDelay();
                    Display.timeDelay();
                }
                else if (toPrint[i].equals("pc")) {
                    Message.pc();

                    pc = new Pc();
                    System.out.println(pc.getSize());
                    currentMenu = 4;
                    depth = 0;
                    cursor = 0;
                    return;
                }

                else if (toPrint[i].contains(".txt") && !hasInteracted) {
                    temp.delete();
                    StdDraw.save("tempBack.png");
                    Display.interval();
                    Display.setMainBackground("tempBack.png");
                    Display.setMain(main);
                    if (toPrint[i].contains("wild")) Display.setWild(true);
                    else Display.setWild(false);
                    HBA.battle(toPrint[i],  main);
                    hasInteracted = true;
                    lock = false;
                }

                else if (toPrint[i].contains(".mid") && !hasInteracted) {
                    lock = true;
                    Display.timeDelay();
                    Display.interval();
                    temp.delete();
                }
                else if (!hasInteracted && i == lines - 1) {
                    hasInteracted = true;
                    Message.customSet(toPrint[i]);
                    message();
                    StdDraw.show(5);
                    Display.interval();
                    do{}while (!StdDraw.isKeyPressed(ENTER));
                }
                else if ((hasInteracted && i != 0) || !hasInteracted && i < lines) {
                    Message.customSet(toPrint[i]);
                    
                    message();
                    StdDraw.show(5);
                    Display.interval();
                    do {} while(!StdDraw.isKeyPressed(ENTER));
                }
                else {
                    temp.delete();
                    i = lines;
                    if (!toPrint[lines - 1].contains(".txt")); {
                        Message.customSet(toPrint[lines - 1]);
                        message();
                        StdDraw.show(5);
                        Display.interval();
                        do {} while (!StdDraw.isKeyPressed(ENTER));
                    }
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
        }
    }
    
    public WorldScreen(Player player) throws IOException {
        StdDraw.setFont(TEXT);
        this.main = player;
        StdAudio.loop(WORLDSONG);
        Display.interval();
        this.direction = 1;
        this.xBack = player.getX();
        this.yBack = player.getY();
        File world = new File("build\\mainWorld.txt");
        Scanner readWorld = new Scanner(world);
        this.background = readWorld.next();
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
        this.depth = 0;
        this.shelf = 0;
        this.holdCursor = 0;
        this.currentMenu = 3;
        this.lock = false;
        this.fromItem = false;
        this.fromPc = false;
    }
    
    private void start() {
        Menu.draw(main, cursor);
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
                Display.interval();
            }
            else if (StdDraw.isKeyPressed(START)) {
                if (!start) cursor = 0;
                start = true;
                Message.customSet("Menu");
                currentMenu = 0;
                drawWorld();
            }
            else if (StdDraw.isKeyPressed(BACK)) {
                back();
            }
            Display.interval();
        }
    }
    
    public void drawWorld() {
        StdDraw.clear(Color.GRAY);
        StdDraw.picture(xBack, yBack, background, 3, 3);

        //for (int i = 0; i < walls.length; i++)
          //  walls[i].draw();
        for (int i = 0; i < actors.length; i++)
            actors[i].draw();
        StdDraw.picture(0, 0, pImage[direction], SPRITE, SPRITE);
        switch(currentMenu) {
            case 0: {
                start();
                message();
            } break;
            case 1: PokeDraw.draw(main, cursor); break;
            case 2: Bag.draw(main, cursor, shelf); break;
            case 3: break;
            case 4: { 
                pc.draw(main, cursor); 
                if (depth == 1) {
                    message();
                }
            } break;
        }
        StdDraw.show(5);
    }
    
    private void altDraw() {
        StdDraw.clear(Color.GRAY);
        StdDraw.picture(xBack, yBack, background, 3, 3);

        //for (int i = 0; i < walls.length; i++)
          // walls[i].draw();
        for (int i = 0; i < actors.length; i++)
            actors[i].draw();
        StdDraw.picture(0, 0, wImage[direction], SPRITE, SPRITE);
        StdDraw.show(5);
    }
    
    public void up() {
        if (!lock) {
            switch(currentMenu) {
                case 0: {
                    if (cursor == 0) cursor = Menu.getSize() - 1;
                    else cursor--;
                } break;
                
                case 1: {
                    if (cursor == 0) cursor = main.getTeamSize() - 1;
                    else cursor--;
                } break;
                
                case 2: { 
                    if (shelf == 0) {
                        if (cursor == 0) cursor = main.getNumItems() - 1;
                        else cursor--;
                    }
                    else if (shelf == 1) {
                        if (cursor == 0) cursor = main.getBalls().length - 1;
                        else cursor --;
                    }
                } break;
                
                case 3: {
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
                } break;
                case 4: break;
            }
            drawWorld();
        }
    }
    
    public void down() {
        if (!lock) {
            switch(currentMenu) {
                case 0: {
                    if (cursor == Menu.getSize() - 1) cursor = 0;
                    else cursor++;
                } break;
                
                case 1: {
                    if (cursor == main.getTeamSize() - 1) cursor = 0;
                    else cursor++;
                } break;
                
                case 2: { 
                    if (shelf == 0) {
                        if (cursor == main.getNumItems() - 1) cursor = 0;
                        else cursor++;
                    }
                    else if (shelf == 1) {
                        if (cursor == main.getBalls().length - 1) cursor = 0;
                        else cursor++;
                    }
                } break;
                
                case 3: {
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
                } break;
                case 4: break;
            }
            drawWorld();
        }
    }
    
    public void right() {
        if (!lock) {
            switch(currentMenu) {
                case 0: break;
                
                case 1: {
                    if (cursor == 0) cursor = 1;
                    else cursor = 0;
                } break;
                case 2: {
                    if (shelf == 0) shelf = 1;
                    else shelf = 0;
                    cursor = 0;
                } break;
                case 3: {
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
                }  break;
                case 4: {
                    if (cursor < pc.getSize()) cursor++;
                    else cursor = 0;
                } break;
            }
            drawWorld();
        }
    }
    
    public void left() {
        if (!lock) {
            switch(currentMenu) {
                case 0: break;
                
                case 1: {
                    if (cursor == 0) cursor = 1;
                    else cursor = 0;
                } break;
                case 2: {
                    if (shelf == 0) shelf = 1;
                    else shelf = 0;
                } break;
                case 3: {
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
                } break;
                case 4: {
                    if (cursor > 0) cursor--;
                    else cursor = pc.getSize();
                } break;
            }
            drawWorld();
        }
    }
    
    public void back() {
        switch(currentMenu) {
            case 0: currentMenu = 3; break;
            case 1: {
                if(fromItem && depth == 1) {
                    depth = 0; 
                    lock = false;
                    Message.item();
                }
                else if (fromItem)  {
                    currentMenu = 2;
                    fromItem = false;
                }
                else if (fromPc && depth == 1) {
                    depth = 0;
                    lock = false;
                    Message.pokeMenu();
                }
                else if (fromPc) {
                    currentMenu = 4;
                    fromPc = false;
                    cursor = 0;
                }
                else if (depth == 1) {
                    depth = 0; 
                    Message.customSet("Pokemon"); 
                    lock = false;
                }
                else {
                    currentMenu = 0;
                    Message.customSet("Menu");
                }
            } break;
            case 2: {
                if (depth == 1) {
                    depth = 0;
                    lock = false;
                }
                else currentMenu = 0;
            } break;
            case 3: break;
            case 4: {
                if (depth == 0) {
                    currentMenu = 3;
                    cursor = 0;
                }
                else if (depth == 1) {
                    depth = 0;
                    lock = false;
                }
            } break;
        }
        drawWorld();
    }
    
    public void act() throws IOException {
        switch(currentMenu) {
            case 0: { // Start menu
                switch(cursor) {
                    case 1: {
                        Message.customSet("Pokemon");
                        currentMenu = 1; 
                        cursor = 0;
                    } break;
                    case 2: currentMenu = 2; cursor = 0; break;
                    case 5: {
                        File player = new File("players\\mainplayer.txt");
                        player.delete();
                        player.createNewFile();
                        PrintWriter saveMe = new PrintWriter(player);
                        saveMe.println(main.getName());
                        saveMe.println(STARTX);
                        saveMe.println(STARTY);
                        saveMe.println(main.getTeamSize());
                        saveMe.println("players\\mainitems.txt");
                        for (int i = 0; i < main.getTeamSize(); i++) {
                            saveMe.println("Pokemon\\" + main.getPokemon(i).getName() + ".txt");
                        }
                        saveMe.close();
                        Message.save();
                        message();
                        Display.timeDelay();
                        Display.timeDelay();
                        currentMenu = 3;
                        cursor = 0;
                    } break;
                    default: {
                        Message.customSet("I wish it was that functional too :(");
                        drawWorld();
                        Display.timeDelay();
                        Message.customSet("Menu");
                    } break;
                }
            } break;

            case 1: { // Pokemon menu
                if (fromItem) {
                    switch(depth) {
                        case 0: {
                            if (toUse.couldUse(main.getPokemon(cursor))) {
                                Message.useItem(toUse, main.getPokemon(cursor));
                                lock = true;
                                depth++;
                            }
                            else {
                                Message.noItemEffect();
                                drawWorld();
                                Display.timeDelay();
                                Display.timeDelay();
                                Message.item();  
                            }
                        } break;
                        case 1: {
                            toUse.use(main.getPokemon(cursor));
                            toUse.reduce();
                            currentMenu = 2;
                            fromItem = false;
                            lock = false;
                        } break;
                    }
                }
                else if (fromPc) {
                    switch(depth) {
                        case 0: {
                            Message.flip();
                            lock = true;
                            depth++;
                        } break;

                        case 1: {
                            pc.swap(main, cursor, holdCursor);
                            pc = new Pc();
                            lock = false;
                            depth = 0;
                            cursor = 0;
                            currentMenu = 4;
                            fromPc = false;
                        } break;
                    }
                }
                else { 
                    switch(depth) {
                        case 0: { 
                            Message.flip();
                            lock = true;
                            depth++;
                        } break;
                        case 1: {
                            Message.customSet("Switch with which Pokemon?");
                            holdCursor = cursor;
                            lock = false;
                            depth++;
                        } break;
                        case 2: {
                            Message.customSet("Pokemon");
                            main.swapPokemon(holdCursor, cursor);
                            lock = false;
                            depth = 0;
                        } break;
                    }
                }
            } break;
            
            case 2: { // Bag menu
                if (shelf == 0) {
                    Item[] usable = main.getItems();
                    if (usable[cursor].getNumber() > 0) {
                        Message.item();
                        toUse = usable[cursor];
                        fromItem = true;
                        currentMenu = 1;
                        depth = 0;
                        cursor = 0;  
                    }
                    else Message.outOfItem(usable[cursor]);
                }
            } break;
            case 3: { // No menu
                boolean actable = false;
                int which = 0;
                for (int i = 0; i < actors.length; i++) {
                    if (actors[i].runsInto(direction)) { 
                        if(actors[i].file != null) actable = true;
                        which = i;
                    }
                }
                if (actable) actors[which].act(); 
            } break;
            case 4: {
                if (pc.getSize() > 0) {
                    if (depth == 0) {
                        depth++;
                        Message.flip();
                        lock = true;
                    }
                    else if (depth == 1) {
                        lock = false;
                        currentMenu = 1;
                        depth = 0;
                        holdCursor = cursor;
                        Message.pokeMenu();
                        fromPc = true;
                    }
                }

            } break;
        }
        drawWorld();
    }
    private void drain() {
       // StdAudio.loop(WORLDSONG);
    }
    private void message() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledPolygon(MESSX, MESSY);
        StdDraw.setPenColor();
        StdDraw.setPenRadius(BORDER);
        StdDraw.polygon(MESSX, MESSY);
        StdDraw.textLeft(-.9, -.8, Message.getMessage());
        StdDraw.show(5);
    }
}