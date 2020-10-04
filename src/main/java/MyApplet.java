import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class MyApplet extends PApplet{

    ArrayList<Magnet> magnets = new ArrayList<>();

    public void settings(){
        size(500, 500);

        for(int i = 0; i < 10; i++) {
            Magnet m = Magnet.newDipole(5, 1, 1);
            m.setCoord(PVector.random2D().mult(100).add(0,0,100));
            m.setVelocity(0, (float) 0, (float) 0.0);
            magnets.add(m);
        }
        for(int i = 0; i < 10; i++) {
            Magnet m = Magnet.newMonopole(1, 1);
            m.setCoord(PVector.random2D().mult(100).add(0,0,100));
            m.setVelocity(0, (float) 0, (float) 0.0);
            magnets.add(m);
        }
    }

    public void draw(){
        background(0);
        translate(width/2, height/2);
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
        fill(255);
        text(frameRate, -200,-200);
    }

    public void mousePressed(){

    }

}