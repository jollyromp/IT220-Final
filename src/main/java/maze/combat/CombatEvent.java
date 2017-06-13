package maze.combat;

import maze.entity.Enemy;
import maze.entity.Player;
import maze.io.ConsoleIO;

/*
 * Caleb Snoozy, Jacob Tran
 * 6/12/2017
 * maze.combat
 * CombatEvent.java
 * IT220-Final - The combat event
 */
public class CombatEvent {
    Player player;
    Enemy enemy;

    public CombatEvent(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public boolean combatLoop() {
        if (player.getHealth() <= 0) {
            return false;
        }
        if (enemy.getHealth() <= 0) {
            return true;
        }



        return combatLoop();
    }
}
