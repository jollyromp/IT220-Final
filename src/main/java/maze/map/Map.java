package maze.map;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.map
 * Map.java
 * IT220-Final - description
 */

import java.util.Random;

public class Map {

    private int width;
    private int height;
    private MapTile[][] map;
    private Random random = new Random();

    public Map(int width, int height) {
        this.width = width;
        this.height = height;

        map = new MapTile[width][height];
        generateTiles();
    }

    public String toString() {
        StringBuilder drawnMap = new StringBuilder();
        for (int h = 0; h < height; h++) {
            for (int row = 0; row < 3; row++) {
                for (int w = 0; w < width; w++) {
                    MapTile tile = map[h][w];
                    String layer = tile.getCorridor().getCorridor()[row];
                    if (tile.getMember() != null) {
                        drawnMap.append(layer.substring(0, 2))
                                .append(tile.getMember().getIcon())
                                .append(layer.substring(3));
                    } else {
                        drawnMap.append(layer);
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
