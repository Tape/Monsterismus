package org.hardy.monsterismus.blocks;

import org.hardy.monsterismus.Player;

import processing.core.PVector;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 * The blank block, it holds nothing
 * 
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public class EmptyBlock extends Block {
    public static PImage img;

    public EmptyBlock(final PVector $pos) {
        super($pos);
    }

    public void update(final float $dt) {
    }

    public void draw(final PGraphics $graphics) {
        super.draw($graphics);

        $graphics.popMatrix();
    }

    public void doAction(Player p) {
    }

    public boolean claimed() {
        return false;
    }

    public void reset() {
    }
}
