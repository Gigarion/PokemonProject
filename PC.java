import java.io.*;
import java.util.*;

public class PC {
    private static final double[] BOXX  = { 0.10,  0.10,  0.97,  0.97};
    private static final double[] BOXY  = { 0.97, -0.97, -0.97,  0.97};
    private static final double[] INNERX = { 0.13,  0.13,  0.94,  0.94};
    private static final double[] INNERY = { 0.88, -0.88, -0.88,  0.88};
	private static final double STARTX = -0.9;
	private static final double STARTY = 0.4 

	Pokemon[] inBox;
	public PC(File box) throws IOException {
		Scanner readBox = new Scanner(box);
		int count = 0;
		while(readBox.hasNext()) {
			String temp = readBox.next();
		}
		readBox.close();
		Scanner readAgain = new Scanner(box);
		inBox = new Pokemon[count];
		for (int i = 0; i < count; i++) {
			File toAdd = new File(readAgain.next());
			inBox[i] = Pokemon.fromFile(toAdd);
		}
	}

	public void draw(int cursor) {

	}
}