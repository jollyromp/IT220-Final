package maze.entity;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.entity
 * Enemy.java
 * IT220-Final - An enemy object
 */

import maze.entity.abilities.Ability;

import java.util.Random;

public class Enemy extends Living {

    private static Random random = new Random();
    private static final String ICON = "\uD83D\uDFBA";

    public Enemy(String name, int level, int health, int damage) {
        super(name, ICON, level, health, damage);
        abilities.add(Ability.BASIC_ABILITY);
    }
}
