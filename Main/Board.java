import processing.core.PVector;
import processing.core.PGraphics;

public class Board implements Drawable
{
  public static final int BLOCK_SIZE = 48;
  private Block[][] _blocks;
  private int _size_x, _size_y;
  private PVector _dims;
  private static Board _board;
  
  private Board(final int $sx, final int $sy)
  {
    _size_x = $sx;
    _size_y = $sy;
    _dims = new PVector($sx * BLOCK_SIZE, $sy * BLOCK_SIZE);
    _blocks = new Block[_size_x][_size_y];
    
    //Initialize board array.
    for(int y = 0; y < _size_y; y++)
    {
      for(int x = 0; x < _size_x; x++)
      {
        _blocks[x][y] = BlockFactory.create(new PVector(x * BLOCK_SIZE, y * BLOCK_SIZE), BlockType.EMPTY);
      }
    }
  }
  
  public static Board getInstance()
  {
    if(_board == null)
      throw new RuntimeException("Board has not been created yet");
    
    return _board;
  }
  
  public static Board getInstance(int $sx, int $sy)
  {
    if(_board == null)
      return (_board = new Board($sx, $sy));
    
    return _board;
  }
  
  public void draw(final PGraphics $graphics, final float $dt)
  {
    $graphics.rectMode(PGraphics.CORNER);
    for(int y = 0; y < _size_y; y++)
    {
      for(int x = 0; x < _size_x; x++)
      {
        _blocks[x][y].draw($graphics, $dt);
      }
    }
  }
  
  public final PVector getDims()
  {
    return new PVector(_dims.x, _dims.y);
  }
}

