import processing.core.PGraphics;

import java.awt.event.MouseEvent;

public interface Screen extends Drawable
{
  public void handleMouseEvent(MouseEvent $event);
}
