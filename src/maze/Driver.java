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
import java.util.function.Consumer;

/**
 * Driver class with main, loads files and starts the game
 */
public class Driver {
    private static List<Ability> abilities = new ArrayList<>();
    private static List<String> enemyNames = new ArrayList<>();

    public static void main(String[] args) {

        // Load abilities from file
        loadFile("./res/abilities.txt", s -> {
            String[] segments = s.split("[,]");
            abilities.add(new Ability(segments[0], segments[3], Integer.parseInt(segments[1]), Integer.parseInt(segments[2])));
        });

        // Load enemyNames from file
        loadFile("./res/enemyNames.txt", enemyNames::add);

        // Start the game
        new Game();
    }

    /**
     * @return The list of abilities
     */
    public static List<Ability> getAbilities() {
        return abilities;
    }

    /**
     * @return The list of enemy names
     */
    public static List<String> getEnemyNames() {
        return enemyNames;
    }

    /**
     * @param name Filename to load
     * @param line Operation to preform on each line of the file
     */
    private static void loadFile(String name, Consumer<String> line) {
        try (Scanner scanner = new Scanner(new File(name))) {
            while (scanner.hasNext()) {
                line.accept(scanner.nextLine());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
