package maze.entity.abilities;

import maze.entity.Living;

import java.util.function.Consumer;

/*
 * Name: Caleb Snoozy
 * Date: 6/7/2017
 * *.java
 * description
 */
public class Ability {

    private String name;
    private String description;
    private int damageMin;
    private int damageMax;

    private static final String BASIC_ATTACK = "Strike";
    private static final String BASIC_DESCRIPTION = "A basic punch.";
    private static final int DAMAGE_MIN = 3;
    private static final int DAMAGE_MAX = 5;

    public static Ability BASIC_ABILITY = new Ability(BASIC_ATTACK, BASIC_DESCRIPTION, DAMAGE_MIN, DAMAGE_MAX);

    public Ability(String name, String description, int damageMin, int damageMax) {
        this.name = name;
        this.description = description;
        this.damageMin = damageMin;
        this.damageMax = damageMax;
    }

    public int getDamageMin() { return damageMin; }

    public int getDamageMax() { return damageMax; }

    public String getName() {
        return name;
    }

    public String getDescription() { return description; }

    public String toString() {
        return name + " (" + damageMin + "-" + damageMax + "): " + description;
    }

}
