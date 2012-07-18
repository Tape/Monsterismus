import java.util.List;
import java.util.ArrayList;

import processing.core.PVector;
import processing.core.PGraphics;

import java.awt.event.MouseEvent;

public class Board implements Screen
{
  //Constants
  public static final int BLOCK_SIZE = 48;
  
  //Instance variables.
  private Block[][] _blocks;
  private int _size_x, _size_y;
  private PVector _dims;
  private static Board _board;
  private List<Drawable> _entities = new ArrayList<Drawable>();
  
  private Board(final int $sx, final int $sy)
  {
    //Store the size of the board.
    _size_x = $sx;
    _size_y = $sy;
    _dims = new PVector($sx * BLOCK_SIZE, $sy * BLOCK_SIZE);
    
    //Initialize the block array.
    _blocks = new Block[_size_x][_size_y];
    
    //Initialize board array.
    for(int y = 0; y < _size_y; y++)
    {
      for(int x = 0; x < _size_x; x++)
      {
        _blocks[x][y] = BlockFactory.create(new PVector(x * BLOCK_SIZE, y * BLOCK_SIZE), BlockType.EMPTY);
      }
    }
  }
  
  public static Board getInstance()
  {
    if(_board == null)
      throw new RuntimeException("Board has not been created yet");
    
    return _board;
  }
  
  public static Board getInstance(int $sx, int $sy)
  {
    if(_board == null)
      return (_board = new Board($sx, $sy));
    
    return _board;
  }
  
  public void addEntity(Drawable $entity)
  {
    _entities.add($entity);
  }
  
  public void update(final float $dt)
  {
    //Update each entity associated with the board.
    for(Drawable entity : _entities)
    {
      entity.update($dt);
    }
  }
  
  public void draw(final PGraphics $graphics)
  {
    //Set up the graphics mode to correcty draw the blocks
    //and draw each block.
    $graphics.rectMode(PGraphics.CORNER);
    for(int y = 0; y < _size_y; y++)
    {
      for(int x = 0; x < _size_x; x++)
      {
        _blocks[x][y].draw($graphics);
      }
    }
    
    //Draw each entity associated with the board.
    for(Drawable entity : _entities)
    {
      entity.draw($graphics);
    }
  }
  
  public final PVector getDims()
  {
    return new PVector(_dims.x, _dims.y);
  }
  
  public void visitBlock(final PVector $position)
  {
    int x = (int) Math.floor($position.x / BLOCK_SIZE),
        y = (int) Math.floor($position.y / BLOCK_SIZE);
    
    _blocks[x][y].doAction();
  }
  
  public void handleMouseEvent(MouseEvent $event)
  {
  }
}

