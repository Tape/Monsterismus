import processing.core.PVector;
import processing.core.PGraphics;

public class Player implements Drawable
{
  //Size constants.
  public static final int BLOCK_SIZE = 20;
  
  //Movement constants
  public static final int MOVE_UP = 1;
  public static final int MOVE_DOWN = 2;
  public static final int MOVE_LEFT = 3;
  public static final int MOVE_RIGHT = 4;
  
  //Drawing constants.
  private static final int FILL_COLOR = 0xFF000000;
  private static final float ANIM_SPEED = 4.0f;
  
  //Instance variables.
  private boolean _is_moving = false;
  private PVector _position, _prev_position;
  private int _move_direction;
  
  public Player(final PVector $pos)
  {
    _position = $pos;
  }
  
  public void move(final int $direction)
  {
    _prev_position = new PVector(_position.x, _position.y);
    _move_direction = $direction;
    _is_moving = true;
  }
  
  private void update(final float $dt)
  {
    if( ! _is_moving) return;
    
    float distance = ($dt * BLOCK_SIZE) * ANIM_SPEED,
          dir = ((_move_direction & 1) > 0) ? -1 : 1;
    
    switch(_move_direction)
    {
    case MOVE_UP:
    case MOVE_DOWN:
      _position.y += distance * dir;
      if(Math.abs(_prev_position.y - _position.y) > Board.BLOCK_SIZE)
      {
        _position.y = _prev_position.y + dir * Board.BLOCK_SIZE;
        _is_moving = false;
      }
      break;
    case MOVE_LEFT:
    case MOVE_RIGHT:
      _position.x += distance * dir;
      if(Math.abs(_prev_position.x - _position.x) > Board.BLOCK_SIZE)
      {
        _position.x = _prev_position.x + dir * Board.BLOCK_SIZE;
        _is_moving = false;
      }
      break;
    }
  }
  
  public void draw(final PGraphics $graphics, final float $dt)
  {
    update($dt);
    
    $graphics.fill(FILL_COLOR);
    $graphics.rect(_position.x, _position.y, BLOCK_SIZE, BLOCK_SIZE);
  }
  
  public boolean isMoving()
  {
    return _is_moving;
  }
}
