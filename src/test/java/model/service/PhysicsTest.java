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
import static model.ParamModelTest.accuracy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhysicsTest {

    public void assertEqualsPVector(PVector expected, PVector actual){
        try {
            assertEquals(expected.x, actual.x, accuracy);
            assertEquals(expected.y, actual.y, accuracy);
            assertEquals(expected.z, actual.z, accuracy);
        }catch(AssertionFailedError e){
            System.out.println("Expected:" + expected);
            System.out.println("Actual  :" + actual);
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

        new Physics(magnets).update();

        assertEquals(1, ((MagnetStub)magnets.get(0)).runCount);
        assertEquals(3*(4+5), ((MagnetStub)magnets.get(0)).speedCount);
        assertEquals(3*(4+5)+1, ((MagnetStub)magnets.get(0)).velocityCount);
    }

    @Test
    public void multiUpdate(){
        ArrayList<Magnet> magnets = new ArrayList<Magnet>();
        magnets.add(randomMagnetStub(3));
        magnets.add(randomMagnetStub(4));
        magnets.add(randomMagnetStub(5));

        new Physics(magnets).multiUpdate();

        assertEquals(1, ((MagnetStub)magnets.get(0)).runCount);
        assertEquals(3*(4+5), ((MagnetStub)magnets.get(0)).speedCount);
        assertEquals(3*(4+5)+1, ((MagnetStub)magnets.get(0)).velocityCount);
    }

    @Test
    public void gravity(){
        int count1 = 5;
        int count2 = 5;
        MagnetStub mag1 = randomMagnetStub(count1);
        MagnetStub mag2 = randomMagnetStub(count2);

        Physics.gravity(mag1, mag2);

        assertEquals(count1*count2, mag1.velocityCount);
        assertEquals(count1*count2, mag1.speedCount);
        assertEquals(count1*count2, mag2.velocityCount);
        assertEquals(count1*count2, mag2.speedCount);
    }

    @Test
    public void gravityOneParticle(){
        int count1 = 5;
        int count2 = 1;
        MagnetStub mag1 = randomMagnetStub(count1);
        MagnetStub mag2 = randomMagnetStub(count2);

        Physics.gravity(mag1, mag2);

        System.out.println(mag2.velocityCount);

        assertEquals(count1*count2, mag1.velocityCount);
        assertEquals(count1*count2, mag1.speedCount);
        assertEquals(0, mag2.velocityCount);
        assertEquals(count1*count2, mag1.speedCount);
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

        assertEqualsPVector(new PVector(1.1f,2.1f,3), magnet.getSpeed());
        assertEqualsPVector(new PVector(4,5,6.05f), magnet.getVelocity());
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

        assertEqualsPVector(new PVector(1,2,3), magnet.getSpeed());
        assertEqualsPVector(new PVector(4,5,6), magnet.getVelocity());
    }

    @Test
    public void forceGravityLongDistance() {
        Particle part1 = new Particle(new PVector(0,0,0),+1,1);
        Particle part2 = new Particle(new PVector(minRadiusOfParticle+1,0,0),-1,1);

        float F = kulon / ((minRadiusOfParticle+1)*(minRadiusOfParticle+1));
        PVector forceExpected = new PVector(F,0,0);

        assertEqualsPVector(forceExpected, Physics.forceGravity(part1,part2));
    }

    @Test
    public void forceGravityLowDistance() {
        Particle part1 = new Particle(new PVector(0,0,0),+1,1);
        Particle part2 = new Particle(new PVector(minRadiusOfParticle*0.9f,0,0),+1,1);

        float F = kulon / (minRadiusOfParticle*minRadiusOfParticle);
        PVector forceExpected = new PVector(-F,0,0);

        assertEqualsPVector(forceExpected, Physics.forceGravity(part1,part2));
    }

    @Test
    public void forceGravityZeroDistance() {
        Particle part1 = new Particle(new PVector(0,0,0),+1,1);
        Particle part2 = new Particle(new PVector(0,0,0),-1,1);

        PVector forceExpected = new PVector(0,0,0);

        assertEqualsPVector(forceExpected, Physics.forceGravity(part1,part2));
    }
}
