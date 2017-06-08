package maze.game;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/4/2017
 * maze.game
 * Game.java
 * Final - description
 */

import java.util.function.Predicate;

public class Game {
    private boolean running = true;

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
        System.exit(0);
    }

    private void init() {

    }

    /**
     * The main game loop. Everything should be loaded before hand as this is a looping method
     */
    private void run() {
        while (running) {

        }
    }

    /**
     * Save the games state
     */
    private void save() {

    }
}
