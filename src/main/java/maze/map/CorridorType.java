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
            "_| |_",
            "_   _",
            " | | "
    ),
    DOWN_RIGHT(
            "  ___",
            " |  _",
            " | | "
    ),
    UP_LEFT(
            "_| | ",
            "___| ",
            "     "
    ),
    UP_RIGHT(
            " | |_",
            " |___",
            "     "
    ),
    DOWN_LEFT(
            "___  ",
            "_  | ",
            " | | "
    ),
    VERTICAL_PIPE(
            " | | ",
            " | | ",
            " | | "
    ),
    HORIZONTAL_PIPE(
            "_____",
            "_____",
            "     "
    ),
    UP_DOWN_LEFT(
            "_| | ",
            "_  | ",
            " | | "
    ),
    UP_DOWN_RIGHT(
            " | |_",
            " |  _",
            " | | "
    ),
    UP_LEFT_RIGHT(
            "_| |_",
            "_____",
            "     "
    ),
    DOWN_LEFT_RIGHT(
            "_____",
            "_   _",
            " | | "
    );

    private String[] corridor;

    CorridorType(String... corridor) {
        this.corridor = corridor;
    }

    public String[] getCorridor(){
        return corridor;
    }
}
