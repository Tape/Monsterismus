import processing.core.PVector;

/**
 *
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public abstract class StatementInstance implements Drawable {
  public static int SPACING = 5;

  protected PVector _pos;
  protected boolean _locked = false;

  public StatementInstance() {
    _pos = new PVector();
  }

  public void setPos(float x, float y) {
    _pos.x = x;
    _pos.y = y;
  }

  public PVector getPos() {
    return _pos;
  }

  public abstract boolean isChild();
  public abstract StatementInstance instanceUnder(final float $x, final float $y);
  public abstract int getHeight();
  public abstract void handleClick();
  public abstract void eval();
  public abstract boolean isDone();
  public abstract boolean executed();
  public abstract void reset();
}

