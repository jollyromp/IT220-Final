package maze.entity;

import maze.entity.abilities.Ability;

/*
 * Name: Caleb Snoozy
 * Date: 6/7/2017
 * *.java
 * description
 */
public class Player extends Living implements Combat {

    public Player(String name, String icon) {
        super(name, icon);

        // Add Abilities
    }

    @Override
    public void basicAttack(Living target) {

    }

    @Override
    public void useAbility(Living target, Ability ability) {
        ability.use(target);
    }
}
