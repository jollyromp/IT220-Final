package maze.combat;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/12/2017
 * maze.combat
 * CombatEvent.java
 * IT220-Final - The combat event
 */

import maze.entity.Enemy;
import maze.entity.Living;
import maze.entity.Player;
import maze.entity.abilities.Ability;
import maze.io.ConsoleIO;

import java.io.Console;
import java.util.Random;

/**
 * The combat event, which contains the main loop of combat and helper functions
 */
public class CombatEvent {
    private Player player;
    private Enemy enemy;
    private Random random = new Random();

    /**
     * creation of the combat event
     * @param player - the player information
     * @param enemy - the information of the enemy they are fighting
     */
    public CombatEvent(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    /**
     * a recursive combat loop that runs the combat system and input
     * @return false if player loses, true if player wins
     */
    public boolean combatLoop() {
        if (player.getHealth() <= 0) {
            return false;
        }
        if (enemy.getHealth() <= 0) {
            player.takeDamage(-player.getDamage()*2);
            return true;
        }

        ConsoleIO.println(player.getName() + ": " + player.getHealth());
        ConsoleIO.println(enemy.getName() + ": " + enemy.getHealth());
        ConsoleIO.println();

        ConsoleIO.println("Your Abilities:");
        for (Ability ability : player.getAbilities()) {
            ConsoleIO.println(ability);
        }
        ConsoleIO.println();

        Ability abilityUsed = null;
        do {
            String abilityInput = ConsoleIO.nextLine("Choose an ability");
            ConsoleIO.println();
            if (player.hasAbility(abilityInput)) {
                abilityUsed = player.getAbility(abilityInput);
                calculateDamage(player, enemy, abilityUsed);
            } else {
                ConsoleIO.println("That's not an ability you have!");
            }
        } while(abilityUsed == null);

        Ability enemyAbility = enemy.getAbilities().get(random.nextInt(enemy.getAbilities().size()));
        if (enemy.getHealth() > 0) {
            calculateDamage(enemy, player, enemyAbility);
        }

        return combatLoop();
    }

    private void calculateDamage(Living attacker, Living victim, Ability ability) {
        int damage = random.nextInt(ability.getDamageMax() + attacker.getDamage()
                - ability.getDamageMin() + 1) + ability.getDamageMin();
        ConsoleIO.println(victim.getName() + " was struck by " + ability.getName() + " for " + damage +
                " points of damage!");
        victim.takeDamage(damage);

        ConsoleIO.println();
    }
}
