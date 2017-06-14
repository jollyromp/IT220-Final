package maze.entity.abilities;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.entity.abilities
 * Ability.java
 * IT220-Final - An ability object
 */

/**
 * Ability class that contains info about the ability and its damage
 */
public class Ability {

    private String name;
    private String description;
    private int damageMin;
    private int damageMax;

    public static final Ability BASIC_ABILITY = new Ability("Strike", "A basic punch.", 3, 5);

    /**
     * Constructor
     * @param name - name of the ability
     * @param description - description of the ability
     * @param damageMin - minimum base damage ability can deal
     * @param damageMax - maximum base damage abillity can deal
     */
    public Ability(String name, String description, int damageMin, int damageMax) {
        this.name = name;
        this.description = description;
        this.damageMin = damageMin;
        this.damageMax = damageMax;
    }

    /**
     * @return minimum damage
     */
    public int getDamageMin() {
        return damageMin;
    }

    /**
     * @return maximum damage
     */
    public int getDamageMax() {
        return damageMax;
    }

    /**
     * @return name of the ability
     */
    public String getName() {
        return name;
    }

    /**
     * @return description of the ability
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return name, minimum damage, maximum damage and description in a String
     */
    public String toString() {
        return name + " (" + damageMin + "-" + damageMax + "): " + description;
    }

}
