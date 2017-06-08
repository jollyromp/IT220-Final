package maze.entity;

import maze.entity.abilities.Ability;

/*
 * Name: Caleb Snoozy
 * Date: 6/7/2017
 * *.java
 * description
 */
public interface Combat {
    void basicAttack(Living target);

    void useAbility(Living target, Ability ability);

}
