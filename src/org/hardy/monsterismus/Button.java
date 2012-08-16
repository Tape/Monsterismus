package org.hardy.monsterismus;

import org.hardy.monsterismus.api.Drawable;

import processing.core.PImage;

/**
 * A very primitive class that is used to bind events to
 * 
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public abstract class Button implements Drawable {
    public static PImage execute, leveldown, levelup, reset, swap, play, tutorial;

    public int x, y, w, h;
    public boolean clicked;
    public Runnable run;

    public Button(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    /**
     * Checks to see if the button was clicked at the given x and y coordinates
     * 
     * @param x
     * @param y
     * @return
     */
    public boolean isClicked(int x, int y) {
        clicked = (x < this.w + this.x && x > this.x && y < this.h + this.y && y > this.y);
        if (clicked && run != null) {
            run.run();
        }
        return clicked;
    }

    /**
     * Releases the button and forces it to be unclicked
     */
    public void release() {
        clicked = false;
    }

    /**
     * Bind an event to the Button, so that when clicked, it will be run.
     * 
     * @param r
     *            the runnable class or instance that you want to have execute
     */
    public void event(Runnable r) {
        this.run = r;
    }

    /**
     * Updates the button
     */
    public void update(final float $dt) {
    }
}
