import processing.core.PGraphics;

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
  private static final int BASE_HEIGHT = 40;

  public int getColor()
  {
    return FILL_COLOR;
  }

  public StatementInstance spawnInstance()
  {
    return new IfStatementInstance();
  }

  private class IfStatementInstance extends StatementInstance
  {
    public void update(final float $dt) { }

    public void draw(final PGraphics $graphics)
    {
      $graphics.fill(FILL_COLOR);
      $graphics.rect(_pos.x, _pos.y, BASE_WIDTH, BASE_HEIGHT);
      $graphics.fill(0xFFFFFFFF);
      $graphics.text(toString(), _pos.x + 3, _pos.y + 12);
    }

    public int getHeight()
    {
      return BASE_HEIGHT;
    }

    public StatementInstance instanceUnder(int $x, int $y)
    {
      if($x > _pos.x && $y > _pos.y
        && $x < _pos.x + BASE_WIDTH && $y < _pos.y + BASE_HEIGHT)
      {
        return this;
      }
      return null;
    }

    public boolean isChild()
    {
      return false;
    }
  }
}

