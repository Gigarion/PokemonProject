import java.io.*;
public class Testing {
    public static void print() {
        System.out.println("yoloswag");
    }

    public static void main(String[] args) throws IOException {
    	File file = new File("files\\test.txt");
    	file.createNewFile();
    	String toSave = file.getCanonicalPath();
    	System.out.println(toSave);
    }
}