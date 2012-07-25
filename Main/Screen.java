import processing.core.PGraphics;

import java.awt.event.MouseEvent;

/**
 *
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public interface Screen extends Drawable {
  public void handleMouseEvent(MouseEvent $event);
}
