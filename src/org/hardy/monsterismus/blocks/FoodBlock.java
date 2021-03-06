package org.hardy.monsterismus.blocks;

import org.hardy.monsterismus.Player;

import processing.core.PVector;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 * 
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public class FoodBlock extends Block {
    private int amount;
    public static PImage img;

    public FoodBlock(final PVector $pos) {
        super($pos);
        reset();
    }

    public void update(final float $dt) {
    }

    public void draw(final PGraphics $graphics) {
        super.draw($graphics);

        // If they've already picked it up. Hide the block
        if (amount > 0) {
            $graphics.image(img, 0, 0);
        }

        $graphics.popMatrix();
    }

    public void doAction(Player p) {
        if (amount > 0) {
            p.addToFoodCount(amount);
            p.addToScore(amount * 5);
            amount = 0;
        }
    }

    public boolean claimed() {
        return amount == 0;
    }

    public void reset() {
        amount = 2;
    }
}
