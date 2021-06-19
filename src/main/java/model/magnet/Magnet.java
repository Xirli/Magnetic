package model.magnet;

import view.GraphicObject;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.Arrays;

public class Magnet {

    private final PVector coord;
    private final PVector speed;

    private final PVector velocity;

    private final Particle[] particle;
    private float mass;
    private float momentOfInertia;

    GraphicObject graphic;

    public Magnet(Particle[] particle, GraphicObject graphic){
        this.particle = particle;
        this.graphic = graphic;

        coord = new PVector(0,0,0);
        speed = new PVector(0,0,0);
        velocity = new PVector(0,0,0);

        for(Particle part : this.particle){
            mass += part.mass;
            coord.add(PVector.mult(part.absoluteCoord, part.mass));
        }
        coord.div(mass);

        for(Particle part : this.particle){
            momentOfInertia += part.mass * PVector.sub(part.absoluteCoord, coord).magSq();
        }
    }

    public PVector getCoord(){
        return coord;
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

    public PVector getSpeed(){
        return speed;
    }
    public void setSpeed(PVector set){
        speed.set(set);
    }
    public void setSpeed(float x, float y, float z) {
        setSpeed(new PVector(x,y,z));
    }

    public void rotate(PVector vel){
        float fi = vel.mag();
        vel.normalize();

        for(Particle part : particle){
            float x = part.absoluteCoord.x - coord.x;
            float y = part.absoluteCoord.y - coord.y;
            float z = part.absoluteCoord.z - coord.z;

            PVector newCoord = new PVector();

            newCoord.x = x * matrices0(fi, vel.x)               + y * matrices2(fi, vel.x, vel.y, vel.z) + z * matrices1(fi, vel.x, vel.z, vel.y);
            newCoord.y = x * matrices1(fi, vel.y, vel.x, vel.z) + y * matrices0(fi, vel.y)               + z * matrices2(fi, vel.y, vel.z, vel.x);
            newCoord.z = x * matrices2(fi, vel.z, vel.x, vel.y) + y * matrices1(fi, vel.z, vel.y, vel.x) + z * matrices0(fi, vel.z);

            newCoord.add(coord);

            part.absoluteCoord.set(newCoord);
        }
    }
    public void rotate(float x, float y, float z) {
        rotate(new PVector(x,y,z));
    }
    private float matrices0(float angle, float a){
        return PApplet.cos(angle) + (1 - PApplet.cos(angle))*a*a;
    }
    private float matrices1(float angle, float a, float b, float c){
        return (1 - PApplet.cos(angle))*a*b + PApplet.sin(angle)*c;
    }
    private float matrices2(float angle, float a, float b, float c){
        return (1 - PApplet.cos(angle))*a*b - PApplet.sin(angle)*c;
    }

    public PVector getVelocity(){
        return velocity;
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

    public float getMass() {
        return mass;
    }

    public float getMomentOfInertia() {
        return momentOfInertia;
    }

    public void run(){
        setCoord(PVector.add(coord, speed));
        rotate(velocity);
    }

    public void draw(PApplet sketch){
        graphic.draw(sketch, this);
    }

    @Override
    public String toString() {
        return "Model.Magnet{" + "\n" +
                "coord=" + coord + "\n" +
                ", speed=" + speed + "\n" +
                ", velocity=" + velocity + "\n" +
                ", particle=" + Arrays.toString(particle) + "\n" +
                ", mass=" + mass + "\n" +
                ", momentOfInertia=" + momentOfInertia + "\n" +
                '}';
    }
}
