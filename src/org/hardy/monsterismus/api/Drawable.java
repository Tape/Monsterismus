package org.hardy.monsterismus.api;

import processing.core.PGraphics;

/**
 * 
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public interface Drawable {
    /**
     * Updates the given object.
     * 
     * @param $dt
     *            The change in time since the last frame.
     */
    public void update(final float $dt);

    /**
     * Draws the object on the given graphics canvas.
     * 
     * @param $graphics
     *            The graphics canvas to draw on.
     */
    public void draw(final PGraphics $graphics);
}
