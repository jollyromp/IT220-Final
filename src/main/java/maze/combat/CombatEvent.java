package maze.combat;

import maze.entity.Enemy;
import maze.entity.Player;
import maze.entity.abilities.Ability;
import maze.io.ConsoleIO;

import java.io.Console;
import java.util.Random;

/*
 * Caleb Snoozy, Jacob Tran
 * 6/12/2017
 * maze.combat
 * CombatEvent.java
 * IT220-Final - The combat event
 */
public class CombatEvent {
    private Player player;
    private Enemy enemy;
    private Random random = new Random();

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

        ConsoleIO.println("The fight continues...");
        ConsoleIO.println();

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
                int damageTakenByEnemy = random.nextInt(abilityUsed.getDamageMax() + player.getDamage()
                        - abilityUsed.getDamageMin() + 1) + abilityUsed.getDamageMin();
                enemy.takeDamage(damageTakenByEnemy);
                ConsoleIO.println("The Enemy was struck by " + abilityUsed.getName() + " for " + damageTakenByEnemy +
                        " points of damage!");
            } else {
                ConsoleIO.println("That's not an ability you have!");
            }
            ConsoleIO.println();
        }while(abilityUsed == null);

        return combatLoop();
    }
}
