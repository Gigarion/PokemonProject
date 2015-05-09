public class BackgroundMaker {
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
    
    public static void main(String[] args) {
        String toFix = args[0];
        int slash = 1 + toFix.lastIndexOf('\\');
        int period = toFix.lastIndexOf('.');
        String pieces = toFix.substring(slash, period);
        Picture source = new Picture(toFix);
        Picture back = new Picture(WIDTH, HEIGHT);
        
        for (int tx = 0; tx < WIDTH; tx++) {
            for (int ty = 0; ty < HEIGHT; ty++) {
                int sx = tx * source.width() / WIDTH;
                int sy = ty * source.height() / HEIGHT;
                back.set(tx, ty, source.get(sx, sy));
            }
        }

        back.save("images\\" + pieces + ".png");
        Display.setBounds();
    }
}
