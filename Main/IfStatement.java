import processing.core.PGraphics;

public class IfStatement extends ProgrammingStatement
{
  private static final int FILL_COLOR = 0xFFFF0000;
  
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
      $graphics.rect(_pos.x, _pos.y, 200, 40);
    }
    
    public int getHeight()
    {
      return 40;
    }
  }
}

