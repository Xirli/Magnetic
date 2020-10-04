import processing.core.PApplet;
import processing.core.PVector;

public class Magnet {

    private static final PApplet SKETCH = Main.SKETCH;

    private final PVector coord;
    private final PVector speed;

    private final PVector angle;
    private final PVector velocity;

    private final Particle[] particle;
    private float mass;
    private float momentOfInertia;

    public Magnet(Particle[] particle){
        this.particle = particle;
        
        coord = new PVector(0,0,0);
        speed = new PVector(0,0,0);
        angle = new PVector(0,0,0);
        velocity = new PVector(0,0,0);

        PVector coordOfCenter = new PVector(0,0,0);

        for(Particle part : particle){
            mass += part.mass;
            coordOfCenter.add(PVector.mult(part.coord, part.mass));
        }
        coordOfCenter.div(mass);

        for(Particle part : particle){
            part.coord.sub(coordOfCenter);
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

    public void initCoord(float x, float y, float z){
        coord.set(x, y, z);
    }
    public void initSpeed(float x, float y, float z){
        speed.set(x, y, z);
    }
    public void initAngle(float x, float y, float z){
        angle.set(x, y, z);
    }
    public void initVelocity(float x, float y, float z){
        velocity.set(x, y, z);
    }

    public Particle[] getParticle(){
        return particle;
    }
}
