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
public class EmptyBlock extends Block {

  public EmptyBlock(final PVector $pos) {
    super($pos);
  }

  public void update(final float $dt) {
  }

  public void draw(final PGraphics $graphics) {
    $graphics.pushMatrix();
    $graphics.translate(pos.x, pos.y);
    $graphics.rect(0, 0, Block.SIZE, Block.SIZE);
    $graphics.fill(255,255,255);
    $graphics.popMatrix();
  }

  public void doAction(Player p) { }
  public boolean claimed() { return false; }
  public void reset() { }
}

