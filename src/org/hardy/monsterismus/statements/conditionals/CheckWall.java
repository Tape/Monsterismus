package org.hardy.monsterismus.statements.conditionals;

import org.hardy.monsterismus.blocks.Block;
import org.hardy.monsterismus.screens.Board;

import android.graphics.Point;

/**
 * Helper class to determine if there is a wall near the player.
 * 
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public class CheckWall {
    /**
     * Checks if there is a wall in the Y direction near the player.
     * 
     * @param dir
     *            The direction to check, 1 is below, -1 is above.
     * @return true if there is a wall, false if not.
     */
    public static boolean checkY(int dir) {
        Block[][] blocks = Board.getInstance().getBlocks();
        Point pos = Board.getInstance().getPlayer().getPoint();

        int y = pos.y + dir;
        return y < 0 || y >= blocks[pos.x].length;
    }

    /**
     * Checks if there is a wall in the X direction near the player.
     * 
     * @param dir
     *            The direction to check, 1 is right, -1 is left.
     * @return true if there is a wall, false if not.
     */
    public static boolean checkX(int dir) {
        Block[][] blocks = Board.getInstance().getBlocks();
        Point pos = Board.getInstance().getPlayer().getPoint();

        int x = pos.x + dir;
        return x < 0 || x >= blocks.length;
    }
}
