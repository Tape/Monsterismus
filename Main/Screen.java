import processing.core.PGraphics;

//import android.view.MotionEvent;
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
  //public void handleMotionEvent(MotionEvent $event);
  public void handleMouseEvent(MouseEvent $event);
}
