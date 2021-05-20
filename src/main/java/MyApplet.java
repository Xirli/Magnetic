import processing.core.PApplet;

import java.util.ArrayList;

public class MyApplet extends PApplet{

    public static final float drawRadiusOfParticle = 1000;

    ArrayList<Magnet> magnets;

    public void settings(){
        size(800, 600);
        MagnetGenerator.sceneX = 400;
        MagnetGenerator.sceneY = 300;
        magnets = MagnetGenerator.generateParaMagnetic();
    }
    public void setup() {
        frameRate(60);
    }


    public void draw(){

        Physics.update(magnets);

        background(0);
        Analizator.potential(magnets);
        for(Magnet mag : magnets) {
            mag.draw();
        }

        fill(0);
        text(frameRate, 10,10);
    }

    public void mouseDragged(){

    }

}