import processing.core.PApplet;
import processing.core.PVector;

import java.util.Arrays;

public class Magnet {

    public static final float KULON = (float) 10;
    public static final float minRadiusOfParticle = 10;
    public static final float drawRadiusOfParticle = 1000;
    //public static final float cameraZ = -100;

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

    public void setCoord(PVector set){
        for(Particle part : particle){
            part.absoluteCoord.add(PVector.mult(coord,-1));
            part.absoluteCoord.add(set);
        }
        coord.set(set);
    }
    public void setCoord(float x, float y, float z) {
        setCoord(new PVector(x,y,z));
    }

    public void setSpeed(PVector set){
        speed.set(set);
    }
    public void setSpeed(float x, float y, float z) {
        setSpeed(new PVector(x,y,z));
    }

    public void setAngle(PVector set){
        angle.set(set);

        angle.x %= PApplet.TWO_PI;
        angle.y %= PApplet.TWO_PI;
        angle.z %= PApplet.TWO_PI;

        for(Particle part : particle){
            part.absoluteCoord.y = part.coord.y * PApplet.cos(angle.x) - part.coord.z * PApplet.sin(angle.x) + this.coord.y;
            part.absoluteCoord.z = part.coord.y * PApplet.sin(angle.x) + part.coord.z * PApplet.cos(angle.x) + this.coord.z;

            part.absoluteCoord.z = part.coord.z * PApplet.cos(angle.y) - part.coord.x * PApplet.sin(angle.y) + this.coord.z;
            part.absoluteCoord.x = part.coord.z * PApplet.sin(angle.y) + part.coord.x * PApplet.cos(angle.y) + this.coord.x;

            part.absoluteCoord.x = part.coord.x * PApplet.cos(angle.z) - part.coord.y * PApplet.sin(angle.z) + this.coord.x;
            part.absoluteCoord.y = part.coord.x * PApplet.sin(angle.z) + part.coord.y * PApplet.cos(angle.z) + this.coord.y;
        }
    }
    public void setAngle(float x, float y, float z) {
        setAngle(new PVector(x,y,z));
    }

    public void setVelocity(PVector set){
        velocity.set(set);
    }
    public void setVelocity(float x, float y, float z) {
        setVelocity(new PVector(x,y,z));
    }

    public Particle[] getParticle(){
        return particle;
    }

    public void display(){
        setCoord(PVector.add(coord, speed));
        setAngle(PVector.add(angle, velocity));
    }

    public static void gravity(Magnet mag1, Magnet mag2){
        for(Particle part1 : mag1.getParticle()){
            for(Particle part2 : mag2.getParticle()){

                PVector force = ForceGravity(part1, part2);
                mag1.forceDisplay(force, part1);
                force.mult(-1);
                mag2.forceDisplay(force, part2);
            }
        }
    }

    private void forceDisplay(PVector force, Particle part){
        speed.add( PVector.div(force, mass) );

        if(momentOfInertia == 0) return;

        PVector Moment = force.cross(PVector.add(part.absoluteCoord, PVector.mult(coord,-1)));
        velocity.add(Moment.div(-momentOfInertia));
    }

    private static PVector ForceGravity(Particle part1, Particle part2){
        PVector vector = new PVector();
        vector.set(part2.absoluteCoord).mult(-1).add(part1.absoluteCoord);
        float F = KULON * part1.charge * part2.charge / Math.max(vector.magSq(), minRadiusOfParticle);
        vector.normalize();
        vector.mult(F);
        return vector;
    }

    public void draw(){
        for(Particle part : particle){

            if(part.absoluteCoord.z < 1) continue;

            if(part.charge > 0) SKETCH.fill(255,0,0);
            else SKETCH.fill(0,0,255);
            SKETCH.ellipse(
                    part.absoluteCoord.x,
                    part.absoluteCoord.y,
                    drawRadiusOfParticle/(part.absoluteCoord.z) ,
                    drawRadiusOfParticle/(part.absoluteCoord.z)
            );
        }
    }

    @Override
    public String toString() {
        return "Magnet{" + "\n" +
                "coord=" + coord + "\n" +
                ", speed=" + speed + "\n" +
                ", angle=" + angle + "\n" +
                ", velocity=" + velocity + "\n" +
                ", particle=" + Arrays.toString(particle) + "\n" +
                ", mass=" + mass + "\n" +
                ", momentOfInertia=" + momentOfInertia + "\n" +
                '}';
    }
}
