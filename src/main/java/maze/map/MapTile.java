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
    private boolean hidden = true;

    // TODO(): This should be removed and the logic just stored in the map class
    private Entity member;

    public MapTile(CorridorType corridor, Entity member) {
        this.corridor = corridor;
        this.member = member;
    }

    public CorridorType getCorridor() {
        return corridor;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public Entity getMember() {
        return member;
    }

    public void setMember(Entity entity){
        this.member = entity;
    }
}
