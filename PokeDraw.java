public class PokeDraw {
    private static final String BACKGROUND = "pbackground.png";

    private static final double BIGY = 0.0;
    private static final double[] XS = {-0.88, .2};
    private static final double[] YS = { 0.61, 0.22, -0.17, -0.56, -0.95};

    private static final double[] MESSX = {-.97, -.97,  0, 0};
    private static final double[] MESSY = {-.95,-.8, -.8, -.95};
    private static final double BORDER = 0.004;
    
    public static void draw(Player main, int cursor) {
        StdDraw.picture(0, 0, BACKGROUND, 4, 4);
        
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledPolygon(MESSX, MESSY);
        StdDraw.setPenRadius(BORDER);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.polygon(MESSX, MESSY);
        StdDraw.setPenRadius();

        StdDraw.textLeft(-.95, -.875, Message.getMessage());

        PokeBlock[] toDraw = new PokeBlock[6];
        Pokemon[] team = main.getTeam();
        int teamSize = main.getTeamSize();
        for (int i = 0; i < teamSize; i++) {
            toDraw[i] = new PokeBlock(team[i]);
        }
        for (int i = teamSize; i < 6; i++) {
            toDraw[i] = new PokeBlock();
        }

        int numOne = Display.getPokeOut();
        if (numOne == cursor)   toDraw[numOne].draw(true, true, XS[0], BIGY);
        else                    toDraw[numOne].draw(true, false, XS[0], BIGY);

        for (int i = 0; i < 5; i++) {
            if (i >= numOne && i == cursor) toDraw[i + 1].draw(false, true, XS[1], YS[i]);
            else if (i >= numOne)           toDraw[i + 1].draw(false, false, XS[1], YS[i]);
            else if (i == cursor)           toDraw[i].draw(false, true, XS[1], YS[i]);
            else                            toDraw[i].draw(false, false, XS[1], YS[i]);
        }
    }
    
    public static void main(String[] args) {
    }
}