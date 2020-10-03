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
    float momentOfInertia;

    public Magnet(Particle[] particle){
        this.particle = particle;

        coord = new PVector(0,0,0);
        speed = new PVector(0,0,0);

        for(Particle part : particle){
            mass += part.mass;
            coord.add(PVector.mult(part.coord, part.mass));
        }
        coord.div(mass);

        for(Particle part : particle){
            part.coord.sub(coord);
            momentOfInertia += part.mass * part.coord.magSq();
        }
    }

    public static Magnet newMonopole(float charge, float particleMass){
        return newMultipole(1, 0, charge, particleMass);
    }

    public static Magnet newDipole(float radius, float charge, float particleMass){
        return newMultipole(2, radius, charge, particleMass);
    }

    public static Magnet newMultipole(int countOfPole, float radius, float charge, float particleMass){
        Particle[] particle = new Particle[countOfPole];

        for(int i = 0; i < countOfPole; i++){
            particle[i] = new Particle(
                    PVector.fromAngle((float) (2*Math.PI * i/countOfPole)).mult(radius),
                    -(charge *=-1),
                    particleMass
            );
        }

        return new Magnet(particle);
    }

}
