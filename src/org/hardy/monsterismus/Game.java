package org.hardy.monsterismus;

import java.awt.event.MouseEvent;

import org.hardy.monsterismus.api.Screen;
import org.hardy.monsterismus.blocks.Block;
import org.hardy.monsterismus.screens.Board;
import org.hardy.monsterismus.screens.Editor;

public class Game {
  public static final int BOARD = 1;
  public static final int EDITOR = 2;

  public static final int BOARD_SIZE = 10;
  public static final int WIDTH = BOARD_SIZE * Block.SIZE;
  public static final int HEIGHT = BOARD_SIZE * Block.SIZE + 100;

  public static Screen screen;
  public static Board board;
  public static Editor editor;

  public static void switchScreen( int s ) {
    switch(s) {
      case EDITOR:
        screen = editor;
        break;
      default:
        screen = board;
        break;
    }

  }

  public static void nextLevel() {
    if(screen instanceof Board)
      board.nextLevel();
  }

  public static void previousLevel() {
    if(screen instanceof Board)
      board.previousLevel();
  }

  public static void handleMouseEvent(MouseEvent e) {
    if(screen instanceof Board)
      board.handleMouseEvent(e);
    else
      editor.handleMouseEvent(e);
  }

}
