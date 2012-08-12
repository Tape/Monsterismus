package org.hardy.monsterismus.blocks;

import processing.core.PVector;

/**
 *
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public class BlockFactory
{
  public static Block create(final PVector $pos, final Block.Type $blockType)
  {
    switch($blockType)
    {
    case FOOD:
      return new FoodBlock($pos);
    case TREASURE:
      return new TreasureBlock($pos);
    default:
      return new EmptyBlock($pos);
    }
  }
}

