import processing.core.PVector;
import processing.core.PGraphics;

/**
 *
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public class Player implements Drawable {
  //Size constants.
  public static final int SIZE = 34;

  //Movement constants
  public enum Movement
  {
    UP(1, "Up"),
    DOWN(2, "Down"),
    LEFT(3, "Left"),
    RIGHT(4, "Right");
    
    private int _dir;
    private String _label;
    
    Movement(int $dir, String $label)
    {
      _dir = $dir;
      _label = $label;
    }
    
    public int getDirection()
    {
      return _dir;
    }
    
    public String getLabel()
    {
      return _label;
    }
    
    public float getMovement()
    {
      return ((_dir & 1) > 0) ? -1.0f : 1.0f;
    }
    
    public Movement next()
    {
      return Movement.get(_dir % 4 + 1);
    }
    
    public static Movement get(int $dir)
    {
      switch($dir)
      {
      case 1: return UP;
      case 2: return DOWN;
      case 3: return LEFT;
      case 4: return RIGHT;
      default: return UP;
      }
    }
  }

  //Drawing constants.
  private static final int FILL_COLOR = 0xFF000000;
  private static final float ANIM_SPEED = 4.0f;

  //Instance variables.
  private boolean _is_moving = false;
  private PVector _start_position, _position, _prev_position;
  private Movement _movement;
  private float _move_direction_modifier;

  public Player(final PVector $pos) {
    _start_position = $pos;
    _position = new PVector($pos.x, $pos.y);
  }

  public void move(final Movement $movement) {
    //Prepare to validate the direction.
    _movement = $movement;
    _move_direction_modifier = _movement.getMovement();

    //Validate the direction.
    PVector newpos = new PVector(_position.x, _position.y),
            boardDims = Board.getInstance().getDims();

    if(_movement.getDirection() < 3)
      newpos.y += _move_direction_modifier * Block.SIZE;
    else
      newpos.x += _move_direction_modifier * Block.SIZE;

    if(newpos.x < 0 || newpos.x >= boardDims.x || newpos.y < 0 || newpos.y >= boardDims.y)
      return;

    //Move is valid, store the previous position.
    _prev_position = new PVector(_position.x, _position.y);
    _is_moving = true;
  }

  public void update(final float $dt) {
    if( ! _is_moving) return;

    //Calculate the distance that will be moved.
    boolean vertical = _movement.getDirection() < 3;
    float distance = ($dt * SIZE) * ANIM_SPEED * _move_direction_modifier;

    //UP/DOWN movements.
    if(vertical)
      _position.y += distance;
    //LEFT/RIGHT movements.
    else
      _position.x += distance;

    if((vertical && Math.abs(_prev_position.y - _position.y) > Block.SIZE)
      || Math.abs(_prev_position.x - _position.x) > Block.SIZE) {
      //Set the final position.
      if(vertical)
        _position.y = _prev_position.y + _move_direction_modifier * Block.SIZE;
      else
        _position.x = _prev_position.x + _move_direction_modifier * Block.SIZE;

      //Visit the block and stop moving.
      Board.getInstance().visitBlock(_position);
      _is_moving = false;
    }
  }

  public void draw(final PGraphics $graphics) {
    $graphics.fill(FILL_COLOR);
    $graphics.rect(_position.x, _position.y, SIZE, SIZE);
  }
  
  public void reset()
  {
    _position = _start_position;
  }

  public boolean isMoving() {
    return _is_moving;
  }
}

