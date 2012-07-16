import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import processing.core.PVector;
import processing.core.PGraphics;

public class Editor implements Drawable
{
  private static final int FILL_COLOR = 0xFFFFFFFF;
  private PVector _dims;
  private PVector _toolbar;
  
  //For drawing each icon in the toolbar.
  private int _icon_bounds, _icon_size, _icon_offset;
  
  //Map containing our statements.
  private static final Map<Class, ProgrammingStatement> _statements;
  static
  {
    Map<Class, ProgrammingStatement> map = new HashMap<Class, ProgrammingStatement>();
    
    //Insert our control structures.
    map.put(IfStatement.class, new IfStatement());
    
    //Make it final!
    _statements = Collections.unmodifiableMap(map);
  }
  
  public Editor(final PVector $dims)
  {
    _dims = $dims;
    _toolbar = new PVector($dims.x * 0.15f, $dims.y);
    
    //The bounds is the overall size of the rectangle.
    _icon_bounds = (int)_toolbar.x;
    //The offset is the padding around the rectangle.
    _icon_offset = (int)(_icon_bounds * 0.1f);
    //The size is the size of the rectangle to draw.
    _icon_size = _icon_bounds - _icon_offset * 2;
  }
  
  public void update(final float $dt)
  {
  }
  
  public void draw(final PGraphics $graphics)
  {
    //Draw the core screen.
    $graphics.fill(FILL_COLOR);
    $graphics.rect(0, 0, _dims.x, _dims.y);
    
    //Draw the editor toolbar.
    $graphics.rect(0, 0, _toolbar.x, _toolbar.y);
    
    //Draw each statement icon.
    int offset = 0;
    for(Class statement : _statements.keySet())
    {
      ProgrammingStatement ps = _statements.get(statement);
      $graphics.fill(ps.getColor());
      $graphics.rect(_icon_offset, offset + _icon_offset, _icon_size, _icon_size);
      
      offset += _icon_bounds;
    }
  }
}

