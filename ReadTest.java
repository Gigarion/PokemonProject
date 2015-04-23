import java.io.*;
import java.util.*;
public class ReadTest {
	public static void main(String[] args) throws IOException {
		File toRead = new File("read.txt");
		Scanner read = new Scanner(toRead);
		while(read.hasNextLine())
			System.out.println(read.nextLine());
	}
}