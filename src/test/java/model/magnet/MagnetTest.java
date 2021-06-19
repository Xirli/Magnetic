package model.magnet;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import processing.core.PVector;

import static model.ParamModelTest.accuracy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MagnetTest {

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
    public void newMagnet() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(4,4,4),1,3),
                new Particle(new PVector(0,0,0),1,1)
        };

        Magnet magnet = new Magnet(particle, (s, m) -> { });

        Particle[] particleResult = new Particle[]{
            new Particle(new PVector(4,4,4),1,3),
            new Particle(new PVector(0,0,0),1,1)
        };

        assertEqualsParticle(particleResult, magnet.getParticle());
        assertEqualsPVector(new PVector(3,3,3), magnet.getCoord());
        assertEquals(36, magnet.getMomentOfInertia());
    }

    @Test
    public void setGetCoord() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(-1,-1,-1),1,1),
                new Particle(new PVector(+1,+1,+1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.setCoord(5,5,5);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(4,4,4),1,1),
                new Particle(new PVector(6,6,6),1,1)
        };

        assertEqualsPVector(new PVector(5, 5, 5), magnet.getCoord());
        assertEqualsParticle(particleResult, magnet.getParticle());
    }

    @Test
    public void setGetSpeed() {
        Magnet magnet = new Magnet(new Particle[]{}, (s, m) -> { });

        magnet.setSpeed(1,2,3);

        assertEqualsPVector(new PVector(1,2,3), magnet.getSpeed());
    }

    @Test
    public void rotateX() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.rotate((float) Math.PI/2, 0,0);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,-1,+1),1,1),
                new Particle(new PVector(-1,+1,-1),1,1)
        };

        assertEqualsParticle(particleResult, magnet.getParticle());
    }

    @Test
    public void rotateY() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.rotate(0,(float) Math.PI/2,0);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,+1,-1),1,1),
                new Particle(new PVector(-1,-1,+1),1,1)
        };

        assertEqualsParticle(particleResult, magnet.getParticle());
    }

    @Test
    public void rotateZ() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.rotate(0,0,(float) Math.PI/2);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(-1,+1,+1),1,1),
                new Particle(new PVector(+1,-1,-1),1,1)
        };

        assertEqualsParticle(particleResult, magnet.getParticle());
    }

    @Test
    public void rotateXY() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,0),1,1),
                new Particle(new PVector(-1,-1,0),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.rotate((float) (-Math.PI/2/Math.sqrt(2)),(float) (Math.PI/2/Math.sqrt(2)),0);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(0,0,(float) (-1.0 * Math.sqrt(2))),1,1),
                new Particle(new PVector(0,0,(float) (+1.0 * Math.sqrt(2))),1,1)
        };

        assertEqualsParticle(particleResult, magnet.getParticle());
    }

    @Test
    public void rotateXYZ() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(-1,+1,0),1,1),
                new Particle(new PVector(+1,-1,0),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.rotate((float) (Math.PI/Math.sqrt(3)),(float) (Math.PI/Math.sqrt(3)),(float) (Math.PI/Math.sqrt(3)));

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,-1,0),1,1),
                new Particle(new PVector(-1,+1,0),1,1)
        };

        assertEqualsParticle(particleResult, magnet.getParticle());
    }

    @Test
    public void rotateZero() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.rotate(0, 0,0);

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };

        assertEqualsParticle(particleResult, magnet.getParticle());
    }

    @Test
    public void setGetVelocity() {
        Magnet magnet = new Magnet(new Particle[]{}, (s, m) -> { });

        magnet.setVelocity(1,2,3);

        assertEqualsPVector(new PVector(1,2,3), magnet.getVelocity());
    }

    @Test
    public void run1step() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.setSpeed(1,1,1);
        magnet.setVelocity((float) Math.PI/2,0,0);

        magnet.run();

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(2,0,2),1,1),
                new Particle(new PVector(0,2,0),1,1)
        };

        assertEqualsParticle(particleResult, magnet.getParticle());
    }

    @Test
    public void run2stepVelocity() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.setVelocity((float) Math.PI/2,0,0);

        magnet.run();

        magnet.setVelocity(0,(float) Math.PI/2,0);

        magnet.run();

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(+1,-1,-1),1,1),
                new Particle(new PVector(-1,+1,+1),1,1)
        };

        assertEqualsParticle(particleResult, magnet.getParticle());
    }

    @Test
    public void run2step() {
        Particle[] particle = new Particle[]{
                new Particle(new PVector(+1,+1,+1),1,1),
                new Particle(new PVector(-1,-1,-1),1,1)
        };
        Magnet magnet = new Magnet(particle, (s, m) -> { });

        magnet.setSpeed(1,1,1);
        magnet.setVelocity((float) Math.PI/2,0,0);

        magnet.run();

        magnet.setSpeed(2,2,2);
        magnet.getVelocity().add(new PVector((float) -Math.PI/2,(float) Math.PI/2,0));

        magnet.run();

        Particle[] particleResult = new Particle[]{
                new Particle(new PVector(4,2,2),1,1),
                new Particle(new PVector(2,4,4),1,1)
        };

        assertEqualsParticle(particleResult, magnet.getParticle());
    }

}
