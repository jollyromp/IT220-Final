package maze.entity;

/*
 * Name: Caleb Snoozy
 * Date: 6/7/2017
 * *.java
 * description
 */
public class Item extends Entity {

    private String name;
    private String icon = "\uD83D\uDFA7";
    private String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }
}
