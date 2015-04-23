import java.util.*;
import java.io.*;
public class Player {
    private String name;
    private String image;
    private int teamSize;
    private Pokemon[] team;
    private Item[] items;
    
    public Player(String n) {
        name = n;
        image = name;
        teamSize = 0;
    }
    
    public Player(Scanner s) throws IOException {
        name = s.next();
        image = name + ".png";
        teamSize = Integer.parseInt(s.next());
        team = new Pokemon[teamSize];
        
        String itemFile = s.next();
        File imp = new File(itemFile);
        Scanner itemRead = new Scanner(imp);
        int unique = Integer.parseInt(itemRead.nextLine());
        items = new Item[unique];
        for (int i = 0; i < unique; i++)
            items[i] = Item.fromFile(itemRead);
        itemRead.close();
        
        for (int i = 0; i < teamSize; i++)
            team[i] = Pokemon.fromFile(s);
    }
    
    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public Pokemon getPokemon(int number) {
        return team[number];
    }
    
    public Pokemon[] getTeam() {
        return team;
    }

    public Item[] getItems() {
        return items;
    }

    public void makeMove(int poke, int move, Pokemon target) {
        team[poke].getMove(move).makeMove(target);
    }

    public String getMove(int pokeNum, int moveNum) {
        return team[pokeNum].getMove(moveNum).getName();
    }

    public void addPokemon(Scanner s) throws IOException {
        if (teamSize < 6) {
            Pokemon[] newTeam = new Pokemon[++teamSize];
            newTeam[teamSize - 1] = Pokemon.fromFile(s);
        }

        else if (teamSize == 6) {
            File pc = new File("storage.txt");
            File output = new File("output.txt");
            output.createNewFile();
            PrintWriter putData = new PrintWriter(output);
            if (!pc.createNewFile()) {
                Scanner keepData = new Scanner(pc);
                while (keepData.hasNext())
                    putData.println(keepData.next());
                keepData.close();
            }
            while (s.hasNext())
                putData.println(s.next());
            putData.close();
        }

    }
}