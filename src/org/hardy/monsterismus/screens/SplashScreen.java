package org.hardy.monsterismus.screens;

import org.hardy.monsterismus.Button;
import org.hardy.monsterismus.Game;
import org.hardy.monsterismus.api.Screen;

import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;
import android.view.MotionEvent;

public class SplashScreen implements Screen {
    private Button _tutorial_button, _play_button;
    public static PFont font;
    public static PImage logo;

    public SplashScreen() {
        _tutorial_button = new Button(72, 600, 336, 77) {
            public void draw(PGraphics $graphics) {
                $graphics.pushMatrix();
                $graphics.translate(this.x, this.y);
                $graphics.image(Button.tutorial, 0, 0);
                $graphics.popMatrix();
            }
        };
        _tutorial_button.event(new Runnable() {
            public void run() {
                Game.switchScreen(Game.TUTORIAL);
            }
        });

        _play_button = new Button(100, 700, 279, 77) {
            public void draw(PGraphics $graphics) {
                $graphics.pushMatrix();
                $graphics.translate(this.x, this.y);
                $graphics.image(Button.play, 0, 0);
                $graphics.popMatrix();
            }
        };
        _play_button.event(new Runnable() {
            public void run() {
                Game.switchScreen(Game.BOARD);
            }
        });
    }

    public void update(float $dt) {

    }

    public void draw(PGraphics $graphics) {
        // Draw background + logo.
        $graphics.fill(0xFF000000);
        $graphics.rect(0, 0, Game.WIDTH, Game.HEIGHT);
        $graphics.image(logo, 10, 30);

        // Draw credits.
        $graphics.fill(0xFFFFFFFF);
        $graphics.textFont(font);
        $graphics.text("Concept by", 50, 195);
        $graphics.text("Developed by", 50, 300);
        $graphics.fill(0xFFAAAAAA);
        $graphics.text("Matthew Berland", 75, 240);
        $graphics.text("David Kosub", 75, 345);
        $graphics.text("Jeff Wooldridge", 75, 385);
        $graphics.text("Trevor Vardeman", 75, 425);
        $graphics.text("Matthew Johnston", 75, 465);
        $graphics.text("Carlos Martinez", 75, 505);

        // Draw buttons.
        //_tutorial_button.draw($graphics);
        _play_button.draw($graphics);
    }

    public void handleMotionEvent(MotionEvent $event) {
        int x = (int) $event.getX(), y = (int) $event.getY();
        switch ($event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            //_tutorial_button.isClicked(x, y);
            _play_button.isClicked(x, y);
            break;
        case MotionEvent.ACTION_UP:
            //_tutorial_button.release();
            _play_button.release();
            break;
        }
    }

}
