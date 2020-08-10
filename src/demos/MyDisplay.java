package demos;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * A class to illustrate some use of the PApplet class
 * Used in module 3 of the UC San Diego MOOC Object Oriented Programming in Java
 *
 * @author UC San Diego Intermediate Software Development MOOC team
 */
public class MyDisplay extends PApplet {
  private PImage webImage;
  private int[] color = new int[2];

  public void setup() {
    size(400, 400);
    String url = "palmTrees.jpg";
    webImage = loadImage(url, "jpg");
//    background(200, 200, 200);

  }

  public void draw() {
    //center's coordinates
//    float centerX = (float) (width / 2.0);
//    float centerY = (float) (height / 2.0);
    background(0);
    //width will be calculating automatically for save the original ratio of the image
    webImage.resize(0, height);
    image(webImage, 0, 0);
    color = sunColorsSec(second());
    fill(color[0], color[1], 0);
    ellipse(width / 4, height / 5, width / 4, height / 5);

    // Just a smiling face which is always in the center
//    fill(255, 255, 0);
//    ellipse(centerX, centerY, getWidth() - 10, getHeight() - 10);
//    ellipse(200, 200, 390, 390);
//    fill(0, 0, 0);
//    ellipse(centerX - 80, centerY - 70, 50, 70);
//    ellipse(centerX + 80, centerY - 70, 50, 70);
//
//    noFill();
//    arc(centerX, centerY + 80, 75, 75, 0, PI);
  }

  private int[] sunColorsSec(int second) {
    int[] rgb = new int[2];
    float diffFrom30 = Math.abs(30 - second);
    float ratio = diffFrom30 / 30;
    rgb[0] = 255;
    rgb[1] = (int) (255 * ratio);
    return rgb;
  }

}
