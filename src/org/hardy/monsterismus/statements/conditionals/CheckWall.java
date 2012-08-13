package org.hardy.monsterismus.statements.conditionals;

import java.awt.Point;

import org.hardy.monsterismus.blocks.Block;
import org.hardy.monsterismus.screens.Board;

public class CheckWall {
  public static boolean checkY(int dir) {
    Block[][] blocks = Board.getInstance().getBlocks();
    Point pos = Board.getInstance().getPlayer().getPoint();

    int y = pos.y + dir;
    return y < 0 || y >= blocks[pos.x].length;
  }

  public static boolean checkX(int dir) {
    Block[][] blocks = Board.getInstance().getBlocks();
    Point pos = Board.getInstance().getPlayer().getPoint();

    int x = pos.x + dir;
    return x < 0 || x >= blocks.length;
  }
}
