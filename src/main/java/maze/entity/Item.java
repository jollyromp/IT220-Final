package maze.entity;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.entity
 * Item.java
 * IT220-Final - An item object
 */

/**
 * the item object, contains name icon and description
 */
public class Item extends Entity {

    private String name;
    private String icon = "\uD83D\uDFA7";
    private String description;

    /**
     * @param name - name of the item
     * @param description - description of the item
     */
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @return - returns the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return - the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @return - description
     */
    public String getDescription() {
        return description;
    }
}
