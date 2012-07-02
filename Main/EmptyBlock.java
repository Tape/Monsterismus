import processing.core.PVector;
import processing.core.PGraphics;

public class EmptyBlock extends Block
{
  public EmptyBlock(final PVector $pos)
  {
    super($pos);
  }
  
  public void draw(final PGraphics $graphics)
  {
    $graphics.fill(0xFFFF0000);
    $graphics.rect(pos.x, pos.y, Board.BLOCK_SIZE, Board.BLOCK_SIZE);
  }
}
