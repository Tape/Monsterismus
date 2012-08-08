import processing.core.PVector;
import processing.core.PGraphics;

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

  public FoodBlock(final PVector $pos) {
    super($pos);
    reset();
  }

  public void update(final float $dt) {
  }

  public void draw(final PGraphics $graphics) {
    // If they've already picked it up. Hide the block
    if(amount == 0)
      $graphics.fill(255,255,255);
    else
      $graphics.fill(255,0,0);

    $graphics.rect(pos.x, pos.y, Block.SIZE, Block.SIZE);
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

