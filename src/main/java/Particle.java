import processing.core.PVector;

public class Particle {

    public PVector coord;

    public float charge;
    public float mass;

    public Particle(PVector coord, float charge, float mass){
        this.coord = coord;
        this.charge = charge;
        this.mass = mass;
    }

}
