import processing.core.PApplet;

public class Main extends PApplet{

    public void settings(){
        size(displayWidth, displayHeight);
    }

    public void draw(){
        ellipse(mouseX, mouseY, 50, 50);
    }

    public void mousePressed(){
        background(255);
    }

    public static void main(String[] args){
        String[] processingArgs = {"MySketch"};
        Main mySketch = new Main();
        PApplet.runSketch(processingArgs, mySketch);
    }
}