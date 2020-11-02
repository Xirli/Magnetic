import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class MyApplet extends PApplet{

    ArrayList<Magnet> magnets;

    public void settings(){
        size(1000, 700);
        MagnetGenerator.sceneX = 500;
        MagnetGenerator.sceneY = 350;
        magnets = MagnetGenerator.generateParaMagnetic();
        //magnets = MagnetGenerator.generateMonopole(50);
    }

    public void draw(){
        background(0);
        //translate((float) width/2, (float) height/2);
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

        //translate((float) -width/2, (float) -height/2);
    }

    public void mouseDragged(){

    }

}