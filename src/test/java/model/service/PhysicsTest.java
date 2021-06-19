package model.service;

import model.magnet.Magnet;
import model.magnet.MagnetStub;
import model.magnet.Particle;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import processing.core.PVector;

import java.util.ArrayList;

import static model.ParamModel.kulon;
import static model.ParamModel.minRadiusOfParticle;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

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

    public static MagnetStub randomMagnetStub(int count){
        Particle[] particle = new Particle[count];
        for (int i = 0; i < count; i++) {
            particle[i] = new Particle(new PVector((float)Math.random(),(float)Math.random(),(float)Math.random()),
                    (float)Math.random(),(float)Math.random());
        }
        return new MagnetStub(particle, (s, m) -> { });
    }

    @Test
    public void update(){
        ArrayList<Magnet> magnets = new ArrayList<Magnet>();
        magnets.add(randomMagnetStub(3));
        magnets.add(randomMagnetStub(4));
        magnets.add(randomMagnetStub(5));

        Physics.update(magnets);

        assertEquals(((MagnetStub)magnets.get(0)).runCount, 1);
        assertEquals(((MagnetStub)magnets.get(0)).speedCount, 3*(4+5));
        assertEquals(((MagnetStub)magnets.get(0)).velocityCount, 3*(4+5)+1);
    }

    @Test
    public void gravity(){
        int count1 = 5;
        int count2 = 5;
        MagnetStub mag1 = randomMagnetStub(count1);
        MagnetStub mag2 = randomMagnetStub(count2);

        Physics.gravity(mag1, mag2);

        assertEquals(mag1.velocityCount,count1*count2);
        assertEquals(mag1.speedCount, count1*count2);
        assertEquals(mag2.velocityCount, count1*count2);
        assertEquals(mag2.speedCount, count1*count2);
    }

    @Test
    public void gravityOneParticle(){
        int count1 = 5;
        int count2 = 1;
        MagnetStub mag1 = randomMagnetStub(count1);
        MagnetStub mag2 = randomMagnetStub(count2);

        Physics.gravity(mag1, mag2);

        System.out.println(mag2.velocityCount);

        assertEquals(mag1.velocityCount, count1*count2);
        assertEquals(mag1.speedCount, count1*count2);
        assertEquals(mag2.velocityCount, 0);
        assertEquals(mag1.speedCount,count1*count2);
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
