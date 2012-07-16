import processing.core.PVector;
import processing.core.PGraphics;

public class Editor implements Drawable
{
  private static final int FILL_COLOR = 0xFFFFFFFF;
  private PVector _dims;
  
  public Editor(final PVector $dims)
  {
    _dims = $dims;
  }
  
  public void draw(final PGraphics $graphics, final float $dt)
  {
    $graphics.fill(FILL_COLOR);
    $graphics.rect(0, 0, _dims.x, _dims.y);
  }
}
