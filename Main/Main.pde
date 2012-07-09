Board _board;
Player _player;
int _width, _height;
float _last_time, _dt;

void setup()
{
  //Prep the board size.
  int sx = 10, sy = 10;
  _width = sx * Board.BLOCK_SIZE;
  _height = sy * Board.BLOCK_SIZE;
  size(_width, _height);
  
  //Build the board and player.
  _board = Board.getInstance(sx, sy);
  int offset = (Board.BLOCK_SIZE - Player.BLOCK_SIZE) / 2;
  _player = new Player(new PVector(offset, offset));
  
  //Prep time calculations.
  _last_time = millis();
}

void draw()
{
  //Calculate the time delta.
  float temp = millis();
  _dt = (temp - _last_time) / 1000;
  _last_time = temp;
  
  //Draw everything.
  _board.draw(g, _dt);
  _player.draw(g, _dt);
}

void keyPressed()
{
  if(key == CODED && ! _player.isMoving())
  {
    switch(keyCode)
    {
    case UP: _player.move(Player.MOVE_UP); return;
    case DOWN: _player.move(Player.MOVE_DOWN); return;
    case LEFT: _player.move(Player.MOVE_LEFT); return;
    case RIGHT: _player.move(Player.MOVE_RIGHT); return;
    }
  }
}
