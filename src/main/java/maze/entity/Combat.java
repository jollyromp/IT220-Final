package maze.entity;

import maze.entity.abilities.Ability;

/*
 * Name: Caleb Snoozy
 * Date: 6/7/2017
 * *.java
 * description
 */
public interface Combat {
    boolean basicAttack(Living target);

    boolean useAbility(Living target, Ability ability);

}
