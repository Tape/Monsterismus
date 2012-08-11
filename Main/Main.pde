Player _player;
int _width, _height;
float _last_time, _dt;

void setup()
{
  size(Game.WIDTH, Game.HEIGHT);

  //Build the board and player.
  Game.board  = new Board(Game.BOARD_SIZE,Game.BOARD_SIZE);
  Game.editor = new Editor(new PVector(Game.WIDTH, Game.HEIGHT));
  Game.screen = Game.board;

  //Prep time calculations.
  _last_time = millis();

  FoodBlock.img = loadImage("sprites/food.gif");
  TreasureBlock.img = loadImage("sprites/treasure.gif");
  EmptyBlock.img = loadImage("sprites/Grass.png");

  Player.imgs = new PImage[5];
  Player.imgs[Player.Movement.UP.dir]    = loadImage("sprites/Up.png");
  Player.imgs[Player.Movement.DOWN.dir]  = loadImage("sprites/Down.png");
  Player.imgs[Player.Movement.LEFT.dir]  = loadImage("sprites/Left.png");
  Player.imgs[Player.Movement.RIGHT.dir] = loadImage("sprites/Right.png");
  
  Button.swap = loadImage("sprites/Switch.png");
  Button.execute = loadImage("sprites/Execute.png");
  Button.levelup = loadImage("sprites/LevelUp.png");
  Button.leveldown = loadImage("sprites/LevelDown.png");
  Button.reset = loadImage("sprites/Reset.png");
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
