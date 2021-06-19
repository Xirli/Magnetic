package model.magnet;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import processing.core.PVector;

import static model.ParamModelTest.accuracy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstCoordMagnetTest {

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

    public void assertEqualsParticle(Particle expected, Particle actual){
        assertEqualsPVector(expected.absoluteCoord, actual.absoluteCoord);
        assertEquals(expected.charge, actual.charge);
        assertEquals(expected.mass, actual.mass);
    }

    public void assertEqualsParticle(Particle[] expected, Particle[] actual){
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEqualsParticle(expected[i], actual[i]);
        }
    }

    @Test
    public void run() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new ConstCoordMagnet(particle, (s, m) -> { });

        magnet.setSpeed(1,1,1);

        magnet.run();

        Particle[] particleExpected = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };

        assertEqualsParticle(particleExpected, magnet.getParticle());
    }

    @Test
    public void runWidthVelocity() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new ConstCoordMagnet(particle, (s, m) -> { });

        magnet.setSpeed(1,1,1);
        magnet.setVelocity((float) Math.PI/2,0,0);

        magnet.run();

        Particle[] particleExpected = new Particle[]{
                new Particle(new PVector(+1,-1,+1),1,1),
                new Particle(new PVector(-1,+1,-1),1,1)
        };

        assertEqualsParticle(particleExpected, magnet.getParticle());
    }
}
