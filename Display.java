import java.io.*;
public class Display {
    private static final String[] BMENU = {"fight", "pokemon", "bag", "run", "main"};
    private static final String[] WORLDMENU = {"null"};
    private static int currentMenu;
    
    private static final String cImage  = "cursor.gif";
    private static final String mainBack = "shield.png";
    private static final String pImage = "pBackground.png";
    
    private static final double[] MESSX = {-.97, -.97,  .5, .5};
    private static final double[] MESSY = {-.97,-.8, -.8, -.97};
    
    private static final double[] MENUX = {.5,  .97,   .97,  .5};
    private static final double[] MENUY = {-.97, -.97, -.8, -.8};
    
    private static final double[] ESBX = {-.95, -.35, -.35, -.95};
    private static final double[] ESBY = { .98, .98, .78, .78};
    private static final double[] EHBX = { -.8, -.4};
    private static final double   EHBY =   .82;
    
    private static final double[] PSBX = { .95, .35, .35, .95};
    private static final double[] PSBY = {-.75, -.75, -.55, -.55};
    private static final double[] PHBX = {  .5, .9};
    private static final double   PHBY =  -.66;
    
    private static final double BORDER = 0.004;
    
    private static final int SCREENH = 768;
    private static final int SCREENW = 1024;
    
    private static String background;
    
    private static final double EPX = 0.75;
    private static final double EPY = 0.75;
    private static final double PPX = -0.75;
    private static final double PPY = -0.6;
    
    private static final double ENWID = .15;
    private static final double ENHI = .3;
    
    private static final double PWID = .225;
    private static final double PHI = .45;
    
    private static String decide;
    
    private static boolean battle;
    private static boolean menu;
    private static boolean world;
    private static boolean message;
    private static boolean roster;
    
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

    private static boolean myTurn;
    
    
    // Thus ends the hella constant block
    
    public static void setEnemy(Player e) {
        enemy = e;
    }
    
    public static void setMain(Player m) {
        main = m;
    }
    
    public static void setBackground(String s) {
        background = s;
    }
    
    public static String getBackground() {
        return background;
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
                    StdDraw.textLeft(-.9,  -.84, main.getMove(mainPokeOut, 0));
                    StdDraw.textLeft(-.9,  -.92, main.getMove(mainPokeOut, 1));
                    StdDraw.textLeft(-.35, -.84, main.getMove(mainPokeOut, 2));
                    StdDraw.textLeft(-.35, -.92, main.getMove(mainPokeOut, 3));   
                    break;
                }
                case 1: StdDraw.textLeft(-.9, -.875, Message.getMessage());  break;
                case 2: StdDraw.textLeft(-.9, -.875, "bagmenu");             break;
                case 3: StdDraw.textLeft(-.95, -.875, Message.getMessage()); break;
                case 4: StdDraw.textLeft(-.95, -.875, Message.getMessage()); break;
            } 
        }
        else StdDraw.textLeft(-.95, -.875, Message.getMessage());
    }
    
    public static void menuUpdate() {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledPolygon(MENUX, MENUY);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(BORDER);
        StdDraw.polygon(MENUX, MENUY);
        
        StdDraw.text(0.6, -0.85, BMENU[0]);
        StdDraw.text(0.63, -0.92, BMENU[1]);
        StdDraw.text(0.8, -0.85, BMENU[2]);
        StdDraw.text(0.8, -0.92, BMENU[3]);
        
        switch(currentMenu) {
            case 0: {
                int spot = getBattleCursorLocation();
                if (myTurn) {
                    switch(spot) {
                        case 0: StdDraw.picture(-.95, -0.84, cImage); break;
                        case 1: StdDraw.picture(-.95, -0.92, cImage); break;
                        case 2: StdDraw.picture( -.4, -0.84, cImage); break;
                        case 3: StdDraw.picture( -.4, -0.92, cImage); break;
                    }
                } 
                
            } break;
            
            case 1: break;
            case 2: break;
            case 3: break;
            
            case 4: {
                int spot = getBattleCursorLocation();
                switch(spot) {
                    case 0: StdDraw.picture(0.55, -0.84, cImage); break;
                    case 1: StdDraw.picture(0.55, -0.92, cImage); break;
                    case 2: StdDraw.picture(0.75, -0.84, cImage); break;
                    case 3: StdDraw.picture(0.75, -0.92, cImage); break;
                }
            }
        } 
    }
    
    public static void update() {
        
        StdDraw.picture(0, 0, background, 2, 2);
                
        if (players) {
            StdDraw.picture(EPX, EPY, enemy.getImage(), ENWID, ENHI);
            StdDraw.picture(PPX, PPY, main.getImage(), PWID, PHI);
        }
        
        if (enPoke) {
            StdDraw.picture(EPX, EPY, enemy.getPokemon(enPokeOut).getName() + ".png");
        } 
        
        if (mainPoke) 
            StdDraw.picture(PPX, PPY, main.getPokemon(mainPokeOut).getName() + ".png");
        
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
            StdDraw.textLeft(-.93, .95, enemy.getPokemon(enPokeOut).getName());
            StdDraw.setPenRadius(0.012);
            StdDraw.line(EHBX[0], EHBY, EHBX[1], EHBY);
            
            Pokemon en = enemy.getPokemon(enPokeOut);
            double perLine = (double) en.getTempHealth() / en.getMaxHealth();
            double length = (EHBX[1] - EHBX[0]) * perLine;
            
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.setPenRadius(0.006);
            if (length > 0)
                StdDraw.line(EHBX[0], EHBY, EHBX[0] + length, EHBY);
        }
        
        if (mainPokeStats) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledPolygon(PSBX, PSBY);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(BORDER);
            StdDraw.polygon(PSBX, PSBY);
            StdDraw.textLeft(.37, -.6, main.getPokemon(mainPokeOut).getName());
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
            StdDraw.textRight(PHBX[1], -.70, "HP: " + mp.getTempHealth() + "/" + mp.getMaxHealth());
        }
        
        if (battle) {
            if (enPokeOut == enemy.getTeamSize()) {
                HappyBirthdayAlana.winBattle();
            }
            if (mainPokeOut == main.getTeamSize()) {
                HappyBirthdayAlana.loseBattle();
            }
        }
        
        if (currentMenu == 1)
            PokeDraw.draw(main, cursor);
        
        StdDraw.show(5);
    }
    
    private static void fadeOut(String newBackground) {
        StdDraw.setPenColor();
        for (double i = 0; i < 2; i += 0.05) {
            StdDraw.picture(0, 0, background);
            StdDraw.filledCircle(0, 0, i);
            StdDraw.show(5);
        }
        background = newBackground;
        for (double i = 2; i >= 0; i -= 0.05) {
            StdDraw.picture(0, 0, background);
            StdDraw.filledCircle(0, 0, i);
            StdDraw.show(5);
        }
        StdDraw.picture(0, 0, background);
        StdDraw.show(5);
    }
    
    public static void openSequence(String newBackground) throws IOException {
        fadeOut(newBackground);
        world = false;
        battle = true;
        menu = true;
        myTurn = true;
        currentMenu = 4;
        depth = 0;
        showPlayers(newBackground);
    }
    
    public static void winSequence(Player enemy, Player main) {
        mainPoke = false;
        enPoke = false;
        mainPokeStats = false;
        enPokeStats = false;
        battle = false;
        StdDraw.clear();
        Message.endBattle(main, enemy);
        update();
        endPlayers();
        do {} while (!StdDraw.isKeyPressed(HappyBirthdayAlana.ENTER));
        Message.retort(enemy);
        showMessage();
        world = true;
        players = false;
        do {} while (!StdDraw.isKeyPressed(HappyBirthdayAlana.ENTER));
        fadeOut(mainBack);
        return;
    }
    
    public static void loseSequence(Player enemy, Player main) {
        mainPoke = false;
        enPoke = false;
        mainPokeStats = false;
        enPokeStats = false;
        battle = false;
        StdDraw.clear();
        Message.endBattle(enemy, main);
        update();
        endPlayers();
        do {} while (!StdDraw.isKeyPressed(HappyBirthdayAlana.ENTER));
        Message.smirk(enemy);
        showMessage();
        world = true;
        players = false;
        do {} while (!StdDraw.isKeyPressed(HappyBirthdayAlana.ENTER));
        fadeOut(mainBack);
        return;
    }
    
    public static void swapPokemon(int toWhich) {
        currentMenu = 4;
        menu = true;
        mainPoke = false;
        mainPokeStats = false;
        Message.thatsEnough(main.getPokemon(mainPokeOut));
        showMessage();
        for (double i = 0; i < 2; i += 0.04) {
            update();
            StdDraw.picture(PPX - i, PPY, main.getPokemon(mainPokeOut).getName() + ".png");
            StdDraw.show(5);
            StdDraw.picture(0, 0, background, 2, 2);
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
            StdDraw.picture(EPX + i, EPY, enemy.getPokemon(enPokeOut).getName() + ".png");
            StdDraw.show(5);
            StdDraw.picture(0, 0, background, 2, 2);
        }
        enPokeStats = true;
        enPoke = true;
        update();
        timeDelay();
        myTurn = true;
        cursor = 0;
        currentMenu = 4;
        Message.customSet(decide);
        update();
    }
    
    public static void mainPokeSequence() {
        if (mainPokeOut == main.getTeamSize()) {
            mainPoke = false;
            mainPokeStats = false;
            return;
        }
        String mainOut = "You're up, " + main.getPokemon(mainPokeOut).getName() + "!";
        Message.customSet(mainOut);
        messageUpdate();
        update();
        for (double i = 2; i > 0; i -= 0.04) {
            update();
            StdDraw.picture(PPX - i, PPY, main.getPokemon(mainPokeOut).getName() + ".png");
            StdDraw.show(5);
            StdDraw.picture(0, 0, background, 2, 2);
        }
        mainPokeStats = true;
        mainPoke = true;
        
        update();
        timeDelay();
        myTurn = true;
        cursor = 0;
        currentMenu = 4;
        Message.customSet(decide);
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
            StdDraw.picture(EPX, EPY + i, enemy.getPokemon(enPokeOut).getName() + ".png");
            StdDraw.show(10);
            StdDraw.picture(0, 0, background, 2, 2);
        }
        for (double i = 0; i < 0.3; i += 0.04) {
            update();
            StdDraw.picture(EPX, EPY - i, enemy.getPokemon(enPokeOut).getName() + ".png");
            StdDraw.show(10);
            StdDraw.picture(0, 0, background, 2, 2);
        }
        StdDraw.picture(0, 0, background, 2, 2);
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
        showMessage();
        for (double i = 0; i < 0.1; i += 0.04) {
            update();
            StdDraw.picture(PPX, PPY + i, main.getPokemon(mainPokeOut).getName() + ".png");
            StdDraw.show(40);
        }
        for (double i = 0; i < 0.2; i += 0.04) {
            update();
            StdDraw.picture(PPX, PPY - i, main.getPokemon(mainPokeOut).getName() + ".png");
            StdDraw.show(40);
        }
        timeDelay();
        StdDraw.show(5);
        
        mainPokeOut++;
        mainPokeSequence();
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
            StdDraw.picture(0, 0, background, 2, 2);
        }
        players = true;
        messageUpdate();
        update();
        timeDelay();
    }
    
    private static void showPlayers(String newBackground) {
        decide = "What will " + main.getPokemon(mainPokeOut).getName() + " do?";
        StdDraw.picture(0, 0, background, 2, 2);
        StdDraw.picture(EPX, EPY, enemy.getImage(), ENWID, ENHI);
        StdDraw.picture(PPX, PPY, main.getImage(), PWID, PHI);
        StdDraw.show(5);
        message = true;
        menu = true;
        players = false;
        Message.challenge(enemy, main);
        menuUpdate();
        StdDraw.show(5);
        do  {   
        } while (!StdDraw.hasNextKeyTyped());
        message = true;
        
        for (double i = 0; i < 2; i += 0.04) {
            update();
            StdDraw.picture(EPX + i, EPY, enemy.getImage(), ENWID, ENHI);
            StdDraw.picture(PPX - i, PPY, main.getImage(), PWID, PHI);
            StdDraw.show(5);
            StdDraw.picture(0, 0, background, 2, 2);
        }
        
        StdDraw.picture(0, 0, background);
        enPokeSequence();
        
        String playerOut = "Go, " + main.getPokemon(0).getName() + "!";
        Message.customSet(playerOut);
        messageUpdate(); 
        for (double i = 2; i > 0; i -= 0.04) {
            update();
            StdDraw.picture(PPX - i, PPY, main.getPokemon(0).getName() + ".png");
            StdDraw.show(5);
            StdDraw.picture(0, 0, background, 2, 2);
        }
        mainPokeStats = true;
        mainPoke = true;
        mainPokeOut = 0;
        Message.customSet(decide);
        update();
    }
    
    public static void setBounds() {
        StdDraw.setCanvasSize(SCREENW, SCREENH);
        StdDraw.setXscale(-1, 1);
        StdDraw.setYscale(-1, 1);
    }

    public static void upCursor() {
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
            case 2:                      break;
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

    public static void downCursor() {
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
            case 2:                      break;
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

    public static void leftCursor() {
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

    public static void rightCursor() {
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

    public static void enterBattle() {
        battle = true;
        world = false;
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
                    
                    if (toUse.getTarget() >= 0)
                        toUse.makeMove(enemy.getPokemon(enPokeOut));
                    
                    else if (toUse.getTarget() < 0)
                        toUse.makeMove(main.getPokemon(mainPokeOut));
                    
                    Message.makeMove(main.getPokemon(mainPokeOut), toUse);
                    showMessage();
                    fightAnimation(toUse.getTarget(), main);
                }
                else {
                    Message.miss(main.getPokemon(mainPokeOut));
                    showMessage();
                }
                
                if (enemy.getPokemon(enPokeOut).isFaint())
                    enemyFaintSequence();
                else if(main.getPokemon(mainPokeOut).isFaint())
                    playerFaintSequence();
                else enemyAction();
            }
            break;
            case 1: { 
                if (depth == 0) {
                    switch(cursor) {
                        case 0: {
                            Message.alreadyOut(main.getPokemon(mainPokeOut)); 
                            showMessage(); 
                            update();   
                        }                                                           break;
                        default: {
                            Message.pickPokemon(main.getPokemon(cursor)); 
                            showMessage(); 
                        }                                                           break;
                    }
                }
                else if (depth == 1) {
                    swapPokemon(cursor);
                    depth = 0;
                    enemyAction();
                }
            } break;
            case 2: break;
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
                    case 2: break;
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
            case 4: break;
            default: currentMenu = 4; cursor = 0; menu = true; break;
        }
    }
    
    public static void enemyAction() {
        currentMenu = 4;
        int move = (int) (Math.random() * 4);
        Move toUse = enemy.getPokemon(enPokeOut).getMove(move);
        if (Math.random() * 100 < toUse.getAccuracy()) {
            if (toUse.getTarget() >= 0) 
                toUse.makeMove(main.getPokemon(mainPokeOut));
            
            else if (toUse.getTarget() < 0)
                toUse.makeMove(enemy.getPokemon(enPokeOut));
            
            Message.makeMove(enemy.getPokemon(enPokeOut), toUse);
            showMessage();
            timeDelay();
            fightAnimation(toUse.getTarget(), enemy);
            StdDraw.show(5);
            timeDelay();
            Message.customSet(decide);
            update();
        }
    } 
    
    private static void runText() {
        Message.run();
    }

    private static void bagMenu() {
        return;
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
                    StdDraw.filledCircle(PPX + i, PPY + i, 0.04);
                else if (initiator == enemy)
                    StdDraw.filledCircle(EPX - i, EPY - i, 0.04);
                StdDraw.show(5); 
            }
            update();
            StdDraw.setPenColor(StdDraw.BLUE);
            for (double i = 1; i > 0; i -= 0.04) {
                update();
                if (initiator == main)
                    StdDraw.filledCircle(PPX + i, PPY + i, 0.04);
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
                    StdDraw.filledCircle(PPX + i, PPY + i, 0.04);
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
        HappyBirthdayAlana.main(args);
    }
}