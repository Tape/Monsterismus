package org.hardy.monsterismus.statements.conditionals;

import java.awt.Point;

import org.hardy.monsterismus.blocks.Block;
import org.hardy.monsterismus.blocks.FoodBlock;
import org.hardy.monsterismus.screens.Board;

/**
 * Helper class to determine if there is food in a specific direction relative
 * to the player.
 *
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public class CheckFood {
  /**
   * Checks if there is food in the Y direction.
   * @param dir The direction, 1 is down and -1 is up.
   * @return true if food has been found, false if not.
   */
  public static boolean checkY(int dir) {
    Block[][] blocks = Board.getInstance().getBlocks();
    Point pos = Board.getInstance().getPlayer().getPoint();

    for(int y = pos.y + dir; y >= 0 && y < blocks[pos.x].length; y += dir)
      if(blocks[pos.x][y] instanceof FoodBlock && ! blocks[pos.x][y].claimed())
        return true;
    return false;
  }

  /**
   * Checks if there is food in the X direction.
   * @param dir The direction, 1 is right and -1 is left.
   * @return true if food has been found, false if not.
   */
  public static boolean checkX(int dir) {
    Block[][] blocks = Board.getInstance().getBlocks();
    Point pos = Board.getInstance().getPlayer().getPoint();

    for(int x = pos.x + dir; x >= 0 && x < blocks.length; x += dir)
      if(blocks[x][pos.y] instanceof FoodBlock && ! blocks[x][pos.y].claimed())
        return true;
    return false;
  }
}