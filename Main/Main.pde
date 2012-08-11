Player _player;
int _width, _height;
float _last_time, _dt;

void setup()
{
  //Prep the board size.
  int sx = 10, sy = 10;
  _width = sx * Block.SIZE;
  _height = sy * Block.SIZE;
  size(_width, _height + 100);

  //Build the board and player.
  Game.board  = new Board(sx,sy);
  Game.editor = new Editor(new PVector(_width, _height));
  Game.screen = Game.board;

  //Prep time calculations.
  _last_time = millis();

  FoodBlock.img = loadImage("sprites/food.gif");
  TreasureBlock.img = loadImage("sprites/treasure.gif");
  
}

void draw()
{
  //Calculate the time delta.
  float temp = millis();
  _dt = (temp - _last_time) / 1000;
  _last_time = temp;

  //Update the core screens.
  Game.board.update(_dt);
  Game.editor.update(_dt);

  //Draw only the current selected screen.
  Game.screen.draw(g);
}

void mouseDragged(MouseEvent $event)  { Game.handleMouseEvent($event); }
void mouseMoved(MouseEvent $event)    { Game.handleMouseEvent($event); }
void mousePressed(MouseEvent $event)  { Game.handleMouseEvent($event); }
void mouseReleased(MouseEvent $event) { Game.handleMouseEvent($event); }
