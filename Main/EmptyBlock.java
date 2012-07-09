import processing.core.PVector;
import processing.core.PGraphics;

public class EmptyBlock extends Block
{
  private static final int FILL_COLOR = 0xFFFFFFFF;
  
  public EmptyBlock(final PVector $pos)
  {
    super($pos);
  }
  
  public void draw(final PGraphics $graphics, final float $dt)
  {
    $graphics.fill(FILL_COLOR);
    $graphics.rect(pos.x, pos.y, Board.BLOCK_SIZE, Board.BLOCK_SIZE);
  }
  
  public void doAction() { }
}

