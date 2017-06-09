package maze.game;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/4/2017
 * maze.game
 * Game.java
 * Final - description
 */

import maze.entity.Player;
import maze.io.ConsoleIO;
import maze.map.Map;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class Game {
    private boolean running = true;
    private boolean quit = false;

    private Player player;
    private Map currentMap;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    // Requirements for text input
    public static final Predicate<String> NOT_EMPTY = s -> !s.isEmpty();
    public static final Predicate<String> YES_OR_NO = s ->
            s.equalsIgnoreCase("yes") ||
                    s.equalsIgnoreCase("y") ||
                    s.equalsIgnoreCase("no") ||
                    s.equalsIgnoreCase("n");


    public Game() {
        init();
        run();
        save();
        System.exit(0);
    }

    private void init() {
        player = new Player(ConsoleIO.nextLine("Please enter your name"), "\uD83D\uDF9C");
        currentMap = new Map(random.nextInt(5, 15), random.nextInt(5, 15));
        currentMap.setPlayer(player);
    }

    /**
     * The main game loop. Everything should be loaded before hand as this is a looping method
     */
    private void run() {
        while (running) {
            boolean doTimelyAction = true;
            // draw mini-map
            ConsoleIO.println(currentMap.miniMap());
            while (doTimelyAction) {
                switch (ConsoleIO.nextLine("What action do you want to take")) {
                    case "quit": // Quit, saves the game
                        quit = true;
                        break;
                    case "save": // Save the current game state
                        save();
                        break;
                    case "show map": // Draw full explored map
                        ConsoleIO.println(currentMap);
                        ConsoleIO.waitForEnter();
                        break;
                    case "move down":
                        if (currentMap.movePlayer(0, 1))
                            doTimelyAction = false;
                        else
                            ConsoleIO.println("You can't go that way");
                        break;
                    case "move up":
                        if (currentMap.movePlayer(0, -1))
                            doTimelyAction = false;
                        else
                            ConsoleIO.println("You can't go that way");
                        break;
                    case "move left":
                        if (currentMap.movePlayer(-1, 0))
                            doTimelyAction = false;
                        else
                            ConsoleIO.println("You can't go that way");
                        break;
                    case "move right":
                        if (currentMap.movePlayer(1, 0))
                            doTimelyAction = false;
                        else
                            ConsoleIO.println("You can't go that way");
                        break;
                    default:
                        ConsoleIO.println("Please enter a valid command");
                }
                if (quit) { // quit out before other actions
                    running = false;
                    break;
                }
                // Player checks
                if (currentMap.isOnExit()) {
                    ConsoleIO.println("Floor complete!");
                    currentMap = new Map(random.nextInt(5, 15), random.nextInt(5, 15));
                    currentMap.setPlayer(player);
                }

                // Enemy actions
            }
        }
    }

    /**
     * Save the games state
     */
    private void save() {
        ConsoleIO.println("Saving...", "Done");
    }
}
