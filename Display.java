import java.io.*;
import java.awt.*;
public class Display {
    private static final String[] BMENU = {"fight", "pokemon", "bag", "run", "main"};
    private static int currentMenu;
    
    private static int ENTER = 32;
    
    private static final String cImage  = "images\\cursor.gif";
    
    private static final double[] MESSX = {-.97, -.97,  .5, .5};
    private static final double[] MESSY = {-.97,-.7, -.7, -.97};
    
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
    private static final double EPY = 0.75;
    private static final double PPX = -0.75;
    private static final double PPY = -0.45;
    
    private static final double ENWID = .15;
    private static final double ENHI = .3;
    
    private static final double PWID = .225;
    private static final double PHI = .45;
    
    private static final Font ITEM = new Font(Font.MONOSPACED, 0, 22);
    
    private static boolean battle;
    private static boolean menu;
    private static boolean world;
    private static boolean message;
    private static boolean mustChoose;
    private static boolean fromItem;
    private static boolean lock;
    
    private static boolean players;
    
    private static boolean enPoke;
    private static boolean enPokeStats;
    
    private static boolean mainPoke;
    private static boolean mainPokeStats;
    
    private static Player main;
    private static Player enemy;
    
    private static int enPokeOut;
    private static int mainPokeOut;
    
    private static int cursor;
    private static int depth;
    
    private static Item[] usable;
    private static Item toUse;
    
    private static boolean myTurn;
    
    
    // Thus ends the hella constant block
    
    public static void setEnemy(Player e) {
        enemy = e;
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
        
        StdDraw.picture(0, 0, fightBack, 2, 2);
        
        if (world) {
            return;  
        }      
        if (players) {
            StdDraw.picture(EPX, EPY, enemy.getImage(), ENWID, ENHI);
            StdDraw.picture(PPX, PPY, main.getImage(), PWID, PHI);
        }
        
        if (enPoke) {
            StdDraw.picture(EPX, EPY, enemy.getPokemon(enPokeOut).getImage());
        } 
        
        if (mainPoke) 
            StdDraw.picture(PPX, PPY, main.getPokemon(mainPokeOut).getImage());
        
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
            StdDraw.textLeft(-.93, .9, enemy.getPokemon(enPokeOut).getName());
            StdDraw.setPenRadius(0.012);
            StdDraw.line(EHBX[0], EHBY, EHBX[1], EHBY);
            
            Pokemon en = enemy.getPokemon(enPokeOut);
            double perLine = (double) en.getTempHealth() / en.getMaxHealth();
            double length = (EHBX[1] - EHBX[0]) * perLine;
            
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.setPenRadius(0.006);
            if (length > 0)
                StdDraw.line(EHBX[0], EHBY, EHBX[0] + length, EHBY);
            
            String stat = enemy.getPokemon(enPokeOut).getStatus();
            if (!stat.equals("no"))
                StdDraw.picture(EHBX[0] - 0.06, EHBY, "images\\" + stat + ".png");
        }
        
        if (mainPokeStats) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledPolygon(PSBX, PSBY);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(BORDER);
            StdDraw.polygon(PSBX, PSBY);
            StdDraw.textLeft(.37, -.55, main.getPokemon(mainPokeOut).getName());
            StdDraw.setPenRadius(0.012);
            StdDraw.line(PHBX[0], PHBY, PHBX[1], PHBY);
            StdDraw.setPenRadius();
            
            Pokemon mp = main.getPokemon(mainPokeOut);
            double perLine = (double) mp.getTempHealth() / mp.getMaxHealth();
            double length = (EHBX[1] - EHBX[0]) * perLine;
            
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.setPenRadius(0.006);
            if (length > 0)
                StdDraw.line(PHBX[0], PHBY, PHBX[0] + length, PHBY);
            StdDraw.setPenRadius();
            StdDraw.setPenColor();
            StdDraw.textRight(PHBX[1], -.55, "HP: " + mp.getTempHealth() + "/" + mp.getMaxHealth());

            String stat = enemy.getPokemon(enPokeOut).getStatus();
            if (!stat.equals("no"))
                StdDraw.picture(EHBX[0] - 0.06, EHBY, "images\\" + stat + ".png");
        }
        
        if (battle) {
            if (enPokeOut == enemy.getTeamSize()) {
                HBA.winBattle();
            }
            if (mainPokeOut == main.getTeamSize()) {
                HBA.loseBattle();
            }
        }
        
        if (currentMenu == 1)
            PokeDraw.draw(main, cursor);
        if (currentMenu == 2) 
            Bag.draw(main, cursor);
        
        StdDraw.show(5);
    }
    
    private static void fadeOut(String newBackground) {
        StdDraw.setPenColor();
        for (double i = 0; i < 2; i += 0.05) {
            //StdDraw.picture(0, 0, fightBack);
            StdDraw.filledCircle(0, 0, i);
            StdDraw.show(5);
        }
        
        for (double i = 2; i >= 0; i -= 0.05) {
            //StdDraw.picture(0, 0, fightBack);
            StdDraw.filledCircle(0, 0, i);
            StdDraw.show(5);
        }
        StdDraw.picture(0, 0, newBackground, 2, 2);
        StdDraw.show(5);
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
        usable = main.getItems();
        currentMenu = 4;
        depth = 0;
        showPlayers(newBackground);
    }
    
    public static void winSequence(Player enemy, Player main) {
        StdAudio.play("win.mid");
        mainPoke = false;
        enPoke = false;
        mainPokeStats = false;
        enPokeStats = false;
        battle = false;
        for (double i = 0; i < 2; i += 0.04) {
            update();
            StdDraw.picture(PPX - i, PPY, main.getPokemon(mainPokeOut).getImage());
            StdDraw.show(5);
            StdDraw.picture(0, 0, fightBack, 2, 2);
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
        fadeOut(worldBack);
        return;
    }
    
    public static void loseSequence(Player enemy, Player main) {
        mainPoke = false;
        enPoke = false;
        mainPokeStats = false;
        enPokeStats = false;
        battle = false;
        for (double i = 2; i > 0; i -= 0.04) {
            update();
            StdDraw.picture(EPX + i, EPY, enemy.getImage(), ENWID, ENHI);
            StdDraw.show(5);
            StdDraw.picture(0, 0, fightBack, 2, 2);
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
        fadeOut(worldBack);
        return;
    }
    
    private static void swapPokemon(int toWhich) {
        currentMenu = 4;
        menu = true;
        mainPoke = false;
        mainPokeStats = false;
        Message.thatsEnough(main.getPokemon(mainPokeOut));
        for (double i = 0; i < 2; i += 0.04) {
            update();
            StdDraw.picture(PPX - i, PPY, main.getPokemon(mainPokeOut).getImage());
            StdDraw.show(5);
            StdDraw.picture(0, 0, fightBack, 2, 2);
        }
        
        mainPokeOut = toWhich;
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
        String enemyOut = enemy.getName() + " sent out a " + enemy.getPokemon(enPokeOut).getName() + "!";
        Message.customSet(enemyOut);
        messageUpdate();
        for (double i = 2; i > 0; i -= 0.04) {
            update();
            StdDraw.picture(EPX + i, EPY, enemy.getPokemon(enPokeOut).getImage());
            StdDraw.show(5);
            StdDraw.picture(0, 0, fightBack, 2, 2);
        }
        enPokeStats = true;
        enPoke = true;
        update();
        timeDelay();
        myTurn = true;
        cursor = 0;
        currentMenu = 4;
        Message.decide(main.getPokemon(mainPokeOut));
        update();
    }
    
    public static void mainPokeSequence() {
        currentMenu = 4;
        menu = true;
        mainPoke = false;
        mainPokeStats = false;
        String mainOut = "You're up, " + main.getPokemon(mainPokeOut).getName() + "!";
        Message.customSet(mainOut);
        messageUpdate();
        update();
        for (double i = 2; i > 0; i -= 0.04) {
            update();
            StdDraw.picture(PPX - i, PPY, main.getPokemon(mainPokeOut).getImage());
            StdDraw.show(5);
            StdDraw.picture(0, 0, fightBack, 2, 2);
        }
        mainPokeStats = true;
        mainPoke = true;
        
        update();
        timeDelay();
        myTurn = true;
        cursor = 0;
        currentMenu = 4;
        Message.decide(main.getPokemon(mainPokeOut));
        update();
    }
    
    public static void enemyFaintSequence() {
        enPoke = false;
        myTurn = false;
        timeDelay();
        Message.fainted(enemy, enemy.getPokemon(enPokeOut));
        showMessage();
        for (double i = 0; i < 0.1; i += 0.04) {
            update();
            StdDraw.picture(EPX, EPY + i, enemy.getPokemon(enPokeOut).getImage());
            StdDraw.show(10);
            StdDraw.picture(0, 0, fightBack, 2, 2);
        }
        for (double i = 0; i < 0.3; i += 0.04) {
            update();
            StdDraw.picture(EPX, EPY - i, enemy.getPokemon(enPokeOut).getImage());
            StdDraw.show(10);
            StdDraw.picture(0, 0, fightBack, 2, 2);
        }
        StdDraw.picture(0, 0, fightBack, 2, 2);
        timeDelay();
        StdDraw.show(5);
        
        enPokeOut++;
        enPokeSequence();
    }
    
    public static void playerFaintSequence() {
        mainPoke = false;
        myTurn = false;
        timeDelay();
        Message.fainted(main, main.getPokemon(mainPokeOut));
        messageUpdate();
        StdDraw.show(5);
        for (double i = 0; i < 0.1; i += 0.04) {
            update();
            StdDraw.picture(PPX, PPY + i, main.getPokemon(mainPokeOut).getImage());
            StdDraw.show(40);
        }
        for (double i = 0; i < 0.2; i += 0.04) {
            update();
            StdDraw.picture(PPX, PPY - i, main.getPokemon(mainPokeOut).getImage());
            messageUpdate();
            StdDraw.show(40);
        }
        timeDelay();
        StdDraw.show(5);
        mustChoose = true;
        Message.pokeMenu();
        depth = 0;
        currentMenu = 1;
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
            StdDraw.show(5);
            StdDraw.picture(0, 0, fightBack, 2, 2);
        }
        players = true;
        messageUpdate();
        update();
        timeDelay();
    }
    
    private static void showPlayers(String newBackground) {
        StdDraw.picture(0, 0, fightBack, 2, 2);
        StdDraw.picture(EPX, EPY, enemy.getImage(), ENWID, ENHI);
        StdDraw.picture(PPX, PPY, main.getImage(), PWID, PHI);
        StdDraw.show(5);
        message = true;
        menu = true;
        players = false;
        Message.challenge(enemy, main);
        menuUpdate();
        StdDraw.show(5);
        Display.timeDelay();
        Display.timeDelay();
        do  {   
        } while (!StdDraw.hasNextKeyTyped());
        message = true;
        
        for (double i = 0; i < 2; i += 0.04) {
            update();
            StdDraw.picture(EPX + i, EPY, enemy.getImage(), ENWID, ENHI);
            StdDraw.picture(PPX - i, PPY, main.getImage(), PWID, PHI);
            messageUpdate();
            StdDraw.show(5);
            StdDraw.picture(0, 0, fightBack, 2, 2);
        }
        
        StdDraw.picture(0, 0, fightBack);
        enPokeSequence();
        
        String playerOut = "Go, " + main.getPokemon(0).getName() + "!";
        Message.customSet(playerOut);
        messageUpdate(); 
        for (double i = 2; i > 0; i -= 0.04) {
            update();
            StdDraw.picture(PPX - i, PPY, main.getPokemon(0).getImage());
            StdDraw.show(5);
            StdDraw.picture(0, 0, fightBack, 2, 2);
        }
        mainPokeStats = true;
        mainPoke = true;
        mainPokeOut = 0;
        Message.decide(main.getPokemon(mainPokeOut));
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
                    if (cursor == 0) 
                        cursor = main.getItems().length - 1;
                    else cursor--;
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
                    if (cursor == main.getItems().length - 1) 
                        cursor = 0;
                    else cursor++;
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
                case 2:                      break;
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
                case 2:                      break;
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
    
    /*public static void enterBattle() {
        battle = true;
        world = false;
    }*/

    public static void poisonSequence(Pokemon poke) {
        Message.poison(poke);
        update();
        timeDelay();
        int poison = (poke.getMaxHealth() / 8);
        poke.receive(0, "no");
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
                int spot = getBattleCursorLocation();
                Move toUse = main.getPokemon(mainPokeOut).getMove(spot);
                currentMenu = 4;
                cursor = 0;
                if (Math.random() * 100 < toUse.getAccuracy()) {
                    
                    Message.makeMove(main.getPokemon(mainPokeOut), toUse);
                    showMessage();
                    fightAnimation(toUse.getTarget(), main);
                    
                    if (toUse.getTarget() >= 0)
                        toUse.makeMove(enemy.getPokemon(enPokeOut));
                    
                    else if (toUse.getTarget() < 0)
                        toUse.makeMove(main.getPokemon(mainPokeOut));
                    update();
                }
                else {
                    Message.miss(main.getPokemon(mainPokeOut));
                    showMessage();
                }
                
                if (enemy.getPokemon(enPokeOut).isFaint())
                    enemyFaintSequence();
                else if(main.getPokemon(mainPokeOut).isFaint()) {
                    playerFaintSequence();
                }
                else { timeDelay(); enemyAction(); }
            }
            break;
            case 1: {
                if (fromItem) {
                    if (depth == 0) {
                        depth++;
                        if (cursor == 0)
                            Message.useItem(toUse, main.getPokemon(mainPokeOut));
                        else if (cursor <= mainPokeOut)
                            Message.useItem(toUse, main.getPokemon(cursor - 1));
                        else
                            Message.useItem(toUse, main.getPokemon(cursor));
                        lock = true;
                    }
                    else if (depth == 1) {
                        if (cursor == 0) {
                            toUse.use(main.getPokemon(mainPokeOut));
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
                            Message.alreadyOut(main.getPokemon(mainPokeOut)); 
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
                if (usable[cursor].getNumber() > 0) {
                    Message.item();
                    toUse = usable[cursor];
                    fromItem = true;
                    currentMenu = 1;
                    cursor = 0;
                    depth = 0;
                }
                else Message.outOfItem(usable[cursor]);
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
                else if (!mustChoose) {
                    currentMenu = 4;
                    cursor = 0;
                    Message.decide(main.getPokemon(mainPokeOut));
                    menu = true;
                }
                else if (fromItem) {
                    cursor = 0;
                    currentMenu = 2;
                    lock = false;
                }
            }   break;
            case 4: break;
            default: currentMenu = 4; cursor = 0; menu = true; break;
        }
    }
    
    public static void enemyAction() {
        currentMenu = 4;
        int move = (int) (Math.random() * 4);
        Move toUse = enemy.getPokemon(enPokeOut).getMove(move);
        if (Math.random() * 100 < toUse.getAccuracy()) {
            Message.makeMove(enemy.getPokemon(enPokeOut), toUse);
            showMessage();
            
            fightAnimation(toUse.getTarget(), enemy);
            
            if (toUse.getTarget() >= 0) 
                toUse.makeMove(main.getPokemon(mainPokeOut));
            
            else if (toUse.getTarget() < 0)
                toUse.makeMove(enemy.getPokemon(enPokeOut));
            
            StdDraw.show(5);
            timeDelay();
            Message.decide(main.getPokemon(mainPokeOut));
            update();
            
            if (enemy.getPokemon(enPokeOut).isFaint())
                enemyFaintSequence();
            else if(main.getPokemon(mainPokeOut).isFaint()) {
                playerFaintSequence();
            }
        }
        else {
            Message.miss(enemy, enemy.getPokemon(enPokeOut));
            showMessage();
        }

        if (main.getPokemon(mainPokeOut).getStatus().equals("PSN")) {
            poisonSequence(main.getPokemon(mainPokeOut));
        }
        if (enemy.getPokemon(enPokeOut).getStatus().equals("PSN")) {
            poisonSequence(enemy.getPokemon(enPokeOut));
        }
    } 
    
    private static void runText() {
        Message.run();
        update();
        timeDelay();
        Message.decide(main.getPokemon(mainPokeOut));
    }
    
    private static void showMessage() {
        messageUpdate();
        StdDraw.show(5);
        timeDelay();
    }
    
    private static void fightAnimation(int type, Player initiator) {
        update();
        if (type <  0) {
            StdDraw.setPenColor(StdDraw.GREEN);
            for(double i = 0; i < 1; i += 0.04) {
                update();
                if (initiator == main)
                    StdDraw.filledCircle(PPX + i + (i / 5), PPY + i, 0.04);
                else if (initiator == enemy)
                    StdDraw.filledCircle(EPX - i, EPY - i, 0.04);
                StdDraw.show(5); 
            }
            update();
            StdDraw.setPenColor(StdDraw.BLUE);
            for (double i = 1; i > 0; i -= 0.04) {
                update();
                if (initiator == main)
                    StdDraw.filledCircle(PPX + i + (i / 5), PPY + i, 0.04);
                else if (initiator == enemy)
                    StdDraw.filledCircle(EPX - i, EPY - i, 0.04);
                StdDraw.show(5);
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
                StdDraw.show(5);
            }
            StdDraw.setPenColor();
        }
    }
    
    public static Player getPlayer() {
        return main;
    }
    
    public static int getPokeOut() {
        return mainPokeOut;
    }
    
    public static void main(String[] args) throws IOException {
        HBA.main(args);
    }
}