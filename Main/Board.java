import java.util.List;
import java.util.ArrayList;

import processing.core.PVector;
import processing.core.PGraphics;

import java.awt.event.MouseEvent;

/**
 *
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public class Board implements Screen {
  //Instance variables.
  private Block[][] _blocks;
  private int _size_x, _size_y;
  private PVector _dims;
  private static Board _board;
  private Player _player;
  private int level;
  private Editor _editor;
  private boolean _running = false;

  /**
   * Creates a new board instance
   *
   * @param $sx The width of the board
   * @param $sy The height of the board
   */
  public Board(final int $sx, final int $sy) {
    _size_x = $sx;
    _size_y = $sy;
    _dims = new PVector($sx * Block.SIZE, $sy * Block.SIZE);
    level = 0;

    this.generateBoard(level);
  }

  public static Board getInstance()
  {
    if(_board == null)
      throw new RuntimeException("Board has not been created yet");

    return _board;
  }
  
  public void setEditor(Editor $editor)
  {
    _editor = $editor;
  }
  
  public void setRunning(boolean $running)
  {
    _running = $running;
  }
  
  public void toggleRunning()
  {
    _running = !_running;
  }

  public static Board getInstance(int $sx, int $sy)
  {
    if(_board == null)
      return (_board = new Board($sx, $sy));

    return _board;
  }

  public void setPlayer(Player $player) {
    _player = $player;
  }

  public void update(final float $dt) {
    //If the activity is being run execute each statement.
    StatementInstance instance = _editor.getStatement();
    if(_running && instance != null || (instance != null && ! instance.isDone()))
    {
      if(instance.executed())
      {
        instance.reset();
        if(_running)
          instance = _editor.nextStatement();
      }
      else
      {
        instance.eval();
        if( ! _running && instance.isDone())
        {
          instance.reset();
          _editor.reset();
          _player.reset();
        }
      }
    }
    
    //Update the player.
    _player.update($dt);
  }

  public void draw(final PGraphics $graphics) {
    //Set up the graphics mode to correcty draw the blocks
    //and draw each block.
    $graphics.rectMode(PGraphics.CORNER);
    for(int y = 0; y < _size_y; y++) {
      for(int x = 0; x < _size_x; x++) {
        _blocks[x][y].draw($graphics);
      }
    }

    //Draw the player.
    _player.draw($graphics);
  }

  public final PVector getDims() {
    return new PVector(_dims.x, _dims.y);
  }

  public void visitBlock(final PVector $position) {
    int x = (int) Math.floor($position.x / Block.SIZE),
        y = (int) Math.floor($position.y / Block.SIZE);

    _blocks[x][y].doAction();
  }

  public void handleMouseEvent(MouseEvent $event) {
  }
  
  public Player getPlayer()
  {
    return _player;
  }

  /**
   * Generates a new board. This will destroy the current board, if one is
   * not present then that's okay
   *
   * @return void
   */
  public void generateBoard(int level) {
    _blocks = new Block[_size_x][_size_y];

    for(int y = 0; y < _size_y; y++) {
      for(int x = 0; x < _size_x; x++) {
        int[][] map = this.getLevel(level);
        _blocks[x][y] = BlockFactory.create(new PVector(x * Block.SIZE, y * Block.SIZE), Block.Type.get(map[x][y]));
      }
    }
  }

  /**
   * @return Gets the 2D integer array for the level provided
   */
  private static int[][] getLevel(int level) {
    switch(level) {
      case 0: return LEVEL_0;
      case 1: return LEVEL_1;
      case 2: return LEVEL_2;
      case 3: return LEVEL_3;
      case 4: return LEVEL_4;
      case 5: return LEVEL_5;
      case 6: return LEVEL_6;
      case 7: return LEVEL_7;
      case 8: return LEVEL_8;
      case 9: return LEVEL_9;
      default: return generateRandomLevel();
    }
  }

  private static int[][] generateRandomLevel() {
    int ret[][] = new int[10][10];

    int treasure = 3;
    int food = 8;

    for(int y = 0; y < 10; y++) {
      for(int x = 0; x < 10; x++) {
        double r = Math.random();
        if(r < 0.1) {
          if(treasure > 0) {
            ret[x][y] = Block.Type.TREASURE.value;
            treasure--;
          }
        } else if ( r < 0.4 ) {
          if(food > 0) {
            ret[x][y] = Block.Type.FOOD.value;
            food--;
          }
        } else {
          ret[x][y] = Block.Type.EMPTY.value;
        }
      }
    }

    return ret;
  }
  
  public void nextLevel() {
    this.generateBoard(++level);
  }


  // ###########################################################################
  // The Static Levels
  //
  // @see Block.Type for more information on what the integer values mean.
  // ###########################################################################
  private static final int[][] LEVEL_0 =  { {0,0,0,0,0,0,0,0,0,1},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,1,1,0,0,0,0},
                                            {0,0,0,0,2,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,2,0,0,0,0,0},
                                            {1,0,0,0,0,0,0,0,0,1} };

  private static final int[][] LEVEL_1 =  { {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,2,0,0,2,0,0,0},
                                            {0,0,0,0,1,1,0,0,0,0},
                                            {0,0,0,2,0,0,2,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

  private static final int[][] LEVEL_2 =  { {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

  private static final int[][] LEVEL_3 =  { {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

  private static final int[][] LEVEL_4 =  { {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

  private static final int[][] LEVEL_5 =  { {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

  private static final int[][] LEVEL_6 =  { {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

  private static final int[][] LEVEL_7 =  { {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

  private static final int[][] LEVEL_8 =  { {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

  private static final int[][] LEVEL_9 =  { {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

}

