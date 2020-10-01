import processing.core.PApplet;

public class MyApplet extends PApplet{

    public void settings(){
        size(displayWidth, displayHeight);
    }

    public void draw(){
        ellipse(mouseX, mouseY, 50, 50);
    }

    public void mousePressed(){
        background(255);
    }

}