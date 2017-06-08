package maze.map;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.map
 * Map.java
 * IT220-Final - description
 */

import maze.entity.Player;

import java.util.Random;

public class Map {

    private int width;
    private int height;
    private MapTile[][] map;
    private Random random = new Random();
    private Player player = new Player("Player Name", "\uD83D\uDF9C");

    public Map(int width, int height) {
        this.width = width;
        this.height = height;

        map = new MapTile[width][height];
        generateTiles();
        map[5][3].setMember(player);
    }

    public String toString() {
        StringBuilder drawnMap = new StringBuilder();
        // For each row of tile
        for (int h = 0; h < height; h++) {
            // for each row of the tile
            for (int row = 0; row < CorridorType.INTERSECTION.getTileHeight(); row++) {
                // for each column of the current row
                for (int w = 0; w < width; w++) {
                    // get the current tile
                    MapTile tile = map[h][w];
                    // get the current layout
                    String layout = tile.getCorridor().getLayout()[row];
                    // If it's the center row and the tile has an entity on it, replace the center with the entity icon
                    if (row == tile.getCorridor().getTileVerticalMid() && tile.getMember() != null) {
                        drawnMap.append(layout.substring(0, 2))
                                .append(tile.getMember().getIcon())
                                .append(layout.substring(3));
                    } else { // draw the layout normally
                        drawnMap.append(layout);
                    }
                }
                drawnMap.append('\n');
            }
        }
        return drawnMap.toString();
    }

    private void generateTiles() {
        CorridorType[] corridors = CorridorType.values();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                map[h][w] = new MapTile(corridors[random.nextInt(corridors.length)], null);
            }
        }
    }
}
