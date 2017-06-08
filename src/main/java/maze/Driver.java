package maze;
/*
 * Caleb Snoozy, Jacob Tran
 * 5/31/2017
 * maze
 * Driver.java
 * IT220-Final - Load files and start the maze game
 */


import maze.game.Game;

public class Driver {

    private static Game game;

    public static void main(String[] args) {

        // This is where we should load files and set generation settings
        game = new Game();
    }
}
