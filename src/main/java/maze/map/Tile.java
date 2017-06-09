package maze.map;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.map
 * Tile.java
 * IT220-Final - description
 */

import maze.entity.Entity;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Tile {

    // Tile dimensions, need to be odd
    private static final int tileWidth = 3;
    private static final int tileHeight = 3;
    // Used to replace tiles center with the entity icon, should be half of the height + floored
    private static final int tileVerticalMid = Math.floorDiv(tileHeight, 2);
    private static final int tileHorizontalMid = Math.floorDiv(tileWidth, 2);

    // Possible corridors, can add dead ends but would require smarter generation logic
    private static final List<Corridor> possibleCorridors = Arrays.asList(
            new Corridor(true), // INTERSECTION
            new Corridor(true, false), // VERTICAL_PIPE
            new Corridor(false, true), // HORIZONTAL_PIPE
            new Corridor(true, false, true, false), // UP_LEFT
            new Corridor(true, false, false, true), // UP_RIGHT
            new Corridor(true, false, true, true), // UP_LEFT_RIGHT
            new Corridor(false, true, true, false), // DOWN_LEFT
            new Corridor(false, true, false, true), // DOWN_RIGHT
            new Corridor(false, true, true, true), // DOWN_LEFT_RIGHT
            new Corridor(true, true, true, false), // UP_DOWN_LEFT
            new Corridor(true, true, false, true) // UP_DOWN_RIGHT
//            new Corridor(true, false, false, false), // DEAD_END
//            new Corridor(false, true, false, false), // DEAD_END
//            new Corridor(false, false, true, false), // DEAD_END
//            new Corridor(false, false, false, true) // DEAD_END
    );
    private static final Corridor ALL_WALLS = new Corridor(false);

    private Corridor corridor;
    private boolean hidden = true;

    private Entity member;

    public Tile(Corridor corridor) {
        this(corridor, null);
    }

    public Tile(Corridor corridor, Entity member) {
        this.corridor = corridor;
        this.member = member;
    }

    public String[] getLayout() {
        return corridor.layout;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public Entity getMember() {
        return member;
    }

    public void setMember(Entity entity) {
        this.member = entity;
    }

    public static int getTileWidth() {
        return tileWidth;
    }

    public static int getTileHeight() {
        return tileHeight;
    }

    public static int getTileVerticalMid() {
        return tileVerticalMid;
    }

    public static int getTileHorizontalMid() {
        return tileHorizontalMid;
    }

    public static List<Corridor> getPossibleCorridors() {
        return possibleCorridors;
    }

    public static Corridor getAllWalls() {
        return ALL_WALLS;
    }

    public boolean canMoveUp() {
        return corridor.up;
    }

    public boolean canMoveDown() {
        return corridor.down;
    }

    public boolean canMoveLeft() {
        return corridor.left;
    }

    public boolean canMoveRight() {
        return corridor.right;
    }

    public static class Corridor {
        private final char WALL = '▓';
        private final char FLOOR = '░';

        public static final Predicate<Corridor> IS_UPPER_LEFT_CORNER = corridor1 -> corridor1.down && corridor1.right && !corridor1.up && !corridor1.left;
        public static final Predicate<Corridor> IS_UPPER_RIGHT_CORNER = corridor1 -> corridor1.down && corridor1.left && !corridor1.up && !corridor1.right;
        public static final Predicate<Corridor> IS_LOWER_LEFT_CORNER = corridor1 -> corridor1.up && corridor1.right && !corridor1.down && !corridor1.left;
        public static final Predicate<Corridor> IS_LOWER_RIGHT_CORNER = corridor1 -> corridor1.up && corridor1.left && !corridor1.down && !corridor1.right;
        public static final Predicate<Corridor> IS_UPPER_WALL = corridor1 -> !corridor1.up;
        public static final Predicate<Corridor> IS_LOWER_WALL = corridor1 -> !corridor1.down;
        public static final Predicate<Corridor> IS_LEFT_WALL = corridor1 -> !corridor1.left;
        public static final Predicate<Corridor> IS_RIGHT_WALL = corridor1 -> !corridor1.right;


        private String[] layout = new String[tileHeight];
        private boolean up, down, left, right;

        public Corridor(boolean all) {
            this(all, all);
        }

        public Corridor(boolean vertical, boolean horizontal) {
            this(vertical, vertical, horizontal, horizontal);
        }

        public Corridor(boolean up, boolean down, boolean left, boolean right) {
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;

            for (int i = 0; i < tileHeight; i++) {
                layout[i] = "";
                for (int j = 0; j < tileWidth; j++) {
                    if (this.up && i < tileVerticalMid && j == tileHorizontalMid) // Up corridor
                        layout[i] += FLOOR;
                    else if (this.down && i > tileVerticalMid && j == tileHorizontalMid) // Down corridor
                        layout[i] += FLOOR;
                    else if (this.left && i == tileVerticalMid && j < tileHorizontalMid)
                        layout[i] += FLOOR;
                    else if (this.right && i == tileVerticalMid && j > tileHorizontalMid)
                        layout[i] += FLOOR;
                    else if (i == tileVerticalMid && j == tileHorizontalMid) // Center tile
                        layout[i] += FLOOR;
                    else
                        layout[i] += WALL;
                }
            }

        }
    }
}
