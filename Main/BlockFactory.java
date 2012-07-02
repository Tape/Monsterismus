import processing.core.PVector;
import processing.core.PGraphics;

public class BlockFactory
{
  public static Block create(final PVector $pos, final BlockType $blockType)
  {
    switch($blockType)
    {
    case FOOD:
      return new FoodBlock($pos);
    case EMPTY:
    default:
      return new EmptyBlock($pos);
    }
  }
}

