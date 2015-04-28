public class PokeDraw {
    private static final String BACKGROUND = "images\\pbackground.png";

    private static final double BIGY = 0.0;
    private static final double[] XS = {-0.88, .2};
    private static final double[] YS = { 0.61, 0.22, -0.17, -0.56, -0.95};

    private static final double[] MESSX = {-.97, -.97,  0.05, 0.05};
    private static final double[] MESSY = {-.95,-.7, -.7, -.95};
    private static final double BORDER = 0.004;

    private static int[] locator;
    
    public static void draw(Player main, int cursor) {
        StdDraw.picture(0, 0, BACKGROUND, 4, 4);
        
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledPolygon(MESSX, MESSY);
        StdDraw.setPenRadius(BORDER);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.polygon(MESSX, MESSY);
        StdDraw.setPenRadius();    

        StdDraw.textLeft(-.95, -.8, Message.getMessage());

        PokeBlock[] toDraw = new PokeBlock[6];
        int teamSize = main.getTeamSize();
        Pokemon[] team = new Pokemon[teamSize];
        locator = new int[teamSize];

        team[0] = main.getPokemon(Display.getPokeOut());
        locator[0] = Display.getPokeOut();
        
        for (int i = 1; i <= Display.getPokeOut(); i++) {
            team[i] = main.getPokemon(i - 1);
            locator[i] = i - 1;
        }

        for (int i = Display.getPokeOut() + 1; i < teamSize; i++) {
            team[i] = main.getPokemon(i);
            locator[i] = i;
        }

        for (int i = 0; i < teamSize; i++)
            toDraw[i] = new PokeBlock(team[i]);

        for (int i = teamSize; i < 6; i++)
            toDraw[i] = new PokeBlock();

        if (cursor == 0)  toDraw[0].draw(true,  true, XS[0], BIGY);
        else              toDraw[0].draw(true, false, XS[0], BIGY);

        for (int i = 1; i <= 5; i++) {
            if (i == cursor) toDraw[i].draw(false,  true, XS[1], YS[i - 1]);
            else             toDraw[i].draw(false, false, XS[1], YS[i - 1]);
        }
    }

    public static int getLocator(int toFind) {
        return locator[toFind];
    }
    
    public static void main(String[] args) {
    }
}