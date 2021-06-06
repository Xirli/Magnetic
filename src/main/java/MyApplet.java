import processing.core.PApplet;

import java.util.ArrayList;

    public class MyApplet extends PApplet{

    public static final float drawRadiusOfParticle = 1000;

    ArrayList<Magnet> magnets;

    MyApplet(ArrayList<Magnet> magnets){
        this.magnets = magnets;
    }

    public void settings(){
        size(800, 600);
    }
    public void setup() {
        frameRate(60);
    }


    public void draw(){
        background(0);
        Analizator.potential(this, magnets);
        for(Magnet mag : magnets) {
            mag.draw(this);
        }
        fill(0);
        text(frameRate, 10,10);
    }

    public void mouseDragged(){

    }

}