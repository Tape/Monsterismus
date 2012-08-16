package org.hardy.monsterismus;

import org.hardy.monsterismus.api.Drawable;
import org.hardy.monsterismus.blocks.Block;
import org.hardy.monsterismus.screens.Board;

import android.graphics.Point;
import android.util.FloatMath;

import processing.core.PVector;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 * 
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public class Player implements Drawable {
    // Size constants.
    public static final int SIZE = 34;
    public static PImage imgs[];

    // Movement constants
    public enum Movement {
        UP(1, "Up"), DOWN(2, "Down"), LEFT(3, "Left"), RIGHT(4, "Right");

        public int dir;
        private String label;

        Movement(int dir, String label) {
            this.dir = dir;
            this.label = label;
        }

        public int getDirection() {
            return dir;
        }

        public String getLabel() {
            return label;
        }

        public float getMovement() {
            return ((dir & 1) > 0) ? -1.0f : 1.0f;
        }

        public Movement next() {
            return Movement.get(dir % 4 + 1);
        }

        public static Movement get(int dir) {
            switch (dir) {
            case 1:
                return UP;
            case 2:
                return DOWN;
            case 3:
                return LEFT;
            case 4:
                return RIGHT;
            default:
                return UP;
            }
        }
    }

    // Drawing constants.
    private static final float ANIM_SPEED = 4.0f;

    // Instance variables.
    private boolean _is_moving = false;
    private PVector _start_position, _position, _prev_position;
    private Movement _movement;
    private float _move_direction_modifier;
    private int _score;
    private int _food;
    private int _treasure;

    public Player(final PVector $pos) {
        _start_position = $pos;
        _position = new PVector($pos.x, $pos.y);
        _score = 0;
        _food = 0;
        _treasure = 0;

        reset();
    }

    public void move(final Movement $movement) {
        // Prepare to validate the direction.
        _movement = $movement;
        _move_direction_modifier = _movement.getMovement();

        // Validate the direction.
        PVector newpos = new PVector(_position.x, _position.y), boardDims = Board.getInstance()
                .getDims();

        if (_movement.getDirection() < 3)
            newpos.y += _move_direction_modifier * Block.SIZE;
        else
            newpos.x += _move_direction_modifier * Block.SIZE;

        if (newpos.x < 0 || newpos.x >= boardDims.x || newpos.y < 0 || newpos.y >= boardDims.y)
            return;

        // Move is valid, store the previous position.
        _prev_position = new PVector(_position.x, _position.y);
        _is_moving = true;
    }

    public PVector getPosition() {
        return _position;
    }

    public Point getPoint() {
        return new Point((int) FloatMath.floor(_position.x / Block.SIZE),
                (int) FloatMath.floor(_position.y / Block.SIZE));
    }

    public void stopMoving() {
        _is_moving = false;
    }

    public void update(final float $dt) {
        if (!_is_moving)
            return;

        // Calculate the distance that will be moved.
        boolean vertical = _movement.getDirection() < 3;
        float distance = ($dt * SIZE) * ANIM_SPEED * _move_direction_modifier;

        // UP/DOWN movements.
        if (vertical)
            _position.y += distance;
        // LEFT/RIGHT movements.
        else
            _position.x += distance;

        if ((vertical && Math.abs(_prev_position.y - _position.y) > Block.SIZE)
                || Math.abs(_prev_position.x - _position.x) > Block.SIZE) {
            // Set the final position.
            if (vertical)
                _position.y = _prev_position.y + _move_direction_modifier * Block.SIZE;
            else
                _position.x = _prev_position.x + _move_direction_modifier * Block.SIZE;

            // Visit the block and stop moving.
            Board.getInstance().visitBlock(getPoint());
            _is_moving = false;
        }
    }

    public void draw(final PGraphics $graphics) {
        $graphics.pushMatrix();
        $graphics.translate(_position.x, _position.y);
        $graphics.image(imgs[_movement.dir], -5.5f, 0);
        $graphics.popMatrix();
    }

    public void reset() {
        _position = new PVector(_start_position.x, _start_position.y);

        _food = _treasure = _score = 0;
        _movement = Movement.DOWN;
    }

    public boolean isMoving() {
        return _is_moving;
    }

    /**
     * Get the player's treasure count
     */
    public int getTreasureCount() {
        return _treasure;
    }

    /**
     * Add's to the player's treasure count
     */
    public void addToTreasureCount(int val) {
        _treasure += val;
    }

    /**
     * Directly set the player's treasure count
     */
    public void setTreasureCount(int val) {
        _treasure = val;
    }

    /**
     * Get the player's score
     */
    public int getScore() {
        return _score;
    }

    /**
     * Add to the player's score
     */
    public void addToScore(int val) {
        _score += val;
    }

    /**
     * Set the player's score
     */
    public void setScore(int val) {
        _score = val;
    }

    /**
     * Get the player's food count
     */
    public int getFoodCount() {
        return _food;
    }

    /**
     * Add to the player's food count
     */
    public void addToFoodCount(int val) {
        _food += val;
    }

    /**
     * Set th player's food count
     */
    public void setFoodCount(int val) {
        _food = val;
    }
}
