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
public class EmptyBlock extends Block {
  
  public static PImage img;

  public EmptyBlock(final PVector $pos) {
    super($pos);
  }

  public void update(final float $dt) {
  }

  public void draw(final PGraphics $graphics) {
    $graphics.pushMatrix();
    $graphics.translate(pos.x, pos.y);
    $graphics.stroke(0, 90);
    $graphics.fill(108,207,81);
    $graphics.rect(0, 0, Block.SIZE, Block.SIZE);
    $graphics.popMatrix();
  }

  public void doAction(Player p) { }
  public boolean claimed() { return false; }
  public void reset() { }
}

