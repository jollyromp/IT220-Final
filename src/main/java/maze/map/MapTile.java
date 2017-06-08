package maze.map;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/7/2017
 * maze.map
 * MapTile.java
 * IT220-Final - description
 */

import maze.entity.Entity;

public class MapTile {

    private CorridorType corridor;
    private Entity member;

    public MapTile(CorridorType corridor, Entity member) {
        this.corridor = corridor;
        this.member = member;
    }

    public CorridorType getCorridor() {
        return corridor;
    }

    public Entity getMember() {
        return member;
    }
}
