package Model;

import View.GraphicObject;
import processing.core.PApplet;
//package magnet
public class MouseMagnet extends Magnet{

    PApplet sketch;

    public MouseMagnet(Particle[] particle, PApplet sketch, GraphicObject graphic) {
        super(particle, graphic);
        this.sketch = sketch;
    }

    public void run(){
        setCoord(sketch.mouseX, sketch.mouseY, getCoord().z);
        setSpeed(0,0,0);
    }
}
