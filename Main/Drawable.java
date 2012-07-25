import processing.core.PGraphics;

/**
 *
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public interface Drawable
{
  public void update(final float $dt);

  public void draw(final PGraphics $graphics);
}

