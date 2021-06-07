package Model;

import View.GraphicObject;
import processing.core.PVector;

//package magnet
public class ConstCoordMagnet extends Magnet{
    public ConstCoordMagnet(Particle[] particle, GraphicObject graphic) {
        super(particle, graphic);
    }

    public void run() {
        //coord is const
        setAngle(PVector.add(getAngle(), getVelocity()));
    }
}
