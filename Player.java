import java.util.*;
import java.io.*;
public class Player {
    private String name;
    private String image;
    private int teamSize;
    private Pokemon[] team;
    private int numItems;
    private Item[] items;
    private Ball[] balls;
    private double x;
    private double y;
    
    public Player(String n) {
        name = n;
        image = name;
        teamSize = 0;
    }

    public Player(Pokemon p) throws IOException {
        name = "Wild " + p.getName();
        image = p.getImage();
        teamSize = 1;
        team = new Pokemon[1];
        team[0] = p;
    }
    
    public Player(Scanner s) throws IOException {
        name = s.next();
        image = "images\\" + name + ".png";
        
        x = Double.parseDouble(s.next());
        y = Double.parseDouble(s.next());
        
        teamSize = Integer.parseInt(s.next());
        team = new Pokemon[teamSize];
        
        String itemFile = s.next();
        File imp = new File(itemFile);
        Scanner itemRead = new Scanner(imp);
        numItems = Integer.parseInt(itemRead.nextLine());
        items = new Item[numItems];
        for (int i = 0; i < numItems; i++)
            items[i] = Item.fromFile(itemRead);
        int numBalls = Integer.parseInt(itemRead.next());
        balls = new Ball[numBalls];
        for (int i = 0; i < numBalls; i++)
            balls[i] = Ball.fromFile(itemRead);
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

    public int getNumItems() {
        return numItems;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public Pokemon getPokemon(int number) {
        return team[number];
    }

    public void heal() {
        for (int i = 0; i < teamSize; i++) {
            team[i].reset();
        }
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public void swapPokemon(int one, int two) {
        if (one == two) return;
        Pokemon holder = getPokemon(one);
        team[one] = team[two];
        team[two] = holder;
    }

    public void setPokemon(int which, Pokemon poke) {
        team[which] = poke;
    }
    
    public Pokemon[] getTeam() {
        return team;
    }

    public Item[] getItems() {
        return items;
    }

    public Ball[] getBalls() {
        return balls;
    }

    public String getMove(int pokeNum, int moveNum) {
        return team[pokeNum].getMove(moveNum).getName();
    }

    public void addPokemon(Pokemon toAdd) throws IOException {
        if (teamSize < 6) {
            Pokemon[] newTeam = new Pokemon[++teamSize];
            newTeam[teamSize - 1] = toAdd;
            for (int i = 0; i < teamSize - 1; i++) {
                newTeam[i] = team[i];
            }
            team = newTeam;
        }
        else if (teamSize == 6) {
            Message.moveToBox(toAdd);
            File pc = new File("players\\storage.txt");
            File output = new File("players\\output.txt");
            output.createNewFile();
            PrintWriter putData = new PrintWriter(output);
            if (!pc.createNewFile()) {
                Scanner keepData = new Scanner(pc);
                while (keepData.hasNext())
                    putData.println(keepData.next());
                keepData.close();
            }
            putData.println("Pokemon\\" + toAdd.getName() + ".txt");
            putData.close();
            Scanner transRead = new Scanner(output);
            PrintWriter transfer = new PrintWriter(pc);
            while (transRead.hasNext()) {
                transfer.println(transRead.next());
            }
            transRead.close();
            transfer.close();
            output.delete();
        }
    }
}