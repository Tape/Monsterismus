import processing.core.PVector;
import processing.core.PGraphics;

public abstract class Block
{
  protected PVector pos;
  
  public Block(final PVector $pos)
  {
    pos = $pos;
  }
  
  public abstract void draw(final PGraphics graphics);
}

