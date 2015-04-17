public class Display {
    private static String background;
    private static boolean battle;
    private static boolean world;
    
    public static void setBackground(String s) {
        background = s;
    }
    
    public static String getBackground() {
        return background;
    }
    
    public static void update() {
        StdDraw.picture(0, 0, background);
    }
    
    public static void fadeOut() {
        for (double i = 0; i < 2; i += 0.05) {
            //Display.update();
            StdDraw.filledCircle(0, 0, i);
            StdDraw.show(10);
        }
    }
    
    public static void openSequence(Player enemy, Player main) {
        battle = true;
        Message.challenge(enemy, main);
    }
    
    public static void setBounds() {
        StdDraw.setXscale(-1, 1);
        StdDraw.setYscale(-1, 1);
    }
    
    public static void main(String[] args) {
        setBounds();
        fadeOut();
    }
}