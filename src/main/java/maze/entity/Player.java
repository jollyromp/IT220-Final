package maze.entity;

import maze.entity.abilities.Ability;

/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.entity
 * Player.java
 * IT220-Final -
 */
public class Player extends Living {

    private static final String ICON = "\uD83D\uDF9C";

    public Player(String name) {
        super(name, ICON, 1, 20, 1);
        abilities.add(Ability.BASIC_ABILITY);
    }

    public void levelUp() {
        level++;
        health += 5;
        damage++;
    }
}
