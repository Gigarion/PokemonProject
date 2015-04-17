import java.util.*;
import java.io.*;
public class Player {
    private String name;
    private String image;
    private int teamSize;
    private Pokemon[] team;
    
    public Player(String n) {
        name = name;
        teamSize = 0;
    }
    
    public Player(Scanner s) throws IOException {
        name = s.next();
        image = name + ".jpg";
        teamSize = Integer.parseInt(s.next());
        team = new Pokemon[teamSize];
        for (int i = 0; i < teamSize; i++)
            team[i] = Pokemon.fromFile(s);
    }
    
    public String getName() {
        return name;
    }
}