import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class MyApplet extends PApplet{

    ArrayList<Magnet> magnets = MagnetGenerator.generateDipole();
    public void settings(){
        size(800, 800);
    }

    public void draw(){
        background(0);
        translate((float) width/2, (float) height/2);
        for(Magnet mag : magnets) {
            mag.draw();
            mag.display();
        }
        for(Magnet mag1 : magnets) {
            for(Magnet mag2 : magnets) {
                Magnet.gravity(mag1, mag2);
                if(mag1 == mag2)break;
            }
        }

        translate((float) -width/2, (float) -height/2);
    }

    public void mouseDragged(){
        magnets.get(0).setCoord(mouseX-(float)width/2, mouseY-(float)height/2, 100);
    }

}