package maze.entity;

import maze.entity.abilities.Ability;

/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.entity
 * Player.java
 * IT220-Final -
 */
public class Player extends Living implements Combat {

    public static final String ICON = "\uD83D\uDF9C";

    public Player(String name, String icon) {
        super(name, icon, 1, 20, 5);

        // Add Abilities
    }

    @Override
    public boolean basicAttack(Living target) {
        return (target.takeDamage(getDamage()));
    }

    @Override
    public boolean useAbility(Living target, Ability ability) {
        ability.use(target);
        return true;
    }
}
