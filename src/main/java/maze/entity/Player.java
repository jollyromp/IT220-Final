package maze.entity;

import maze.entity.abilities.Ability;

/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.entity
 * Player.java
 * IT220-Final -
 */

/**
 * The player object that extends Living, contains a levelup function
 * and adds the default ability
 */
public class Player extends Living {

    private static final String ICON = "\uD83D\uDF9C";

    /**
     * @param name - name of the player
     */
    public Player(String name) {
        super(name, ICON, 1, 20, 1);
        abilities.add(Ability.BASIC_ABILITY);
    }

    /**
     * increases the level damage and heals the player
     */
    public void levelUp() {
        level++;
        damage++;
        health+=damage*2;
    }
}
