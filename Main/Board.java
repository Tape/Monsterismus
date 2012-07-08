import processing.core.PVector;
import processing.core.PGraphics;

public class Board implements Drawable
{
  public static final int BLOCK_SIZE = 30;
  private Block[][] _blocks;
  private int _size_x, _size_y;
  
  public Board(final int $sx, final int $sy)
  {
    _size_x = $sx;
    _size_y = $sy;
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
}

