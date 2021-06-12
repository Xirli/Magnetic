package model.service;

import model.Param;
import model.magnet.Magnet;
import model.magnet.Particle;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import processing.core.PVector;

import static model.Param.kulon;
import static model.Param.minRadiusOfParticle;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhysicsTest {

    public void assertEqualsPVector(PVector vector1, PVector vector2){
        try {
            assertEquals(vector1.x, vector2.x, 1E-5);
            assertEquals(vector1.y, vector2.y, 1E-5);
            assertEquals(vector1.z, vector2.z, 1E-5);
        }catch(AssertionFailedError e){
            System.out.println("Expected:" + vector1);
            System.out.println("Actual  :" + vector2);
            System.out.println();
            throw e;
        }
    }

    @Test
    public void forceDisplay(){
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+2,0,0),1,5),
                new Particle(new PVector(-2,0,0),1,5)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });
        magnet.setSpeed(1,2,3);
        magnet.setVelocity(4,5,6);

        Physics.forceDisplay(magnet, new PVector(1,1,0), particle[0]);

        assertEqualsPVector(magnet.getSpeed(), new PVector(1.1f,2.1f,3));
        assertEqualsPVector(magnet.getVelocity(), new PVector(4,5,6.05f));
    }

    @Test
    public void zeroForceDisplay(){
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+2,0,0),1,5),
                new Particle(new PVector(-2,0,0),1,5)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });
        magnet.setSpeed(1,2,3);
        magnet.setVelocity(4,5,6);

        Physics.forceDisplay(magnet, new PVector(0,0,0), particle[0]);

        assertEqualsPVector(magnet.getSpeed(), new PVector(1,2,3));
        assertEqualsPVector(magnet.getVelocity(), new PVector(4,5,6));
    }

    @Test
    public void forceGravityLongDistance() {
        Particle part1 = new Particle(new PVector(0,0,0),+1,1);
        Particle part2 = new Particle(new PVector(minRadiusOfParticle+1,0,0),-1,1);

        float F = kulon / ((minRadiusOfParticle+1)*(minRadiusOfParticle+1));

        PVector force = new PVector(F,0,0);

        assertEqualsPVector(Physics.forceGravity(part1,part2), force);
    }

    @Test
    public void forceGravityLowDistance() {
        Particle part1 = new Particle(new PVector(0,0,0),+1,1);
        Particle part2 = new Particle(new PVector(minRadiusOfParticle*0.9f,0,0),+1,1);

        float F = kulon / (minRadiusOfParticle*minRadiusOfParticle);

        PVector force = new PVector(-F,0,0);

        assertEqualsPVector(Physics.forceGravity(part1,part2), force);
    }

    @Test
    public void forceGravityZeroDistance() {
        Particle part1 = new Particle(new PVector(0,0,0),+1,1);
        Particle part2 = new Particle(new PVector(0,0,0),-1,1);

        PVector force = new PVector(0,0,0);

        assertEqualsPVector(Physics.forceGravity(part1,part2), force);
    }
}
