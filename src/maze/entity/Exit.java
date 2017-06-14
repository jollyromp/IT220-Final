package maze.entity;/*
 * Caleb Snoozy, Jacob Tran
 * 6/9/2017
 * maze.entity
 * Exit.java
 * IT220-Final - description
 */

/**
 * Exit entity, place where player exits to next floor
 */
public class Exit extends Entity {

    private String name;
    private String icon = "\uD83D\uDF91";

    /**
     * constructor
     */
    public Exit() {
        name = "exit";
    }

    /**
     *
     * @return name of exit
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return icon of exit
     */
    public String getIcon() {
        return icon;
    }
}
