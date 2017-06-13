package maze.entity.abilities;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.entity.abilities
 * Ability.java
 * IT220-Final - An ability object
 */

public class Ability {

    private String name;
    private String description;
    private int damageMin;
    private int damageMax;

    public static final Ability BASIC_ABILITY = new Ability("Strike", "A basic punch.", 3, 5);

    public Ability(String name, String description, int damageMin, int damageMax) {
        this.name = name;
        this.description = description;
        this.damageMin = damageMin;
        this.damageMax = damageMax;
    }

    public int getDamageMin() {
        return damageMin;
    }

    public int getDamageMax() {
        return damageMax;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return name + " (" + damageMin + "-" + damageMax + "): " + description;
    }

}
