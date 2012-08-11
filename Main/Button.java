
import processing.core.PGraphics;

public abstract class Button {
  public int x, y, w, h;
  public boolean clicked;
  public Runnable run;

  public Button(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  public boolean isClicked(int x, int y) {
    clicked = (x < this.w + this.x && x > this.x && y < this.h + this.y && y > this.y);
    if(clicked && run != null){
      run.run();
    }
    return clicked;
  }
  public void release() {
    clicked = false;
  }
  public void event(Runnable r) {
    this.run = r;
  }
  public void draw(final PGraphics $graphics) {}
}
