package maze.game;
/*
 * Caleb Snoozy
 * 6/4/2017
 * maze.game
 * Game.java
 * Final - description
 */

import maze.view.TextInput;
import maze.view.TextView;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

public class Game {
    private TextInput input;
    private TextView output;

    private final String MONSTERS_PATH = "";

    private boolean running = true;

    // Requirements for text input
    public static final Predicate<String> NOT_EMPTY = s -> !s.isEmpty();
    public static final Predicate<String> YES_OR_NO = s ->
            s.equalsIgnoreCase("yes") ||
                    s.equalsIgnoreCase("y") ||
                    s.equalsIgnoreCase("no") ||
                    s.equalsIgnoreCase("n");


    public Game(TextInput input, TextView output) {
        this.input = input;
        this.output = output;

        init();
        run();
        System.exit(0);
    }

    private void init() {
        output.setLocation("Loading...");
        // Load Monsters
        // Load Items
        // Load Tiles

        output.setTitle("That game you wanted to make when you were a kid but didn't know how.");

        input.resetPrompt();
    }

    /**
     * The main game loop. Everything should be loaded before hand as this is a looping method
     */
    private void run() {
        while (running) {
            output.addToLog("Please enter a command");
            input.setCurrentCommands("quit", "save", "reload");
            String command = input.getInput("[Quit, Save, Reload]", NOT_EMPTY).toLowerCase();
            if (command.equals("quit")) {
                output.addToLog("Do you want to save?");
                input.setCurrentCommands("yes", "no");
                command = input.getInput("yes or no", YES_OR_NO).toLowerCase();
                if (command.equals("yes") || command.equals("y")) {
                    save();
                }
                running = false;
            } else if (command.equals("save")) {
                save();
            } else if (command.equals("reload")) {

            } else{
                output.addToLog("Invalid Command");
            }
        }
    }

    /**
     * Save the games state
     */
    private void save() {
        output.addToLog("Saving...");
        System.out.println("Saving...");
        output.addToLog("Done");
        System.out.println("Done");
    }
}
