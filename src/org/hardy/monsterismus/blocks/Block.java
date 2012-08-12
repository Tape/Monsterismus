package org.hardy.monsterismus.blocks;

import org.hardy.monsterismus.Player;
import org.hardy.monsterismus.api.Drawable;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public abstract class Block implements Drawable
{
  /**
   * The type of block. This is mainly used for the BlockFactory
   *
   * @see BlockFactory
   */
  public enum Type {
    EMPTY(0),
    FOOD(1),
    TREASURE(2);

    public final int value;
    /**
     * @param v The value of the type
     */
    Type(int v) {
      this.value = v;
    }

    /**
     * Get the specific Type enum from an integer
     *
     * @param v The value that you want to get
     * @return The type of food
     */
    public static Type get(int v) {
      switch(v) {
        case 1:  return FOOD;
        case 2:  return TREASURE;
        default: return EMPTY;
      }
    }
  }

  public static final int SIZE = 48;
  public static PImage img;
  protected PVector pos;

  public Block(final PVector $pos)
  {
    pos = $pos;
    reset();
  }
  
  public void draw(final PGraphics $graphics)
  {
    $graphics.pushMatrix();
    $graphics.translate(pos.x, pos.y);
    $graphics.image(img, 0, 0);
  }

  public abstract void doAction(Player p);
  public abstract boolean claimed();
  public abstract void reset();
}

