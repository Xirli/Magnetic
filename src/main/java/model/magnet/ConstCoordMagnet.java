package model.magnet;

import view.GraphicObject;

public class ConstCoordMagnet extends Magnet{
    public ConstCoordMagnet(Particle[] particle, GraphicObject graphic) {
        super(particle, graphic);
    }

    public void run() {
        //coord is const
        rotate(getVelocity());
    }
}
