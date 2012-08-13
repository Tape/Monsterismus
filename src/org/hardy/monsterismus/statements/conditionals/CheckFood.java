package org.hardy.monsterismus.statements.conditionals;

import java.awt.Point;

import org.hardy.monsterismus.blocks.Block;
import org.hardy.monsterismus.blocks.FoodBlock;
import org.hardy.monsterismus.screens.Board;

public class CheckFood {
  public static boolean checkY(int dir) {
    Block[][] blocks = Board.getInstance().getBlocks();
    Point pos = Board.getInstance().getPlayer().getPoint();

    for(int y = pos.y + dir; y >= 0 && y < blocks[pos.x].length; y += dir)
      if(blocks[pos.x][y] instanceof FoodBlock && ! blocks[pos.x][y].claimed())
        return true;
    return false;
  }

  public static boolean checkX(int dir) {
    Block[][] blocks = Board.getInstance().getBlocks();
    Point pos = Board.getInstance().getPlayer().getPoint();

    for(int x = pos.x + dir; x >= 0 && x < blocks.length; x += dir)
      if(blocks[x][pos.y] instanceof FoodBlock && ! blocks[x][pos.y].claimed())
        return true;
    return false;
  }
}