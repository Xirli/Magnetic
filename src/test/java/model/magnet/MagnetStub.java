package model.magnet;

import processing.core.PVector;
import view.GraphicObject;

public class MagnetStub extends Magnet{
    public MagnetStub(Particle[] particle, GraphicObject graphic) {
        super(particle, graphic);
    }

    public int velocityCount = 0;
    public int speedCount = 0;
    public int runCount = 0;
    public void setVelocity(PVector set) {
        velocityCount++;
    }
    public void setSpeed(PVector set){
        speedCount++;
    }
    public void run(){runCount++;}
}
