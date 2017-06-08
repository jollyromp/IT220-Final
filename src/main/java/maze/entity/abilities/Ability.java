package maze.entity.abilities;

import maze.entity.Living;

import java.util.function.Consumer;

/*
 * Name: Caleb Snoozy
 * Date: 6/7/2017
 * *.java
 * description
 */
public class Ability {

    private String name;
    private String description;
    private Consumer<Living> logic;

    public Ability(String name, String description, Consumer<Living> logic) {
        this.name = name;
        this.description = description;
        this.logic = logic;
    }

    public void use(Living target) {
        logic.accept(target);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
