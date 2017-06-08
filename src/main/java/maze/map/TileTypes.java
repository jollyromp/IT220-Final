package maze.map;

/*
 * Name: Caleb Snoozy
 * Date: 6/7/2017
 * *.java
 * description
 */
public class TileTypes {
    private CorridorType tile;

    private enum CorridorType {
        INTERSECTION("", "", "");

        private String[] view;

        CorridorType(String... view) {
            this.view = view;
        }
    }
}
