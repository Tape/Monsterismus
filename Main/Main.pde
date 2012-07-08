Board _board;
int _width, _height;
float _last_time, _dt;

void setup()
{
  //Prep the board size.
  int sx = 7, sy = 7;
  _width = sx * Board.BLOCK_SIZE;
  _height = sy * Board.BLOCK_SIZE;
  size(_width, _height);
  
  //Build the board.
  _board = new Board(sx, sy);
  
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
}

