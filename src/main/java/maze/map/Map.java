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

    private final String hiddenRow = "▒▒▒";

    private int width;
    private int height;
    private MapTile[][] map;
    private Random random = new Random();
    private Player player;
    private int playerX, playerY;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;

        map = new MapTile[width][height];
        generateTiles();
        playerX = 1;
        playerY = 1;
        map[1][1].setHidden(false);
    }

    public void setPlayer(Player player){
        this.player = player;
        map[1][1].setMember(player);
    }

    public String miniMap(){
        StringBuilder drawnMap = new StringBuilder();
        // For each row of tile
        for (int h = playerY == 0 ? 0 : playerY-1; h < (playerY == height ? height : playerY + 2); h++) {
            // for each row of the tile
            for (int row = 0; row < CorridorType.INTERSECTION.getTileHeight(); row++) {
                // for each column of the current row
                for (int w = playerX == 0 ? 0 : playerX-1; w < (playerX == width ? width : playerX + 2); w++) {
                    // get the current tile
                    MapTile tile = map[h][w];
                    // get the current layout
                    String layout = tile.getCorridor().getLayout()[row];
                    // If it's the center row and the tile has an entity on it, replace the center with the entity icon
                    if (tile.isHidden()) {
                        drawnMap.append(hiddenRow);
                    } else if (row == tile.getCorridor().getTileVerticalMid() && tile.getMember() != null) {
                        drawnMap.append(layout.substring(0, 1))
                                .append(tile.getMember().getIcon())
                                .append(layout.substring(2));
                    } else { // draw the layout normally
                        drawnMap.append(layout);
                    }
                }
                drawnMap.append('\n');
            }
        }
        return drawnMap.toString();
    }

    public boolean movePlayer(int x, int y){
        map[playerY][playerX].setMember(null);
        playerY += y;
        playerX += x;
        map[playerY][playerX].setMember(player);
        map[playerY][playerX].setHidden(false);

        return true;
    }

    /**
     * Draw the exposed map
     */
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
                    if (tile.isHidden()) {
                        drawnMap.append(hiddenRow);
                    } else if (row == tile.getCorridor().getTileVerticalMid() && tile.getMember() != null) {
                        drawnMap.append(layout.substring(0, 1))
                                .append(tile.getMember().getIcon())
                                .append(layout.substring(2));
                    } else { // draw the layout normally
                        drawnMap.append(layout);
                    }
                }
                drawnMap.append('\n');
            }
        }
        return drawnMap.toString();
    }

    /**
     * Generate a map given the provided dimensions
     */
    private void generateTiles() {
        CorridorType[] corridors = CorridorType.values();
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                map[h][w] = new MapTile(corridors[random.nextInt(corridors.length)], null);
            }
        }
    }
}
