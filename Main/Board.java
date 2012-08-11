import java.util.List;
import java.util.ArrayList;

import processing.core.PVector;
import processing.core.PGraphics;

//import android.view.MotionEvent;
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
  private int level, _foodcount;
  private boolean _running = false, _reset = true;

  private Button nextLevelButton,
                 previousLevelButton,
                 editorButton,
                 execButton,
                 resetButton;


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
    
    int offset = (Block.SIZE - Player.SIZE) / 2;
    _player = new Player(new PVector(offset, offset));

    nextLevelButton = new Button(10,520,50,50) {
      public void draw(final PGraphics $graphics) {
        $graphics.pushMatrix();
        $graphics.translate(this.x, this.y);
        if (this.clicked)
          $graphics.fill(128,128,128);
         else
          $graphics.fill(255,255,255);
        $graphics.rect(0,0,50,50);
        $graphics.fill(0,0,0);
        $graphics.text("+", 5, 18);
        $graphics.popMatrix();
      }
    };

    previousLevelButton = new Button(70,520,50,50) {
      public void draw(final PGraphics $graphics) {
        $graphics.pushMatrix();
        $graphics.translate(this.x, this.y);
        if (this.clicked)
          $graphics.fill(128,128,128);
         else
          $graphics.fill(255,255,255);
        $graphics.rect(0,0,50,50);
        $graphics.fill(0,0,0);
        $graphics.text("-", 5, 18);
        $graphics.popMatrix();
      }
    };

    editorButton = new Button(130,520,50,50) {
      public void draw(final PGraphics $graphics) {
        $graphics.pushMatrix();
        $graphics.translate(this.x, this.y);
        if (this.clicked)
          $graphics.fill(128,128,128);
         else
          $graphics.fill(255,255,255);
        $graphics.rect(0,0,50,50);
        $graphics.fill(0,0,0);
        $graphics.text("Editor", 5, 18);
        $graphics.popMatrix();
      }
    };
    editorButton.event(new Runnable() {
      public void run(){
        reset();
        setRunning(false);
        Game.switchScreen(Game.EDITOR);
      }
    });

    execButton = new Button(190,520,50,50) {
      public void draw(final PGraphics $graphics) {
        $graphics.pushMatrix();
        $graphics.translate(this.x, this.y);
        if (this.clicked)
          $graphics.fill(128,128,128);
         else
          $graphics.fill(255,255,255);
        $graphics.rect(0,0,50,50);
        $graphics.fill(0,0,0);
        $graphics.text("Exec", 5, 18);
        $graphics.popMatrix();
      }
    };
    execButton.event(new Runnable() {
      public void run() {
        reset();
        setRunning(true);
      }
    });

    resetButton = new Button(250,520,50,50) {
      public void draw(final PGraphics $graphics) {
        $graphics.pushMatrix();
        $graphics.translate(this.x, this.y);
        if (this.clicked)
          $graphics.fill(128,128,128);
         else
          $graphics.fill(255,255,255);
        $graphics.rect(0,0,50,50);
        $graphics.fill(0,0,0);
        $graphics.text("Reset", 5, 18);
        $graphics.popMatrix();
      }
    };
    resetButton.event(new Runnable() {
     public void run() {
       setRunning(false);
       reset();
     }
    });

    this.generateBoard(level);
  }

  public static Board getInstance()
  {
    if(_board == null)
      throw new RuntimeException("Board has not been created yet");

    return _board;
  }

  public Block[][] getBlocks()
  {
    return _blocks;
  }

  public void setRunning(boolean $running)
  {
    if(_running)
    {
      _running = $running;
      _reset = false;
    }
    else if(_reset)
    {
      _running = $running;
    }
  }

  public void toggleRunning()
  {
    setRunning( ! _running);
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
    StatementInstance instance = Game.editor.getStatement();

    if(_running && instance != null) {
      if(instance.executed()) {
        instance.reset();
        if(_running)
          instance = Game.editor.nextStatement();
      } else{
        instance.eval();
      }
    } else if( ! _reset && instance != null) {
      _reset = true;
      this.reset();
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

    $graphics.pushMatrix();
    $graphics.translate(0,480);

    $graphics.fill(255,255,255);
    $graphics.rect(0,0,_dims.x,30);
    $graphics.fill(0,0,0);
    $graphics.text("Treasure: " + _player.getTreasureCount(), 5, 18);
    $graphics.text("Food: " + _player.getFoodCount(), 100, 18);
    $graphics.text("Score: " + _player.getScore(), 175, 18);
    $graphics.popMatrix();

    nextLevelButton.draw($graphics);
    previousLevelButton.draw($graphics);
    editorButton.draw($graphics);
    execButton.draw($graphics);
    resetButton.draw($graphics);
  }

  public final PVector getDims() {
    return new PVector(_dims.x, _dims.y);
  }

  public void visitBlock(final PVector $position) {
    int x = (int) Math.floor($position.x / Block.SIZE),
        y = (int) Math.floor($position.y / Block.SIZE);

    _blocks[x][y].doAction(_player);

    if(_player.getFoodCount() == _foodcount)
    {
      _foodcount = 0;
      _player.reset(true);
      generateBoard(++level);
      Game.editor.reset();
      Game.editor.clear();
    }
  }

  public void handleMouseEvent(MouseEvent $event) {
    int   x = (int)$event.getX(),
          y = (int)$event.getY();
    switch($event.getID()) {
      case MouseEvent.MOUSE_PRESSED:
        nextLevelButton.isClicked(x,y);
        previousLevelButton.isClicked(x,y);
        editorButton.isClicked(x,y);
        execButton.isClicked(x,y);
        resetButton.isClicked(x,y);
        break;
      case MouseEvent.MOUSE_RELEASED:
        nextLevelButton.release();
        previousLevelButton.release();
        editorButton.release();
        execButton.release();
        resetButton.release();
        break;
    }
  }

  public Player getPlayer() {
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
    int[][] map = this.getLevel(level);

    for(int y = 0; y < _size_y; y++) {
      for(int x = 0; x < _size_x; x++) {
        Block.Type blockType = Block.Type.get(map[x][y]);
        _blocks[x][y] = BlockFactory.create(new PVector(x * Block.SIZE, y * Block.SIZE), blockType);

        if(blockType == Block.Type.FOOD)
        {
          _foodcount += 2;
        }
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

  public void previousLevel() {
    this.generateBoard(--level);
  }

  public void reset() {
    StatementInstance si = Game.editor.getStatement();
    if(si != null)
      Game.editor.getStatement().reset();
    Game.editor.reset();
    _player.reset();

    for(int y=0; y<10; y++)
      for(int x=0; x<10; x++)
        _blocks[y][x].reset();
  }


  // ###########################################################################
  // The Static Levels
  //
  // @see Block.Type for more information on what the integer values mean.
  // ###########################################################################
  private static final int[][] LEVEL_0 =  { {0,1,1,1,1,1,1,1,1,1},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

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
                                            {0,0,1,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,1,0,2,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,1,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,2,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

  private static final int[][] LEVEL_3 =  { {0,1,1,1,2,0,0,0,0,0},
                                            {0,0,0,0,2,0,0,1,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,1,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,1,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,1,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,1,0,0,0,0,0} };

  private static final int[][] LEVEL_4 =  { {0,0,0,0,1,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,1,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,2,0,1},
                                            {0,2,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,1,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,1},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,1,0,0,1,0,0} };

  private static final int[][] LEVEL_5 =  { {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,2,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,1,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,2,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,1,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

  private static final int[][] LEVEL_6 =  { {0,0,0,0,0,0,0,0,0,0},
                                            {0,1,0,0,0,0,0,0,0,0},
                                            {0,0,1,0,0,0,0,1,1,0},
                                            {0,0,0,1,0,0,1,0,1,0},
                                            {0,0,0,0,2,2,0,0,1,0},
                                            {0,0,0,0,2,2,0,0,1,0},
                                            {0,0,0,0,0,0,0,0,1,0},
                                            {0,0,0,0,0,0,0,0,1,0},
                                            {0,0,0,0,0,0,0,0,1,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

  private static final int[][] LEVEL_7 =  { {0,0,0,0,0,0,0,0,0,0},
                                            {0,1,0,0,1,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,2,0,1,0,2,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,2,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

  private static final int[][] LEVEL_8 =  { {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,1,0,0,0,0,0,0,0,0},
                                            {0,0,0,1,0,0,0,0,0,0},
                                            {0,0,0,0,0,1,0,0,0,0},
                                            {0,0,0,0,0,1,0,0,0,0},
                                            {0,0,0,1,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,1,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

  private static final int[][] LEVEL_9 =  { {1,0,0,0,0,0,0,1,0,0},
                                            {0,1,0,0,0,0,0,1,0,0},
                                            {0,0,1,0,0,0,0,2,1,0},
                                            {0,0,1,0,0,0,0,0,1,0},
                                            {0,0,1,0,0,0,0,0,2,0},
                                            {0,0,1,2,1,1,0,0,1,0},
                                            {0,0,2,0,0,0,1,0,1,0},
                                            {0,0,1,0,0,0,0,1,0,0},
                                            {0,0,0,0,0,0,0,0,0,0},
                                            {0,0,0,0,0,0,0,0,0,0} };

}

// public class NextLevelButton extends Button {
//   public NextLevelButton(int x, int y) {
//     super(x,y,50,50);
//   }
//   public void draw(final PGraphics $graphics) {
    // $graphics.pushMatrix();
    // $graphics.translate(this.x, this.y);
    // $graphics.fill(255,255,255);
    // $graphics.rect(0,0,50,50);
    // $graphics.fill(0,0,0);
    // $graphics.text("+", 5, 18);
//    $graphics.popMatrix();
//   }
// }

// public class PreviousLevelButton extends Button {
//   public PreviousLevelButton(int x, int y) {
//     super(x,y,50,50);
//   }
//   public void draw(final PGraphics $graphics) {
//     $graphics.pushMatrix();
//     $graphics.translate(this.x, this.y);
//     $graphics.fill(255,255,255);
//     $graphics.rect(0,0,50,50);
//     $graphics.fill(0,0,0);
//     $graphics.text("-", 5, 18);
//     $graphics.popMatrix();
//   }
// }

// public class ExecButton extends Button {
//   public ExecButton(int x, int y) {
//     super(x,y,50,50);
//   }
//   public void draw(final PGraphics $graphics) {
//     $graphics.pushMatrix();
//     $graphics.translate(this.x, this.y);
//     $graphics.fill(255,255,255);
//     $graphics.rect(0,0,50,50);
//     $graphics.fill(0,0,0);
//     $graphics.text("Exec", 5, 18);
//     $graphics.popMatrix();
//   }
// }

// public class ResetButton extends Button {
//   public ExecButton(int x, int y) {
//     super(x,y,50,50);
//   }
//   public void draw(final PGraphics $graphics) {
//     $graphics.pushMatrix();
//     $graphics.translate(this.x, this.y);
//     $graphics.fill(255,255,255);
//     $graphics.rect(0,0,50,50);
//     $graphics.fill(0,0,0);
//     $graphics.text("Reset", 5, 18);
//     $graphics.popMatrix();
//   }
// }


// public class EditorButton extends Button {
//   public EditorButton(int x, int y) {
//     super(x,y,50,50);
//   }
//   public void draw(final PGraphics $graphics) {
//     $graphics.pushMatrix();
//     $graphics.translate(this.x, this.y);
//     $graphics.fill(255,255,255);
//     $graphics.rect(0,0,50,50);
//     $graphics.fill(0,0,0);
//     $graphics.text("Editor", 5, 18);
//     $graphics.popMatrix();
//   }
// }
