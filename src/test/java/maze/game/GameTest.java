package maze.game;/*
 * Caleb Snoozy
 * 6/6/2017
 * maze.game
 * GameTest.java
 * IT220-Final - description
 */

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class GameTest {

    @Test
    public void predicateNotEmptyShouldBeTrue() {
        String test = "This is not empty";
        Assert.assertEquals(true, Game.NOT_EMPTY.test(test));
    }

    @Test
    public void predicateNotEmptyShouldBeFalse() {
        String test = "";
        Assert.assertEquals(false, Game.NOT_EMPTY.test(test));
    }

    @Test
    public void predicateIsYesOrNoShouldBeTrue() {
        for (String test : Arrays.asList("y", "yes", "n", "no", "YES", "No")) {
            Assert.assertEquals(true, Game.YES_OR_NO.test(test));
        }
    }

    @Test
    public void predicateIsYesOrNoShouldBeFalse() {
        for (String test : Arrays.asList("ye", "s", "not", "si")) {
            Assert.assertEquals(false, Game.YES_OR_NO.test(test));
        }
    }
}
