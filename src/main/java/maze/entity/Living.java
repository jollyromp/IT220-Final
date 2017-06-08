package maze.entity;

import maze.entity.abilities.Ability;

import java.util.ArrayList;
import java.util.List;

/*
 * Name: Caleb Snoozy
 * Date: 6/7/2017
 * *.java
 * description
 */
public class Living extends Entity {

    private String name;
    private String icon;
    private List<Ability> abilities = new ArrayList<>();


    public Living(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIcon() {
        return icon;
    }
}
