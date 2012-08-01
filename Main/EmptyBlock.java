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
    $graphics.fill(255,255,255);
    $graphics.rect(pos.x, pos.y, Block.SIZE, Block.SIZE);
  }

  public void doAction(Player p) { }
}

