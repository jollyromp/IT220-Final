package maze.entity;

import maze.entity.abilities.Ability;

import java.util.Random;

/*
 * Name: Caleb Snoozy
 * Date: 6/7/2017
 * *.java
 * description
 */
public class Enemy extends Living implements Combat {

    private static Random random = new Random();
    private static String[] icons = {"\uD83D\uDFA7", "\uD83D\uDFAE", "\uD83D\uDFB4", "\uD83D\uDFBA", "\uD83D\uDFBF"};

    public Enemy(String name, String icon, int level, int health, int damage) {
        super(name, icon, level, health, damage);
    }

    @Override
    public boolean basicAttack(Living target) {
        return target.takeDamage(getDamage());
    }

    @Override
    public boolean useAbility(Living target, Ability ability) {
        ability.use(target);
        return true;
    }

    public static String getRandomIcon() {
        return icons[random.nextInt(icons.length)];
    }
}
