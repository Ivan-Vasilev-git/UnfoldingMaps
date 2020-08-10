package demos;

import processing.core.PApplet;

public class ProcessingTest extends PApplet {
  public void setup()
  {
    size(400, 400);
    background(200, 200, 200);

  }

  @Override
  public void draw() {
    background(0);
    fill(255, 0, 0);
    ellipse(200, 200, 200, 200);
  }

  public static void main(String... args) {
    ProcessingTest pt = new ProcessingTest();
    PApplet.runSketch(new String[]{"ProcessingTest"}, pt);
  }
}
