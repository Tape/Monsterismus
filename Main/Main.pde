Board board;
PGraphics graphics;
int width, height;

void setup()
{
  int sx = 7, sy = 7;
  width = sx * board.BLOCK_SIZE;
  height = sy * board.BLOCK_SIZE;
  
  board = new Board(sx, sy);
  size(width, height);
  graphics = createGraphics(width, height, P2D);
}

void draw()
{
  board.draw(g);
}
