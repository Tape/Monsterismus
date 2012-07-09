import processing.core.PVector;
import processing.core.PGraphics;

public class Player implements Drawable
{
  //Size constants.
  public static final int BLOCK_SIZE = 34;
  
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
  private float _move_direction_modifier;
  
  public Player(final PVector $pos)
  {
    _position = $pos;
  }
  
  public void move(final int $direction)
  { 
    //Prepare to validate the direction.
    _move_direction = $direction;
    _move_direction_modifier = ((_move_direction & 1) > 0) ? -1.0f : 1.0f;
    
    //Validate the direction.
    PVector newpos = new PVector(_position.x, _position.y),
            boardDims = Board.getInstance().getDims();
    
    if(_move_direction < 3)
      newpos.y += _move_direction_modifier * Board.BLOCK_SIZE;
    else
      newpos.x += _move_direction_modifier * Board.BLOCK_SIZE;
    
    if(newpos.x < 0 || newpos.x >= boardDims.x || newpos.y < 0 || newpos.y >= boardDims.y)
      return;
    
    //Move is valid, store the previous position.
    _prev_position = new PVector(_position.x, _position.y);
    _is_moving = true;
  }
  
  private void update(final float $dt)
  {
    if( ! _is_moving) return;
    
    //Calculate the distance that will be moved.
    boolean vertical = _move_direction < 3;
    float distance = ($dt * BLOCK_SIZE) * ANIM_SPEED * _move_direction_modifier;
    
    //UP/DOWN movements.
    if(vertical)
      _position.y += distance;
    //LEFT/RIGHT movements.
    else
      _position.x += distance;
    
    if((vertical && Math.abs(_prev_position.y - _position.y) > Board.BLOCK_SIZE)
      || Math.abs(_prev_position.x - _position.x) > Board.BLOCK_SIZE)
    {
      //Set the final position.
      if(vertical)
        _position.y = _prev_position.y + _move_direction_modifier * Board.BLOCK_SIZE;
      else
        _position.x = _prev_position.x + _move_direction_modifier * Board.BLOCK_SIZE;
      
      //Visit the block and stop moving.
      Board.getInstance().visitBlock(_position);
      _is_moving = false;
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

