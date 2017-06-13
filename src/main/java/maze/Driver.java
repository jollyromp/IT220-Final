package maze;
/*
 * Caleb Snoozy, Jacob Tran
 * 5/31/2017
 * maze
 * Driver.java
 * IT220-Final - Load files and start the maze game
 */


import maze.entity.abilities.Ability;
import maze.game.Game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    private static List<Ability> abilities = new ArrayList<>();

    private static Game game;

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File(Driver.class.getResource("/abilities.txt").getFile()));
            while (scanner.hasNext()) {
                String[] segments = scanner.nextLine().split("[,]");
                abilities.add(new Ability(segments[0], segments[3], Integer.parseInt(segments[1]), Integer.parseInt(segments[2])));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // This is where we should load files and set generation settings
        game = new Game();
    }

    public static List<Ability> getAbilities() {
        return abilities;
    }
}
