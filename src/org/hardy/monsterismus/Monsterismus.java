package org.hardy.monsterismus;

import org.hardy.monsterismus.blocks.*;
import org.hardy.monsterismus.screens.*;
import org.hardy.monsterismus.statements.IfStatement;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;

import processing.core.*;

/**
 * 
 * @author David Kosub
 * @author Jeffery Wooldridge
 * @author Matthew A. Johnston
 * @author Trevor Vardeman
 * @author Carlos Martinez
 */
public class Monsterismus extends PApplet {
    private float _last_time, _dt;
    private BitmapFactory.Options _options;

    public void setup() {
        // Configure the bitmap factory sizing...
        _options = new BitmapFactory.Options();
        _options.inScaled = false;

        // Build the board and player.
        Game.board = new Board(Game.BOARD_SIZE, Game.BOARD_SIZE);
        Game.editor = new Editor(new PVector(Game.WIDTH, Game.HEIGHT));
        Game.screen = Game.splash = new SplashScreen();
        Game.font = createDefaultFont(16);

        // Prep time calculations.
        _last_time = millis();

        SplashScreen.logo = loadImage(org.hardy.monsterismus.R.drawable.logo);
        SplashScreen.font = createDefaultFont(20);

        Block.img = loadImage(org.hardy.monsterismus.R.drawable.grass);
        FoodBlock.img = loadImage(org.hardy.monsterismus.R.drawable.food);
        TreasureBlock.img = loadImage(org.hardy.monsterismus.R.drawable.treasure);

        Player.imgs = new PImage[5];
        Player.imgs[Player.Movement.UP.dir] = loadImage(org.hardy.monsterismus.R.drawable.up);
        Player.imgs[Player.Movement.DOWN.dir] = loadImage(org.hardy.monsterismus.R.drawable.down);
        Player.imgs[Player.Movement.LEFT.dir] = loadImage(org.hardy.monsterismus.R.drawable.left);
        Player.imgs[Player.Movement.RIGHT.dir] = loadImage(org.hardy.monsterismus.R.drawable.right);

        Button.swap = loadImage(org.hardy.monsterismus.R.drawable.switchbtn);
        Button.execute = loadImage(org.hardy.monsterismus.R.drawable.execute);
        Button.levelup = loadImage(org.hardy.monsterismus.R.drawable.nextbtn);
        Button.leveldown = loadImage(org.hardy.monsterismus.R.drawable.lastbtn);
        Button.reset = loadImage(org.hardy.monsterismus.R.drawable.reset);
        Button.play = loadImage(org.hardy.monsterismus.R.drawable.startbtn);
        Button.tutorial = loadImage(org.hardy.monsterismus.R.drawable.tutorialbtn);
    }

    public int sketchWidth() {
        return Game.WIDTH;
    }

    public int sketchHeight() {
        return Game.HEIGHT;
    }

    public void draw() {
        // Calculate the time delta.
        float temp = millis();
        _dt = (temp - _last_time) / 1000;
        _last_time = temp;

        // Update the core screens.
        Game.board.update(_dt);
        Game.editor.update(_dt);

        // Draw only the current selected screen.
        Game.screen.draw(g);
    }

    public boolean surfaceTouchEvent(MotionEvent $event) {
        Game.handleMotionEvent($event);
        return super.surfaceTouchEvent($event);
    }

    public PImage loadImage(final int $resourceID) {
        Bitmap image = BitmapFactory.decodeResource(getResources(), $resourceID, _options);
        return new PImage(image);
    }
}
