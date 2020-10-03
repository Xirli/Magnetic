import processing.core.PApplet;
import processing.core.PVector;

public class Magnet {

    private static final PApplet SKETCH = Main.SKETCH;

    PVector coord;
    PVector speed;

    float angle;
    float velocity;

    Particle[] particle;
    float mass;

    public Magnet(Particle[] particle, float mass, float angle, float velocity){
        this.particle = particle;
        this.mass = mass;
        this.angle = angle;
        this.velocity = velocity;
    }

    public static Magnet newMonopole(float charge, float mass){
        return newMultipole(1, 0, charge, mass);
    }

    public static Magnet newDipole(float radius, float charge, float mass){
        return newMultipole(2, radius, charge, mass);
    }

    public static Magnet newMultipole(int countOfPole, float radius, float charge, float mass){
        Particle[] particle = new Particle[countOfPole];

        for(int i = 0; i < countOfPole; i++){
            particle[i] = new Particle(
                    PVector.fromAngle((float) (2*Math.PI * i/countOfPole)).mult(radius),
                    -(charge *=-1),
                    mass
            );
        }

        return new Magnet(particle, countOfPole * mass, 0, 0);
    }

}
