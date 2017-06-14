package maze.entity;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.entity
 * Entity.java
 * IT220-Final - A Living entity
 */

import maze.entity.abilities.Ability;

import java.util.ArrayList;
import java.util.List;

/**
 * The living entity, contains important imformation for players and enemies
 */
public class Living extends Entity {

    private String name, icon;
    protected int level, health, damage, x, y;

    protected List<Ability> abilities = new ArrayList<>();

    /**
     *
     * @param name - Name of living entity
     * @param icon - Icon of living entity
     * @param level - level of living entity
     * @param health - health of living entity
     * @param damage - damage modifier of living entity
     */
    public Living(String name, String icon, int level, int health, int damage) {
        this.name = name;
        this.icon = icon;
        this.level = level;
        this.health = health;
        this.damage = damage;
    }

    /**
     *
     * @return name of living
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return name of living
     */
    public String getIcon() {
        return icon;
    }

    /**
     *
     * @return level of living
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @return health of living
     */
    public int getHealth() {
        return health;
    }

    /**
     *
     * @return damage modifier of living
     */
    public int getDamage() {
        return damage;
    }

    /**
     *
     * @return X position of living
     */
    public int getX() {
        return x;
    }

    /**
     * changes x position
     * @param x - next x position
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @return y position of living
     */
    public int getY() {
        return y;
    }

    /**
     * changes y position
     * @param y - next y position
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @return List of abilities
     */
    public List<Ability> getAbilities() {
        return abilities;
    }

    /**
     *
     * @param search name of ability being searched
     * @return whether Living has the ability in abilities
     */
    public boolean hasAbility(String search) {
        for(Ability ability : abilities) {
            if (ability.getName().equalsIgnoreCase(search)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param search name of ability being searched
     * @return Ability that is searched
     */
    public Ability getAbility(String search) {
        for(Ability ability : abilities) {
            if (ability.getName().equalsIgnoreCase(search)) {
                return ability;
            }
        }
        return null;
    }

    /**
     *
     * @param ability new ability
     */
    public void addAbility(Ability ability) {
        if (!hasAbility(ability.getName())) {
            this.abilities.add(ability);
        }
    }

    /**
     *
     * @param damage damage taken
     * @return true if not dead
     */
    public boolean takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0)
            return false;
        return true;
    }

    /**
     * Movement enum
     */
    public enum Move{
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * enum of results possible from a move
     */
    public enum MoveResult {
        WALL,
        ENEMY,
        SUCCESS,
        EXIT,
        LOSE
    }
}
