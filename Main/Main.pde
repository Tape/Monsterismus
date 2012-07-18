Board _board;
Editor _editor;
Player _player;
int _width, _height;
float _last_time, _dt;
Screen _screen;

void setup()
{
  //Prep the board size.
  int sx = 10, sy = 10;
  _width = sx * Board.BLOCK_SIZE;
  _height = sy * Board.BLOCK_SIZE;
  size(_width, _height);
  
  //Build the board and player.
  _screen = _board = Board.getInstance(sx, sy);
  _editor = new Editor(new PVector(_width, _height));
  int offset = (Board.BLOCK_SIZE - Player.BLOCK_SIZE) / 2;
  _player = new Player(new PVector(offset, offset));
  _board.addEntity(_player);
  
  //Prep time calculations.
  _last_time = millis();
}

void draw()
{
  //Calculate the time delta.
  float temp = millis();
  _dt = (temp - _last_time) / 1000;
  _last_time = temp;
  
  //Update the core screens.
  _board.update(_dt);
  _editor.update(_dt);
  
  //Draw only the current selected screen.
  _screen.draw(g);
}

void mouseDragged() { _screen.handleMouseEvent(mouseEvent); }
void mouseMoved() { _screen.handleMouseEvent(mouseEvent); }
void mousePressed() { _screen.handleMouseEvent(mouseEvent); }
void mouseReleased() { _screen.handleMouseEvent(mouseEvent); }

void keyPressed()
{
  if(key == CODED && _screen instanceof Board && ! _player.isMoving())
  {
    switch(keyCode)
    {
    case UP: _player.move(Player.MOVE_UP); return;
    case DOWN: _player.move(Player.MOVE_DOWN); return;
    case LEFT: _player.move(Player.MOVE_LEFT); return;
    case RIGHT: _player.move(Player.MOVE_RIGHT); return;
    }
  }
  else
  {
    switch(key)
    {
    case 's':
    case 'S':
      if(_screen instanceof Board)
        _screen = _editor;
      else
        _screen = _board;
      break;
    }
  }
}

