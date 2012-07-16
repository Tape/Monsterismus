import processing.core.PVector;

public abstract class StatementInstance implements Drawable
{
  protected PVector _pos;
  protected boolean _locked = false;
  
  public StatementInstance()
  {
    _pos = new PVector();
  }
}

