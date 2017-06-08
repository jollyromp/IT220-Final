package maze.entity;

/*
 * Name: Caleb Snoozy
 * Date: 6/7/2017
 * *.java
 * description
 */
public class Item extends Entity {

    private String name;
    private String icon;
    private String description;

    public Item(String name, String icon, String description) {
        this.name = name;
        this.icon = icon;
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
