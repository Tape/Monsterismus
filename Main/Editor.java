import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import processing.core.PVector;
import processing.core.PGraphics;

//import android.view.MotionEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public class Editor implements Screen
{
  //Fill color of the background.
  private static final int FILL_COLOR = 0xFFFFFFFF;

  //Vector for the dimensions of the screen and the toolbar.
  private PVector _dims, _toolbar;

  //Cordinates where the mouse started dragging, in integer form.
  private float _drag_start_x, _drag_start_y;

  //For drawing each icon in the toolbar.
  private int _icon_bounds, _icon_size, _icon_offset;

  //Related to the drag handling.
  private boolean _dragstart;

  //List to contain all of our drawable elements.
  StatementInstance _instance;
  List<StatementInstance> _instances;
  
  private boolean _running = false;
  private int _position = 0;

  //Map containing our statements.
  private static final Map<Class, ProgrammingStatement> _statements;
  static {
    Map<Class, ProgrammingStatement> map = new HashMap<Class, ProgrammingStatement>();

    //Insert our control structures.
    map.put(IfStatement.class, new IfStatement());
    map.put(MoveStatement.class, new MoveStatement());

    //Make it final!
    _statements = Collections.unmodifiableMap(map);
  }

  public Editor(final PVector $dims) {
    _dims = $dims;
    _toolbar = new PVector($dims.x * 0.15f, $dims.y);

    //The bounds is the overall size of the rectangle.
    _icon_bounds = (int)_toolbar.x;
    //The offset is the padding around the rectangle.
    _icon_offset = (int)(_icon_bounds * 0.1f);
    //The size is the size of the rectangle to draw.
    _icon_size = _icon_bounds - _icon_offset * 2;
    clear();
  }

  public void update(final float $dt) {
  }
  
  public void reset() {
    _position = 0;
  }
  
  public void clear() {
    _instances = Collections.synchronizedList(new LinkedList<StatementInstance>());
  }
  
  public StatementInstance getStatement() {
    synchronized(_instances) {
      if(_instances.size() == 0)
        return null;
      
      return _instances.get(_position);
    }
  }
  
  public StatementInstance nextStatement() {
    synchronized(_instances) {
      _position = (_position + 1) % _instances.size();
      return _instances.get(_position);
    }
  }

  public void draw(final PGraphics $graphics) {
    //Draw the core screen.
    $graphics.fill(FILL_COLOR);
    $graphics.rect(0, 0, _dims.x, _dims.y);

    //Draw the editor toolbar.
    $graphics.rect(0, 0, _toolbar.x, _toolbar.y);

    //Draw each statement icon.
    int offset = 0;
    for(Class statement : _statements.keySet()) {
      ProgrammingStatement ps = _statements.get(statement);
      $graphics.fill(ps.getColor());
      $graphics.rect(_icon_offset, offset + _icon_offset, _icon_size, _icon_size);

      offset += _icon_bounds;
    }

    synchronized(_instances) {
      for(Drawable instance : _instances) {
        instance.draw($graphics);
      }
    }
  }

  public void handleMouseEvent(MouseEvent $event) {
    float x = $event.getX(),
          y = $event.getY();

    synchronized(_instances) {
      switch($event.getID()) {
      case MouseEvent.MOUSE_PRESSED:
        _dragstart = true;
        _drag_start_x = x;
        _drag_start_y = y;
        break;
      case MouseEvent.MOUSE_DRAGGED:
        //Require at least a 7 pixel move (helps on the phone for clicks).
        if(_dragstart && Math.abs(_drag_start_x - x) < 7 && Math.abs(_drag_start_y - y) < 7) {
          break;
        }
        
        //If we are about to start dragging an item from the toolbar.
        if(_dragstart && _drag_start_x > _icon_offset && _drag_start_x < _icon_offset + _icon_size) {
          //Stop dragging.
          _dragstart = false;
  
          //Helper values.
          int index = (int)Math.floor(_drag_start_y / _icon_bounds),
              pos = (int)Math.floor(_drag_start_y % _icon_bounds);
  
          //Get an array of statement types on the toolbar.
          Class _statement_types[] = _statements.keySet().toArray(new Class[0]);
  
          if(index < _statement_types.length && pos > _icon_offset && pos < _icon_offset + _icon_size) {
            _instance = _statements.get(_statement_types[index]).spawnInstance();
            _instances.add(_instance);
          } else {
            _instance = null;
          }
        } else if(_dragstart) {
          //Otherwise we're on the board, let's find exactly which element is hovered over.
          _dragstart = false;
          
          for(StatementInstance instance : _instances) {
            StatementInstance under = instance.instanceUnder(_drag_start_x, _drag_start_y);
  
            if(under != null) {
              _instance = under;
              break;
            }
          }
        }
  
        //If we are dragging an item.
        if(_instance != null) {
          _instance.setPos(x, y);
        }
        break;
      case MouseEvent.MOUSE_RELEASED:
        //This means we never started dragging in the first place, so handle a click.
        if(_dragstart) {
          for(StatementInstance instance : _instances) {
            StatementInstance under = instance.instanceUnder(_drag_start_x, _drag_start_y);
  
            if(under != null) {
              under.handleClick();
            }
          }
        }
        _dragstart = false;
  
        //If we are dropping a currently carried instance.
        if(_instance != null) {
          //Always remove the parent.
          _instance.setParent(null);
          
          //Check and see if the instance is over the toolbar (that means we want to delete it)
          if(x < _toolbar.x) {
            _instances.remove(_instance);
          } else {
            int i, len;
            //Remove the instance from the current list.
            _instances.remove(_instance);
  
            //Find where it belongs using it's y position.
            for(i = 0, len = _instances.size(); i < len; i++) {
              StatementInstance instance = _instances.get(i);
              PVector pos = instance.getPos();
              
              //We are getting the instance under the cursor (which could just be the same element!)
              StatementInstance nested = instance.instanceUnder(x, y);
              if(nested != null && nested.isNestable()) {
                Nestable nestable = (Nestable) nested;
                nestable.addChild(_instance);
                break;
              }
              
              if(pos.y > y) {
                _instance.setParent(null);
                _instances.add(i, _instance);
                break;
              }
            }
  
            //This means we reached the end of the list.
            if(i == len) {
              _instance.setParent(null);
              _instances.add(_instance);
            }
          }
  
          reshuffle();
          _instance = null;
        }
        break;
      }
    }
  }

  private void reshuffle() {
    int offset_y = StatementInstance.SPACING,
        offset_x = (int)_toolbar.x + StatementInstance.SPACING;

    synchronized(_instances) {
      for(StatementInstance instance : _instances) {
        instance.setPos(offset_x, offset_y);
        offset_y += StatementInstance.SPACING + instance.getHeight();
      }
    }
  }
}

