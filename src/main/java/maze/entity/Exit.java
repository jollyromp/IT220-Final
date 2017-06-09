package maze.entity;/*
 * Caleb Snoozy, Jacob Tran
 * 6/9/2017
 * maze.entity
 * Exit.java
 * IT220-Final - description
 */

public class Exit extends Entity{

    private String name;
    private String icon;

    public Exit() {
        name = "exit";
        icon = "\uD83D\uDF91";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIcon() {
        return icon;
    }
}
