package org.hardy.monsterismus.api;

import android.view.MotionEvent;

/**
 * 
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public interface Screen extends Drawable {
    public void handleMotionEvent(MotionEvent $event);
}
