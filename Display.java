import java.io.*;
import java.awt.*;
public class Display {
    private static final String[] BMENU = {"fight", "pokemon", "bag", "run", "main"};
    private static int currentMenu;
    
    private static int ENTER = 32;
    
    private static final String cImage  = "images\\cursor.gif";
    
    private static final double[] MESSX = {-.97, -.97,  .5, .5};
    private static final double[] MESSY = {-.97,-.7, -.7, -.97};

    private static final double[] BMESSX  = {-0.97, -0.97,     0,     0};
    private static final double[] BMESSY  = {-0.97, -0.40, -0.40, -0.97};
    
    private static final double[] MENUX = {.5,  .97,   .97,  .5};
    private static final double[] MENUY = {-.97, -.97, -.7, -.7};
    
    private static final double[] ESBX = {-.95, -.35, -.35, -.95};
    private static final double[] ESBY = { .95, .95, .75, .75};
    private static final double[] EHBX = { -.8, -.4};
    private static final double   EHBY =   .82;
    
    private static final double[] PSBX = { .95, .35, .35, .95};
    private static final double[] PSBY = {-.68, -.68, -.48, -.48};
    private static final double[] PHBX = {  .5, .9};
    private static final double   PHBY =  -.63;
    
    private static final double BORDER = 0.004;
    
    private static final int SCREENH = 768;
    private static final int SCREENW = 1024;
    
    private static String worldBack;
    private static String fightBack;
    
    private static final double EPX = 0.75;
    private static final double EPY = 0.5;
    private static final double PPX = -0.6;
    private static final double PPY = -0.25;
    
    private static final double ENWID = .4;
    private static final double ENHI = .7;

    private static final double BWID = .1;
    private static final double BHI = .1;
    
    private static final double PWID = .6;
    private static final double PHI = .9;
    
    private static final Font ITEM = new Font(Font.MONOSPACED, 0, 22);
    
    private static boolean battle;
    private static boolean wild;
    private static boolean menu;
    private static boolean world;
    private static boolean message;
    private static boolean mustChoose;
    private static boolean fromItem;
    private static boolean lock;

    private static int shelf;
    
    private static boolean players;
    
    private static boolean enPoke;
    private static boolean enPokeStats;
    
    private static boolean mainPoke;
    private static boolean mainPokeStats;
    
    private static Player main;
    private static Player enemy;
    
    private static Pokemon mainOut;
    private static Pokemon enOut;

    private static int enPokeOut;
    private static int mainPokeOut;
    private static int mainPokeFaint;
    
    private static int cursor;
    private static int depth;
    
    private static Item[] usable;
    private static Item toUse;
    
    private static boolean myTurn;
    
    
    // Thus ends the hella constant block
    
    public static void setEnemy(Player e) {
        System.out.println("yo");
        enemy = e;
        enOut = enemy.getPokemon(0);
    }
    
    public static boolean isWild() {
        return wild;
    }

    public static void setMain(Player m) {
        main = m;
    }
    
    public static void setFightBackground(String s) {
        fightBack = s;
    }
    
    public static void setMainBackground(String s) {
        worldBack = s;
    }
    
    public static void messageUpdate() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledPolygon(MESSX, MESSY);
        StdDraw.setPenRadius(BORDER);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.polygon(MESSX, MESSY);
        StdDraw.setPenRadius();
        if (myTurn) {
            switch(currentMenu) {
                case 0: {
                    StdDraw.textLeft(-.9,  -.78, main.getMove(mainPokeOut, 0));
                    StdDraw.textLeft(-.9,  -.88, main.getMove(mainPokeOut, 1));
                    StdDraw.textLeft(-.35, -.78, main.getMove(mainPokeOut, 2));
                    StdDraw.textLeft(-.35, -.88, main.getMove(mainPokeOut, 3));   
                    break;
                }
                case 1: StdDraw.textLeft(-.9, -.8, Message.getMessage()); break;
                case 2: StdDraw.textLeft(-.9, -.8, "bagmenu");            break;
                case 3: StdDraw.textLeft(-.9, -.8, Message.getMessage()); break;
                case 4: StdDraw.textLeft(-.9, -.8, Message.getMessage()); break;
            } 
        }
        else StdDraw.textLeft(-.9, -.8, Message.getMessage());
    }
    
    public static void menuUpdate() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledPolygon(MENUX, MENUY);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(BORDER);
        StdDraw.polygon(MENUX, MENUY);
        
        StdDraw.textLeft(0.58, -0.78, BMENU[0]);
        StdDraw.textLeft(0.58, -0.88, BMENU[1]);
        StdDraw.textLeft(0.8, -0.78, BMENU[2]);
        StdDraw.textLeft(0.8, -0.88, BMENU[3]);
        
        switch(currentMenu) {
            case 0: {
                int spot = getBattleCursorLocation();
                if (myTurn) {
                    switch(spot) {
                        case 0: StdDraw.picture(-.95, -0.78, cImage); break;
                        case 1: StdDraw.picture(-.95, -0.88, cImage); break;
                        case 2: StdDraw.picture( -.4, -0.78, cImage); break;
                        case 3: StdDraw.picture( -.4, -0.88, cImage); break;
                    }
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledPolygon(MENUX, MENUY);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.setPenRadius(BORDER);
                    StdDraw.polygon(MENUX, MENUY);
                    StdDraw.text(0.72, -0.78,"PP:");
                    int[] pp = mainOut.getMove(cursor).getPP();
                    StdDraw.text(0.72, -0.88, pp[0] + "/" + pp[1]);
                } 
                
            } break;
            
            case 1: break;
            case 2: break;
            case 3: break;
            
            case 4: {
                int spot = getBattleCursorLocation();
                switch(spot) {
                    case 0: StdDraw.picture(0.55, -0.78, cImage); break;
                    case 1: StdDraw.picture(0.55, -0.88, cImage); break;
                    case 2: StdDraw.picture(0.75, -0.78, cImage); break;
                    case 3: StdDraw.picture(0.75, -0.88, cImage); break;
                }
            }
        } 
    }
    
    public static void update() {
        if (enPokeOut != enemy.getTeamSize() && mainPokeOut != main.getTeamSize()) {
            mainOut = main.getPokemon(mainPokeOut);
            enOut = enemy.getPokemon(enPokeOut);
        }
        StdDraw.picture(0, 0, fightBack);
        
        if (world) {
            return;  
        }      
        if (players) {
            StdDraw.picture(EPX, EPY, enemy.getImage(), ENWID, ENHI);
            StdDraw.picture(PPX, PPY, main.getImage(), PWID, PHI);
        }
        
        if (enPoke) {
            StdDraw.picture(EPX, EPY, enOut.getImage(), ENWID, ENHI);
        } 
        
        if (mainPoke) 
            StdDraw.picture(PPX, PPY, mainOut.getImage(), PWID, PHI);
        
        if (message) {
            messageUpdate();
        }
        if (menu) {
            menuUpdate();
        }
        
        if (enPokeStats) {
            StdDraw.setPenColor(StdDraw.WHITE); 
            StdDraw.filledPolygon(ESBX, ESBY);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(BORDER);
            StdDraw.polygon(ESBX, ESBY);
            StdDraw.textLeft(-.93, .9, enOut.getName());
            StdDraw.setPenRadius(0.012);
            StdDraw.line(EHBX[0], EHBY, EHBX[1], EHBY);
            
            Pokemon en = enOut;
            double perLine = (double) en.getTempHealth() / en.getMaxHealth();
            double length = (EHBX[1] - EHBX[0]) * perLine;
            
            StdDraw.setPenColor(StdDraw.GREEN);
            if (perLine < .50) StdDraw.setPenColor(StdDraw.ORANGE);
            if (perLine < .15) StdDraw.setPenColor(StdDraw.RED);
            
            StdDraw.setPenRadius(0.006);
            if (length > 0)
                StdDraw.line(EHBX[0], EHBY, EHBX[0] + length, EHBY);
            
            String stat = enOut.getStatus();
            if (!stat.equals("no"))
                StdDraw.picture(EHBX[0] - 0.06, EHBY, "images\\" + stat + ".png");
        }
        
        if (mainPokeStats) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledPolygon(PSBX, PSBY);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(BORDER);
            StdDraw.polygon(PSBX, PSBY);
            StdDraw.textLeft(.37, -.55, mainOut.getName());
            StdDraw.setPenRadius(0.012);
            StdDraw.line(PHBX[0], PHBY, PHBX[1], PHBY);
            StdDraw.setPenRadius();
            
            Pokemon mp = mainOut;
            double perLine = (double) mp.getTempHealth() / mp.getMaxHealth();
            double length = (EHBX[1] - EHBX[0]) * perLine;
            
            StdDraw.setPenColor(StdDraw.GREEN);
            if (perLine < .50) StdDraw.setPenColor(StdDraw.ORANGE);
            if (perLine < .15) StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.006);
            if (length > 0)
                StdDraw.line(PHBX[0], PHBY, PHBX[0] + length, PHBY);
            StdDraw.setPenRadius();
            StdDraw.setPenColor();
            StdDraw.textRight(PHBX[1], -.55, "HP: " + mp.getTempHealth() + "/" + mp.getMaxHealth());

            String stat = mainOut.getStatus();
            if (!stat.equals("no"))
                StdDraw.picture(PHBX[0] - 0.06, PHBY, "images\\" + stat + ".png");
        }
        
        if (battle) {
            if (enPokeOut == enemy.getTeamSize()) {
                HBA.winBattle();
            }
            if (mainPokeFaint == main.getTeamSize()) {
                HBA.loseBattle();
            }
        }
        
        if (currentMenu == 1)
            PokeDraw.draw(main, cursor);
        if (currentMenu == 2) {
            Bag.draw(main, cursor, shelf); 
            if (depth == 1) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledPolygon(BMESSX, BMESSY);
                StdDraw.setPenRadius(BORDER);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.polygon(BMESSX, BMESSY);
                StdDraw.textLeft(-0.95, -0.45, Message.getMessage());
            }
        }
        
        StdDraw.show(10);
    }
    
    private static void fadeOut(String newBackground) {
        StdDraw.setPenColor();
        for (double i = 0; i < 2; i += 0.05) {
            //StdDraw.picture(0, 0, fightBack);
            StdDraw.filledCircle(0, 0, i);
            StdDraw.show(10);
        }
        
        for (double i = 2; i >= 0; i -= 0.05) {
            //StdDraw.picture(0, 0, fightBack);
            StdDraw.filledCircle(0, 0, i);
            StdDraw.show(10);
        }
        StdDraw.picture(0, 0, newBackground, 2, 2);
        StdDraw.show(10);
    }
    
    public static void openSequence(String newBackground) throws IOException {
        fadeOut(newBackground);
        StdDraw.setFont(ITEM);
        world = false;
        battle = true;
        menu = true;
        myTurn = true;
        fromItem = false;
        lock = false;
        message = true;
        usable = main.getItems();
        currentMenu = 4;
        depth = 0;
        shelf = 0;
        enPokeOut = 0;
        enOut = enemy.getPokemon(0);
        mainPokeFaint = 0;
        if (!wild) {
            showPlayers(newBackground);
        }

        else {
            Message.wildAppear(enemy);
            StdDraw.picture(0, 0, fightBack);
            StdDraw.picture(EPX, EPY, enemy.getImage(), ENWID, ENHI);
            StdDraw.picture(PPX, PPY, main.getImage(), PWID, PHI);
            messageUpdate();
            StdDraw.show(10);
            message = true;
            menu = true;
            players = false;
            enPoke = true;
            enPokeStats = true;
            menuUpdate();
            StdDraw.show(10);
            Display.timeDelay();
            Display.timeDelay();
            do  {} while (!StdDraw.hasNextKeyTyped());
            message = true;
            
            for (double i = 0; i < 2; i += 0.04) {
                StdDraw.picture(EPX, EPY, enemy.getImage(), ENWID, ENHI);
                StdDraw.picture(PPX - i, PPY, main.getImage(), PWID, PHI);
                messageUpdate();
                menuUpdate();
                StdDraw.show(10);
                StdDraw.picture(0, 0, fightBack);
            }
            mainPokeSequence();
        }
    }
    
    public static void winSequence() {
        //StdAudio.play("music\\win.mid");
        mainPoke = false;
        enPoke = false;
        mainPokeStats = false;
        enPokeStats = false;
        battle = false;
        if (!wild) {
            for (double i = 0; i < 2; i += 0.04) {
                update();
                StdDraw.picture(PPX - i, PPY, mainOut.getImage(), PWID, PHI);
                StdDraw.show(10);
                StdDraw.picture(0, 0, fightBack);
            }        
            StdDraw.clear();
            Message.endBattle(main, enemy);
            update();
            endPlayers();
            do {} while (!StdDraw.isKeyPressed(ENTER));
            Message.retort(enemy);
            showMessage();
            world = true;
            players = false;
            do {} while (!StdDraw.isKeyPressed(ENTER));
        }
        else {
            Message.winWild(enemy);
            update();
            showMessage();
            do{} while (!StdDraw.isKeyPressed(ENTER));
            world = true;
            players = false;
        }

        fadeOut(worldBack);
        return;
    }
    
    public static void loseSequence() {
        mainPoke = false;
        enPoke = false;
        mainPokeStats = false;
        enPokeStats = false;
        battle = false;
        if (!wild) {
            for (double i = 2; i > 0; i -= 0.04) {
                update();
                StdDraw.picture(EPX + i, EPY, enemy.getImage(), ENWID, ENHI);
                StdDraw.show(10);
                StdDraw.picture(0, 0, fightBack);
            }
            StdDraw.clear();
            Message.endBattle(enemy, main);
            update();
            endPlayers();
            do {} while (!StdDraw.isKeyPressed(ENTER));
            Message.smirk(enemy);
            showMessage();
            world = true;
            players = false;
            do {} while (!StdDraw.isKeyPressed(ENTER));
        }

        else {
            Message.loseWild(enemy, main);
            update();
            showMessage();
            do{} while (!StdDraw.isKeyPressed(ENTER));
            world = true;
            players = false;
        }

        fadeOut(worldBack);
        return;
    }

    public static void captureSequence() throws IOException {
        //StdAudio.play("music\\win.mid");
        Message.capture(enemy);
        update();
        do {} while (!StdDraw.isKeyPressed(ENTER));
        mainPoke = false;
        enPoke = false;
        mainPokeStats = false;
        enPokeStats = false;
        battle = false;
        main.addPokemon(enemy.getPokemon(0));
    }
    
    private static void swapPokemon(int toWhich) {
        currentMenu = 4;
        menu = true;
        mainPoke = false;
        mainPokeStats = false;
        Message.thatsEnough(mainOut);

        for (double i = 0; i < 2; i += 0.04) {
            update();
            StdDraw.picture(PPX - i, PPY, mainOut.getImage(), PWID, PHI);
            StdDraw.show(10);
            StdDraw.picture(0, 0, fightBack);
        }
        
        mainPokeOut = toWhich;
        mainOut = main.getPokemon(mainPokeOut);
        mainPokeSequence();
        mainPoke = true;
        mainPokeStats = true;
    }
    
    public static void enPokeSequence() {
        if (enPokeOut == enemy.getTeamSize()) {
            enPoke = false;
            enPokeStats = false;
            return;
        }
        if (!wild)
            enOut = enemy.getPokemon(enPokeOut);
        String enemyOut = enemy.getName() + " sent out a " + enOut.getName() + "!";
        Message.customSet(enemyOut);
        messageUpdate();
        for (double i = 2; i > 0; i -= 0.04) {
            update();
            StdDraw.picture(EPX + i, EPY, enOut.getImage(), ENWID, ENHI);
            StdDraw.show(10);
            StdDraw.picture(0, 0, fightBack);
        }
        enPokeStats = true;
        enPoke = true;
        update();
        timeDelay();
        myTurn = true;
        cursor = 0;
        currentMenu = 4;
        Message.decide(mainOut);
        update();
    }
    
    public static void mainPokeSequence() {
        currentMenu = 4;
        menu = true;
        mainPoke = false;
        mainPokeStats = false;
        mainOut = main.getPokemon(mainPokeOut);
        String mess = "You're up, " + mainOut.getName() + "!";
        Message.customSet(mess);
        messageUpdate();
        update();
        for (double i = 2; i > 0; i -= 0.04) {
            update();
            StdDraw.picture(PPX - i, PPY, mainOut.getImage(), PWID, PHI);
            StdDraw.show(10);
            StdDraw.picture(0, 0, fightBack);
        }
        mainPokeStats = true;
        mainPoke = true;
        
        update();
        timeDelay();
        myTurn = true;
        cursor = 0;
        currentMenu = 4;
        Message.decide(mainOut);
        update();
    }
    
    public static void enemyFaintSequence() {
        enPoke = false;
        myTurn = false;
        timeDelay();
        Message.fainted(enemy, enOut);
        showMessage();
        for (double i = 0; i < 0.1; i += 0.04) {
            update();
            StdDraw.picture(EPX, EPY + i, enOut.getImage(), ENWID, ENHI);
            StdDraw.show(10);
            StdDraw.picture(0, 0, fightBack);
        }
        for (double i = 0; i < 0.3; i += 0.04) {
            update();
            StdDraw.picture(EPX, EPY - i, enOut.getImage(), ENWID, ENHI);
            StdDraw.show(10);
            StdDraw.picture(0, 0, fightBack);
        }
        StdDraw.picture(0, 0, fightBack);
        timeDelay();
        StdDraw.show(10);
        
        enPokeOut++;
        enPokeSequence();

        if (mainOut.getStatus().equals("PSN")) {
            poisonSequence(mainOut);
        }
        if (mainOut.getStatus().equals("BRN")) {
            burnSequence(mainOut);
        }
    }
    
    public static void playerFaintSequence() {
        mainPoke = false;
        myTurn = false;
        mainPokeFaint++;
        timeDelay();
        Message.fainted(main, mainOut);
        messageUpdate();
        StdDraw.show(10);
        for (double i = 0; i < 0.1; i += 0.04) {
            update();
            StdDraw.picture(PPX, PPY + i, mainOut.getImage(), PWID, PHI);
            StdDraw.show(40);
        }
        for (double i = 0; i < 0.2; i += 0.04) {
            update();
            StdDraw.picture(PPX, PPY - i, mainOut.getImage(), PWID, PHI);
            messageUpdate();
            StdDraw.show(40);
        }
        timeDelay();
        StdDraw.show(10);
        if (!(mainPokeFaint == main.getTeamSize())) {
            mustChoose = true;
            Message.pokeMenu();
            depth = 0;
            currentMenu = 1;
        }
    }
    
    public static void timeDelay() {
        double a = System.currentTimeMillis();
        while (System.currentTimeMillis() - a < 500) {
        }
    }
    
    public static void interval() {
        double a = System.currentTimeMillis();
        while (System.currentTimeMillis() - a < 200) {
        }
    }
    
    private static void endPlayers() {
        for (double i = 2; i > 0; i -= 0.04) {
            update();
            StdDraw.picture(EPX + i, EPY, enemy.getImage(), ENWID, ENHI);
            StdDraw.picture(PPX - i, PPY, main.getImage(), PWID, PHI);
            StdDraw.show(10);
            StdDraw.picture(0, 0, fightBack);
        }
        players = true;
        messageUpdate();
        update();
        timeDelay();
    }
    
    private static void showPlayers(String newBackground) {
        StdDraw.picture(0, 0, fightBack);
        StdDraw.picture(EPX, EPY, enemy.getImage(), ENWID, ENHI);
        StdDraw.picture(PPX, PPY, main.getImage(), PWID, PHI);
        StdDraw.show(10);
        message = true;
        menu = true;
        players = false;
        Message.challenge(enemy, main);
        menuUpdate();
        StdDraw.show(10);
        Display.timeDelay();
        Display.timeDelay();
        do  {   
        } while (!StdDraw.hasNextKeyTyped());
        message = true;
        
        for (double i = 0; i < 2; i += 0.04) {
            update();
            StdDraw.picture(EPX + i, EPY, enemy.getImage(), ENWID, ENHI);
            StdDraw.picture(PPX - i, PPY, main.getImage(), PWID, PHI);
            StdDraw.show(10);
            StdDraw.picture(0, 0, fightBack);
        }
        
        StdDraw.picture(0, 0, fightBack);
        enPokeSequence();
        
        String playerOut = "Go, " + main.getPokemon(0).getName() + "!";
        Message.customSet(playerOut);
        messageUpdate(); 
        for (double i = 2; i > 0; i -= 0.04) {
            messageUpdate();
            menuUpdate();
            StdDraw.picture(PPX - i, PPY, main.getPokemon(0).getImage(), PWID, PHI);
            StdDraw.show(10);
            StdDraw.picture(0, 0, fightBack);
        }
        mainPokeStats = true;
        mainPoke = true;
        mainPokeOut = 0;
        Message.decide(mainOut);
        update();
    }
    
    public static void setBounds() {
        StdDraw.setCanvasSize(SCREENW, SCREENH);
        StdDraw.setXscale(-1, 1);
        StdDraw.setYscale(-1, 1);
    }
    
    public static void upCursor() {
        if (!lock) {
            switch(currentMenu) {
                case 0: {
                    switch(cursor) {
                        case 0: cursor++;    break;
                        case 1: cursor--;    break;
                        case 2: cursor++;    break;
                        case 3: cursor--;    break;
                    }
                }                            break;
                case 1: {
                    switch(cursor) {
                        case 0:  cursor = 5; break;
                        default: cursor--;   break;
                    }
                }                            break;
                case 2: {
                    if (shelf == 0) {
                        if (cursor == 0) 
                            cursor = main.getItems().length - 1;
                        else cursor--;
                    }
                    else {
                        if (cursor == 0) 
                            cursor = main.getBalls().length - 1;
                        else cursor--;
                    }
                }                            break;
                case 3:                      break;
                case 4: {
                    switch(cursor) {
                        case 0: cursor++;    break;
                        case 1: cursor--;    break;
                        case 2: cursor++;    break;
                        case 3: cursor--;    break;    
                    }
                }                            break;
            }
        }
    }
    
    public static void downCursor() {
        if (!lock) {
            switch(currentMenu) {
                case 0: {
                    switch(cursor) {
                        case 0: cursor++;    break;
                        case 1: cursor--;    break;
                        case 2: cursor++;    break;
                        case 3: cursor--;    break;
                    }
                }                            break;
                case 1: {
                    switch(cursor) {
                        case 5:  cursor = 0; break;
                        default: cursor++;   break;
                    }
                }                            break;
                case 2: {
                    if (shelf == 0) {
                        if (cursor == main.getItems().length - 1) 
                            cursor = 0;
                        else cursor++;
                    }
                    else {
                        if (cursor == 0) 
                            cursor = main.getBalls().length - 1;
                        else cursor++;
                    }
                }                            break;
                case 3:                      break;
                case 4: {
                    switch(cursor) {
                        case 0: cursor++;    break;
                        case 1: cursor--;    break;
                        case 2: cursor++;    break;
                        case 3: cursor--;    break;
                    }
                }                            break;
            }
        }
    }
    
    public static void leftCursor() {
        if (!lock) {
            switch(currentMenu) {
                case 0: {
                    switch(cursor) {
                        case 0: cursor += 2; break;
                        case 1: cursor += 2; break;
                        case 2: cursor -= 2; break;
                        case 3: cursor -= 2; break;
                    }
                }                            break;
                case 1: {
                    switch(cursor) {
                        case 0:  cursor = 1; break;
                        default: cursor = 0; break;
                    }
                }                            break;
                case 2: {
                    if (shelf == 0) shelf = 1;
                    else shelf = 0;
                    cursor = 0;
                }                            break;
                case 3:                      break;
                case 4: {
                    switch(cursor) {
                        case 0: cursor += 2; break;
                        case 1: cursor += 2; break;
                        case 2: cursor -= 2; break;
                        case 3: cursor -= 2; break;
                    }
                }                            break;
            }
        }
    }
    
    public static void rightCursor() {
        if (!lock) {
            switch(currentMenu) {
                case 0: {
                    switch(cursor) {
                        case 0: cursor += 2; break;
                        case 1: cursor += 2; break;
                        case 2: cursor -= 2; break;
                        case 3: cursor -= 2; break;
                    }
                }                            break;
                case 1: {
                    switch(cursor) {
                        case 0:  cursor = 1; break;
                        default: cursor = 0; break;
                    }
                }                            break;
                case 2: {
                    if (shelf == 0) shelf = 1;
                    else shelf = 0;
                    cursor = 0;
                }                            break;
                case 3:                      break;
                case 4: {
                    switch(cursor) {
                        case 0: cursor += 2; break;
                        case 1: cursor += 2; break;
                        case 2: cursor -= 2; break;
                        case 3: cursor -= 2; break;
                    }
                }                            break;
            }
        }
    }
    
    public static void poisonSequence(Pokemon poke) {
        if (poke == enOut)
            Message.enPoison(poke);
        else
            Message.poison(poke);
        update();
        timeDelay();
        timeDelay();
        int poison = (poke.getMaxHealth() / 8);
        poke.receive(poison, "no");
        Message.decide(mainOut);
    }

    public static void burnSequence(Pokemon poke) {
        if (poke == enOut)
            Message.enBurn(poke);
        else
            Message.burn(poke);
        update();
        timeDelay();
        timeDelay();
        int burn = (poke.getMaxHealth() / 12);
        poke.receive(burn, "no");
        Message.decide(mainOut);
    }

    public static void throwSequence(Ball toThrow) {
        int chance = (int) (Math.random() * 100);
        int adjusted = toThrow.getRate();
        if ((double) enOut.getTempHealth() / enOut.getMaxHealth() < 0.5) adjusted += 10;
        if ((double) enOut.getTempHealth() / enOut.getMaxHealth() < 0.25) adjusted += 15;
        if (!enOut.getStatus().equals("no")) adjusted += 15;
        
        for (double i = 0; i < 1.32; i += 0.04) {
                update();
                StdDraw.picture(PPX + i + (i / 5), PPY + i, toThrow.getImage(), BWID, BHI);
                StdDraw.show(10);
        }
        enPoke = false;
        update();
        if (chance < adjusted) {
            for (int j = 0; j < 3; j++) {
                StdDraw.picture(EPX, EPY, toThrow.getImage(), BWID, BHI);
                interval();
                for (double i = 0; i < 15; i++) {
                    StdDraw.picture(EPX, EPY, toThrow.getImage(), BWID, BHI, i);
                    StdDraw.show(10);
                }
                for (double i = 15; i > -15; i--) {
                    StdDraw.picture(EPX, EPY, toThrow.getImage(), BWID, BHI, i);
                    StdDraw.show(10);
                }
                for (double i = -15; i < 0; i++) {
                    StdDraw.picture(EPX, EPY, toThrow.getImage(), BWID, BHI, i);
                    StdDraw.show(10);
                }
            }
            HBA.capture();
        }
        else {
            enPoke = false;
            int times =  1 + (int) (Math.random() * 3);
            for (int j = 0; j < times; j++) {
                StdDraw.picture(EPX, EPY, toThrow.getImage(), BWID, BHI);
                StdDraw.show(10);
                interval();
                for (double i = 0; i < 15; i++) {
                    //update();
                    StdDraw.picture(EPX, EPY, toThrow.getImage(), BWID, BHI, i);
                    StdDraw.show(10);
                }
                for (double i = 15; i > -15; i--) {
                    //update();
                    StdDraw.picture(EPX, EPY, toThrow.getImage(), BWID, BHI, i);
                    StdDraw.show(10);
                }
                for (double i = -15; i < 0; i++) {
                    //update();
                    StdDraw.picture(EPX, EPY, toThrow.getImage(), BWID, BHI, i);
                    StdDraw.show(10);
                }
            }
            Message.escaped(enOut);
            enPoke = true;
            update();
            timeDelay();
            timeDelay();
            Message.decide(mainOut);
            enemyAction();
        }
    }
    
    public static int getBattleCursorLocation() {
        int toReturn = Math.abs(cursor % 4);
        return toReturn;
    }
    
    public static String getMenuType() {
        return BMENU[currentMenu];
    }
    
    public static void battleMenuAction() {
        switch(currentMenu) {
            case 0: {
                Move toUse = mainOut.getMove(cursor);
                if (!toUse.canUse()) return;
                currentMenu = 4;
                if (mainOut.getStatus().equals("PAR") && Math.random() < 0.25) {
                    Message.paralyze(mainOut);                    
                    update();
                    timeDelay();
                    timeDelay();
                }

                else if ((int) (Math.random() * 100) < toUse.getAccuracy()) {
                    Message.makeMove(mainOut, toUse);
                    showMessage();
                    fightAnimation(toUse.getTarget(), main);
                    
                    if (toUse.getTarget() >= 0) {
                        mainOut.useMove(cursor, enOut);
                    }
                    
                    else if (toUse.getTarget() < 0)
                        mainOut.useMove(cursor, mainOut);
                    update();
                }
                else {
                    Message.miss(mainOut);
                    showMessage();
                    timeDelay();
                    timeDelay();
                }
                
                if (enOut.isFaint())
                    enemyFaintSequence();
                else if(mainOut.isFaint()) {
                    playerFaintSequence();
                }
                else { 
                    timeDelay(); 
                    enemyAction(); 
                }
            cursor = 0;
            }
            break;
            case 1: {
                if (fromItem) {
                    if (depth == 0) {
                        if (cursor == 0) {
                            if (toUse.couldUse(mainOut)) {
                                Message.useItem(toUse, mainOut);
                                lock = true;
                                depth++;
                            }
                            else {
                                Message.noItemEffect();
                                update();
                                timeDelay();
                                timeDelay();
                                Message.item();
                            }
                        }
                        else if (cursor <= mainPokeOut) {
                            if (toUse.couldUse(main.getPokemon(cursor - 1))) {
                                Message.useItem(toUse, main.getPokemon(cursor - 1));
                                lock = true;
                                depth++;
                            }
                            else {
                                Message.noItemEffect();
                                update();
                                timeDelay();
                                timeDelay();
                                Message.item();
                            }
                        }
                        else {
                            if (toUse.couldUse(main.getPokemon(cursor))) {
                                Message.useItem(toUse, main.getPokemon(cursor));
                                lock = true;
                                depth++;
                            }
                            else {
                                Message.noItemEffect();
                                update();
                                timeDelay();
                                timeDelay();
                                Message.item();
                            }
                        }
                    }
                    else if (depth == 1) {
                        if (cursor == 0) {
                            toUse.use(mainOut);
                        }
                        else if (cursor <= mainPokeOut) {
                            toUse.use(main.getPokemon(cursor - 1));
                            Message.usedItem(toUse, main.getPokemon(cursor - 1));
                        }
                        else {
                            toUse.use(main.getPokemon(cursor));
                            Message.usedItem(toUse, main.getPokemon(cursor));
                        }
                        
                        toUse.reduce();
                        currentMenu = 4;
                        fromItem = false;
                        lock = false;
                        update();
                        timeDelay();
                        do {} while(!StdDraw.hasNextKeyTyped());
                        enemyAction();
                    }
                } 
                else {
                    if (depth == 0) {
                        if (cursor == 0) {
                            Message.alreadyOut(mainOut); 
                            update(); 
                        }
                        else if (cursor <= mainPokeOut) {
                            Message.pickPokemon(main.getPokemon(cursor - 1));
                            update();
                            lock = true;
                        }
                        else {
                            Message.pickPokemon(main.getPokemon(cursor));
                            update();               
                            lock = true;
                        }
                    }
                    else if (depth == 1) {
                        depth = 0;
                        if (mustChoose) {
                            mainPokeOut = PokeDraw.getLocator(cursor);
                            mainPokeSequence();

                            if (mainOut.getStatus().equals("PSN")) {
                                poisonSequence(mainOut);
                            }
                            if (enOut.getStatus().equals("PSN")) {
                                poisonSequence(enOut);
                            }
                            if (mainOut.getStatus().equals("BRN")) {
                                burnSequence(mainOut);
                            }
                            if (enOut.getStatus().equals("BRN")) {
                                burnSequence(enOut);
                            }
                            if (enOut.isFaint())
                                enemyFaintSequence();
                            if(mainOut.isFaint()) {
                                playerFaintSequence();
                            }

                            mustChoose = false;
                        }
                        else {
                            swapPokemon(PokeDraw.getLocator(cursor));
                            enemyAction();
                        }
                        lock = false;
                    }
                }
            } break;
            case 2:  {
                if (shelf == 0) {
                    if (usable[cursor].getNumber() > 0) {
                        Message.item();
                        toUse = usable[cursor];
                        fromItem = true;
                        currentMenu = 1;
                        cursor = 0;
                        depth = 0;
                    }
                    else Message.outOfItem(usable[cursor]);
                }
                else if (shelf == 1) {
                    Ball[] ballSet = main.getBalls();
                    Ball toThrow = ballSet[cursor];
                    if (depth == 0) {
                        if (wild) {
                            Message.ball(toThrow);
                            update();
                            depth++;
                        }
                        else {
                            Message.notWild();
                            StdDraw.setPenColor(StdDraw.WHITE);
                            StdDraw.filledPolygon(BMESSX, BMESSY);
                            StdDraw.setPenRadius(BORDER);
                            StdDraw.setPenColor(StdDraw.BLACK);
                            StdDraw.polygon(BMESSX, BMESSY);
                            StdDraw.textLeft(-0.95, -0.45, Message.getMessage());
                            StdDraw.show(10);
                            timeDelay();
                            update();
                        }
                    }
                    else if (depth == 1) {
                        currentMenu = 4;
                        throwSequence(toThrow);
                        toThrow.reduce();
                        currentMenu = 4;
                        cursor = 0;
                        depth = 0;
                    }
                }
            } break;
            case 3: break;
            case 4: {
                currentMenu = getBattleCursorLocation();
                switch(getBattleCursorLocation()) {
                    case 0: break;
                    case 1: {
                        menu = false;
                        cursor = 0;
                        Message.pokeMenu();
                        update();
                    } break;
                    case 2: cursor = 0; break;
                    case 3: runText(); showMessage(); currentMenu = 4; break;
                }
                break;
            }
        }
    }
    
    public static void addDepth() {
        depth++;
    }
    
    public static void backFunction() {
        switch(currentMenu) {
            case 1: {
                if (depth == 1) {
                    depth = 0;
                    Message.pokeMenu();
                    update();
                    lock = false;
                }
                else if (fromItem) {
                    cursor = 0;
                    currentMenu = 2;
                    lock = false;
                }
                else if (!mustChoose) {
                    currentMenu = 4;
                    cursor = 0;
                    Message.decide(mainOut);
                    menu = true;
                }
            }   break;
            case 4: break;
            default: currentMenu = 4; cursor = 0; menu = true; break;
        }
    }
    
    public static void enemyAction() {
        currentMenu = 4;
        int move = (int) (Math.random() * 4);
        do { move = (int) (Math.random() * 4); } while (!enOut.getMove(move).canUse());
        Move toUse = enOut.getMove(move);

        if (enOut.getStatus().equals("PAR") && Math.random() < 0.25) {
            Message.paralyze(enOut);
            update();
            timeDelay();
        }

        else if ((int) (Math.random() * 100) < toUse.getAccuracy()) {
            Message.makeMove(enOut, toUse);
            showMessage();
            
            fightAnimation(toUse.getTarget(), enemy);
            
            if (toUse.getTarget() >= 0) 
                enOut.useMove(move, mainOut);
            
            else if (toUse.getTarget() < 0)
                enOut.useMove(move, enOut);
            
            StdDraw.show(10);
            timeDelay();
            update();
            
            if (enOut.isFaint())
                enemyFaintSequence();
            else if(mainOut.isFaint()) {
                playerFaintSequence();
            }
        }
        else {
            Message.miss(enemy, enOut);
            showMessage();
            timeDelay();
            timeDelay();
        }
        if (!mustChoose && !mainOut.isFaint()) {
            if (mainOut.getStatus().equals("PSN")) {
                poisonSequence(mainOut);
            }
            if (enOut.getStatus().equals("PSN")) {
                poisonSequence(enOut);
            }
            if (mainOut.getStatus().equals("BRN")) {
                burnSequence(mainOut);
            }
            if (enOut.getStatus().equals("BRN")) {
                burnSequence(enOut);
            }
            if (enOut.isFaint())
                enemyFaintSequence();
            if(mainOut.isFaint()) {
                playerFaintSequence();
            }
        }

        timeDelay();
        Message.decide(mainOut);
    } 
    
    private static void runText() {
        Message.run();
        update();
        timeDelay();
        Message.decide(mainOut);
    }
    
    private static void showMessage() {
        messageUpdate();
        StdDraw.show(10);
        timeDelay();
    }
    
    private static void fightAnimation(int type, Player initiator) {
        update();
        if (type <  0) {
            StdDraw.setPenColor(StdDraw.GREEN);
            for(double i = 0; i < .5; i += 0.04) {
                update();
                if (initiator == main)
                    StdDraw.filledCircle(PPX + i + (i / 5), PPY + i, 0.04);
                else if (initiator == enemy)
                    StdDraw.filledCircle(EPX - i, EPY - i, 0.04);
                StdDraw.show(10); 
            }
            update();
            StdDraw.setPenColor(StdDraw.BLUE);
            for (double i = .5; i > 0; i -= 0.04) {
                update();
                if (initiator == main)
                    StdDraw.filledCircle(PPX + i + (i / 5), PPY + i, 0.04);
                else if (initiator == enemy)
                    StdDraw.filledCircle(EPX - i, EPY - i, 0.04);
                StdDraw.show(10);
            }
            StdDraw.setPenColor();
            update();
        }
        else if (type > 0) {
            StdDraw.setPenColor(StdDraw.GREEN);
            for(double i = 0; i < 2; i += 0.04) {
                update();
                if (initiator == main)
                    StdDraw.filledCircle(PPX + i + (i / 5), PPY + i, 0.04);
                else if (initiator == enemy)
                    StdDraw.filledCircle(EPX - i, EPY - i, 0.04);
                StdDraw.show(10);
            }
            StdDraw.setPenColor();
        }
    }

    public static void bringBack() {
        mainPokeFaint--;
    }
    
    public static Player getPlayer() {
        return main;
    }
    
    public static int getPokeOut() {
        return mainPokeOut;
    }

    public static void setWild(boolean truth) {
        wild = truth;
    }
    
    public static void main(String[] args) throws IOException {
        HBA.main(args);
    }
}