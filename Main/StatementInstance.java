import processing.core.PVector;

/**
 *
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public abstract class StatementInstance implements Drawable
{
  public static int SPACING = 5;

  protected PVector _pos;
  protected boolean _locked = false;
  private Nestable _parent;

  public StatementInstance()
  {
    _pos = new PVector();
  }

  public void setPos(final float $x, final float $y)
  {
    _pos.x = $x;
    _pos.y = $y;
  }

  public PVector getPos()
  {
    return _pos;
  }
  
  public boolean isNestable()
  {
    return this instanceof Nestable;
  }

  public boolean isChild()
  {
    return _parent == null;
  }
  
  public void setParent(final Nestable $parent)
  {
    if($parent != null)
    {
      _parent = $parent;
      return;
    }
    
    if(_parent != null)
    {
      _parent.removeAllInstances(this);
      _parent = null;
    }
  }
  
  public abstract StatementInstance instanceUnder(final float $x, final float $y);
  public abstract int getHeight();
  public abstract int getWidth();
  public abstract void handleClick();
  public abstract void eval();
  public abstract boolean isDone();
  public abstract boolean executed();
  public abstract void reset();
}

