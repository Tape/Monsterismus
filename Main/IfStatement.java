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
  private static final int BASE_HEIGHT = 60;

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
    private Conditional _conditional = Conditional.get(0);
    private StatementInstance _consequent;
    private StatementInstance _alternative;
    private StatementInstance _evaluated;
    private String _label = "If " + _conditional.getLabel() + ", then ";
    private boolean _is_done = true, _is_executed = false;
    
    public void update(final float $dt)
    {
    }

    public void draw(final PGraphics $graphics)
    {
      $graphics.fill(FILL_COLOR);
      $graphics.rect(_pos.x, _pos.y, BASE_WIDTH, getHeight());
      $graphics.fill(0xFFFFFFFF);
      $graphics.text(_label, _pos.x + 3, _pos.y + 12);
      
      if(_consequent != null)
      {
        $graphics.text("else", _pos.x + 3, _pos.y + _consequent.getHeight() + 35);
        _consequent.draw($graphics);
      }
      else
        $graphics.text("else", _pos.x + 3, _pos.y + 40);
      
      if(_alternative != null)
      {
        _alternative.draw($graphics);
      }
    }
    
    public void setPos(final float $x, final float $y)
    {
      super.setPos($x, $y);
      
      if(_consequent != null)
        _consequent.setPos(_pos.x + 5, _pos.y + 20);
      
      if(_alternative != null)
        if(_consequent != null)
          _alternative.setPos(_pos.x + 5, _pos.y + _consequent.getHeight() + 40);
        else
          _alternative.setPos(_pos.x + 5, _pos.y + 45);
    }

    public int getHeight()
    {
      int height = BASE_HEIGHT;
      
      if(_consequent != null)
      {
        height += _consequent.getHeight() - 5;
      }
      
      if(_alternative != null)
      {
        height += _alternative.getHeight() - 10;
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
      else if(_alternative == $instance) _alternative = null;
    }
    
    public StatementInstance instanceUnder(final float $x, final float $y)
    {
      if($x > _pos.x && $y > _pos.y
        && $x < _pos.x + BASE_WIDTH && $y < _pos.y + getHeight())
      {
        StatementInstance under = null;
        if(_consequent != null)
          under = _consequent.instanceUnder($x, $y);
        if(under == null && _alternative != null)
          under = _alternative.instanceUnder($x, $y);
        
        return under == null ? this : under;
      }
      return null;
    }
    
    public void addChild(StatementInstance $instance)
    {
      $instance.setParent(this);
      int offset = _consequent != null ? _consequent.getHeight() : 10;
      
      if($instance.getPos().y - _pos.y > offset + 30)
        _alternative = $instance;
      else
        _consequent = $instance;
    }
    
    public void handleClick()
    {
      //Increment the conditional.
      _conditional = _conditional.next();
      
      //Update the label.
      _label = "If ";
      _label += _conditional.getLabel() + ", then";
    }
    
    public boolean isDone()
    {
      return _is_done;
    }
    
    public boolean executed()
    {
      return _is_executed;
    }
    
    public void eval()
    {
      //Evaluate until action is complete.
      if( ! _is_done)
      {
        //Action finished? reset.
        if(_evaluated.isDone())
        {
          _evaluated.reset();
          _is_done = _is_executed = true;
        }
        else
          _evaluated.eval();
      }
      else
      {
        _evaluated = _conditional.evaluate() ? _consequent : _alternative;
        if(_evaluated != null)
        {
          _is_done = false;
          _evaluated.eval();
        }
        else
          _is_done = _is_executed = true;
      }
    }
    
    public void reset()
    {
      _is_done = true;
      _is_executed = false;
      
      if(_consequent != null)
        _consequent.reset();
    }
  }
  
  private enum Conditional
  {
    FOOD_ABOVE(0, "food above"),
    FOOD_BELOW(1, "food below"),
    FOOD_LEFT(2, "food left"),
    FOOD_RIGHT(3, "food right");
    
    private int _value;
    private String _label;
    private Evaluator _eval;
    
    Conditional(final int $value, final String $label)
    {
      _value = $value;
      _label = $label;
      
      switch($value)
      {
      //Case 0 - 4, search for food.
      case 0: case 1: case 2: case 3:
        _eval = new Evaluator() {
          public boolean eval()
          {
            //Load up the board and the player position.
            Board board = Board.getInstance();
            PVector pos = board.getPlayer().getPosition();
            Block[][] blocks = board.getBlocks();
            
            //Prepare the player position.
            int x = (int)Math.floor(pos.x / Block.SIZE),
                y = (int)Math.floor(pos.y / Block.SIZE),
                dir = $value == 0 || $value == 2 ? -1 : 1;
            
            if($value < 2)
            {
              for(y += dir; y >= 0 && y < blocks[x].length; y += dir)
                if(blocks[x][y] instanceof FoodBlock && ! blocks[x][y].claimed())
                  return true;
              return false;
            }
            else
            {
              for(x += dir; x >= 0 && x < blocks.length; x += dir)
                if(blocks[x][y] instanceof FoodBlock && ! blocks[x][y].claimed())
                  return true;
              return false;
            }
          }
        };
        break;
      }
    }
    
    private static Conditional get(final int $value)
    {
      switch($value)
      {
      case 0: return FOOD_ABOVE;
      case 1: return FOOD_BELOW;
      case 2: return FOOD_LEFT;
      case 3: return FOOD_RIGHT;
      default: return FOOD_ABOVE;
      }
    }
    
    public String getLabel()
    {
      return _label;
    }
    
    public boolean evaluate()
    {
      return _eval.eval();
    }
    
    public Conditional next()
    {
      return get((_value + 1) % 4);
    }
  }
  
  private interface Evaluator
  {
    public boolean eval();
  }
}

