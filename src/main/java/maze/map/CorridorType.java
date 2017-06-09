package maze.map;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.map
 * CorridorType.java
 * IT220-Final - description
 */

public enum CorridorType {
    INTERSECTION(
            "▓░▓",
            "░░░",
            "▓░▓"
    ),
    DOWN_RIGHT(
            "▓▓▓",
            "▓░░",
            "▓░▓"
    ),
    UP_LEFT(
            "▓░▓",
            "░░▓",
            "▓▓▓"
    ),
    UP_RIGHT(
            "▓░▓",
            "▓░░",
            "▓▓▓"
    ),
    DOWN_LEFT(
            "▓▓▓",
            "░░▓",
            "▓░▓"
    ),
    VERTICAL_PIPE(
            "▓░▓",
            "▓░▓",
            "▓░▓"
    ),
    HORIZONTAL_PIPE(
            "▓▓▓",
            "░░░",
            "▓▓▓"
    ),
    UP_DOWN_LEFT(
            "▓░▓",
            "░░▓",
            "▓░▓"
    ),
    UP_DOWN_RIGHT(
            "▓░▓",
            "▓░░",
            "▓░▓"
    ),
    UP_LEFT_RIGHT(
            "▓░▓",
            "░░░",
            "▓▓▓"
    ),
    DOWN_LEFT_RIGHT(
            "▓▓▓",
            "░░░",
            "▓░▓"
    );

//    INTERSECTION(
//            "_| |_",
//            "_   _",
//            " | | "
//    ),
//    DOWN_RIGHT(
//            "  ___",
//            " |  _",
//            " | | "
//    ),
//    UP_LEFT(
//            "_| | ",
//            "___| ",
//            "     "
//    ),
//    UP_RIGHT(
//            " | |_",
//            " |___",
//            "     "
//    ),
//    DOWN_LEFT(
//            "___  ",
//            "_  | ",
//            " | | "
//    ),
//    VERTICAL_PIPE(
//            " | | ",
//            " | | ",
//            " | | "
//    ),
//    HORIZONTAL_PIPE(
//            "_____",
//            "_____",
//            "     "
//    ),
//    UP_DOWN_LEFT(
//            "_| | ",
//            "_  | ",
//            " | | "
//    ),
//    UP_DOWN_RIGHT(
//            " | |_",
//            " |  _",
//            " | | "
//    ),
//    UP_LEFT_RIGHT(
//            "_| |_",
//            "_____",
//            "     "
//    ),
//    DOWN_LEFT_RIGHT(
//            "_____",
//            "_   _",
//            " | | "
//    );

    private int tileWidth = 3;
    private int tileHeight = 3;
    // Used to replace tiles center with the entity icon, should be half of the height + floored
    private int tileVerticalMid = Math.floorDiv(tileHeight, 2);
    private int tileHorizontalMid = Math.floorDiv(tileWidth, 2);

    private String[] layout;

    CorridorType(String... layout) {
        this.layout = layout;
    }

    public String[] getLayout() {
        return layout;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTileVerticalMid() {
        return tileVerticalMid;
    }

    public int getTileHorizontalMid() {
        return tileHorizontalMid;
    }
}
