import processing.core.PApplet;
import processing.core.PVector;

import java.util.Arrays;

public class Magnet {

    public static final float KULON = (float) 4;
    public static final float minRadiusOfParticle = 10;
    public static final float drawRadiusOfParticle = 1000;
    public static final float rotationSlowdown = (float) 0.999;

    protected final PVector coord;
    protected final PVector speed;

    protected final PVector angle;
    protected final PVector velocity;

    protected final Particle[] particle;
    protected float mass;
    protected float momentOfInertia;

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
        set.normalize();
        float fi = angle.mag();

        for(Particle part : particle){
            float x = part.coord.x;
            float y = part.coord.y;
            float z = part.coord.z;

            PVector newCoord = new PVector(part.coord.x, part.coord.y, part.coord.z);

            newCoord.x = x * matrices0(fi, set.x)               + y * matrices2(fi, set.x, set.y, set.z) + z * matrices1(fi, set.x, set.z, set.y);
            newCoord.y = x * matrices1(fi, set.y, set.x, set.z) + y * matrices0(fi, set.y)               + z * matrices2(fi, set.y, set.z, set.x);
            newCoord.z = x * matrices2(fi, set.z, set.x, set.y) + y * matrices1(fi, set.z, set.y, set.x) + z * matrices0(fi, set.z);

            newCoord.add(coord);

            part.absoluteCoord.set(newCoord);
        }
    }
    private float matrices0(float angle, float a){
        return PApplet.cos(angle) + (1 - PApplet.cos(angle))*a*a ;
    }
    private float matrices1(float angle, float a, float b, float c){
        return (1 - PApplet.cos(angle))*a*b + PApplet.sin(angle)*c  ;
    }
    private float matrices2(float angle, float a, float b, float c){
        return (1 - PApplet.cos(angle))*a*b - PApplet.sin(angle)*c  ;
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
        angle.mult(rotationSlowdown);
    }

    public void draw(){
        for(Particle part : particle){

            if(part.absoluteCoord.z < 1) continue;

            if(part.charge > 0) Main.SKETCH.fill(255,0,0);
            else Main.SKETCH.fill(0,0,255);
            Main.SKETCH.ellipse(
                    100*part.absoluteCoord.x/(part.absoluteCoord.z),
                    100*part.absoluteCoord.y/(part.absoluteCoord.z),
                    drawRadiusOfParticle/(part.absoluteCoord.z),
                    drawRadiusOfParticle/(part.absoluteCoord.z)
            );
        }
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
