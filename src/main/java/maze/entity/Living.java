package maze.entity;

import maze.entity.abilities.Ability;

import java.util.ArrayList;
import java.util.List;

/*
 * Caleb Snoozy
 * 6/7/2017
 * maze.entity
 * Entity.java
 * IT220-Final - description
 */
public class Living extends Entity {

    private String name, icon;
    private int level, health, damage, x, y;

    private List<Ability> abilities = new ArrayList<>();

    public Living(String name, String icon, int level, int health, int damage) {
        this.name = name;
        this.icon = icon;
        this.level = level;
        this.health = health;
        this.damage = damage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public boolean takeDamage(int damage) {
        this.damage -= damage;
        if (this.damage <= 0)
            return false;
        return true;
    }

    public enum Move{
        UP, DOWN, LEFT, RIGHT
    }

    public enum MoveResult {
        WALL,
        ENEMY,
        SUCCESS
    }
}
