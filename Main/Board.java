import processing.core.PVector;
import processing.core.PGraphics;

public class Board
{
  public static final int BLOCK_SIZE = 30;
  private Block[][] blocks;
  private int sx, sy;
  
  public Board(final int $sx, final int $sy)
  {
    sx = $sx;
    sy = $sy;
    blocks = new Block[sx][sy];
    
    //Initialize board array.
    for(int y = 0; y < sy; y++)
    {
      for(int x = 0; x < sx; x++)
      {
        blocks[x][y] = BlockFactory.create(new PVector(x * BLOCK_SIZE, y * BLOCK_SIZE), BlockType.EMPTY);
      }
    }
  }
  
  public void draw(final PGraphics graphics)
  {
    graphics.rectMode(PGraphics.CORNER);
    for(int y = 0; y < sy; y++)
    {
      for(int x = 0; x < sx; x++)
      {
        blocks[x][y].draw(graphics);
      }
    }
  }
}
