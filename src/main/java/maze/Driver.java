package maze;
/*
 * Caleb Snoozy, Jacob Tran
 * 5/31/2017
 * maze
 * Driver.java
 * IT220-Final - Load files and start the maze game
 */


import maze.map.Map;

public class Driver {
    public static void main(String[] args) {

        // This is where we should load files and set generation settings
        Map gameMap = new Map(10, 10);
        System.out.println(gameMap);
    }
}
