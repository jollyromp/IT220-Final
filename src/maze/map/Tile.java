package maze.map;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.map
 * Tile.java
 * IT220-Final - The representation of a maze tile
 */

import maze.entity.Entity;

/**
 * A tile, knows if there is a path up, down, left or right and creates a variable size tile from that information.
 */
public class Tile {

    // Characters to use to draw the tiles
    private final char WALL = '▓';
    private final char FLOOR = '░';

    // Tile dimensions, need to be odd
    public static final int tileWidth = 5;
    public static final int tileHeight = 3;
    public static final int miniWidth = 3;
    public static final int miniHeight = 3;
    // Used to replace tiles center with the entity icon, should be half of the height floored
    public static final int tileVerticalMid = Math.floorDiv(tileHeight, 2);
    public static final int tileHorizontalMid = Math.floorDiv(tileWidth, 2);
    public static final int miniVerticalMid = Math.floorDiv(miniHeight, 2);
    public static final int miniHorizontalMid = Math.floorDiv(miniWidth, 2);

    // Pathways
    private boolean up, down, left, right;
    // Has this tile been explored before
    private boolean hidden = true;
    // If not null, what entity is on this tile, used for graphic representation
    private Entity member;

    /**
     * @param up    is there an up corridor
     * @param down  is there an down corridor
     * @param left  is there an left corridor
     * @param right is there an right corridor
     */
    public Tile(boolean up, boolean down, boolean left, boolean right) {
        this(up, down, left, right, null);
    }

    /**
     * @param up     is there an up corridor
     * @param down   is there an down corridor
     * @param left   is there an left corridor
     * @param right  is there an right corridor
     * @param member the entity on the tile
     */
    public Tile(boolean up, boolean down, boolean left, boolean right, Entity member) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.member = member;
    }

    /**
     * @return Get the larger tile
     */
    public String[] getLayout() {
        return layout(tileWidth, tileHeight, tileVerticalMid, tileHorizontalMid);
    }

    /**
     * @return Get the small tile
     */
    public String[] getMiniLayout() {
        return layout(miniWidth, miniHeight, miniVerticalMid, miniHorizontalMid);
    }

    /**
     * Create the tile on demand based of the provided details
     *
     * @param width         width of the tile
     * @param height        height of the tile
     * @param verticalMid   center point of the vertical height
     * @param horizontalMid center point of the horizontal width
     * @return the tile array
     */
    private String[] layout(int width, int height, int verticalMid, int horizontalMid) {
        String[] layout = new String[height];

        for (int i = 0; i < height; i++) {
            layout[i] = "";
            for (int j = 0; j < width; j++) {
                if (up && i < verticalMid && j == horizontalMid) // Up corridor
                    layout[i] += FLOOR;
                else if (down && i > verticalMid && j == horizontalMid) // Down corridor
                    layout[i] += FLOOR;
                else if (left && i == verticalMid && j < horizontalMid) // Left corridor
                    layout[i] += FLOOR;
                else if (right && i == verticalMid && j > horizontalMid) // Right corridor
                    layout[i] += FLOOR;
                else if (i == verticalMid && j == horizontalMid) // Center tile
                    layout[i] += FLOOR;
                else
                    layout[i] += WALL;
            }
        }

        return layout;
    }

    /**
     * @return Is this tile hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @param hidden Set the tile hidden state
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * @return Get the entity of the tile
     */
    public Entity getMember() {
        return member;
    }

    /**
     * @param entity Set the entity of the tile
     */
    public void setMember(Entity entity) {
        this.member = entity;
    }

    /**
     * @return is there a member on the tile
     */
    public boolean hasMember() {
        return member != null;
    }

    /**
     * @return can the player move up
     */
    public boolean canMoveUp() {
        return up;
    }

    /**
     * @return can the player move down
     */
    public boolean canMoveDown() {
        return down;
    }

    /**
     * @return can the player move left
     */
    public boolean canMoveLeft() {
        return left;
    }

    /**
     * @return can the player move right
     */
    public boolean canMoveRight() {
        return right;
    }
}
