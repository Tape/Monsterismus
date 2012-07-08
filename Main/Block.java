import processing.core.PVector;
import processing.core.PGraphics;

public abstract class Block implements Drawable
{
  protected PVector pos;
  
  public Block(final PVector $pos)
  {
    pos = $pos;
  }
}

