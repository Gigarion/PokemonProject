public class PokeDraw {
    private static final String BACKGROUND = "pbackground.png";
    private static final double[] FPBX = {-.88, -.88, -.1, -.1};
    private static final double[] FPBY = {.68, .1, .1, .68};
    private static final double[] SPBX = {0, 0, .78, .78};
    private static final double[] TPBY = {0.97, .61, .61, 0.97};
    private static final double[] FOPBY = {0.58, .22, .22, 0.58};
    private static final double[] FIPBY = {0.19, -.17, -.17, 0.19};
    private static final double[] SIPBY = {-.2, -.56, -.56, -.2};
    private static final double[] LPBY = {-.59, -.95, -.95, -.59};
    
    public static void draw(int cursor) {
        StdDraw.picture(0, 0, BACKGROUND, 4, 4);
        
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledPolygon(FPBX, FPBY);
        StdDraw.filledPolygon(SPBX, LPBY);
        StdDraw.filledPolygon(SPBX, TPBY);
        StdDraw.filledPolygon(SPBX, FOPBY);
        StdDraw.filledPolygon(SPBX, FIPBY);
        StdDraw.filledPolygon(SPBX, SIPBY);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.004);
        StdDraw.polygon(FPBX, FPBY);
        StdDraw.polygon(SPBX, LPBY);
        StdDraw.polygon(SPBX, TPBY);
        StdDraw.polygon(SPBX, FOPBY);
        StdDraw.polygon(SPBX, FIPBY);
        StdDraw.polygon(SPBX, SIPBY);
        
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.004);
        StdDraw.polygon(FPBX, FPBY);
        
        Player main = Display.getPlayer();
        int teamSize = main.getTeamSize();
        Pokemon[] toShow = main.getTeam();
        int outNow = Display.getPokeOut();
        
        StdDraw.picture(-.8, .6, toShow[outNow].getName() + ".png");
        if (teamSize > 1) {
        }
        if (teamSize > 2) {
        }
        if (teamSize > 3) {
        }
        if (teamSize > 4) {
        }
        if (teamSize > 5) {
        }
    }
    
    public static void main(String[] args) {
        Display.setBounds();
        StdDraw.picture(0, 0, BACKGROUND, 4, 4);
        draw(0);
        StdDraw.show();
    }
}