/**
 *
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public interface Nestable
{
  public void addChild(Instance $instance, ProgrammingStatement $statement);
  
  public enum Instance
  {
    CONDITIONAL(0),
    CONSEQUENT(1),
    ALTERNATIVE(2);
    
    private final int _value;
    
    Instance(int $v)
    {
      this._value = $v;
    }
  }
}
