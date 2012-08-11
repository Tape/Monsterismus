import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

import processing.core.PVector;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PApplet;

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
    $graphics.pushMatrix();
    $graphics.translate(pos.x, pos.y);
    $graphics.rect(0, 0, Block.SIZE, Block.SIZE);
    $graphics.fill(255,255,255);

    // If they've already picked it up. Hide the block
    if(amount > 0) {
      $graphics.image(img, 0, 0);
    }

    $graphics.popMatrix();
  }

  public void doAction(Player p) {
    if(amount > 0) {
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

