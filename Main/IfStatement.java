import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public class IfStatement extends ProgrammingStatement
{
  private static final int FILL_COLOR = 0xFFFF0000;
  private static final int BASE_WIDTH = 300;
  private static final int BASE_HEIGHT = 25;

  public int getColor()
  {
    return FILL_COLOR;
  }

  public StatementInstance spawnInstance()
  {
    return new IfStatementInstance();
  }

  private class IfStatementInstance extends StatementInstance implements Nestable
  {
    private StatementInstance _conditional;
    private StatementInstance _consequent;
    private StatementInstance _alternative;
    
    public void update(final float $dt)
    {
    }

    public void draw(final PGraphics $graphics)
    {
      int height = BASE_HEIGHT;
      
      if(_consequent != null)
      {
        height += _consequent.getHeight();
      }
      
      $graphics.fill(FILL_COLOR);
      $graphics.rect(_pos.x, _pos.y, BASE_WIDTH, height);
      $graphics.fill(0xFFFFFFFF);
      $graphics.text(toString(), _pos.x + 3, _pos.y + 12);
      
      if(_consequent != null)
      {
        _consequent.draw($graphics);
      }
    }
    
    public void setPos(final float $x, final float $y)
    {
      super.setPos($x, $y);
      if(_consequent != null)
      {
        _consequent.setPos(_pos.x + 5, _pos.y + 20);
      }
    }

    public int getHeight()
    {
      int height = BASE_HEIGHT;
      
      if(_consequent != null)
      {
        height += _consequent.getHeight();
      }
      
      return height;
    }
    
    public int getWidth()
    {
      return BASE_WIDTH;
    }
    
    public void removeAllInstances(final StatementInstance $instance)
    {
      if(_consequent == $instance) _consequent = null;
    }
    
    public StatementInstance instanceUnder(final float $x, final float $y)
    {
      if($x > _pos.x && $y > _pos.y
        && $x < _pos.x + BASE_WIDTH && $y < _pos.y + getHeight())
      {
        StatementInstance under = this;
        if(_consequent != null)
        {
          under = checkUnder(_consequent, $x, $y);
        }
        return under;
      }
      return null;
    }
    
    private StatementInstance checkUnder(final StatementInstance $instance, final float $x, final float $y)
    {
      PVector pos = $instance.getPos();
      if(pos.x < $x && pos.x + $instance.getWidth() > $x
        && pos.y < $y && pos.y + $instance.getHeight() > $y)
      {
        return $instance;
      }
      return this;
    }
    
    public void addChild(StatementInstance $instance)
    {
      $instance.setParent(this);
      _consequent = $instance;
    }
    
    public void handleClick() {}
    public boolean isDone() { return true; }
    public boolean executed() { return true; }
    public void eval() { }
    public void reset() { }
  }
}

