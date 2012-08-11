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
  _width = sx * Block.SIZE;
  _height = sy * Block.SIZE;
  size(_width, _height + 100);

  //Build the board and player.
  _screen = _board = Board.getInstance(sx, sy);
  _editor = new Editor(new PVector(_width, _height));
  int offset = (Block.SIZE - Player.SIZE) / 2;
  _player = new Player(new PVector(offset, offset));
  _board.setPlayer(_player);
  _board.setEditor(_editor);

  //Prep time calculations.
  _last_time = millis();
  
  FoodBlock.img = loadImage("food.gif");
  TreasureBlock.img = loadImage("treasure.gif");
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

void mouseDragged(MouseEvent $event)  { _screen.handleMouseEvent($event); }
void mouseMoved(MouseEvent $event)    { _screen.handleMouseEvent($event); }
void mousePressed(MouseEvent $event)  { _screen.handleMouseEvent($event); }
void mouseReleased(MouseEvent $event) { _screen.handleMouseEvent($event); }

void keyPressed() {
  if(key != CODED) {
    switch(key) {
      // Remove these and add the button functionality
    case 's':
    case 'S':
      if(_screen instanceof Board) {
        _board.setRunning(false);
        _screen = _editor;
      } else {
        _screen = _board;
      }
      break;
    case 'e':
    case 'E':
      if(_screen instanceof Board) {
        _board.toggleRunning();
      }
    }
  }
}

